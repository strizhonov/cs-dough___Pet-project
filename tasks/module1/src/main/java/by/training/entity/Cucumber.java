package by.training.entity;

import java.util.Objects;

public class Cucumber extends FruitVegetable {

    private CucumberType cucumberType;

    public Cucumber(double weight, double calories, boolean edibleSeeds, CucumberType cucumberType) {
        super(VegetableType.CUCUMBER, weight, calories, edibleSeeds);
        this.cucumberType = cucumberType;
    }

    public CucumberType getCucumberType() {
        return cucumberType;
    }

    public void setCucumberType(CucumberType cucumberType) {
        this.cucumberType = cucumberType;
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
        Cucumber cucumber = (Cucumber) o;
        return this.cucumberType == cucumber.cucumberType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cucumberType);
    }

    @Override
    public String toString() {
        return "Cucumber{" +
                "vegetableType=" + vegetableType +
                ", cucumberType=" + cucumberType +
                ", edibleSeeds=" + edibleSeeds +
                ", weight=" + weight +
                ", calories=" + calories +
                '}';
    }

}
