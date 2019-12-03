package by.training.validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BaseValidatorTest {

    private BaseInputValidator validator;

    @Before
    public void init(){
        validator = new BaseInputValidator() {
            @Override
            public ValidationResult validate(String... strings) throws ValidationException {
                return null;
            }
            @Validation
            public ValidationResult checkOne(String s) throws ValidationException {
                return null;
            }
            @Validation
            public ValidationResult checkTwo() throws ValidationException {
                return null;
            }
            @Validation
            public ValidationResult checkThree(String s1, String s2) throws ValidationException {
                return null;
            }
            @Validation
            public ValidationResult checkFour(String s) throws ValidationException {
                return null;
            }
        };
    }

    @Test
    public void checkInput() throws ValidationException {
        boolean result = validator.isArgsCountValid("1", "2", "3", "4");
        Assert.assertTrue(result);
    }

    @Test
    public void checkIllegalInput() throws ValidationException {
        boolean result = validator.isArgsCountValid("1", "2");
        Assert.assertFalse(result);
    }

}
