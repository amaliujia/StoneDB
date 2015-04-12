import com.emeralddb.base.Emeralddb;
import com.emeralddb.util.RandomGUID;

public class JavaClient{
	public static void main(String[] args)
	{
	   String addr = new String("127.0.0.1");
	   int port = 48127;
	   Emeralddb edb = new Emeralddb();
	   edb.startStat();
	   edb.init("Configure");
//	   edb.insert("_id:212", "{_id:212,hello:1}");
//	   edb.query("{_id:212}");
//	   edb.delete("{_id:212}");

		int recordNum = 10;
		String[] uuidArray = null;
		uuidArray = new String[ recordNum ];
		for( int i = 0; i < recordNum; i++ )
		{
			String uuid = java.util.UUID.randomUUID().toString().replaceAll( "-","" )
					+ RandomGUID.newGuid();
			uuidArray[i] = uuid;
		}

		for(int i = 0; i < recordNum; i++){
			edb.insert(uuidArray[i], String.format( "{_id:'%s'}", uuidArray[i]));
			edb.query( String.format( "{_id:'%s'}", uuidArray[i]));
			edb.delete(String.format( "{_id:'%s'}", uuidArray[i]));
		}

	   edb.endStat(1);
	   edb.disconnect();
	}
}
