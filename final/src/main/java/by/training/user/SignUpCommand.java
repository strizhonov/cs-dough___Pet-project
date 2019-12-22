package by.training.user;

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
import by.training.util.CommandMapper;
import by.training.util.ServletUtil;
import by.training.validation.InputDataValidator;
import by.training.validation.UserDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
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

            UserValidationDto validationDto = CommandMapper.mapUserValidationDto(request);


            LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                    (Locale) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString()));


            InputDataValidator<UserValidationDto> validator
                    = new UserDataValidator(userService, manager);


            ValidationResult result = validator.validate(validationDto);
            if (!result.isValid()) {
                request.setAttribute(AttributesContainer.MESSAGE.toString(),
                        manager.getValue(result.getFirstKey()));
                return Optional.of(new HttpForwarder(PathsContainer.FILE_LOGIN));
            }


            RegistrationDto registrationDto = compile(validationDto, request);
            userService.registerUser(registrationDto);


            return Optional.of(new HttpRedirector(request.getContextPath()
                    + PathsContainer.FILE_AFTER_SIGN_UP));


        } catch (ServiceException | IOException | ValidationException e) {
            LOGGER.error("Unable to perform user creation.", e);
            throw new ActionCommandExecutionException("Unable to perform user creation.", e);
        }

    }


    private RegistrationDto compile(UserValidationDto validationDto, HttpServletRequest request)
            throws IOException {

        byte[] defAvatar = ServletUtil.fromImg(request.getServletContext().getRealPath(PathsContainer.FILE_NOBODY),
                AttributesContainer.JPG.toString());
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setDefAvatar(defAvatar);
        registrationDto.setUsername(validationDto.getUsername());
        registrationDto.setEmail(validationDto.getEmail());
        registrationDto.setPassword(validationDto.getPassword());

        String sLang = ((Locale) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString())).getLanguage();
        User.Language language = User.Language.fromLocaleString(sLang).orElse(User.Language.getDefault());
        registrationDto.setLanguage(language);

        return registrationDto;

    }
}
