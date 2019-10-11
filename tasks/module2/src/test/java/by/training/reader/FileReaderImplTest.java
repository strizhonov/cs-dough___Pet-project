package by.training.reader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class FileReaderImplTest {

    private FileReader fileReader;

    @Before
    public void init() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readValid() throws URISyntaxException, IOException {
        URI uri = this.getClass().getResource("/valid_text.txt").toURI();
        String path = Paths.get(uri).toString();
        String text = fileReader.read(path);
        Assert.assertEquals(116, text.split(" ").length);
    }

}
