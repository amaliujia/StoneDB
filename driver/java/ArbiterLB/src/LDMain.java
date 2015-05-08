import LoadBalancer.ConsistencyHashingApproach.ConsistencyHashMultiQueueMultiEDBLB;
import LoadBalancer.EqualSharingApproach.EqualSharingLB;
import LoadBalancer.Base.LoadBalancer;
import LoadBalancer.NaiveSharingApproach.NaiveShardingLB;
import LoadBalancer.EqualSharingApproach.EqualSharingMultiEDBLB;
import Simulator.ConstantSimulator;
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
        }else if(LBPolicy.equals("Consist")){
            LB = new ConsistencyHashMultiQueueMultiEDBLB();
        }

        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(config));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(scanner == null){
            System.out.println(config + "  " + LBPolicy);
        }

        int numInstances = Integer.parseInt(scanner.next());
        System.out.println("Instances num: " + numInstances);
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
