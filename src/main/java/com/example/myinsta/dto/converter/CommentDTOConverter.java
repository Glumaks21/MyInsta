package com.example.myinsta.dto.converter;

import com.example.myinsta.dto.CommentDTO;
import com.example.myinsta.model.Comment;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CommentDTOConverter implements Function<Comment, CommentDTO> {

    @Override
    public CommentDTO apply(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getMessage(),
                comment.getPost().getId(),
                comment.getAuthor().getUsername()
        );
    }

}
