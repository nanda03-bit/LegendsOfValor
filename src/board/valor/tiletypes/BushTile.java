package board.valor.tiletypes;

import board.valor.ValorTileType;
import Utilities.ValorBoardConstants;
import Player.Heroes.Hero;
import Player.Monsters.Monster;

public class BushTile implements ValorTileType {
    
    @Override
    public char getSymbol() {
        return 'B';
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }
    
    @Override
    public void applyTerrainBonus(Hero hero) {
        int newDexterity = (int) (hero.getDexterity() * ValorBoardConstants.TERRAIN_BONUS_MULTIPLIER);
        hero.setDexterity(newDexterity);
    }
    
    @Override
    public void removeTerrainBonus(Hero hero) {
        int originalDexterity = (int) (hero.getDexterity() / ValorBoardConstants.TERRAIN_BONUS_MULTIPLIER);
        hero.setDexterity(originalDexterity);
    }
    
    @Override
    public void applyMonsterBonus(Monster monster) {}
    
    @Override
    public boolean isRemovable() {
        return false;
    }
    
    @Override
    public String getTypeName() {
        return "Bush";
    }
}

