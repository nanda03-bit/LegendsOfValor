package board.common;

public interface BoardEntity {
    String getEntityId();
    int getRow();
    int getCol();
    void setPosition(int row, int col);
    boolean isAlive();
}

