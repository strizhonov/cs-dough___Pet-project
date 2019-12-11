package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.constant.MessagesContainer;
import by.training.constant.PathsContainer;
import by.training.core.AppSetting;
import by.training.common.ServiceException;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpRedirector;
import by.training.user.UserDto;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogInCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(LogInCommand.class);
    private final ActionCommandType type = ActionCommandType.LOGIN;
    private final UserService userService;

    public LogInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        String login = request.getParameter(AttributesContainer.USERNAME.toString());
        String password = request.getParameter(AttributesContainer.PASSWORD.toString());

        UserDto userDto;
        try {
            userDto = userService.findBy(login, password);
        } catch (ServiceException e) {
            LOGGER.error("Unable to retrieve user.", e);
            request.setAttribute(AttributesContainer.USERNAME_ERROR.toString(),
                    MessagesContainer.LOGIN_ERROR_MESSAGE);
            return new HttpForwarder(PathsContainer.LOGIN);
        }
        if (userDto == null) {
            request.setAttribute(AttributesContainer.USERNAME_OR_PASSWORD_ERROR.toString(),
                    MessagesContainer.USERNAME_OR_PASSWORD_ERROR_MESSAGE);
            return new HttpForwarder(PathsContainer.LOGIN);
        }

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(AttributesContainer.USER_ROLE.toString(), userDto.getType().name());
        httpSession.setAttribute(AttributesContainer.USER.toString(), userDto);


        String lang = userDto.getLanguage().name();
        httpSession.setAttribute(AttributesContainer.LANGUAGE.toString(), lang);

        return new HttpForwarder(PathsContainer.TO_USER_PAGE_COMMAND + userDto.getId());
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
