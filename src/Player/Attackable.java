package Player;

/**
 * Interface for entities that can participate in combat.
 * Provides a common abstraction for attacking behavior.
 */
public interface Attackable {

    /**
     * Calculates the damage this entity deals when attacking.
     *
     * @return The calculated damage value.
     */
    int calculateDamage();

    /**
     * Applies damage to this entity.
     *
     * @param damage The amount of damage to apply.
     */
    void takeDamage(int damage);

    /**
     * Checks if this entity is still alive.
     *
     * @return true if alive, false otherwise.
     */
    boolean isAlive();

    /**
     * Gets the name of this entity.
     *
     * @return The entity's name.
     */
    String getName();

    /**
     * Gets the current HP of this entity.
     *
     * @return Current HP value.
     */
    int getHp();

    /**
     * Gets the defense/dodge value for damage reduction calculations.
     *
     * @return The defense value.
     */
    int getDefenseValue();
}
