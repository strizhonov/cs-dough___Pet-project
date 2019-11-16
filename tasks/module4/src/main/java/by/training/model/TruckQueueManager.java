package by.training.model;

import by.training.entity.PlainTruck;
import org.apache.log4j.Logger;
import by.training.service.TruckService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class TruckQueueManager {

    private static final Logger LOGGER = Logger.getLogger(TruckQueueManager.class);
    private static final int THREAD_POOL_DEF_SIZE = 10;
    private static final TimeUnit bufferWaitingTimeUnit = TimeUnit.MILLISECONDS;
    private static final int bufferWaitingTimeValue = 2;
    private TruckService service;
    private List<Truck> trucks;
    private Warehouse warehouse;
    private Comparator<Truck> defComparator;

    public TruckQueueManager(TruckService service, List<Truck> trucks,
                             Warehouse warehouse, Comparator<Truck> defComparator) {
        this.service = service;
        this.trucks = trucks;
        this.warehouse = warehouse;
        this.defComparator = defComparator;
        trucks.sort(defComparator);
    }

    /**
     * Removes truck by truck from list after it communicated with warehouse.
     *
     * @throws TruckQueueManagerException if the queue manager is not able to process current state.
     */
    public void processQueue() throws TruckQueueManagerException {
        try {
            while (trucks.size() > 0) {
                TruckQueueManagerState state = getQueueState();
                state.performShipment(this);
            }
        } catch (TruckQueueManagerStateException e) {
            LOGGER.error(e);
            throw new TruckQueueManagerException(e);
        }
    }

    /**
     * Processing one item from the trucks list with the max value (according to the {@param comparator}).
     * If processing was successful item is being removed from the list.
     *
     * @param comparator is used to get first component from the list with the max value.
     */
    void processMax(Comparator<Truck> comparator) throws TruckQueueManagerException {
        trucks.sort(comparator);
        Truck forLoadingTruck = trucks.get(0);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_DEF_SIZE);
        FutureTask<Truck> task = new FutureTask<>(forLoadingTruck);

        executorService.submit(task);
        executorService.shutdown();

        try {
            while (!executorService.isTerminated()) {
                executorService.awaitTermination(bufferWaitingTimeValue, bufferWaitingTimeUnit);
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
            throw new TruckQueueManagerException(e);
        } finally {
            updateStorage();
            trucks.sort(defComparator);
        }
    }

    /**
     * Processing every item from the trucks list. If processing was successful
     * item is being removed from the list.
     *
     * @throws TruckQueueManagerException if the current thread is interrupted or
     * when attempting to retrieve the result of a task that aborted by throwing an exception.
     */
    void processAll() throws TruckQueueManagerException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_DEF_SIZE);
        List<FutureTask<Truck>> tasks = getSubmittedTasks(executorService);
        executorService.shutdown();

        updateTrucks(tasks);

        try {
            while (!executorService.isTerminated()) {
                executorService.awaitTermination(bufferWaitingTimeValue, bufferWaitingTimeUnit);
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
            throw new TruckQueueManagerException(e);
        } finally {
            updateStorage();
        }
    }

    /**
     * Retrieves queue state according to its containing. If queue (list) contains
     * different trucks of different state, {@code new MixedQueueState()} returns.
     *
     * If there is no trucks in the queue (list), or all trucks have {@code PROCEED} state type,
     * method returns {@code new UncertainQueueState()}.
     *
     * @return TruckQueueManagerState according to truck list containing
     */
    private TruckQueueManagerState getQueueState() {
        if (trucks.size() == 0) {
            return new UncertainQueueState();
        }

        TruckStateType previousType = trucks.get(0).getState().getType();

        int terminals = warehouse.getTerminalsCount();
        for (int i = 1; i < terminals && i < trucks.size(); i++) {
            TruckStateType truckStatusType = trucks.get(i).getState().getType();
            if (truckStatusType != previousType) {
                return new MixedQueueState();
            }
        }

        if (previousType == TruckStateType.FOR_LOAD) {
            return new OnlyLoadingQueueState();
        } else if (previousType == TruckStateType.FOR_UNLOAD) {
            return new OnlyUnloadingQueueState();
        } else {
            return new UncertainQueueState();
        }
    }

    /**
     * If truck was processed, it is being removed from list
     * and updated in truck repository.
     */
    private void updateStorage() {
        Iterator<Truck> iter = trucks.iterator();
        while (iter.hasNext()) {
            Truck current = iter.next();
            if (current.getState().getType() == TruckStateType.PROCESSED) {
                PlainTruck plainTruck = service.getPlainTruck(current);
                service.update(plainTruck);
                iter.remove();
            }
        }
    }

    /**
     * Transfer {@code List<Truck> trucks} to {@code List<FutureTask<Truck>> tasks}
     * and submit every task.
     *
     * @param executorService submitting tasks
     * @return {@code List<FutureTask<Truck>>} list of submitted tasks from truck list.
     * @throws TruckQueueManagerException if the current thread is interrupted.
     */
    private List<FutureTask<Truck>> getSubmittedTasks(ExecutorService executorService)
            throws TruckQueueManagerException {
        List<FutureTask<Truck>> tasks = new ArrayList<>();
        for (Truck truck : trucks) {
            FutureTask<Truck> task = new FutureTask<>(truck);
            tasks.add(task);
            executorService.submit(task);
            try {
                bufferWaitingTimeUnit.sleep(bufferWaitingTimeValue);
            } catch (InterruptedException e) {
                LOGGER.error(e);
                throw new TruckQueueManagerException(e);
            }
        }
        return tasks;
    }

    /**
     * Updates current truck list according to the result of task list {@code List<FutureTask<Truck>> tasks}
     * @param tasks list of callable with {@code Truck} return type.
     * @throws TruckQueueManagerException if the current thread is interrupted or
     * when attempting to retrieve the result of a task that aborted by throwing an exception.
     */
    private void updateTrucks(List<FutureTask<Truck>> tasks) throws TruckQueueManagerException {
        trucks = new ArrayList<>();

        for (FutureTask<Truck> task : tasks) {
            try {
                Truck truck = task.get();
                trucks.add(truck);
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error(e);
                throw new TruckQueueManagerException(e);
            }
        }
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Comparator<Truck> getDefComparator() {
        return defComparator;
    }
}
