package by.training.validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLValidator {

    private static final Logger LOGGER = Logger.getLogger(XMLValidator.class);

    public boolean validateXMLWithXSD(String XMLPath, String XSDPath) {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File XSDFile = new File(XSDPath);
        File XMLFile = new File(XMLPath);
        Source XMLSource = new StreamSource(XMLFile);

        try {
            Schema schema = factory.newSchema(XSDFile);
            Validator validator = schema.newValidator();
            validator.validate(XMLSource);
            return true;
        } catch (SAXException | IOException e) {
            LOGGER.warn("File " + XMLPath + " is not valid by XSD.");
            return false;
        }
    }

}
