package com.example.myinsta.payload.response;

public record InvalidLoginResponse(
        String username,
        String password
) {
}
