package by.training.reader;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReaderImpl implements FileReader {

    private static final Logger LOGGER = Logger.getLogger(FileReaderImpl.class);

    @Override
    public String read(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        LOGGER.info("File " + filePath + " was successfully read into bytes.");

        return new String(bytes);
    }

}
