package Items.spells;

import Items.Spell;
import Player.Monsters.Monster;

/**
 * A spell that deals lightning damage and reduces the target's dodge chance.
 * Extends the base Spell class.
 */
public class LightningSpell extends Spell {

    private static final double DODGE_REDUCTION = 0.10; // 10% dodge reduction

    /**
     * Constructor for LightningSpell.
     *
     * @param name          The name of the spell.
     * @param price         The price of the spell.
     * @param requiredLevel The level required to use.
     * @param damage        The base damage of the spell.
     * @param manaCost      The mana cost to cast.
     */
    public LightningSpell(String name, int price, int requiredLevel, int damage, int manaCost) {
        super(name, price, requiredLevel, damage, manaCost, "Lightning");
    }

    /**
     * Applies the lightning debuff to a monster (reduces dodge chance).
     *
     * @param monster The monster to debuff.
     */
    public void applyDebuff(Monster monster) {
        int currentDodge = monster.getDodgeChance();
        int reduction = (int) (currentDodge * DODGE_REDUCTION);
        monster.setDodgeChance(currentDodge - reduction);
    }
}
