package by.training.validation;

public interface InputDataValidator<T> {

    ValidationResult validate(T data) throws ValidationException;

}
