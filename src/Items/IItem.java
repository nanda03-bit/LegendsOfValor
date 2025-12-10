/**
 * Filename: IItem.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-08
 * Description: Interface defining common item-related methods for all in-game items.
 */


package Items;

/**
 * Interface for items in the game.
 */
public interface IItem {
    /**
     * Gets the name of the item.
     * @return The name of the item.
     */
    String getName();

    /**
     * Gets the price of the item.
     * @return The price of the item.
     */
    int getPrice();

    /**
     * Gets the level required to use the item.
     * @return The required level.
     */
    int getRequiredLevel();
}

