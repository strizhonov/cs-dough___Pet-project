package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.constant.MessagesContainer;
import by.training.constant.ValuesContainer;
import by.training.dto.UserDto;
import by.training.entity.User;
import by.training.service.ServiceException;
import by.training.service.UserService;
import by.training.servlet.HttpRouter;
import by.training.servlet.ServletForwarder;
import by.training.validation.RegistrationValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        String username = request.getParameter(AttributesContainer.USERNAME.toString());
        String email = request.getParameter(AttributesContainer.EMAIL.toString());
        String password = request.getParameter(AttributesContainer.PASSWORD.toString());
        String passConfirmation = request.getParameter(AttributesContainer.PASSWORD_CONFIRMATION.toString());

        if (!isDataValid(validator, request, username, email, password, passConfirmation)) {
            return new ServletForwarder(servlet, request.getContextPath());
        }

        HttpSession session = request.getSession();
        String localeName = (String) session.getAttribute(AttributesContainer.LOCALE.toString());
        User.Language language = User.Language.fromLocaleString(localeName).orElse(ValuesContainer.DEFAULT_LANGUAGE);

        UserDto userDto = UserDto.Builder.anUserDto()
                .username(username)
                .email(email)
                .password(password)
                .language(language)
                .build();

        try {
            userService.registerUser(userDto);
        } catch (ServiceException e) {
            LOGGER.error("User registration failed.", e);
            throw new ActionCommandExecutionException("User registration failed.", e);
        }

        request.setAttribute(AttributesContainer.MESSAGE.toString(), MessagesContainer.REGISTERED_MESSAGE);
        return new ServletForwarder(servlet, "/jsp/login.jsp");
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
