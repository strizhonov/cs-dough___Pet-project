package by.training.validation;

import java.lang.reflect.Method;

public abstract class BaseInputValidator implements InputDataValidator {

    /**
     * Checks if the length of strings is legal according to class requirements
     * Using {@code newArgs()} method of Validation annotation.
     */
    protected boolean isArgsCountValid(String... strings) throws ValidationException {
        int argsRequired = 0;

        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(Validation.class)) {
                Validation validation = m.getAnnotation(Validation.class);
                argsRequired += validation.newArgs();
            }
        }
        return argsRequired == strings.length;
    }


}
