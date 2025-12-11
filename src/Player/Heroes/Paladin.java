/**
 * Filename: Paladin.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-13
 * Description: Hero type combining strength and magical resistance, offering balanced combat abilities.
 */

 package Player.Heroes;

import Utilities.MonstersAndHeroesGameConstants;

public class Paladin extends Hero {
    /**
     * Constructor for the Paladin class.
     * @param name The name of the paladin.
     * @param mana The starting mana of the paladin.
     * @param strength The starting strength of the paladin.
     * @param agility The starting agility of the paladin.
     * @param dexterity The starting dexterity of the paladin.
     * @param startingMoney The starting gold of the paladin.
     * @param startingExperience The starting experience of the paladin.
     */
    public Paladin(String name, int mana, int strength, int agility, int dexterity, int startingMoney, int startingExperience) {
        super(name, MonstersAndHeroesGameConstants.HERO_STARTING_LEVEL, MonstersAndHeroesGameConstants.HERO_STARTING_HP, mana, strength, agility, dexterity, startingMoney, startingExperience);
    }

    /**
     * Levels up the paladin, increasing their stats with a focus on strength and dexterity.
     */
    @Override
    public void levelUp() {
        super.levelUp();
        setStrength((int) (getStrength() * 1.1));
        setDexterity((int) (getDexterity() * 1.1));
        setAgility((int) (getAgility() * 1.05));
    }
}
