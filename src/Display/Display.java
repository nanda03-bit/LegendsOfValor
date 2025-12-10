/**
 * Filename: Display.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-05
 * Description: Base display utility for rendering game elements such as maps, menus, and messages.
 */

package Display;

import Player.Heroes.*;
import Player.Monsters.*;
import Items.*;
import World.*;
import java.util.*;
import Color.*;


public class Display {
    private static Color c = new Color();
    /**
     * Displays a welcome message to the player.
     * @Return No return type
     */
    public static void welcome() {
        Color c = new Color();
        System.out.println( c.Blue + c.Bg_White + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + c.Reset);
        System.out.println( c.Blue + c.Bg_White + "                            Welcome to Legends: Monsters and Heroes!                          " + c.Reset);
        System.out.println( c.Blue + c.Bg_White + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + c.Reset);
        System.out.println();

    }

    /**
     * Displays the hero type selection menu.
     * @param heroNum The number of the hero being selected.
     */
    public static void selectHeroType(int heroNum) {
        System.out.println("Choose the destiny of Hero " + heroNum + ":");
        System.out.println("     1. Warrior   — Chosen for their unmatched strength and fearless battle spirit.");
        System.out.println("     2. Sorcerer  — Chosen for their mastery of arcane energies and ancient magic.");
        System.out.println("     3. Paladin   — Chosen for their holy resolve and unwavering protection of the light.");
    }

    /**
     * Displays a list of heroes to choose from.
     * @param heroData A list of hero data arrays.
     */
    public static void selectHero(List<String[]> heroData) {
        System.out.println("Select a hero:   ");
        for (int i = 0; i < heroData.size(); i++) {
            System.out.println("      " + (i + 1) + ".     " + heroData.get(i)[0]);
        }
    }

    /**
     * Displays the world map.
     * @param world The world to display.
     * @param party The hero party.
     */
    public static void map(World world, List<Hero> party) {
        Color c = new Color();
        int size = world.getSize();
        int heroRow = world.getHeroRow();
        int heroCol = world.getHeroCol();
        int i, j, row, col;
        
        // Iterate through the abstract 2*size+1 x 2*size+1 matrix
        for (i = 0; i < 2 * size + 1; i++) {
            for (j = 0; j < 2 * size + 1; j++) {
                // To print corner markers (.)
                // Every corner marker is located at an even position, so we check for i and j %2 = 0
                if (i % 2 == 0 && j % 2 == 0) {
                    System.out.print(c.Bold + "  . " + c.Reset);
                }
                
                // To print the horizontal edges (top and bottom borders)
                // Every horizontal edge is located at a position where i is even and j is odd
                else if (i % 2 == 0 && j % 2 == 1) {
                    System.out.print(c.Bold + "  ..  " + c.Reset);
                }
                
                // To print the vertical edges (left and right borders)
                // The vertical edges are located at positions where i is odd and j is even
                else if (i % 2 == 1 && j % 2 == 0) {
                    System.out.print(c.Bold + "  .  " + c.Reset);
                }
                
                // To print the tile content (H, X, M, or .)
                // When both i and j are odd, we're at the center of a cell
                else if (i % 2 == 1 && j % 2 == 1) {
                    row = (i - 1) / 2;
                    col = (j - 1) / 2;
                    
                    String content;
                    String colorCode = c.Reset;
                    
                    // Check if hero is at this position
                    if (row == heroRow && col == heroCol) {
                        content = "  H  ";
                        colorCode = c.Bold + c.LightGreen;
                    } else {
                        // Get tile type and display appropriate content
                        switch (world.getTile(row, col).getType()) {
                            case INACCESSIBLE:
                                content = "  X  ";
                                colorCode = c.Bold + c.Red;
                                break;
                            case MARKET:
                                content = "  M  ";
                                colorCode = c.Bold + c.LightPink;
                                break;
                            case COMMON:
                                content = "     ";
                                colorCode = c.Reset;
                                break;
                            default:
                                content = "  ?  ";
                                break;
                        }
                    }
                    
                    System.out.print(colorCode + content + c.Reset);
                }
            }
            System.out.println();
        }
        System.out.println();
    }


    /**
     * Displays a message indicating the start of a battle.
     */
    public static void battleStart() {
        Color c = new Color();

        System.out.println();
        slowPrint("Shhhhhhhhhhh. . . . . . . Do you hear that. . . ?", 150);
        slowPrint("Something is moving in the shadows...", 45);
        slowPrint("Oh NO MONSTERS !", 100);
        System.out.println();
        System.out.println( c.Red + c.Bg_White + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + c.Reset);
        System.out.println( c.Red + c.Bg_White + "                                      A Battle Has Started !                                  " + c.Reset);
        System.out.println( c.Red + c.Bg_White + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + c.Reset);
        System.out.println();
    }

