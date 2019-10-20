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

    private String XSDPath;

    public XMLValidator(String XSDPath) {
        this.XSDPath = XSDPath;
    }

    public ValidationResult validate(String XMLPath) {
        ValidationResult result = new ValidationResult();

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File XSDFile = new File(XSDPath);
        File XMLFile = new File(XMLPath);
        Source XMLSource = new StreamSource(XMLFile);

        try {
            Schema schema = factory.newSchema(XSDFile);
            Validator validator = schema.newValidator();
            validator.validate(XMLSource);
        } catch (SAXException | IOException e) {
            result.add("XSD validation", "XML file " + XMLPath + " doesn't fit XSD " + XSDPath);
            LOGGER.warn("File " + XMLPath + " is not valid by XSD.", e);
        }

        return result;
    }

}
