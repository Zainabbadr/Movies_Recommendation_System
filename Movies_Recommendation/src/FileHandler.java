import java.io.*;
import java.util.*;

public class FileHandler {
    public static List<String> readFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    public static void writeFile(String filename, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(content);
        writer.close();
    }

    public static BufferedWriter getBufferedWriter(String filename) throws IOException {
        return new BufferedWriter(new FileWriter(filename));
    }
}
