/**
 * Filename: Potion.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-08
 * Description: Represents consumable potions that temporarily enhance hero attributes or restore HP/MP.
 */

package Items;

public class Potion extends Item {
    private int attributeIncrease;
    private String attributeAffected;

    /**
     * Constructor for the Potion class.
     * @param name The name of the potion.
     * @param price The price of the potion.
     * @param requiredLevel The level required to use the potion.
     * @param attributeIncrease The amount by which the potion increases an attribute.
     * @param attributeAffected The attribute affected by the potion (e.g., "Health", "Mana", "Strength").
     */
    public Potion(String name, int price, int requiredLevel, int attributeIncrease, String attributeAffected) {
        super(name, price, requiredLevel);
        this.attributeIncrease = attributeIncrease;
        this.attributeAffected = attributeAffected;
    }

    /**
     * Gets the amount by which the potion increases an attribute.
     * @return The attribute increase value.
     */
    public int getAttributeIncrease() {
        return attributeIncrease;
    }

    /**
     * Gets the attribute affected by the potion.
     * @return The name of the attribute affected.
     */
    public String getAttributeAffected() {
        return attributeAffected;
    }
}
