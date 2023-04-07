package ua.glumaks.rest.dto.converter;

import org.springframework.stereotype.Component;
import ua.glumaks.rest.dto.UserDTO;
import ua.glumaks.rest.model.User;

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
