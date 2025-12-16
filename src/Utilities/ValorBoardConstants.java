/**
 * Filename: ValorBoardConstants.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Constants for Legends of Valor board configuration and layout.
 */

package Utilities;

public class ValorBoardConstants {
    public static final int BOARD_SIZE = 8;
    public static final double SPECIAL_TERRAIN_PERCENTAGE = 0.20;
    
    public static final int MONSTERS_NEXUS_ROW = 0;
    public static final int HEROES_NEXUS_ROW_OFFSET = 1;
    public static final int NEXUS_ROW_COUNT = 2;
    
    public static final int LANE_COUNT = 3;
    public static final int[] LANE_0_COLUMNS = {0, 1};
    public static final int[] LANE_1_COLUMNS = {3, 4};
    public static final int[] LANE_2_COLUMNS = {6, 7};
    
    public static final int WALL_COLUMN_1 = 2;
    public static final int WALL_COLUMN_2 = 5;
    
    public static final int TERRAIN_TYPE_COUNT = 3;
    public static final int OBSTACLE_MIN_COUNT = 3;
    public static final int OBSTACLE_MAX_COUNT = 7;
    
    public static final int SPAWN_COLUMN_INDEX_0 = 0;
    public static final int SPAWN_COLUMN_INDEX_1 = 1;
    
    public static final int INVALID_LANE = -1;
    
    public static final int[][] MOVEMENT_DIRECTIONS = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    
    public static final double TERRAIN_BONUS_MULTIPLIER = 1.10;
    
    public static final int FIRST_VALID_POSITION_INDEX = 0;
    
    private ValorBoardConstants() {
    }
}

