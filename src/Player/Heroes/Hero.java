/**
 * Filename: Hero.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-08
 * Description: Abstract class defining attributes and behaviors shared by all hero types.
 */

 package Player.Heroes;

import Player.Character;
import Items.*;
import Utilities.MonstersAndHeroesGameConstants;

import java.util.*;

public class Hero extends Character{
    private int mp;
    private int strength;
    private int dexterity;
    private int agility;
    private int gold;
    private int experience;
    private List<Item> inventory;
    private Weapon equippedWeapon;
    private Armor equippedArmor;
    private int maxMp;
    private double attackDebuff = 1.0;
    private double dodgeDebuff = 1.0;
    private int debuffDuration = 0;

    /**
     * Constructor for the Hero class.
     * @param name The name of the hero.
     * @param level The starting level of the hero.
     * @param hp The starting health points of the hero.
     * @param mp The starting mana points of the hero.
     * @param strength The starting strength of the hero.
     * @param dexterity The starting dexterity of the hero.
     * @param agility The starting agility of the hero.
     * @param gold The starting gold of the hero.
     * @param experience The starting experience of the hero.
     */
    public Hero(String name, int level, int hp, int mp, int strength, int dexterity, int agility, int gold, int experience) {
        super(name, level, hp);
        this.mp = mp;
        this.maxMp = mp;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
        this.gold = gold;
        this.experience = experience;
        this.inventory = new ArrayList<>();
    }

    /**
     * Gets the current mana points of the hero.
     * @return The current mana points.
     */
    public int getMp(){
        return mp;
    }

    /**
     * Gets the maximum mana points of the hero.
     * @return The maximum mana points.
     */
    public int getMaxMp(){
        return maxMp;
    }

    /**
     * Gets the strength of the hero.
     * @return The strength of the hero.
     */
    public int getStrength(){
        return strength;
    }

    /**
     * Gets the dexterity of the hero.
     * @return The dexterity of the hero.
     */
    public int getDexterity(){
        return dexterity;
    }

    /**
     * Gets the agility of the hero.
     * @return The agility of the hero.
     */
    public int getAgility(){
        return agility;
    }

    /**
     * Gets the amount of gold the hero has.
     * @return The amount of gold.
     */
    public int getGold(){
        return gold;
    }

    /**
     * Gets the experience points of the hero.
     * @return The experience points.
     */
    public int getExperience(){
        return experience;
    }

    /**
     * Gets the inventory of the hero.
     * @return A list of items in the hero's inventory.
     */
    public List<Item> getInventory(){
        return inventory;
    }

    /**
     * Gets the currently equipped weapon of the hero.
     * @return The equipped weapon, or null if no weapon is equipped.
     */
    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    /**
     * Gets the currently equipped armor of the hero.
     * @return The equipped armor, or null if no armor is equipped.
     */
    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    /**
     * Gets the attack debuff multiplier for the hero.
     * @return The attack debuff multiplier.
     */
    public double getAttackDebuff() {
        return attackDebuff;
    }

    /**
     * Gets the dodge debuff multiplier for the hero.
     * @return The dodge debuff multiplier.
     */
    public double getDodgeDebuff() {
        return dodgeDebuff;
    }

    /**
     * Sets the current mana points of the hero.
     * @param mp The new mana points value.
     */
    public void setMp(int mp) {
        this.mp = mp;
    }

    /**
     * Sets the strength of the hero.
     * @param strength The new strength value.
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * Sets the dexterity of the hero.
     * @param dexterity The new dexterity value.
     */
    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    /**
     * Sets the agility of the hero.
     * @param agility The new agility value.
     */
    public void setAgility(int agility) {
        this.agility = agility;
    }

