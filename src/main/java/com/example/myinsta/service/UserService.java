package com.example.myinsta.service;


import com.example.myinsta.entity.User;
import com.example.myinsta.payload.request.LoginRequest;
import com.example.myinsta.payload.request.SignupRequest;
import com.example.myinsta.security.JwtResponse;

public interface UserService {
    User findById(Long id);
    User register(SignupRequest request);
    JwtResponse login(LoginRequest request);
}
