package Items.potions;

import Items.Potion;
import Player.Heroes.Hero;

/**
 * A potion that increases the dexterity attribute of a hero.
 * Extends the base Potion class.
 */
public class DexterityPotion extends Potion {

    /**
     * Constructor for DexterityPotion.
     *
     * @param name              The name of the potion.
     * @param price             The price of the potion.
     * @param requiredLevel     The level required to use.
     * @param attributeIncrease The amount of dexterity increase.
     */
    public DexterityPotion(String name, int price, int requiredLevel, int attributeIncrease) {
        super(name, price, requiredLevel, attributeIncrease, "Dexterity");
    }

    /**
     * Applies the potion effect to a hero.
     *
     * @param hero The hero to apply the potion to.
     */
    public void apply(Hero hero) {
        hero.setDexterity(hero.getDexterity() + getAttributeIncrease());
    }
}
