/**
 * Filename: Character.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-08
 * Description: Abstract base class for all characters, defining core attributes like HP, MP, level, and common behaviors.
 */

package Player;

/**
 * Abstract base class for all game characters (heroes and monsters).
 * Implements Attackable interface to allow both heroes and monsters to attack in the same way.
 */
public abstract class Character implements ICharacter, Attackable {
    private String name;
    private int level;
    private int hp;
    protected int maxHp;

    /**
     * Constructor for the Character class.
     *
     * @param name  The name of the character.
     * @param level The starting level of the character.
     * @param hp    The starting health points of the character.
     */
    public Character(String name, int level, int hp) {
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.maxHp = hp;
    }

    /**
     * Gets the name of the character.
     *
     * @return The name of the character.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the level of the character.
     *
     * @return The level of the character.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the current health points of the character.
     *
     * @return The current health points of the character.
     */
    @Override
    public int getHp() {
        return hp;
    }

    /**
     * Gets the maximum health points of the character.
     *
     * @return The maximum health points of the character.
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Sets the current health points of the character.
     *
     * @param hp The new health points value.
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Sets the maximum health points of the character.
     *
     * @param maxHp The new maximum health points value.
     */
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    /**
     * Reduces the character's health points by a specified amount of damage.
     * HP cannot go below 0.
     *
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    /**
     * Checks if the character is alive.
     *
     * @return true if the character's health points are greater than 0, false otherwise.
     */
    @Override
    public boolean isAlive() {
        return this.hp > 0;
    }

    /**
     * Increases the character's level by 1.
     */
    public void levelUp() {
        this.level++;
    }

    /**
     * Calculates the damage this character deals when attacking.
     * Must be implemented by subclasses.
     *
     * @return The calculated damage value.
     */
    @Override
    public abstract int calculateDamage();

    /**
     * Gets the defense value for damage reduction calculations.
     * Must be implemented by subclasses.
     *
     * @return The defense value.
     */
    @Override
    public abstract int getDefenseValue();
}
