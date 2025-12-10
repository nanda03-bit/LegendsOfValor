/**
 * Filename: Battle.java
 * Author: Nandana Shashi
 * Date: 2025-Nov-05
 * Description: Handles battle interactions between heroes and monsters, including actions, turn flow, and combat mechanics.
 *
 */

package Battle;

import Player.Heroes.Hero;
import Player.Monsters.*;
import Display.*;
import Display.Statistics.StatisticsDisplay;
import Utilities.DataLoader;
import Utilities.Percentages;
import Utilities.GameConstants;
import ErrorMessages.*;
import Items.*;
import java.util.*;

public class Battle implements IBattle {
    private Map<Monster, SpellEffect> monsterDebuffs;
    private List<Hero> heroes;
    private List<Monster> monsters;
    private boolean surrendered = false;
    PrintErrorMessages print = new PrintErrorMessages();

    // Inner class used to store and manage temporary spell effects on a monster,
    // such as damage over time or reduced stats.
    private static class SpellEffect {
        double damageMultiplier = GameConstants.BASE_DAMAGE_MULTIPLIER;
        double defenseMultiplier = GameConstants.BASE_DAMAGE_MULTIPLIER;
        double dodgeMultiplier = GameConstants.BASE_DAMAGE_MULTIPLIER;
        int roundsRemaining = GameConstants.SPELL_DEBUFF_ROUNDS;
    }

    /**
     * Constructor for the Battle class.
     * @param heroes The list of heroes participating in the battle.
     * @return no return type
     */
    public Battle(List<Hero> heroes) {
        this.heroes = heroes;
        this.monsters = createMonsters();
        this.monsterDebuffs = new HashMap<>();
        // For each monster in the battle, creates a new spell effect
        for (Monster monster : monsters) {
            monsterDebuffs.put(monster, new SpellEffect());
        }
    }

    /**
     * Creates a list of monsters for the battle.
     * The number of monsters is equal to the number of heroes.
     * The level of the monsters is equal to the highest level of a hero in the party.
     * @return A list of monsters.
     */
    private List<Monster> createMonsters() {
        List<Monster> monsters = new ArrayList<>();
        int monsterCount = heroes.size();
        int monsterLevel = getHighestHeroLevel();

        // Loading Data
        List<String[]> dragonData = DataLoader.readData("Dragons.txt");
        List<String[]> exoskeletonData = DataLoader.readData("Exoskeletons.txt");
        List<String[]> spiritData = DataLoader.readData("Spirits.txt");

        // Percentages for Monsters
        double dragonProb = Percentages.DRAGON;
        double exoskeletonProb = Percentages.EXOSKELETON;

        for (int i = 0; i < monsterCount; i++) {
            double random = Math.random();

            // If the random value falls within the dragon probability range, then picks a random dragon, then cast to an int (random dragon added to list)
            if (random < dragonProb) {
                String[] dragon = dragonData.get( (int)(Math.random() * dragonData.size()));
                monsters.add(new Dragon(dragon[0], monsterLevel, Integer.parseInt(dragon[2]), Integer.parseInt(dragon[3]), Integer.parseInt(dragon[4])));
            }

            // If the random number wasn't small enough to create a Dragon, but is still within range of Dragon + Exoskeleton probability, then we create an Exoskeleton monster instead.
            else if (random < dragonProb + exoskeletonProb) {
                String[] exoskeleton = exoskeletonData.get((int) (Math.random() * exoskeletonData.size()));
                monsters.add(new Exoskeleton(exoskeleton[0], monsterLevel, Integer.parseInt(exoskeleton[2]), Integer.parseInt(exoskeleton[3]), Integer.parseInt(exoskeleton[4])));
            }

            // Last probablility is spirit monster
            else {
                String[] spirit = spiritData.get((int) (Math.random() * spiritData.size()));
                monsters.add(new Spirit(spirit[0], monsterLevel, Integer.parseInt(spirit[2]), Integer.parseInt(spirit[3]), Integer.parseInt(spirit[4])));
            }
        }
        // returns list of monster
        return monsters;
    }

