package model;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Warehouse {

    private static final TimeUnit lockWaitingTimeUnit = TimeUnit.SECONDS;
    private static final int lockWaitingTimeValue = 1;
    private Lock lock;
    private Semaphore semaphore;
    private int capacity;
    private int cargoWeight;
    private int terminalsCount;

    private Warehouse() {

    }

    private static class InstanceHolder {
        private static final Warehouse INSTANCE = new Warehouse();
    }

    public static Warehouse getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void init(int capacity, int cargoWeight, int terminalsCount) {
        this.capacity = capacity;
        this.cargoWeight = cargoWeight;
        this.terminalsCount = terminalsCount;
        this.semaphore = new Semaphore(terminalsCount, true);
        this.lock = new ReentrantLock();
    }

    /**
     * Method increases {@code this.cargoWeight} value if possible.
     *
     * @param cargoWeight value that {@code this.cargoWeight} should be increased.
     * @return success of modification ({@code true} or {@code false}).
     * @throws InterruptedException if the current thread is interrupted.
     */
    boolean acceptCargo(int cargoWeight) throws InterruptedException {
        boolean isLockAcquired = lock.tryLock(lockWaitingTimeValue, lockWaitingTimeUnit);

        if (!isLockAcquired) {
            return false;
        }

        try {

            if (this.capacity - this.cargoWeight < cargoWeight) {
                return false;
            }

            this.cargoWeight += cargoWeight;
            return true;

        } finally {
            lock.unlock();
        }

    }

    /**
     * Method decreases {@code this.cargoWeight} value if possible.
     *
     * @param cargoWeight value that {@code this.cargoWeight} should be decreased.
     * @return success of modification ({@code true} or {@code false}).
     * @throws InterruptedException if the current thread is interrupted.
     */
    boolean giveCargo(int cargoWeight) throws InterruptedException {
        boolean isLockAcquired = lock.tryLock(lockWaitingTimeValue, lockWaitingTimeUnit);

        if (!isLockAcquired) {
            return false;
        }

        try {

            if (this.cargoWeight < cargoWeight) {
                return false;
            }

            this.cargoWeight -= cargoWeight;
            return true;

        } finally {
            lock.unlock();
        }
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
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

    public int getTerminalsCount() {
        return terminalsCount;
    }

    public void setTerminalsCount(int terminalsCount) {
        this.terminalsCount = terminalsCount;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "lock=" + lock +
                ", semaphore=" + semaphore +
                ", capacity=" + capacity +
                ", cargoWeight=" + cargoWeight +
                ", terminalsCount=" + terminalsCount +
                '}';
    }

}
