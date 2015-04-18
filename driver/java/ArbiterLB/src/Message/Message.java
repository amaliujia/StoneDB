package Message;

import Util.Operations;

/**
 * Created by amaliujia on 15-4-15.
 */
public abstract class Message {
    protected Operations o;

    protected String Key;

    protected String Record;

    public Message(Operations e, String Key, String Record){
        o = e;
        this.Key = Key;
        this.Record = Record;
    }

    public Message(Operations e, String Key){
        o = e;
        this.Key = Key;
    }

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
