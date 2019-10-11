package by.training.entity;

import java.util.Objects;

public class Carrot extends RootVegetable {

    private boolean hasBitterness;

    public Carrot(double weight, double calories, boolean peeled, boolean hasBitterness) {
        super(VegetableType.CARROT, weight, calories, peeled);
        this.hasBitterness = hasBitterness;
    }

    public boolean hasBitterness() {
        return hasBitterness;
    }

    public void setHasBitterness(boolean hasBitterness) {
        this.hasBitterness = hasBitterness;
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
        Carrot carrot = (Carrot) o;
        return this.hasBitterness == carrot.hasBitterness;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hasBitterness);
    }

    @Override
    public String toString() {
        return "Carrot{" +
                "vegetableType=" + vegetableType +
                ", hasBitterness=" + hasBitterness +
                ", peeled=" + peeled +
                ", weight=" + weight +
                ", calories=" + calories +
                '}';
    }

}
