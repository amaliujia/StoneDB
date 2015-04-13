/**
 * Created by amaliujia on 15-4-12.
 */
public class Processor {
    private RequestStrategy strategy;

    private String tracefile;

    public void setStrategy(RequestStrategy r){
        this.strategy = r;
    }

    public void setTracefile(String f){
        this.tracefile = f;
    }

    public void run(){
        this.strategy.run(tracefile);
    }
}
