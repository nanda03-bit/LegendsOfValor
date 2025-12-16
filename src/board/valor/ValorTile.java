/**
 * Filename: ValorTile.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Represents a single tile on the Valor board, managing terrain type and entity placement.
 */

package board.valor;

import board.valor.tiletypes.*;
import Player.Heroes.Hero;
import Player.Monsters.Monster;

public class ValorTile {
    private ValorTileType state;
    private Hero hero;
    private Monster monster;
    
    public ValorTile(ValorTileType state) {
        this.state = state;
        this.hero = null;
        this.monster = null;
    }
    
    public ValorTileType getState() {
        return state;
    }
    
    public void setState(ValorTileType newState) {
        if (hero != null && state != newState) {
            state.removeTerrainBonus(hero);
            newState.applyTerrainBonus(hero);
        }
        this.state = newState;
    }
    
    public boolean canEnter() {
        if (!state.isPassable()) {
            return false;
        }
        return true;
    }
    
    public boolean placeHero(Hero hero) {
        if (this.hero != null) {
            return false;
        }
        if (!state.isPassable()) {
            return false;
        }
        this.hero = hero;
        state.applyTerrainBonus(hero);
        return true;
    }
    
    public void removeHero() {
        if (hero != null) {
            state.removeTerrainBonus(hero);
            hero = null;
        }
    }
    
    public boolean placeMonster(Monster monster) {
        if (this.monster != null) {
            return false;
        }
        if (!state.isPassable()) {
            return false;
        }
        this.monster = monster;
        state.applyMonsterBonus(monster);
        return true;
    }
    
    public void removeMonster() {
        monster = null;
    }
    
    public Hero getHero() {
        return hero;
    }
    
    public Monster getMonster() {
        return monster;
    }
    
    public boolean hasHero() {
        return hero != null;
    }
    
    public boolean hasMonster() {
        return monster != null && monster.isAlive();
    }
    
    public void removeObstacle() {
        if (state.isRemovable()) {
            setState(new PlainTile());
        }
    }
    
    public char getSymbol() {
        return state.getSymbol();
    }

    // wrapper method
    public void applyTerrainEffect(Hero hero) {
        state.applyTerrainBonus(hero);
    }

    // wrapper method
    public void removeTerrainEffect(Hero hero) {
        state.removeTerrainBonus(hero);
    }
}

