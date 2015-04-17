package LoadBalancer;

import Message.Delete;
import Message.Insert;
import Message.Query;
import Util.Operations;
import com.google.gson.internal.StringMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by amaliujia on 15-4-16.
 */
public class EqualSharingLB extends LoadBalancer {
    private static final int numEDBperDB = 1;

    private long insert;
    private long query;
    private long delete;

//    private long[] count;

    private ArrayList<HashSet<String>> keys;
    //private long[] count;
    private ArrayList<Long> count;
    public void init(){

        logger.setLogFile("Equal_sharing_stat.txt");
        insert = 0;
        query = 0;
        delete = 0;
        keys = new ArrayList<HashSet<String>>();

        for(int i = 0; i < instances.size(); i++) {
            BufferedWriter w = null;

            try {
                w = new BufferedWriter(new FileWriter(new File(tempFile)));
                DBInstance dbInstance = instances.get(i);
                w.write(dbInstance.getIp() + ":" + dbInstance.getPort() + "\n");
                w.flush();
                w.close();
                LBEmeralddb edb = new LBEmeralddb();
                edb.startStat();
                edb.init(tempFile);
                dbs.add(edb);
                //new Thread(edb).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < dbs.size(); i++){
            keys.add(new HashSet<String>());
        }
       // count = new long[keys.size()];
        count = new ArrayList<Long>();
        for(int i = 0; i < dbs.size(); i++){
            //count[i] = 0;
            count.add((long) 0);
        }

        logger.start();
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            logger.stat();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void assignInsert(String Key, String record){
        int minIndex = count.indexOf(Collections.min(count));
        HashSet<String> aKey = keys.get(minIndex);
        if(aKey.contains(record)){
            return;
        }
        aKey.add(record);
        //dbs.get(minIndex).insert(Key, record);
        dbs.get(minIndex).put(new Insert(Operations.INSERT, Key, record));
    }

    private void assignDelete(String Key){
       for(int i = 0; i < keys.size(); i++){
          if(keys.get(i).contains(Key)){
              //dbs.get(i).delete(Key);
              dbs.get(i).put(new Delete(Operations.DELETE, Key));
          }
        }
    }

    private void assignQuery(String Key){
        for(int i = 0; i < keys.size(); i++){
            if(keys.get(i).contains(Key)) {
                //dbs.get(i).query(Key);
                dbs.get(i).put(new Query(Operations.QUERY, Key));
            }
        }
    }


    @Override
    public void sumbit(Operations e, String Key, String record) {
        if(e.equals(Operations.INSERT)){
            //dbs.get(0).insert(Key, record);
            assignInsert(Key, record);
            insert++;
            logger.record(insert, query, delete);
        }else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void sumbit(Operations e, String Key) {
        if(e.equals(Operations.DELETE)){
            //dbs.get(0).delete(Key);
            assignDelete(Key);
            delete++;
        }else if(e.equals(Operations.QUERY)){
            //dbs.get(0).query(Key);
            assignQuery(Key);
            query++;
        }else{
            throw new IllegalArgumentException();
        }
    }
}
