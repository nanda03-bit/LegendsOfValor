/**
 * Filename: StatisticsDisplay.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-10
 * Description: Handles display of statistics for both heroes and monsters in various game contexts.
 */

package Display.Statistics;

import Player.Heroes.Hero;
import Player.Monsters.Monster;
import Color.Color;
import java.util.*;

public class StatisticsDisplay {
    private static Color c = new Color();

    /**
     * Displays party statistics.
     * @param party The list of heroes in the party.
     */
    public static void displayPartyStats(List<Hero> party) {
        System.out.println();
        System.out.println(c.Green + "╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println(c.Green + "║" + c.Bold + "                           PARTY STATISTICS                            " + c.Green + "║");
        System.out.println(c.Green + "╠═══════════════════════════════════════════════════════════════════════╣" + c.Reset);
        
        for (Hero hero : party) {
            System.out.println(c.Green + "║" + c.Reset + "                                                                       " + c.Green + "║");
            System.out.println(c.Green + "║" + c.Bold + "  " + hero.getName() + " (Level " + hero.getLevel() + ")" + c.Reset + "                                           " + c.Green + "║");
            System.out.println(c.Green + "║" + c.Reset + String.format("    HP: %-3d / %-6d       MP: %-3d / %-6d", hero.getHp(), hero.getMaxHp(), hero.getMp(), hero.getMaxMp()) + "                            " + c.Green + "║");
            System.out.println(c.Green + "║" + c.Reset + String.format("    Strength: %-12d Dexterity: %-10d  Agility: %-6d", hero.getStrength(), hero.getDexterity(), hero.getAgility()) + "      " + c.Green + "║");
            System.out.println(c.Green + "║" + c.Reset + String.format("    Gold: %-15d  Experience: %-15d", hero.getGold(), hero.getExperience()) + "                 " + c.Green + "║");
        }
        System.out.println(c.Green + "╚═══════════════════════════════════════════════════════════════════════╝" + c.Reset + "\n");
    }
    

    /**
     * Displays battle statistics for heroes and monsters.
     * @param heroes The list of heroes.
     * @param monsters The list of monsters.
     */
    public static void displayBattleStats(List<Hero> heroes, List<Monster> monsters) {
        // Heroes section
        System.out.println();
        System.out.println(c.Green + "╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println(c.Green + "║" + c.Bold + "                              HEROES                                   " + c.Green + "║");
        System.out.println(c.Green + "╠═══════════════════════════════════════════════════════════════════════╣" + c.Reset);
        System.out.println(c.Green + "║" + c.Bold + String.format(" %-20s %-10s %-15s %-15s", "Name", "Level", "HP", "MP") + c.Green + "       ║" + c.Reset);
        System.out.println(c.Green + "╠═══════════════════════════════════════════════════════════════════════╣" + c.Reset);
        
        for (Hero hero : heroes) {
            System.out.println(c.Green + "║" + c.Reset + String.format(" %-20s %-10d %-15s %-15s", hero.getName(), hero.getLevel(), hero.getHp() + "/" + hero.getMaxHp(), hero.getMp() + "/" + hero.getMaxMp()) + c.Green + "       ║" + c.Reset);
        }
        
        System.out.println(c.Green + "╚═══════════════════════════════════════════════════════════════════════╝" + c.Reset);
        
        // Monsters section
        System.out.println();
        System.out.println(c.Red + "╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println(c.Red + "║" + c.Bold + "                                MONSTERS                                    " + c.Red + "║");
        System.out.println(c.Red + "╠════════════════════════════════════════════════════════════════════════════╣" + c.Reset);
        System.out.println(c.Red + "║" + c.Bold + String.format(" %-20s %-10s %-15s %-15s %-10s", "Name", "Level", "HP", "Damage", "Defense") + c.Red + " ║" + c.Reset);
        System.out.println(c.Red + "╠════════════════════════════════════════════════════════════════════════════╣" + c.Reset);
        
        List<String> colors = Arrays.asList(c.Red, c.Green, c.Cyan, c.Blue, c.Purple);
        int colorIndex = 0;
        
        for (Monster monster : monsters) {
            String monsterColor = colors.get(colorIndex % colors.size());
            colorIndex++;
            System.out.println(c.Red + "║" + monsterColor + String.format(" %-20s", monster.getName()) + c.Reset + 
                String.format(" %-10d %-15s %-15d %-10d", 
                    monster.getLevel(), 
                    monster.getHp() + "/" + monster.getMaxHp(),
                    monster.getBaseDamage(),
                    monster.getDefense()) + c.Red + " ║" + c.Reset);
        }
        
        System.out.println(c.Red + "╚════════════════════════════════════════════════════════════════════════════╝" + c.Reset + "\n");
    }
}

