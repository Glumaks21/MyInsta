package ua.glumaks.exception;

public class StorageServiceException extends RuntimeException {

    public StorageServiceException(String message) {
        super(message);
    }


    public StorageServiceException(Throwable cause) {
        super(cause);
    }
}
