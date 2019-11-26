package by.training.command.impl;

import by.training.appentry.ApplicationConstantsContainer;
import by.training.command.ActionCommand;
import by.training.command.ActionCommandType;
import by.training.entity.UserDto;
import by.training.service.ServiceException;
import by.training.service.UserService;
import by.training.servlet.ServletRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogInCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(LogInCommand.class);
    private final ActionCommandType type = ActionCommandType.LOGIN;
    private UserService userService;

    public LogInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(ApplicationConstantsContainer.USERNAME);
        String password = request.getParameter(ApplicationConstantsContainer.PASSWORD);

        UserDto userDto;
        try {
            userDto = userService.login(login, password);
        } catch (ServiceException e) {
            LOGGER.error("Unable to retrieve user.", e);
            request.setAttribute(ApplicationConstantsContainer.USERNAME_ERROR,
                    ApplicationConstantsContainer.LOGIN_ERROR_MESSAGE);
            return new ServletRouter(request.getContextPath());
        }
        if (userDto == null) {
            request.setAttribute(ApplicationConstantsContainer.USERNAME_OR_PASSWORD_ERROR,
                    ApplicationConstantsContainer.USERNAME_OR_PASSWORD_ERROR_MESSAGE);
            return new ServletRouter(request.getContextPath());
        }

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(ApplicationConstantsContainer.USER_ROLE, userDto.getType().name());
        httpSession.setAttribute(ApplicationConstantsContainer.USER, userDto);

//        String avatar = Base64.getEncoder().encodeToString(userDto.getAvatar());
//        httpSession.setAttribute("avatar", avatar);

        return new ServletRouter("/jsp/user-profile.jsp");
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
