package LoadBalancer.ConsistencyHashingApproach;

import Hashing.BuiltinHashFunction;
import Hashing.ConsistencyHashing;
import Interface.HashFunction;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by amaliujia on 15-5-7.
 */
public class ConsistencyHashMultiQueueMultiEDBLB extends LoadBalancer {
    private final static String String4k = "abcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnopabcdefghijklmnop";

    private long insert;

    private long query;

    private long delete;

    private ArrayList<HashSet<String>> keys;

    private ArrayList<OperationQueue>  ques;

    private ConsistencyHashing<OperationQueue> consistencyHashingPool;

    private ArrayList<Thread> threads;

    private TimeLogger timeLogger;

    private final static int n = 3;

    @Override
    public void init() {
        logger.setLogFile("Consistency_hashing_stat.txt");
        insert = 0;
        query = 0;
        delete = 0;
        keys = new ArrayList<HashSet<String>>();
        ques = new ArrayList<OperationQueue>();
        threads = new ArrayList<Thread>();

        Collection<OperationQueue> collection = new ArrayList<OperationQueue>();

        for(int i = 0; i < instances.size(); i++) {
            BufferedWriter w = null;

            try {
                w = new BufferedWriter(new FileWriter(new File(tempFile)));
                DBInstance dbInstance = instances.get(i);
                w.write(dbInstance.getIp() + ":" + dbInstance.getPort() + "\n");
                w.flush();
                w.close();


                for(int j = 0; j < n; j++){
                    OperationQueue que = new OperationQueue();
                    LBEmeralddb emeralddb = new LBEmeralddb(tempFile, que, logger);
                    threads.add(emeralddb);
                    emeralddb.start();
                    collection.add(que);
                    ques.add(que);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < ques.size(); i++){
            keys.add(new HashSet<String>());
        }

        HashFunction hashFunction = new BuiltinHashFunction();
        consistencyHashingPool = new ConsistencyHashing<OperationQueue>(hashFunction, 1, collection);

        logger.start();
    }

    public void waitThreads(){
        for (int i = 0; i < ques.size(); i++){
            OperationQueue q = ques.get(i);
            while (true){
                if(q.check()){
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

    private void assignInsert(String Key, String record){
        String completeRecord = String.format("{_id:'" + Key + "',data:'" + String4k +"'}");
        OperationQueue que = consistencyHashingPool.get(Key);
        HashSet<String> aKey = keys.get((int) que.getId());
        aKey.add(record);
        try {
            que.put(new Insert(Operations.INSERT, Key, completeRecord));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void assignDelete(String Key){
        OperationQueue que = consistencyHashingPool.get(Key);
        HashSet<String> aKey = keys.get((int) que.getId());
        aKey.remove(Key);

        try {
            que.put(new Delete(Operations.DELETE, Key));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void assignQuery(String Key){
        OperationQueue que = consistencyHashingPool.get(Key);
        try {
            que.put(new Delete(Operations.QUERY, Key));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
