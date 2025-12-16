/**
 * Filename: LegendsOfValor.java
 * Author: Gowrav
 * Date: 2025-Dec
 * Description: Main game class for Legends of Valor. Manages game initialization,
 * hero selection, lane assignment, and the main game loop.
 */

package Games;

import Player.Heroes.*;
import Battle.ValorBattle;
import board.valor.*;
import Display.MonstersAndHeroes.Display;
import Display.MonstersAndHeroes.Input;
import Display.Valor.ValorDisplay;
import Display.Valor.ValorInput;
import Display.Valor.ValorBoardDisplay;

import Utilities.*;
import ErrorMessages.PrintErrorMessages;
import Color.Color;
import Player.Monsters.Monster;
import Factories.MonsterFactory;

import java.util.*;

/**
 * Main game class for Legends of Valor.
 * Manages game initialization, hero selection, lane assignment, and the main game loop.
 */
public class LegendsOfValor extends BaseGame {
    private ValorBoard board;
    private List<Integer> heroLanes;
    private ValorBattle battleSystem;
    private int difficulty;
    private Color c = new Color();

    /**
     * Starts the Legends of Valor game.
     */
    @Override
    public void start() {
        try {
            ValorDisplay.welcome();
            ValorDisplay.instructions();

            initialize();
            gameLoop();

            endGame();
        }
        catch (Exception e) {
            System.out.println(c.Red + "An error occurred: " + e.getMessage() + c.Reset);
            e.printStackTrace();
        }
    }

    /**
     * Initializes the game world, heroes, lanes, and spawns initial monsters.
     */
    @Override
    protected void initialize() {
        // Create the game board
        board = new ValorBoard();
        heroes = new ArrayList<>();
        heroLanes = new ArrayList<>();

        // Get difficulty
        difficulty = ValorInput.getDifficulty();

        // Create and assign heroes to lanes
        System.out.println();
        System.out.println(c.Cyan + c.Bold + "═══ SELECT YOUR HEROES ═══" + c.Reset);
        System.out.println(c.Yellow + "You must select 3 heroes for your team." + c.Reset);
        System.out.println();
        createHeroes();
        System.out.println();
        System.out.println(c.Green + "Your team is ready!" + c.Reset);
        assignHeroesToLanes();

        // Spawn initial monsters
        spawnInitialMonsters();

        // Create the battle system
        battleSystem = new ValorBattle(board, heroes, heroLanes, difficulty);

        System.out.println();
        System.out.println(c.Green + "Game initialized! Heroes are at the bottom, monsters at the top." + c.Reset);
        System.out.println(c.Yellow + "Reach the monsters' Nexus (row 0) to win! Don't let monsters reach row 7!" + c.Reset);
        System.out.println();
    }

    /**
     * Gets the number of heroes to create.
     * @return The number of heroes to create.
     */
    @Override
    protected int getNumHeroes() {
        return ValorGameConstants.NUM_HEROES;
    }

    /**
     * Override createHeroes to add color formatting for Legends of Valor.
     */
    @Override
    protected List<Hero> createHeroes() {
        if (heroes == null) {
            heroes = new ArrayList<>();
        }
        
        // Print header with color formatting
        System.out.println();
        System.out.println(c.Cyan + c.Bold + "═══ SELECT YOUR HEROES ═══" + c.Reset);
        System.out.println(c.Yellow + "You must select 3 heroes for your team." + c.Reset);
        System.out.println();

        List<String[]> warriorData = DataLoader.readData("Warriors.txt");
        List<String[]> sorcererData = DataLoader.readData("Sorcerers.txt");
        List<String[]> paladinData = DataLoader.readData("Paladins.txt");

        // Track selected hero names to prevent duplicates
        Set<String> selectedHeroNames = new HashSet<>();

        for (int i = 0; i < getNumHeroes(); i++) {
            System.out.println();
            System.out.println(c.Cyan + "--- Hero " + (i + 1) + " ---" + c.Reset);
            Display.selectHeroType(i + 1);
            int heroType = Input.getHeroType();
            Hero hero = createHero(heroType, selectedHeroNames);

            if (hero != null) {
                heroes.add(hero);
                selectedHeroNames.add(hero.getName());  // Mark this hero as selected
                System.out.println(c.Green + "Added " + hero.getName() + " to your team!" + c.Reset);
            } else {
                error.failedCreateHero();
                i--; // Retry
            }
        }

        System.out.println();
        System.out.println(c.Green + "Your team is ready!" + c.Reset);
        return heroes;
    }

