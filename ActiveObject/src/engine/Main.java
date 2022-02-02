package engine;

import utils.Monitor;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        int maxBuffer = 1000;
        int producerNumber = 2500;
        int consumerNumber = 2500;
        int seconds = 60;
        Simulation.start(producerNumber,consumerNumber,seconds,maxBuffer);
    }
}
