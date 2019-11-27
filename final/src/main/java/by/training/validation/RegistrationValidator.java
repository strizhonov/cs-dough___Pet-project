package by.training.validation;

import by.training.appentry.ApplicationConstantsContainer;
import by.training.service.ServiceException;
import by.training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataValidator implements InputDataValidator {

    private static final Logger LOGGER = LogManager.getLogger(UserDataValidator.class);
    private UserService userService;

    public UserDataValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ValidationResult validate(String... strings) throws ValidationException {
        checkInput(strings);
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
        Pattern pattern = Pattern.compile(ApplicationConstantsContainer.USERNAME_REGEX);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.find()) {
            result.add(ApplicationConstantsContainer.USERNAME_ERROR,
                    ApplicationConstantsContainer.USERNAME_CORRECTNESS_ERROR_MESSAGE);
        }
        return result;
    }

    @Validation
    private ValidationResult usernameUniqueness(String username) throws ValidationException {
        ValidationResult result = new ValidationResult();
        try {
            if (userService.findByUsername(username) != null) {
                result.addIfAbsent(ApplicationConstantsContainer.USERNAME_ERROR,
                        ApplicationConstantsContainer.USERNAME_UNIQUENESS_ERROR_MESSAGE);
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
        Pattern pattern = Pattern.compile(ApplicationConstantsContainer.EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            result.add(ApplicationConstantsContainer.EMAIL_ERROR,
                    ApplicationConstantsContainer.EMAIL_CORRECTNESS_ERROR_MESSAGE);
        }
        return result;
    }

    @Validation
    private ValidationResult emailUniqueness(String email) throws ValidationException {
        ValidationResult result = new ValidationResult();
        try {
            if (userService.findByEmail(email) != null) {
                result.add(ApplicationConstantsContainer.EMAIL_ERROR,
                        ApplicationConstantsContainer.EMAIL_UNIQUENESS_ERROR_MESSAGE);
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
        Pattern pattern = Pattern.compile(ApplicationConstantsContainer.PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            result.add(ApplicationConstantsContainer.PASSWORD_ERROR,
                    ApplicationConstantsContainer.PASSWORD_CORRECTNESS_ERROR_MESSAGE);
        }
        return result;
    }

    @Validation
    private ValidationResult passwordsEquality(String password, String passConfirmation) {
        ValidationResult result = new ValidationResult();
        if (password == null || !password.equals(passConfirmation)) {
            result.add(ApplicationConstantsContainer.PASSWORD_ERROR,
                    ApplicationConstantsContainer.PASSWORD_CONFIRMATION_ERROR_MESSAGE);
        }
        return result;
    }

}
