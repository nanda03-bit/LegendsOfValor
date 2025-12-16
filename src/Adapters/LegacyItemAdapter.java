/**
 * Filename: LegacyItemAdapter.java
 * Author: Olivia Ma
 * Date: 2025-Dec
 * Description: Adapter to convert previous assignment format into current Item objects.
 */

package Adapters;

import Items.*;

/**
* Adapter to convert prev assignment format into current Item objects (might not be necessary, need to check!)
*/
public class LegacyItemAdapter {
    // example: line array for a weapon: ["Sword", "100", "2", "30", "1"]
    public static Item fromWeaponRecord(String[] fields) {
        String name = fields[0];
        int price = Integer.parseInt(fields[1]);
        int level = Integer.parseInt(fields[2]);
        int damage = Integer.parseInt(fields[3]);
        int hands = Integer.parseInt(fields[4]);
        return new Weapon(name, price, level, damage, hands);
    }


    public static Item fromArmorRecord(String[] fields) {
        String name = fields[0];
        int price = Integer.parseInt(fields[1]);
        int level = Integer.parseInt(fields[2]);
        int reduction = Integer.parseInt(fields[3]);
        return new Armor(name, price, level, reduction);
    }


    public static Item fromPotionRecord(String[] fields) {
        String name = fields[0];
        int price = Integer.parseInt(fields[1]);
        int level = Integer.parseInt(fields[2]);
        int inc = Integer.parseInt(fields[3]);
        String attr = fields[4];
        return new Potion(name, price, level, inc, attr);
    }


    public static Item fromSpellRecord(String[] fields, String spellType) {
        String name = fields[0];
        int price = Integer.parseInt(fields[1]);
        int level = Integer.parseInt(fields[2]);
        int damage = Integer.parseInt(fields[3]);
        int mana = Integer.parseInt(fields[4]);
        return new Spell(name, price, level, damage, mana, spellType);
    }
}