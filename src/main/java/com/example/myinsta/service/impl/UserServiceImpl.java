package com.example.myinsta.service.impl;

import com.example.myinsta.entity.User;
import com.example.myinsta.entity.enums.Role;
import com.example.myinsta.exceptions.ObjectNotValidException;
import com.example.myinsta.exceptions.UserServiceException;
import com.example.myinsta.payload.request.LoginRequest;
import com.example.myinsta.payload.request.SignupRequest;
import com.example.myinsta.repository.UserRepository;
import com.example.myinsta.security.JwtResponse;
import com.example.myinsta.security.JwtService;
import com.example.myinsta.service.UserService;
import com.example.myinsta.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ObjectsValidator validator;

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public User register(SignupRequest request) {
        validator.validate(request);
        if (userRepo.existsByEmail(request.email())) {
            throw new ObjectNotValidException(
                    Set.of("Email is already registered")
            );
        }

        User user = User.builder()
                .email(request.email())
                .name(request.name())
                .surname(request.surname())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(Role.USER))
                .build();

        return userRepo.save(user);
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepo.findByUsername(request.username())
                .orElseThrow(() -> new UserServiceException("User is not registered"));
        String token = jwtService.generateToken(user);
        return JwtResponse.success(token);
    }

}
