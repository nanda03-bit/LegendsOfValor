/**
 * Filename: ValorDisplay.java
 * Author: Gowrav
 * Date: 2025-Dec
 * Description: Handles all display output for the Legends of Valor game.
 */

package Display.Valor;

import Player.Heroes.*;
import Player.Monsters.*;
import Items.*;
import Wrapper.MonsterWrapper;
import board.valor.*;
import board.common.BoardEntity;
import Color.*;
import java.util.*;

public class ValorDisplay {
    private static Color c = new Color();

    /**
     * Displays the welcome message for Legends of Valor.
     */
    public static void welcome() {
        System.out.println();
        System.out.println(c.Blue + "  _                                   _              __   __     _            " + c.Reset);
        System.out.println(c.Blue + " | |    ___  __ _  ___ _ __   __| |___    ___  / _|  \\ \\   / /_ _| | ___  _ __ " + c.Reset);
        System.out.println(c.Blue + " | |   / _ \\/ _` |/ _ \\ '_ \\ / _` / __|  / _ \\| |_    \\ \\ / / _` | |/ _ \\| '__|" + c.Reset);
        System.out.println(c.Blue + " | |__|  __/ (_| |  __/ | | | (_| \\__ \\ | (_) |  _|    \\ V / (_| | | (_) | |   " + c.Reset);
        System.out.println(c.Blue + " |_____\\___|\\__, |\\___|_| |_|\\__,_|___/  \\___/|_|       \\_/ \\__,_|_|\\___/|_|   " + c.Reset);
        System.out.println(c.Blue + "            |___/                                                              " + c.Reset);
        System.out.println();
        System.out.println(c.Yellow + "A MOBA-like game where heroes battle monsters in a contest of strategy!" + c.Reset);
        System.out.println(c.Yellow + "Lead your team of 3 heroes through the lanes to destroy the monsters' Nexus!" + c.Reset);
        System.out.println();
    }

