package by.training.validation;

public interface InputDataValidator {

    ValidationResult validate(String... strings) throws ValidationException;

}
