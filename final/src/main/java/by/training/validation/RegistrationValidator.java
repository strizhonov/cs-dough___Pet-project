package by.training.validation;

import by.training.constant.AttributesContainer;
import by.training.constant.MessagesContainer;
import by.training.constant.ValuesContainer;
import by.training.service.ServiceException;
import by.training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationValidator extends BaseInputValidator {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationValidator.class);
    private UserService userService;

    public RegistrationValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ValidationResult validate(String... strings) throws ValidationException {
        if (!isArgsCountValid(strings)) {
            LOGGER.error("Illegal number of arguments to validate.");
            throw new ValidationException("Illegal number of arguments to validate.");
        }
        int i = -1;
        ValidationResult usernameCorrectness = usernameCorrectness(strings[++i]);
        ValidationResult usernameUniqueness = usernameUniqueness(strings[++i]);
        ValidationResult emailCorrectness = emailCorrectness(strings[++i]);
        ValidationResult emailUniqueness = emailUniqueness(strings[++i]);
        ValidationResult passwordCorrectness = passwordCorrectness(strings[++i]);
        ValidationResult passwordsEquality = passwordsEquality(strings[++i], strings[++i]);
        return usernameCorrectness
                .and(usernameUniqueness)
                .and(emailCorrectness)
                .and(emailUniqueness)
                .and(passwordCorrectness)
                .and(passwordsEquality);
    }

    @Validation
    private ValidationResult usernameCorrectness(String username) {
        ValidationResult result = new ValidationResult();
        Pattern pattern = Pattern.compile(ValuesContainer.USERNAME_REGEX);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.find()) {
            result.add(AttributesContainer.USERNAME_ERROR.toString(),
                    MessagesContainer.USERNAME_CORRECTNESS_ERROR_MESSAGE);
        }
        return result;
    }

    @Validation
    private ValidationResult usernameUniqueness(String username) throws ValidationException {
        ValidationResult result = new ValidationResult();
        try {
            if (userService.findByUsername(username) != null) {
                result.addIfAbsent(AttributesContainer.USERNAME_ERROR.toString(),
                        MessagesContainer.USERNAME_UNIQUENESS_ERROR_MESSAGE);
            }
        } catch (ServiceException e) {
            LOGGER.error("Username validation failed.", e);
            throw new ValidationException("Username validation failed.", e);
        }
        return result;
    }

    @Validation
    private ValidationResult emailCorrectness(String email) {
        ValidationResult result = new ValidationResult();
        Pattern pattern = Pattern.compile(ValuesContainer.EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            result.add(AttributesContainer.EMAIL_ERROR.toString(),
                    MessagesContainer.EMAIL_CORRECTNESS_ERROR_MESSAGE);
        }
        return result;
    }

    @Validation
    private ValidationResult emailUniqueness(String email) throws ValidationException {
        ValidationResult result = new ValidationResult();
        try {
            if (userService.findByEmail(email) != null) {
                result.add(AttributesContainer.EMAIL_ERROR.toString(),
                        MessagesContainer.EMAIL_UNIQUENESS_ERROR_MESSAGE);
            }
        } catch (ServiceException e) {
            LOGGER.error("Email validation failed.", e);
            throw new ValidationException("Email validation failed.", e);
        }
        return result;
    }

    @Validation
    private ValidationResult passwordCorrectness(String password) {
        ValidationResult result = new ValidationResult();
        Pattern pattern = Pattern.compile(ValuesContainer.PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            result.add(AttributesContainer.PASSWORD_ERROR.toString(),
                    MessagesContainer.PASSWORD_CORRECTNESS_ERROR_MESSAGE);
        }
        return result;
    }

    @Validation
    private ValidationResult passwordsEquality(String password, String passConfirmation) {
        ValidationResult result = new ValidationResult();
        if (password == null || !password.equals(passConfirmation)) {
            result.add(AttributesContainer.PASSWORD_ERROR.toString(),
                    MessagesContainer.PASSWORD_CONFIRMATION_ERROR_MESSAGE);
        }
        return result;
    }

}
