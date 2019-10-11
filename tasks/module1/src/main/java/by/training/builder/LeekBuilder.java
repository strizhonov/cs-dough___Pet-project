package by.training.builder;

import by.training.controller.ArgumentValueException;
import by.training.controller.InvalidLineException;
import by.training.entity.Leek;

import java.util.Map;

public class LeekBuilder {

    private double length;
    private double sluggishLeavesPercentage;
    private double weight;
    private double calories;
    private Map<String, String> creationData;

    public LeekBuilder(Map<String, String> creationData) {
        this.creationData = creationData;
    }

    public Leek build() throws InvalidLineException {
        init();
        return new Leek(weight, calories, sluggishLeavesPercentage, length);
    }

    private void init() throws InvalidLineException {
        initLength();
        initSluggishLeavesPercentage();
        initWeight();
        initCalories();
    }

    private void initLength() throws InvalidLineException {
        String sLength = creationData.get("length");

        double length;
        try {
            length = Double.parseDouble(sLength);
        } catch (NumberFormatException e) {
            throw new ArgumentValueException("Illegal double length field data.");
        }

        if (length >= 0) {
            this.length = length;
        } else {
            throw new ArgumentValueException("Illegal double length field data.");
        }
    }

    private void initSluggishLeavesPercentage() throws InvalidLineException {
        String sSluggishLeavesPercentage = creationData.get("sluggish_leaves_percentage");

        double sluggishLeavesPercentage;
        try {
            sluggishLeavesPercentage = Double.parseDouble(sSluggishLeavesPercentage);
        } catch (NumberFormatException e) {
            throw new ArgumentValueException("Illegal double sluggishLeavesPercentage field data.");
        }

        if (sluggishLeavesPercentage >= 0 && sluggishLeavesPercentage <= 100) {
            this.sluggishLeavesPercentage = sluggishLeavesPercentage;
        } else {
            throw new ArgumentValueException("Illegal double sluggishLeavesPercentage field data.");
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