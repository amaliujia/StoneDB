package LoadBalancer.NaiveSharingApproach;

import LoadBalancer.Base.DBInstance;
import LoadBalancer.Base.LBEmeralddb;
import LoadBalancer.Base.LoadBalancer;
import Util.Operations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by amaliujia on 15-4-13.
 */
public class NaiveShardingLB extends LoadBalancer {

    private static final int numEDBperDB = 1;

    private long insert;
    private long query;
    private long delete;

    public void init(){
        logger.setLogFile("Constant_stat.txt");
        insert = 0;
        query = 0;
        delete = 0;

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.start();
    }

    @Override
    public void destroy() {
        //super.destroy();
        try {
            logger.end();
            logger.stat();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sumbit(Operations e, String Key, String record) {
        if(e.equals(Operations.INSERT)){
            dbs.get(0).insert(Key, record);
            insert++;
            //logger.record(insert, query, delete);
        }else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void sumbit(Operations e, String Key) {
       if(e.equals(Operations.DELETE)){
           dbs.get(0).delete(Key);
           delete++;
           //logger.record(insert, query, delete);
       }else if(e.equals(Operations.QUERY)){
           dbs.get(0).query(Key);
           query++;
           //logger.record(insert, query, delete);
       }else{
           throw new IllegalArgumentException();
       }
    }
}
