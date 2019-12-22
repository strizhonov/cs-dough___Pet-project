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
    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    private final UserService userService;
    private final LocalizationManager localizationManager;


    public UserDataValidator(UserService userService, LocalizationManager localizationManager) {
        this.userService = userService;
        this.localizationManager = localizationManager;
    }


    @Override
    public ValidationResult validate(UserValidationDto dto) throws ValidationException {
        ValidationResult usernameCorrectness = usernameCorrectness(dto.getUsername());
        if (!usernameCorrectness.isValid()) {
            return usernameCorrectness;
        }

        ValidationResult usernameUniqueness = usernameUniqueness(dto.getUsername());
        if (!usernameUniqueness.isValid()) {
            return usernameUniqueness;
        }

        ValidationResult emailCorrectness = emailCorrectness(dto.getEmail());
        if (!emailCorrectness.isValid()) {
            return emailCorrectness;
        }

        ValidationResult emailUniqueness = emailUniqueness(dto.getEmail());
        if (!emailUniqueness.isValid()) {
            return emailUniqueness;
        }

        ValidationResult passwordCorrectness = passwordCorrectness(dto.getPassword());
        if (!passwordCorrectness.isValid()) {
            return passwordCorrectness;
        }

        ValidationResult passwordsEquality = passwordsEquality(dto.getPassword(), dto.getPasswordConfirmation());
        if (!passwordsEquality.isValid()) {
            return passwordsEquality;
        }

        return new ValidationResult();
    }


    public ValidationResult usernameCorrectness(String username) {
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


    public ValidationResult emailCorrectness(String email) {
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


    public ValidationResult passwordCorrectness(String password) {
        ValidationResult result = new ValidationResult();

        Pattern pattern = Pattern.compile(ValidationRegexp.PASSWORD_REGEXP);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.find()) {
            result.add(AttributesContainer.WRONG_PASSWORD.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_PASSWORD.toString()));
        }
        return result;
    }


    public ValidationResult passwordsEquality(String password, String passConfirmation) {
        ValidationResult result = new ValidationResult();

        if (password == null || !password.equals(passConfirmation)) {
            result.add(AttributesContainer.WRONG_PASSWORD_CONFIRMATION.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_PASSWORD_CONFIRMATION.toString()));
        }

        return result;
    }


    public ValidationResult avatarSize(long size) {
        ValidationResult result = new ValidationResult();

        String sAllowedSize = setting.get(AppSetting.SettingName.IMAGE_ALLOWED_SIZE);
        if (size > Long.parseLong(sAllowedSize)) {
            result.addIfAbsent(AttributesContainer.IMAGE_SIZE_ERROR.toString(),
                    localizationManager.getValue(AttributesContainer.IMAGE_SIZE_ERROR.toString()));
        }

        return result;
    }

}
