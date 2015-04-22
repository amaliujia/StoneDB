import LoadBalancer.EqualSharingLB;
import LoadBalancer.LoadBalancer;
import LoadBalancer.NaiveShardingLB;
import LoadBalancer.EqualSharingMultiEDBLB;
import Simulator.ConstantSimulator;
import Simulator.InmemoryConstantSimulator;
import Simulator.Simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by amaliujia on 15-4-13.
 */
public class LDMain {
    public static void main(String[] args){
        String config = args[0];
        String LBPolicy = args[1];
        LoadBalancer LB = null;
        if(LBPolicy.equals("NaiveSharing")){
           LB = new NaiveShardingLB();
        } else if(LBPolicy.equals("EqualSharing")){
           LB = new EqualSharingLB();
        } else if(LBPolicy.equals("MultiSharing")){
            LB = new EqualSharingMultiEDBLB();
        }

        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(config));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int numInstances = Integer.parseInt(scanner.next());
        for(int i = 0; i < numInstances; i++){
            String[] l = scanner.next().split(":");
            LB.addInstance(l[0], Integer.parseInt(l[1]));
        }

        Simulator simulator = new ConstantSimulator(LB);
        //Simulator simulator = new InmemoryConstantSimulator(LB);
        simulator.setTracefile(scanner.next());

        simulator.simulate();
    }
}
