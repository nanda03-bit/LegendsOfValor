/**
 * Filename: Tile.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-09
 * Description: Represents a single tile on the game board, storing its type
 *              (COMMON, MARKET, or INACCESSIBLE) and providing access to tile information.
 */
package board.monstersandheroes;

public class Tile {
    private TileType type;

    /**
     * Constructor for the Tile class.
     * @param type The type of tile (COMMON, MARKET, or INACCESSIBLE).
     */
    public Tile(TileType type) {
        this.type = type;
    }

    /**
     * Gets the type of this tile.
     * @return The TileType of this tile.
     */
    public TileType getType() {
        return type;
    }
}

