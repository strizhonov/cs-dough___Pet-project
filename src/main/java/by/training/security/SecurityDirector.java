package by.training.security;

import by.training.servlet.HttpRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface SecurityDirector {

    AccessAllowedForType getType();

    Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response);

}