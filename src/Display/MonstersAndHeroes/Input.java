package Display.MonstersAndHeroes;

import Color.Color;
import ErrorMessages.PrintErrorMessages;
import Utilities.MonstersAndHeroesGameConstants;
import Utilities.ScannerUtil;
import java.util.*;

public class Input {
    private static PrintErrorMessages error = new PrintErrorMessages();
    private static Color c = new Color();

    /**
     * Gets the scanner instance.
     */
    private static Scanner getScanner() {
        return ScannerUtil.getScanner();
    }

    public static int getPartySize(){
        while (true){
            try{
                System.out.print(c.Yellow + "Choose the number of warriors who will face the shadows beside you (" + MonstersAndHeroesGameConstants.MIN_PARTY_SIZE + "-" + MonstersAndHeroesGameConstants.MAX_PARTY_SIZE + "):    "+ c.Reset);
                int size = getScanner().nextInt();
                if (size >= MonstersAndHeroesGameConstants.MIN_PARTY_SIZE && size <= MonstersAndHeroesGameConstants.MAX_PARTY_SIZE) {
                    return size;
                }
                else{
                    error.invalidSizeInput();
                    System.out.println("Number of warriors must be between " + MonstersAndHeroesGameConstants.MIN_PARTY_SIZE + " and " + MonstersAndHeroesGameConstants.MAX_PARTY_SIZE + ".");
                }
            }
            catch (InputMismatchException e){
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    public static int getHeroType() {
        while (true) {
            try {
                System.out.print(c.Yellow + "Enter hero type (" + MonstersAndHeroesGameConstants.MIN_HERO_TYPE + "-" + MonstersAndHeroesGameConstants.MAX_HERO_TYPE + "): " + c.Reset);
                int type = getScanner().nextInt();
                if (type >= MonstersAndHeroesGameConstants.MIN_HERO_TYPE && type <= MonstersAndHeroesGameConstants.MAX_HERO_TYPE) {
                    return type;
                }
                else {
                    error.invalidActionInput();
                    System.out.println("Hero type must be " + MonstersAndHeroesGameConstants.MIN_HERO_TYPE + ", " + (MonstersAndHeroesGameConstants.MIN_HERO_TYPE + 1) + ", or " + MonstersAndHeroesGameConstants.MAX_HERO_TYPE + ".");
                }
            }
            catch (InputMismatchException e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    public static int getHeroChoice(int max) {
        while (true) {
            try {
                System.out.print(c.Yellow + "Enter your choice (1-" + max + "): "+ c.Reset);
                int choice = getScanner().nextInt();
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
                getScanner().nextLine();
            }
        }
    }

    public static char getAction() {
        while (true) {
            try {
                System.out.println(c.Yellow + c.Bold + "Choose your action:" + c.Reset);
                System.out.println(c.Yellow + "   [W] Up   |   [A] Left   |   [S] Down   |   [D] Right   |   [M] Market   |   [I] Inventory   |   [Q] Quit" + c.Reset);
                System.out.println();
                System.out.print(c.Bold + "Enter action: " + c.Reset);
                String input = getScanner().next().toLowerCase();
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
                getScanner().nextLine();
            }
        }
    }

    public static char getBattleAction() {
        while (true) {
            try {
                System.out.println(c.Yellow + c.Bold + "Choose your next move:" + c.Reset);
                System.out.println(c.Yellow + "      [A] Attack an enemy      |      [S] Cast Spell      |      [P] Use Potion      |      [E] Equip Item      |      [I] View Info      |      [U] Surrender      " + c.Reset);
                System.out.print( c.Bold+ "Your action: " + c.Reset);
                String input = getScanner().next().toUpperCase();
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
                getScanner().nextLine();
            }
        }
    }

    public static char getMarketAction() {
        while (true) {
            try {
                System.out.println("Choose a market action:");
                System.out.println("   B  - Buy Items     (Purchase weapons, armor, spells, or potions)");
                System.out.println("   S  - Sell Items    (Sell items from your inventory for gold)");
                System.out.println("   L  - Leave Market  (Return to your adventure)");
                System.out.print("Your action: ");
                String input = getScanner().next().toUpperCase();
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
                getScanner().nextLine();
            }
        }
    }

    public static int getMarketChoice(int max) {
        while (true){
            try{
                System.out.print("Enter your choice (1-" + max + ", or 0 to go back): ");
                String input = getScanner().next();
                int choice = Integer.parseInt(input);
                if (choice == 0) {
                    return -1;
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
                getScanner().nextLine();
            }
        }
    }

    public static int getWorldSize() {
        while (true) {
            try {
                System.out.print("Your Realm size can be between (" + MonstersAndHeroesGameConstants.MIN_WORLD_SIZE + "-" + MonstersAndHeroesGameConstants.MAX_WORLD_SIZE + "): ");
                int size = getScanner().nextInt();
                System.out.println();
                if (size >= MonstersAndHeroesGameConstants.MIN_WORLD_SIZE && size <= MonstersAndHeroesGameConstants.MAX_WORLD_SIZE) {
                    return size;
                }
                else {
                    error.invalidActionInput();
                    System.out.println("World size must be between " + MonstersAndHeroesGameConstants.MIN_WORLD_SIZE + " and " + MonstersAndHeroesGameConstants.MAX_WORLD_SIZE + ".");
                }
            }
            catch (InputMismatchException e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    public static int getTargetChoice(int max) {
        while (true) {
            try{
                System.out.print(c.Yellow + "Select target (1-" + max + "): " + c.Reset);
                int choice = getScanner().nextInt();
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
                getScanner().nextLine();
            }
        }
    }

    public static int getSpellChoice(int max){
        while (true){
            try{
                System.out.print(c.Yellow + "Select spell (1-" + max + "): " + c.Reset);
                int choice = getScanner().nextInt();
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
                getScanner().nextLine();
            }
        }
    }

    public static int getPotionChoice(int max) {
        while (true) {
            try {
                System.out.print(c.Yellow + "Select potion (1-" + max + "): " + c.Reset);
                int choice = getScanner().nextInt();
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
                getScanner().nextLine();
            }
        }
    }

    public static int getEquipChoice(int max) {
        while (true){
            try{
                System.out.print(c.Yellow + "Choose the item you want to equip from your inventory (enter a number between 1 and " + max + "): " + c.Reset);
                int choice = getScanner().nextInt();
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
                getScanner().nextLine();
            }
        }
    }

    /**
     * Gets the market category choice (0-7).
     * Options: 1-Weapons, 2-Armor, 3-Potions, 4-Spells, 5-All Items, 6-Sell, 7-Hero Info, 0-Exit
     */
    public static int getMarketCategoryChoice() {
        while (true) {
            try {
                System.out.print(c.Yellow + "Enter your choice (0-7): " + c.Reset);
                int choice = getScanner().nextInt();
                if (choice >= 0 && choice <= 7) {
                    return choice;
                }
                else {
                    error.invalidActionInput();
                    System.out.println("Choice must be between 0 and 7.");
                }
            }
            catch (InputMismatchException e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    public static int getHeroSelection(int partySize) {
        while (true) {
            try {
                System.out.print(c.Yellow + "Select hero (1-" + partySize + ", 0 to cancel): " + c.Reset);
                int choice = getScanner().nextInt();
                if (choice == 0) {
                    return -1;
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
                getScanner().nextLine();
            }
        }
    }
}
