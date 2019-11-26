package by.training.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

public interface InputDataValidator {

    ValidationResult validate(String... strings) throws ValidationException;

    /**
     * Checks if there is legal length of strings according to class requirements
     */
    default void checkInput(String... strings) throws ValidationException {
        int validationMethods = 0;
        for (Method m : getClass().getMethods()) {
            if (m.isAnnotationPresent(Validation.class)) {
                validationMethods += m.getParameterCount();
            }
        }
        if (validationMethods != strings.length) {
            Logger LOGGER = LogManager.getLogger(this.getClass());
            LOGGER.error("Illegal number of arguments to validate.");
            throw new ValidationException("Illegal number of arguments to validate.");
        }
    }

}
