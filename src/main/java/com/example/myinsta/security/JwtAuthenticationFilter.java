package com.example.myinsta.security;

import com.example.myinsta.entity.User;
import com.example.myinsta.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private  UserService userService;

    @Lazy
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        log.debug("Received request: {}", request);

        String jwt = getJwt(request);
        if (jwt != null) {
            processJwt(jwt, request);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);

        if (bearerToken == null ||
                !bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return null;
        }

        return bearerToken.substring(7);
    }

    private void processJwt(String jwt, HttpServletRequest request) {
        Long userId = jwtService.extractUserId(jwt);

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.findById(userId);

            if (jwtService.isValid(jwt, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }

}
