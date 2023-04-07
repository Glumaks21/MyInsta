package ua.glumaks.rest.payload.response;

public record InvalidLoginResponse(
        String username,
        String password
) {
}
