package LoadBalancer;

import Interface.HashFunction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by amaliujia on 15-4-24.
 */
public class ConsistencyHash<T> {
    private HashMap<Integer, Integer> virtualNodeToRealNode;

    private ArrayList<String> nodes; // String represent ip:port of node

    private HashMap<String, ArrayList<String>> realNodesToVirtualNodes;

    private HashFunction hashFunction = null;
    private int numberOfReplicas = 1;
    private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();

    public ConsistencyHash(){
        virtualNodeToRealNode = new HashMap<Integer, Integer>();
        nodes = new ArrayList<>();
        realNodesToVirtualNodes = new HashMap<>();
    }
}
