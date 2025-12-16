package Items.potions;

import Items.Potion;
import Player.Heroes.Hero;

/**
 * A potion that restores mana points to a hero.
 * Extends the base Potion class.
 */
public class ManaPotion extends Potion {

    /**
     * Constructor for ManaPotion.
     *
     * @param name              The name of the potion.
     * @param price             The price of the potion.
     * @param requiredLevel     The level required to use.
     * @param attributeIncrease The amount of MP restored.
     */
    public ManaPotion(String name, int price, int requiredLevel, int attributeIncrease) {
        super(name, price, requiredLevel, attributeIncrease, "Mana");
    }

    /**
     * Applies the potion effect to a hero.
     *
     * @param hero The hero to apply the potion to.
     */
    public void apply(Hero hero) {
        int newMp = Math.min(hero.getMp() + getAttributeIncrease(), hero.getMaxMp());
        hero.setMp(newMp);
    }
}
