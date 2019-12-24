package by.training.validation;

import by.training.core.ApplicationContext;
import by.training.core.ServiceException;
import by.training.resourse.AppSetting;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.ValidationRegexp;
import by.training.user.UserService;
import by.training.user.UserValidationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataValidator implements InputDataValidator<UserValidationDto> {

    private static final Logger LOGGER = LogManager.getLogger(UserDataValidator.class);
    private final UserService userService;
    private final LocalizationManager localizationManager;


    public UserDataValidator(UserService userService, LocalizationManager localizationManager) {
        this.userService = userService;
        this.localizationManager = localizationManager;
    }


    @Override
    public ValidationResult validate(UserValidationDto dto) throws ValidationException {
        ValidationResult result = usernameCorrectness(dto.getUsername());
        if (!result.isValid()) {
            return result;
        }

        result = usernameUniqueness(dto.getUsername());
        if (!result.isValid()) {
            return result;
        }

        result = emailCorrectness(dto.getEmail());
        if (!result.isValid()) {
            return result;
        }

        result = emailUniqueness(dto.getEmail());
        if (!result.isValid()) {
            return result;
        }

        result = passwordCorrectness(dto.getPassword());
        if (!result.isValid()) {
            return result;
        }

        result = passwordsEquality(dto.getPassword(), dto.getPasswordConfirmation());

        return result;

    }


    public ValidationResult usernameCorrectness(String username) throws ValidationException {
        if (username == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

        ValidationResult result = new ValidationResult();

        Pattern pattern = Pattern.compile(ValidationRegexp.USERNAME_REGEXP);
        Matcher matcher = pattern.matcher(username);

        if (!matcher.find()) {
            result.add(AttributesContainer.WRONG_USERNAME.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_USERNAME.toString()));
        }
        return result;
    }


    public ValidationResult usernameUniqueness(String username) throws ValidationException {
        if (username == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

        ValidationResult result = new ValidationResult();

        try {
            if (userService.findByUsername(username) != null) {
                result.addIfAbsent(AttributesContainer.USERNAME_UNIQUENESS_ERROR.toString(),
                        localizationManager.getValue(AttributesContainer.USERNAME_UNIQUENESS_ERROR.toString()));
            }
        } catch (ServiceException e) {
            LOGGER.error("Username validation failed.", e);
            throw new ValidationException("Username validation failed.", e);
        }

        return result;
    }


    public ValidationResult emailCorrectness(String email) throws ValidationException {
        if (email == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

        ValidationResult result = new ValidationResult();

        Pattern pattern = Pattern.compile(ValidationRegexp.EMAIL_REGEXP, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.find()) {
            result.add(AttributesContainer.WRONG_EMAIL.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_EMAIL.toString()));
        }
        return result;
    }


    public ValidationResult emailUniqueness(String email) throws ValidationException {
        if (email == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

        ValidationResult result = new ValidationResult();

        try {
            if (userService.findByEmail(email) != null) {
                result.add(AttributesContainer.EMAIL_UNIQUENESS_ERROR.toString(),
                        localizationManager.getValue(AttributesContainer.EMAIL_UNIQUENESS_ERROR.toString()));
            }

        } catch (ServiceException e) {
            LOGGER.error("Email validation failed.", e);
            throw new ValidationException("Email validation failed.", e);
        }

        return result;
    }


    public ValidationResult passwordCorrectness(String password) throws ValidationException {
        if (password == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

        ValidationResult result = new ValidationResult();

        Pattern pattern = Pattern.compile(ValidationRegexp.PASSWORD_REGEXP);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.find()) {
            result.add(AttributesContainer.WRONG_PASSWORD.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_PASSWORD.toString()));
        }
        return result;
    }


    public ValidationResult passwordsEquality(String password, String passConfirmation) throws ValidationException {
        if (password == null || passConfirmation == null) {
            throw new ValidationException("Value to validate is null.");
        }

        ValidationResult result = new ValidationResult();

        if (!password.equals(passConfirmation)) {
            result.add(AttributesContainer.WRONG_PASSWORD_CONFIRMATION.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_PASSWORD_CONFIRMATION.toString()));
        }

        return result;
    }

}
