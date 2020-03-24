package by.training.resourse;

import by.training.util.ResourceUtil;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationManager {

    private ResourceBundle resourceBundle;

    public LocalizationManager(String resourceName, String lang) {
        resourceBundle = ResourceBundle.getBundle(resourceName, new Locale(lang));
    }

    public LocalizationManager(String resourceName, Locale locale) {
        resourceBundle = ResourceBundle.getBundle(resourceName, locale);
    }

    public String getValue(String key) {
        return resourceBundle.getString(ResourceUtil.fromConstantToProperty(key));
    }

}
