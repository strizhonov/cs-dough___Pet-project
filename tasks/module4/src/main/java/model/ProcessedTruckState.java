package model;

public class ProcessedTruckState implements TruckState {

    private final TruckStateType type = TruckStateType.PROCESSED;

    @Override
    public void process(Truck truck) {
        truck.setWarehouse(null);
    }

    @Override
    public TruckStateType getType() {
        return type;
    }

}