    /**
     * Displays game instructions.
     */
    public static void instructions() {
        System.out.println(c.Bold + "═══════════════════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println(c.Bold + "                            GAME INSTRUCTIONS                              " + c.Reset);
        System.out.println(c.Bold + "═══════════════════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println();
        System.out.println(c.Yellow + "OBJECTIVE:" + c.Reset + " Reach the monsters' Nexus (row 0) before any monster reaches yours (row 7)!");
        System.out.println();
        System.out.println(c.Yellow + "THE BOARD:" + c.Reset);
        System.out.println("  • 8x8 grid divided into 3 lanes separated by walls");
        System.out.println("  • Top Lane: Columns 0-1  |  Mid Lane: Columns 3-4  |  Bot Lane: Columns 6-7");
        System.out.println("  • Walls (columns 2 and 5) are impassable");
        System.out.println();
        System.out.println(c.Yellow + "TERRAIN TYPES:" + c.Reset);
        System.out.println("  • " + c.Blue + "N" + c.Reset + " - Nexus (spawn point & market for heroes)");
        System.out.println("  • " + c.Brown + "I" + c.Reset + " - Inaccessible (walls)");
        System.out.println("  • " + c.Pink + "O" + c.Reset + " - Obstacle (can be removed)");
        System.out.println("  •   P - Plain (no bonuses)");
        System.out.println("  • " + c.LightGreen + "B" + c.Reset + " - Bush (+10% Dexterity)");
        System.out.println("  • " + c.Purple + "C" + c.Reset + " - Cave (+10% Agility)");
        System.out.println("  • " + c.Orange + "K" + c.Reset + " - Koulou (+10% Strength)");
        System.out.println();
        System.out.println(c.Yellow + "ACTIONS:" + c.Reset);
        System.out.println("  • Move (W/A/S/D) - Move north/west/south/east");
        System.out.println("  • Attack - Attack a monster in range");
        System.out.println("  • Cast Spell - Use a spell on a monster in range");
        System.out.println("  • Use Potion - Consume a potion from inventory");
        System.out.println("  • Equip - Change weapon or armor");
        System.out.println("  • Teleport - Move to an adjacent space of another hero in a different lane");
        System.out.println("  • Recall - Return to your Nexus spawn point");
        System.out.println("  • Market - Buy/sell items (only at Nexus)");
        System.out.println();
        System.out.println(c.Yellow + "RULES:" + c.Reset);
        System.out.println("  • Heroes cannot pass monsters without killing them");
        System.out.println("  • Two heroes cannot occupy the same space");
        System.out.println("  • Monsters spawn every few rounds and move toward your Nexus");
        System.out.println("  • Heroes respawn at their Nexus if killed");
        System.out.println();
        System.out.println(c.Bold + "═══════════════════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println();
    }

    /**
     * Displays the current round number.
     * @param round The current round number.
     */
    public static void showRound(int round) {
        System.out.println();
        System.out.println(c.Cyan + c.Bold + "══════════════════════════════════════════════════════" + c.Reset);
        System.out.println(c.Cyan + c.Bold + "                     ROUND " + round + "                          " + c.Reset);
        System.out.println(c.Cyan + c.Bold + "══════════════════════════════════════════════════════" + c.Reset);
        System.out.println();
    }

    /**
     * Displays whose turn it is.
     * @param hero The hero whose turn it is.
     * @param heroIndex The hero's index (1-3).
     */
    public static void heroTurnStart(Hero hero, int heroIndex) {
        System.out.println();
        System.out.println(c.Green + c.Bold + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + c.Reset);
        System.out.println(c.Green + c.Bold + "  HERO " + heroIndex + " - " + hero.getName() + "'s Turn" + c.Reset);
        System.out.println(c.Green + "  HP: " + hero.getHp() + "/" + hero.getMaxHp() + "  |  MP: " + hero.getMp() + "/" + hero.getMaxMp() + c.Reset);
        System.out.println(c.Green + c.Bold + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + c.Reset);
    }

    /**
     * Displays that monsters are taking their turn.
     */
    public static void monstersTurnStart() {
        System.out.println();
        System.out.println(c.Red + c.Bold + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + c.Reset);
        System.out.println(c.Red + c.Bold + "                    MONSTERS' TURN                     " + c.Reset);
        System.out.println(c.Red + c.Bold + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + c.Reset);
    }

    /**
     * Displays hero attack result.
     */
    public static void heroAttackResult(Hero hero, Monster monster, int damage) {
        System.out.println(c.Green + hero.getName() + c.Reset + " attacked " + c.Red + monster.getName() + c.Reset + " for " + c.Yellow + damage + c.Reset + " damage!");
    }

    /**
     * Displays monster attack result.
     */
    public static void monsterAttackResult(Monster monster, Hero hero, int damage) {
        System.out.println(c.Red + monster.getName() + c.Reset + " attacked " + c.Green + hero.getName() + c.Reset + " for " + c.Yellow + damage + c.Reset + " damage!");
    }

    /**
     * Displays that a monster was killed.
     */
    public static void monsterKilled(Monster monster) {
        System.out.println(c.Yellow + c.Bold + "★ " + monster.getName() + " has been defeated! ★" + c.Reset);
    }

    /**
     * Displays that a hero was killed.
     */
    public static void heroKilled(Hero hero) {
        System.out.println(c.Red + c.Bold + "☠ " + hero.getName() + " has fallen in battle! ☠" + c.Reset);
    }

    /**
     * Displays that a hero has respawned.
     */
    public static void heroRespawned(Hero hero) {
        System.out.println(c.Green + "✦ " + hero.getName() + " has respawned at their Nexus! ✦" + c.Reset);
    }

    /**
     * Displays monster movement.
     */
    public static void monsterMoved(Monster monster, int fromRow, int toRow) {
        System.out.println(c.Red + monster.getName() + c.Reset + " advanced from row " + fromRow + " to row " + toRow + ".");
    }

    /**
     * Displays monster spawn notification.
     */
    public static void monstersSpawned(int count) {
        System.out.println();
        System.out.println(c.Red + c.Bold + "⚠ WARNING: " + count + " new monster(s) have spawned in the monsters' Nexus! ⚠" + c.Reset);
        System.out.println();
    }

    /**
     * Displays victory message.
     */
    public static void victory() {
        System.out.println();
        System.out.println(c.Green + c.Bold + "═══════════════════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println(c.Green + c.Bold + "                              VICTORY!                                      " + c.Reset);
        System.out.println(c.Green + c.Bold + "═══════════════════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println(c.Green + "          A hero has reached the monsters' Nexus!                          " + c.Reset);
        System.out.println(c.Green + "          The monsters have been vanquished!                               " + c.Reset);
        System.out.println(c.Green + c.Bold + "═══════════════════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println();
    }

    /**
     * Displays defeat message.
     */
    public static void defeat() {
        System.out.println();
        System.out.println(c.Red + c.Bold + "═══════════════════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println(c.Red + c.Bold + "                              DEFEAT!                                       " + c.Reset);
        System.out.println(c.Red + c.Bold + "═══════════════════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println(c.Red + "          A monster has reached your Nexus!                                " + c.Reset);
        System.out.println(c.Red + "          The heroes have fallen...                                        " + c.Reset);
        System.out.println(c.Red + c.Bold + "═══════════════════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println();
    }

    /**
     * Displays game quit message.
     */
    public static void quit() {
        System.out.println();
        System.out.println(c.Yellow + "Thanks for playing Legends of Valor!" + c.Reset);
        System.out.println();
    }

    /**
     * Displays available targets for attack.
     */
    public static void showTargets(List<Monster> monsters) {
        System.out.println(c.Yellow + "Select a target to attack:" + c.Reset);
        for (int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            System.out.println("  " + (i + 1) + ". " + c.Red + m.getName() + c.Reset +
                " (HP: " + m.getHp() + "/" + m.getMaxHp() + ", Lv." + m.getLevel() + ")");
        }
    }

    /**
     * Displays available spells.
     */
    public static void showSpells(Hero hero, List<Spell> spells) {
        System.out.println(c.Yellow + hero.getName() + ", select a spell to cast:" + c.Reset);
        for (int i = 0; i < spells.size(); i++) {
            Spell s = spells.get(i);
            System.out.println("  " + (i + 1) + ". " + c.Cyan + s.getName() + c.Reset +
                " (Damage: " + s.getDamage() + ", MP Cost: " + s.getManaCost() + ", Type: " + s.getType() + ")");
        }
        System.out.println("  Current MP: " + hero.getMp());
    }

    /**
     * Displays available potions.
     */
    public static void showPotions(Hero hero, List<Potion> potions) {
        System.out.println(c.Yellow + hero.getName() + ", select a potion to use:" + c.Reset);
        for (int i = 0; i < potions.size(); i++) {
            Potion p = potions.get(i);
            System.out.println("  " + (i + 1) + ". " + c.Magenta + p.getName() + c.Reset +
                " (+" + p.getAttributeIncrease() + " " + p.getAttributeAffected() + ")");
        }
    }

    /**
     * Displays equippable items.
     */
    public static void showEquippableItems(Hero hero, List<Item> items) {
        System.out.println(c.Yellow + hero.getName() + ", select an item to equip:" + c.Reset);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            String info;
            if (item instanceof Weapon) {
                Weapon w = (Weapon) item;
                info = "Weapon (Damage: " + w.getDamage() + ", Hands: " + w.getRequiredHands() + ")";
            } else if (item instanceof Armor) {
                Armor a = (Armor) item;
                info = "Armor (Defense: " + a.getDamageReduction() + ")";
            } else {
                info = "Unknown";
            }
            System.out.println("  " + (i + 1) + ". " + c.Yellow + item.getName() + c.Reset + " - " + info);
        }

        // Show currently equipped items
        System.out.println();
        System.out.println("  Currently equipped:");
        if (hero.getEquippedWeapon() != null) {
            System.out.println("    Weapon: " + hero.getEquippedWeapon().getName());
        } else {
            System.out.println("    Weapon: None");
        }
        if (hero.getEquippedArmor() != null) {
            System.out.println("    Armor: " + hero.getEquippedArmor().getName());
        } else {
            System.out.println("    Armor: None");
        }
    }

    /**
     * Displays heroes for teleport selection.
     */
    public static void showHeroesForTeleport(Hero currentHero, List<Hero> heroes, List<Integer> heroIndices) {
        System.out.println(c.Yellow + currentHero.getName() + ", select a hero to teleport to:" + c.Reset);
        for (int i = 0; i < heroes.size(); i++) {
            Hero h = heroes.get(i);
            System.out.println("  " + (i + 1) + ". " + c.Green + "H" + heroIndices.get(i) + " - " + h.getName() + c.Reset);
        }
    }

    /**
     * Displays rewards for killing a monster.
     */
    public static void showRewards(int gold, int exp) {
        System.out.println(c.Yellow + "  All heroes receive: " + gold + " gold and " + exp + " experience!" + c.Reset);
    }

    /**
     * Displays end of round regeneration.
     */
    public static void showRegeneration() {
        System.out.println();
        System.out.println(c.Cyan + "━━ End of Round: All heroes regenerate 10% HP and MP ━━" + c.Reset);
    }

    /**
     * Displays a dodge event.
     */
    public static void showDodge(String dodger, String attacker) {
        System.out.println(c.Yellow + dodger + " dodged " + attacker + "'s attack!" + c.Reset);
    }

    /**
     * Displays spell cast result with debuff.
     */
    public static void showSpellCast(Hero hero, Monster monster, Spell spell, int damage) {
        System.out.println(c.Cyan + hero.getName() + c.Reset + " cast " + c.Magenta + spell.getName() + c.Reset +
            " on " + c.Red + monster.getName() + c.Reset + " for " + c.Yellow + damage + c.Reset + " damage!");

        String debuffType = "";
        switch (spell.getType()) {
            case "Fire":
                debuffType = "reduced defense";
                break;
            case "Ice":
                debuffType = "reduced damage";
                break;
            case "Lightning":
                debuffType = "reduced dodge";
                break;
        }
        System.out.println("  " + c.Magenta + spell.getType() + " spell applied " + debuffType + " to " + monster.getName() + c.Reset);
    }

    /**
     * Displays potion used.
     */
    public static void showPotionUsed(Hero hero, Potion potion) {
        System.out.println(c.Green + hero.getName() + c.Reset + " used " + c.Magenta + potion.getName() + c.Reset +
            " and gained +" + potion.getAttributeIncrease() + " " + potion.getAttributeAffected() + ".");
    }

    /**
     * Displays item equipped.
     */
    public static void showItemEquipped(Hero hero, Item item) {
        System.out.println(c.Green + hero.getName() + c.Reset + " equipped " + c.Yellow + item.getName() + c.Reset + ".");
    }

    /**
     * Displays move result.
     */
    public static void showMoveResult(Hero hero, boolean success, char direction) {
        String dirName;
        switch (direction) {
            case 'W': dirName = "north"; break;
            case 'S': dirName = "south"; break;
            case 'A': dirName = "west"; break;
            case 'D': dirName = "east"; break;
            default: dirName = "unknown"; break;
        }

        if (success) {
            System.out.println(c.Green + hero.getName() + c.Reset + " moved " + dirName + ".");
        } else {
            System.out.println(c.Red + "Cannot move " + dirName + "! Path is blocked or invalid." + c.Reset);
        }
    }

    /**
     * Displays teleport result.
     */
    public static void showTeleportResult(Hero hero, Hero target, boolean success) {
        if (success) {
            System.out.println(c.Cyan + hero.getName() + c.Reset + " teleported to " + c.Green + target.getName() + c.Reset + "'s lane!");
        } else {
            System.out.println(c.Red + "Teleport failed! No valid position available." + c.Reset);
        }
    }

    /**
     * Displays recall result.
     */
    public static void showRecallResult(Hero hero, boolean success) {
        if (success) {
            System.out.println(c.Cyan + hero.getName() + c.Reset + " recalled to their Nexus!");
        } else {
            System.out.println(c.Red + "Recall failed! Nexus position is occupied." + c.Reset);
        }
    }

    /**
     * Displays no valid targets message.
     */
    public static void noTargetsInRange() {
        System.out.println(c.Red + "No enemies in range to attack!" + c.Reset);
    }

    /**
     * Displays no spells available message.
     */
    public static void noSpellsAvailable() {
        System.out.println(c.Red + "No spells available to cast!" + c.Reset);
    }

    /**
     * Displays insufficient mana message.
     */
    public static void insufficientMana(Spell spell, int currentMp) {
        System.out.println(c.Red + "Insufficient MP! Need " + spell.getManaCost() + " but only have " + currentMp + "." + c.Reset);
    }

    /**
     * Displays no potions available message.
     */
    public static void noPotionsAvailable() {
        System.out.println(c.Red + "No potions available to use!" + c.Reset);
    }

    /**
     * Displays no equippable items message.
     */
    public static void noEquippableItems() {
        System.out.println(c.Red + "No items available to equip!" + c.Reset);
    }

    /**
     * Displays cannot teleport message.
     */
    public static void cannotTeleport(String reason) {
        System.out.println(c.Red + "Cannot teleport: " + reason + c.Reset);
    }

    /**
     * Displays not at nexus for market message.
     */
    public static void notAtNexus() {
        System.out.println(c.Red + "You must be at your Nexus to access the market!" + c.Reset);
    }

    /**
     * Displays hero info.
     */
    public static void showHeroInfo(Hero hero, int heroIndex) {
        System.out.println();
        System.out.println(c.Green + c.Bold + "═══ HERO " + heroIndex + ": " + hero.getName() + " ═══" + c.Reset);
        System.out.println("  Level: " + hero.getLevel() + "  |  Exp: " + hero.getExperience());
        System.out.println("  HP: " + hero.getHp() + "/" + hero.getMaxHp() + "  |  MP: " + hero.getMp() + "/" + hero.getMaxMp());
        System.out.println("  Strength: " + hero.getStrength() + "  |  Dexterity: " + hero.getDexterity() + "  |  Agility: " + hero.getAgility());
        System.out.println("  Gold: " + hero.getGold());
        System.out.println("  Weapon: " + (hero.getEquippedWeapon() != null ? hero.getEquippedWeapon().getName() : "None"));
        System.out.println("  Armor: " + (hero.getEquippedArmor() != null ? hero.getEquippedArmor().getName() : "None"));
        System.out.println("  Inventory: " + hero.getInventory().size() + " items");
    }

    /**
     * Displays all game info (heroes and monsters).
     */
    public static void showAllInfo(List<Hero> heroes, EntityCollection monsters) {
        System.out.println();
        System.out.println(c.Bold + "═══════════════════════════════════════════════════════════════════════════" + c.Reset);
        System.out.println(c.Bold + "                           GAME STATUS                                     " + c.Reset);
        System.out.println(c.Bold + "═══════════════════════════════════════════════════════════════════════════" + c.Reset);

        System.out.println();
        System.out.println(c.Green + c.Bold + "───── HEROES ─────" + c.Reset);
        for (int i = 0; i < heroes.size(); i++) {
            showHeroInfo(heroes.get(i), i + 1);
        }

        System.out.println();
        System.out.println(c.Red + c.Bold + "───── MONSTERS ─────" + c.Reset);
        int monsterCount = 0;
        for (BoardEntity entity : monsters) {
            if (entity.isAlive()) {
                MonsterWrapper mw = (MonsterWrapper) entity;
                Monster m = mw.getMonster();
                monsterCount++;
                System.out.println("  " + mw.getEntityId() + ": " + m.getName() +
                    " (Lv." + m.getLevel() + ", HP: " + m.getHp() + "/" + m.getMaxHp() +
                    ", Row: " + mw.getRow() + ", Col: " + mw.getCol() + ")");
            }
        }
        if (monsterCount == 0) {
            System.out.println("  No monsters on the board.");
        }
        System.out.println();
    }

    /**
     * Displays a generic message.
     */
    public static void message(String msg) {
        System.out.println(msg);
    }

    /**
     * Displays obstacle removal result.
     */
    public static void showObstacleRemoved(Hero hero) {
        System.out.println(c.Green + hero.getName() + c.Reset + " removed an obstacle!");
    }

    /**
     * Displays no adjacent obstacle message.
     */
    public static void noAdjacentObstacle() {
        System.out.println(c.Red + "No removable obstacle adjacent to your position!" + c.Reset);
    }

    /**
     * Displays action cancelled.
     */
    public static void actionCancelled() {
        System.out.println(c.Yellow + "Action cancelled." + c.Reset);
    }

    /**
     * Displays pass turn message.
     */
    public static void passTurn(Hero hero) {
        System.out.println(c.Yellow + hero.getName() + " passes their turn." + c.Reset);
    }
}
