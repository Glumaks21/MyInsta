package com.example.myinsta.controller;

import com.example.myinsta.dto.UserDTO;
import com.example.myinsta.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/users", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/me")
    ResponseEntity<UserDTO> getAuthenticated() {
        return ResponseEntity.ok(userService.currentUser());
    }

    @GetMapping("{id}")
    ResponseEntity<UserDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping(value = "{id}", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO changed) {
        log.info("Update user: {}", changed);
        return ResponseEntity.ok(userService.update(id, changed));
    }

}
