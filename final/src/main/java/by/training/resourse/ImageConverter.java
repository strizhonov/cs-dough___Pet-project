package by.training.resourse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class ImageConverter {

    public static byte[] fromImg(String path, String format) throws IOException, URISyntaxException {
        if (path == null || format == null) {
            return null;
        }
        BufferedImage bImage = ImageIO.read(new File(path));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, format, bos );
        return bos.toByteArray();
    }

}
