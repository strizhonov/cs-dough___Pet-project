package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ApplicationContext;
import by.training.core.ServiceException;
import by.training.resourse.AppSetting;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.user.LoginDto;
import by.training.user.UserDto;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LogInCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(LogInCommand.class);
    private final ActionCommandType type = ActionCommandType.LOGIN;
    private final UserService userService;


    public LogInCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {


        String username = request.getParameter(AttributesContainer.USERNAME.toString());
        String password = request.getParameter(AttributesContainer.PASSWORD.toString());


        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(username);
        loginDto.setPassword(password);


        try {
            UserDto userDto = userService.login(loginDto);

            if (userDto == null) {
                LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                        (String) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString()));

                request.setAttribute(AttributesContainer.MESSAGE.toString(),
                        manager.getValue(AttributesContainer.USERNAME_OR_PASSWORD_ERROR.toString()));

                return Optional.of(new HttpForwarder(PathsContainer.FILE_LOGIN));
            }

            HttpSession httpSession = request.getSession();
            httpSession.setAttribute(AttributesContainer.USER.toString(), userDto);


            String lang = userDto.getLanguage().name();
            httpSession.setAttribute(AttributesContainer.LANGUAGE.toString(), lang);

            return Optional.of(new HttpForwarder(PathsContainer.COMMAND_TO_USER_PAGE + userDto.getId()));

        } catch (ServiceException e) {
            LOGGER.error("Unable to retrieve user.", e);
            throw new ActionCommandExecutionException("Unable to retrieve user.", e);
        }
    }

}
