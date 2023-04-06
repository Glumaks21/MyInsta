package com.example.myinsta.security;

public record JWTResponse(
        boolean success,
        String token
){
    public static JWTResponse success(String token) {
        return new JWTResponse(true, token);
    }

    public static JWTResponse failure(String token) {
        return new JWTResponse(false, token);
    }

}
