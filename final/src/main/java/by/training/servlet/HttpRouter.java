fapackage by.training.servlet;

import by.training.constant.PathsContainer;

public interface ServletRouter {

    void dispatch(HttpRequest request, HttpResponse response);
    
}
