package com.example.myinsta.controller;

import com.example.myinsta.payload.request.LoginRequest;
import com.example.myinsta.payload.request.SignupRequest;
import com.example.myinsta.payload.response.MessageResponse;
import com.example.myinsta.security.JwtResponse;
import com.example.myinsta.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(
        path = "/api/auth",
        consumes = "application/json",
        produces = "application/json"
)
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/authenticate")
    JwtResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/register")
    MessageResponse register(@RequestBody SignupRequest request) {
        userService.register(request);
        return new MessageResponse("User successfully registered");
    }

}