    /**
     * Override createWarrior to add color formatting for Legends of Valor.
     * Creates a warrior hero with duplicate checking.
     *
     * @param warriorData       List of available warrior data.
     * @param selectedHeroNames Set of already selected hero names to prevent duplicates.
     * @return The created warrior, or null if creation failed.
     */
    @Override
    protected Hero createWarrior(List<String[]> warriorData, Set<String> selectedHeroNames) {
        if (warriorData.isEmpty()) {
            error.noWarriorHero();
            return null;
        }

        List<String[]> availableWarriors = filterAvailableHeroes(warriorData, selectedHeroNames);
        if (availableWarriors.isEmpty()) {
            System.out.println(c.Red + "All warriors have been selected! Choose a different type." + c.Reset);
            return null;
        }

                Display.selectHero(availableWarriors);
                int warriorChoice = Input.getHeroChoice(availableWarriors.size());
                String[] warrior = availableWarriors.get(warriorChoice);

        if (selectedHeroNames.contains(warrior[0])) {
            System.out.println(c.Red + warrior[0] + " is already in your team! Choose a different hero." + c.Reset);
            return null;
        }

        return new Warrior(warrior[0], Integer.parseInt(warrior[1]),
                Integer.parseInt(warrior[2]), Integer.parseInt(warrior[3]),
                Integer.parseInt(warrior[4]), Integer.parseInt(warrior[5]),
                Integer.parseInt(warrior[6]));
    }

    /**
     * Override createSorcerer to add color formatting for Legends of Valor.
     */
    @Override
    protected Hero createSorcerer(List<String[]> sorcererData, Set<String> selectedHeroNames) {
        if (sorcererData.isEmpty()) {
            error.noSorcererHero();
            return null;
        }

        List<String[]> availableSorcerers = filterAvailableHeroes(sorcererData, selectedHeroNames);
        if (availableSorcerers.isEmpty()) {
            System.out.println(c.Red + "All sorcerers have been selected! Choose a different type." + c.Reset);
            return null;
        }

        Display.selectHero(availableSorcerers);
        int sorcererChoice = Input.getHeroChoice(availableSorcerers.size());
        String[] sorcerer = availableSorcerers.get(sorcererChoice);

                // Double-check for duplicates (safety check)
                if (selectedHeroNames.contains(sorcerer[0])) {
                    System.out.println(c.Red + sorcerer[0] + " is already in your team! Choose a different hero." + c.Reset);
                    return null;
                }

        return new Sorcerer(sorcerer[0], Integer.parseInt(sorcerer[1]),
                Integer.parseInt(sorcerer[2]), Integer.parseInt(sorcerer[3]),
                Integer.parseInt(sorcerer[4]), Integer.parseInt(sorcerer[5]),
                Integer.parseInt(sorcerer[6]));
    }

    /**
     * Override createPaladin to add color formatting for Legends of Valor.
     */
    @Override
    protected Hero createPaladin(List<String[]> paladinData, Set<String> selectedHeroNames) {
        if (paladinData.isEmpty()) {
            error.noPaladinHero();
            return null;
        }

        List<String[]> availablePaladins = filterAvailableHeroes(paladinData, selectedHeroNames);
        if (availablePaladins.isEmpty()) {
            System.out.println(c.Red + "All paladins have been selected! Choose a different type." + c.Reset);
            return null;
        }

                Display.selectHero(availablePaladins);
                int paladinChoice = Input.getHeroChoice(availablePaladins.size());
                String[] paladin = availablePaladins.get(paladinChoice);

                // Double-check for duplicates (safety check)
                if (selectedHeroNames.contains(paladin[0])) {
                    System.out.println(c.Red + paladin[0] + " is already in your team! Choose a different hero." + c.Reset);
                    return null;
                }

        return new Paladin(paladin[0], Integer.parseInt(paladin[1]),
                Integer.parseInt(paladin[2]), Integer.parseInt(paladin[3]),
                Integer.parseInt(paladin[4]), Integer.parseInt(paladin[5]),
                Integer.parseInt(paladin[6]));
    }

