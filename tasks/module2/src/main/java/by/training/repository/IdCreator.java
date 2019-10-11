package by.training.repository;

import java.util.concurrent.atomic.AtomicInteger;

public class IdCreator {

    private static final int INIT_VALUE = 0;
    private static IdCreator INSTANCE = new IdCreator();

    private AtomicInteger seq;

    private IdCreator() {
        seq = new AtomicInteger(INIT_VALUE);
    }

    public int getUniqueId() {
        return seq.incrementAndGet();
    }

    public static IdCreator getInstance() {
        return INSTANCE;
    }

}
