package Items.spells;

import Items.Spell;
import Player.Monsters.Monster;

/**
 * A spell that deals fire damage and reduces the target's defense.
 * Extends the base Spell class.
 */
public class FireSpell extends Spell {

    private static final double DEFENSE_REDUCTION = 0.10; // 10% defense reduction

    /**
     * Constructor for FireSpell.
     *
     * @param name          The name of the spell.
     * @param price         The price of the spell.
     * @param requiredLevel The level required to use.
     * @param damage        The base damage of the spell.
     * @param manaCost      The mana cost to cast.
     */
    public FireSpell(String name, int price, int requiredLevel, int damage, int manaCost) {
        super(name, price, requiredLevel, damage, manaCost, "Fire");
    }

    /**
     * Applies the fire debuff to a monster (reduces defense).
     *
     * @param monster The monster to debuff.
     */
    public void applyDebuff(Monster monster) {
        int currentDefense = monster.getDefense();
        int reduction = (int) (currentDefense * DEFENSE_REDUCTION);
        monster.setDefense(currentDefense - reduction);
    }
}
