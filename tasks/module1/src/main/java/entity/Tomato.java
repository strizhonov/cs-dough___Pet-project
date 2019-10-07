package entity;

import java.util.Objects;

public class Tomato extends FruitVegetable {

    private boolean sweet;

    public Tomato(double weight, double calories, boolean edibleSeeds, boolean sweet) {
        super(VegetableType.TOMATO, weight, calories, edibleSeeds);
        this.sweet = sweet;
    }

    public boolean isSweet() {
        return sweet;
    }

    public void setSweet(boolean sweet) {
        this.sweet = sweet;
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
        Tomato tomato = (Tomato) o;
        return this.sweet == tomato.sweet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sweet);
    }

    @Override
    public String toString() {
        return "Tomato{" +
                "vegetableType=" + vegetableType +
                ", sweet=" + sweet +
                ", edibleSeeds=" + edibleSeeds +
                ", weight=" + weight +
                ", calories=" + calories +
                '}';
    }

}
