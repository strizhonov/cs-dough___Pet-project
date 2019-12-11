package by.training.validation;

import by.training.constant.AttributesContainer;
import by.training.constant.MessagesContainer;
import by.training.common.ServiceException;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrganizerDataValidator extends BaseInputValidator {

    private static final Logger LOGGER = LogManager.getLogger(OrganizerDataValidator.class);
    private UserService userService;

    public OrganizerDataValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ValidationResult validate(String... strings) throws ValidationException {
        if (!isArgsCountValid(strings)) {
            LOGGER.error("Illegal number of arguments to validate.");
            throw new ValidationException("Illegal number of arguments to validate.");
        }
        int i = -1;
        return nameUniqueness(strings[++i]);
    }

    @Validation
    public ValidationResult nameUniqueness(String name) throws ValidationException {
        ValidationResult result = new ValidationResult();
        try {
            if (userService.findOrganizerByName(name) != null) {
                result.addIfAbsent(AttributesContainer.NAME_ERROR.toString(),
                        MessagesContainer.NAME_UNIQUENESS_ERROR_MESSAGE);
            }
        } catch (ServiceException e) {
            LOGGER.error("Organizer's name validation failed.", e);
            throw new ValidationException("Organizer's name validation failed.", e);
        }
        return result;
    }

}
