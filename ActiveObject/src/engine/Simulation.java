package engine;

import Observers.Observer1;
import agents.Consumer;
import agents.Producer;
import agents.Scheduler;
import utils.Buffer;
import utils.Monitor;
import utils.Proxy;

import java.util.ArrayList;

public class Simulation {
    public static void start(int producerNumber, int consumerNumber, int seconds, int maxBuffer) throws InterruptedException {

        Buffer buffer = new Buffer(maxBuffer);
        Monitor monitor = new Monitor(buffer);
        Observer1 observer = new Observer1(maxBuffer);
        ArrayList<Thread> consumerList = new ArrayList<Thread>();
        ArrayList<Thread> producerList = new ArrayList<Thread>();
        Proxy proxy = new Proxy(monitor, maxBuffer);
        Thread scheduler = new Thread(new Scheduler(monitor,buffer));

        for (int i = 0; i < producerNumber; i += 1) {
            producerList.add(new Thread(new Producer(monitor,proxy,observer)));

        }
        for (int i = 0; i < consumerNumber; i += 1) {
            consumerList.add(new Thread(new Consumer(monitor,proxy,observer)));
        }

        scheduler.start();
        for(Thread a:producerList){
            a.start();
        }
        for(Thread a:consumerList){
            a.start();
        }

        Thread.sleep(seconds*1000);
        observer.getCustomerStatistics();
        observer.getProducerStatistics();



        System.out.println("-------------------------------------");
        System.out.println("SIMULATION ENDED");
    }
}
