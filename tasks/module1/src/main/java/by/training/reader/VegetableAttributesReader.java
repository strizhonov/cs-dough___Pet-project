package by.training.reader;

import java.io.IOException;
import java.util.List;

public interface VegetableAttributesReader {

    List<String> getAttributesFrom(String filePath) throws IOException;

}
