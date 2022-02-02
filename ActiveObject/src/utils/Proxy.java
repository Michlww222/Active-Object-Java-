package utils;

public class Proxy {
    public final int maxBufferChange;
    public final int maxBuffer;
    private final Monitor monitor;

    public Proxy(Monitor monitor, int maxBuffer) {
        this.monitor = monitor;
        maxBufferChange = maxBuffer / 2;
        this.maxBuffer = maxBuffer;
    }

    public Future consume(int value) {
        Future future = new Future(value);
        monitor.addConsumer(future);
        return future;
    }

    public Future produce(int value) {
        Future future = new Future(value);
        monitor.addProducer(future);
        return future;
    }

    public int randNumber(){
        return (int) ((Math.random() * (maxBuffer/2)) + 1);

    }

}
