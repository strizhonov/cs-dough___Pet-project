package by.training.command.impl;

import by.training.appentry.ApplicationConstantsContainer;
import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.entity.UserDto;
import by.training.service.ServiceException;
import by.training.service.UserService;
import by.training.servlet.ServletRouter;
import by.training.validation.UserDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RegisterCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);
    private final ActionCommandType type = ActionCommandType.REGISTER;

    private UserService userService;
    private UserDataValidator validator;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
        this.validator = new UserDataValidator(userService);
    }

    @Override
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        String username = request.getParameter(ApplicationConstantsContainer.USERNAME);
        String email = request.getParameter(ApplicationConstantsContainer.EMAIL);
        String password = request.getParameter(ApplicationConstantsContainer.PASSWORD);
        String passConfirmation = request.getParameter(ApplicationConstantsContainer.PASSWORD_CONFIRMATION);

        if (!isDataValid(validator, request, username, email, password, passConfirmation)) {
            return new ServletRouter(request.getContextPath());
        }

        UserDto userDto = UserDto.Builder.anUserDto()
                .login(username)
                .email(email)
                .password(password)
                .build();

        try {
            userService.registerUser(userDto);
        } catch (ServiceException e) {
            LOGGER.error("User registration failed.", e);
            throw new ActionCommandExecutionException("User registration failed.", e);
        }

        request.setAttribute(ApplicationConstantsContainer.MESSAGE, ApplicationConstantsContainer.REGISTERED_MESSAGE);
        return new ServletRouter("/jsp/login.jsp");
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }

    private boolean isDataValid(UserDataValidator validator, HttpServletRequest request, String... data)
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
