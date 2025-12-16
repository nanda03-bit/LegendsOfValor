/**
 * Filename: BaseGame.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Abstract base class for game implementations, containing common methods
 * shared between MonstersAndHeroes and LegendsOfValor.
 */

package Games;

import Player.Heroes.*;
import Utilities.*;
import Display.MonstersAndHeroes.Display;
import Display.MonstersAndHeroes.Input;
import ErrorMessages.PrintErrorMessages;

import java.util.*;

/**
 * Abstract base class for game implementations.
 * Provides common functionality for hero creation and game initialization.
 */
public abstract class BaseGame {
    protected List<Hero> heroes;
    protected PrintErrorMessages error = new PrintErrorMessages();

    /**
     * Starts the game. Must be implemented by subclasses.
     */
    public abstract void start();

    /**
     * Initializes the game. Must be implemented by subclasses.
     */
    protected abstract void initialize();

    /**
     * Creates the heroes for the game.
     * @return The list of heroes created.
     */
    protected List<Hero> createHeroes() {
        if (heroes == null) {
            heroes = new ArrayList<>();
        }
        
        int numHeroes = getNumHeroes();
        Set<String> selectedHeroNames = new HashSet<>();

        for (int i = 0; i < numHeroes; i++) {
            System.out.println();
            System.out.println("--- Hero " + (i + 1) + " ---");
            Display.selectHeroType(i + 1);
            int heroType = Input.getHeroType();
            Hero hero = createHero(heroType, selectedHeroNames);

            if (hero != null) {
                heroes.add(hero);
                selectedHeroNames.add(hero.getName());
                System.out.println("Added " + hero.getName() + " to your team!");
            } else {
                error.failedCreateHero();
                i--; // Retry
            }
        }

        return heroes;
    }

    /**
     * Gets the number of heroes to create. Must be implemented by subclasses.
     * @return The number of heroes to create.
     */
    protected abstract int getNumHeroes();

    /**
     * Creates a hero of a specific type based on player input.
     * @param heroType The type of hero to create.
     * @param selectedHeroNames Set of already selected hero names to prevent duplicates.
     * @return The created hero, or null if creation failed.
     */
    protected Hero createHero(int heroType, Set<String> selectedHeroNames) {
        List<String[]> warriorData = DataLoader.readData("Warriors.txt");
        List<String[]> sorcererData = DataLoader.readData("Sorcerers.txt");
        List<String[]> paladinData = DataLoader.readData("Paladins.txt");

        switch (heroType) {
            case MonstersAndHeroesGameConstants.WARRIOR_TYPE:
                return createWarrior(warriorData, selectedHeroNames);
            case MonstersAndHeroesGameConstants.SORCERER_TYPE:
                return createSorcerer(sorcererData, selectedHeroNames);
            case MonstersAndHeroesGameConstants.PALADIN_TYPE:
                return createPaladin(paladinData, selectedHeroNames);
            default:
                return null;
        }
    }

    /**
     * Creates a warrior hero.
     * @param warriorData List of available warrior data.
     * @param selectedHeroNames Set of already selected hero names.
     * @return The created warrior, or null if creation failed.
     */
    protected Hero createWarrior(List<String[]> warriorData, Set<String> selectedHeroNames) {
        if (warriorData.isEmpty()) {
            error.noWarriorHero();
            return null;
        }

        List<String[]> availableWarriors = filterAvailableHeroes(warriorData, selectedHeroNames);
        if (availableWarriors.isEmpty()) {
            System.out.println("All warriors have been selected! Choose a different type.");
            return null;
        }

        Display.selectHero(availableWarriors);
        int warriorChoice = Input.getHeroChoice(availableWarriors.size());
        String[] warrior = availableWarriors.get(warriorChoice);

        if (selectedHeroNames.contains(warrior[0])) {
            System.out.println(warrior[0] + " is already in your team! Choose a different hero.");
            return null;
        }

        return new Warrior(warrior[0], Integer.parseInt(warrior[1]),
                Integer.parseInt(warrior[2]), Integer.parseInt(warrior[3]),
                Integer.parseInt(warrior[4]), Integer.parseInt(warrior[5]),
                Integer.parseInt(warrior[6]));
    }

