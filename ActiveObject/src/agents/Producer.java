package agents;

import Observers.Observer1;
import utils.Future;
import utils.Monitor;
import utils.Proxy;


public class Producer extends User {
    public Producer(Monitor monitor, Proxy proxy, Observer1 observer1) {
        super(monitor, proxy,observer1);
    }

    @Override
    public Future doNormalTask() {
        return proxy.produce(proxy.randNumber());
    }

    @Override
    public void comment(Future future,int val){
        //System.out.println("produce done " + getId());
        this.observer.produced(getId(), future.getValue(),val);
    }

    @Override
    public void informObserver(){
        observer.addProducer(getId());
    }
}