package by.training.organizer;

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
import by.training.user.UserDto;
import by.training.util.CommandMapper;
import by.training.util.ServletUtil;
import by.training.validation.OrganizerDataValidator;
import by.training.validation.UpdatedDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class UpdateOrganizerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UpdateOrganizerCommand.class);
    private final ActionCommandType type = ActionCommandType.UPDATE_ORGANIZER;
    private final OrganizerService organizerService;


    public UpdateOrganizerCommand(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {


        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());

        try {

            OrganizerDto organizer = organizerService.findOfUser(user);

            List<FileItem> items = ServletUtil.parseRequest(request);
            OrganizerValidationDto validationDto = CommandMapper.mapOrganizerValidationDto(items);

            LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                    (Locale) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString()));
            UpdatedDataValidator<OrganizerDto, OrganizerValidationDto> validator
                    = new OrganizerDataValidator(organizerService, manager);


            ValidationResult result = validator.validate(organizer, validationDto);
            if (!result.isValid()) {
                request.setAttribute(AttributesContainer.MESSAGE.toString(), manager.getValue(result.getFirstKey()));
                return Optional.of(new HttpForwarder(PathsContainer.FILE_ORGANIZER_EDITING));
            }


            CommandMapper.merge(organizer, validationDto);

            if (organizerService.update(organizer)) {

                return Optional.of(new HttpRedirector(request.getContextPath()
                        + PathsContainer.COMMAND_SHOW_ORGANIZER + organizer.getId()));

            } else {
                throw new ActionCommandExecutionException("Unable to perform organizer updating.");
            }


        } catch (ServiceException | IOException | FileUploadException | ValidationException e) {
            LOGGER.error("Unable to perform organizer updating.", e);
            throw new ActionCommandExecutionException("Unable to perform organizer updating.", e);
        }

    }

}
