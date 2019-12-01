package by.training.servlet;

public abstract class RedirectRouterState implements RouterState {

    protected String path;

    protected RedirectRouterState(String path) {
        this.path = path;
    }

}
