package Simulator;

import LoadBalancer.LoadBalancer;
import Util.Operations;

import java.io.*;

/**
 * Created by amaliujia on 15-4-13.
 */
public class ConstantSimulator extends Simulator {

    public ConstantSimulator(LoadBalancer l) {
        super(l);
    }

    @Override
    public void simulate() {
        LB.init();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(tracefile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line = null;
        try {
            //Simulator.Simulator Pattern. Ignore
            line = reader.readLine();

            while ((line = reader.readLine()) != null){
                String[] cuts = line.split(" ");
                if(cuts.length < 2){
                    throw new IllegalArgumentException();
                }
                if(cuts[0].equals("Insert")){
                    LB.sumbit(Operations.INSERT, cuts[1], cuts[2]);
                }else if(cuts[0].equals("Delete")){
                    LB.sumbit(Operations.DELETE, cuts[1]);
                }else if(cuts[0].equals("Query")){
                    LB.sumbit(Operations.QUERY, cuts[1]);
                }else{
                    throw new IllegalArgumentException();
                }
            }
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LB.destroy();
    }
}
