/**
 * Filename: InaccessibleTile.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Tile type representing walls that cannot be entered or removed.
 */

package board.valor.tiletypes;

import board.valor.ValorTileType;
import Player.Heroes.Hero;
import Player.Monsters.Monster;

public class InaccessibleTile implements ValorTileType {
    @Override
    public char getSymbol() {
        return 'I';
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
        return false;
    }
    
    @Override
    public String getTypeName() {
        return "Inaccessible";
    }
}

