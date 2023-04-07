package com.example.myinsta.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.glumaks.rest.model.User;
import ua.glumaks.rest.payload.request.LoginRequest;
import ua.glumaks.rest.payload.request.SignupRequest;
import ua.glumaks.rest.payload.response.MessageResponse;
import ua.glumaks.rest.security.JWTResponse;
import ua.glumaks.rest.service.UserService;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/api/auth", produces = APPLICATION_JSON_VALUE)
@CrossOrigin //TODO add frontend server
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<JWTResponse> login(@RequestBody LoginRequest request) {
        log.info("Login the user: {}", request);
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping(path = "/register", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResponse> register(@RequestBody SignupRequest request) {
        log.info("Register the user: {}", request);
        User user = userService.register(request);
        return ResponseEntity
                .created(URI.create("/api/users/" + user.getId()))
                .build();
    }

}
