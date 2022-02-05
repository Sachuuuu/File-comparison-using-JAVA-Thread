/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assginment.similiratythread;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dhanuka
 */
public class ThreadGenerator {

    private ArrayList<String> array;
    private final ArrayBlockingQueue<String> BlckQueue = new ArrayBlockingQueue<>(10);
    final ExecutorService threadPool = Executors.newCachedThreadPool();
    public static int numberOfComparisons = 0;
    public static boolean status =true;

    public ThreadGenerator() {
    }

    public ThreadGenerator(ArrayList<String> array) {
        System.out.println("Thread Generator started");
        this.array = array;
    }

    public void compare() {

        Runnable consume = () -> {
            while (status) {
                consumer();
            }
        };

        for (int i = 0; i < readFiles.total_comparisons / 2; i++) {
            threadPool.submit(consume);
        }

        threadPool.submit(() -> {
            producer();
        });
    }

    public void producer() {

        for (int i = 0; i < this.array.size(); i++) {

            BlckQueue.offer(this.array.get(i));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(readFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void consumer() {
        String path = BlckQueue.poll();
        if (path != null) {
            int index = this.array.indexOf(path);
            String mainfile = read(path);
            

            for (int i = index + 1; i < this.array.size(); i++) {
                LCSalgo2 lcs = new LCSalgo2();
                String fl2 = read(this.array.get(i));

                float score = lcs.calculateScore(lcs.findMatches(mainfile, fl2, mainfile.length(), fl2.length()), mainfile.length(), fl2.length());

                numberOfComparisons = numberOfComparisons + 1;
                Main.progress.setValue(numberOfComparisons * 100 / readFiles.total_comparisons);
                System.out.println("Similarity : " + score + " files between " + this.array.get(index) + " -> " + this.array.get(i));
                if (score > 0.5) {

                    String data[] = {this.array.get(index).replace(Main.Path.getText() + "\\", ""), this.array.get(i).replace(Main.Path.getText() + "\\", ""), String.valueOf(score * 100) + "%"};
                    DefaultTableModel tb = (DefaultTableModel) Main.jTable1.getModel();
                    tb.addRow(data);
                }

            }

        }
    }

    public static String read(String path) {

        String content = "";
        try {
            content = new Scanner(new File(path)).useDelimiter("\\Z").next();
            content = content.replaceAll("\\s", "");
            content = content.replaceAll("[^a-zA-Z0-9]", "");

        } catch (Exception e) {
            System.out.println("Exceptuion " + e);
        }

        return content;
    }
    
    public void stop() {
        
        System.out.println("STOP INVOKED");
        
        int x = JOptionPane.showConfirmDialog(null, "Please Press OK to Stop the Process");
        
        if(x==0){
            status = false;
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
        }
        }

    }

}
