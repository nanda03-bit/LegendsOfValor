/**
 * Filename: ValorBoardDisplay.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Handles the display and formatting of the Legends of Valor game board.
 */

package Display.Valor;

import board.valor.ValorBoard;
import board.valor.ValorTile;
import Wrapper.HeroWrapper;
import Wrapper.MonsterWrapper;
import board.common.BoardEntity;
import board.valor.ValorBoardUtilities;
import Color.Color;

public class ValorBoardDisplay {
    private static final Color c = new Color();
    
    public static void printBoard(ValorBoard board) {
        System.out.println();
        System.out.println(c.Cyan + "----------------------------------------------------------------" + c.Reset);
        System.out.println(c.Cyan + "                     LEGENDS OF VALOR BOARD                  " + c.Reset);
        System.out.println(c.Cyan + "----------------------------------------------------------------" + c.Reset);
        System.out.println();
        
        String[][] display = new String[board.getSize()][board.getSize()];
        
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                ValorTile tile = board.getTile(row, col);
                if (tile != null) {
                    display[row][col] = String.valueOf(tile.getSymbol());
                } 
                else {
                    display[row][col] = " ";
                }
            }
        }
        
        for (BoardEntity hero : board.getHeroes()) {
            if (hero.isAlive()) {
                int row = hero.getRow();
                int col = hero.getCol();
                if (display[row][col].length() == 1) {
                    display[row][col] = ((HeroWrapper) hero).getEntityId();
                } 
                else {
                    display[row][col] += "/" + ((HeroWrapper) hero).getEntityId();
                }
            }
        }
        
        for (BoardEntity monster : board.getMonsters()) {
            if (monster.isAlive()) {
                int row = monster.getRow();
                int col = monster.getCol();
                if (display[row][col].length() == 1) {
                    display[row][col] = ((MonsterWrapper) monster).getEntityId();
                } 
                else {
                    display[row][col] += "/" + ((MonsterWrapper) monster).getEntityId();
                }
            }
        }
        
        // Build list of visible columns (excluding wall columns)
        java.util.List<Integer> visibleColumns = new java.util.ArrayList<>();
        for (int col = 0; col < board.getSize(); col++) {
            if (!ValorBoardUtilities.isWallColumn(col)) {
                visibleColumns.add(col);
            }
        }
        
        // Print header row with column numbers (only visible columns)
        System.out.print("      ");
        for (int col : visibleColumns) {
            System.out.print(String.format("  Col%-2d  ", col));
        }
        System.out.println();
        
        // Print top separator line
        System.out.print("      ");
        for (int i = 0; i < visibleColumns.size(); i++) {
            System.out.print("---------");
        }
        System.out.println();
        
        // Print board rows
        for (int row = 0; row < board.getSize(); row++) {
            System.out.print(String.format("Row %2d ", row));
            System.out.print("|");
            for (int i = 0; i < visibleColumns.size(); i++) {
                int col = visibleColumns.get(i);
                String content = display[row][col];
                String formattedContent;
                
                if (content.startsWith("H")) {
                    formattedContent = c.Green + String.format("  %-5s", content) + c.Reset;
                } 
                else if (content.startsWith("M")) {
                    formattedContent = c.Red + String.format("  %-5s", content) + c.Reset;
                } 
                else if (content.contains("H") && content.contains("M")) {
                    formattedContent = c.Yellow + String.format("  %-5s", content) + c.Reset;
                } 
                else if (content.equals("N")) {
                    formattedContent = c.Blue + String.format("  %-5s", content) + c.Reset;
                } 
                else if (content.equals("I")) {
                    formattedContent = c.Brown + String.format("  %-5s", content) + c.Reset;
                } 
                else if (content.equals("B")) {
                    formattedContent = c.LightPink + String.format("  %-5s", content) + c.Reset;
                } 
                else if (content.equals("C")) {
                    formattedContent = c.LightBlue + String.format("  %-5s", content) + c.Reset;
                } 
                else if (content.equals("K")) {
                    formattedContent = c.Orange + String.format("  %-5s", content) + c.Reset;
                } 
                else if (content.equals("O")) {
                    formattedContent = c.Violet + String.format("  %-5s", content) + c.Reset;
                } 
                else {
                    formattedContent = String.format("  %-5s", content);
                }
                
                System.out.print(formattedContent);
                
                // Print vertical separator after each column, except between columns in the same lane
                // Lanes: 0-1 (no separator between), 3-4 (no separator between), 6-7 (no separator between)
                boolean showSeparator = true;
                if (i < visibleColumns.size() - 1) {
                    int currentCol = visibleColumns.get(i);
                    int nextCol = visibleColumns.get(i + 1);
                    // Check if current and next columns are in the same lane
                    int currentLane = ValorBoardUtilities.getLaneForColumn(currentCol);
                    int nextLane = ValorBoardUtilities.getLaneForColumn(nextCol);
                    if (currentLane != -1 && currentLane == nextLane) {
                        showSeparator = false; // Same lane, no separator
                    }
                }
                
                if (showSeparator) {
                    System.out.print("|");
                } else {
                    System.out.print(" "); // Space instead of separator for same-lane columns
                }
            }
            System.out.println();
            System.out.println(); // Empty line after each row
        }
        
        // Print bottom separator line
        System.out.print("      ");
        for (int i = 0; i < visibleColumns.size(); i++) {
            System.out.print("---------");
        }
        System.out.println();
        
        System.out.println();
        System.out.println(c.Bold + "Legend:" + c.Reset);
        System.out.println(c.Green + "  H1, H2, H3" + c.Reset + " - Heroes");
        System.out.println(c.Red + "  M1, M2, M3..." + c.Reset + " - Monsters");
        System.out.println(c.Blue + "  N" + c.Reset + " - Nexus");
        System.out.println(c.Brown + "  I" + c.Reset + " - Inaccessible (Wall)");
        System.out.println(c.Violet + "  O" + c.Reset + " - Obstacle");
        System.out.println("  P - Plain");
        System.out.println(c.LightPink + "  B" + c.Reset + " - Bush (Dexterity +10%)");
        System.out.println(c.LightBlue + "  C" + c.Reset + " - Cave (Agility +10%)");
        System.out.println(c.Orange + "  K" + c.Reset + " - Koulou (Strength +10%)");
        System.out.println();
    }
}