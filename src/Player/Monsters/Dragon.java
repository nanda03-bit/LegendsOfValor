/**
 * Filename: Dragon.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-09
 * Description: Monster type known for powerful fire attacks and high overall combat stats.
 */

 package Player.Monsters;

import Utilities.GameConstants;

public class Dragon extends Monster {
    /**
     * Constructor for the Dragon class.
     * Dragons have increased base damage compared to other monsters.
     * @param name The name of the dragon.
     * @param level The level of the dragon.
     * @param damage The base damage of the dragon.
     * @param defense The defense value of the dragon.
     * @param dodgeChance The dodge chance of the dragon.
     */
    public Dragon(String name, int level, int damage, int defense, int dodgeChance) {
        super(name, level, level * GameConstants.MONSTER_HP_PER_LEVEL, damage, defense, dodgeChance);
        // Dragons have increased base damage
        setBaseDamage((int) (getBaseDamage() * GameConstants.MONSTER_TYPE_BONUS));
    }
}
