package com.example.myinsta.service.impl;

import com.example.myinsta.dto.UserDTO;
import com.example.myinsta.dto.converter.UserDTOConvertor;
import com.example.myinsta.exception.ResourceForbiddenException;
import com.example.myinsta.model.User;
import com.example.myinsta.model.enums.Role;
import com.example.myinsta.exception.ObjectNotValidException;
import com.example.myinsta.exception.ResourceNotFoundException;
import com.example.myinsta.exception.UserServiceException;
import com.example.myinsta.payload.request.LoginRequest;
import com.example.myinsta.payload.request.SignupRequest;
import com.example.myinsta.repository.UserRepository;
import com.example.myinsta.security.JWTResponse;
import com.example.myinsta.security.JWTService;
import com.example.myinsta.service.UserService;
import com.example.myinsta.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final ObjectsValidator validator;
    private final UserDTOConvertor convertor;

    @Override
    public UserDTO findById(Long id) {
        return userRepo.findById(id)
                .map(convertor)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " wasn't found"));
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

        log.debug("Saving user to db: {}", user);
        return userRepo.save(user);
    }

    @Override
    public JWTResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepo.findByUsername(request.username())
                .orElseThrow(() -> new UserServiceException("User is not registered"));
        String token = jwtService.generateToken(user);
        return JWTResponse.success(token);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            return userRepo.findById(user.getId()).orElse(null);
        }
        return null;
    }

    @Override
    public UserDTO update(Long userId, UserDTO userDTO) {
        validator.validate(userDTO);

        User currentUser = getAuthenticatedUser();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " wasn't found"));

        if (!currentUser.equals(user)) {
            throw new ResourceForbiddenException("Authenticated user is not owner of the account");
        }

        user.setName(userDTO.name());
        user.setSurname(userDTO.surname());
        user.setEmail(userDTO.email());
        user.setBio(userDTO.bio());

        log.debug("Updating user in db: {}", user);
        return convertor.apply(userRepo.save(user));
    }

}
