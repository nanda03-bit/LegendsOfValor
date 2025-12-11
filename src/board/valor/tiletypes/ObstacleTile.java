package board.valor.tiletypes;

import board.valor.ValorTileType;
import Player.Heroes.Hero;
import Player.Monsters.Monster;

public class ObstacleTile implements ValorTileType {
    @Override
    public char getSymbol() {
        return 'O';
    }
    
    @Override
    public boolean isPassable() {
        return false;
    }
    
    @Override
    public void applyTerrainBonus(Hero hero) {}
    
    @Override
    public void removeTerrainBonus(Hero hero) {}
    
    @Override
    public void applyMonsterBonus(Monster monster) {}
    
    @Override
    public boolean isRemovable() {
        return true;
    }
    
    @Override
    public String getTypeName() {
        return "Obstacle";
    }
}