    /**
     * Creates a sorcerer hero.
     * @param sorcererData List of available sorcerer data.
     * @param selectedHeroNames Set of already selected hero names.
     * @return The created sorcerer, or null if creation failed.
     */
    protected Hero createSorcerer(List<String[]> sorcererData, Set<String> selectedHeroNames) {
        if (sorcererData.isEmpty()) {
            error.noSorcererHero();
            return null;
        }

        List<String[]> availableSorcerers = filterAvailableHeroes(sorcererData, selectedHeroNames);
        if (availableSorcerers.isEmpty()) {
            System.out.println("All sorcerers have been selected! Choose a different type.");
            return null;
        }

        Display.selectHero(availableSorcerers);
        int sorcererChoice = Input.getHeroChoice(availableSorcerers.size());
        String[] sorcerer = availableSorcerers.get(sorcererChoice);

        if (selectedHeroNames.contains(sorcerer[0])) {
            System.out.println(sorcerer[0] + " is already in your team! Choose a different hero.");
            return null;
        }

        return new Sorcerer(sorcerer[0], Integer.parseInt(sorcerer[1]),
                Integer.parseInt(sorcerer[2]), Integer.parseInt(sorcerer[3]),
                Integer.parseInt(sorcerer[4]), Integer.parseInt(sorcerer[5]),
                Integer.parseInt(sorcerer[6]));
    }

    /**
     * Creates a paladin hero.
     * @param paladinData List of available paladin data.
     * @param selectedHeroNames Set of already selected hero names.
     * @return The created paladin, or null if creation failed.
     */
    protected Hero createPaladin(List<String[]> paladinData, Set<String> selectedHeroNames) {
        if (paladinData.isEmpty()) {
            error.noPaladinHero();
            return null;
        }

        List<String[]> availablePaladins = filterAvailableHeroes(paladinData, selectedHeroNames);
        if (availablePaladins.isEmpty()) {
            System.out.println("All paladins have been selected! Choose a different type.");
            return null;
        }

        Display.selectHero(availablePaladins);
        int paladinChoice = Input.getHeroChoice(availablePaladins.size());
        String[] paladin = availablePaladins.get(paladinChoice);

        if (selectedHeroNames.contains(paladin[0])) {
            System.out.println(paladin[0] + " is already in your team! Choose a different hero.");
            return null;
        }

        return new Paladin(paladin[0], Integer.parseInt(paladin[1]),
                Integer.parseInt(paladin[2]), Integer.parseInt(paladin[3]),
                Integer.parseInt(paladin[4]), Integer.parseInt(paladin[5]),
                Integer.parseInt(paladin[6]));
    }

    /**
     * Filters out already selected heroes from the available hero data.
     * @param heroData List of hero data to filter.
     * @param selectedHeroNames Set of already selected hero names.
     * @return Filtered list of available heroes.
     */
    protected List<String[]> filterAvailableHeroes(List<String[]> heroData, Set<String> selectedHeroNames) {
        List<String[]> available = new ArrayList<>();
        for (String[] hero : heroData) {
            if (!selectedHeroNames.contains(hero[0])) {
                available.add(hero);
            }
        }
        return available;
    }

    /**
     * Gets the highest level among all heroes.
     * @return The highest hero level.
     */
    protected int getHighestHeroLevel() {
        if (heroes == null || heroes.isEmpty()) {
            return 1;
        }
        int maxLevel = 1;
        for (Hero hero : heroes) {
            if (hero.getLevel() > maxLevel) {
                maxLevel = hero.getLevel();
            }
        }
        return maxLevel;
    }

    /**
     * Checks if the game is over. Must be implemented by subclasses.
     * @return true if the game is over, false otherwise.
     */
    protected abstract boolean isGameOver();

    /**
     * Main game loop. Must be implemented by subclasses.
     */
    protected abstract void gameLoop();
}
