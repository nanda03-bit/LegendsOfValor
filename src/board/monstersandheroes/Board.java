package board.monstersandheroes;

import Player.Heroes.*;
import Utilities.Percentages;
import java.util.*;
import ErrorMessages.PrintErrorMessages;

public class Board {
    private Tile[][] grid;
    private int size;
    private int heroRow;
    private int heroCol;
    PrintErrorMessages error = new PrintErrorMessages();

    public Board(int size, double inaccessibleProb, double marketProb, double commonProb) {
        this.size = size;
        this.grid = new Tile[size][size];
        this.heroRow = 0;
        this.heroCol = 0;
        initializeGrid(inaccessibleProb, marketProb, commonProb);
    }

    public Board(int size) {
        this(size, Percentages.TILE_INACCESSIBLE, Percentages.TILE_MARKET, Percentages.TILE_COMMON);
    }

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
        grid[0][0] = new Tile(TileType.COMMON);
        if (grid.length > 1 && grid[0].length > 1) {
            if (grid[0][1].getType() == TileType.INACCESSIBLE && grid[1][0].getType() == TileType.INACCESSIBLE) {
                grid[0][1] = new Tile(TileType.COMMON);
            }
        }
    }

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
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                error.inaccessibleTile();
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size && grid[row][col].getType() != TileType.INACCESSIBLE;
    }

    public boolean isCommonSpace() {
        return grid[heroRow][heroCol].getType() == TileType.COMMON;
    }

    public boolean isMarket() {
        return grid[heroRow][heroCol].getType() == TileType.MARKET;
    }

    public Tile getTile(int row, int col) {
        return grid[row][col];
    }

    public int getHeroRow() {
        return heroRow;
    }

    public int getHeroCol() {
        return heroCol;
    }

    public int getSize() {
        return size;
    }
}

