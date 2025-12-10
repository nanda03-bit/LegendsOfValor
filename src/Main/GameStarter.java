/**
 * Filename: GameStarter.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-06
 * Description: Displays the menu of available games and launches the selected gameplay mode.
 */

package Main;

import ErrorMessages.PrintErrorMessages;
import Games.MonstersAndHeroes;
import java.util.*;

public class GameStarter {
    private int choice;
    Scanner input = new Scanner(System.in);
    PrintErrorMessages error = new PrintErrorMessages();
    private static MonstersAndHeroes m = new MonstersAndHeroes();
    /**
     * Displays the main menu and allows the player to choose a game to play or quit.
     */
    public void chooseGame()
    {
        char continueGame = 'N';

        // Main menu
        do{
            System.out.println("Welcome! Choose a game from the menu below by entering the corresponding serial number: ");
            System.out.println("    1. Monsters and Heroes");
            System.out.println("    2. Quit Game");
            System.out.print("Enter Your Choice (1-2):  ");
            try {
                choice = input.nextInt();
                System.out.println();
            }
            catch (InputMismatchException e)
            {
                // Handles exception cases
                error.invalidMenuInput();
                input.nextLine();
            }

            // Players input is checked in switch case
            switch(choice)
            {
                // Monsters and heroes
                case 1:
                    m.start();
                    break;

                // Exit from the game
                case 2:
                    System.out.println("See you later!");
                    System.exit(0);
                    break;

                // Handles invalid inputs
                default:
                    error.invalidMenuInput();
                    break;
            }

            // After a game is played, asks the player if they want to play another type of game
            System.out.print("Do you want to play another game? (Y/N):  ");

            // Players response
            continueGame = input.next().charAt(0);
        } while(Character.toLowerCase(continueGame)=='y');
        System.out.println();
        System.out.println("See you later!");
    }
}
