package by.training.model;

import org.apache.log4j.Logger;

public class UncertainQueueState implements TruckQueueManagerState {

    private static final Logger LOGGER = Logger.getLogger(UncertainQueueState.class);

    @Override
    public void performShipment(TruckQueueManager manager) {
        LOGGER.warn("Attempt to perform inside " + getClass().getName() + ".");
    }

}
