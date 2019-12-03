package by.training.servlet;

public abstract class BaseRedirector implements HttpRouter {

    protected String path;

    public BaseRedirector(String path) {
        this.path = path;
    }
}
