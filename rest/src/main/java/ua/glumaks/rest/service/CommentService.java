package ua.glumaks.rest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.glumaks.rest.dto.CommentCreationDTO;
import ua.glumaks.rest.dto.CommentDTO;
import ua.glumaks.rest.model.Comment;

public interface CommentService {

    CommentDTO getCommentById(Long id);
    Page<CommentDTO> getAllComments(Pageable pageable);
    Comment createComment(CommentCreationDTO commentDTO);
    CommentDTO updateComment(Long id, CommentDTO commentDTO);
    void deleteCommentById(Long id);

}
