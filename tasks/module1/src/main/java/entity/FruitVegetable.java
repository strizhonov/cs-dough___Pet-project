package entity;

public abstract class FruitVegetable extends Vegetable {

    protected boolean edibleSeeds;

    public FruitVegetable(VegetableType vegetableType, double weight, double calories, boolean edibleSeeds) {
        super(vegetableType, weight, calories);
        this.edibleSeeds = edibleSeeds;
    }

    public boolean isEdibleSeeds() {
        return edibleSeeds;
    }

    public void setEdibleSeeds(boolean edibleSeeds) {
        this.edibleSeeds = edibleSeeds;
    }

}
