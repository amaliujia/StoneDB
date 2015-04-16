/**
 * Created by amaliujia on 15-4-12.
 */
public class Producer {
    public static void main(String[] args){
        Processor processor = new Processor();
        processor.setStrategy(new ConsantInsertStrategy(1000000));
        processor.setTracefile("trace_constantInsert.txt");
        processor.run();
    }
}
