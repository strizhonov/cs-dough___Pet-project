package by.training.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class ImageConverterTest {

    @Test
    public void convert() throws URISyntaxException, IOException {
        URI uri = this.getClass().getResource("/test.jpg").toURI();
        String path = Paths.get(uri).toString();
        byte[] image = ServletUtil.fromImg(path, "jpg");
        Assert.assertTrue(image != null && image.length > 0);
    }


}
