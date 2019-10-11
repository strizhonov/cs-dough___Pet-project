package by.training.repository;

import by.training.entity.Vegetable;

public class VegetableCaloriesLowerThanSpecification implements Specification<Vegetable> {

    private double upperBound;

    public VegetableCaloriesLowerThanSpecification(double upperBound) {
        this.upperBound = upperBound;
    }

    @Override
    public boolean isSatisfiedBy(Vegetable vegetable) {
        return vegetable.getCalories() <= upperBound;
    }

}
