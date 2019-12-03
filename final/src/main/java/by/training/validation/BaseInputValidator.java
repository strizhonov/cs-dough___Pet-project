package by.training.validation;

import java.lang.reflect.Method;

public abstract class BaseInputValidator implements InputDataValidator {

    /**
     * Checks if the length of strings is legal according to class requirements by
     * counting parameters in annotated methods.
     */
    protected boolean isArgsCountValid(String... strings) throws ValidationException {
        int argsRequired = 0;

        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(Validation.class)) {
                argsRequired += m.getParameterCount();
            }
        }
        return argsRequired == strings.length;
    }


}
