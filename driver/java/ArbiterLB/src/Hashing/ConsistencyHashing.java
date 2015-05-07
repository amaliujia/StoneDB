package Hashing;

import Interface.HashFunction;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by amaliujia on 15-4-24.
 */
public class ConsistencyHashing<T> {
    private HashMap<Integer, Integer> virtualNodeToRealNode;

    private ArrayList<String> nodes; // String represent ip:port of node

    private HashMap<String, ArrayList<String>> realNodesToVirtualNodes;

    private HashFunction hashFunction = null;
    private int numberOfReplicas = 1;
    private final SortedMap<Long, T> circle = new TreeMap<Long, T>();

    public ConsistencyHashing(){
        virtualNodeToRealNode = new HashMap<Integer, Integer>();
        nodes = new ArrayList<>();
        realNodesToVirtualNodes = new HashMap<>();
    }

    public ConsistencyHashing(HashFunction function, int numberOfReplicas, Collection<T> nodes){
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;

        for (T node : nodes) {
            add(node);
        }
    }

    public void add(T node){
        for(int i = 0; i < numberOfReplicas; i++){
            circle.put(hashFunction.hashString(node.toString() + i), node);
        }
    }

    public void remove(T node){
        for(int i = 0; i < numberOfReplicas; i++){
            circle.remove(hashFunction.hashString(node.toString() + i));
        }
    }

    public T get(Object key){
        if(circle.isEmpty()){
            return null;
        }

        long hash = hashFunction.hashString(key.toString());

        if(!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public T get(String key){
        if(circle.isEmpty()){
            return null;
        }

        long hash = hashFunction.hashString(key);

        if(!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }
}
