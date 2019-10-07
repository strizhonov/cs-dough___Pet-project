package entity;

public abstract class RootVegetable extends Vegetable {

    protected boolean peeled;

    public RootVegetable(VegetableType vegetableType, double weight, double calories, boolean peeled) {
        super(vegetableType, weight, calories);
        this.peeled = peeled;
    }

    public boolean isPeeled() {
        return peeled;
    }

    public void setPeeled(boolean peeled) {
        this.peeled = peeled;
    }

}
