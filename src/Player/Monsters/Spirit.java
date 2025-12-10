/**
 * Filename: Spirit.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-12
 * Description: Monster type specializing in agility-based attacks and evasive abilities.
 */

 package Player.Monsters;

import Utilities.GameConstants;

public class Spirit extends Monster {
    /**
     * Constructor for the Spirit class.
     * Spirits have increased dodge ability compared to other monsters.
     * @param name The name of the spirit.
     * @param level The level of the spirit.
     * @param damage The base damage of the spirit.
     * @param defense The defense value of the spirit.
     * @param dodgeChance The dodge chance of the spirit.
     */
    public Spirit(String name, int level, int damage, int defense, int dodgeChance) {
        super(name, level, level * GameConstants.MONSTER_HP_PER_LEVEL, damage, defense, dodgeChance);
        // Spirits have increased dodge ability
        setDodgeChance((int) (getDodgeChance() * GameConstants.MONSTER_TYPE_BONUS));
    }
}
