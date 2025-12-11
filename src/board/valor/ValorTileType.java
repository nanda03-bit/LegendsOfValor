package board.valor;

import Player.Heroes.Hero;
import Player.Monsters.Monster;

public interface ValorTileType {
    char getSymbol();
    boolean isPassable();
    void applyTerrainBonus(Hero hero);
    void removeTerrainBonus(Hero hero);
    void applyMonsterBonus(Monster monster);
    boolean isRemovable();
    String getTypeName();
}

