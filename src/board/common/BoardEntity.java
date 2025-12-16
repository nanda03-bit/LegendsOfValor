/**
 * Filename: BoardEntity.java
 * Author: Nandana Shashi
 * Date: 2025-Nov
 * Description: Interface for entities that can be placed on the board.
 */

package board.common;

public interface BoardEntity {
    String getEntityId();
    int getRow();
    int getCol();
    void setPosition(int row, int col);
    boolean isAlive();
}

