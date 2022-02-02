package agents;

import Observers.Observer1;
import utils.Future;
import utils.Monitor;
import utils.Proxy;

import static java.lang.Thread.sleep;

public abstract class User extends Agent {
    protected final Proxy proxy;

    private int extraWorkDone = 0;

    public User(Monitor monitor, Proxy proxy, Observer1 observer1) {
        super(monitor,observer1);
        this.proxy = proxy;
    }

    @Override
    public void run() {
        informObserver();
        while (true) {
            Future future = doNormalTask();
            while(!future.isAvailable()){
                try {
                    doExtraWork();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            comment(future,extraWorkDone);
            extraWorkDone = 0;
        }
    }

    public void doExtraWork() throws InterruptedException {
        sleep(0, 10);
        extraWorkDone = extraWorkDone+1;
    }

    public abstract void comment(Future future,int val);

    public abstract Future doNormalTask();

    @Override
    public int getId(){
        return this.id;
    }

    public abstract void informObserver();
}
