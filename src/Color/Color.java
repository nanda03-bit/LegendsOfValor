/**
 * Filename: Color.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-06
 * Description: Stores ANSI escape codes for terminal colors used throughout the game.
 */

package Color;

import java.util.*;

/**
 * A utility class to store ANSI color codes for console output.
 */
public class Color {
    public String Red = "\u001B[31m";
    public String Green = "\u001B[32m";
    public String Cyan = "\u001B[36m";
    public String Blue = "\u001B[34m";
    public String Purple = "\u001B[35m";
    public String Bg_White = "\u001B[47m";
    public String Orange = "\u001B[38;5;208m";
    public String Bold = "\u001B[1m";
    public String Pink = "\u001B[35m";
    public String Yellow = "\u001B[33m";
    public String Brown = "\u001B[38;5;94m";
    public String Violet = "\u001B[38;5;177m";
    public String LightGreen = "\u001B[92m";
    public String LightBlue = "\u001B[94m";
    public String LightPink = "\u001B[38;5;218m";
    public String Reset = "\u001B[0m";

    public List<String> colours;
}
