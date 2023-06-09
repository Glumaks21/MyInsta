package ua.glumaks.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.glumaks.rest.dto.PostCreationDTO;
import ua.glumaks.rest.dto.PostDTO;
import ua.glumaks.rest.dto.converter.PostDTOConverter;
import ua.glumaks.rest.exception.ResourceForbiddenException;
import ua.glumaks.rest.exception.ResourceNotFoundException;
import ua.glumaks.rest.model.Image;
import ua.glumaks.rest.model.Post;
import ua.glumaks.rest.model.User;
import ua.glumaks.rest.repository.ImageRepository;
import ua.glumaks.rest.repository.PostRepository;
import ua.glumaks.rest.service.PostService;
import ua.glumaks.rest.validators.ObjectsValidator;

import java.util.Optional;

import static ua.glumaks.rest.util.SecurityUtil.getAuthenticatedUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;
    private final ImageRepository imageRepo;
    private final ObjectsValidator validator;
    private final PostDTOConverter converter;


    @Override
    public PostDTO getPostById(Long id) {
        return converter.apply(getPostByIdOrThrow(id));
    }

    @Override
    public Page<PostDTO> getPosts(Pageable pageable) {
        return  postRepo
                .findAll(pageable)
                .map(converter);
    }

    @Override
    public Post createPost(PostCreationDTO postDTO) {
        validator.validate(postDTO);

        User author = getAuthenticatedUser();
        Post post = Post.builder()
                .title(postDTO.title())
                .body(postDTO.body())
                .location(postDTO.location())
                .author(author)
                .build();

        log.debug("Saving post to db: {}", post);
        return postRepo.save(post);
    }

    @Override
    public Page<PostDTO> getUserPosts(Long userId, Pageable pageable) {
        return postRepo
                .findAllByAuthor_Id(userId, pageable)
                .map(converter);
    }

    @Override
    public Page<PostDTO> getUserPosts(Pageable pageable) {
        User author = getAuthenticatedUser();
        return postRepo
                .findAllByAuthor(author, pageable)
                .map(converter);
    }

    @Override
    public Integer likePostById(Long postId) {
        User user = getAuthenticatedUser();
        Post post = getPostByIdOrThrow(postId);

        post.getLikedUsers().add(user);

        postRepo.save(post);
        return post.getLikedUsers().size();
    }

    @Override
    public Integer unlikePostById(Long postId) {
        User user = getAuthenticatedUser();
        Post post = getPostByIdOrThrow(postId);

        post.getLikedUsers().remove(user);

        postRepo.save(post);
        return post.getLikedUsers().size();
    }

    @Override
    public PostDTO editPost(Long id, PostDTO postDTO) {
        validator.validate(postDTO);

        User user = getAuthenticatedUser();
        Post post = getPostByIdOrThrow(id);

        if (!user.equals(post.getAuthor())) {
            throw new ResourceForbiddenException("Authenticated user is not author of post");
        }

        post.setTitle(postDTO.title());
        post.setBody(postDTO.body());
        post.setLocation(postDTO.location());

        log.debug("Updating post in db: {}", post);
        return converter.apply(postRepo.save(post));
    }

    @Override
    public void deletePostById(Long postId) {
        User user = getAuthenticatedUser();
        Post post = getPostByIdOrThrow(postId);

        if (user.equals(post.getAuthor())) {
            throw new ResourceForbiddenException("Authenticated user is not author of post");
        }

        log.debug("Deleting post from db: {}", post);
        postRepo.delete(post);

        Optional<Image> imageCandidate = imageRepo.findByPostId(post.getId());
        if (imageCandidate.isPresent()) {
            log.debug("Delete image from db: {}", imageCandidate);
            imageRepo.delete(imageCandidate.get());
        }
    }

    private Post getPostByIdOrThrow(Long id) {
       return postRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " wasn't found"));
    }

}
