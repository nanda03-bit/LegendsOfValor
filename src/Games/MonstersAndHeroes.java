/**
 * Filename: MonstersAndHeroes.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-05
 * Description: Main class governing the Monsters and Heroes RPG gameplay, including movement, encounters, leveling, and map interactions.
 */

package Games;

import java.util.*;

import Battle.Battle;
import ErrorMessages.PrintErrorMessages;
import Player.Heroes.*;
import Player.HeroTeam;
import Utilities.*;
import Utilities.MonstersAndHeroesGameConstants;
import Market.Market;
import board.monstersandheroes.Board;
import Display.MonstersAndHeroes.*;
import Display.Statistics.StatisticsDisplay;
import Color.Color;

/**
 * Main game class for Monsters and Heroes.
 * Extends RPGGame to provide common game structure.
 */
public class MonstersAndHeroes extends RPGGame {
    private Board world;
    private List<Hero> party;
    private Market market;
    private static PrintErrorMessages error = new PrintErrorMessages();
    private Color c = new Color();
    private boolean gameEnded = false;

    /**
     * Constructor for MonstersAndHeroes.
     */
    public MonstersAndHeroes() {
        super("Monsters and Heroes");
    }

    /**
     * Starts the game.
     */
    @Override
    public void start() {
        Display.welcome();
        DisplayInstruction.welcomeInstructions();
        initialize();
        DisplayInstruction.instructions();
        gameLoop();
        endGame();
    }

    /**
     * Initializes the game world, party, and market.
     */
    @Override
    protected void initialize() {
        int worldSize = Input.getWorldSize();
        world = new Board(worldSize);
        DisplayInstruction.heroesInstructions();
        createHeroes();
        party = heroes; // For backward compatibility
    }

    /**
     * Creates the hero party based on player input.
     */
    @Override
    protected void createHeroes() {
        heroes = new ArrayList<>();
        heroTeam = new HeroTeam("Hero Party", 3);
        int partySize = Input.getPartySize();

        for (int i = 0; i < partySize; i++) {
            Display.selectHeroType(i + 1);
            int heroType = Input.getHeroType();
            Hero hero = createHero(heroType);
            if (hero != null) {
                heroes.add(hero);
                heroTeam.addHero(hero);
            } else {
                error.failedCreateHero();
                i--;
            }
        }
    }

    /**
     * Creates a hero of a specific type based on player input.
     *
     * @param heroType The type of hero to create.
     * @return The created hero, or null if creation failed.
     */
    private Hero createHero(int heroType) {
        List<String[]> warriorData = DataLoader.readData("Warriors.txt");
        List<String[]> sorcererData = DataLoader.readData("Sorcerers.txt");
        List<String[]> paladinData = DataLoader.readData("Paladins.txt");

        switch (heroType) {
            case MonstersAndHeroesGameConstants.WARRIOR_TYPE:
                if (warriorData.isEmpty()) {
                    error.noWarriorHero();
                    return null;
                }

                Display.selectHero(warriorData);
                int warriorChoice = Input.getHeroChoice(warriorData.size());
                String[] warrior = warriorData.get(warriorChoice);
                return new Warrior(warrior[0], Integer.parseInt(warrior[1]), Integer.parseInt(warrior[2]), Integer.parseInt(warrior[3]), Integer.parseInt(warrior[4]), Integer.parseInt(warrior[5]), Integer.parseInt(warrior[6]));
            case MonstersAndHeroesGameConstants.SORCERER_TYPE:
                if (sorcererData.isEmpty()) {
                    error.noSorcererHero();
                    return null;
                }
                Display.selectHero(sorcererData);
                int sorcererChoice = Input.getHeroChoice(sorcererData.size());
                String[] sorcerer = sorcererData.get(sorcererChoice);
                return new Sorcerer(sorcerer[0], Integer.parseInt(sorcerer[1]), Integer.parseInt(sorcerer[2]), Integer.parseInt(sorcerer[3]), Integer.parseInt(sorcerer[4]), Integer.parseInt(sorcerer[5]), Integer.parseInt(sorcerer[6]));
            case MonstersAndHeroesGameConstants.PALADIN_TYPE:
                if (paladinData.isEmpty()) {
                    error.noPaladinHero();
                    return null;
                }
                Display.selectHero(paladinData);
                int paladinChoice = Input.getHeroChoice(paladinData.size());
                String[] paladin = paladinData.get(paladinChoice);
                return new Paladin(paladin[0], Integer.parseInt(paladin[1]), Integer.parseInt(paladin[2]), Integer.parseInt(paladin[3]), Integer.parseInt(paladin[4]), Integer.parseInt(paladin[5]), Integer.parseInt(paladin[6]));
            default:
                return null;
        }
    }

