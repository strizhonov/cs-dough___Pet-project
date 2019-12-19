package by.training.user.command;

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
import by.training.user.RegistrationDto;
import by.training.user.UserDto;
import by.training.user.UserService;
import by.training.user.UserValidationDto;
import by.training.util.ImageConverter;
import by.training.validation.InputDataValidator;
import by.training.validation.UserDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class SignUpCommand implements ActionCommand {


    private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);
    private final ActionCommandType type = ActionCommandType.SIGN_UP;
    private final UserService userService;


    public SignUpCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        try {

            UserValidationDto validationDto = compile(request);


            LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                    request.getLocale());


            InputDataValidator<UserValidationDto> validator
                    = new UserDataValidator(userService, manager);


            ValidationResult result = validator.validate(validationDto);
            if (!result.isValid()) {
                setErrorMessage(result, request);
                return Optional.of(new HttpForwarder(PathsContainer.FILE_TOURNAMENT_CREATION_PAGE));
            }


            RegistrationDto registrationDto = convert(validationDto, request);
            userService.registerUser(registrationDto);


            return Optional.of(new HttpRedirector(request.getContextPath()
                    + PathsContainer.FILE_AFTER_SIGN_UP));


        } catch (ServiceException | IOException | ValidationException e) {
            LOGGER.error("Unable to perform user creation.", e);
            throw new ActionCommandExecutionException("Unable to perform user creation.", e);
        }

    }


    private UserValidationDto compile(HttpServletRequest request) {

        String username = request.getParameter(AttributesContainer.USERNAME.toString());
        String email = request.getParameter(AttributesContainer.EMAIL.toString());
        String password = request.getParameter(AttributesContainer.PASSWORD.toString());
        String passConfirmation = request.getParameter(AttributesContainer.PASSWORD_CONFIRMATION.toString());

        return new UserValidationDto(username, email, password, passConfirmation);

    }


    private void setErrorMessage(ValidationResult result, HttpServletRequest request) {
        LocalizationManager manager
                = new LocalizationManager(AttributesContainer.I18N.toString(), request.getLocale());


        request.setAttribute(AttributesContainer.MESSAGE.toString(),
                manager.getValue(result.getFirstValue()));
    }


    private RegistrationDto convert(UserValidationDto validationDto, HttpServletRequest request)
            throws IOException {

        byte[] defAvatar = ImageConverter.fromImg(request.getServletContext().getRealPath(PathsContainer.FILE_NOBODY),
                AttributesContainer.JPG.toString());
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setDefAvatar(defAvatar);
        registrationDto.setUsername(validationDto.getUsername());
        registrationDto.setEmail(validationDto.getUsername());
        registrationDto.setPassword(validationDto.getPassword());

        return registrationDto;

    }
}
