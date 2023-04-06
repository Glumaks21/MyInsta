package com.example.myinsta.service.impl;

import com.example.myinsta.dto.PostCreationDTO;
import com.example.myinsta.dto.PostDTO;
import com.example.myinsta.dto.converter.PostDTOConverter;
import com.example.myinsta.exception.ResourceForbiddenException;
import com.example.myinsta.exception.ResourceNotFoundException;
import com.example.myinsta.model.Image;
import com.example.myinsta.model.Post;
import com.example.myinsta.model.User;
import com.example.myinsta.repository.ImageRepository;
import com.example.myinsta.repository.PostRepository;
import com.example.myinsta.service.PostService;
import com.example.myinsta.service.UserService;
import com.example.myinsta.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;
    private final ImageRepository imageRepo;
    private final UserService userService;
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

        User author = getCurrentUserOrThrow();
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
        User author = getCurrentUserOrThrow();
        return postRepo
                .findAllByAuthor(author, pageable)
                .map(converter);
    }

    @Override
    public Integer likePostById(Long postId) {
        User user = getCurrentUserOrThrow();
        Post post = getPostByIdOrThrow(postId);

        post.getLikedUsers().add(user);

        postRepo.save(post);
        return post.getLikedUsers().size();
    }

    @Override
    public Integer unlikePostById(Long postId) {
        User user = getCurrentUserOrThrow();
        Post post = getPostByIdOrThrow(postId);

        post.getLikedUsers().remove(user);

        postRepo.save(post);
        return post.getLikedUsers().size();
    }

    @Override
    public PostDTO editPost(Long id, PostDTO postDTO) {
        validator.validate(postDTO);

        User user = getCurrentUserOrThrow();
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
        User user = getCurrentUserOrThrow();
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

    private User getCurrentUserOrThrow() {
        User user = userService.getAuthenticatedUser();
        if (user == null) {
            throw new ResourceForbiddenException("Authenticated user hasn't authorities");
        }
        return user;
    }

}
