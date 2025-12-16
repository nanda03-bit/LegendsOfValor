/**
 * Filename: ScannerUtil.java
 * Author: Gowrav
 * Date: 2025-Dec
 * Description: Provides a single shared Scanner instance for the entire application
 * to avoid input buffering issues with multiple Scanner objects.
 */

package Utilities;

import java.util.Scanner;

public class ScannerUtil {
    private static Scanner scanner = null;
    
    /**
     * Gets the shared Scanner instance.
     * @return The shared Scanner.
     */
    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
    
    /**
     * Clears the scanner buffer.
     */
    public static void clearBuffer() {
        if (scanner != null && scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
}
