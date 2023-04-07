package ua.glumaks.rest.security;

public class JwtServiceException extends RuntimeException {

    public JwtServiceException() {
    }

    public JwtServiceException(String message) {
        super(message);
    }

    public JwtServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtServiceException(Throwable cause) {
        super(cause);
    }
}
