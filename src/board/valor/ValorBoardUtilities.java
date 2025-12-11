package board.valor;

import Utilities.ValorBoardConstants;
import java.util.ArrayList;
import java.util.List;

public class ValorBoardUtilities {
    
    public static int getLaneForColumn(int col) {
        if (col >= ValorBoardConstants.LANE_0_COLUMNS[0] && col <= ValorBoardConstants.LANE_0_COLUMNS[1]) {
            return 0;
        } 
        else if (col >= ValorBoardConstants.LANE_1_COLUMNS[0] && col <= ValorBoardConstants.LANE_1_COLUMNS[1]){
            return 1;
        } 
        else if (col >= ValorBoardConstants.LANE_2_COLUMNS[0] && col <= ValorBoardConstants.LANE_2_COLUMNS[1]){
            return 2;
        }
        return ValorBoardConstants.INVALID_LANE;
    }
    
    public static int[] getColumnsForLane(int lane) {
        switch (lane) {
            case 0: 
                return ValorBoardConstants.LANE_0_COLUMNS;
            case 1: 
                return ValorBoardConstants.LANE_1_COLUMNS;
            case 2: 
                return ValorBoardConstants.LANE_2_COLUMNS;
            default: 
                return null;
        }
    }
    
    public static boolean isWallColumn(int col) {
        return col == ValorBoardConstants.WALL_COLUMN_1 || col == ValorBoardConstants.WALL_COLUMN_2;
    }
    
    public static boolean isMonstersNexus(int row) {
        return row == ValorBoardConstants.MONSTERS_NEXUS_ROW;
    }
    
    public static boolean isHeroesNexus(int row, int boardSize) {
        return row == boardSize - ValorBoardConstants.HEROES_NEXUS_ROW_OFFSET;
    }
    
    public static List<int[]> getCoordinatesInRange(int row, int col, int boardSize) {
        List<int[]> inRange = new ArrayList<>();
        
        inRange.add(new int[]{row, col});
        
        for (int[] dir : ValorBoardConstants.MOVEMENT_DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (board.common.BoardUtilities.isValidCoordinate(newRow, newCol, boardSize)) {
                inRange.add(new int[]{newRow, newCol});
            }
        }
        
        return inRange;
    }
    
    private ValorBoardUtilities() {
    }
}

