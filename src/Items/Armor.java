/**
 * Filename: Armor.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-08
 * Description: Represents armor items that provide defensive attributes and reduce incoming damage.
 */

package Items;

public class Armor extends Item {
    private int damageReduction;

    /**
     * Constructor for the Armor class.
     * @param name The name of the armor.
     * @param price The price of the armor.
     * @param requiredLevel The level required to use the armor.
     * @param damageReduction The amount of damage reduction provided by the armor.
     */
    public Armor(String name, int price, int requiredLevel, int damageReduction) {
        super(name, price, requiredLevel);
        this.damageReduction = damageReduction;
    }

    /**
     * Gets the damage reduction provided by the armor.
     * @return The damage reduction value.
     */
    public int getDamageReduction() {
        return damageReduction;
    }
}