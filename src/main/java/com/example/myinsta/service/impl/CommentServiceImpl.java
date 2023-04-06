package com.example.myinsta.service.impl;

import com.example.myinsta.dto.CommentCreationDTO;
import com.example.myinsta.dto.CommentDTO;
import com.example.myinsta.dto.converter.CommentDTOConverter;
import com.example.myinsta.exception.ResourceForbiddenException;
import com.example.myinsta.exception.ResourceNotFoundException;
import com.example.myinsta.model.Comment;
import com.example.myinsta.model.Post;
import com.example.myinsta.model.User;
import com.example.myinsta.repository.CommentRepository;
import com.example.myinsta.repository.PostRepository;
import com.example.myinsta.service.CommentService;
import com.example.myinsta.service.UserService;
import com.example.myinsta.util.SecurityUtil;
import com.example.myinsta.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.myinsta.util.SecurityUtil.getAuthenticatedUser;

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
