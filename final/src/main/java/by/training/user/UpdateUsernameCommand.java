package by.training.user;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.validation.UserDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Optional;

public class UpdateUsernameCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UpdateUsernameCommand.class);
    private final ActionCommandType type = ActionCommandType.UPDATE_USERNAME;
    private final UserService userService;

    public UpdateUsernameCommand(UserService userService) {
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


        LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                (Locale) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString()));


        UserDataValidator validator = new UserDataValidator(userService, manager);

        try {

            ValidationResult result = validator.usernameCorrectness(username).and(validator.usernameUniqueness(username));
            if (!result.isValid()) {
                request.setAttribute(AttributesContainer.MESSAGE.toString(), manager.getValue(result.getFirstKey()));
                return Optional.of(new HttpForwarder(PathsContainer.FILE_USER_PROFILE_PAGE));
            }

            HttpSession httpSession = request.getSession();
            UserDto user = (UserDto) httpSession.getAttribute(AttributesContainer.USER.toString());
            user.setUsername(username);
            userService.update(user);

            return Optional.of(new HttpRedirector(request.getContextPath()
                    + PathsContainer.FILE_USER_PROFILE_PAGE));

        } catch (ServiceException | ValidationException e) {
            LOGGER.error("User updating failed.", e);
            throw new ActionCommandExecutionException("User updating failed.", e);
        }


    }


}
