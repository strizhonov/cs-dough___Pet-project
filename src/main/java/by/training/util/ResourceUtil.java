package by.training.util;

public class ResourceUtil {

    public static String fromConstantToProperty(String constant) {
        if (constant == null) {
            return null;
        } else {
            return constant.replace('_', '.').toLowerCase();
        }
    }

}
