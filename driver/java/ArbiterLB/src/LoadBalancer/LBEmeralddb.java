package LoadBalancer;

import Message.Message;
import Protocol.DBService;
import Util.Operations;
import com.emeralddb.base.Emeralddb;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by amaliujia on 15-4-14.
 */
public class LBEmeralddb extends Emeralddb implements Runnable{

    private Queue<Message> jobQueue;

    private final Lock lock = new ReentrantLock();

//    private final Condition notFull = lock.newCondition();
//    private final Condition notEmpty = lock.newCondition();

    public LBEmeralddb(){
        super();
        //jobQueue = new LinkedList<Message>();
        //jobQueue = new LinkedBlockingQueue<Message>();
    }

    public void setQueue(Queue<Message> q){
        jobQueue = q;
    }

//    public void put(Message e){
//        System.out.println("I can really put!!!!");
////        lock.lock();
////        //jobQueue.add(e);
////        jobQueue.offer(e);
////        lock.unlock();
////        notEmpty.signal();
//        jobQueue.offer(e);
//    }

    @Override
    public void run() {

        Message m = null;

        while (true) {
//            try {
//                System.out.println("Iteration");
//                lock.lock();
//                while (jobQueue.size() == 0) {
//                    notEmpty.await();
//                }
//               // m = jobQueue.remove();
//                m = jobQueue.poll();
//            }catch(InterruptedException e){
//                e.printStackTrace();
//            }finally{
//                lock.unlock();
//            }
            m = jobQueue.poll();
            if(m == null){
                continue;
            }
            handleMessgae(m);
        }

    }

    private void handleMessgae(Message e){
        Operations o = e.getOperation();

        if(o.equals(Operations.INSERT)){
            insert(e.getKey(), e.getRecord());
        }else if(o.equals(Operations.DELETE)){
           delete(e.getKey());
        }else if(o.equals(Operations.QUERY)){
            query(e.getKey());
        }else{
            return;
        }
    }
}
