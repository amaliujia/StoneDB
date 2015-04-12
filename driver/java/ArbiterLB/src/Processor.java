/**
 * Created by amaliujia on 15-4-12.
 */
public class Processor {
    private RequestStrategy strategy;
    private LoadBalancer LB;


    public void setStrategy(RequestStrategy r){
        this.strategy = r;
    }

    public void setLB(LoadBalancer lb){
        this.LB = lb;
    }

    public void run(){
        this.strategy.run(this.LB);
    }
}
