/**
 * Filename: PrintErrorMessages.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-06
 * Description: Provides functions to print consistent and formatted error messages to the player.
 */

package ErrorMessages;

import Color.Color;

public class PrintErrorMessages {
    Color c = new Color();
    public String errorStatement;

    /**
     * Displays an error message for invalid menu input.
     */
    public void invalidMenuInput()
    {
        errorStatement = "Such a number holds no power here. Choose a rightful menu option. ";
        System.out.println();
        print(errorStatement);
    }

    /**
     * Displays an error message for invalid action input.
     */
    public void invalidActionInput() {
        errorStatement = "The spirits say nope. Enter a valid action.  ";
        System.out.println();
        print(errorStatement);
    }

    /**
     * Displays an error message for invalid size input.
     */
    public void invalidSizeInput() {
        errorStatement = "Even the monsters laughed at that size. Try again.   ";
        System.out.println();
        print(errorStatement);
    }

    /**
     * Displays an error message for invalid input that was expected to be a number.
     */
    public void invalidInput() {
        errorStatement = "That was not even a number, a hero fainted!!! Try again.  ";
        System.out.println();
        print(errorStatement);
    }

    /**
     * Displays an error message for attempting to access the market from a non-market tile.
     */
    public void notInMarket() {
        errorStatement = "You are not on a market tile right now, Traveler!! Actions related to buying or selling are unavailable.";
        System.out.println();
        print(errorStatement);
    }

    /**
     * Displays a message indicating that the tile is inaccessible.
     */
    public void inaccessibleTile() {
        errorStatement = "You cannot move to this tile, it is inaccessible.";
        System.out.println();
        print(errorStatement);
    }

    public void failedCreateHero() {
        errorStatement = "Failed to create hero. Please try again.";
        System.out.println();
        print(errorStatement);
    }

    public void noWarriorHero() {
        errorStatement = "No warriors available. Defaulting to first available hero.";
        System.out.println();
        print(errorStatement);
    }

    public void noSorcererHero() {
        errorStatement = "No sorcerers available. Defaulting to first available hero.";
        System.out.println();
        print(errorStatement);
    }

    public void noPaladinHero() {
        errorStatement = "No paladins available. Defaulting to first available hero.";
        System.out.println();
        print(errorStatement);
    }

    /**
     * Prints a given statement in red color.
     * @param statement The statement to print.
     */
    public void print(String statement) {
        System.out.println(c.Red + statement + c.Reset);
        System.out.println();
    }

}
