package com.example.myinsta.dto.converter;

import com.example.myinsta.dto.UserDTO;
import com.example.myinsta.model.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDTOConvertor implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getBio()
        );
    }

}
