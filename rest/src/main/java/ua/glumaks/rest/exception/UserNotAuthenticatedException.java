package ua.glumaks.rest.exception;

public class UserNotAuthenticatedException extends RuntimeException {
    public UserNotAuthenticatedException() {
    }

    public UserNotAuthenticatedException(String message) {
        super(message);
    }

    public UserNotAuthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotAuthenticatedException(Throwable cause) {
        super(cause);
    }
}
