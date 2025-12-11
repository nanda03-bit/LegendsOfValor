package board.common;

import board.valor.ValorBoard;

public interface MovementStrategy {
    boolean move(ValorBoard board, BoardEntity entity, char direction);
    boolean isValidMove(ValorBoard board, BoardEntity entity, int newRow, int newCol);
}

