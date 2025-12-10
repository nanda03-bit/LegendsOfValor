/**
 * Filename: Weapon.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-08
 * Description: Represents weapon items used by heroes to deal physical damage, with attributes like damage and required hands.
 */

package Items;

public class Weapon extends Item {
    private int damage;
    private int requiredHands;

    /**
     * Constructor for the Weapon class.
     * @param name The name of the weapon.
     * @param price The price of the weapon.
     * @param requiredLevel The level required to use the weapon.
     * @param damage The damage of the weapon.
     * @param requiredHands The number of hands required to wield the weapon.
     */
    public Weapon(String name, int price, int requiredLevel, int damage, int requiredHands) {
        super(name, price, requiredLevel);
        this.damage = damage;
        this.requiredHands = requiredHands;
    }

    /**
     * Gets the damage of the weapon.
     * @return The damage of the weapon.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Gets the number of hands required to wield the weapon.
     * @return The number of required hands.
     */
    public int getRequiredHands() {
        return requiredHands;
    }
}