package by.training.user;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Optional;

public class ChangeUserLanguageToEnCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ChangeUserLanguageToEnCommand.class);
    private final ActionCommandType type = ActionCommandType.CHANGE_USER_LANGUAGE_TO_EN;
    private final UserService userService;


    public ChangeUserLanguageToEnCommand(UserService userService) {
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

        user.setLanguage(User.Language.EN);
        httpSession.setAttribute(AttributesContainer.LANGUAGE.toString(), new Locale(AttributesContainer.EN.toString()));

        try {

            userService.update(user);

            return Optional.of(new HttpRedirector(request.getContextPath()
                    + PathsContainer.FILE_USER_PROFILE_PAGE));

        } catch (ServiceException e) {
            LOGGER.error("Language setting failed.", e);
            throw new ActionCommandExecutionException("Language setting failed.", e);
        }
    }

}