    /**
     * Sets the amount of gold the hero has.
     * @param gold The new amount of gold.
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * Adds experience points to the hero and levels up if the experience threshold is met.
     * @param experience The amount of experience to add.
     */
    public void addExperience(int experience) {
        this.experience += experience;
        // Check for level up repeatedly until no more level ups are possible
        while (this.experience >= getLevel() * MonstersAndHeroesGameConstants.EXPERIENCE_THRESHOLD_MULTIPLIER) {
            levelUp();
        }
    }

    /**
     * Levels up the hero, increasing their stats and resetting their HP and MP.
     */
    public void levelUp() {
        int oldLevel = getLevel();
        super.levelUp();
        int newLevel = getLevel();
        
        // Reset HP to level * HP_PER_LEVEL
        setMaxHp(newLevel * MonstersAndHeroesGameConstants.HP_PER_LEVEL);
        setHp(getMaxHp());
        
        // Increase MP by MP_INCREASE_ON_LEVEL_UP
        maxMp = (int) (maxMp * MonstersAndHeroesGameConstants.MP_INCREASE_ON_LEVEL_UP);
        setMp(maxMp);
        
        // Stat increases are handled in subclasses
        System.out.println(getName() + " leveled up from " + oldLevel + " to " + newLevel + "!");
        System.out.println();
    }

    /**
     * Equips a weapon to the hero.
     * @param weapon The weapon to equip.
     */
    public void equipWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
    }

    /**
     * Equips an armor to the hero.
     * @param armor The armor to equip.
     */
    public void equipArmor(Armor armor) {
        this.equippedArmor = armor;
    }

    /**
     * Buys an item and adds it to the hero's inventory.
     * @param item The item to buy.
     */
    public void buyItem(Item item) {
        this.gold -= item.getPrice();
        this.inventory.add(item);
    }

    /**
     * Sells an item from the hero's inventory.
     * @param item The item to sell.
     */
    public void sellItem(Item item) {
        this.gold += item.getPrice() / MonstersAndHeroesGameConstants.SELL_PRICE_DIVISOR;
        this.inventory.remove(item);
    }

    /**
     * Calculates the experience points gained from defeating a number of monsters.
     * @param count The number of monsters defeated.
     * @return The total experience points gained.
     */
    public int xpForMonsters(int count) {
        return count * MonstersAndHeroesGameConstants.EXPERIENCE_PER_MONSTER;
    }

    /**
     * Calculates the gold reward based on the highest monster level in a battle.
     * @param highestLevel The highest level of a monster in the battle.
     * @return The amount of gold rewarded.
     */
    public int goldReward(int highestLevel) {
        return highestLevel * MonstersAndHeroesGameConstants.GOLD_PER_LEVEL;
    }

    /**
     * Calculates the hero's HP after being revived.
     * @return The HP after revival.
     */
    public int hpAfterDeath() {
        return (int)(getMaxHp() * MonstersAndHeroesGameConstants.DEATH_HP_PENALTY);
    }

    /**
     * Calculates the hero's MP after being revived.
     * @return The MP after revival.
     */
    public int mpAfterDeath() {
        return (int)(getMaxMp() * MonstersAndHeroesGameConstants.DEATH_MP_PENALTY);
    }

    /**
     * Applies a surrender debuff to the hero.
     * The debuff lasts for a random number of battles between 1 and 3.
     */
    public void applySurrenderDebuff() {
        this.attackDebuff = 0.9; // -10% attack
        this.dodgeDebuff = 0.85; // -15% dodge
        this.debuffDuration = new Random().nextInt(3) + 1; // 1-3 battles
        System.out.println(getName() + " has been demoralized and will be weaker for " + debuffDuration + " battles!");
    }

    /**
     * Decrements the debuff duration and removes the debuff if the duration is over.
     */
    public void decrementDebuffDuration() {
        if (debuffDuration > 0) {
            debuffDuration--;
            if (debuffDuration == 0) {
                this.attackDebuff = 1.0;
                this.dodgeDebuff = 1.0;
                System.out.println(getName() + " has recovered their morale!");
            }
        }
    }
}
