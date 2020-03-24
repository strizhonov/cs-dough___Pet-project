package by.training.validation;

import by.training.core.ApplicationContext;
import by.training.resourse.AppSetting;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.ValidationRegexp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageValidator {

    protected static final Logger LOGGER = LogManager.getLogger(ImageValidator.class);
    protected final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    protected final LocalizationManager localizationManager;


    public ImageValidator(LocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }


    public ValidationResult imageSize(long size) {
        ValidationResult result = new ValidationResult();

        String sAllowedSize = setting.get(AppSetting.SettingName.IMAGE_ALLOWED_SIZE);
        if (size > Long.parseLong(sAllowedSize)) {
            result.addIfAbsent(AttributesContainer.IMAGE_SIZE_ERROR.toString(),
                    localizationManager.getValue(AttributesContainer.IMAGE_SIZE_ERROR.toString()));
        }

        return result;
    }


    public ValidationResult imageType(String type) throws ValidationException {
        if (type == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

        ValidationResult result = new ValidationResult();

        Pattern pattern = Pattern.compile(ValidationRegexp.IMG_REGEXP);
        Matcher matcher = pattern.matcher(type);

        if (!matcher.find()) {
            result.add(AttributesContainer.WRONG_IMAGE_TYPE.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_IMAGE_TYPE.toString()));
        }
        return result;
    }

}
