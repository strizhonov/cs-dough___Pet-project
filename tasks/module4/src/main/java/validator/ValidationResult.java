package validator;

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

    public Map<String, String> getValidationResult() {
        return result;
    }

}
