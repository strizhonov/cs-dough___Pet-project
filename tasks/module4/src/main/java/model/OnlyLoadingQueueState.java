package model;

import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class OnlyLoadingQueueState implements TruckQueueManagerState {

    private static final Logger LOGGER = Logger.getLogger(OnlyLoadingQueueState.class);

    @Override
    public void process(TruckQueueManager manager) throws TruckQueueManagerStateException {
        List<Truck> trucks = manager.getTrucks();
        Warehouse warehouse = manager.getWarehouse();

        if (!checkWarehouseResources(trucks, warehouse)) {
            LOGGER.error("Not enough cargo at warehouse.");
            throw new TruckQueueManagerStateException("Not enough cargo at warehouse.");
        }

        Comparator<Truck> defComparator = manager.getDefComparator();
        Comparator<Truck> forLoading = new ForLoadingTruckComparator().thenComparing(defComparator);

        try {
            manager.prepareQueue(forLoading);
            manager.processPreparedQueue();
        } catch (TruckQueueManagerException e) {
            LOGGER.error(e);
            throw new TruckQueueManagerStateException(e);
        }

    }

    /**
     * Checking if {@code warehouse} has enough cargo to load {@code truck} with the lowest capacity.
     *
     * @param trucks list of items to get the one with the lowest capacity and then test for ability to
     *               perform with {@param warehouse}
     * @param warehouse object to test for ability to perform with {@code truck} with the lowest capacity.
     * @return {@code true} if there is able to perform action between {@param warehouse} and {@code truck}
     * with the lowest capacity and {@code false} if not.
     */
    private boolean checkWarehouseResources(List<Truck> trucks, Warehouse warehouse) {
        int warehouseCargo = warehouse.getCargoWeight();
        int terminals = warehouse.getTerminalsCount();

        Truck minCapacityTruck = trucks.get(0);
        for (int i = 1; i < terminals && i < trucks.size(); i++) {
            Truck current = trucks.get(i);
            if (current.getCapacity() < minCapacityTruck.getCapacity()) {
                minCapacityTruck = current;
            }
        }

        return warehouseCargo >= minCapacityTruck.getCapacity();
    }

}
