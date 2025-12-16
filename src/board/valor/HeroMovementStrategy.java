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

        // RULE: Heroes cannot move forward (north) past monsters in their LANE.
        // They must defeat any monster at or behind them before advancing.

        if (newRow < currentRow) {
            // Moving NORTH - check if there's any monster at hero's row or BEHIND (south)
            // in the same LANE (both columns of the lane).

            // Determine which columns belong to hero's lane
            int laneCol1, laneCol2;
            if (currentCol <= 1) {
                laneCol1 = 0; laneCol2 = 1;  // Lane 1
            } else if (currentCol <= 4) {
                laneCol1 = 3; laneCol2 = 4;  // Lane 2
            } else {
                laneCol1 = 6; laneCol2 = 7;  // Lane 3
            }

            // Check both columns of the lane for monsters at or behind the hero
            for (int row = currentRow; row < board.getSize(); row++) {
                if (board.getTile(row, laneCol1).hasMonster() || board.getTile(row, laneCol2).hasMonster()) {
                    return false; // Monster at or behind hero in same lane - cannot advance
                }
            }
        }

        return true;
    }
}
