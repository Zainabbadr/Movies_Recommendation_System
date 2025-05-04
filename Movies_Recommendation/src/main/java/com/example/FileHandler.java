package com.example;
import java.io.*;
import java.util.*;

public class FileHandler {
    public  List<String> readFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        }
        return lines;
    }

    public static void writeFile(String filename, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static BufferedWriter getBufferedWriter(String filename) throws IOException {
        return new BufferedWriter(new FileWriter(filename));
    }
}
