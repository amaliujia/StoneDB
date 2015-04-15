import LoadBalancer.LoadBalancer;
import LoadBalancer.NaiveShardingLB;
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
        LoadBalancer LB = new NaiveShardingLB();
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
        simulator.setTracefile(scanner.next());

        simulator.simulate();
    }
}
