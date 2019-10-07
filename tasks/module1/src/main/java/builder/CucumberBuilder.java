package builder;

import controller.ArgumentValueException;
import controller.InvalidLineException;
import entity.Cucumber;
import entity.CucumberType;

import java.util.Map;
import java.util.Optional;

public class CucumberBuilder {

    private CucumberType type;
    private boolean edibleSeeds;
    private double weight;
    private double calories;
    private Map<String, String> creationData;

    public CucumberBuilder(Map<String, String> creationData) {
        this.creationData = creationData;
    }

    public Cucumber build() throws InvalidLineException {
        init();
        return new Cucumber(weight, calories, edibleSeeds, type);
    }

    private void init() throws InvalidLineException {
        initType();
        initEdibleSeeds();
        initWeight();
        initCalories();
    }

    private void initType() throws InvalidLineException {
        String key = creationData.get("cucumber_type");
        if (key == null) {
            throw new ArgumentValueException("Illegal CucumberType cucumberType field data.");
        }

        Optional<CucumberType> cucumberType = CucumberType.fromString(key);
        if (cucumberType.isPresent()) {
            this.type = cucumberType.get();
        } else {
            throw new ArgumentValueException("Illegal CucumberType cucumberType field data.");
        }
    }

    private void initEdibleSeeds() throws InvalidLineException {
        String sEdibleSeeds = creationData.get("edible_seeds");
        if ("true".equalsIgnoreCase(sEdibleSeeds)) {
            this.edibleSeeds = true;
        } else if ("false".equalsIgnoreCase(sEdibleSeeds)) {
            this.edibleSeeds = false;
        } else {
            throw new ArgumentValueException("Illegal boolean edibleSeeds field data.");
        }
    }

    private void initWeight() throws InvalidLineException {
        String sWeight = creationData.get("weight");
        if (sWeight == null) {
            throw new ArgumentValueException("No weight argument in the line.");
        }

        double weight;
        try {
            weight = Double.parseDouble(sWeight);
        } catch (NumberFormatException e) {
            throw new ArgumentValueException("Illegal double weight field data.");
        }

        if (weight >= 0) {
            this.weight = weight;
        } else {
            throw new ArgumentValueException("Illegal double weight field data.");
        }
    }

    private void initCalories() throws InvalidLineException {
        String sCalories = creationData.get("calories");
        if (sCalories == null) {
            throw new ArgumentValueException("No calories argument in the line.");
        }

        double calories;
        try {
            calories = Double.parseDouble(sCalories);
        } catch (NumberFormatException e) {
            throw new ArgumentValueException("Illegal double calories field data.");
        }

        if (calories >= 0) {
            this.calories = calories;
        } else {
            throw new ArgumentValueException("Illegal double calories field data.");
        }
    }

}