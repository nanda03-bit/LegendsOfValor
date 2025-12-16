/**
 * Filename: DisplayInstruction.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-05
 * Description: Displays game instructions and welcome messages for Monsters and Heroes.
 */

package Display.MonstersAndHeroes;

import Color.Color;

public class DisplayInstruction {
    private static Color c = new Color();

    public static void welcomeInstructions() {
        System.out.println();
        System.out.println(c.LightPink + c.Bold + "Welcome Traveler......."+ c.Reset);
        System.out.println();

        System.out.println("The world stirs once more.");
        System.out.println("Monsters roam the shattered lands, and the ruins of fallen kingdoms whisper of ages long gone.");
        System.out.println("Yet in this age of fading hope, a few brave souls still rise to challenge the darkness…");
        System.out.println();
        System.out.println("And now, you stand among them.");
        System.out.println("Alone at first—yet destined to shape the fate of this realm with every choice you make.");
        System.out.println();
        System.out.println("Steel your will, Traveler. Your legend begins now.");
        System.out.println();

        System.out.println("Before your journey begins, you must choose the size of the world you will brave.");
        System.out.println(c.Yellow + "How vast shall your realm be?" + c.Reset);
    }

    public static void instructions(){
        System.out.println();
        System.out.println(c.Bold + c.Pink + c.Bg_White + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + c.Reset);
        System.out.println(c.Bold + c.LightPink + c.Bg_White + "                                             HOW TO PLAY                                                   " + c.Reset);
        System.out.println(c.Bold + c.Pink + c.Bg_White + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + c.Reset);
        System.out.println();
        System.out.println("You control a small party of heroes traveling across a dangerous world.");
        System.out.println(c.LightPink +"Use the movement keys to explore new tiles, uncover markets, and face monsters: " + c.Reset);
        System.out.println(c.Pink + c.Bold + "          W  - move up           S  - move down");
        System.out.println("          A  - move left           D  - move right" + c.Reset);
        System.out.println();
        System.out.println(c.LightPink + "At any time, you can check how your heroes are doing by clicking 'I': " + c.Reset);
        System.out.println(c.Pink + c.Bold + "         I  - View your party's stats, HP/MP, gold, and inventory" + c.Reset);
        System.out.println();
        System.out.println(c.LightPink +"If you step onto a Market tile, you can shop for powerful gear:" +c.Reset);
        System.out.println(c.Pink + c.Bold + "         M  - Enter the market (only when you are standing on a Market tile)" + c.Reset);
        System.out.println();
        System.out.println(c.LightPink +"If you wish to end your adventure: " +c.Reset);
        System.out.println(c.Pink + c.Bold + "         Q  - Quit the game" + c.Reset);
        System.out.println();
        System.out.println("Blocked terrain. You cannot enter or move into this tile.");
        System.out.println(c.Red + c.Bold + "         X  - Inaccessible Tile" + c.Reset);
        System.out.println("              Every walkable tile that is NOT a Market and NOT Inaccessible is a Common Tile.");
        System.out.println("              These are the dangerous parts of the world where battles can occur at random.");
        System.out.println();

        System.out.println(c.Bold + c.Pink + c.Bg_White + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + c.Reset);
        System.out.println(c.Bold + c.LightPink + c.Bg_White + "                                             HOW BATTLES OCCUR                                              " + c.Reset);
        System.out.println(c.Bold + c.Pink + c.Bg_White + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + c.Reset);
        System.out.println();
        System.out.println("When you step onto a Common Tile, the game performs a random encounter check.");
        System.out.println("If the encounter roll succeeds (based on the battle probability), a battle begins.");
        System.out.println("The chance of battle is usually a fixed percentage, such as 30%.");
        System.out.println();
        System.out.println("If a battle starts:");
        System.out.println("• A group of monsters is generated to match the number of heroes you have.");
        System.out.println("• Monster levels scale to your highest-level hero.");
        System.out.println("• Combat begins immediately, with heroes acting first each round.");
        System.out.println();
        System.out.println("If no battle occurs, you may continue exploring safely—until your next step...");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println();
        System.out.println(c.Pink + c.Bold + "Your Battle Actions:" + c.Reset);
        System.out.println(c.Pink + "    A  - Attack" + c.Reset + c.LightPink + "         Strike a monster using your equipped weapon." + c.Reset);
        System.out.println(c.Pink + "    S  - Cast Spell" + c.Reset + c.LightPink + "     Use magic to damage enemies or apply special effects (costs MP)." + c.Reset);
        System.out.println(c.Pink + "    P  - Use Potion" + c.Reset + c.LightPink + "     Drink a potion to restore HP/MP or boost your stats temporarily." + c.Reset);
        System.out.println(c.Pink + "    E  - Equip Item" + c.Reset + c.LightPink + "    Change your weapon or armor mid-battle to adapt to the fight." + c.Reset);
        System.out.println(c.Pink + "    I  - Inspect" + c.Reset + c.LightPink + "        View detailed status of your heroes and the monsters." + c.Reset);

        System.out.println();
        System.out.println(c.Bold + "                                        GOOD LUCK TREAVELER                        " + c.Reset);
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println();
    }

    public static void heroesInstructions() {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
        System.out.println(c.LightPink + c.Bold + "Heroes... your only light against the darkness." + c.Reset);
        System.out.println();
        System.out.println("Before you step further into this ruined world, you must gather those who will fight beside you.");
        System.out.println("These heroes will face monsters, survive dangers, and turn the tide of battles in your journey.");
        System.out.println();
        System.out.println("Choose wisely—each hero brings a different strength to your party.");
        System.out.println();
        System.out.println("You may recruit up to " + c.Bold + "3 champions" + c.Reset + " to stand at your side.");
        System.out.println();
    }
}

