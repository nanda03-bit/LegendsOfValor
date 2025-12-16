/**
 * Filename: BoardUtilities.java
 * Author: Nandana Shashi
 * Date: 2025-Nov
 * Description: Utility methods for board coordinate validation and adjacency checks.
 */

package board.common;

public class BoardUtilities {
    public static boolean isValidCoordinate(int row, int col, int boardSize) {
        boolean insideRows = row >= 0 && row < boardSize;
        boolean insideCols = col >= 0 && col < boardSize;
        return insideRows && insideCols;
    }
    
    public static boolean isAdjacent(int row1, int col1, int row2, int col2) {
        int rowDiff = Math.abs(row1 - row2);
        int colDiff = Math.abs(col1 - col2);
        boolean closeEnough = rowDiff <= 1 && colDiff <= 1;
        boolean notSameSpot = (rowDiff + colDiff) > 0;

        return closeEnough && notSameSpot;
    }
    
    public static boolean isInAttackRange(int row1, int col1, int row2, int col2) {
        int rowDiff = Math.abs(row1 - row2);
        int colDiff = Math.abs(col1 - col2);
        
        if (rowDiff == 0 && colDiff == 0) {
            return true;
        }
        
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }
    
}

