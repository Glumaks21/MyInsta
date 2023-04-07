package ua.glumaks.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.glumaks.rest.dto.UserDTO;
import ua.glumaks.rest.dto.converter.UserDTOConvertor;
import ua.glumaks.rest.exception.ObjectNotValidException;
import ua.glumaks.rest.exception.ResourceForbiddenException;
import ua.glumaks.rest.exception.ResourceNotFoundException;
import ua.glumaks.rest.exception.UserServiceException;
import ua.glumaks.rest.model.User;
import ua.glumaks.rest.model.enums.Role;
import ua.glumaks.rest.payload.request.LoginRequest;
import ua.glumaks.rest.payload.request.SignupRequest;
import ua.glumaks.rest.repository.UserRepository;
import ua.glumaks.rest.security.JWTResponse;
import ua.glumaks.rest.security.JWTService;
import ua.glumaks.rest.service.UserService;
import ua.glumaks.rest.validators.ObjectsValidator;

import java.util.Set;

import static ua.glumaks.rest.util.SecurityUtil.getAuthenticatedUser;

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
    public UserDTO currentUser() {
        return convertor.apply(getAuthenticatedUser());
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
