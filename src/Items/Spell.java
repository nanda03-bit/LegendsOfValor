/**
 * Filename: Spell.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-09
 * Description: Represents powerful magical attacks with damage, mana cost, and possible debuff effects.
 */

package Items;

public class Spell extends Item {
    private int damage;
    private int manaCost;
    private String type;

    /**
     * Constructor for the Spell class.
     * @param name The name of the spell.
     * @param price The price of the spell.
     * @param requiredLevel The level required to use the spell.
     * @param damage The damage of the spell.
     * @param manaCost The mana cost of the spell.
     * @param type The type of the spell (e.g., "Fire", "Ice", "Lightning").
     */
    public Spell(String name, int price, int requiredLevel, int damage, int manaCost, String type) {
        super(name, price, requiredLevel);
        this.damage = damage;
        this.manaCost = manaCost;
        this.type = type;
    }

    /**
     * Gets the damage of the spell.
     * @return The damage of the spell.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Gets the mana cost of the spell.
     * @return The mana cost of the spell.
     */
    public int getManaCost() {
        return manaCost;
    }

    /**
     * Gets the type of the spell.
     * @return The type of the spell.
     */
    public String getType() {
        return type;
    }
}