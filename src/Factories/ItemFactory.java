/**
 * Filename: ItemFactory.java
 * Author: Olivia Ma
 * Date: 2025-Dec
 * Description: Factory class for creating item instances (weapons, armor, potions, spells).
 */

package Factories;

import Items.*;
import Items.potions.*;
import Items.spells.*;

/**
 * Factory class for creating items.
 * Creates specific potion and spell subtypes based on attributes.
 */
public class ItemFactory {

    /**
     * Creates a weapon item.
     *
     * @param name          The weapon name.
     * @param price         The price.
     * @param requiredLevel Required level to use.
     * @param damage        Damage value.
     * @param requiredHands Number of hands required.
     * @return The created Weapon.
     */
    public static Item createWeapon(String name, int price, int requiredLevel, int damage, int requiredHands) {
        return new Weapon(name, price, requiredLevel, damage, requiredHands);
    }

    /**
     * Creates an armor item.
     *
     * @param name            The armor name.
     * @param price           The price.
     * @param requiredLevel   Required level to use.
     * @param damageReduction Damage reduction value.
     * @return The created Armor.
     */
    public static Item createArmor(String name, int price, int requiredLevel, int damageReduction) {
        return new Armor(name, price, requiredLevel, damageReduction);
    }

    /**
     * Creates a potion of the appropriate type based on the attribute affected.
     *
     * @param name              The potion name.
     * @param price             The price.
     * @param requiredLevel     Required level to use.
     * @param attributeIncrease The amount of increase.
     * @param attributeAffected The attribute affected (Health, Mana, Strength, Agility, Dexterity, or All).
     * @return The created Potion (specific subtype).
     */
    public static Potion createPotion(String name, int price, int requiredLevel, int attributeIncrease, String attributeAffected) {
        String attr = attributeAffected.toLowerCase().trim();

        // Handle multi-attribute potions (like "Health/Mana/Strength/Agility")
        if (attr.contains("/") || attr.equalsIgnoreCase("all")) {
            // For multi-attribute potions, default to base Potion class
            return new Potion(name, price, requiredLevel, attributeIncrease, attributeAffected);
        }

        switch (attr) {
            case "health":
                return new HealthPotion(name, price, requiredLevel, attributeIncrease);
            case "mana":
                return new ManaPotion(name, price, requiredLevel, attributeIncrease);
            case "strength":
                return new StrengthPotion(name, price, requiredLevel, attributeIncrease);
            case "agility":
                return new AgilityPotion(name, price, requiredLevel, attributeIncrease);
            case "dexterity":
                return new DexterityPotion(name, price, requiredLevel, attributeIncrease);
            default:
                // For unknown types, use base Potion
                return new Potion(name, price, requiredLevel, attributeIncrease, attributeAffected);
        }
    }

    /**
     * Creates a spell of the appropriate type based on the spell type.
     *
     * @param name          The spell name.
     * @param price         The price.
     * @param requiredLevel Required level to use.
     * @param damage        The spell damage.
     * @param manaCost      The mana cost.
     * @param type          The spell type (Fire, Ice, Lightning).
     * @return The created Spell (specific subtype).
     */
    public static Spell createSpell(String name, int price, int requiredLevel, int damage, int manaCost, String type) {
        String spellType = type.toLowerCase().trim();

        switch (spellType) {
            case "fire":
                return new FireSpell(name, price, requiredLevel, damage, manaCost);
            case "ice":
                return new IceSpell(name, price, requiredLevel, damage, manaCost);
            case "lightning":
                return new LightningSpell(name, price, requiredLevel, damage, manaCost);
            default:
                // For unknown types, use base Spell
                return new Spell(name, price, requiredLevel, damage, manaCost, type);
        }
    }
}
