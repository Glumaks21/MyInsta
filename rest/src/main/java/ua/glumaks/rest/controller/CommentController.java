package ua.glumaks.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.glumaks.rest.dto.CommentCreationDTO;
import ua.glumaks.rest.dto.CommentDTO;
import ua.glumaks.rest.model.Comment;
import ua.glumaks.rest.payload.response.MessageResponse;
import ua.glumaks.rest.service.CommentService;

import java.net.URI;

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

    @PreAuthorize("#commentDTO.author() == authentication.principal.username")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<String> create(@RequestBody CommentCreationDTO commentDTO) {
        log.info("Create the comment: {}", commentDTO);
        Comment comment = commentService.createComment(commentDTO);
        return ResponseEntity
                .created(URI.create("/api/comments/" + comment.getId()))
                .build();
    }

    @PreAuthorize("#commentDTO.author() == authentication.principal.username")
    @PutMapping(value = "{id}", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<CommentDTO> update(@PathVariable Long id, @RequestBody CommentDTO changed) {
        log.info("Update the comment: {}", changed);
        return ResponseEntity.ok(commentService.updateComment(id, changed));
    }

    @DeleteMapping(value = "{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        log.info("Create the comment with id: {}", id);
        commentService.deleteCommentById(id);
        return ResponseEntity.ok(MessageResponse.of("Comment successfully deleted"));
    }

}
