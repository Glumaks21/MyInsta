package com.example.myinsta.controller;

import com.example.myinsta.dto.CommentCreationDTO;
import com.example.myinsta.dto.CommentDTO;
import com.example.myinsta.model.Comment;
import com.example.myinsta.payload.response.MessageResponse;
import com.example.myinsta.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/comments", produces = APPLICATION_JSON_VALUE)
@CrossOrigin //TODO add frontend server
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @GetMapping
    ResponseEntity<Page<CommentDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(commentService.getAllComments(pageable));
    }

    @GetMapping("{id}")
    ResponseEntity<CommentDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<String> create(@RequestBody CommentCreationDTO commentDTO) {
        log.info("Create the comment: {}", commentDTO);
        Comment comment = commentService.createComment(commentDTO);
        return ResponseEntity
                .status(CREATED)
                .body("ID: " + comment.getId());
    }

    @PreAuthorize("#commentDTO.author() == authentication.principal.username")
    @PutMapping(value = "{id}", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<CommentDTO> update(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        log.info("Update the comment: {}", commentDTO);
        return ResponseEntity.ok(commentService.updateComment(id, commentDTO));
    }

    @DeleteMapping(value = "{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        log.info("Create the comment with id: {}", id);
        commentService.deleteCommentById(id);
        return ResponseEntity.ok(MessageResponse.of("Comment successfully deleted"));
    }

}
