package entity;

public abstract class Vegetable {

    protected VegetableType vegetableType;
    protected double weight;
    protected double calories;

    public Vegetable(VegetableType vegetableType, double weight, double calories) {
        this.vegetableType = vegetableType;
        this.weight = weight;
        this.calories = calories;
    }

    public VegetableType getVegetableType() {
        return vegetableType;
    }

    public void setVegetableType(VegetableType vegetableType) {
        this.vegetableType = vegetableType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

}
