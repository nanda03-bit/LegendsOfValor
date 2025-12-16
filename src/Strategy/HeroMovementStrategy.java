/**
 * Filename: HeroMovementStrategy.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Strategy implementation for hero movement on the board.
 */

package Strategy;

import board.valor.ValorBoard;
import Wrapper.HeroWrapper;
import board.common.BoardEntity;
import board.common.BoardUtilities;

public class HeroMovementStrategy implements MovementStrategy {

    @Override
    public boolean move(ValorBoard board, BoardEntity entity, char direction) {
        if (!(entity instanceof HeroWrapper)) {
            return false;
        }
        
        HeroWrapper hero = (HeroWrapper) entity;
        int currentRow = hero.getRow();
        int currentCol = hero.getCol();
        
        int newRow = currentRow;
        int newCol = currentCol;
        
        switch (direction) {
            case 'N': 
                newRow--; 
                break;
            case 'S': 
                newRow++; 
                break;
            case 'E': 
                newCol++; 
                break;
            case 'W': 
                newCol--; 
                break;
            default: 
                return false;
        }
        
        if (!isValidMove(board, entity, newRow, newCol)) {
            return false;
        }
        return board.moveEntityTo(hero, newRow, newCol);
    }
    
    @Override
    public boolean isValidMove(ValorBoard board, BoardEntity entity, int newRow, int newCol) {
        if (!(entity instanceof HeroWrapper)) {
            return false;
        }
        
        HeroWrapper hero = (HeroWrapper) entity;
        int currentRow = hero.getRow();
        int currentCol = hero.getCol();
        
        if (!BoardUtilities.isValidCoordinate(newRow, newCol, board.getSize())) {
            return false;
        }
        
        if (!board.getTile(newRow, newCol).canEnter()) {
            return false;
        }
        
        if (board.getTile(newRow, newCol).hasHero()) {
            return false;
        }

        // Check if target has a monster (heroes can't move into monster tiles - they must attack)
        if (board.getTile(newRow, newCol).hasMonster()) {
            return false;
        }

        // Heroes cannot move through or past monsters
        // Check the space between current position and target
        // For orthogonal movement (not diagonal), check if there's a monster in the path

        if (newRow < currentRow) { // Moving north
            // Check if there's a monster directly north that would block
            for (int r = currentRow - 1; r >= newRow; r--) {
                if (board.getTile(r, currentCol).hasMonster()) {
                    return false; // Monster blocks the path
                }
            }
        } else if (newRow > currentRow) { // Moving south
            // Check if there's a monster directly south that would block
            for (int r = currentRow + 1; r <= newRow; r++) {
                if (board.getTile(r, currentCol).hasMonster()) {
                    return false; // Monster blocks the path
                }
            }
        }

        if (newCol < currentCol) { // Moving west
            // Check if there's a monster directly west that would block
            for (int c = currentCol - 1; c >= newCol; c--) {
                if (board.getTile(currentRow, c).hasMonster()) {
                    return false; // Monster blocks the path
                }
            }
        } else if (newCol > currentCol) { // Moving east
            // Check if there's a monster directly east that would block
            for (int c = currentCol + 1; c <= newCol; c++) {
                if (board.getTile(currentRow, c).hasMonster()) {
                    return false; // Monster blocks the path
                }
            }
        }

        return true;
    }
}
