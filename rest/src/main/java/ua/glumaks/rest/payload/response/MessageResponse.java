package ua.glumaks.rest.payload.response;

public record MessageResponse(
        String message
) {
    public static MessageResponse of(String message) {
        return new MessageResponse(message);
    }
}
