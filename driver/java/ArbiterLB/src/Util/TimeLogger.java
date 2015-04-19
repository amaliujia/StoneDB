package Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

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


    public String statfile;

    private BufferedWriter writer;

    public TimeLogger(){
        setLogFile("stat.txt");
    }

    public TimeLogger(String path){

        setLogFile(path);
        try {
            writer = new BufferedWriter(new FileWriter(new File(statfile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLogFile(String path){
        this.statfile = path;

        try {
            writer = new BufferedWriter(new FileWriter(new File(statfile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void curDate(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH); // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        int millis = now.get(Calendar.MILLISECOND);

        String s = String.format("%d-%02d-%02d %02d:%02d:%02d.%03d", year, month + 1, day, hour, minute, second, millis);
        try {
            writer.write(s + "\t");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        start_time = System.currentTimeMillis();
        last_time = System.currentTimeMillis();
        query = 0;
        insert = 0;
        delete = 0;
        try {
            writer.write("Start-------------------------\n");
            curDate();
            writer.write("\n----------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void end(){
        end_time = System.currentTimeMillis();
    }

    public void record(long i, long q, long d){
        long offset = System.currentTimeMillis() - last_time;
        if(offset > 1){
            last_time = System.currentTimeMillis();

            //curDate();
            long qq = q - query;
            long ii = i - insert;
            long dd = d - delete;
            long tt = i + q + d  - query - insert - delete;
            try {
                writer.write(offset + "\t");
                writer.write(qq + "\t" + ii + "\t" + dd + "\t" + tt + "\n");
                writer.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

            insert = i;
            query = q;
            delete = d;

        }


    }


    public void stat() throws IOException{
        System.out.println("Statistics---------------------------");
        System.out.println("Totoal insert " + insert);
        writer.write("Totoal insert " + insert + "\n");
        System.out.println("Total query " + query);
        writer.write("Totoal query " + query + "\n");
        System.out.println("Total delete " + delete);
        writer.write("Totoal delete " + delete + "\n");
        System.out.println("Total time " + (System.currentTimeMillis() - start_time));
        writer.write("Totoal time " + (System.currentTimeMillis() - start_time) + "\n");
        curDate();
        writer.write("\n");

        writer.flush();
        writer.close();
    }
}
