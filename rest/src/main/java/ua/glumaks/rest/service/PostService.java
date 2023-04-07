package ua.glumaks.rest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.glumaks.rest.dto.PostCreationDTO;
import ua.glumaks.rest.dto.PostDTO;
import ua.glumaks.rest.model.Post;


public interface PostService {
    PostDTO getPostById(Long id);
    Page<PostDTO> getPosts(Pageable pageable);
    Post createPost(PostCreationDTO postDTO);
    Page<PostDTO> getUserPosts(Long userId, Pageable pageable);
    Page<PostDTO> getUserPosts(Pageable pageable);
    Integer likePostById(Long postId);
    Integer unlikePostById(Long postId);
    PostDTO editPost(Long id, PostDTO postDTO);
    void deletePostById(Long postId);

}
