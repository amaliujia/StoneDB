package Simulator;

import LoadBalancer.Base.LoadBalancer;

/**
 * Created by amaliujia on 15-4-13.
 */
public abstract class Simulator {
    protected LoadBalancer LB;
    protected String tracefile;

    public Simulator(LoadBalancer l){
        LB = l;
    }

    public void setTracefile(String file){
        this.tracefile = file;
    }

    public abstract void simulate();

}
