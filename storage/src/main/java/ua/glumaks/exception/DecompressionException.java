package ua.glumaks.exception;

public class DecompressionException extends RuntimeException {
    public DecompressionException(Throwable throwable) {
        super(throwable);
    }
}