    /**
     * The main game loop, which handles player actions and game events.
     */
    @Override
    protected void gameLoop() {
        while (true) {
            Display.map(world, party);
            char action = Input.getAction();
            switch (action) {
                case 'w':
                    world.move(action, party);
                    if (world.isCommonSpace()) {
                        if (Math.random() < Percentages.BATTLE) {
                            Battle battle = new Battle(party);
                            battle.start();
                            endOfBattle();
                            if (isGameOver()) {
                                Display.gameOver();
                                gameEnded = true;
                                return;
                            }
                        }
                    }
                    break;
                case 'a':
                    world.move(action, party);
                    if (world.isCommonSpace()) {
                        if (Math.random() < Percentages.BATTLE) {
                            Battle battle = new Battle(party);
                            battle.start();
                            endOfBattle();
                            if (isGameOver()) {
                                Display.gameOver();
                                gameEnded = true;
                                return;
                            }
                        }
                    }
                    break;
                case 's':
                    world.move(action, party);
                    if (world.isCommonSpace()) {
                        if (Math.random() < Percentages.BATTLE) {
                            Battle battle = new Battle(party);
                            battle.start();
                            endOfBattle();
                            if (isGameOver()) {
                                Display.gameOver();
                                gameEnded = true;
                                return;
                            }
                        }
                    }
                    break;
                case 'd':
                    world.move(action, party);
                    if (world.isCommonSpace()) {
                        if (Math.random() < Percentages.BATTLE) {
                            Battle battle = new Battle(party);
                            battle.start();
                            endOfBattle();
                            if (isGameOver()) {
                                Display.gameOver();
                                gameEnded = true;
                                return;
                            }
                        }
                    }
                    break;
                case 'm':
                    if (world.isMarket()) {
                        market = new Market();
                        market.enter(party);
                    } else {
                        error.notInMarket();
                    }
                    break;
                case 'i':
                    StatisticsDisplay.displayPartyStats(party);
                    break;
                case 'q':
                    Display.quit();
                    return;
                default:
                    error.invalidActionInput();
            }
        }
    }

    /**
     * Called at the end of each battle.
     */
    private void endOfBattle() {
        for (Hero hero : party) {
            hero.decrementDebuffDuration();
        }
    }

    /**
     * Checks if the game is over (i.e., all heroes are defeated).
     *
     * @return true if the game is over, false otherwise.
     */
    private boolean isGameOver() {
        for (Hero hero : party) {
            if (hero.getHp() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Ends the game and displays final statistics.
     */
    @Override
    protected void endGame() {
        if (gameEnded) {
            displayFinalStats();
        }
    }

    /**
     * Displays final statistics for all heroes.
     */
    @Override
    protected void displayFinalStats() {
        System.out.println();
        System.out.println(c.Cyan + c.Bold + "═══════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println(c.Cyan + c.Bold + "                      FINAL STATISTICS                         " + c.Reset);
        System.out.println(c.Cyan + c.Bold + "═══════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println();

        for (int i = 0; i < party.size(); i++) {
            Hero hero = party.get(i);
            System.out.println(c.Green + "┌─────────────────────────────────────────────────────────────┐" + c.Reset);
            System.out.println(c.Green + "│ HERO " + (i + 1) + ": " + String.format("%-50s", hero.getName()) + "│" + c.Reset);
            System.out.println(c.Green + "├─────────────────────────────────────────────────────────────┤" + c.Reset);
            System.out.println(c.Green + "│" + c.Reset + " Level: " + String.format("%-8d", hero.getLevel()) +
                    " Experience: " + String.format("%-10d", hero.getExperience()) +
                    " Gold: " + String.format("%-10d", hero.getGold()) + c.Green + "│" + c.Reset);
            System.out.println(c.Green + "│" + c.Reset + " HP: " + String.format("%-4d", hero.getHp()) + "/" + String.format("%-8d", hero.getMaxHp()) +
                    " MP: " + String.format("%-4d", hero.getMp()) + "/" + String.format("%-20d", hero.getMaxMp()) + c.Green + "│" + c.Reset);
            System.out.println(c.Green + "│" + c.Reset + " Strength: " + String.format("%-8d", hero.getStrength()) +
                    " Dexterity: " + String.format("%-8d", hero.getDexterity()) +
                    " Agility: " + String.format("%-8d", hero.getAgility()) + c.Green + "│" + c.Reset);

            // Equipment info
            String weaponName = hero.getEquippedWeapon() != null ? hero.getEquippedWeapon().getName() : "None";
            String armorName = hero.getEquippedArmor() != null ? hero.getEquippedArmor().getName() : "None";
            System.out.println(c.Green + "├─────────────────────────────────────────────────────────────┤" + c.Reset);
            System.out.println(c.Green + "│" + c.Reset + " Weapon: " + String.format("%-20s", weaponName) +
                    " Armor: " + String.format("%-17s", armorName) + c.Green + "│" + c.Reset);
            System.out.println(c.Green + "│" + c.Reset + " Inventory Items: " + String.format("%-40d", hero.getInventory().size()) + c.Green + "│" + c.Reset);
            System.out.println(c.Green + "└─────────────────────────────────────────────────────────────┘" + c.Reset);
            System.out.println();
        }

        System.out.println(c.Yellow + "Thank you for playing Monsters and Heroes!" + c.Reset);
        System.out.println();
    }
}
