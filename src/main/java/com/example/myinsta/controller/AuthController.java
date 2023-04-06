package com.example.myinsta.controller;

import com.example.myinsta.payload.request.LoginRequest;
import com.example.myinsta.payload.request.SignupRequest;
import com.example.myinsta.payload.response.MessageResponse;
import com.example.myinsta.security.JWTResponse;
import com.example.myinsta.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        userService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MessageResponse.of("User successfully registered"));
    }

}
