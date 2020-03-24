package by.training.user;

import by.training.core.ServiceException;

public class NotEnoughFundsException extends ServiceException {

    public NotEnoughFundsException(String message) {
        super(message);
    }

    public NotEnoughFundsException(String message, Throwable cause) {
        super(message, cause);
    }

}
