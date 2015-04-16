package Protocol;

import com.emeralddb.base.Emeralddb;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by amaliujia on 15-4-15.
 */
public class DBService extends UnicastRemoteObject implements DBProtocol{

    private Emeralddb db;

    protected DBService(Emeralddb e) throws RemoteException {
        db = e;
    }

    @Override
    public void sumbitInsert(String Key, String Record) {
        db.insert(Key, Record);
    }

    @Override
    public void sumbitQuery(String Key) {
        db.query(Key);
    }

    @Override
    public void sumbitDelete(String Key) {
        db.delete(Key);
    }
}
