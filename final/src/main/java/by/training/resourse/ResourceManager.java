package by.training.resourse;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager {

    private ResourceBundle resourceBundle;

    public ResourceManager(String resourceName, String localeCode) {
        resourceBundle = ResourceBundle.getBundle(resourceName, new Locale(localeCode));
    }

    public String getValue(String key) {
        return resourceBundle.getString(key);
    }

}
