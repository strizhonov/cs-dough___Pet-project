package controller;

import entity.Vegetable;

import java.util.Comparator;

public class VegetableWeightComparator implements Comparator<Vegetable> {

    private boolean asc;

    public VegetableWeightComparator(boolean asc) {
        this.asc = asc;
    }

    @Override
    public int compare(Vegetable o1, Vegetable o2) {
        if (asc) {
            return Double.compare(o1.getWeight(), o2.getWeight());
        } else {
            return Double.compare(o2.getWeight(), o1.getWeight());
        }

    }

}
