package by.training.resourse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@RunWith(JUnit4.class)
public class ImageConverterTest {

    @Test
    public void fromImg() throws IOException, URISyntaxException {
        URL truckXMLUri = this.getClass().getResource("/img/nobody.jpg");
        ImageConverter.fromImg("", "");
    }

}
