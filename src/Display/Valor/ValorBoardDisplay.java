package Display.Valor;

import board.valor.ValorBoard;
import board.valor.ValorTile;
import board.valor.HeroWrapper;
import board.valor.MonsterWrapper;
import board.common.BoardEntity;
import Color.Color;

public class ValorBoardDisplay {
    private static final Color c = new Color();
    
    public static void printBoard(ValorBoard board) {
        System.out.println();
        System.out.println(c.Cyan + "----------------------------------------------------------------" + c.Reset);
        System.out.println(c.Cyan + "                    LEGENDS OF VALOR BOARD                  " + c.Reset);
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
        
        System.out.print("    ");
        for (int col = 0; col < board.getSize(); col++) {
            System.out.print(String.format("%-6s", "Col" + col));
        }
        System.out.println();
        
        for (int row = 0; row < board.getSize(); row++) {
            System.out.print(String.format("%2d  ", row));
            for (int col = 0; col < board.getSize(); col++) {
                String content = display[row][col];
                
                if (content.startsWith("H")) {
                    System.out.print(c.Green + String.format("%-6s", content) + c.Reset);
                } 
                else if (content.startsWith("M")) {
                    System.out.print(c.Red + String.format("%-6s", content) + c.Reset);
                } 
                else if (content.contains("H") && content.contains("M")) {
                    System.out.print(c.Yellow + String.format("%-6s", content) + c.Reset);
                } 
                else if (content.equals("N")) {
                    System.out.print(c.Blue + String.format("%-6s", content) + c.Reset);
                } 
                else if (content.equals("I")) {
                    System.out.print(c.Brown + String.format("%-6s", content) + c.Reset);
                } 
                else if (content.equals("B")) {
                    System.out.print(c.LightGreen + String.format("%-6s", content) + c.Reset);
                } 
                else if (content.equals("C")) {
                    System.out.print(c.Purple + String.format("%-6s", content) + c.Reset);
                } 
                else if (content.equals("K")) {
                    System.out.print(c.Orange + String.format("%-6s", content) + c.Reset);
                } 
                else if (content.equals("O")) {
                    System.out.print(c.Pink + String.format("%-6s", content) + c.Reset);
                } 
                else {
                    System.out.print(String.format("%-6s", content));
                }
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.println(c.Bold + "Legend:" + c.Reset);
        System.out.println(c.Green + "  H1, H2, H3" + c.Reset + " - Heroes");
        System.out.println(c.Red + "  M1, M2, M3..." + c.Reset + " - Monsters");
        System.out.println(c.Blue + "  N" + c.Reset + " - Nexus");
        System.out.println(c.Brown + "  I" + c.Reset + " - Inaccessible (Wall)");
        System.out.println(c.Pink + "  O" + c.Reset + " - Obstacle");
        System.out.println("  P - Plain");
        System.out.println(c.LightGreen + "  B" + c.Reset + " - Bush (Dexterity +10%)");
        System.out.println(c.Purple + "  C" + c.Reset + " - Cave (Agility +10%)");
        System.out.println(c.Orange + "  K" + c.Reset + " - Koulou (Strength +10%)");
        System.out.println();
    }
}