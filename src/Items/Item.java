/**
 * Filename: Item.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-08
 * Description: Abstract base class for all items, storing shared attributes such as name, price, and required level.
 */

package Items;

public abstract class Item implements IItem {
    private String name;
    private int price;
    private int requiredLevel;

    /**
     * Constructor for the Item class.
     * @param name The name of the item.
     * @param price The price of the item.
     * @param requiredLevel The level required to use the item.
     */
    public Item(String name, int price, int requiredLevel) {
        this.name = name;
        this.price = price;
        this.requiredLevel = requiredLevel;
    }

    /**
     * Gets the name of the item.
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the item.
     * @return The price of the item.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets the level required to use the item.
     * @return The required level.
     */
    public int getRequiredLevel() {
        return requiredLevel;
    }
}

