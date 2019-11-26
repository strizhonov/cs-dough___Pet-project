package by.training.validation;

import by.training.appentry.ApplicationConstantsContainer;
import by.training.service.OrganizerService;
import by.training.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrganizerDataValidator implements InputDataValidator {

    private static final Logger LOGGER = LogManager.getLogger(OrganizerDataValidator.class);
    private OrganizerService organizerService;

    public OrganizerDataValidator(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @Override
    public ValidationResult validate(String... strings) throws ValidationException {
        checkInput(strings);
        int i = -1;
        return nameUniqueness(strings[++i]);
    }

    @Validation
    private ValidationResult nameUniqueness(String name) throws ValidationException {
        ValidationResult result = new ValidationResult();
        try {
            if (organizerService.findByName(name) != null) {
                result.addIfAbsent(ApplicationConstantsContainer.NAME_ERROR,
                        ApplicationConstantsContainer.NAME_UNIQUENESS_ERROR_MESSAGE);
            }
        } catch (ServiceException e) {
            LOGGER.error("Organizer's name validation failed.", e);
            throw new ValidationException("Organizer's name validation failed.", e);
        }
        return result;
    }

}
