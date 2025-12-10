/**
 * Filename: Exoskeleton.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-07
 * Description: Monster type with extremely high defense and resilience in battle.
 */

 package Player.Monsters;

import Utilities.GameConstants;

public class Exoskeleton extends Monster {
    /**
     * Constructor for the Exoskeleton class.
     * Exoskeletons have increased defense compared to other monsters.
     * @param name The name of the exoskeleton.
     * @param level The level of the exoskeleton.
     * @param damage The base damage of the exoskeleton.
     * @param defense The defense value of the exoskeleton.
     * @param dodgeChance The dodge chance of the exoskeleton.
     */
    public Exoskeleton(String name, int level, int damage, int defense, int dodgeChance) {
        super(name, level, level * GameConstants.MONSTER_HP_PER_LEVEL, damage, defense, dodgeChance);
        // Exoskeletons have increased defense
        setDefense((int) (getDefense() * GameConstants.MONSTER_TYPE_BONUS));
    }
}
