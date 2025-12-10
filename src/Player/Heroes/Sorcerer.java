/**
 * Filename: Sorcerer.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-11
 * Description: Hero type with powerful magic attacks and high dexterity.
 */

 package Player.Heroes;

import Utilities.GameConstants;

public class Sorcerer extends Hero {
    /**
     * Constructor for the Sorcerer class.
     * @param name The name of the sorcerer.
     * @param mana The starting mana of the sorcerer.
     * @param strength The starting strength of the sorcerer.
     * @param agility The starting agility of the sorcerer.
     * @param dexterity The starting dexterity of the sorcerer.
     * @param startingMoney The starting gold of the sorcerer.
     * @param startingExperience The starting experience of the sorcerer.
     */
    public Sorcerer(String name, int mana, int strength, int agility, int dexterity, int startingMoney, int startingExperience) {
        super(name, GameConstants.HERO_STARTING_LEVEL, GameConstants.HERO_STARTING_HP, mana, strength, agility, dexterity, startingMoney, startingExperience);
    }

    /**
     * Levels up the sorcerer, increasing their stats with a focus on dexterity and agility.
     */
    @Override
    public void levelUp() {
        super.levelUp();
        setDexterity((int) (getDexterity() * 1.1));
        setAgility((int) (getAgility() * 1.1));
        setStrength((int) (getStrength() * 1.05));
    }
}