    /**
     * Gets the highest level among the heroes in the party.
     * @return The highest hero level.
     */
    private int getHighestHeroLevel() {
        int maxLevel = 0;
        for (Hero hero : heroes) {
            if (hero.getLevel() > maxLevel) {
                maxLevel = hero.getLevel();
            }
        }
        return maxLevel;
    }

    /**
     * Starts the battle and continues until one side is defeated or the player surrenders.
     * @return no return type
     */
    public void start() {
        // Displays the start for the battle
        Display.battleStart();

        // loop to keep going for both monster and hero, if both heroes and monsters are alive then loop keeps going
        while (heroesAlive() && monstersAlive() && !surrendered) {
            //Hero starts first, then the monsters turn
            heroesTurn();
            if (!monstersAlive()) {
                break;
            }
            monstersTurn();
            endOfRound();
        }
        battleEnd();
    }

    /**
     * Manages the heroes' turn, allowing each living hero to perform an action.
     * @return no return type
     */
    private void heroesTurn() {
        // Goes through list of heroes player had chosen
        for (Hero hero : heroes) {
            // Flag to ensure if Player gives wrong input loops back
            boolean valid = false;
            if (hero.isAlive()) {
                while (!valid){
                    Display.heroTurn(hero);
                    // gets heroes action and inputs it to action
                    char action = Input.getBattleAction();
                    switch (action) {
                        case 'A':
                            attack(hero);
                            valid = true;
                            break;
                        case 'S':
                            if (castSpell(hero)) {
                            valid = true;
                            }
                            break;
                        case 'P':
                            if (usePotion(hero)) {
                            valid = true;
                            }
                            break;
                        case 'E':
                            if (equip(hero)) {
                            valid = true;
                            }
                            break;
                        case 'I':
                            StatisticsDisplay.displayBattleStats(heroes, monsters);
                            valid = true;
                            break;
                        case 'U':
                            surrendered = true;
                            valid = true;
                            break;
                        default:
                            print.invalidActionInput();
                            break;
                    }
                }
            }
        }
    }


    /**
     * Allows a hero to attack a monster.
     * @param hero The hero performing the attack.
     * @return no return type
     */
    private void attack(Hero hero) {
        // If no monsters alive then no attack
        if (!monstersAlive()) {
            return;
        }

        // If none are alive, stop the attack.
        List<Monster> aliveMonsters = getAliveMonsters();
        if (aliveMonsters.isEmpty()) {
            return;
        }

        // Ask the player to pick which monster to attack.
        Display.selectTarget(aliveMonsters);
        int targetIndex = Input.getTargetChoice(aliveMonsters.size());
        // If the user's input is wrong or is invalid, default to the first monster to make it easier and less complex
        if (targetIndex < 0 || targetIndex >= aliveMonsters.size()) {
            targetIndex = 0;
        }
        Monster target = aliveMonsters.get(targetIndex);
        
        // looks at targets dodge chances, if the probablitiy falls between the chances then returns true
        if (checkDodge(target, hero.getAgility())) {
            Display.dodgeMessage(target, hero);
            return;
        }

        // Calculate the hero's base damage: weapon damage + strength, adjusted by debuffs and a damage percentage.
        int weaponDamage;
        if (hero.getEquippedWeapon() != null) {
            weaponDamage = hero.getEquippedWeapon().getDamage();
        }
        else {
            weaponDamage = 0;
        }
        int baseDamage = (int) ((hero.getStrength() + weaponDamage) * hero.getAttackDebuff() * Percentages.DAMAGE);
        
        // Calculate the monster's effective defense against the attack, taking into account any active debuffs
        SpellEffect effect = monsterDebuffs.get(target);
        double defenseMult;
        if (effect != null) {
            defenseMult = effect.defenseMultiplier;
        }
        else {
            defenseMult = GameConstants.BASE_DAMAGE_MULTIPLIER;
        }
        // Calculating the monster's effective defense by multiplying its base defense withthe active defense multiplier
        int actualDefense = (int) (target.getDefense() * defenseMult);
        int finalDamage = Math.max(0, baseDamage - actualDefense);
        if (finalDamage == 0) {
            // To prevent the game getting stuck if both sides do 0 damage,
            // there's a small chance to still deal 1 damage.
            // 25% chance to do 1 damage to prevent stalemate
            if (Math.random() < 0.25) {
                finalDamage = 1;
            }
        }

        // Target will take damage and then the target is checked if live snd if alive then loop continues
        target.takeDamage(finalDamage);
        Display.attackResult(hero, target, finalDamage);
        if (!target.isAlive()) {
            Display.monsterDefeated(target);
        }
    }


