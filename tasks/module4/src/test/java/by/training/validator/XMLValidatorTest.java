package by.training.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class XMLValidatorTest {

    @Test
    public void validateXMLWithXSD() throws URISyntaxException {

        URI XMLUri = this.getClass().getResource("/valid_trucks.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        URI XSDUri = this.getClass().getResource("/trucks_xsd_schema.xml").toURI();
        String XSDPath = Paths.get(XSDUri).toString();

        XMLValidator validator = new XMLValidator(XSDPath);

        ValidationResult result = validator.validate(XMLPath);
        Assert.assertTrue(result.isValid());
    }

    @Test
    public void invalidXML() throws URISyntaxException {

        URI XMLUri = this.getClass().getResource("/warehouses.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        URI XSDUri = this.getClass().getResource("/trucks_xsd_schema.xml").toURI();
        String XSDPath = Paths.get(XSDUri).toString();

        XMLValidator validator = new XMLValidator(XSDPath);

        ValidationResult result = validator.validate(XMLPath);
        Assert.assertFalse(result.isValid());
    }

    @Test
    public void voidXML() throws URISyntaxException {

        URI XMLUri = this.getClass().getResource("/empty_file.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        URI XSDUri = this.getClass().getResource("/trucks_xsd_schema.xml").toURI();
        String XSDPath = Paths.get(XSDUri).toString();

        XMLValidator validator = new XMLValidator(XSDPath);

        ValidationResult result = validator.validate(XMLPath);
        Assert.assertFalse(result.isValid());
    }

}
