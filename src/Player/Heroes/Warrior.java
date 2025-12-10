/**
 * Filename: Warrior.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-11
 * Description: Hero type specializing in physical strength and weapon-based combat.
 */

 package Player.Heroes;

import Utilities.GameConstants;

public class Warrior  extends Hero {
    /**
     * Constructor for the Warrior class.
     * @param name The name of the warrior.
     * @param mana The starting mana of the warrior.
     * @param strength The starting strength of the warrior.
     * @param agility The starting agility of the warrior.
     * @param dexterity The starting dexterity of the warrior.
     * @param startingMoney The starting gold of the warrior.
     * @param startingExperience The starting experience of the warrior.
     */
    public Warrior(String name, int mana, int strength, int agility, int dexterity, int startingMoney, int startingExperience) {
        super(name, GameConstants.HERO_STARTING_LEVEL, GameConstants.HERO_STARTING_HP, mana, strength, agility, dexterity, startingMoney, startingExperience);
    }

    /**
     * Levels up the warrior, increasing their stats with a focus on strength and agility.
     */
    @Override
    public void levelUp() {
        super.levelUp();
        setStrength((int) (getStrength() * 1.1));
        setAgility((int) (getAgility() * 1.1));
        setDexterity((int) (getDexterity() * 1.05));
    }
}
