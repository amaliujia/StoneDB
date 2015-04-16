package Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by amaliujia on 15-4-16.
 */
public class TimeLogger {
    private long start_time;

    private long end_time;

    private long last_time;

    public long insert;

    public long  query;

    public long delete;

    public ArrayList<Long> intervals;

    public ArrayList<Long> insertval;

    public ArrayList<Long> queryval;

    public ArrayList<Long> deleteval;

    public  static final String statfile = "stat.txt";

    public TimeLogger(){
        insertval = new ArrayList<Long>();
        intervals = new ArrayList<Long>();
        queryval = new ArrayList<Long>();
        deleteval = new ArrayList<Long>();
    }


    public void start(){
        start_time = System.currentTimeMillis();
        last_time = System.currentTimeMillis();
    }

    public void end(){
        end_time = System.currentTimeMillis();
    }

    public void record(long i, long q, long d){
        if((System.currentTimeMillis() - last_time) > 1000){
            last_time = System.currentTimeMillis();
            queryval.add(q - query);
            insertval.add(i - insert);
            deleteval.add(d - delete);
            intervals.add(i + q + d  - query - insert - delete);
            insert = i;
            query = q;
            delete = d;
        }
    }


    public void stat() throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(statfile)));;

        System.out.println("Statistics---------------------------");
        System.out.println("Totoal insert " + insert);
        writer.write("Totoal insert " + insert + "\n");
        System.out.println("Total query " + query);
        writer.write("Totoal query " + query + "\n");
        System.out.println("Total delete " + delete);
        writer.write("Totoal delete " + delete + "\n");
        for(int i = 0; i < intervals.size(); i++){
            System.out.print(intervals.get(i) + " ");
            writer.write(intervals.get(i) + " ");
            if(i % 20 == 0){
                System.out.println();
                writer.write("\n");
            }
        }

        for(int i = 0; i < insertval.size(); i++){
            System.out.print(insertval.get(i) + " ");
            writer.write(insertval.get(i) + " ");
            if(i % 20 == 0){
                System.out.println();
                writer.write("\n");
            }
        }

        for(int i = 0; i < queryval.size(); i++){
            System.out.print(queryval.get(i) + " ");
            writer.write(queryval.get(i) + " ");
            if(i % 20 == 0){
                System.out.println();
                writer.write("\n");
            }
        }

        for(int i = 0; i < deleteval.size(); i++){
            System.out.print(deleteval.get(i) + " ");
            writer.write(deleteval.get(i) + " ");
            if(i % 20 == 0){
                System.out.println();
                writer.write("\n");
            }
        }

        writer.flush();
        writer.close();
    }
}
