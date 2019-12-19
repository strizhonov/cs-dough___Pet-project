package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.user.User;
import by.training.user.UserDto;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ChangeUserLanguageToRuCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ChangeUserLanguageToRuCommand.class);
    private final ActionCommandType type = ActionCommandType.CHANGE_USER_LANGUAGE_TO_RU;
    private final UserService userService;


    public ChangeUserLanguageToRuCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        HttpSession httpSession = request.getSession();
        UserDto user = (UserDto) httpSession.getAttribute(AttributesContainer.USER.toString());
        user.setLanguage(User.Language.RU);
        try {

            userService.update(user);

            httpSession.setAttribute(AttributesContainer.LANGUAGE.toString(), AttributesContainer.RU.toString());

            return Optional.of(new HttpRedirector(request.getContextPath()
                    + PathsContainer.COMMAND_TO_USER_PAGE + user.getId()));

        } catch (ServiceException e) {
            LOGGER.error("Language setting failed.", e);
            throw new ActionCommandExecutionException("Language setting failed.", e);
        }
    }

}
