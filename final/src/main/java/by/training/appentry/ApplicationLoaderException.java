package by.training.appentry;

public class ApplicationLoaderException extends RuntimeException {

    public ApplicationLoaderException() {
    }

    public ApplicationLoaderException(String message) {
        super(message);
    }

    public ApplicationLoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationLoaderException(Throwable cause) {
        super(cause);
    }

    public ApplicationLoaderException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
