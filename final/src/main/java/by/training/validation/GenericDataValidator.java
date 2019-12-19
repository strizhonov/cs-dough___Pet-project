package by.training.validation;

import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenericDataValidator implements InputDataValidator<String> {

    private static final Logger LOGGER = LogManager.getLogger(TournamentDataValidator.class);
    private final LocalizationManager localizationManager;


    public GenericDataValidator(LocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }


    @Override
    public ValidationResult validate(String value) {
        return positiveDouble(value);
    }


    public ValidationResult positiveDouble(String value) {
        ValidationResult result = new ValidationResult();
        try {
            if (Double.parseDouble(value) < 0) {
                LOGGER.error(value + " is not valid double value.");
                throw new NumberFormatException(value + " is not valid double value.");
            }

        } catch (NumberFormatException e) {
            result.add(AttributesContainer.NON_POSITIVE_INTEGER.toString(),
                    localizationManager.getValue(AttributesContainer.NON_POSITIVE_INTEGER.toString()));
        }

        return result;
    }



}
