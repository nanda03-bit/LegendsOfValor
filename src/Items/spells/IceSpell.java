package Items.spells;

import Items.Spell;
import Player.Monsters.Monster;

/**
 * A spell that deals ice damage and reduces the target's damage output.
 * Extends the base Spell class.
 */
public class IceSpell extends Spell {

    private static final double DAMAGE_REDUCTION = 0.10; // 10% damage reduction

    /**
     * Constructor for IceSpell.
     *
     * @param name          The name of the spell.
     * @param price         The price of the spell.
     * @param requiredLevel The level required to use.
     * @param damage        The base damage of the spell.
     * @param manaCost      The mana cost to cast.
     */
    public IceSpell(String name, int price, int requiredLevel, int damage, int manaCost) {
        super(name, price, requiredLevel, damage, manaCost, "Ice");
    }

    /**
     * Applies the ice debuff to a monster (reduces damage).
     *
     * @param monster The monster to debuff.
     */
    public void applyDebuff(Monster monster) {
        int currentDamage = monster.getBaseDamage();
        int reduction = (int) (currentDamage * DAMAGE_REDUCTION);
        monster.setBaseDamage(currentDamage - reduction);
    }
}
