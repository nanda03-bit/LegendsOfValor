package board.valor;

import board.common.BoardEntity;
import Player.Monsters.Monster;

public class MonsterWrapper implements BoardEntity {
    private final Monster monster;
    private int row;
    private int col;
    private final int monsterIndex;
    
    public MonsterWrapper(Monster monster, int monsterIndex, int row, int col) {
        this.monster = monster;
        this.monsterIndex = monsterIndex;
        this.row = row;
        this.col = col;
    }
    
    @Override
    public String getEntityId() {
        return "M" + monsterIndex;
    }
    
    @Override
    public int getRow() {
        return row;
    }
    
    @Override
    public int getCol() {
        return col;
    }
    
    @Override
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    @Override
    public boolean isAlive() {
        return monster.isAlive();
    }
    
    public Monster getMonster() {
        return monster;
    }
    
    public int getMonsterIndex() {
        return monsterIndex;
    }
}

