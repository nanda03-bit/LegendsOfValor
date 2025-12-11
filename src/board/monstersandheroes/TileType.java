/**
 * Filename: TileType.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-09
 * Description: Enumeration representing the different types of tiles that can exist
 *              on the game board. Used to distinguish between accessible areas,
 *              markets, and blocked terrain.
 */
package board.monstersandheroes;

public enum TileType {
    /**
     * Common space tile where battles can occur randomly.
     */
    COMMON,
    
    /**
     * Market tile where heroes can buy and sell items.
     */
    MARKET,
    
    /**
     * Inaccessible tile that cannot be entered by heroes.
     */
    INACCESSIBLE
}

