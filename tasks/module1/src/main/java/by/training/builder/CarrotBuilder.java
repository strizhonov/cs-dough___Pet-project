package by.training.builder;

import by.training.controller.ArgumentValueException;
import by.training.controller.InvalidLineException;
import by.training.entity.Carrot;

import java.util.Map;

public class CarrotBuilder {

    private boolean hasBitterness;
    private boolean peeled;
    private double weight;
    private double calories;
    private Map<String, String> creationData;

    public CarrotBuilder(Map<String, String> creationData) {
        this.creationData = creationData;
    }

    public Carrot build() throws InvalidLineException {
        init();
        return new Carrot(weight, calories, hasBitterness, peeled);
    }

    private void init() throws InvalidLineException {
        initHasBitterness();
        initPeeled();
        initWeight();
        initCalories();
    }

    private void initHasBitterness() throws InvalidLineException {
        String sHasBitterness = creationData.get("has_bitterness");
        if ("true".equalsIgnoreCase(sHasBitterness)) {
            this.hasBitterness = true;
        } else if ("false".equalsIgnoreCase(sHasBitterness)) {
            this.hasBitterness = false;
        } else {
            throw new ArgumentValueException("Illegal boolean hasBitterness field data.");
        }
    }

    private void initPeeled() throws InvalidLineException {
        String sPeeled = creationData.get("peeled");
        if ("true".equalsIgnoreCase(sPeeled)) {
            this.peeled = true;
        } else if ("false".equalsIgnoreCase(sPeeled)) {
            this.peeled = false;
        } else {
            throw new ArgumentValueException("Illegal boolean peeled field data.");
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
