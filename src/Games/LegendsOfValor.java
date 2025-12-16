package Games;

import java.util.*;

import Battle.ValorBattle;
import Player.Heroes.*;
import Player.HeroTeam;
import Utilities.*;
import board.valor.*;
import Display.Valor.*;
import Display.MonstersAndHeroes.*;
import Display.Statistics.StatisticsDisplay;
import ErrorMessages.PrintErrorMessages;
import Factories.MonsterFactory;
import Player.Monsters.Monster;
import Color.Color;

/**
 * Main game class for Legends of Valor.
 * Extends RPGGame to provide common game structure.
 * Manages game initialization, hero selection, lane assignment, and the main game loop.
 */
public class LegendsOfValor extends RPGGame {
    private ValorBoard board;
    private List<Integer> heroLanes;
    private ValorBattle battleSystem;
    private int difficulty;
    private PrintErrorMessages error = new PrintErrorMessages();
    private Color c = new Color();

    /**
     * Constructor for LegendsOfValor.
     */
    public LegendsOfValor() {
        super("Legends of Valor");
    }

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
        } catch (Exception e) {
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
        heroTeam = new HeroTeam("Valor Heroes", ValorGameConstants.NUM_HEROES);
        heroLanes = new ArrayList<>();

        // Get difficulty
        difficulty = ValorInput.getDifficulty();

        // Create and assign heroes to lanes
        createHeroes();
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
     * Creates the 3 heroes for the game with duplicate prevention.
     */
    @Override
    protected void createHeroes() {
        System.out.println();
        System.out.println(c.Cyan + c.Bold + "═══ SELECT YOUR HEROES ═══" + c.Reset);
        System.out.println(c.Yellow + "You must select 3 heroes for your team." + c.Reset);
        System.out.println();

        List<String[]> warriorData = DataLoader.readData("Warriors.txt");
        List<String[]> sorcererData = DataLoader.readData("Sorcerers.txt");
        List<String[]> paladinData = DataLoader.readData("Paladins.txt");

        // Track selected hero names to prevent duplicates
        Set<String> selectedHeroNames = new HashSet<>();

        for (int i = 0; i < ValorGameConstants.NUM_HEROES; i++) {
            System.out.println();
            System.out.println(c.Cyan + "--- Hero " + (i + 1) + " ---" + c.Reset);
            Display.selectHeroType(i + 1);
            int heroType = Input.getHeroType();
            Hero hero = createHero(heroType, warriorData, sorcererData, paladinData, selectedHeroNames);

            if (hero != null) {
                heroes.add(hero);
                heroTeam.addHero(hero);
                selectedHeroNames.add(hero.getName());
                System.out.println(c.Green + "Added " + hero.getName() + " to your team!" + c.Reset);
            } else {
                error.failedCreateHero();
                i--; // Retry
            }
        }

        System.out.println();
        System.out.println(c.Green + "Your team is ready!" + c.Reset);
    }

    /**
     * Creates a hero of a specific type with duplicate checking.
     */
    private Hero createHero(int heroType, List<String[]> warriorData,
                            List<String[]> sorcererData, List<String[]> paladinData,
                            Set<String> selectedHeroNames) {
        switch (heroType) {
            case MonstersAndHeroesGameConstants.WARRIOR_TYPE:
                if (warriorData.isEmpty()) {
                    error.noWarriorHero();
                    return null;
                }

                // Filter out already selected heroes
                List<String[]> availableWarriors = new ArrayList<>();
                for (String[] warrior : warriorData) {
                    if (!selectedHeroNames.contains(warrior[0])) {
                        availableWarriors.add(warrior);
                    }
                }

                if (availableWarriors.isEmpty()) {
                    System.out.println(c.Red + "All warriors have been selected! Choose a different type." + c.Reset);
                    return null;
                }

                Display.selectHero(availableWarriors);
                int warriorChoice = Input.getHeroChoice(availableWarriors.size());
                String[] warrior = availableWarriors.get(warriorChoice);

                return new Warrior(warrior[0], Integer.parseInt(warrior[1]),
                        Integer.parseInt(warrior[2]), Integer.parseInt(warrior[3]),
                        Integer.parseInt(warrior[4]), Integer.parseInt(warrior[5]),
                        Integer.parseInt(warrior[6]));

            case MonstersAndHeroesGameConstants.SORCERER_TYPE:
                if (sorcererData.isEmpty()) {
                    error.noSorcererHero();
                    return null;
                }

                // Filter out already selected heroes
                List<String[]> availableSorcerers = new ArrayList<>();
                for (String[] sorcerer : sorcererData) {
                    if (!selectedHeroNames.contains(sorcerer[0])) {
                        availableSorcerers.add(sorcerer);
                    }
                }

                if (availableSorcerers.isEmpty()) {
                    System.out.println(c.Red + "All sorcerers have been selected! Choose a different type." + c.Reset);
                    return null;
                }

                Display.selectHero(availableSorcerers);
                int sorcererChoice = Input.getHeroChoice(availableSorcerers.size());
                String[] sorcerer = availableSorcerers.get(sorcererChoice);

                return new Sorcerer(sorcerer[0], Integer.parseInt(sorcerer[1]),
                        Integer.parseInt(sorcerer[2]), Integer.parseInt(sorcerer[3]),
                        Integer.parseInt(sorcerer[4]), Integer.parseInt(sorcerer[5]),
                        Integer.parseInt(sorcerer[6]));

            case MonstersAndHeroesGameConstants.PALADIN_TYPE:
                if (paladinData.isEmpty()) {
                    error.noPaladinHero();
                    return null;
                }

                // Filter out already selected heroes
                List<String[]> availablePaladins = new ArrayList<>();
                for (String[] paladin : paladinData) {
                    if (!selectedHeroNames.contains(paladin[0])) {
                        availablePaladins.add(paladin);
                    }
                }

                if (availablePaladins.isEmpty()) {
                    System.out.println(c.Red + "All paladins have been selected! Choose a different type." + c.Reset);
                    return null;
                }

                Display.selectHero(availablePaladins);
                int paladinChoice = Input.getHeroChoice(availablePaladins.size());
                String[] paladin = availablePaladins.get(paladinChoice);

                return new Paladin(paladin[0], Integer.parseInt(paladin[1]),
                        Integer.parseInt(paladin[2]), Integer.parseInt(paladin[3]),
                        Integer.parseInt(paladin[4]), Integer.parseInt(paladin[5]),
                        Integer.parseInt(paladin[6]));

            default:
                return null;
        }
    }

    /**
     * Assigns each hero to a unique lane.
     */
    private void assignHeroesToLanes() {
        System.out.println();
        System.out.println(c.Cyan + c.Bold + "═══ ASSIGN HEROES TO LANES ═══" + c.Reset);
        System.out.println(c.Yellow + "Each hero must be assigned to a different lane." + c.Reset);
        System.out.println();

        Set<Integer> usedLanes = new HashSet<>();

        for (int i = 0; i < heroes.size(); i++) {
            Hero hero = heroes.get(i);
            System.out.println(c.Green + "Assigning " + hero.getName() + ":" + c.Reset);

            int lane = ValorInput.getLaneSelection(i + 1, usedLanes);
            heroLanes.add(lane);
            usedLanes.add(lane);

            // Spawn hero on the board
            board.spawnHero(hero, i + 1, lane);

            String laneName = (lane == 0) ? "Top" : (lane == 1) ? "Middle" : "Bottom";
            System.out.println(c.Green + hero.getName() + " assigned to " + laneName + " Lane!" + c.Reset);
            System.out.println();
        }
    }

    /**
     * Spawns the initial monsters (one per lane).
     */
    private void spawnInitialMonsters() {
        int monsterLevel = getHighestHeroLevel();

        List<String[]> dragonData = DataLoader.readData("Dragons.txt");
        List<String[]> exoskeletonData = DataLoader.readData("Exoskeletons.txt");
        List<String[]> spiritData = DataLoader.readData("Spirits.txt");

        System.out.println();
        System.out.println(c.Red + "Spawning initial monsters..." + c.Reset);

        for (int lane = 0; lane < ValorGameConstants.NUM_LANES; lane++) {
            Monster monster = createRandomMonster(dragonData, exoskeletonData, spiritData, monsterLevel);
            board.spawnMonster(monster, lane + 1, lane);

            String laneName = (lane == 0) ? "Top" : (lane == 1) ? "Middle" : "Bottom";
            System.out.println(c.Red + monster.getName() + " spawned in " + laneName + " Lane!" + c.Reset);
        }
    }

    /**
     * Creates a random monster.
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
     * Gets the highest level among heroes.
     */
    private int getHighestHeroLevel() {
        int maxLevel = 1;
        for (Hero hero : heroes) {
            if (hero.getLevel() > maxLevel) {
                maxLevel = hero.getLevel();
            }
        }
        return maxLevel;
    }

    /**
     * The main game loop - executes rounds until game ends.
     */
    @Override
    protected void gameLoop() {
        System.out.println(c.Cyan + "Starting game loop..." + c.Reset);
        while (!battleSystem.isGameOver()) {
            boolean continueGame = battleSystem.executeRound();

            if (!continueGame) {
                break;
            }
        }

        System.out.println(c.Cyan + "Game loop ended." + c.Reset);
    }

    /**
     * Ends the game and displays the final result.
     * Shows stats on both victory AND defeat.
     */
    @Override
    protected void endGame() {
        System.out.println();

        if (battleSystem.didHeroesWin()) {
            ValorDisplay.victory();
        } else {
            ValorDisplay.defeat();
        }

        // Always show stats, not just on victory
        displayFinalStats();

        ValorDisplay.quit();
    }

    /**
     * Displays final statistics for all heroes.
     */
    @Override
    protected void displayFinalStats() {
        System.out.println(c.Cyan + c.Bold + "═══════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println(c.Cyan + c.Bold + "                      FINAL STATISTICS                         " + c.Reset);
        System.out.println(c.Cyan + c.Bold + "═══════════════════════════════════════════════════════════════" + c.Reset);

        for (int i = 0; i < heroes.size(); i++) {
            Hero hero = heroes.get(i);

            String nameLine = " HERO " + (i + 1) + ": " + hero.getName();
            String statsLine = " Level: " + hero.getLevel() + "        Experience: " + hero.getExperience() + "         Gold: " + hero.getGold();
            String hpMpLine = " HP: " + hero.getHp() + "/" + hero.getMaxHp() + "      MP: " + hero.getMp() + "/" + hero.getMaxMp();
            String attrLine = " Strength: " + hero.getStrength() + "     Dexterity: " + hero.getDexterity() + "     Agility: " + hero.getAgility();

            System.out.println(c.Green + "┌─────────────────────────────────────────────────────────────┐" + c.Reset);
            System.out.println(c.Green + "│" + c.Bold + String.format("%-61s", nameLine) + c.Reset + c.Green + "│" + c.Reset);
            System.out.println(c.Green + "├─────────────────────────────────────────────────────────────┤" + c.Reset);
            System.out.println(c.Green + "│" + c.Reset + String.format("%-61s", statsLine) + c.Green + "│" + c.Reset);
            System.out.println(c.Green + "│" + c.Reset + String.format("%-61s", hpMpLine) + c.Green + "│" + c.Reset);
            System.out.println(c.Green + "│" + c.Reset + String.format("%-61s", attrLine) + c.Green + "│" + c.Reset);
            System.out.println(c.Green + "└─────────────────────────────────────────────────────────────┘" + c.Reset);
        }

        System.out.println();
        System.out.println(c.Yellow + "Game completed in " + battleSystem.getCurrentRound() + " rounds!" + c.Reset);
        System.out.println();
    }
}
