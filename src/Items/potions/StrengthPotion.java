package Items.potions;

import Items.Potion;
import Player.Heroes.Hero;

/**
 * A potion that increases the strength attribute of a hero.
 * Extends the base Potion class.
 */
public class StrengthPotion extends Potion {

    /**
     * Constructor for StrengthPotion.
     *
     * @param name              The name of the potion.
     * @param price             The price of the potion.
     * @param requiredLevel     The level required to use.
     * @param attributeIncrease The amount of strength increase.
     */
    public StrengthPotion(String name, int price, int requiredLevel, int attributeIncrease) {
        super(name, price, requiredLevel, attributeIncrease, "Strength");
    }

    /**
     * Applies the potion effect to a hero.
     *
     * @param hero The hero to apply the potion to.
     */
    public void apply(Hero hero) {
        hero.setStrength(hero.getStrength() + getAttributeIncrease());
    }
}
