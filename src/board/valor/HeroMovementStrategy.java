package board.valor;

import board.common.MovementStrategy;
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
        
        if (newRow < currentRow) {
            if (BoardUtilities.isValidCoordinate(currentRow + 1, currentCol, board.getSize())) {
                if (board.getTile(currentRow + 1, currentCol).hasMonster()) {
                    return false;
                }
            }
        } 
        else if (newRow > currentRow) {
            if (BoardUtilities.isValidCoordinate(currentRow - 1, currentCol, board.getSize())) {
                if (board.getTile(currentRow - 1, currentCol).hasMonster()) {
                    return false;
                }
            }
        }
        
        if (newCol < currentCol) {
            if (BoardUtilities.isValidCoordinate(currentRow, currentCol + 1, board.getSize())) {
                if (board.getTile(currentRow, currentCol + 1).hasMonster()) {
                    return false;
                }
            }
        } 
        else if (newCol > currentCol) {
            if (BoardUtilities.isValidCoordinate(currentRow, currentCol - 1, board.getSize())) {
                if (board.getTile(currentRow, currentCol - 1).hasMonster()) {
                    return false;
                }
            }
        }
        
        return true;
    }
}

