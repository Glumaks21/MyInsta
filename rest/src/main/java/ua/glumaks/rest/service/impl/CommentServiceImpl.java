package ua.glumaks.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.glumaks.rest.dto.CommentCreationDTO;
import ua.glumaks.rest.dto.CommentDTO;
import ua.glumaks.rest.dto.converter.CommentDTOConverter;
import ua.glumaks.rest.exception.ResourceForbiddenException;
import ua.glumaks.rest.exception.ResourceNotFoundException;
import ua.glumaks.rest.model.Comment;
import ua.glumaks.rest.model.Post;
import ua.glumaks.rest.model.User;
import ua.glumaks.rest.repository.CommentRepository;
import ua.glumaks.rest.repository.PostRepository;
import ua.glumaks.rest.service.CommentService;
import ua.glumaks.rest.service.UserService;
import ua.glumaks.rest.validators.ObjectsValidator;

import static ua.glumaks.rest.util.SecurityUtil.getAuthenticatedUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepo;
    private final CommentRepository commentRepo;
    private final UserService userService;
    private final ObjectsValidator validator;
    private final CommentDTOConverter converter;


    @Override
    public CommentDTO getCommentById(Long id) {
        return commentRepo.findById(id)
                .map(converter)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " wasn't found"));
    }

    @Override
    public Page<CommentDTO> getAllComments(Pageable pageable) {
        return commentRepo
                .findAll(pageable)
                .map(converter);
    }

    @Override
    public Comment createComment(CommentCreationDTO commentDTO) {
        validator.validate(commentDTO);

        User user = getAuthenticatedUser();
        Post post = postRepo.findById(commentDTO.postId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post with id " + commentDTO.postId() + " wasn't found")
                );

        Comment comment = Comment.builder()
                .post(post)
                .message(commentDTO.message())
                .author(user)
                .build();

        log.debug("Saving comment to db: {}", comment);
        return commentRepo.save(comment);
    }

    @Override
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        validator.validate(commentDTO);

        User user = getAuthenticatedUser();
        Comment comment = getCommentByIdOrThrow(id);

        if (!user.equals(comment.getAuthor())) {
            throw new ResourceForbiddenException("Authenticated user is not author of this comment");
        }

        comment.setMessage(comment.getMessage());

        log.debug("Updating comment in db: {}", comment);
        return converter.apply(commentRepo.save(comment));
    }

    @Override
    public void deleteCommentById(Long id) {
        User user = getAuthenticatedUser();
        Comment comment = getCommentByIdOrThrow(id);

        if (!user.equals(comment.getAuthor())) {
            throw new ResourceForbiddenException("Authenticated user is not author of this comment");
        }

        log.debug("Deleting comment from db: {}", comment);
        commentRepo.delete(comment);
    }

    private Comment getCommentByIdOrThrow(Long id) {
        return commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " wasn't found"));
    }

}
