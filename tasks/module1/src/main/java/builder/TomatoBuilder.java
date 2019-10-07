package builder;

import controller.ArgumentValueException;
import controller.InvalidLineException;
import entity.Tomato;

import java.util.Map;

public class TomatoBuilder {

    private boolean sweet;
    private boolean edibleSeeds;
    private double weight;
    private double calories;
    private Map<String, String> creationData;

    public TomatoBuilder(Map<String, String> creationData) {
        this.creationData = creationData;
    }

    public Tomato build() throws InvalidLineException {
        init();
        return new Tomato(weight, calories, edibleSeeds, sweet);
    }

    private void init() throws InvalidLineException {
        initSweet();
        initEdibleSeeds();
        initWeight();
        initCalories();
    }

    private void initSweet() throws InvalidLineException {
        String sSweet = creationData.get("sweet");
        if ("true".equalsIgnoreCase(sSweet)) {
            this.sweet = true;
        } else if ("false".equalsIgnoreCase(sSweet)) {
            this.sweet = false;
        } else {
            throw new ArgumentValueException("Illegal boolean sweet field data.");
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
