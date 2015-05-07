package LoadBalancer.EqualSharingApproach;

import LoadBalancer.Base.DBInstance;
import LoadBalancer.Base.LBEmeralddb;
import LoadBalancer.Base.LoadBalancer;
import LoadBalancer.Base.OperationQueue;
import Message.Delete;
import Message.Insert;
import Message.Query;
import Util.Operations;
import Util.TimeLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by amaliujia on 15-4-16.
 */
public class EqualSharingLB extends LoadBalancer {
    private static final int numEDBperDB = 1;

    private final static String String4k = "abcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnop";

    private long insert;
    private long query;
    private long delete;

//    private long[] count;

    private ArrayList<HashSet<String>> keys;
    //private ArrayList<Queue<Message>> ques;
    private ArrayList<OperationQueue>  ques;
    //private long[] count;
    private ArrayList<Long> count;

    private ArrayList<Thread> threads;

    private TimeLogger timeLogger;

    public void init(){
        logger.setLogFile("Equal_sharing_stat.txt");
        insert = 0;
        query = 0;
        delete = 0;
        keys = new ArrayList<HashSet<String>>();
        //ques = new ArrayList<Queue<Message>>();
        ques = new ArrayList<OperationQueue>();
         threads = new ArrayList<Thread>();
        for(int i = 0; i < instances.size(); i++) {
            BufferedWriter w = null;

            try {
                w = new BufferedWriter(new FileWriter(new File(tempFile)));
                DBInstance dbInstance = instances.get(i);
                w.write(dbInstance.getIp() + ":" + dbInstance.getPort() + "\n");
                w.flush();
                w.close();
                OperationQueue que = new OperationQueue();
                LBEmeralddb emeralddb = new LBEmeralddb(tempFile, que, logger);

                ques.add(que);
                threads.add(emeralddb);
                emeralddb.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < ques.size(); i++){
            keys.add(new HashSet<String>());
        }

        count = new ArrayList<Long>();
        for(int i = 0; i < ques.size(); i++){
            //count[i] = 0;
            count.add((long) 0);
        }

        logger.start();
    }

    public void waitThreads(){
        for (int i = 0; i < ques.size(); i++){
            OperationQueue q = ques.get(i);
            while (true){
                if(q.check()){
                    //threads.get(i).stop();
                    break;
                }
            }
        }
    }

    public void stopThreads(){
        for (int i = 0; i < ques.size(); i++){
            OperationQueue q = ques.get(i);
            while (true){
                if(q.check()){
                    threads.get(i).stop();
                    break;
                }
            }
        }
    }

    @Override
    public void destroy() {
        waitThreads();
        //super.destroy();

        try {
            logger.stat();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < keys.size(); i++){
            HashSet<String> k = keys.get(i);
            for(String s : k){
                try {
                    ques.get((i)).put(new Delete(Operations.DELETE, s));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        stopThreads();
    }

    private void assignInsert(String Key, String record){
        int minIndex = count.indexOf(Collections.min(count));
        HashSet<String> aKey = keys.get(minIndex);
        if(aKey.contains(record)){
            return;
        }
        aKey.add(record);
        String completeRecord = String.format("{_id:'" + Key + "',data:'" + String4k +"'}");
        try {
            ques.get((minIndex)).put(new Insert(Operations.INSERT, Key, completeRecord));
            count.set(minIndex, count.get(minIndex) + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void assignDelete(String Key){
       for(int i = 0; i < keys.size(); i++){
          if(keys.get(i).contains(Key)){
              try {
                  ques.get((i)).put(new Delete(Operations.DELETE, Key));
                  count.set(i, count.get(i) - 1);
                  keys.remove(Key);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }

          }
        }
    }

    private void assignQuery(String Key){
        for(int i = 0; i < keys.size(); i++){
            if(keys.get(i).contains(Key)) {
                try {
                    ques.get((i)).put(new Query(Operations.QUERY, Key));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void sumbit(Operations e, String Key, String record) {
        if(e.equals(Operations.INSERT)){
            assignInsert(Key, record);
        }else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void sumbit(Operations e, String Key) {
        if(e.equals(Operations.DELETE)){
            assignDelete(Key);
        }else if(e.equals(Operations.QUERY)){
            assignQuery(Key);
        }else{
            throw new IllegalArgumentException();
        }
    }
}
