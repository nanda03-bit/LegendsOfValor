/**
 * Filename: NexusTile.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Tile type representing the nexus (spawn point) for heroes or monsters.
 */

package board.valor.tiletypes;

import board.valor.ValorTileType;
import Player.Heroes.Hero;
import Player.Monsters.Monster;

public class NexusTile implements ValorTileType {
    private final boolean isHeroesNexus;
    
    public NexusTile(boolean isHeroesNexus) {
        this.isHeroesNexus = isHeroesNexus;
    }
    
    @Override
    public char getSymbol() {
        return 'N';
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
        return isHeroesNexus() ? "Heroes' Nexus" : "Monsters' Nexus";
    }
    
    public boolean isHeroesNexus() {
        return isHeroesNexus;
    }
}

