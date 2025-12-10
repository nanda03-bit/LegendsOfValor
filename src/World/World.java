/**
 * Filename: World.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-09
 * Description: Represents the game world and its map layout, handling tile generation,
 *              player positioning, movement rules, and world interactions.
 */

package World;

import Player.Heroes.*;
import Utilities.Percentages;
import java.util.*;
import ErrorMessages.PrintErrorMessages;

public class World {
    private Tile[][] grid;
    private int size;
    private int heroRow;
    private int heroCol;
    PrintErrorMessages error = new PrintErrorMessages();

    /**
     * Constructor for the World class.
     * @param size The size of the world (size x size grid).
     * @param inaccessibleProb The probability of a tile being inaccessible.
     * @param marketProb The probability of a tile being a market.
     * @param commonProb The probability of a tile being common space.
     */
    public World(int size, double inaccessibleProb, double marketProb, double commonProb) {
        this.size = size;
        this.grid = new Tile[size][size];
        this.heroRow = 0;
        this.heroCol = 0;
        initializeGrid(inaccessibleProb, marketProb, commonProb);
    }

    /**
     * Constructor for the World class with default probabilities.
     * @param size The size of the world (size x size grid).
     */
    public World(int size) {
        this(size, Percentages.TILE_INACCESSIBLE, Percentages.TILE_MARKET, Percentages.TILE_COMMON);
    }

    /**
     * Initializes the grid with tiles of different types based on probabilities.
     * @param inaccessibleProb The probability of a tile being inaccessible.
     * @param marketProb The probability of a tile being a market.
     * @param commonProb The probability of a tile being common space.
     */
    private void initializeGrid(double inaccessibleProb, double marketProb, double commonProb) {
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                double rand = Math.random();
                if (rand < inaccessibleProb){
                    grid[i][j] = new Tile(TileType.INACCESSIBLE);
                }
                else if (rand < inaccessibleProb + marketProb){
                    grid[i][j] = new Tile(TileType.MARKET);
                }
                else{
                    grid[i][j] = new Tile(TileType.COMMON);
                }
            }
        }
        // Ensure the starting tile is not inaccessible and has at least one valid move.
        grid[0][0] = new Tile(TileType.COMMON);
        if (grid.length > 1 && grid[0].length > 1) {
            if (grid[0][1].getType() == TileType.INACCESSIBLE && grid[1][0].getType() == TileType.INACCESSIBLE) {
                grid[0][1] = new Tile(TileType.COMMON);
            }
        }
    }

    /**
     * Moves the hero party in the specified direction.
     * @param direction The direction to move ('w', 'a', 's', 'd').
     * @param party The list of heroes in the party.
     */
    public void move(char direction, List<Hero> party) {
        int newRow = heroRow;
        int newCol = heroCol;

        switch (direction) {
            case 'w':
                newRow--;
                break;
            case 'a':
                newCol--;
                break;
            case 's':
                newRow++;
                break;
            case 'd':
                newCol++;
                break;
        }

        if (isValidMove(newRow, newCol)) {
            heroRow = newRow;
            heroCol = newCol;
        } else {
            // Check if the move is out of bounds or into an inaccessible tile
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                // It's inside the grid, so it must be an inaccessible tile
                error.inaccessibleTile();
            }
        }
    }

    /**
     * Checks if a move to the specified row and column is valid.
     * @param row The row to move to.
     * @param col The column to move to.
     * @return true if the move is valid, false otherwise.
     */
    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size && grid[row][col].getType() != TileType.INACCESSIBLE;
    }

    /**
     * Checks if the hero party is currently on a common space tile.
     * @return true if on a common space tile, false otherwise.
     */
    public boolean isCommonSpace() {
        return grid[heroRow][heroCol].getType() == TileType.COMMON;
    }

    /**
     * Checks if the hero party is currently on a market tile.
     * @return true if on a market tile, false otherwise.
     */
    public boolean isMarket() {
        return grid[heroRow][heroCol].getType() == TileType.MARKET;
    }

    /**
     * Gets the tile at the specified row and column.
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return The tile at the specified coordinates.
     */
    public Tile getTile(int row, int col) {
        return grid[row][col];
    }

    /**
     * Gets the current row of the hero party.
     * @return The hero party's current row.
     */
    public int getHeroRow() {
        return heroRow;
    }

    /**
     * Gets the current column of the hero party.
     * @return The hero party's current column.
     */
    public int getHeroCol() {
        return heroCol;
    }

    /**
     * Gets the size of the world.
     * @return The size of the world.
     */
    public int getSize() {
        return size;
    }
}
