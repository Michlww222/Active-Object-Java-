package agents;

import utils.Monitor;
import utils.Buffer;

public class Scheduler implements Runnable {
    private final Buffer buffer;
    private final Monitor monitor;


    public Scheduler(Monitor monitor, Buffer buffer) {
        this.buffer = buffer;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                monitor.schedule();
                //System.out.println("S: operation done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
