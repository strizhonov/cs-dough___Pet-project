package by.training.validation;

import by.training.core.ApplicationContext;
import by.training.core.ServiceException;
import by.training.organizer.OrganizerService;
import by.training.organizer.OrganizerValidationDto;
import by.training.resourse.AppSetting;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.ValidationRegexp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrganizerDataValidator implements InputDataValidator<OrganizerValidationDto> {

    private static final Logger LOGGER = LogManager.getLogger(OrganizerDataValidator.class);
    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    private final OrganizerService organizerService;
    private final LocalizationManager localizationManager;


    public OrganizerDataValidator(OrganizerService organizerService, LocalizationManager localizationManager) {
        this.organizerService = organizerService;
        this.localizationManager = localizationManager;
    }


    @Override
    public ValidationResult validate(OrganizerValidationDto dto) throws ValidationException {
        ValidationResult logoSize = logoSize(dto.getLogoSize());
        if (!logoSize.isValid()) {
            return logoSize;
        }

        ValidationResult nameCorrectness = nameCorrectness(dto.getName());
        if (!nameCorrectness.isValid()) {
            return nameCorrectness;
        }

        ValidationResult nameUniqueness = nameUniqueness(dto.getName());
        if (!nameUniqueness.isValid()) {
            return nameUniqueness;
        }

        return new ValidationResult();
    }


    public ValidationResult logoSize(long size) {
        ValidationResult result = new ValidationResult();

        String sAllowedSize = setting.get(AppSetting.SettingName.IMAGE_ALLOWED_SIZE);
        if (size > Long.parseLong(sAllowedSize)) {
            result.addIfAbsent(AttributesContainer.IMAGE_SIZE_ERROR.toString(),
                    localizationManager.getValue(AttributesContainer.IMAGE_SIZE_ERROR.toString()));
        }

        return result;
    }


    public ValidationResult nameCorrectness(String name) {
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
