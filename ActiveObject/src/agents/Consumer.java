package agents;

import Observers.Observer1;
import utils.Future;
import utils.Monitor;
import utils.Proxy;

import java.io.IOException;

public class Consumer extends User {

    public Consumer(Monitor monitor, Proxy proxy, Observer1 observer1) {
        super(monitor, proxy,observer1);
    }

    @Override
    public Future doNormalTask() {
        return proxy.consume(proxy.randNumber());
    }

    @Override
    public void comment(Future future,int val){
        //System.out.println("consume done");
        this.observer.consumed(getId(), future.getValue(),val);
    }

    @Override
    public void informObserver(){
        observer.addConsumer(getId());
    }

}