    /**
     * Allows a hero to cast a spell on a monster.
     * @param hero The hero casting the spell.
     * @return true if the spell was successfully cast, false otherwise.
     */
    private boolean castSpell(Hero hero) {
        if (!monstersAlive()) return true;
        
        // going through the hero’s inventory and pulling out only the spells
        // that they are actually high enough level to use.
        List<Spell> availableSpells = new ArrayList<>();
        // Goes through heroes inventory one by one
        for (Item item : hero.getInventory()){
            // Checks if the item is a spell or not
            if (item instanceof Spell){
                Spell spell = (Spell) item;
                // Only add the spell to availableSpells if the hero's level is high enough
                if (spell.getRequiredLevel() <= hero.getLevel()) {
                    availableSpells.add(spell);
                }
            }
        }
        if (availableSpells.isEmpty()) {
            Display.noSpellsAvailable(hero);
            return false;
        }
        
        // Selecting the spells
        Display.selectSpell(hero, availableSpells);
        int spellIndex = Input.getSpellChoice(availableSpells.size());
        if (spellIndex < 0 || spellIndex >= availableSpells.size()) {
            Display.invalidSelection();
            return false;
        }
        Spell spell = availableSpells.get(spellIndex);
        
        // Checks MP
        if (hero.getMp() < spell.getManaCost()) {
            Display.insufficientMP(hero, spell);
            return false;
        }
        
        // Gets the alive monsters and puts into a list, iterates through the lost and shows which all targets can be selected
        List<Monster> aliveMonsters = getAliveMonsters();
        if (aliveMonsters.isEmpty()) {
            return true;
        }

        Display.selectTarget(aliveMonsters);
        int targetIndex = Input.getTargetChoice(aliveMonsters.size());
        if (targetIndex < 0 || targetIndex >= aliveMonsters.size()) {
            targetIndex = 0;
        }
        Monster target = aliveMonsters.get(targetIndex);
        
        // Check dodge
        if (checkDodge(target, hero.getAgility())) {
            Display.dodgeMessage(target, hero);
            hero.setMp(hero.getMp() - spell.getManaCost()); // Still consume MP
            return true;
        }
        
        // calculated spell damage using hero’s dexterity to give magic-focused heroes more impact
        double spellDamageMultiplier = 1 + ((double)hero.getDexterity() / 10000);
        int spellDamage = (int) (spell.getDamage() * spellDamageMultiplier);
        int baseDamage = spellDamage;
        
        // Apply defense
        SpellEffect effect = monsterDebuffs.get(target);
        if (effect == null) {
            effect = new SpellEffect();
            monsterDebuffs.put(target, effect);
        }
        double defenseMult = effect.defenseMultiplier;
        int actualDefense = (int) (target.getDefense() * defenseMult);
        int finalDamage = Math.max(0, baseDamage - actualDefense);
        
        target.takeDamage(finalDamage);
        hero.setMp(hero.getMp() - spell.getManaCost());
        // Apply spell debuffs based on type
        applySpellDebuff(target, spell.getType(), effect);
        Display.spellCastResult(hero, target, spell, finalDamage);
        
        if (!target.isAlive()) {
            Display.monsterDefeated(target);
        }
        return true;
    }


