package Protocol;

import java.rmi.Remote;

/**
 * Created by amaliujia on 15-4-15.
 */
public interface DBProtocol extends Remote {

    void sumbitInsert(String Key, String Record);

    void sumbitQuery(String Key);

    void sumbitDelete(String Key);
}
