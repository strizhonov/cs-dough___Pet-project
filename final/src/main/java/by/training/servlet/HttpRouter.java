fapackage by.training.servlet;

import by.training.constant.PathsContainer;

public interface HttpRouter {

    void dispatch(HttpRequest request, HttpResponse response);
    
}
