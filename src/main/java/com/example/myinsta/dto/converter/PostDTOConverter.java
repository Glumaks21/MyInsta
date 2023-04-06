package com.example.myinsta.dto.converter;

import com.example.myinsta.dto.PostDTO;
import com.example.myinsta.model.Post;
import com.example.myinsta.model.User;
import com.example.myinsta.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PostDTOConverter implements Function<Post, PostDTO> {

    private final PostRepository postRepo;

    @Override
    public PostDTO apply(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getAuthor().getUsername(),
                post.getLocation(),
                postRepo.countLikesById(post.getId())
        );
    }

}
