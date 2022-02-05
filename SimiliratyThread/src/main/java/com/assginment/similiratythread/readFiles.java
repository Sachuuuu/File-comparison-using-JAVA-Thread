/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assginment.similiratythread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Dhanuka
 */
public class readFiles {

    public static int total_comparisons = 0;

    readFiles(String folder) throws FileNotFoundException {

        File directoryPath = new File(folder);

        boolean exist = directoryPath.exists();

        if (exist == true) {

            File filesList[] = directoryPath.listFiles();

            String filenames[] = new String[filesList.length];

            int i = 0;

            for (File file : filesList) {

                long val = fileSize(file.getAbsolutePath());

                if (val > 0) {
                    filenames[i] = file.getName();
                    i++;

                }
            }

            total_comparisons = ((filenames.length - 1) * filenames.length) / 2;
            System.out.println(" TOT = " + total_comparisons);
            ThreadGenerator tg = new ThreadGenerator(createArraylistOfFiles(filenames));
            tg.compare();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Path");
        }

    }

    public static long fileSize(String fileName) {

        Path path = Paths.get(fileName);
        long bytes = 0;
        try {
            bytes = Files.size(path);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public static ArrayList<String> createArraylistOfFiles(String[] array) {

        ArrayList<String> dataFiles = new ArrayList<String>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                dataFiles.add(Main.Path.getText() + "\\" + array[i]);
            }
        }
        return dataFiles;
    }

}
