import com.emeralddb.base.Emeralddb;

public class JavaClient{
	public static void main(String[] args)
	{
	   String addr = new String("127.0.0.1");
	   int port = 48127;
	   Emeralddb edb = new Emeralddb();
	   edb.startStat();
	   edb.init("Configure");
	   edb.insert("_id:212", "{_id:212,hello:1}");
	   edb.query("{_id:212}");
	   edb.delete("{_id:212}");
	   edb.endStat(1);
	   edb.disconnect();
	}
}
