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
import by.training.util.ServletUtil;
import by.training.validation.InputDataValidator;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class UpdateOrganizerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UpdateOrganizerCommand.class);
    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
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
        try {

            List<FileItem> items = ServletUtil.parseRequest(request);

            OrganizerValidationDto validationDto = compile(items);


            LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                    (String) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString()));


            InputDataValidator<OrganizerValidationDto> validator
                    = new OrganizerDataValidator(organizerService, manager);


            ValidationResult result = validator.validate(validationDto);
            if (!result.isValid()) {

                request.setAttribute(AttributesContainer.MESSAGE.toString(),
                        manager.getValue(result.getFirstValue()));

                return Optional.of(new HttpForwarder(PathsContainer.FILE_ORGANIZER_PROFILE_PAGE));
            }

            HttpSession session = request.getSession();
            UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());



            OrganizerDto genericDto = convert(validationDto, items, request, user);
            if (organizerService.update(genericDto)) {

                return Optional.of(new HttpRedirector(request.getContextPath()
                        + PathsContainer.COMMAND_SHOW_ORGANIZER + genericDto.getId()));

            } else {

                throw new ActionCommandExecutionException("Unable to perform organizer updating.");

            }


        } catch (ServiceException | IOException | FileUploadException | ValidationException e) {
            LOGGER.error("Unable to perform organizer updating.", e);
            throw new ActionCommandExecutionException("Unable to perform organizer updating.", e);
        }

    }


    private OrganizerValidationDto compile(List<FileItem> items) throws IOException {

        int i = -1;
        long logoSize = items.get(++i).getSize();
        String name = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));


        return new OrganizerValidationDto(logoSize, name);

    }


    private OrganizerDto convert(OrganizerValidationDto validationDto, List<FileItem> items,
                                 HttpServletRequest request, UserDto user)
            throws IOException {

        byte[] logo = items.get(0).get();
        if (logo == null || logo.length == 0) {
            File file = new File(request.getServletContext().getRealPath(PathsContainer.FILE_BLANK_LOGO));
            InputStream is = new FileInputStream(file);
            logo = IOUtils.toByteArray(is);
        }

        String sId = request.getParameter(AttributesContainer.ID.toString());

        return OrganizerDto.Builder.anOrganizerDto()
                .id(Long.parseLong(sId))
                .name(validationDto.getName())
                .logo(logo)
                .userId(user.getId())
                .build();

    }
}
