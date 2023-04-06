package com.example.myinsta.service;

import com.example.myinsta.dto.CommentCreationDTO;
import com.example.myinsta.dto.CommentDTO;
import com.example.myinsta.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CommentDTO getCommentById(Long id);
    Page<CommentDTO> getAllComments(Pageable pageable);
    Comment createComment(CommentCreationDTO commentDTO);
    CommentDTO updateComment(Long id, CommentDTO commentDTO);
    void deleteCommentById(Long id);

}
