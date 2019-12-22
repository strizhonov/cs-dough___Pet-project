package by.training.organizer;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ApplicationContext;
import by.training.core.ServiceException;
import by.training.resourse.AppSetting;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.user.UserDto;
import by.training.util.CommandMapper;
import by.training.util.ServletUtil;
import by.training.validation.InputDataValidator;
import by.training.validation.OrganizerDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class CreateOrganizerCommand implements ActionCommand {


    private static final Logger LOGGER = LogManager.getLogger(CreateOrganizerCommand.class);
    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    private final ActionCommandType type = ActionCommandType.CREATE_ORGANIZER;
    private final OrganizerService organizerService;


    public CreateOrganizerCommand(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        try {

            List<FileItem> items = ServletUtil.parseRequest(request);

            OrganizerValidationDto validationDto = CommandMapper.mapOrganizerValidationDto(items);


            LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                    (Locale) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString()));


            InputDataValidator<OrganizerValidationDto> validator
                    = new OrganizerDataValidator(organizerService, manager);


            ValidationResult result = validator.validate(validationDto);
            if (!result.isValid()) {
                request.setAttribute(AttributesContainer.MESSAGE.toString(), manager.getValue(result.getFirstKey()));
                return Optional.of(new HttpForwarder(PathsContainer.FILE_ORGANIZER_CREATION));
            }

            HttpSession session = request.getSession();
            UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());


            OrganizerDto genericDto = compile(validationDto, items, request, user);
            long organizerId = organizerService.create(genericDto);


            user.setOrganizerId(organizerId);
            session.setAttribute(AttributesContainer.USER.toString(), user);

            return Optional.of(new HttpRedirector(request.getContextPath()
                    + PathsContainer.COMMAND_SHOW_ORGANIZER + organizerId));


        } catch (ServiceException | IOException | FileUploadException | ValidationException e) {
            LOGGER.error("Unable to perform organizer creation and user updating.", e);
            throw new ActionCommandExecutionException("Unable to perform organizer creation and user updating.", e);
        }

    }


    private OrganizerDto compile(OrganizerValidationDto validationDto, List<FileItem> items, HttpServletRequest request,
                                 UserDto user) throws IOException {

        byte[] logo = items.get(0).get();
        if (logo == null || logo.length == 0) {
            File file = new File(request.getServletContext().getRealPath(PathsContainer.FILE_BLANK_LOGO));
            InputStream is = new FileInputStream(file);
            logo = IOUtils.toByteArray(is);
        }


        return OrganizerDto.Builder.anOrganizerDto()
                .name(validationDto.getName())
                .logo(logo)
                .userId(user.getId())
                .build();

    }

}
