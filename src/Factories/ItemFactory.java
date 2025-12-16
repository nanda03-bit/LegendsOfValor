/**
 * Filename: ItemFactory.java
 * Author: Olivia Ma
 * Date: 2025-Dec
 * Description: Factory class for creating item instances (weapons, armor, potions, spells).
 */

package Factories;

import Items.*;

public class ItemFactory {
    // creates Items from explicit fields.
    // useful when integrating with DataLoader or adapters
    public static Item createWeapon(String name, int price, int requiredLevel, int damage, int requiredHands) {
        return new Weapon(name, price, requiredLevel, damage, requiredHands);
    }

    public static Item createArmor(String name, int price, int requiredLevel, int damageReduction) {
        return new Armor(name, price, requiredLevel, damageReduction);
    }


    public static Item createPotion(String name, int price, int requiredLevel, int attributeIncrease, String attributeAffected) {
        return new Potion(name, price, requiredLevel, attributeIncrease, attributeAffected);
    }


    public static Item createSpell(String name, int price, int requiredLevel, int damage, int manaCost, String type) {
        return new Spell(name, price, requiredLevel, damage, manaCost, type);
    }
}