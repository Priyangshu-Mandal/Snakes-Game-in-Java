package com.company;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandling {
    public static void writingToFile(String path, int contents){
        File file = new File(path);
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(Integer.toString(contents));
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int readingFromFile(String path){
        File file = new File(path);
        if (!file.exists()){
            writingToFile(path, 0);
            return 0;
        }
        else {
            try {
                file.createNewFile();
                Scanner scanner = new Scanner(file);
                int hiscore = scanner.nextInt();
                scanner.close();
                return hiscore;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public static boolean readingBooleanFromFile(String path){
        File file = new File(path);
        if (!file.exists()){
            writingToFile(path, 0);
            return false;
        }
        else {
            try {
                file.createNewFile();
                Scanner scanner = new Scanner(file);
                int nextInt = scanner.nextInt();
                scanner.close();
                if (nextInt == 0){
                    return false;
                }
                else {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        }
    }

    public static void sound(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(path);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
    // 139/85
    // 145/91
    // 138/88
    // 141/88 avg
}
