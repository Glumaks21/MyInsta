package ua.glumaks.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.glumaks.rest.dto.PostCreationDTO;
import ua.glumaks.rest.dto.PostDTO;
import ua.glumaks.rest.model.Post;
import ua.glumaks.rest.service.PostService;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/posts", produces = APPLICATION_JSON_VALUE)
@CrossOrigin //TODO add frontend server
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping
    ResponseEntity<Page<PostDTO>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(
                postService.getPosts(pageable)
        );
    }

    @GetMapping("{id}")
    ResponseEntity<PostDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("#postDTO.author() == authentication.principal.username")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<String> create(@RequestBody PostCreationDTO postDTO) {
        log.info("Create the post: {}", postDTO);
        Post post = postService.createPost(postDTO);
        return ResponseEntity
                .created(URI.create("/api/posts/" + post.getId()))
                .build();
    }

    @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<PostDTO> edit(@PathVariable Long id, @RequestBody PostDTO changed) {
        log.info("Update the post: {}", changed);
        return ResponseEntity.ok(postService.editPost(id, changed));
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Delete the post with id: {}", id);
        postService.deletePostById(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

}
