package LoadBalancer;

import Message.Message;

import java.util.LinkedList;

/**
 * Created by amaliujia on 15-4-17.
 */
public class OperationQueue {
    private LinkedList<Message> jobQuueue;

    public OperationQueue(){
        jobQuueue = new LinkedList<Message>();
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

}
