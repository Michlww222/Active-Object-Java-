package agents;

import Observers.Observer1;
import utils.Monitor;
import java.util.Random;

public abstract class Agent implements Runnable {
    protected Monitor monitor;
    private static int NextId = 0;

    protected final int id;
    protected final Random generator;
    protected Observer1 observer;

    public Agent(Monitor monitor,Observer1 observer) {
        this.monitor = monitor;
        this.id = setId();
        generator = new Random(id);
        this.observer = observer;
    }

    private static int setId() {
        NextId = NextId + 1;
        return NextId - 1;
    }


    public abstract int getId();
}
