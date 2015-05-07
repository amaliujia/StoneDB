package LoadBalancer.Base;

import Message.Message;
import Util.Operations;
import Util.TimeLogger;
import com.emeralddb.base.EDBMessage;
import com.emeralddb.base.EmeralddbCommon;
import com.emeralddb.base.EmeralddbConstants;
import com.emeralddb.exception.BaseException;
import com.emeralddb.net.IConnection;
import com.emeralddb.net.ServerAddress;
import com.emeralddb.util.EDBMessageHelper;
import org.bson.BSONObject;
import org.bson.util.JSON;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by amaliujia on 15-4-14.
 */
public class LBEmeralddb extends Thread {

    private EmeralddbCommon _edbCommon;
    private Map<ServerAddress, Integer> _nodeRecordMap = null;
    private long  insertTotalTime = 0;

    private OperationQueue jobQueue;

    private TimeLogger timeLogger;

    public LBEmeralddb(String tempFile, OperationQueue que, TimeLogger logger) throws BaseException {
        _edbCommon = new EmeralddbCommon();

        startStat();
        init(tempFile);
        setQueue(que);
        timeLogger = logger;
    }

    public LBEmeralddb() throws BaseException {
        _edbCommon = new EmeralddbCommon();;
    }
    public void endStat(int total) {
        for(Map.Entry<ServerAddress, Integer> entry : _nodeRecordMap.entrySet()) {
            System.out.println("Node name:" + entry.getKey().getPort()
                    + "-Times:" + entry.getValue()
                    + " - Percent: " + (float)entry.getValue()/total*100 + "%");
        }
/*
      long totalTime = 1000*1000*1000;
      System.out.println(" sendtime is "
            + (double)insertTotalTime/totalTime );
      _nodeRecordMap.clear();
*/
    }

    public void startStat() {
        insertTotalTime = 0;
        _nodeRecordMap = new HashMap<ServerAddress,Integer>();
    }

    public int init(String configFilePath) {
        return _edbCommon.init( configFilePath);
    }

    /**
     * @fn void disconnect()
     * @brief Disconnect the remote server
     * @exception com.emeralddb.exception.BaseException
     * @author zhouzhengle
     */
    public void disconnect() throws BaseException {
        EDBMessage edbMessage = new EDBMessage();
        byte[] request = EDBMessageHelper.buildDisconnectRequest(edbMessage);
        Map<ServerAddress, IConnection> connectionMap = _edbCommon.getConnectionMap();
        Iterator<Map.Entry<ServerAddress, IConnection>> it = connectionMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<ServerAddress, IConnection> entry = it.next();
            IConnection connection = entry.getValue();
            ServerAddress servAddress = entry.getKey();
            connection.sendMessage(request);
            connection.close();
        }
    }

    /**
     * @funtion query data
     * @param key
     * @return
     * @throws BaseException
     */
    public EDBMessage query(String key)
            throws BaseException {
        IConnection connection = getConnection(key);

        if( null == connection ) {
            System.out.println("connection is failed");
            return null;
        }

        EDBMessage edbMessage = new EDBMessage();
        edbMessage.setOperationCode(EmeralddbConstants.Operation.OP_QUERY);

        BSONObject bson = (BSONObject) JSON.parse(key);
        edbMessage.setQuery(bson);
        byte[] request = EDBMessageHelper.buildQueryRequest(edbMessage);
        // send message
        connection.sendMessage(request);

        byte[] recvBuffer = connection.receiveMessage();
        EDBMessage rtnSDBMessage = EDBMessageHelper.msgExtractReply(recvBuffer);


        return rtnSDBMessage;
    }

    /***
     *
     * @param record
     * @return
     * @throws BaseException
     * @throws
     * @author zhouzhengle
     */
    public EDBMessage insert(String key,String record)
            throws BaseException {

        IConnection connection = getConnection(key);
        if( null == connection ) {
            System.out.println("connection is failed");
            return null;
        }
        //long start = System.currentTimeMillis();

        EDBMessage edbMessage = new EDBMessage();
        edbMessage.setOperationCode(EmeralddbConstants.Operation.OP_INSERT);
        edbMessage.setMessageText(record);

        BSONObject bson = (BSONObject)JSON.parse(record);
        edbMessage.setInsertor(bson);

        byte[] request = EDBMessageHelper.buildInsertRequest(edbMessage);
        connection.sendMessage(request);
        //insertTotalTime += connection.sendMessage(request);

        byte[] recvBuffer = connection.receiveMessage();

        EDBMessage rtnSDBMessage = EDBMessageHelper.msgExtractReply(recvBuffer);

        return rtnSDBMessage;
    }
    /**
     *
     * @param key
     * @return
     * @throws BaseException
     */
    public EDBMessage delete(String key)
            throws BaseException {
        IConnection connection = getConnection(key);

        if( null == connection ) {
            System.out.println("connection is failed");
            return null;
        }

        EDBMessage edbMessage = new EDBMessage();
        edbMessage.setOperationCode(EmeralddbConstants.Operation.OP_DELETE);

        BSONObject bson = (BSONObject)JSON.parse(key);
        edbMessage.setDelete(bson);
        byte[] request = EDBMessageHelper.buildDeleteRequest(edbMessage);

        // send message
        connection.sendMessage(request);

        byte[] recvBuffer = connection.receiveMessage();
        EDBMessage rtnSDBMessage = EDBMessageHelper.msgExtractReply(recvBuffer);
        return rtnSDBMessage;
    }

    private IConnection getConnection(String key) {
        ServerAddress sa = _edbCommon.getLocator().getPrimary(key);
        if(sa != null) {
            // for testing
            Integer times = _nodeRecordMap.get(sa);
            if(times == null) {
                _nodeRecordMap.put(sa, 1);
            } else {
                _nodeRecordMap.put(sa, times+1);
            }
        }

        return _edbCommon.getConnectionMap().get(sa);
    }

    public void setQueue(OperationQueue q){
        jobQueue = q;
    }

    public void run(){
        while (true){
            try {
                Message e = jobQueue.get();
                handleMessgae(e);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void handleMessgae(Message e){
        if (e == null){
            return;
        }
        Operations o = e.getOperation();
        if(o.equals(Operations.INSERT)){
            EDBMessage message = insert(e.getKey(), e.getRecord());
            timeLogger.addInsert();
        }else if(o.equals(Operations.DELETE)){
            EDBMessage message = delete(e.getKey());
            timeLogger.addDelete();
        }else if(o.equals(Operations.QUERY)){
            EDBMessage message = query(e.getKey());
            timeLogger.addQuery();
        }else{
            return;
        }
    }
}
