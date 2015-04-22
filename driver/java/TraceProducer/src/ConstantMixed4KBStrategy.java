import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by amaliujia on 15-4-20.
 */
public class ConstantMixed4KBStrategy implements RequestStrategy {
    private int recordNum;

    public ConstantMixed4KBStrategy(int n){
        this.recordNum = n;
    }

    @Override
    public void run(String path) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer.write("Constant\n");
            String[] uuidArray = null;
            uuidArray = new String[ recordNum ];
            for( int i = 0; i < recordNum; i++ )
            {
                String uuid = java.util.UUID.randomUUID().toString().replaceAll( "-","" )
                        + RandomGUID.newGuid();
                uuidArray[i] = uuid;
            }

            for(int i = 0; i < recordNum; i++){
                writer.write("Insert " + uuidArray[i] + " " + String.format( "{_id:'%s'}", uuidArray[i]) + "\n");
                writer.write("Query " + String.format( "{_id:'%s'}", uuidArray[i]) + "\n");
                writer.write("Delete " + String.format("{_id:'%s'}", uuidArray[i]) + "\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

