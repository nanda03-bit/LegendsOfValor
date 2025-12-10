/**
 * Filename: Tile.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-06
 * Description: Represents a single tile in the game world, storing its position
 *              and the piece occupying it. Serves as the core unit for all map structures.
 */

package World;

public class Tile {
    private TileType type;

    /**
     * Constructor for the Tile class.
     * @param type The type of the tile.
     */
    public Tile(TileType type) {
        this.type = type;
    }

    /**
     * Gets the type of the tile.
     * @return The type of the tile.
     */
    public TileType getType() {
        return type;
    }
}
