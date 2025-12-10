/**
 * Filename: MarketDisplay.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-10
 * Description: Handles all market-related display operations including category menus and item listings.
 */

package Display;

import Player.Heroes.Hero;
import Items.*;
import Utilities.GameConstants;
import Color.Color;
import java.util.*;

public class MarketDisplay {
    private static Color c = new Color();
    
    /**
     * Displays the main market menu with categories.
     */
    public static void showMarketMenu(Hero hero) {
        System.out.println();
        System.out.println(c.Blue + "╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println(c.Blue + "║" + c.Bold + "                                MARKET MENU                                   ║");
        System.out.println(c.Blue + "║" + c.Reset+ "                              Your Gold: " + c.Bold + c.Yellow + String.format("%-6d", hero.getGold()) + c.Reset +"                               " + c.Blue + "║");
        System.out.println(c.Blue + "║" + c.Reset+ "                                                                              " + c.Blue + "║");
        System.out.println(c.Blue + "╠══════════════════════════════════════════════════════════════════════════════╣" + c.Reset);
        System.out.println(c.Blue + "║" + c.Bold + "  Select a category:                                                          " + c.Blue + "║" + c.Reset);
        System.out.println(c.Blue + "║" + c.Reset + "                                                                              " + c.Blue + "║");
        System.out.println(c.Blue + "║" + c.Reset + "      " + c.Bold + "1." + c.Reset + "  " + c.LightPink + " Weapons" + c.Reset + "                                                            " + c.Blue + "║");
        System.out.println(c.Blue + "║" + c.Reset + "      " + c.Bold + "2." + c.Reset + "  " + c.Orange + " Armor" + c.Reset + "                                                              " + c.Blue + "║");
        System.out.println(c.Blue + "║" + c.Reset + "      " + c.Bold + "3." + c.Reset + "  " + c.Violet + " Potions" + c.Reset + "                                                            " + c.Blue + "║");
        System.out.println(c.Blue + "║" + c.Reset + "      " + c.Bold + "4." + c.Reset + "  " + c.Pink + " Spells" + c.Reset + "                                                             " + c.Blue + "║");
        System.out.println(c.Blue + "║" + c.Reset + "      " + c.Bold + "5." + c.Reset + "  View All Items                                                      " + c.Blue + "║");
        System.out.println(c.Blue + "║" + c.Reset + "      " + c.Bold + "6." + c.Reset + "  Sell Items                                                          " + c.Blue + "║");
        System.out.println(c.Blue + "║" + c.Reset + "      " + c.Bold + "0." + c.Reset + "  Exit Market                                                         " + c.Blue + "║");
        System.out.println(c.Blue + "╚══════════════════════════════════════════════════════════════════════════════╝" + c.Reset + "\n");
    }
    
    /**
     * Displays items of a specific category.
     * @param items The list of items to display.
     * @param categoryName The name of the category.
     * @param hero The hero viewing the items.
     */
    public static void showCategoryItems(List<Item> items, String categoryName, Hero hero) {
        System.out.println();
        System.out.println(c.Blue + "╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println(c.Blue + "║" + c.Bold + "                               " + categoryName + "                                " + c.Blue + "║");
        System.out.println(c.Blue + "║" + c.Reset+ "                          Your Gold: " + c.Bold + c.Yellow + String.format("%-6d", hero.getGold()) + c.Reset +"                           " + c.Blue + "║");
        System.out.println(c.Blue + "╠══════════════════════════════════════════════════════════════════════╣" + c.Reset);
        
        if (items.isEmpty()) {
            System.out.println(c.Blue + "║" + c.Reset + "                    No " + categoryName.toLowerCase() + " available                     " + c.Blue + "║");
        }
        else {
            // Header
            System.out.println(c.Blue + "║" + c.Bold + String.format(" %-4s %-29s %-14s %-8s %-9s ", "#", "Item Name", "Type", "Lvl", "Price") + c.Blue + "║" + c.Reset);
            System.out.println(c.Blue + "╠══════════════════════════════════════════════════════════════════════╣" + c.Reset);
            
            // Items
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                String itemType = "";
                String colorCode = c.Reset;
                
                if (item instanceof Weapon) {
                    itemType = "Weapon";
                    colorCode = c.LightPink;
                }
                else if (item instanceof Armor) {
                    itemType = "Armor";
                    colorCode = c.Orange;
                }
                else if (item instanceof Potion) {
                    itemType = "Potion";
                    colorCode = c.Violet;
                }
                else if (item instanceof Spell) {
                    itemType = "Spell";
                    colorCode = c.Pink;
                }
                
                String itemName = item.getName();
                if (itemName.length() > 30) {
                    itemName = itemName.substring(0, 27) + "...";
                }
                
                // Check if hero can afford it
                String priceColor = c.Reset;
                if (hero.getGold() < item.getPrice()) {
                    priceColor = c.Red;
                }
                else if (hero.getLevel() < item.getRequiredLevel()) {
                    priceColor = c.Red;
                }
                
                System.out.println(c.Blue + "║" + c.Reset + String.format(" %-4d ", (i + 1)) + colorCode + String.format("%-30s ", itemName) + colorCode + String.format("%-14s ", itemType) + c.Reset + String.format("%-8d ", item.getRequiredLevel()) + priceColor + String.format("%-9d", item.getPrice()) + c.Blue + "║" + c.Reset);
            }
        }
        
        System.out.println(c.Blue + "╚══════════════════════════════════════════════════════════════════════╝" + c.Reset + "\n");
    }
    
    /**
     * Displays all items in the market.
     * @param items The list of all items.
     * @param hero The hero viewing the items.
     */
    public static void showAllItems(List<Item> items, Hero hero) {
        showCategoryItems(items, "ALL ITEMS", hero);
    }
    
    /**
     * Displays hero selection menu for item purchase.
     * @param party The list of heroes in the party.
     * @return The selected hero index, or -1 to cancel.
     */
    public static int showHeroSelection(List<Hero> party) {
        System.out.println();
        System.out.println(c.Green + "╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println(c.Green + "║" + c.Bold + "                       SELECT HERO FOR ITEM                            " + c.Green + "║");
        System.out.println(c.Green + "╠═══════════════════════════════════════════════════════════════════════╣" + c.Reset);
        
        for (int i = 0; i < party.size(); i++) {
            Hero hero = party.get(i);
            System.out.println(c.Green + "║" + c.Reset + String.format("     %-3d %-30s Gold: %-8d Level: %-3d      ", (i + 1), hero.getName(), hero.getGold(), hero.getLevel()) + c.Green + "║" + c.Reset);
        }
        
        System.out.println(c.Green + "║" + c.Reset + "  " + c.Bold + "   0." + c.Reset + "  Cancel                                                        " + c.Green + "║");
        System.out.println(c.Green + "╚═══════════════════════════════════════════════════════════════════════╝" + c.Reset + "\n");
        
        return Input.getHeroSelection(party.size());
    }
    
    /**
     * Displays welcome message for the market.
     */
    public static void marketWelcome() {
        System.out.println();
        System.out.println(c.Blue + "╔═════════════════════════════════════════════════════════════════════════════╗");
        System.out.println(         "║                                                                             ║");
        System.out.println("║" + c.Reset + "                          Welcome to the Market!                             " + c.Blue + "║");
        System.out.println("║                                                                             ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════════════════╝" + c.Reset);
        System.out.println();
    }
    
    /**
     * Displays success message for buying an item.
     */
    public static void buySuccess(Hero hero, Item item) {
        System.out.println(c.Green + hero.getName() + c.Reset + " successfully bought " + c.Bold + item.getName() + c.Reset + "!");
        System.out.println("Remaining Gold: " + c.Yellow + c.Bold + hero.getGold() + c.Reset);
        System.out.println();
    }
    
    /**
     * Displays failure message for buying an item.
     */
    public static void buyFail(Hero hero, Item item) {
        System.out.println(c.Red + "Purchase failed!" + c.Reset);
        if (hero.getGold() < item.getPrice()) {
            System.out.println("Insufficient gold. Need: " + item.getPrice() + ", Have: " + hero.getGold());
        }
        if (hero.getLevel() < item.getRequiredLevel()) {
            System.out.println("Level too low. Need: " + item.getRequiredLevel() + ", Have: " + hero.getLevel());
        }
        System.out.println();
    }
    
    /**
     * Displays hero inventory for selling.
     */
    public static void showHeroInventoryForSell(Hero hero) {
        Color c = new Color();
        System.out.println(c.Green + "\n╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║" + c.Bold + "              " + hero.getName() + "'s INVENTORY (Selling)                  " + c.Green + "║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣" + c.Reset);
        
        List<Item> inventory = hero.getInventory();
        if (inventory.isEmpty()) {
            System.out.println(c.Green + "║" + c.Reset + "                      Inventory is empty                       " + c.Green + "║");
        } else {
            System.out.println(c.Green + "║" + c.Bold + String.format(" %-4s %-35s %-15s %-10s ", "#", "Item Name", "Type", "Sell Price") + c.Green + "║" + c.Reset);
            System.out.println(c.Green + "╠═══════════════════════════════════════════════════════════════════════╣" + c.Reset);
            
            for (int i = 0; i < inventory.size(); i++) {
                Item item = inventory.get(i);
                String itemType = "";
                String colorCode = c.Reset;
                
                if (item instanceof Weapon) {
                    itemType = "⚔ Weapon";
                    colorCode = c.Red;
                } else if (item instanceof Armor) {
                    itemType = "🛡 Armor";
                    colorCode = c.Blue;
                } else if (item instanceof Potion) {
                    itemType = "🧪 Potion";
                    colorCode = c.Green;
                } else if (item instanceof Spell) {
                    itemType = "✨ Spell";
                    colorCode = c.Purple;
                }
                
                String itemName = item.getName();
                if (itemName.length() > 35) {
                    itemName = itemName.substring(0, 32) + "...";
                }
                
                int sellPrice = item.getPrice() / GameConstants.SELL_PRICE_DIVISOR;
                
                System.out.println(c.Green + "║" + c.Reset + colorCode + String.format(" %-4d %-35s %-15s %-10d", 
                    (i + 1), itemName, itemType, sellPrice) + c.Green + "║" + c.Reset);
            }
        }
        
        System.out.println(c.Green + "╚═══════════════════════════════════════════════════════════════════════╝" + c.Reset + "\n");
    }
    
    /**
     * Displays success message for selling an item.
     */
    public static void sellSuccess(Hero hero, Item item) {
        System.out.println(c.Green + hero.getName() + c.Reset + " sold " + c.Bold + item.getName() + c.Reset + " for " + 
            c.Green + c.Bold + (item.getPrice() / GameConstants.SELL_PRICE_DIVISOR) + c.Reset + " gold!");
        System.out.println("New Gold: " + c.Green + c.Bold + hero.getGold() + c.Reset);
        System.out.println();
    }
}

