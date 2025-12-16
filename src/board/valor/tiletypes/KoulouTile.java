/**
 * Filename: KoulouTile.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Tile type that provides strength bonus to heroes.
 */

package board.valor.tiletypes;

import board.valor.ValorTileType;
import Utilities.ValorBoardConstants;
import Player.Heroes.Hero;
import Player.Monsters.Monster;

public class KoulouTile implements ValorTileType {
    
    @Override
    public char getSymbol() {
        return 'K';
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }
    
    @Override
    public void applyTerrainBonus(Hero hero) {
        int newStrength = (int) (hero.getStrength() * ValorBoardConstants.TERRAIN_BONUS_MULTIPLIER);
        hero.setStrength(newStrength);
    }
    
    @Override
    public void removeTerrainBonus(Hero hero) {
        int originalStrength = (int) (hero.getStrength() / ValorBoardConstants.TERRAIN_BONUS_MULTIPLIER);
        hero.setStrength(originalStrength);
    }
    
    @Override
    public void applyMonsterBonus(Monster monster) {}
    
    @Override
    public boolean isRemovable() {
        return false;
    }
    
    @Override
    public String getTypeName() {
        return "Koulou";
    }
}

