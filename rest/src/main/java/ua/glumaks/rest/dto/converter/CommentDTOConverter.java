package ua.glumaks.rest.dto.converter;

import org.springframework.stereotype.Component;
import ua.glumaks.rest.dto.CommentDTO;
import ua.glumaks.rest.model.Comment;

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
