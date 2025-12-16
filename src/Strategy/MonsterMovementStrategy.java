/**
 * Filename: MonsterMovementStrategy.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Strategy implementation for monster movement on the board.
 */

package Strategy;

import board.valor.ValorBoard;
import Wrapper.MonsterWrapper;
import Utilities.ValorBoardConstants;
import board.common.BoardEntity;
import board.common.BoardUtilities;
import board.valor.ValorBoardUtilities;

public class MonsterMovementStrategy implements MovementStrategy {

    @Override
    public boolean move(ValorBoard board, BoardEntity entity, char direction) {
        if (!(entity instanceof MonsterWrapper)) return false;
        if (direction != 'S') return false;

        MonsterWrapper monster = (MonsterWrapper) entity;
        int currentRow = monster.getRow();
        int currentCol = monster.getCol();
        int newRow = currentRow + 1;

        // Try direct south first
        if (isValidMove(board, entity, newRow, currentCol)) {
            return board.moveEntityTo(monster, newRow, currentCol);
        }

        // If blocked, try alternate column in same lane
        int lane = ValorBoardUtilities.getLaneForColumn(currentCol);
        if (lane != ValorBoardConstants.INVALID_LANE) {
            int[] laneColumns = ValorBoardUtilities.getColumnsForLane(lane);
            for (int altCol : laneColumns) {
                if (altCol != currentCol && isValidMove(board, entity, newRow, altCol)) {
                    return board.moveEntityTo(monster, newRow, altCol);
                }
            }
        }

        return false; // Truly blocked
    }

    @Override
    public boolean isValidMove(ValorBoard board, BoardEntity entity, int newRow, int newCol) {
        if (!(entity instanceof MonsterWrapper)) return false;

        if (!BoardUtilities.isValidCoordinate(newRow, newCol, board.getSize())) {
            return false;
        }

        if (!board.getTile(newRow, newCol).canEnter()) {
            return false;
        }

        if (board.getTile(newRow, newCol).hasMonster()) {
            return false;
        }

        // Remove the incorrect hero check
        return true;
    }
}
