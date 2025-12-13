package Player.Monsters;

import Player.Character;

public class Monster extends Character {
    private int baseDamage;
    private int defense;
    private int dodgeChance;
    private int maxHp;

    /**
     * Constructor for the Monster class.
     * @param name The name of the monster.
     * @param level The level of the monster.
     * @param hp The health points of the monster.
     * @param baseDamage The base damage of the monster.
     * @param defense The defense value of the monster.
     * @param dodgeChance The dodge chance of the monster.
     */
    public Monster(String name, int level, int hp, int baseDamage, int defense, int dodgeChance) {
        super(name, level, hp);
        this.maxHp = hp;
        this.baseDamage = baseDamage + (int)(baseDamage * 0.1 * (level -1)); // 10% increase per level
        this.defense = defense + (int)(defense * 0.1 * (level - 1)); // 10% increase per level
        this.dodgeChance = dodgeChance + (int)(dodgeChance * 0.05 * (level - 1)); // 5% increase per level
    }
    
    /**
     * Gets the maximum HP of the monster.
     * @return The maximum HP of the monster.
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Gets the base damage of the monster.
     * @return The base damage of the monster.
     */
    public int getBaseDamage() {
        return baseDamage;
    }

    /**
     * Gets the defense value of the monster.
     * @return The defense value of the monster.
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Gets the dodge chance of the monster.
     * @return The dodge chance of the monster.
     */
    public int getDodgeChance() {
        return dodgeChance;
    }

    /**
     * Sets the base damage of the monster.
     * @param baseDamage The new base damage value.
     */
    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    /**
     * Sets the defense value of the monster.
     * @param defense The new defense value.
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }

    /**
     * Sets the dodge chance of the monster.
     * @param dodgeChance The new dodge chance value.
     */
    public void setDodgeChance(int dodgeChance) {
        this.dodgeChance = dodgeChance;
    }

    public static int getDamage(Monster m) {
        // Use base damage and other multipliers; project uses getBaseDamage() * multiplier * Percentages.DAMAGE
        return (int) (m.getBaseDamage() * 1.0); // multiplier can be adjusted by debuffs elsewhere
    }


    public static void applyDebuff(Monster m, String debuffType, double multiplier, int rounds) {
        // The Battle class stores debuffs in its own map. 
        // Monsters can now own their own debuffs.
        // For compatibility: we add a placeholder that prints an info message (teams should wire this to Battle).
        System.out.println("Applying debuff " + debuffType + " to monster " + m.getName() + " x" + multiplier + " for " + rounds + " rounds.");
    }
}
