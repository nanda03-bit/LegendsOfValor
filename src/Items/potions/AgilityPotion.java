package Items.potions;

import Items.Potion;
import Player.Heroes.Hero;

/**
 * A potion that increases the agility attribute of a hero.
 * Extends the base Potion class.
 */
public class AgilityPotion extends Potion {

    /**
     * Constructor for AgilityPotion.
     *
     * @param name              The name of the potion.
     * @param price             The price of the potion.
     * @param requiredLevel     The level required to use.
     * @param attributeIncrease The amount of agility increase.
     */
    public AgilityPotion(String name, int price, int requiredLevel, int attributeIncrease) {
        super(name, price, requiredLevel, attributeIncrease, "Agility");
    }

    /**
     * Applies the potion effect to a hero.
     *
     * @param hero The hero to apply the potion to.
     */
    public void apply(Hero hero) {
        hero.setAgility(hero.getAgility() + getAttributeIncrease());
    }
}
