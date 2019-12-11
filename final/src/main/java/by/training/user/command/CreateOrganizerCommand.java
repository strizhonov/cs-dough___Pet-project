package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.common.ServiceException;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;
import by.training.user.OrganizerDto;
import by.training.user.UserDto;
import by.training.user.UserService;
import by.training.validation.OrganizerDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Map;

public class CreateOrganizerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateOrganizerCommand.class);
    private final ActionCommandType type = ActionCommandType.CREATE_ORGANIZER;
    private final UserService userService;
    private final OrganizerDataValidator validator;

    public CreateOrganizerCommand(UserService userService) {
        this.userService = userService;
        this.validator = new OrganizerDataValidator(userService);
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        try {
            List<FileItem> items = sfu.parseRequest(request);
            byte[] logo = items.get(0).get();
            if (logo == null || logo.length == 0) {
                File file = new File(request.getServletContext().getRealPath(PathsContainer.BLANK_LOGO));
                InputStream is = new FileInputStream(file);
                logo = IOUtils.toByteArray(is);
            }
            String name = items.get(1).getString();
            if (!isDataValid(name, validator, request)) {
                return new HttpForwarder(request.getContextPath() + request.getContextPath());
            }

            HttpSession httpSession = request.getSession();
            UserDto userDto = (UserDto) httpSession.getAttribute(AttributesContainer.USER.toString());
            OrganizerDto organizerDto = OrganizerDto.Builder.anOrganizerDto()
                    .name(name)
                    .logo(logo)
                    .userId(userDto.getId())
                    .build();

            long organizerId = userService.createOrganizer(organizerDto);
            userDto.setOrganizerId(organizerId);
            httpSession.setAttribute(AttributesContainer.USER.toString(), userDto);
            return new HttpForwarder(PathsContainer.TO_USER_PAGE_COMMAND + userDto.getId());
        } catch (ServiceException | IOException | FileUploadException  e) {
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
