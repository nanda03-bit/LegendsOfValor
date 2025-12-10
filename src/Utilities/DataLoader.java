package Utilities;

/**
 * Filename: DataLoader.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-11
 * Description: Loads game data such as heroes, monsters, items, and map details from external files.
 */

import java.io.*;
import java.util.*;

public class DataLoader {
    /**
     * Reads data from a file and returns it as a list of string arrays.
     * Skips the header line and any empty lines.
     * @param fileName The name of the file to read.
     * @return A list of string arrays, where each array represents a line in the file.
     */
    public static List<String[]> readData(String fileName) {
        List<String[]> data = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] parts = line.split("\\s+");
                if (parts.length > 0 && parts[0].length() > 0) {
                    data.add(parts);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
            e.printStackTrace();
        }
        return data;
    }
}
