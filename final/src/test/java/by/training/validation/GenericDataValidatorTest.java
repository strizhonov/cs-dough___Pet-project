package by.training.validation;

import by.training.resourse.LocalizationManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

@RunWith(JUnit4.class)
public class GenericDataValidatorTest {

    private WalletDataValidator validator;

    @Before
    public void init() {
        LocalizationManager localizationManager = Mockito.mock(LocalizationManager.class);
        Mockito.when(localizationManager.getValue(Mockito.anyString())).thenReturn("");

        validator = new WalletDataValidator(localizationManager);
    }


    @Test
    public void positiveDouble() {
        String valid = "5.8";
        ValidationResult result = validator.positiveDoubleUpTo(valid);
        Assert.assertTrue(result.getValidationResult().isEmpty());

        valid = "66";
        result = validator.positiveDoubleUpTo(valid);
        Assert.assertTrue(result.getValidationResult().isEmpty());

        String invalid = "-6";
        result = validator.positiveDoubleUpTo(invalid);
        Assert.assertFalse(result.getValidationResult().isEmpty());

        invalid = "fgdfgdgs@@@";
        result = validator.positiveDoubleUpTo(invalid);
        Assert.assertFalse(result.getValidationResult().isEmpty());
    }



}
