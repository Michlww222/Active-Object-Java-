package utils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Future {
    private boolean isFinish;
    private final int value;

    public Future(int value) {
        isFinish = false;
        this.value = value;
    }

    public void finishProcessing() {
        isFinish = true;
    }

    public boolean isAvailable() {
        return isFinish;
    }

    public int getValue() {
        return value;
    }
}
