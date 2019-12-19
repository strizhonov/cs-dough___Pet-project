package by.training.validation;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {

    private Map<String, String> result = new HashMap<>();


    public boolean isValid() {
        return result.isEmpty();
    }


    public void add(String key, String message) {
        result.put(key, message);
    }


    public void addIfAbsent(String key, String message) {
        result.putIfAbsent(key, message);
    }


    public ValidationResult and(ValidationResult addition) {
        for (Map.Entry<String, String> entry : addition.getValidationResult().entrySet()) {
            result.putIfAbsent(entry.getKey(), entry.getValue());
        }
        return this;
    }


    public Map<String, String> getValidationResult() {
        return result;
    }


    public String getFirstValue() {
        for (String value : result.values()) {
            return value;
        }

        throw new IndexOutOfBoundsException("Map is empty, unable to get first element.");
    }

}