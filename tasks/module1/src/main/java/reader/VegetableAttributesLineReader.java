package reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class VegetableAttributesLineReader implements VegetableAttributesReader {

    @Override
    public List<String> getAttributesFrom(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path);
    }

}
