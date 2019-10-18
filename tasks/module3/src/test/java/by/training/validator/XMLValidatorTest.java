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

        URI XMLUri = this.getClass().getResource("/devices.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        URI XSDUri = this.getClass().getResource("/devices_xsd_schema.xml").toURI();
        String XSDPath = Paths.get(XSDUri).toString();

        XMLValidator validator = new XMLValidator();

        Assert.assertTrue(validator.validateXMLWithXSD(XMLPath, XSDPath));
    }

    @Test
    public void invalidXML() throws URISyntaxException {

        URI XMLUri = this.getClass().getResource("/invalid_devices.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        URI XSDUri = this.getClass().getResource("/devices_xsd_schema.xml").toURI();
        String XSDPath = Paths.get(XSDUri).toString();

        XMLValidator validator = new XMLValidator();

        Assert.assertFalse(validator.validateXMLWithXSD(XMLPath, XSDPath));
    }

}
