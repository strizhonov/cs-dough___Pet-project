package by.training.entity;

public abstract class LeafyVegetable extends Vegetable {

    protected double sluggishLeavesPercentage;

    public LeafyVegetable(VegetableType vegetableType, double weight, double calories, double sluggishLeavesPercentage) {
        super(vegetableType, weight, calories);
        this.sluggishLeavesPercentage = sluggishLeavesPercentage;
    }

    public double getSluggishLeavesPercentage() {
        return sluggishLeavesPercentage;
    }

    public void setSluggishLeavesPercentage(double sluggishLeavesPercentage) {
        this.sluggishLeavesPercentage = sluggishLeavesPercentage;
    }

}
