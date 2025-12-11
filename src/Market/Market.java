/**
 * Filename: Market.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-10
 * Description: Implements the marketplace system where heroes can buy or sell items such as weapons, armor, spells, and potions.
 */

package Market;

import Player.Heroes.*;
import Display.MonstersAndHeroes.*;
import Items.*;
import Utilities.*;
import Utilities.MonstersAndHeroesGameConstants;
import ErrorMessages.PrintErrorMessages;
import java.util.*;

public class Market {
    private List<Item> itemsForSale;
    private static PrintErrorMessages error = new PrintErrorMessages();

    /**
     * Constructor for the Market class.
     * Initializes the market with items for sale.
     */
    public Market() {
        this.itemsForSale = new ArrayList<>();
        initializeInventory();
    }

    /**
     * Initializes the market's inventory by loading items from data files.
     */
    private void initializeInventory() {
        List<String[]> weaponData = DataLoader.readData("Weaponry.txt");
        for (String[] data : weaponData) {
            itemsForSale.add(new Weapon(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4])));
        }

        List<String[]> armorData = DataLoader.readData("Armory.txt");
        for (String[] data : armorData) {
            itemsForSale.add(new Armor(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
        }

        List<String[]> potionData = DataLoader.readData("Potions.txt");
        for (String[] data : potionData) {
            itemsForSale.add(new Potion(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), data[4]));
        }

        List<String[]> fireSpellData = DataLoader.readData("FireSpells.txt");
        for (String[] data : fireSpellData) {
            itemsForSale.add(new Spell(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), MonstersAndHeroesGameConstants.SPELL_TYPE_FIRE));
        }

        List<String[]> iceSpellData = DataLoader.readData("IceSpells.txt");
        for (String[] data : iceSpellData) {
            itemsForSale.add(new Spell(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), MonstersAndHeroesGameConstants.SPELL_TYPE_ICE));
        }

        List<String[]> lightningSpellData = DataLoader.readData("LightningSpells.txt");
        for (String[] data : lightningSpellData) {
            itemsForSale.add(new Spell(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), MonstersAndHeroesGameConstants.SPELL_TYPE_LIGHTNING));
        }
    }

    /**
     * Allows the hero party to enter the market and perform actions like buying and selling.
     * @param party The list of heroes in the party.
     */
    public void enter(List<Hero> party) {
        MarketDisplay.marketWelcome();
        boolean marketOpen = true;
        
        while (marketOpen) {
            // Show market menu with first hero's gold (or party leader)
            Hero displayHero = party.get(0);
            MarketDisplay.showMarketMenu(displayHero);
            
            int categoryChoice = Input.getMarketCategoryChoice();
            
            switch (categoryChoice) {
                case 1: // Weapons
                    showCategoryAndBuy(party, getItemsByType(Weapon.class), "WEAPONS");
                    break;
                case 2: // Armor
                    showCategoryAndBuy(party, getItemsByType(Armor.class), "ARMOR");
                    break;
                case 3: // Potions
                    showCategoryAndBuy(party, getItemsByType(Potion.class), "POTIONS");
                    break;
                case 4: // Spells
                    showCategoryAndBuy(party, getItemsByType(Spell.class), "SPELLS");
                    break;
                case 5: // View All Items
                    showCategoryAndBuy(party, itemsForSale, "ALL ITEMS");
                    break;
                case 6: // Sell Items
                    sellFromParty(party);
                    break;
                case 0: // Exit Market
                    marketOpen = false;
                    System.out.println("Leaving the market...");
                    break;
                default:
                    error.invalidMenuInput();
                    break;
            }
        }
    }
    
    /**
     * Gets items of a specific type from the market.
     */
    private <T extends Item> List<Item> getItemsByType(Class<T> itemType) {
        List<Item> filtered = new ArrayList<>();
        for (Item item : itemsForSale) {
            if (itemType.isInstance(item)) {
                filtered.add(item);
            }
        }
        return filtered;
    }
    
    /**
     * Shows a category and handles buying from that category.
     */
    private void showCategoryAndBuy(List<Hero> party, List<Item> categoryItems, String categoryName) {
        if (categoryItems.isEmpty()) {
            System.out.println("No items available in this category.");
            return;
        }
        
        // Show items with first hero's gold for display
        Hero displayHero = party.get(0);
        MarketDisplay.showCategoryItems(categoryItems, categoryName, displayHero);
        
        int itemChoice = Input.getMarketChoice(categoryItems.size());
        if (itemChoice < 0 || itemChoice >= categoryItems.size()) {
            Display.invalidSelection();
            return;
        }
        
        Item selectedItem = categoryItems.get(itemChoice);
        
        // Select which hero should buy the item
        int heroIndex = MarketDisplay.showHeroSelection(party);
        if (heroIndex == -1) {
            return; // Cancelled
        }
        
        Hero buyer = party.get(heroIndex);
        
        // Attempt purchase
        if (buyer.getGold() >= selectedItem.getPrice() && buyer.getLevel() >= selectedItem.getRequiredLevel()) {
            buyer.buyItem(selectedItem);
            // Items remain in market (not removed) - players can buy multiple copies
            MarketDisplay.buySuccess(buyer, selectedItem);
        } else {
            MarketDisplay.buyFail(buyer, selectedItem);
        }
    }
    
    /**
     * Handles selling items from any hero in the party.
     */
    private void sellFromParty(List<Hero> party) {
        // Select which hero is selling
        int heroIndex = MarketDisplay.showHeroSelection(party);
        if (heroIndex == -1) {
            return; // Cancelled
        }
        
        Hero seller = party.get(heroIndex);
        
        if (seller.getInventory().isEmpty()) {
            System.out.println(seller.getName() + " has no items to sell.");
            return;
        }
        
        MarketDisplay.showHeroInventoryForSell(seller);
        int itemChoice = Input.getMarketChoice(seller.getInventory().size());
        
        if (itemChoice < 0 || itemChoice >= seller.getInventory().size()) {
            Display.invalidSelection();
            return;
        }
        
        Item item = seller.getInventory().get(itemChoice);
        seller.sellItem(item);
        itemsForSale.add(item);
        MarketDisplay.sellSuccess(seller, item);
    }

}
