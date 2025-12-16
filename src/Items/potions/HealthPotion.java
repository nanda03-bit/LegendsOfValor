package Items.potions;

import Items.Potion;
import Player.Heroes.Hero;

/**
 * A potion that restores health points to a hero.
 * Extends the base Potion class.
 */
public class HealthPotion extends Potion {

    /**
     * Constructor for HealthPotion.
     *
     * @param name              The name of the potion.
     * @param price             The price of the potion.
     * @param requiredLevel     The level required to use.
     * @param attributeIncrease The amount of HP restored.
     */
    public HealthPotion(String name, int price, int requiredLevel, int attributeIncrease) {
        super(name, price, requiredLevel, attributeIncrease, "Health");
    }

    /**
     * Applies the potion effect to a hero.
     *
     * @param hero The hero to apply the potion to.
     */
    public void apply(Hero hero) {
        int newHp = Math.min(hero.getHp() + getAttributeIncrease(), hero.getMaxHp());
        hero.setHp(newHp);
    }
}
