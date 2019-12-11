package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.constant.MessagesContainer;
import by.training.constant.PathsContainer;
import by.training.core.AppSetting;
import by.training.common.ServiceException;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpForwarder;
import by.training.user.User;
import by.training.user.UserDto;
import by.training.user.UserService;
import by.training.util.ImageConverter;
import by.training.validation.RegistrationValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class RegisterCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);
    private final ActionCommandType type = ActionCommandType.REGISTER;

    private UserService userService;
    private RegistrationValidator validator;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
        this.validator = new RegistrationValidator(userService);
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        try {
            byte[] defAvatar = ImageConverter.fromImg(request.getServletContext().getRealPath("/") + "/img/nobody.jpg", "jpg");
            String username = request.getParameter(AttributesContainer.USERNAME.toString());
            String email = request.getParameter(AttributesContainer.EMAIL.toString());
            String password = request.getParameter(AttributesContainer.PASSWORD.toString());
            String passConfirmation = request.getParameter(AttributesContainer.PASSWORD_CONFIRMATION.toString());

            if (!isDataValid(validator, request, username, username, email, email, password, password, passConfirmation)) {
                return new HttpForwarder(PathsContainer.REGISTRATION_PAGE);
            }

            HttpSession session = request.getSession();
            String localeName = (String) session.getAttribute(AttributesContainer.LOCALE.toString());
            User.Language language = User.Language.fromLocaleString(localeName).orElse(User.Language.getDefault());

            UserDto userDto = new UserDto.Builder()
                    .username(username)
                    .email(email)
                    .avatar(defAvatar)
                    .password(password)
                    .language(language)
                    .build();


            userService.registerUser(userDto);
        } catch (ServiceException | IOException e) {
            LOGGER.error("User registration failed.", e);
            throw new ActionCommandExecutionException("User registration failed.", e);
        }

        request.setAttribute(AttributesContainer.MESSAGE.toString(), MessagesContainer.REGISTERED_MESSAGE);
        return new HttpForwarder(PathsContainer.LOGIN);
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }

    private boolean isDataValid(RegistrationValidator validator, HttpServletRequest request, String... data)
            throws ActionCommandExecutionException {
        try {
            ValidationResult result = validator.validate(data);
            if (!result.isValid()) {
                setErrorAttributes(result.getValidationResult(), request);
                return false;
            }
            return true;
        } catch (ValidationException e) {
            LOGGER.error("Validation failed.", e);
            throw new ActionCommandExecutionException("Validation failed.", e);
        }
    }

    private void setErrorAttributes(Map<String, String> errorMap, HttpServletRequest request) {
        for (Map.Entry<String, String> entry : errorMap.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }
}