    /**
     * Displays the available actions for a hero's turn in battle.
     * @param hero The hero whose turn it is.
     * @return No return
     */
    public static void heroTurn(Hero hero) {
        System.out.println(c.Cyan + hero.getName() + " 's turn  " + c.Reset);
        System.out.println("What will you do?");
        System.out.println();
    }


    /**
     * Displays the result of a hero's attack on a monster.
     * @param hero The attacking hero.
     * @param monster The targeted monster.
     * @param damage The damage dealt.
     */
    public static void attackResult(Hero hero, Monster monster, int damage) {
        Color c = new Color();
        System.out.println(c.LightGreen + hero.getName() + c.Reset + " attacked " + c.Cyan + monster.getName() +c.Reset + " for " + damage + " damage!");
        System.out.println();
    }

    /**
     * Displays the result of a monster's attack on a hero.
     * @param monster The attacking monster.
     * @param hero The targeted hero.
     * @param damage The damage dealt.
     */
    public static void monsterAttackResult(Monster monster, Hero hero, int damage) {
        Color c = new Color();
        System.out.println(c.Cyan + monster.getName() + c.Reset + " attacked " + c.LightGreen +hero.getName() + c.Reset + " for " + damage + " damage!");
        System.out.println();
    }

    /**
     * Displays a message indicating that the player won the battle.
     */
    public static void battleWin() {
        System.out.println("The heroes have triumphed! Victory shines upon you!");
        System.out.println();
    }

    /**
     * Displays a message indicating that the player lost the battle.
     */
    public static void battleLose() {
        System.out.println("Darkness prevails. Your heroes have fallen in battle....");
        System.out.println();
    }

    /**
     * Displays a message indicating that the player surrendered the battle.
     */
    public static void battleSurrender() {
        System.out.println("How unfortunate! You have surrendered the battle.");
        System.out.println();
    }

    /**
     * Displays a "Game Over" message.
     */
    public static void gameOver() {
        System.out.println("Game Over.");
        System.out.println();
    }

    /**
     * Displays a message indicating that the game is quitting.
     */
    public static void quit() {
        System.out.println("Quitting game...");
        System.out.println();
    }



    /**
     * Displays a list of targets to choose from.
     * @param monsters The list of monsters to choose from.
     */
    public static void selectTarget(List<Monster> monsters) {
        System.out.println("Select one of the target/s: ");
        for (int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            if (m.isAlive()) {
                System.out.println("    " + (i + 1) + ". " + m.getName() + " (HP: " + m.getHp() + ")");
            }
        }
    }

    /**
     * Displays a message indicating that a monster dodged an attack.
     * @param monster The monster that dodged.
     * @param hero The hero who attacked.
     */
    public static void dodgeMessage(Monster monster, Hero hero) {
        Color c = new Color();
        System.out.println(c.Cyan + monster.getName() + c.Reset+ " dodged " + c.LightGreen+ hero.getName() +c.Reset+  "'s attack!");
    }

    /**
     * Displays a message indicating that a hero dodged an attack.
     * @param hero The hero that dodged.
     * @param monster The monster who attacked.
     */
    public static void heroDodgeMessage(Hero hero, Monster monster) {
        Color c = new Color();
        System.out.println(c.LightGreen+ hero.getName() +c.Reset+ " dodged " + c.Cyan + monster.getName() + c.Reset + "'s attack!");
    }

    /**
     * Displays a message indicating that a monster has been defeated.
     * @param monster The defeated monster.
     */
    public static void monsterDefeated(Monster monster) {
        System.out.println(c.Pink + c.Bold + c.Bg_White+  "✔ " + monster.getName() + " has fallen in battle!" + c.Reset);
        System.out.println();
    }

    /**
     * Displays a message indicating that a hero has been defeated.
     * @param hero The defeated hero.
     */
    public static void heroDefeated(Hero hero) {
        System.out.println(c.Pink + c.Bold + c.Bg_White+ "⚠ HERO DOWN ! "+  hero.getName() + " has been defeated!" + c.Reset);
        System.out.println();
    }

    /**
     * Displays a message indicating that a hero has no spells available.
     * @param hero The hero with no spells.
     */
    public static void noSpellsAvailable(Hero hero) {
        System.out.println(hero.getName() + " has no spells available.");
        System.out.println();
    }

    /**
     * Displays a list of spells to choose from.
     * @param hero The hero casting the spell.
     * @param spells The list of available spells.
     */
    public static void selectSpell(Hero hero, List<Spell> spells) {
        System.out.println(hero.getName() + ", select a spell:");
        for (int i = 0; i < spells.size(); i++) {
            Spell spell = spells.get(i);
            System.out.println("    "+ (i + 1) + ". " + spell.getName() + " (Cost: " + spell.getManaCost() + " MP, Damage: " + spell.getDamage() + ")");
        }
    }

