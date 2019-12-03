package by.training.security;

import by.training.servlet.BaseRedirector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface SecuritySupervisor<SECURITY_TYPE> {

    SECURITY_TYPE getType();

    Optional<BaseRedirector> direct(HttpServletRequest request, HttpServletResponse response);

}