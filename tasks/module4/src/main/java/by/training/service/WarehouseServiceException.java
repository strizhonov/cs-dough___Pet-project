package by.training.service;

public class WarehouseServiceException extends Exception {

    public WarehouseServiceException() {
    }

    public WarehouseServiceException(String message) {
        super(message);
    }

    public WarehouseServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WarehouseServiceException(Throwable cause) {
        super(cause);
    }

    public WarehouseServiceException(String message, Throwable cause,
                                     boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
