package Display.MonstersAndHeroes;

import Player.Heroes.*;
import Player.Monsters.*;
import Items.*;
import board.monstersandheroes.Board;
import java.util.*;
import Color.*;

public class Display {
    private static Color c = new Color();
    
    public static void welcome() {
        Color c = new Color();
        System.out.println( c.Blue + c.Bg_White + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + c.Reset);
        System.out.println( c.Blue + c.Bg_White + "                            Welcome to Legends: Monsters and Heroes!                          " + c.Reset);
        System.out.println( c.Blue + c.Bg_White + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + c.Reset);
        System.out.println();
    }

    public static void selectHeroType(int heroNum) {
        System.out.println("Choose the destiny of Hero " + heroNum + ":");
        System.out.println("     1. Warrior   — Chosen for their unmatched strength and fearless battle spirit.");
        System.out.println("     2. Sorcerer  — Chosen for their mastery of arcane energies and ancient magic.");
        System.out.println("     3. Paladin   — Chosen for their holy resolve and unwavering protection of the light.");
    }

    public static void selectHero(List<String[]> heroData) {
        System.out.println("Select a hero:   ");
        for (int i = 0; i < heroData.size(); i++) {
            System.out.println("      " + (i + 1) + ".     " + heroData.get(i)[0]);
        }
    }

    public static void map(Board world, List<Hero> party) {
        Color c = new Color();
        int size = world.getSize();
        int heroRow = world.getHeroRow();
        int heroCol = world.getHeroCol();
        int i, j, row, col;
        
        for (i = 0; i < 2 * size + 1; i++) {
            for (j = 0; j < 2 * size + 1; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    System.out.print(c.Bold + "  . " + c.Reset);
                }
                else if (i % 2 == 0 && j % 2 == 1) {
                    System.out.print(c.Bold + "  ..  " + c.Reset);
                }
                else if (i % 2 == 1 && j % 2 == 0) {
                    System.out.print(c.Bold + "  .  " + c.Reset);
                }
                else if (i % 2 == 1 && j % 2 == 1) {
                    row = (i - 1) / 2;
                    col = (j - 1) / 2;
                    
                    String content;
                    String colorCode = c.Reset;
                    
                    if (row == heroRow && col == heroCol) {
                        content = "  H  ";
                        colorCode = c.Bold + c.LightGreen;
                    } else {
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

    public static void heroTurn(Hero hero) {
        System.out.println(c.Cyan + hero.getName() + " 's turn  " + c.Reset);
        System.out.println("What will you do?");
        System.out.println();
    }

    public static void attackResult(Hero hero, Monster monster, int damage) {
        Color c = new Color();
        System.out.println(c.LightGreen + hero.getName() + c.Reset + " attacked " + c.Cyan + monster.getName() +c.Reset + " for " + damage + " damage!");
        System.out.println();
    }

    public static void monsterAttackResult(Monster monster, Hero hero, int damage) {
        Color c = new Color();
        System.out.println(c.Cyan + monster.getName() + c.Reset + " attacked " + c.LightGreen +hero.getName() + c.Reset + " for " + damage + " damage!");
        System.out.println();
    }

    public static void battleWin() {
        System.out.println("The heroes have triumphed! Victory shines upon you!");
        System.out.println();
    }

    public static void battleLose() {
        System.out.println("Darkness prevails. Your heroes have fallen in battle....");
        System.out.println();
    }

    public static void battleSurrender() {
        System.out.println("How unfortunate! You have surrendered the battle.");
        System.out.println();
    }

    public static void gameOver() {
        System.out.println("Game Over.");
        System.out.println();
    }

    public static void quit() {
        System.out.println("Quitting game...");
        System.out.println();
    }

    public static void selectTarget(List<Monster> monsters) {
        System.out.println("Select one of the target/s: ");
        for (int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            if (m.isAlive()) {
                System.out.println("    " + (i + 1) + ". " + m.getName() + " (HP: " + m.getHp() + ")");
            }
        }
    }

    public static void dodgeMessage(Monster monster, Hero hero) {
        Color c = new Color();
        System.out.println(c.Cyan + monster.getName() + c.Reset+ " dodged " + c.LightGreen+ hero.getName() +c.Reset+  "'s attack!");
    }

    public static void heroDodgeMessage(Hero hero, Monster monster) {
        Color c = new Color();
        System.out.println(c.LightGreen+ hero.getName() +c.Reset+ " dodged " + c.Cyan + monster.getName() + c.Reset + "'s attack!");
    }

    public static void monsterDefeated(Monster monster) {
        System.out.println(c.Pink + c.Bold + c.Bg_White+  "✔ " + monster.getName() + " has fallen in battle!" + c.Reset);
        System.out.println();
    }

    public static void heroDefeated(Hero hero) {
        System.out.println(c.Pink + c.Bold + c.Bg_White+ "⚠ HERO DOWN ! "+  hero.getName() + " has been defeated!" + c.Reset);
        System.out.println();
    }

    public static void noSpellsAvailable(Hero hero) {
        System.out.println(hero.getName() + " has no spells available.");
        System.out.println();
    }

    public static void selectSpell(Hero hero, List<Spell> spells) {
        System.out.println(hero.getName() + ", select a spell:");
        for (int i = 0; i < spells.size(); i++) {
            Spell spell = spells.get(i);
            System.out.println("    "+ (i + 1) + ". " + spell.getName() + " (Cost: " + spell.getManaCost() + " MP, Damage: " + spell.getDamage() + ")");
        }
    }

    public static void insufficientMP(Hero hero, Spell spell) {
        System.out.println(hero.getName() + " does not have enough MP to cast " + spell.getName() + " (Need: " + spell.getManaCost() + ", Have: " + hero.getMp() + ")");
    }

    public static void spellCastResult(Hero hero, Monster target, Spell spell, int damage) {
        System.out.println(hero.getName() + " cast " + spell.getName() + " on " + target.getName() + " for " + damage + " damage!");
        System.out.println("Spell effect: " + spell.getType() + " spell applied debuff to " + target.getName());
    }

    public static void invalidSelection() {
        System.out.println("Invalid selection.");
    }

    public static void noPotionsAvailable(Hero hero) {
        System.out.println(hero.getName() + " has no potions available.");
    }

    public static void selectPotion(Hero hero, List<Potion> potions) {
        System.out.println(hero.getName() + ", Select a potion:");
        for (int i = 0; i < potions.size(); i++) {
            Potion potion = potions.get(i);
            System.out.println(c.Yellow + "    " + (i + 1) + ". " + potion.getName() + " (+" + potion.getAttributeIncrease() + " " + potion.getAttributeAffected() + ")");
        }
    }

    public static void potionUsed(Hero hero, Potion potion, String attribute) {
        System.out.println(hero.getName() + " used " + potion.getName() + " and gained " + potion.getAttributeIncrease() + " to " + attribute);
    }

    public static void noItemsToEquip(Hero hero) {
        System.out.println(hero.getName() + " has no items to equip.");
    }

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

    public static void weaponEquipped(Hero hero, Weapon weapon) {
        System.out.println(c.LightGreen + hero.getName() + c.Bold + c.Reset+ " equipped " + weapon.getName());
    }

    public static void armorEquipped(Hero hero, Armor armor) {
        System.out.println(c.LightGreen + hero.getName() + c.Bold+ c.Reset + " equipped " + armor.getName());
    }

    public static void heroRewards(Hero hero, int experience, int gold) {
        System.out.println(c.LightGreen + hero.getName() + c.Bold + c.Reset+ " gained " + c.Yellow + experience + " experience and " + gold + " gold!" + c.Reset);
        System.out.println();
    }

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

