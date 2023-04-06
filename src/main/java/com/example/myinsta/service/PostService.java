package com.example.myinsta.service;

import com.example.myinsta.dto.PostCreationDTO;
import com.example.myinsta.dto.PostDTO;
import com.example.myinsta.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


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
