package by.training.validation;

import by.training.resourse.LocalizationManager;
import by.training.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

@RunWith(JUnit4.class)
public class UserDataValidatorTest {

    private UserDataValidator validator;

    @Before
    public void init() {
        LocalizationManager localizationManager = Mockito.mock(LocalizationManager.class);
        Mockito.when(localizationManager.getValue(Mockito.anyString())).thenReturn("");
        UserService userService = Mockito.mock(UserService.class);

        validator = new UserDataValidator(userService, localizationManager);
    }


    @Test
    public void positiveDouble() {
        String valid = "VALID";
        ValidationResult result = validator.usernameCorrectness(valid);
        Assert.assertTrue(result.getValidationResult().isEmpty());

        valid = "ВАЛИДНО";
        result = validator.usernameCorrectness(valid);
        Assert.assertTrue(result.getValidationResult().isEmpty());

        String invalid = "<INVALID";
        result = validator.usernameCorrectness(invalid);
        Assert.assertFalse(result.getValidationResult().isEmpty());
    }

    @Test
    public void emailCorrectness() {
        String valid = "TEST@TEST.RU";
        ValidationResult result = validator.emailCorrectness(valid);
        Assert.assertTrue(result.getValidationResult().isEmpty());

        valid = "lalala@TEST.comm";
        result = validator.emailCorrectness(valid);
        Assert.assertTrue(result.getValidationResult().isEmpty());

        String invalid = "<INVALID";
        result = validator.emailCorrectness(invalid);
        Assert.assertFalse(result.getValidationResult().isEmpty());

        invalid = "<script>@test.by";
        result = validator.emailCorrectness(invalid);
        Assert.assertFalse(result.getValidationResult().isEmpty());
    }

    @Test
    public void passwordCorrectness() {
        String valid = "PASSWORD";
        ValidationResult result = validator.passwordCorrectness(valid);
        Assert.assertTrue(result.getValidationResult().isEmpty());

        valid = "password@TEST^comm";
        result = validator.passwordCorrectness(valid);
        Assert.assertTrue(result.getValidationResult().isEmpty());

        String invalid = "<INVALID";
        result = validator.passwordCorrectness(invalid);
        Assert.assertFalse(result.getValidationResult().isEmpty());

        invalid = "<script>@test.by";
        result = validator.passwordCorrectness(invalid);
        Assert.assertFalse(result.getValidationResult().isEmpty());
    }



}
