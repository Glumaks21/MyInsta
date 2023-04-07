package ua.glumaks.rest.service;


import ua.glumaks.rest.dto.UserDTO;
import ua.glumaks.rest.model.User;
import ua.glumaks.rest.payload.request.LoginRequest;
import ua.glumaks.rest.payload.request.SignupRequest;
import ua.glumaks.rest.security.JWTResponse;

public interface UserService {
    UserDTO findById(Long id);
    User register(SignupRequest request);
    JWTResponse login(LoginRequest request);
    UserDTO update(Long userId, UserDTO userDto);
    UserDTO currentUser();
}
