package utils;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private final LinkedList<Future> consumers = new LinkedList<>();
    private final LinkedList<Future> producers = new LinkedList<>();
    private Buffer buffer;
    private boolean wasLastProduce = true;

    ReentrantLock lock = new ReentrantLock();

    Condition lockCondition = lock.newCondition();


    public Monitor(Buffer buffer){
        this.buffer = buffer;
    }
    public void addConsumer(Future future) {
        lock.lock();
        consumers.addLast(future);
        lockCondition.signal();
        //System.out.println("C: added to queue");
        lock.unlock();
    }

    public void addProducer(Future future) {
        lock.lock();
        producers.addLast(future);
        lockCondition.signal();
        //System.out.println("C: added to queue");
        lock.unlock();
    }

    public void schedule() throws InterruptedException {
        lock.lock();
        Future firstProduce = getProduce();
        Future firstConsume = getConsume();
        while (!canDoProduce(firstProduce) && !canDoConsume(firstConsume)) {
            lockCondition.await();
            firstProduce = getProduce();
            firstConsume = getConsume();
        }
        doAction(firstProduce,firstConsume);
        lock.unlock();
    }

    private Future getConsume() {
        return consumers.isEmpty() ? null : consumers.getFirst();
    }

    public Future getProduce() {
        return producers.isEmpty() ? null : producers.getFirst();
    }

    public void takeOffConsume() {
        lock.lock();
        if (consumers.isEmpty()) {
            throw new IllegalStateException("No consumer in queue");
        }
        consumers.removeFirst();
        lock.unlock();
    }

    public void takeOffProduce() {
        lock.lock();
        if (producers.isEmpty()) {
            throw new IllegalStateException("No consumer in queue");
        }
        producers.removeFirst();
        lock.unlock();
    }


    private void doAction(Future firstProduce,Future firstConsume){
        if(wasLastProduce){
            if(canDoProduce(firstProduce)){
                doProduce(firstProduce);
            }
            else{
                doConsume(firstConsume);
            }
        }
        else{
            if(canDoConsume(firstConsume)){
                doConsume(firstConsume);
            }
            else{
                doProduce(firstProduce);
            }
        }
    }

    private boolean canDoProduce(Future client){
        if(client == null || client.getValue() + buffer.getBuffer() > buffer.getMaxBuffer()){
            return false;
        }
        return true;
    };

    private boolean canDoConsume(Future client){
        if(client == null || buffer.getBuffer() - client.getValue()  < 0 ){
            return false;
        }
        return true;
    };

    private void doProduce(Future client){
        takeOffProduce();
        wasLastProduce = true;
        buffer.increment(client.getValue());
        client.finishProcessing();
    };

    private void doConsume(Future client){
        takeOffConsume();
        wasLastProduce = false;
        buffer.decrement(client.getValue());
        client.finishProcessing();
    };
}
