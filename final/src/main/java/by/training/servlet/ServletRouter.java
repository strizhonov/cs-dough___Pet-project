package by.training.servlet;

import by.training.constant.PathsContainer;

public class ServletRouter {

    private String path;
    private ServletRouterState state;

    public ServletRouter() {
        this.path = PathsContainer.INDEX_PAGE_PATH;
        this.state = new ForwardRouterState(path);
    }

    public ServletRouter(String path) {
        this.path = path;
        this.state = new ForwardRouterState(path);
    }

    public ServletRouter(String path, RouterType routerType) {
        this.path = path;
        if (routerType == RouterType.FORWARD) {
            this.state = new ForwardRouterState(path);
        } else if (routerType == RouterType.RELATIVE_REDIRECT) {
            this.state = new RelativeRedirectRouterState(path);
        } else if (routerType == RouterType.ABSOLUTE_REDIRECT) {
            this.state = new AbsoluteRedirectRouterState(path);
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ServletRouterState getState() {
        return state;
    }

    public void setState(RouterType routerType) {
        if (routerType == RouterType.FORWARD) {
            this.state = new ForwardRouterState(path);
        } else if (routerType == RouterType.RELATIVE_REDIRECT) {
            this.state = new RelativeRedirectRouterState(path);
        } else if (routerType == RouterType.ABSOLUTE_REDIRECT) {
            this.state = new AbsoluteRedirectRouterState(path);
        }
    }

    public enum RouterType {
        FORWARD, RELATIVE_REDIRECT, ABSOLUTE_REDIRECT
    }
}
