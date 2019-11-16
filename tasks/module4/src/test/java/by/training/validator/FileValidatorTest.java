package by.training.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class FileValidatorTest {

    @Test
    public void processValid() throws URISyntaxException {
        URI truckXMLUri = this.getClass().getResource("/valid_trucks.xml").toURI();
        String truckXMLPath = Paths.get(truckXMLUri).toString();

        FileValidator fileValidator = new FileValidator();
        ValidationResult result = fileValidator.validate(truckXMLPath);
        Assert.assertTrue(result.isValid());
    }

    @Test
    public void processInvalid() {
        FileValidator fileValidator = new FileValidator();
        ValidationResult result = fileValidator.validate("INVALID_PATH");
        Assert.assertFalse(result.isValid());
    }

}
