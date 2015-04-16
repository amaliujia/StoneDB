package Message;

import Util.Operations;
import com.emeralddb.base.EmeralddbConstants;
import sun.jvm.hotspot.gc_implementation.parallelScavenge.PSYoungGen;

import java.security.PublicKey;

/**
 * Created by amaliujia on 15-4-15.
 */
public abstract class Message {
    protected Operations o;

    protected String Key;

    protected String Record;

    public String getKey(){
        return Key;
    }

    public String getRecord(){
        return Record;
    }

    public void setOperation(Operations o){
        this.o = o;
    }

    public Operations getOperation(){
        return o;
    }

    public void setKey(String key){
        this.Key = key;
    }

    public void setRecord(String record){
        this.Record = record;
    }
}