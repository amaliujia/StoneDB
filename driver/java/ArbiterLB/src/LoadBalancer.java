import com.emeralddb.base.Emeralddb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by amaliujia on 15-4-12.
 */
public abstract class LoadBalancer {
    protected ArrayList<DBInstance> instances;

    protected ArrayList<Emeralddb> dbs;

    protected static final String tempFile = "c.txt";


    public LoadBalancer(){
        instances = new ArrayList<DBInstance>();
    }

    public void addInstance(DBInstance instance){
        instances.add(instance);
    }

    public void addInstance(String ip, int port){
        instances.add(new DBInstance(ip, port));
    }

    public abstract void init();

}
