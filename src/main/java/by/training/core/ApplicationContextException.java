package by.training.core;

public class ApplicationContextException extends RuntimeException {

    public ApplicationContextException(String message) {
        super(message);
    }

    public ApplicationContextException(String message, Throwable cause) {
        super(message, cause);
    }

}