    /**
     * Applies a debuff to a monster based on the type of spell cast.
     * @param monster The monster to debuff.
     * @param spellType The type of the spell.
     * @param effect The SpellEffect object to modify.
     */
    private void applySpellDebuff(Monster monster, String spellType, SpellEffect effect) {
        // Fire spells weaken the monster's damage output
        if (GameConstants.SPELL_TYPE_FIRE.equals(spellType)) {
            effect.damageMultiplier = GameConstants.SPELL_DEBUFF_MULTIPLIER;
            effect.roundsRemaining = GameConstants.SPELL_DEBUFF_ROUNDS;
        }

        // Ice spells weaken the monster's defense
        else if (GameConstants.SPELL_TYPE_ICE.equals(spellType)) {
            effect.defenseMultiplier = GameConstants.SPELL_DEBUFF_MULTIPLIER;
            effect.roundsRemaining = GameConstants.SPELL_DEBUFF_ROUNDS;
        }

        // Lightning spells weaken the monster's dodge chance
        else if (GameConstants.SPELL_TYPE_LIGHTNING.equals(spellType)) {
            effect.dodgeMultiplier = GameConstants.SPELL_DEBUFF_MULTIPLIER;
            effect.roundsRemaining = GameConstants.SPELL_DEBUFF_ROUNDS;
        }
    }

