package controller;

import entity.Vegetable;

import java.util.Comparator;

public class VegetableCaloriesComparator implements Comparator<Vegetable> {

    private boolean asc;

    public VegetableCaloriesComparator(boolean asc) {
        this.asc = asc;
    }

    @Override
    public int compare(Vegetable o1, Vegetable o2) {
        if (asc) {
            return Double.compare(o1.getCalories(), o2.getCalories());
        } else {
            return Double.compare(o2.getCalories(), o1.getCalories());
        }
    }

}
