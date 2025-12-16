/**
 * Filename: ICharacter.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-07
 * Description: Interface specifying character-related behaviors that both heroes and monsters must implement.
 */

package Player;

/**
 * Interface for characters in the game.
 */
public interface ICharacter {
    /**
     * Gets the name of the character.
     * @return The name of the character.
     */
    String getName();

    /**
     * Gets the level of the character.
     * @return The level of the character.
     */
    int getLevel();

    /**
     * Gets the current health points of the character.
     * @return The current health points.
     */
    int getHp();

    /**
     * Sets the current health points of the character.
     * @param hp The new health points value.
     */
    void setHp(int hp);

    /**
     * Reduces the character's health points by a specified amount of damage.
     * @param damage The amount of damage to take.
     */
    void takeDamage(int damage);

    /**
     * Checks if the character is alive.
     * @return true if the character is alive, false otherwise.
     */
    boolean isAlive();

    /**
     * Levels up the character.
     */
    void levelUp();

    /**
     * Calculates the attack damage this character can deal.
     * This abstraction allows heroes and monsters to attack in the same way.
     * @return The base attack damage value.
     */
    int calculateAttackDamage();
}

