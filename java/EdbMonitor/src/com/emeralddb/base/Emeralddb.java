/**
 *      Copyright (C) 2012 SequoiaDB Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
/**
 * @package com.sequoiadb.base;
 * @brief Emeralddb Driver for Java
 * @author zhengle zhou
 */
package com.emeralddb.base;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.List;

import org.bson.BSONObject;
import org.bson.util.JSON;

import com.emeralddb.exception.BaseException;
import com.emeralddb.net.ConfigOptions;
import com.emeralddb.net.ConnectionTCPImpl;
import com.emeralddb.net.IConnection;
import com.emeralddb.net.ServerAddress;
import com.emeralddb.util.EDBMessageHelper;
import com.emeralddb.base.EmeralddbConstants.Operation;
import com.emeralddb.base.EDBMessage;
/**
 * @class Emeralddb
 * @brief Database operation interfaces of user
 */
public class Emeralddb {

   private IConnection connection = null;
   private String ip;
   private int port;
   private int lastTimes;
   private String errMsg;

   private int[] updateTimesArr = null;
   private int updateTimes;
   private int index;
   private final int SAVE_RECORD_MAX_NUMER = 40;
   public Emeralddb() {
      index = 0;
      updateTimesArr = new int[SAVE_RECORD_MAX_NUMER];
   }

   public Emeralddb( String ip, int port ) {
      index = 0;
      updateTimesArr = new int[SAVE_RECORD_MAX_NUMER];
      this.ip = ip;
      this.port = port;
   }

   public int getUpdateTimes() {
      EDBMessage edbMsg = getInsertTimes();
      if( edbMsg == null ) {
        updateTimes = -1;
      }
      Snapshot snapshot = edbMsg.getSnapshot();
      if( snapshot == null ) {
         return -1;
      }
      System.out.println("3");
      int insertTimes = snapshot.getInsertTimes();
      System.out.println("insertTimes:" + insertTimes );
      updateTimes =  (insertTimes - lastTimes);
      lastTimes = insertTimes;
      if( index >= SAVE_RECORD_MAX_NUMER )
      {
         index = 0;
      }
      //System.out.println("updateTimes: " + updateTimes);
      updateTimesArr[index%SAVE_RECORD_MAX_NUMER] = updateTimes;
      index ++;
      return updateTimes;
   }

   /**
    * @fn void disconnect()
    * @brief Disconnect the remote server
    * @exception com.emeralddb.exception.BaseException
    */
   public void disconnect() throws BaseException {
      EDBMessage edbMessage = new EDBMessage();
      edbMessage.setOperationCode(Operation.OP_DISCONNECT);
      byte[] request = EDBMessageHelper.buildQueryRequest(edbMessage);
      connection.sendMessage(request);
      connection.close();
   }
   /***
    *
    * @param key
    * @return
    * @throws BaseException
    */
   public EDBMessage getInsertTimes()
      throws BaseException {
      EDBMessage edbMessage = new EDBMessage();
      edbMessage.setOperationCode(Operation.OP_INSERTSNAPSHOT);
      byte[] request = EDBMessageHelper.buildInsertSnapshotRequest(edbMessage);
      try {
         boolean ret = connection.sendMessage(request);
         if( !ret )
         {
            errMsg = String.format( "server is closed." );
            return null;
         }
      } catch( BaseException e ) {
         errMsg = String.format( "[BaseException]send to %s:%d - %s", ip, port, e.getMessage() );
         return null;
      }
      try {
         byte[] recvBuffer = connection.receiveMessage();
         EDBMessage rtnSDBMessage = EDBMessageHelper.msgExtractReply(recvBuffer);
         List<BSONObject> list = rtnSDBMessage.getObjList() ;
         for(int i=0; i<list.size(); i++) {
            BSONObject bo = list.get(i);
            int insertTimes = Integer.parseInt(bo.get("insertTimes").toString());
            int delTimes = Integer.parseInt(bo.get("delTimes").toString());
            int queryTimes = Integer.parseInt(bo.get("queryTimes").toString());
            int serverRunTime = Integer.parseInt(bo.get("serverRunTime").toString());
            Snapshot snapshot = new Snapshot();
            snapshot.setInsertTimes(insertTimes);
            snapshot.setDelTimes(delTimes);
            snapshot.setQueryTimes(queryTimes);
            snapshot.setServerRunTime(serverRunTime);
            rtnSDBMessage.setSnapshot(snapshot);
            break;
         }
         return rtnSDBMessage;
      } catch( BaseException e) {
         errMsg = String.format( "[BaseException]recive from %s:%d - %s", ip, port, e.getMessage() );
         return null;
      }
   }
   private boolean initConnection(String ip, int port) throws UnknownHostException {
         ServerAddress sa = new ServerAddress(ip, port);
         ConfigOptions options = new ConfigOptions();
         connection= new ConnectionTCPImpl(sa, options);
         return connection.connect();
   }

   public boolean start()  {
      try {
         return initConnection(ip, port);
      } catch (UnknownHostException e1) {
         connection = null;
         //e1.printStackTrace();
         errMsg = String.format( "[UnknownHostException]can not connect server %s:%d", ip, port );
         return false;
      } catch(BaseException be) {
         errMsg = String.format( "can not connect server %s:%d", ip, port );
         return false;
      } catch( Exception e) {
         errMsg = String.format( "%s", e.getMessage().toString() );
         return false;
      }
   }

   public String getErrorMsg() {
      return errMsg;
   }

   public String getIp() {
      return ip;
   }

   public int getLastTimes() {
      return lastTimes;
   }

   public int getCurInsertTimes() {
//      System.out.println( updateTimes );
      return updateTimes;
   }

   public int getPort() {
      return port;
   }
   public int maxRecordNumber() {
      int max = 0;
      for( int i=0; i < updateTimesArr.length; i++ ) {
         //System.out.print( " " + updateTimesArr[i]);
         if( max < updateTimesArr[i] ) {
            max = updateTimesArr[i];
         }
      }
      //System.out.print("\n");
      return max;
   }

}
