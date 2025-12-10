package Display;

import Color.Color;
import ErrorMessages.PrintErrorMessages;
import Utilities.GameConstants;
import java.util.*;


public class Input {
    private static Scanner scanner = new Scanner(System.in);
    private static PrintErrorMessages error = new PrintErrorMessages();
    private static Color c = new Color();

    /**
     * Gets the desired party size from the player.
     * @return The party size.
     */
    public static int getPartySize(){
        while (true){
            try{
                System.out.print(c.Yellow + "Choose the number of warriors who will face the shadows beside you (" + GameConstants.MIN_PARTY_SIZE + "-" + GameConstants.MAX_PARTY_SIZE + "):    "+ c.Reset);
                int size = scanner.nextInt();
                if (size >= GameConstants.MIN_PARTY_SIZE && size <= GameConstants.MAX_PARTY_SIZE) {
                    return size;
                }
                else{
                    error.invalidSizeInput();
                    System.out.println("Number of warriors must be between " + GameConstants.MIN_PARTY_SIZE + " and " + GameConstants.MAX_PARTY_SIZE + ".");
                }
            }

            catch (InputMismatchException e){
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    /**
     * Gets the hero type selection from the player.
     * @return The hero type.
     */
    public static int getHeroType() {
        while (true) {
            try {
                System.out.print(c.Yellow + "Enter hero type (" + GameConstants.MIN_HERO_TYPE + "-" + GameConstants.MAX_HERO_TYPE + "): " + c.Reset);
                int type = scanner.nextInt();
                if (type >= GameConstants.MIN_HERO_TYPE && type <= GameConstants.MAX_HERO_TYPE) {
                    return type;
                }
                else {
                    error.invalidActionInput();
                    System.out.println("Hero type must be " + GameConstants.MIN_HERO_TYPE + ", " + (GameConstants.MIN_HERO_TYPE + 1) + ", or " + GameConstants.MAX_HERO_TYPE + ".");
                }
            }

            catch (InputMismatchException e) {
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    /**
     * Gets the hero selection from the player.
     * @param max The maximum number of choices.
     * @return The chosen hero index.
     */
    public static int getHeroChoice(int max) {
        while (true) {
            try {
                System.out.print(c.Yellow + "Enter your choice (1-" + max + "): "+ c.Reset);
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= max) {
                    return choice - 1;
                }
                else{
                    error.invalidActionInput();
                    System.out.println("Choice must be between 1 and " + max + ".");
                }
            }
            catch (InputMismatchException e){
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    /**
     * Gets the main game action from the player (e.g., move, open map, quit).
     * @return The chosen action character.
     */
    public static char getAction() {
        while (true) {
            try {
                System.out.println(c.Yellow + c.Bold + "Choose your action:" + c.Reset);
                System.out.println(c.Yellow + "   [W] Up   |   [A] Left   |   [S] Down   |   [D] Right   |   [M] Market   |   [I] Inventory   |   [Q] Quit" + c.Reset);
                System.out.println();
                System.out.print(c.Bold + "Enter action: " + c.Reset);
                String input = scanner.next().toLowerCase();
                if (input.length() > 0) {
                    char action = input.charAt(0);
                    if (action == 'w' || action == 'a' || action == 's' || action == 'd' || action == 'm' || action == 'i' || action == 'q') {
                        return action;
                    }
                }
                error.invalidActionInput();
            }
            catch (Exception e) {
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    /**
     * Gets the battle action from the player.
     * @return The chosen battle action character.
     */
    public static char getBattleAction() {
        while (true) {
            try {
                System.out.println(c.Yellow + c.Bold + "Choose your next move:" + c.Reset);
                System.out.println(c.Yellow + "      [A] Attack an enemy      |      [S] Cast Spell      |      [P] Use Potion      |      [E] Equip Item      |      [I] View Info      |      [U] Surrender      " + c.Reset);
                System.out.print( c.Bold+ "Your action: " + c.Reset);
                String input = scanner.next().toUpperCase();
                if (input.length() > 0){
                    char action = input.charAt(0);
                    if (action == 'A' || action == 'S' || action == 'P' || action == 'E' || action == 'I' || action == 'U') {
                        return action;
                    }
                }
                error.invalidActionInput();
            }
            catch (Exception e){
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    /**
     * Gets the market action from the player.
     * @return The chosen market action character.
     */
    public static char getMarketAction() {
        while (true) {
            try {
                System.out.println("Choose a market action:");
                System.out.println("   B  - Buy Items     (Purchase weapons, armor, spells, or potions)");
                System.out.println("   S  - Sell Items    (Sell items from your inventory for gold)");
                System.out.println("   L  - Leave Market  (Return to your adventure)");
                System.out.print("Your action: ");
                String input = scanner.next().toUpperCase();
                if (input.length() > 0) {
                    char action = input.charAt(0);
                    if (action == 'B' || action == 'S' || action == 'L') {
                        return action;
                    }
                }
                error.invalidActionInput();
            }
            catch (Exception e){
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    /**
     * Gets the player's choice in the market.
     * @param max The maximum number of choices.
     * @return The chosen item index, or -1 to go back.
     */
    public static int getMarketChoice(int max) {
        while (true){
            try{
                System.out.print("Enter your choice (1-" + max + ", or 0 to go back): ");
                String input = scanner.next();
                int choice = Integer.parseInt(input);
                if (choice == 0) {
                    return -1; // Special value to indicate going back
                }
                if (choice >= 1 && choice <= max){
                    return choice - 1;
                }
                else{
                    error.invalidActionInput();
                    System.out.println("Choice must be between 1 and " + max + ".");
                }
            }
            catch (NumberFormatException e) {
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    /**
     * Gets the desired world size from the player.
     * @return The world size.
     */
    public static int getWorldSize() {
        while (true) {
            try {
                System.out.print("Your Realm size can be between (" + GameConstants.MIN_WORLD_SIZE + "-" + GameConstants.MAX_WORLD_SIZE + "): ");
                int size = scanner.nextInt();
                System.out.println();
                if (size >= GameConstants.MIN_WORLD_SIZE && size <= GameConstants.MAX_WORLD_SIZE) {
                    return size;
                }
                else {
                    error.invalidActionInput();
                    System.out.println("World size must be between " + GameConstants.MIN_WORLD_SIZE + " and " + GameConstants.MAX_WORLD_SIZE + ".");
                }
            }
            catch (InputMismatchException e) {
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    /**
     * Gets the player's target selection in battle.
     * @param max The maximum number of targets.
     * @return The chosen target index.
     */
    public static int getTargetChoice(int max) {
        while (true) {
            try{
                System.out.print(c.Yellow + "Select target (1-" + max + "): " + c.Reset);
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= max) {
                    return choice - 1;
                }
                else{
                    error.invalidActionInput();
                    System.out.println("Target must be between 1 and " + max + ".");
                }
            }
            catch (InputMismatchException e){
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    /**
     * Gets the player's spell selection in battle.
     * @param max The maximum number of spells.
     * @return The chosen spell index.
     */
    public static int getSpellChoice(int max){
        while (true){
            try{
                System.out.print(c.Yellow + "Select spell (1-" + max + "): " + c.Reset);
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= max) {
                    return choice - 1;
                }
                else{
                    error.invalidActionInput();
                    System.out.println("Spell must be between 1 and " + max + ".");
                }
            }
            catch (InputMismatchException e){
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    /**
     * Gets the player's potion selection in battle.
     * @param max The maximum number of potions.
     * @return The chosen potion index.
     */
    public static int getPotionChoice(int max) {
        while (true) {
            try {
                System.out.print(c.Yellow + "Select potion (1-" + max + "): " + c.Reset);
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= max) {
                    return choice - 1;
                }
                else{
                    error.invalidActionInput();
                    System.out.println("Potion must be between 1 and " + max + ".");
                }
            }
            catch (InputMismatchException e) {
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    /**
     * Gets the player's item selection for equipping.
     * @param max The maximum number of items.
     * @return The chosen item index.
     */
    public static int getEquipChoice(int max) {
        while (true){
            try{
                System.out.print(c.Yellow + "Choose the item you want to equip from your inventory (enter a number between 1 and " + max + "): " + c.Reset);
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= max){
                    return choice - 1;
                }
                else{
                    error.invalidActionInput();
                    System.out.println("Item must be between 1 and " + max + ".");
                }
            }
            catch (InputMismatchException e){
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    public static int getMarketCategoryChoice() {
        while (true) {
            try {
                System.out.print(c.Yellow + "Enter your choice (0-6): " + c.Reset);
                int choice = scanner.nextInt();
                if (choice >= 0 && choice <= 6) {
                    return choice;
                }
                else {
                    error.invalidActionInput();
                    System.out.println("Choice must be between 0 and 6.");
                }
            }
            catch (InputMismatchException e) {
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

    public static int getHeroSelection(int partySize) {
        while (true) {
            try {
                System.out.print(c.Yellow + "Select hero (1-" + partySize + ", 0 to cancel): " + c.Reset);
                int choice = scanner.nextInt();
                if (choice == 0) {
                    return -1; // Cancel
                }
                if (choice >= 1 && choice <= partySize) {
                    return choice - 1;
                }
                else {
                    error.invalidActionInput();
                    System.out.println("Hero must be between 1 and " + partySize + ", or 0 to cancel.");
                }
            }
            catch (InputMismatchException e) {
                error.invalidInput();
                scanner.nextLine();
            }
        }
    }

}

