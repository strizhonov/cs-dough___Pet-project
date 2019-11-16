package by.training.model;

import org.apache.log4j.Logger;

public class MixedQueueState implements TruckQueueManagerState {

    private static final Logger LOGGER = Logger.getLogger(MixedQueueState.class);

    @Override
    public void performShipment(TruckQueueManager manager) throws TruckQueueManagerStateException {
        try {
            manager.processAll();
        } catch (TruckQueueManagerException e) {
            LOGGER.error(e);
            throw new TruckQueueManagerStateException(e);
        }
    }
}
