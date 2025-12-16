/**
 * Filename: ValorInput.java
 * Author: Gowrav
 * Date: 2025-Dec
 * Description: Handles all user input for the Legends of Valor game.
 */

package Display.Valor;

import Color.Color;
import ErrorMessages.PrintErrorMessages;
import Utilities.ValorGameConstants;
import Utilities.ScannerUtil;

import java.util.*;

public class ValorInput {
    private static PrintErrorMessages error = new PrintErrorMessages();
    private static Color c = new Color();

    /**
     * Gets the scanner instance.
     */
    private static Scanner getScanner() {
        return ScannerUtil.getScanner();
    }

    /**
     * Gets the hero action for a turn in Legends of Valor.
     *
     * @return The action character selected by the player.
     */
    public static char getHeroAction() {
        while (true) {
            try {
                System.out.println(c.Yellow + c.Bold + "Choose your action:" + c.Reset);
                System.out.println(c.Yellow + "  [M] Move          [A] Attack        [C] Cast Spell     [P] Use Potion" + c.Reset);
                System.out.println(c.Yellow + "  [E] Equip Item    [T] Teleport      [R] Recall         [B] Market (Nexus only)" + c.Reset);
                System.out.println(c.Yellow + "  [O] Remove Obstacle                 [I] View Info      [X] Pass Turn      [Q] Quit" + c.Reset);
                System.out.print(c.Bold + "Enter action: " + c.Reset);
                String input = getScanner().next().toUpperCase();
                if (input.length() > 0) {
                    char action = input.charAt(0);
                    if (action == 'M' || action == 'A' || action == 'C' || action == 'P' ||
                            action == 'E' || action == 'T' || action == 'R' || action == 'B' ||
                            action == 'I' || action == 'X' || action == 'Q' || action == 'O') {
                        return action;
                    }
                }
                error.invalidActionInput();
            } catch (Exception e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    /**
     * Gets a movement direction from the player.
     *
     * @return The direction character (W, A, S, D).
     */
    public static char getMovementDirection() {
        while (true) {
            try {
                System.out.println(c.Yellow + "Choose direction:" + c.Reset);
                System.out.println(c.Yellow + "  [W] North   [A] West   [S] South   [D] East   [X] Cancel" + c.Reset);
                System.out.print(c.Bold + "Enter direction: " + c.Reset);
                String input = getScanner().next().toUpperCase();
                if (input.length() > 0) {
                    char direction = input.charAt(0);
                    if (direction == 'W' || direction == 'A' || direction == 'S' || direction == 'D' || direction == 'X') {
                        return direction;
                    }
                }
                error.invalidActionInput();
            } catch (Exception e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    /**
     * Gets a target selection (for attack or spell).
     *
     * @param max The maximum number of targets.
     * @return The selected target index (0-based), or -1 to cancel.
     */
    public static int getTargetChoice(int max) {
        while (true) {
            try {
                System.out.print(c.Yellow + "Select target (1-" + max + ", 0 to cancel): " + c.Reset);
                int choice = getScanner().nextInt();
                if (choice == 0) {
                    return -1;
                }
                if (choice >= 1 && choice <= max) {
                    return choice - 1;
                }
                error.invalidActionInput();
                System.out.println("Target must be between 1 and " + max + ".");
            } catch (InputMismatchException e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    /**
     * Gets a spell selection from available spells.
     *
     * @param max The maximum number of spells.
     * @return The selected spell index (0-based), or -1 to cancel.
     */
    public static int getSpellChoice(int max) {
        while (true) {
            try {
                System.out.print(c.Yellow + "Select spell (1-" + max + ", 0 to cancel): " + c.Reset);
                int choice = getScanner().nextInt();
                if (choice == 0) {
                    return -1;
                }
                if (choice >= 1 && choice <= max) {
                    return choice - 1;
                }
                error.invalidActionInput();
                System.out.println("Spell must be between 1 and " + max + ".");
            } catch (InputMismatchException e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    /**
     * Gets a potion selection from available potions.
     *
     * @param max The maximum number of potions.
     * @return The selected potion index (0-based), or -1 to cancel.
     */
    public static int getPotionChoice(int max) {
        while (true) {
            try {
                System.out.print(c.Yellow + "Select potion (1-" + max + ", 0 to cancel): " + c.Reset);
                int choice = getScanner().nextInt();
                if (choice == 0) {
                    return -1;
                }
                if (choice >= 1 && choice <= max) {
                    return choice - 1;
                }
                error.invalidActionInput();
                System.out.println("Potion must be between 1 and " + max + ".");
            } catch (InputMismatchException e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    /**
     * Gets an item selection for equipping.
     *
     * @param max The maximum number of items.
     * @return The selected item index (0-based), or -1 to cancel.
     */
    public static int getEquipChoice(int max) {
        while (true) {
            try {
                System.out.print(c.Yellow + "Select item to equip (1-" + max + ", 0 to cancel): " + c.Reset);
                int choice = getScanner().nextInt();
                if (choice == 0) {
                    return -1;
                }
                if (choice >= 1 && choice <= max) {
                    return choice - 1;
                }
                error.invalidActionInput();
                System.out.println("Item must be between 1 and " + max + ".");
            } catch (InputMismatchException e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    /**
     * Gets a hero selection for teleport.
     *
     * @param max The number of heroes.
     * @return The selected hero index (0-based), or -1 to cancel.
     */
    public static int getHeroSelection(int max) {
        while (true) {
            try {
                System.out.print(c.Yellow + "Select hero (1-" + max + ", 0 to cancel): " + c.Reset);
                int choice = getScanner().nextInt();
                if (choice == 0) {
                    return -1;
                }
                if (choice >= 1 && choice <= max) {
                    return choice - 1;
                }
                error.invalidActionInput();
                System.out.println("Hero must be between 1 and " + max + ".");
            } catch (InputMismatchException e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    /**
     * Gets the lane selection for hero spawning.
     *
     * @param heroNum   The hero number being assigned.
     * @param usedLanes Set of already used lanes.
     * @return The selected lane (0, 1, or 2).
     */
    public static int getLaneSelection(int heroNum, Set<Integer> usedLanes) {
        while (true) {
            try {
                System.out.println(c.Yellow + "Assign Hero " + heroNum + " to a lane:" + c.Reset);
                System.out.println(c.Yellow + "  [1] Top Lane    [2] Middle Lane    [3] Bottom Lane" + c.Reset);

                // Show which lanes are taken
                if (!usedLanes.isEmpty()) {
                    System.out.print(c.Yellow + "  (Already assigned: ");
                    for (int lane : usedLanes) {
                        System.out.print("Lane " + (lane + 1) + " ");
                    }
                    System.out.println(")" + c.Reset);
                }

                System.out.print(c.Bold + "Enter lane: " + c.Reset);
                int lane = getScanner().nextInt();
                if (lane >= 1 && lane <= 3) {
                    int laneIndex = lane - 1;
                    if (!usedLanes.contains(laneIndex)) {
                        return laneIndex;
                    } else {
                        System.out.println("That lane is already assigned. Choose a different lane.");
                    }
                } else {
                    error.invalidActionInput();
                    System.out.println("Lane must be 1, 2, or 3.");
                }
            } catch (InputMismatchException e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    /**
     * Gets difficulty selection for the game.
     *
     * @return The difficulty level (1 = Easy, 2 = Medium, 3 = Hard).
     */
    public static int getDifficulty() {
        while (true) {
            try {
                System.out.println(c.Yellow + c.Bold + "Select Difficulty:" + c.Reset);
                System.out.println(c.Yellow + "  [1] Easy   - Monster spawn every 8 rounds" + c.Reset);
                System.out.println(c.Yellow + "  [2] Medium - Monster spawn every 6 rounds" + c.Reset);
                System.out.println(c.Yellow + "  [3] Hard   - Monster spawn every 4 rounds" + c.Reset);
                System.out.print(c.Bold + "Enter difficulty: " + c.Reset);
                int diff = getScanner().nextInt();
                if (diff >= 1 && diff <= 3) {
                    return diff;
                }
                error.invalidActionInput();
            } catch (InputMismatchException e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    /**
     * Asks for confirmation (Y/N).
     *
     * @param prompt The prompt to display.
     * @return true if confirmed, false otherwise.
     */
    public static boolean confirm(String prompt) {
        while (true) {
            try {
                System.out.print(c.Yellow + prompt + " (Y/N): " + c.Reset);
                String input = getScanner().next().toUpperCase();
                if (input.length() > 0) {
                    char answer = input.charAt(0);
                    if (answer == 'Y') {
                        return true;
                    }
                    if (answer == 'N') {
                        return false;
                    }
                }
                System.out.println("Please enter Y or N.");
            } catch (Exception e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }

    /**
     * Gets a general choice from a maximum.
     *
     * @param max Maximum number of options.
     * @return The selected choice (0-based), or -1 to cancel.
     */
    public static int getChoice(int max) {
        while (true) {
            try {
                System.out.print(c.Yellow + "Enter choice (1-" + max + ", 0 to cancel): " + c.Reset);
                int choice = getScanner().nextInt();
                if (choice == 0) {
                    return -1;
                }
                if (choice >= 1 && choice <= max) {
                    return choice - 1;
                }
                error.invalidActionInput();
            } catch (InputMismatchException e) {
                error.invalidInput();
                getScanner().nextLine();
            }
        }
    }
}
