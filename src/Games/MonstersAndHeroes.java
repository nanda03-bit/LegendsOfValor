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
import Utilities.*;
import Utilities.MonstersAndHeroesGameConstants;
import Market.Market;
import board.monstersandheroes.Board;
import Display.MonstersAndHeroes.*;
import Display.Statistics.StatisticsDisplay;

public class MonstersAndHeroes extends BaseGame {
    private Board world;
    private Market market;

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
    }

    /**
     * Initializes the game world, party, and market.
     */
    @Override
    protected void initialize() {
        int worldSize = Input.getWorldSize();
        world = new Board(worldSize);
        DisplayInstruction.heroesInstructions();
        heroes = createHeroes();
    }

    /**
     * Gets the number of heroes to create.
     * @return The number of heroes to create.
     */
    @Override
    protected int getNumHeroes() {
        return Input.getPartySize();
    }

    /**
     * Creates a hero of a specific type based on player input.
     * @param heroType The type of hero to create.
     * @return The created hero, or null if creation failed.
     */
    @Override
    protected void gameLoop() {
        while (true) {
            Display.map(world, heroes);
            char action = Input.getAction();
            switch (action) {
                case 'w':
                    world.move(action, heroes);
                    if (world.isCommonSpace()) {
                        if (Math.random() < Percentages.BATTLE) {
                            Battle battle = new Battle(heroes);
                            battle.start();
                            endOfBattle();
                            if (isGameOver()) {
                                Display.gameOver();
                                return;
                            }
                        }
                    }
                    break;
                case 'a':
                    world.move(action, heroes);
                    if (world.isCommonSpace()) {
                        if (Math.random() < Percentages.BATTLE) {
                            Battle battle = new Battle(heroes);
                            battle.start();
                            endOfBattle();
                            if (isGameOver()) {
                                Display.gameOver();
                                return;
                            }
                        }
                    }
                    break;
                case 's':
                    world.move(action, heroes);
                    if (world.isCommonSpace()) {
                        if (Math.random() < Percentages.BATTLE) {
                            Battle battle = new Battle(heroes);
                            battle.start();
                            endOfBattle();
                            if (isGameOver()) {
                                Display.gameOver();
                                return;
                            }
                        }
                    }
                    break;
                case 'd':
                    world.move(action, heroes);
                    if (world.isCommonSpace()) {
                        if (Math.random() < Percentages.BATTLE) {
                            Battle battle = new Battle(heroes);
                            battle.start();
                            endOfBattle();
                            if (isGameOver()) {
                                Display.gameOver();
                                return;
                            }
                        }
                    }
                    break;
                case 'm':
                    if (world.isMarket()) {
                        market = new Market();
                        market.enter(heroes);
                    }
                    else {
                        error.notInMarket();
                    }
                    break;
                case 'i':
                    StatisticsDisplay.displayPartyStats(heroes);
                    break;
                case 'q':
                    Display.quit();
                    return;
                default:
                    error.invalidActionInput();
            }
        }
    }

    private void endOfBattle() {
        for (Hero hero : heroes) {
            hero.decrementDebuffDuration();
        }
    }

    /**
     * Checks if the game is over (i.e., all heroes are defeated).
     * @return true if the game is over, false otherwise.
     */
    @Override
    protected boolean isGameOver() {
        for (Hero hero : heroes) {
            if (hero.getHp() > 0) {
                return false;
            }
        }
        return true;
    }
}
