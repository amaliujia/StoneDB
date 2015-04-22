/**
 * Created by amaliujia on 15-4-12.
 */
public class Producer {
    public static void main(String[] args){
        Processor processor = new Processor();
        if(args[2].equals("Mixed")){
            processor.setStrategy(new ConstantMixedStrategy(Integer.parseInt(args[0])));
        }else if(args[2].equals("Insert")){
            processor.setStrategy(new ConsantInsertStrategy(Integer.parseInt(args[0])));
        }else{
            return;
        }

        processor.setTracefile(args[1]);
        processor.run();
    }
}
