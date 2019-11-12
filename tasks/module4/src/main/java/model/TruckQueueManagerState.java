package model;

public interface TruckQueueManagerState {
    void process(TruckQueueManager manager) throws TruckQueueManagerStateException;
}
