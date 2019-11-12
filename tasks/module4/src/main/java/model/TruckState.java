package model;

public interface TruckState {
    void process(Truck truck) throws InterruptedException;

    TruckStateType getType();
}
