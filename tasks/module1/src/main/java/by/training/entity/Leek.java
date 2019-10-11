package by.training.entity;

import java.util.Objects;

public class Leek extends LeafyVegetable {

    private double length;

    public Leek(double weight, double calories, double sluggishLeavesPercentage, double length) {
        super(VegetableType.LEEK, weight, calories, sluggishLeavesPercentage);
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Leek leek = (Leek) o;
        return Double.compare(this.length, leek.length) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), length);
    }

    @Override
    public String toString() {
        return "Leek{" +
                "vegetableType=" + vegetableType +
                ", length=" + length +
                ", sluggishLeavesPercentage=" + sluggishLeavesPercentage +
                ", weight=" + weight +
                ", calories=" + calories +
                '}';
    }

}
