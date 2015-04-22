import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by amaliujia on 15-4-21.
 */
public class String4K {
    public static void main(String[] args){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("4k.txt")));
            for(int i = 0; i < 256; i++){
                writer.write("abcdefghijklmnop");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
