package by.training.model;

public interface TruckState {
    void perform(Truck truck) throws InterruptedException;

    TruckStateType getType();
}
