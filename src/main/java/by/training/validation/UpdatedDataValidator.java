package by.training.validation;

public interface UpdatedDataValidator<T, V> {

    ValidationResult validate(T previous, V updated) throws ValidationException;

}
