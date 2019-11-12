package model;

import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class OnlyUnloadingQueueState implements TruckQueueManagerState {

    private static final Logger LOGGER = Logger.getLogger(OnlyUnloadingQueueState.class);

    @Override
    public void process(TruckQueueManager manager) throws TruckQueueManagerStateException {
        List<Truck> trucks = manager.getTrucks();
        Warehouse warehouse = manager.getWarehouse();

        if (!checkWarehouseResources(trucks, warehouse)) {
            LOGGER.error("Not enough free space at warehouse.");
            throw new TruckQueueManagerStateException("Not enough free space at warehouse.");
        }

        Comparator<Truck> defComparator = manager.getDefComparator();
        Comparator<Truck> forUnloading = new ForUnloadingTruckComparator().thenComparing(defComparator);

        try {
            manager.prepareQueue(forUnloading);
            manager.processPreparedQueue();
        } catch (TruckQueueManagerException e) {
            LOGGER.error(e);
            throw new TruckQueueManagerStateException(e);
        }

    }

    /**
     * Checking if {@code warehouse} has enough free space to unload {@code truck} with the smallest cargo.
     *
     * @param trucks    list of items to get the one with the smallest cargo and then test for ability to
     *                  perform with {@param warehouse}
     * @param warehouse object to test for ability to perform with {@code truck} with the smallest cargo.
     * @return {@code true} if there is able to perform action between {@param warehouse} and {@code truck}
     * with the smallest cargo and {@code false} if not.
     */
    private boolean checkWarehouseResources(List<Truck> trucks, Warehouse warehouse) {
        int warehouseFreeSpace = warehouse.getCapacity() - warehouse.getCargoWeight();
        int terminals = warehouse.getTerminalsCount();

        Truck minCargoTruck = trucks.get(0);
        for (int i = 1; i < terminals && i < trucks.size(); i++) {
            Truck current = trucks.get(i);
            if (current.getCargoWeight() < minCargoTruck.getCargoWeight()) {
                minCargoTruck = current;
            }
        }

        return warehouseFreeSpace >= minCargoTruck.getCargoWeight();
    }

}