    /**
     * Allows a hero to use a potion from their inventory.
     * @param hero The hero using the potion.
     * @return true if the potion was successfully used, false otherwise.
     */
    private boolean usePotion(Hero hero) {
        // Get available potions from inventory
        List<Potion> availablePotions = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Potion) {
                Potion potion = (Potion) item;
                if (potion.getRequiredLevel() <= hero.getLevel()) {
                    availablePotions.add(potion);
                }
            }
        }
        if (availablePotions.isEmpty()) {
            Display.noPotionsAvailable(hero);
            return false;
        }
        // Select potion
        Display.selectPotion(hero, availablePotions);
        int potionIndex = Input.getPotionChoice(availablePotions.size());
        if (potionIndex < 0 || potionIndex >= availablePotions.size()) {
            Display.invalidSelection();
            return false;
        }
        
        Potion potion = availablePotions.get(potionIndex);
        String attribute = potion.getAttributeAffected();
        int increase = potion.getAttributeIncrease();
        
        // Apply potion effects
        boolean used = false;
        if (attribute.contains("Health") || attribute.contains("All")) {
            int newHp = Math.min(hero.getHp() + increase, hero.getMaxHp());
            hero.setHp(newHp);
            used = true;
        }
        if (attribute.contains("Mana") || attribute.contains("All")) {
            int newMp = Math.min(hero.getMp() + increase, hero.getMaxMp());
            hero.setMp(newMp);
            used = true;
        }
        if (attribute.contains("Strength") || attribute.contains("All")) {
            hero.setStrength(hero.getStrength() + increase);
            used = true;
        }
        if (attribute.contains("Dexterity") || attribute.contains("All")) {
            hero.setDexterity(hero.getDexterity() + increase);
            used = true;
        }
        if (attribute.contains("Agility") || attribute.contains("All")) {
            hero.setAgility(hero.getAgility() + increase);
            used = true;
        }
        if (used){
            hero.getInventory().remove(potion);
            Display.potionUsed(hero, potion, attribute);
            return true;
        }
        return false;
    }


    /**
     * Allows a hero to equip a weapon or armor from their inventory.
     * @param hero The hero equipping the item.
     * @return true if the item was successfully equipped, false otherwise.
     */
    private boolean equip(Hero hero) {
        // Get equippable items from inventory
        List<Item> equippableItems = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if ((item instanceof Weapon || item instanceof Armor) && 
                item.getRequiredLevel() <= hero.getLevel()) {
                equippableItems.add(item);
            }
        }
        if (equippableItems.isEmpty()) {
            Display.noItemsToEquip(hero);
            return false;
        }
        // Display equippable items
        Display.selectEquipItem(hero, equippableItems);
        int itemIndex = Input.getEquipChoice(equippableItems.size());
        if (itemIndex < 0 || itemIndex >= equippableItems.size()) {
            Display.invalidSelection();
            return false;
        }
        
        Item item = equippableItems.get(itemIndex);
        
        // Equip the item
        if (item instanceof Weapon) {
            Weapon oldWeapon = hero.getEquippedWeapon();
            if (oldWeapon != null) {
                hero.getInventory().add(oldWeapon);
            }
            hero.equipWeapon((Weapon) item);
            hero.getInventory().remove(item);
            Display.weaponEquipped(hero, (Weapon) item);
        }
        else if (item instanceof Armor) {
            Armor oldArmor = hero.getEquippedArmor();
            if (oldArmor != null) {
                hero.getInventory().add(oldArmor);
            }
            hero.equipArmor((Armor) item);
            hero.getInventory().remove(item);
            Display.armorEquipped(hero, (Armor) item);
        }
        return true;
    }

    /**
     * Manages the monsters' turn, where each living monster attacks a random hero.
     */
    private void monstersTurn() {
        List<Hero> aliveHeroes = getAliveHeroes();
        if (aliveHeroes.isEmpty()) return;
        
        for (Monster monster : monsters) {
            if (monster.isAlive()) {
                // Select random alive hero as target
                Hero target = aliveHeroes.get((int) (Math.random() * aliveHeroes.size()));
                
                // Check dodge based on hero agility
                if (checkHeroDodge(target, monster.getDodgeChance())) {
                    Display.heroDodgeMessage(target, monster);
                    continue;
                }
                
                // Calculate damage
                SpellEffect effect = monsterDebuffs.get(monster);
                // Determines the damage multiplier based on any debuffs applied to the monster
                double damageMult;
                // If there is a debuff, use its multiplier, otherwise uses the defaukt multipleier
                if (effect != null) {
                    damageMult = effect.damageMultiplier;
                }
                else {
                    damageMult = GameConstants.BASE_DAMAGE_MULTIPLIER;
                }
                int baseDamage = (int) (monster.getBaseDamage() * damageMult * Percentages.DAMAGE);

                // Initialize armor reduction value
                int armorReduction = 0;
                // Check if the target hero has armor equipped
                if (target.getEquippedArmor() != null) {
                    armorReduction = target.getEquippedArmor().getDamageReduction();
                }

                // Calculate the final damage after applying armor reduction, cannot go below 0
                int finalDamage = Math.max(0, baseDamage - armorReduction);
                // If the final damage is 0, we add a small chance to deal 1 damage
                // This prevents a fight from completely stalling when armor negates all damage
                if (finalDamage == 0) {
                    // 25% chance to do 1 damage to prevent stalemate
                    if (Math.random() < 0.25) {
                        finalDamage = 1; //applies just one point of dammage
                    }
                }
                target.takeDamage(finalDamage);
                Display.monsterAttackResult(monster, target, finalDamage);
                
                if (!target.isAlive()) {
                    Display.heroDefeated(target);
                }
            }
        }
    }
    
    /**
     * Checks if a monster successfully dodges an attack.
     * @param monster The monster being attacked.
     * @param attackerAgility The agility of the attacker.
     * @return true if the monster dodges, false otherwise.
     */
    private boolean checkDodge(Monster monster, int attackerAgility) {
        SpellEffect effect = monsterDebuffs.get(monster);
        double dodgeMult;
        if (effect != null) {
            dodgeMult = effect.dodgeMultiplier;
        }
        else {
            dodgeMult = GameConstants.BASE_DAMAGE_MULTIPLIER;
        }
        return Math.random() < (monster.getDodgeChance() * 0.01 * dodgeMult);
    }
    
    /**
     * Checks if a hero successfully dodges an attack.
     * @param hero The hero being attacked.
     * @param monsterDodge The dodge chance of the attacking monster.
     * @return true if the hero dodges, false otherwise.
     */
    private boolean checkHeroDodge(Hero hero, int monsterDodge) {
        return Math.random() < (hero.getAgility() * 0.002 * hero.getDodgeDebuff());
    }
    
    /**
     * Gets a list of all living monsters.
     * @return A list of alive monsters.
     */
    private List<Monster> getAliveMonsters() {
        List<Monster> alive = new ArrayList<>();
        for (Monster monster : monsters) {
            if (monster.isAlive()) {
                alive.add(monster);
            }
        }
        return alive;
    }
    
    /**
     * Gets a list of all living heroes.
     * @return A list of alive heroes.
     */
    private List<Hero> getAliveHeroes() {
        List<Hero> alive = new ArrayList<>();
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                alive.add(hero);
            }
        }
        return alive;
    }

    /**
     * Performs end-of-round actions, such as HP/MP regeneration and debuff countdown.
     */
    private void endOfRound() {
        // Regenerate HP and MP for alive heroes
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                int hpRegen = Math.max(GameConstants.MIN_REGENERATION, (int) (hero.getHp() * GameConstants.REGENERATION_PERCENTAGE));
                int mpRegen = Math.max(GameConstants.MIN_REGENERATION, (int) (hero.getMp() * GameConstants.REGENERATION_PERCENTAGE));
                hero.setHp(Math.min(hero.getHp() + hpRegen, hero.getMaxHp()));
                hero.setMp(Math.min(hero.getMp() + mpRegen, hero.getMaxMp()));
            }
        }

        // Create an iterator to go through all the monsters that currently have debuffs applied
        Iterator<Map.Entry<Monster, SpellEffect>> iterator = monsterDebuffs.entrySet().iterator();
        // Loop through each entry in the map
        while (iterator.hasNext()) {
            // Get the next monster + its debuff effect
            Map.Entry<Monster, SpellEffect> entry = iterator.next();
            SpellEffect effect = entry.getValue();  // Extract the debuff information for this monster
            // Reduces the number of turns the debuff will last by 1
            effect.roundsRemaining--;
            if (effect.roundsRemaining <= 0) {
                // If the debuff duration is over, reset all multipliers to normal, everything is put back to normal
                effect.damageMultiplier = GameConstants.BASE_DAMAGE_MULTIPLIER;
                effect.defenseMultiplier = GameConstants.BASE_DAMAGE_MULTIPLIER;
                effect.dodgeMultiplier = GameConstants.BASE_DAMAGE_MULTIPLIER;
            }
        }
    }

    /**
     * Handles the end of the battle, including rewards for winning or penalties for losing.
     */
    private void battleEnd() {
        if (surrendered) {
            Display.battleSurrender();
            for (Hero hero : heroes) {
                hero.applySurrenderDebuff();
            }
            return;
        }
        if (heroesAlive()) {
            Display.battleWin();
            int highestLevel = getHighestHeroLevel();
            int monsterCount = monsters.size();
            
            for (Hero hero : heroes) {
                if (hero.isAlive()) {
                    // Living heroes get XP and gold
                    int experienceGained = monsterCount * GameConstants.EXPERIENCE_PER_MONSTER;
                    int goldGained = highestLevel * GameConstants.GOLD_PER_LEVEL;
                    hero.addExperience(experienceGained);
                    hero.setGold(hero.getGold() + goldGained);
                    Display.heroRewards(hero, experienceGained, goldGained);
                }
                else {
                    // Fainted heroes revive with half HP and MP but get no rewards
                    hero.setHp(Math.max(1, (int)(hero.getMaxHp() * GameConstants.DEATH_HP_PENALTY)));
                    hero.setMp(Math.max(1, (int)(hero.getMaxMp() * GameConstants.DEATH_MP_PENALTY)));
                    Display.heroRevived(hero);
                }
            }
        }
        else {
            Display.battleLose();
            // All heroes fainted - game over
        }
    }

    /**
     * Checks if there are any living heroes in the party.
     * @return true if at least one hero is alive, false otherwise.
     */
    private boolean heroesAlive() {
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there are any living monsters.
     * @return true if at least one monster is alive, false otherwise.
     */
    private boolean monstersAlive() {
        for (Monster monster : monsters) {
            if (monster.isAlive()) {
                return true;
            }
        }
        return false;
    }
}
