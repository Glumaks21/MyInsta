package com.example.myinsta.security;

public record JwtResponse(
        boolean success,
        String token
){
    public static JwtResponse success(String token) {
        return new JwtResponse(true, token);
    }

    public static JwtResponse failure(String token) {
        return new JwtResponse(false, token);
    }

}
