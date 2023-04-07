package ua.glumaks.rest.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.glumaks.rest.exception.UserNotAuthenticatedException;
import ua.glumaks.rest.model.User;

@Component
public class SecurityUtil {

    public static User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }

        return (User) authentication.getPrincipal();
    }

}
