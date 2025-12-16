package Games;

import Player.Heroes.Hero;
import Player.HeroTeam;

import java.util.*;

/**
 * Abstract base class for role-playing games.
 * Defines the common structure for game initialization, main loop, and termination.
 */
public abstract class RPGGame {

    /**
     * The team of heroes playing the game
     */
    protected HeroTeam heroTeam;

    /**
     * Legacy list for backward compatibility
     */
    protected List<Hero> heroes;

    /**
     * Flag indicating if the game is running
     */
    protected boolean isRunning;

    /**
     * The name of this game
     */
    protected String gameName;

    /**
     * Default constructor.
     */
    public RPGGame() {
        this.heroTeam = new HeroTeam();
        this.heroes = new ArrayList<>();
        this.isRunning = false;
        this.gameName = "RPG Game";
    }

    /**
     * Constructor with game name.
     *
     * @param gameName The name of the game.
     */
    public RPGGame(String gameName) {
        this.heroTeam = new HeroTeam();
        this.heroes = new ArrayList<>();
        this.isRunning = false;
        this.gameName = gameName;
    }

    /**
     * Starts the game. This is the main entry point.
     * Template method that calls initialize, gameLoop, and endGame in order.
     */
    public abstract void start();

    /**
     * Displays the welcome message for the game.
     * Can be overridden by subclasses for custom welcome screens.
     */
    protected void displayWelcome() {
        System.out.println("Welcome to " + gameName + "!");
        System.out.println();
    }

    /**
     * Initializes the game world, heroes, and any other setup needed.
     * Must be implemented by subclasses.
     */
    protected abstract void initialize();

    /**
     * The main game loop that runs until the game ends.
     * Must be implemented by subclasses.
     */
    protected abstract void gameLoop();

    /**
     * Handles end of game logic, including displaying final stats.
     * Must be implemented by subclasses.
     */
    protected abstract void endGame();

    /**
     * Displays final statistics for all heroes.
     * Must be implemented by subclasses.
     */
    protected abstract void displayFinalStats();

    /**
     * Creates the hero team/party for the game.
     * Can be overridden by subclasses for custom hero selection.
     */
    protected abstract void createHeroes();

    /**
     * Gets the hero team.
     *
     * @return The hero team.
     */
    public HeroTeam getHeroTeam() {
        return heroTeam;
    }

    /**
     * Gets the list of heroes (for backward compatibility).
     *
     * @return List of heroes.
     */
    public List<Hero> getHeroes() {
        return heroes;
    }

    /**
     * Checks if the game is currently running.
     *
     * @return true if game is running.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Gets the game name.
     *
     * @return The name of the game.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Stops the game.
     */
    public void stop() {
        isRunning = false;
    }
}
