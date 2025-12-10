/**
 * Filename: Game.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-05
 * Description: Main entry point for launching the overall game system.
 */

package Main;

public class Game {
    /**
     * The main entry point of the application.
     * @param args Command line arguments (not used).
     * @return no return type
     */
    public static void main(String[] args) {
        GameStarter game = new GameStarter();
        game.chooseGame();
    }
}