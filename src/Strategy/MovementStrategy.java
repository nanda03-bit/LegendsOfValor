/**
 * Filename: MovementStrategy.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Interface defining movement strategies for entities on the board.
 */

package Strategy;

import board.valor.ValorBoard;
import board.common.BoardEntity;

public interface MovementStrategy {
    boolean move(ValorBoard board, BoardEntity entity, char direction);
    boolean isValidMove(ValorBoard board, BoardEntity entity, int newRow, int newCol);
}
