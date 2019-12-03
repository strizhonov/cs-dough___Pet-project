package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.dto.OrganizerDto;
import by.training.dto.UserDto;
import by.training.service.OrganizerService;
import by.training.service.ServiceException;
import by.training.servlet.HttpRouter;
import by.training.servlet.ServletForwarder;
import by.training.validation.OrganizerDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class CreateOrganizerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateOrganizerCommand.class);
    private final ActionCommandType type = ActionCommandType.CREATE_ORGANIZER;
    private OrganizerService organizerService;
    private OrganizerDataValidator validator;

    public CreateOrganizerCommand(OrganizerService organizerService) {
        this.organizerService = organizerService;
        this.validator = new OrganizerDataValidator(organizerService);
    }

    @Override
    public HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        String name = request.getParameter(AttributesContainer.NAME.toString());

        if (!isDataValid(name, validator, request)) {
            return new ServletForwarder(servlet, request.getContextPath());
        }

        HttpSession httpSession = request.getSession();
        UserDto userDto = (UserDto) httpSession.getAttribute(AttributesContainer.USER.toString());
        OrganizerDto organizerDto = OrganizerDto.Builder.anOrganizerDto()
                .name(name)
                .userId(userDto.getId())
                .build();

        try {
            organizerService.create(organizerDto, userDto);
            return new ServletForwarder(servlet, "/jsp/user-profile.jsp");
        } catch (ServiceException e) {
            LOGGER.error("Unable to perform organizer creation and user updating.", e);
            throw new ActionCommandExecutionException("Unable to perform organizer creation and user updating.", e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }

    private boolean isDataValid(String name, OrganizerDataValidator validator, HttpServletRequest request)
            throws ActionCommandExecutionException {
        try {
            ValidationResult result = validator.validate(name);
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
