package by.training.appentry;

public class SingleBeanApplicationContextException extends Exception {

    public SingleBeanApplicationContextException() {
    }

    public SingleBeanApplicationContextException(String message) {
        super(message);
    }

    public SingleBeanApplicationContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public SingleBeanApplicationContextException(Throwable cause) {
        super(cause);
    }

    public SingleBeanApplicationContextException(String message, Throwable cause, boolean enableSuppression,
                                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
