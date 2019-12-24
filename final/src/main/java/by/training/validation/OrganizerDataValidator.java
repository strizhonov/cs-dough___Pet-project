package by.training.validation;

import by.training.core.ServiceException;
import by.training.organizer.OrganizerDto;
import by.training.organizer.OrganizerService;
import by.training.organizer.OrganizerValidationDto;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.ValidationRegexp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrganizerDataValidator extends ImageValidator implements InputDataValidator<OrganizerValidationDto>,
        UpdatedDataValidator<OrganizerDto, OrganizerValidationDto> {

    private static final Logger LOGGER = LogManager.getLogger(OrganizerDataValidator.class);
    private final OrganizerService organizerService;


    public OrganizerDataValidator(OrganizerService organizerService, LocalizationManager localizationManager) {
        super(localizationManager);
        this.organizerService = organizerService;
    }


    @Override
    public ValidationResult validate(OrganizerValidationDto dto) throws ValidationException {
        ValidationResult result = imageSize(dto.getLogoSize());
        if (!result.isValid()) {
            return result;
        }

        result = imageType(dto.getLogoType());
        if (!result.isValid()) {
            return result;
        }

        result = nameCorrectness(dto.getName());
        if (!result.isValid()) {
            return result;
        }

        result = nameUniqueness(dto.getName());

        return result;

    }


    @Override
    public ValidationResult validate(OrganizerDto previous, OrganizerValidationDto updated) throws ValidationException {
        ValidationResult result = new ValidationResult();

        if (!Arrays.equals(previous.getLogo(), updated.getLogo())) {
            result = imageSize(updated.getLogoSize());
            if (result.isValid()) {
                return result;
            }

            result = imageType(updated.getLogoType());
            if (result.isValid()) {
                return result;
            }

        }

        String newName = updated.getName();
        if (!previous.getName().equals(newName)) {
            result = nameCorrectness(newName);
            if (result.isValid()) {
                return result;
            }

            result = nameUniqueness(newName);

        }

        return result;


    }


    public ValidationResult nameCorrectness(String name) throws ValidationException {
        if (name == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

        ValidationResult result = new ValidationResult();

        Pattern pattern = Pattern.compile(ValidationRegexp.ORGANIZER_NAME_REGEXP);
        Matcher matcher = pattern.matcher(name);

        if (!matcher.find()) {
            result.add(AttributesContainer.WRONG_ORGANIZER_NAME.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_ORGANIZER_NAME.toString()));
        }
        return result;
    }


    public ValidationResult nameUniqueness(String name) throws ValidationException {
        if (name == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

        ValidationResult result = new ValidationResult();

        try {
            if (organizerService.findByName(name) != null) {
                result.add(AttributesContainer.ORGANIZER_NAME_UNIQUENESS_ERROR.toString(),
                        localizationManager.getValue(AttributesContainer.ORGANIZER_NAME_UNIQUENESS_ERROR.toString()));
            }
        } catch (ServiceException e) {
            LOGGER.error("Organizer's name validation failed.", e);
            throw new ValidationException("Organizer's name validation failed.", e);
        }

        return result;
    }


}
