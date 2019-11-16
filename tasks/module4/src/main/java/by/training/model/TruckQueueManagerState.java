package by.training.model;

public interface TruckQueueManagerState {
    void performShipment(TruckQueueManager manager) throws TruckQueueManagerStateException;
}
