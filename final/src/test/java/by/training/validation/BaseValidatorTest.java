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
            @Validation(newArgs = 3)
            public ValidationResult checkOne() throws ValidationException {
                return null;
            }
            @Validation(newArgs = 1)
            public ValidationResult checkTwo() throws ValidationException {
                return null;
            }
            @Validation(newArgs = 0)
            public ValidationResult checkThree() throws ValidationException {
                return null;
            }
            @Validation
            public ValidationResult checkFour() throws ValidationException {
                return null;
            }
        };
    }

    @Test
    public void checkInput() throws ValidationException {
        boolean result = validator.isArgsCountValid("1", "2", "3", "4", "5");
        Assert.assertTrue(result);
    }

    @Test
    public void checkIllegalInput() throws ValidationException {
        boolean result = validator.isArgsCountValid("1", "2");
        Assert.assertFalse(result);
    }

}
