package board.valor;

import board.common.BoardEntity;
import Player.Heroes.Hero;

public class HeroWrapper implements BoardEntity {
    private final Hero hero;
    private int row;
    private int col;
    private final int heroIndex;
    
    public HeroWrapper(Hero hero, int heroIndex, int row, int col) {
        this.hero = hero;
        this.heroIndex = heroIndex;
        this.row = row;
        this.col = col;
    }
    
    @Override
    public String getEntityId() {
        return "H" + heroIndex;
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
        return hero.isAlive();
    }
    
    public Hero getHero() {
        return hero;
    }
    
    public int getHeroIndex() {
        return heroIndex;
    }
}