    /**
     * Assigns heroes to their designated lanes.
     */
    private void assignHeroesToLanes() {
        System.out.println();
        System.out.println(c.Yellow + "Assigning heroes to lanes..." + c.Reset);

        // Track which lanes have been used to prevent duplicates
        Set<Integer> usedLanes = new HashSet<>();

        for (int i = 0; i < heroes.size(); i++) {
            Hero hero = heroes.get(i);

            // Get lane selection with proper parameters: heroNum and usedLanes
            int lane = ValorInput.getLaneSelection(i + 1, usedLanes);

            heroLanes.add(lane);
            usedLanes.add(lane);  // Mark this lane as used

            // Spawn hero on the board
            board.spawnHero(hero, i + 1, lane);

            String laneName = (lane == 0) ? "Top" : (lane == 1) ? "Middle" : "Bottom";
            System.out.println(c.Green + hero.getName() + " assigned to " + laneName + " Lane!" + c.Reset);
        }
    }

    /**
     * Spawns initial monsters in each lane.
     */
    private void spawnInitialMonsters() {
        System.out.println();
        System.out.println(c.Red + "Monsters are spawning..." + c.Reset);

        int monsterLevel = getHighestHeroLevel();

        List<String[]> dragonData = DataLoader.readData("Dragons.txt");
        List<String[]> exoskeletonData = DataLoader.readData("Exoskeletons.txt");
        List<String[]> spiritData = DataLoader.readData("Spirits.txt");

        for (int lane = 0; lane < ValorGameConstants.NUM_LANES; lane++) {
            Monster monster = createRandomMonster(dragonData, exoskeletonData, spiritData, monsterLevel);
            board.spawnMonster(monster, lane + 1, lane);
            System.out.println(c.Red + "Monster " + monster.getName() + " spawned in Lane " + (lane + 1) + "!" + c.Reset);
        }

        System.out.println();
    }

    /**
     * Creates a random monster from available types.
     */
    private Monster createRandomMonster(List<String[]> dragons, List<String[]> exos,
                                        List<String[]> spirits, int level) {
        double random = Math.random();

        if (random < Percentages.DRAGON) {
            String[] data = dragons.get((int) (Math.random() * dragons.size()));
            return MonsterFactory.createMonster("Dragon", data, level);
        } else if (random < Percentages.DRAGON + Percentages.EXOSKELETON) {
            String[] data = exos.get((int) (Math.random() * exos.size()));
            return MonsterFactory.createMonster("Exoskeleton", data, level);
        } else {
            String[] data = spirits.get((int) (Math.random() * spirits.size()));
            return MonsterFactory.createMonster("Spirit", data, level);
        }
    }

    /**
     * Main game loop.
     */
    @Override
    protected void gameLoop() {
        System.out.println();
        System.out.println(c.Green + c.Bold + "═══ GAME START ═══" + c.Reset);
        System.out.println();

        while (!battleSystem.isGameOver()) {
            boolean continueGame = battleSystem.executeRound();

            if (!continueGame) {
                break;
            }
        }
    }

    /**
     * Checks if the game is over.
     * @return true if the game is over, false otherwise.
     */
    @Override
    protected boolean isGameOver() {
        return battleSystem.isGameOver();
    }

    /**
     * Handles the end of the game.
     */
    private void endGame() {
        System.out.println();
        System.out.println(c.Bold + "═══════════════════════════" + c.Reset);

        if (battleSystem.didHeroesWin()) {
            System.out.println(c.Green + c.Bold + "   🎉 VICTORY! 🎉   " + c.Reset);
            System.out.println(c.Green + "Your heroes have conquered the monsters' Nexus!" + c.Reset);
        } else {
            System.out.println(c.Red + c.Bold + "   ☠ DEFEAT ☠   " + c.Reset);
            System.out.println(c.Red + "The monsters have reached your Nexus..." + c.Reset);
        }

        System.out.println(c.Bold + "═══════════════════════════" + c.Reset);
        System.out.println();
        System.out.println(c.Yellow + "Game lasted " + battleSystem.getCurrentRound() + " rounds." + c.Reset);
        System.out.println(c.Yellow + "Thanks for playing Legends of Valor!" + c.Reset);
        System.out.println();
    }
}
