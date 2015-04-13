/**
 * Created by amaliujia on 15-4-13.
 */
public class DBInstance {
    private String ip;
    private int port;

    public DBInstance(String i, int p){
        this.ip = i;
        this.port = p;
    }

    public String getIp(){
        return this.ip;
    }

    public int getPort(){
        return this.port;
    }
}
