package board.valor.tiletypes;

import board.valor.ValorTileType;
import Player.Heroes.Hero;
import Player.Monsters.Monster;

public class PlainTile implements ValorTileType {
    @Override
    public char getSymbol() {
        return 'P';
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }
    
    @Override
    public void applyTerrainBonus(Hero hero) {}
    
    @Override
    public void removeTerrainBonus(Hero hero) {}
    
    @Override
    public void applyMonsterBonus(Monster monster) {}
    
    @Override
    public boolean isRemovable() {
        return false;
    }
    
    @Override
    public String getTypeName() {
        return "Plain";
    }
}

