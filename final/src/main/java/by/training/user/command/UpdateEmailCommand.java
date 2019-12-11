package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.common.ServiceException;
import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;
import by.training.tournament.ListTournamentsCommand;
import by.training.user.UserDto;
import by.training.user.UserService;
import by.training.validation.InputDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class UpdateEmailCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UpdateEmailCommand.class);
    private final ActionCommandType type = ActionCommandType.UPDATE_EMAIL;
    private final UserService userService;
    //private InputDataValidator validator;

    public UpdateEmailCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        String email = request.getParameter(AttributesContainer.EMAIL.toString());
//        if (!isDataValid(username, validator, request)) {
//            return new ServletForwarder(servlet, request.getContextPath());
//        }
        HttpSession httpSession = request.getSession();
        UserDto userDto = (UserDto) httpSession.getAttribute(AttributesContainer.USER.toString());
        userDto.setEmail(email);
        try {
            userService.update(userDto);
        } catch (ServiceException e) {
            LOGGER.error("User updating failed.", e);
            throw new ActionCommandExecutionException("User updating failed.", e);
        }

        return new HttpForwarder(PathsContainer.TO_USER_PAGE_COMMAND + userDto.getId());
    }

    private boolean isDataValid(String username, InputDataValidator validator, HttpServletRequest request)
            throws ActionCommandExecutionException {
        try {
            ValidationResult result = validator.validate(username);
            if (!result.isValid()) {
                setErrorAttributes(result.getValidationResult(), request);
                return false;
            }
            return true;
        } catch (ValidationException e) {
            //LOGGER.error("Validation failed.", e);
            throw new ActionCommandExecutionException("Validation failed.", e);
        }
    }

    private void setErrorAttributes(Map<String, String> errorMap, HttpServletRequest request) {
        for (Map.Entry<String, String> entry : errorMap.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }
}
