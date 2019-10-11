package by.training.repository;

import by.training.entity.Vegetable;

public class VegetableCaloriesHigherThanSpecification implements Specification<Vegetable> {

    private double lowerBound;

    public VegetableCaloriesHigherThanSpecification(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    @Override
    public boolean isSatisfiedBy(Vegetable vegetable) {
        return vegetable.getCalories() >= lowerBound;
    }

}