    /**
     * Displays a message indicating that a hero has insufficient MP to cast a spell.
     * @param hero The hero with insufficient MP.
     * @param spell The spell that could not be cast.
     */
    public static void insufficientMP(Hero hero, Spell spell) {
        System.out.println(hero.getName() + " does not have enough MP to cast " + spell.getName() + " (Need: " + spell.getManaCost() + ", Have: " + hero.getMp() + ")");
    }

    /**
     * Displays the result of a spell being cast.
     * @param hero The hero who cast the spell.
     * @param target The targeted monster.
     * @param spell The spell that was cast.
     * @param damage The damage dealt.
     */
    public static void spellCastResult(Hero hero, Monster target, Spell spell, int damage) {
        System.out.println(hero.getName() + " cast " + spell.getName() + " on " + target.getName() + " for " + damage + " damage!");
        System.out.println("Spell effect: " + spell.getType() + " spell applied debuff to " + target.getName());
    }

    /**
     * Displays a message indicating an invalid selection.
     */
    public static void invalidSelection() {
        System.out.println("Invalid selection.");
    }

    /**
     * Displays a message indicating that a hero has no potions available.
     * @param hero The hero with no potions.
     */
    public static void noPotionsAvailable(Hero hero) {
        System.out.println(hero.getName() + " has no potions available.");
    }

    /**
     * Displays a list of potions to choose from.
     * @param hero The hero using the potion.
     * @param potions The list of available potions.
     */
    public static void selectPotion(Hero hero, List<Potion> potions) {
        System.out.println(hero.getName() + ", Select a potion:");
        for (int i = 0; i < potions.size(); i++) {
            Potion potion = potions.get(i);
            System.out.println(c.Yellow + "    " + (i + 1) + ". " + potion.getName() + " (+" + potion.getAttributeIncrease() + " " + potion.getAttributeAffected() + ")");
        }
    }

    /**
     * Displays a message indicating that a potion was used.
     * @param hero The hero who used the potion.
     * @param potion The potion that was used.
     * @param attribute The attribute that was increased.
     */
    public static void potionUsed(Hero hero, Potion potion, String attribute) {
        System.out.println(hero.getName() + " used " + potion.getName() + " and gained " + potion.getAttributeIncrease() + " to " + attribute);
    }

    /**
     * Displays a message indicating that a hero has no items to equip.
     * @param hero The hero with no items to equip.
     */
    public static void noItemsToEquip(Hero hero) {
        System.out.println(hero.getName() + " has no items to equip.");
    }

    /**
     * Displays a list of items to equip.
     * @param hero The hero equipping the item.
     * @param items The list of equippable items.
     */
    public static void selectEquipItem(Hero hero, List<Item> items) {
        System.out.println(c.Yellow + c.Bold +  hero.getName() + ", select an item to equip:" + c.Reset);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            String itemType = "";
            if (item instanceof Weapon) {
                Weapon weapon = (Weapon) item;
                itemType = "Weapon (Damage: " + weapon.getDamage() + ")";
            }
            else if (item instanceof Armor) {
                Armor armor = (Armor) item;
                itemType = "Armor (Defense: " + armor.getDamageReduction() + ")";
            }
            System.out.println(c.Yellow + c.Bold + (i + 1) + ". " + item.getName() + " - " + itemType + c.Reset);
        }
    }

    /**
     * Displays a message indicating that a weapon was equipped.
     * @param hero The hero who equipped the weapon.
     * @param weapon The weapon that was equipped.
     */
    public static void weaponEquipped(Hero hero, Weapon weapon) {
        System.out.println(c.LightGreen + hero.getName() + c.Bold + c.Reset+ " equipped " + weapon.getName());
    }

    /**
     * Displays a message indicating that an armor was equipped.
     * @param hero The hero who equipped the armor.
     * @param armor The armor that was equipped.
     */
    public static void armorEquipped(Hero hero, Armor armor) {
        System.out.println(c.LightGreen + hero.getName() + c.Bold+ c.Reset + " equipped " + armor.getName());
    }

    /**
     * Displays the rewards a hero gained from winning a battle.
     * @param hero The hero who gained the rewards.
     * @param experience The amount of experience gained.
     * @param gold The amount of gold gained.
     */
    public static void heroRewards(Hero hero, int experience, int gold) {
        System.out.println(c.LightGreen + hero.getName() + c.Bold + c.Reset+ " gained " + c.Yellow + experience + " experience and " + gold + " gold!" + c.Reset);
        System.out.println();
    }

    /**
     * Displays a message indicating that a hero has been revived.
     * @param hero The hero who was revived.
     */
    public static void heroRevived(Hero hero) {
        System.out.println(c.LightGreen + hero.getName() + c.Reset + " has been revived with half HP and MP (no rewards).");
    }

    public static void slowPrint(String text, int delay) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delay);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
}
