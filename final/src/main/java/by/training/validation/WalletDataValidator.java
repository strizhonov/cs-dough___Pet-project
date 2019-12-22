package by.training.validation;

import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WalletDataValidator implements InputDataValidator<String> {

    private static final Logger LOGGER = LogManager.getLogger(TournamentDataValidator.class);
    private static final int MAX_VALUE = 1000;
    private final LocalizationManager localizationManager;


    public WalletDataValidator(LocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }


    @Override
    public ValidationResult validate(String value) {
        return positiveDoubleUpTo(value);
    }


    public ValidationResult positiveDoubleUpTo(String sValue) {
        ValidationResult result = new ValidationResult();
        try {

            double value = Double.parseDouble(sValue);

            if (value < 0 || value > MAX_VALUE) {
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
