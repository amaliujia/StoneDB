import com.emeralddb.base.Emeralddb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by amaliujia on 15-4-13.
 */
public class NaiveShardingLB extends LoadBalancer {

    private static final int numEDBperDB = 1;

    public void init(){
        for(int i = 0; i < instances.size(); i++) {
            BufferedWriter w = null;

            try {
                w = new BufferedWriter(new FileWriter(new File(tempFile)));
                DBInstance dbInstance = instances.get(i);
                w.write(dbInstance.getIp() + ":" + dbInstance.getPort() + "\n");
                w.flush();
                w.close();
                Emeralddb edb = new Emeralddb();
                edb.startStat();
                edb.init(tempFile);
                dbs.add(edb);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
