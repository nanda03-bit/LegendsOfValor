/**
 * Filename: CaveTile.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Tile type that provides agility bonus to heroes.
 */

package board.valor.tiletypes;

import board.valor.ValorTileType;
import Utilities.ValorBoardConstants;
import Player.Heroes.Hero;
import Player.Monsters.Monster;

public class CaveTile implements ValorTileType {
    
    @Override
    public char getSymbol() {
        return 'C';
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }
    
    @Override
    public void applyTerrainBonus(Hero hero) {
        int newAgility = (int) (hero.getAgility() * ValorBoardConstants.TERRAIN_BONUS_MULTIPLIER);
        hero.setAgility(newAgility);
    }
    
    @Override
    public void removeTerrainBonus(Hero hero) {
        int originalAgility = (int) (hero.getAgility() / ValorBoardConstants.TERRAIN_BONUS_MULTIPLIER);
        hero.setAgility(originalAgility);
    }
    
    @Override
    public void applyMonsterBonus(Monster monster) {}
    
    @Override
    public boolean isRemovable() {
        return false;
    }
    
    @Override
    public String getTypeName() {
        return "Cave";
    }
}

