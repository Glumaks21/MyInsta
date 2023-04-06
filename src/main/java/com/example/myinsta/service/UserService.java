package com.example.myinsta.service;


import com.example.myinsta.dto.UserDTO;
import com.example.myinsta.model.User;
import com.example.myinsta.payload.request.LoginRequest;
import com.example.myinsta.payload.request.SignupRequest;
import com.example.myinsta.security.JWTResponse;

public interface UserService {
    UserDTO findById(Long id);
    User register(SignupRequest request);
    JWTResponse login(LoginRequest request);
    UserDTO update(Long userId, UserDTO userDto);
    User getAuthenticatedUser();
}
