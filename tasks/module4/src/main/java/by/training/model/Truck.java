package by.training.model;

import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Truck implements Callable<Truck> {

    private static final Logger LOGGER = Logger.getLogger(Truck.class);
    private static final TimeUnit accessWaitingTimeUnit = TimeUnit.SECONDS;
    private static final int accessWaitingTimeValue = 1;
    private long id;
    private int capacity;
    private int cargoWeight;
    private boolean perishable;
    private Warehouse warehouse;
    private TruckState state;

    public Truck(long id, int capacity, int cargoWeight, boolean perishable, Warehouse warehouse) {
        this.id = id;
        this.capacity = capacity;
        this.cargoWeight = cargoWeight;
        this.perishable = perishable;
        this.warehouse = warehouse;
        if (cargoWeight == 0) {
            this.state = new ForLoadTruckState();
        } else {
            this.state = new ForUnloadTruckState();
        }
    }

    @Override
    public Truck call() throws InterruptedException {
        if (warehouse == null) {
            LOGGER.error("Can not perform call() method without warehouse.");
            throw new InterruptedException("Can not perform call() method without warehouse.");
        }

        Semaphore semaphore = warehouse.getSemaphore();
        try {
            semaphore.acquire();
            state.perform(this);
            return this;
        } finally {
            semaphore.release();
        }
    }

    /**
     * Communicating with {@code warehouse} trying to modify its data.
     *
     * <p> If communicating succeeded, @return {@code true};
     * <p> If communicating can not be performed after {@code warehouse.getTerminalsCount()}
     * attempts, @return {@code false}.
     *
     * @throws InterruptedException if the current thread is interrupted.
     */
    boolean tryToLoad() throws InterruptedException {
        for (int loadingAttempts = 0; loadingAttempts < warehouse.getTerminalsCount(); loadingAttempts++) {
            if (warehouse.giveCargo(capacity)) {
                cargoWeight = capacity;
                return true;
            }
            accessWaitingTimeUnit.sleep(accessWaitingTimeValue);
        }

        return false;
    }

    /**
     * Communicating with {@code warehouse} trying to modify its data.
     *
     * @return {@code true} if communicating succeeded or {@code false} if communicating can not
     * be performed after {@code warehouse.getTerminalsCount()} attempts.
     * @throws InterruptedException if the current thread is interrupted.
     */
    boolean tryToUnload() throws InterruptedException {
        for (int loadingAttempts = 0; loadingAttempts < warehouse.getTerminalsCount(); loadingAttempts++) {
            if (warehouse.acceptCargo(capacity)) {
                cargoWeight = 0;
                return true;
            }
            accessWaitingTimeUnit.sleep(accessWaitingTimeValue);
        }

        return false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(int cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public boolean isPerishable() {
        return perishable;
    }

    public void setPerishable(boolean perishable) {
        this.perishable = perishable;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public TruckState getState() {
        return state;
    }

    public void setState(TruckState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return id == truck.id &&
                capacity == truck.capacity &&
                cargoWeight == truck.cargoWeight &&
                perishable == truck.perishable &&
                Objects.equals(warehouse, truck.warehouse) &&
                Objects.equals(state, truck.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity, cargoWeight, perishable, warehouse, state);
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", cargoWeight=" + cargoWeight +
                ", perishable=" + perishable +
                ", warehouse=" + warehouse +
                ", state=" + state +
                '}';
    }

}
