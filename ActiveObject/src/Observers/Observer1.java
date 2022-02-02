package Observers;


import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;

public class Observer1  {

    HashMap<Integer,Integer> producers = new HashMap<>();
    HashMap<Integer,Integer> consumers = new HashMap<>();

    ArrayList<Integer> consumedList = new ArrayList<Integer>();
    ArrayList<Integer> producedList = new ArrayList<Integer>();
    private long extraWork = 0;

    public Observer1(int maxBuffer){
        for(int i=0;i<maxBuffer/2 + 1;i+=1){
            consumedList.add(0);
            producedList.add(0);
        }
    }

    public void check(){
        producers.forEach((k,v) -> System.out.println(k + ' ' + v));
        consumers.forEach((k,v) -> System.out.println(k + ' ' + v));
    }

    public void addProducer(int key){
        producers.put(key,0);
    }
    public void addConsumer(int key){
        consumers.put(key,0);
    }

    public synchronized  void produced(int id,int val,int extraWork){
        int a = producedList.get(val);
        producedList.set(val,a+1);
        //System.out.println(id);
        int b = producers.get(id) +1;
        producers.remove(id);
        producers.put(id,b+1);
        this.extraWork += extraWork;
    }
    public synchronized void consumed(int id,int val,int extraWork){
        int a = consumedList.get(val);
        consumedList.set(val,a+1);
        //System.out.println(id);
        int b = consumers.get(id) + 1;
        consumers.remove(id);
        consumers.put(id,b);
        this.extraWork += extraWork;
    }

    public void getCustomerStatistics(){
        /*
        System.out.println("CUSTOMERS STATISTICS");
        System.out.println("-------------------------------------");
        System.out.println("VALUE DONE");
        for(int i=1;i<consumedList.size();i+=1){
            System.out.println(consumedList.get(i));
        }
         */
        int val = 0;
        for(int i=1;i<consumedList.size();i+=1) {
            //System.out.println(consumedList.get(i));
            val = val + consumedList.get(i);
        }
        System.out.println(val);
        System.out.println("-------------------------------------");


        /*
        System.out.println("-------------------------------------");
        System.out.println("ID DONE");
        consumers.forEach((k,v) -> System.out.println(k + ' ' + v));
        */

    }
    public void getProducerStatistics(){
        /*
        System.out.println("PRODUCERS STATISTICS");
        System.out.println("-------------------------------------");
        System.out.println("VALUE DONE");
        */
        int val = 0;
        for(int i=1;i<producedList.size();i+=1){
            //System.out.println(producedList.get(i));
            val = val + producedList.get(i);
        }
        System.out.println(val);
        System.out.println("-------------------------------------");
        System.out.println(extraWork);
        /*
        System.out.println("-------------------------------------");
        System.out.println("ID DONE");
        producers.forEach((k,v) -> System.out.println(k + ' ' + v));
        */
    }
    public void getOneProducer(int key){
        System.out.println(key + ' ' + producers.get(key));
    }

    public void getOneConsumer(int key){
        System.out.println(key + ' ' + consumers.get(key));
    }

}
