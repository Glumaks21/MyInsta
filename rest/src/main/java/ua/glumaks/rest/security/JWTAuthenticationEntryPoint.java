package ua.glumaks.rest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ua.glumaks.rest.payload.response.InvalidLoginResponse;

import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(SecurityConstants.CONTENT_TYPE);

        ObjectMapper objectMapper = new ObjectMapper();
        InvalidLoginResponse invalidLoginResponse = new InvalidLoginResponse(
                "Invalid login", "Invalid password");
        objectMapper.writeValue(response.getOutputStream(), invalidLoginResponse);
    }
}
