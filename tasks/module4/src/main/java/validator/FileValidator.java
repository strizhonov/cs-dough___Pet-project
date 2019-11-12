package validator;

import org.apache.log4j.Logger;

import java.io.File;

public class FileValidator {

    private static final Logger LOGGER = Logger.getLogger(FileValidator.class);

    public ValidationResult validate(String path) {
        ValidationResult result = new ValidationResult();

        File file = new File(path);

        if (!file.exists()) {
            result.add("path", "File with specified path doesn't exist.");
            LOGGER.error("File with specified path (" + path + ") doesn't exist.");
        }

        return result;
    }

}
