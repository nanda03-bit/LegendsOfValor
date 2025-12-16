/**
 * Filename: MonsterMovementStrategy.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Strategy implementation for monster movement on the board.
 */

package Strategy;

import board.valor.ValorBoard;
import Wrapper.MonsterWrapper;
import board.common.BoardEntity;
import board.common.BoardUtilities;

public class MonsterMovementStrategy implements MovementStrategy {
    
    @Override
    public boolean move(ValorBoard board, BoardEntity entity, char direction) {
        if (!(entity instanceof MonsterWrapper)) {
            return false;
        }
        
        if (direction != 'S') {
            return false;
        }
        
        MonsterWrapper monster = (MonsterWrapper) entity;
        int currentRow = monster.getRow();
        int currentCol = monster.getCol();
        
        int newRow = currentRow + 1;
        int newCol = currentCol;
        
        if (!isValidMove(board, entity, newRow, newCol)) {
            return false;
        }
        
        return board.moveEntityTo(monster, newRow, newCol);
    }
    
    @Override
    public boolean isValidMove(ValorBoard board, BoardEntity entity, int newRow, int newCol) {
        if (!(entity instanceof MonsterWrapper)) {
            return false;
        }
        
        MonsterWrapper monster = (MonsterWrapper) entity;
        int currentRow = monster.getRow();
        int currentCol = monster.getCol();
        
        if (newRow <= currentRow || newCol != currentCol) {
            return false;
        }
        
        if (!BoardUtilities.isValidCoordinate(newRow, newCol, board.getSize())) {
            return false;
        }
        
        if (!board.getTile(newRow, newCol).canEnter()) {
            return false;
        }
        
        if (board.getTile(newRow, newCol).hasMonster()) {
            return false;
        }
        
        if (BoardUtilities.isValidCoordinate(currentRow - 1, currentCol, board.getSize())) {
            if (board.getTile(currentRow - 1, currentCol).hasHero()) {
                return false;
            }
        }
        
        return true;
    }
}
