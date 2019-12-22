package by.training.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ValidationResultTest {


    @Test(expected = IndexOutOfBoundsException.class)
    public void emptyGetFirst() {
        new ValidationResult().getFirstKey();
    }


}
