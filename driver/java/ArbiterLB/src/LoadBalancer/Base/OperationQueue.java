package LoadBalancer.Base;

import Message.Message;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by amaliujia on 15-4-17.
 */
public class OperationQueue {
    private static AtomicLong maxId = new AtomicLong(-1);

    private LinkedList<Message> jobQuueue;

    private long id;

    public long getId(){
        return this.id;
    }

    public OperationQueue(){
        jobQuueue = new LinkedList<Message>();
        id = maxId.incrementAndGet();
    }

    public synchronized void put(Message e) throws InterruptedException{
        jobQuueue.offer(e);
        notifyAll();
    }

    public synchronized Message get() throws InterruptedException{
        if(jobQuueue.size() <= 0){
            wait();
        }

        Message e = jobQuueue.poll();
        return e;
    }

    public synchronized boolean check(){
        if(jobQuueue.size() == 0){
            return true;
        }
        return false;
    }

    public String toString(){
       return "OperationQueue: " + this.id;
    }
}
