/**
 * Filename: ValorBattle.java
 * Author: Gowrav
 * Date: 2025-Dec
 * Description: Handles all combat mechanics, hero actions, and monster AI for Legends of Valor.
 * Implements the battle system interface for the Valor game mode.
 */

package Battle;

import Player.Heroes.*;
import Player.Monsters.*;
import board.valor.*;
import board.common.BoardEntity;
import Wrapper.HeroWrapper;
import Wrapper.MonsterWrapper;
import Items.*;
import Display.Valor.*;
import Utilities.*;
import Factories.MonsterFactory;
import Market.Market;

import java.util.*;

public class ValorBattle {
    
    private ValorBoard board;
    private List<Hero> heroes;
    private List<Integer> heroLanes;
    private Map<Monster, SpellEffect> monsterDebuffs;
    private int currentRound;
    private int monsterSpawnInterval;
    private int monsterCounter;
    private boolean gameOver;
    private boolean heroesWon;
    
    /**
     * Inner class for tracking spell debuffs on monsters.
     */
    private static class SpellEffect {
        double damageMultiplier = 1.0;
        double defenseMultiplier = 1.0;
        double dodgeMultiplier = 1.0;
        int roundsRemaining = 0;
    }
    
    /**
     * Constructor for ValorBattle.
     * @param board The game board.
     * @param heroes The list of heroes.
     * @param heroLanes The lane assignments for each hero.
     * @param difficulty The difficulty level (1=Easy, 2=Medium, 3=Hard).
     */
    public ValorBattle(ValorBoard board, List<Hero> heroes, List<Integer> heroLanes, int difficulty) {
        this.board = board;
        this.heroes = heroes;
        this.heroLanes = heroLanes;
        this.monsterDebuffs = new HashMap<>();
        this.currentRound = 0;
        this.monsterCounter = 3; // Start at 3 since initial monsters are 1, 2, 3
        this.gameOver = false;
        this.heroesWon = false;
        
        // Initialize debuff maps for existing monsters on board
        initializeExistingMonsters();
        
        switch (difficulty) {
            case ValorGameConstants.DIFFICULTY_EASY:
                this.monsterSpawnInterval = ValorGameConstants.MONSTER_SPAWN_INTERVAL_EASY;
                break;
            case ValorGameConstants.DIFFICULTY_MEDIUM:
                this.monsterSpawnInterval = ValorGameConstants.MONSTER_SPAWN_INTERVAL_MEDIUM;
                break;
            case ValorGameConstants.DIFFICULTY_HARD:
                this.monsterSpawnInterval = ValorGameConstants.MONSTER_SPAWN_INTERVAL_HARD;
                break;
            default:
                this.monsterSpawnInterval = ValorGameConstants.DEFAULT_SPAWN_INTERVAL;
        }
    }
    
    /**
     * Initializes debuff tracking for monsters already on the board.
     */
    private void initializeExistingMonsters() {
        for (BoardEntity entity : board.getMonsters()) {
            if (entity instanceof MonsterWrapper) {
                Monster monster = ((MonsterWrapper) entity).getMonster();
                if (!monsterDebuffs.containsKey(monster)) {
                    monsterDebuffs.put(monster, new SpellEffect());
                }
            }
        }
    }
    
    /**
     * Executes a full game round: hero turns -> monster turns -> end of round.
     * @return true if the game should continue, false if game is over.
     */
    public boolean executeRound() {
        currentRound++;
        ValorDisplay.showRound(currentRound);
        
        // Display the board
        board.printBoard();
        
        // Heroes' turn
        heroTurn();
        if (gameOver) {
            return false;
        }
        
        // Check for victory after heroes move
        if (checkVictory()) {
            gameOver = true;
            heroesWon = true;
            return false;
        }
        
        // Monsters' turn
        monsterTurn();
        if (gameOver) {
            return false;
        }
        
        // Check for defeat after monsters move
        if (checkDefeat()) {
            gameOver = true;
            heroesWon = false;
            return false;
        }
        
        // End of round processing
        endOfRound();
        
        // Spawn new monsters if interval reached
        if (currentRound % monsterSpawnInterval == 0) {
            spawnMonsters();
        }
        
        return true;
    }
    
    /**
     * Manages heroes' turns - each hero takes one action.
     */
    public void heroTurn() {
        for (int i = 0; i < heroes.size(); i++) {
            Hero hero = heroes.get(i);
            int heroIndex = i + 1;
            
            // Check if hero needs to respawn
            if (!hero.isAlive()) {
                respawnHero(hero, i);
                continue; // Skip turn for respawned heroes
            }
            
            ValorDisplay.heroTurnStart(hero, heroIndex);
            
            boolean actionTaken = false;
            while (!actionTaken && !gameOver) {
                char action = ValorInput.getHeroAction();
                
                switch (action) {
                    case 'A': // Attack
                        actionTaken = handleAttack(hero, i);
                        break;
                    case 'C': // Cast Spell
                        actionTaken = handleCastSpell(hero, i);
                        break;
                    case 'P': // Use Potion
                        actionTaken = handleUsePotion(hero);
                        break;
                    case 'E': // Equip Item
                        actionTaken = handleEquip(hero);
                        break;
                    case 'M': // Move
                        actionTaken = handleMove(hero);
                        if (actionTaken) {
                            board.printBoard();
                        }
                        break;
                    case 'T': // Teleport
                        actionTaken = handleTeleport(hero, i);
                        if (actionTaken) {
                            board.printBoard();
                        }
                        break;
                    case 'R': // Recall
                        actionTaken = handleRecall(hero);
                        if (actionTaken) {
                            board.printBoard();
                        }
                        break;
                    case 'B': // Market (Buy/Sell)
                        handleMarket(hero); // Market doesn't consume action
                        break;
                    case 'O': // Remove Obstacle
                        actionTaken = handleRemoveObstacle(hero);
                        if (actionTaken) {
                            board.printBoard();
                        }
                        break;
                    case 'I': // Info
                        ValorDisplay.showAllInfo(heroes, board.getMonsters());
                        board.printBoard();
                        break;
                    case 'X': // Pass turn
                        ValorDisplay.passTurn(hero);
                        actionTaken = true;
                        break;
                    case 'Q': // Quit
                        gameOver = true;
                        heroesWon = false;
                        return;
                }
                
                // Check for victory after each action
                if (actionTaken && checkVictory()) {
                    gameOver = true;
                    heroesWon = true;
                    return;
                }
            }
        }
    }
    
    /**
     * Manages monsters' turns - each monster attacks if possible, otherwise moves.
     */
    public void monsterTurn() {
        ValorDisplay.monstersTurnStart();
        
        List<BoardEntity> monsterList = board.getMonsters().getAliveEntities();
        
        for (BoardEntity entity : monsterList) {
            if (!entity.isAlive()) continue;
            
            MonsterWrapper mw = (MonsterWrapper) entity;
            Monster monster = mw.getMonster();
            
            // Check if any hero is in range
            List<BoardEntity> heroesInRange = board.getEntitiesInRange(entity);
            
            if (!heroesInRange.isEmpty()) {
                // Attack a random hero in range
                BoardEntity targetEntity = heroesInRange.get((int)(Math.random() * heroesInRange.size()));
                HeroWrapper hw = (HeroWrapper) targetEntity;
                Hero targetHero = hw.getHero();
                
                monsterAttack(monster, targetHero, mw);
            } else {
                // Move forward (south)
                int oldRow = mw.getRow();
                boolean moved = board.moveMonster(mw);
                
                if (moved) {
                    ValorDisplay.monsterMoved(monster, oldRow, mw.getRow());
                    board.printBoard();
                    
                    // Check for defeat (monster reached heroes' nexus)
                    if (mw.getRow() >= ValorGameConstants.HEROES_NEXUS_ROW) {
                        gameOver = true;
                        heroesWon = false;
                        return;
                    }
                }
            }
        }
    }
    
    /**
     * Handles a monster attacking a hero.
     */
    private void monsterAttack(Monster monster, Hero hero, MonsterWrapper mw) {
        // Check hero dodge
        double dodgeChance = hero.getAgility() * ValorGameConstants.HERO_DODGE_MULTIPLIER * hero.getDodgeDebuff();
        if (Math.random() < dodgeChance) {
            ValorDisplay.showDodge(hero.getName(), monster.getName());
            return;
        }
        
        // Calculate damage
        SpellEffect effect = monsterDebuffs.get(monster);
        double damageMult = (effect != null) ? effect.damageMultiplier : 1.0;
        
        int baseDamage = (int)(monster.getBaseDamage() * damageMult * ValorGameConstants.DAMAGE_MULTIPLIER);
        
        // Apply armor reduction
        int armorReduction = 0;
        if (hero.getEquippedArmor() != null) {
            armorReduction = hero.getEquippedArmor().getDamageReduction();
        }
        
        int finalDamage = Math.max(0, baseDamage - armorReduction);
        
        // Prevent stalemate
        if (finalDamage == 0 && Math.random() < 0.25) {
            finalDamage = 1;
        }
        
        hero.takeDamage(finalDamage);
        ValorDisplay.monsterAttackResult(monster, hero, finalDamage);
        
        if (!hero.isAlive()) {
            ValorDisplay.heroKilled(hero);
        }
    }
    
    /**
     * Handles hero attack action.
     * @return true if action was taken.
     */
    private boolean handleAttack(Hero hero, int heroIndex) {
        HeroWrapper hw = board.getHeroWrapper(hero);
        List<BoardEntity> monstersInRange = board.getEntitiesInRange(hw);
        
        if (monstersInRange.isEmpty()) {
            ValorDisplay.noTargetsInRange();
            return false;
        }
        
        // Convert to monster list for display
        List<Monster> targets = new ArrayList<>();
        for (BoardEntity entity : monstersInRange) {
            if (entity instanceof MonsterWrapper) {
                targets.add(((MonsterWrapper) entity).getMonster());
            }
        }
        
        ValorDisplay.showTargets(targets);
        int choice = ValorInput.getTargetChoice(targets.size());
        
        if (choice < 0) {
            ValorDisplay.actionCancelled();
            return false;
        }
        
        Monster target = targets.get(choice);
        
        // Check monster dodge
        SpellEffect effect = monsterDebuffs.get(target);
        double dodgeMult = (effect != null) ? effect.dodgeMultiplier : 1.0;
        double dodgeChance = target.getDodgeChance() * ValorGameConstants.MONSTER_DODGE_MULTIPLIER * dodgeMult;
        
        if (Math.random() < dodgeChance) {
            ValorDisplay.showDodge(target.getName(), hero.getName());
            return true;
        }
        
        // Calculate damage
        int weaponDamage = (hero.getEquippedWeapon() != null) ? hero.getEquippedWeapon().getDamage() : 0;
        int baseDamage = (int)((hero.getStrength() + weaponDamage) * hero.getAttackDebuff() * ValorGameConstants.DAMAGE_MULTIPLIER);
        
        // Apply defense
        double defenseMult = (effect != null) ? effect.defenseMultiplier : 1.0;
        int actualDefense = (int)(target.getDefense() * defenseMult);
        
        int finalDamage = Math.max(0, baseDamage - actualDefense);
        
        // Prevent stalemate
        if (finalDamage == 0 && Math.random() < 0.25) {
            finalDamage = 1;
        }
        
        target.takeDamage(finalDamage);
        ValorDisplay.heroAttackResult(hero, target, finalDamage);
        
        if (!target.isAlive()) {
            ValorDisplay.monsterKilled(target);
            distributeRewards(target);
            removeDeadMonster(target);
        }
        
        return true;
    }
    
    /**
     * Handles hero cast spell action.
     * @return true if action was taken.
     */
    private boolean handleCastSpell(Hero hero, int heroIndex) {
        // Get available spells
        List<Spell> availableSpells = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Spell && item.getRequiredLevel() <= hero.getLevel()) {
                availableSpells.add((Spell) item);
            }
        }
        
        if (availableSpells.isEmpty()) {
            ValorDisplay.noSpellsAvailable();
            return false;
        }
        
        HeroWrapper hw = board.getHeroWrapper(hero);
        List<BoardEntity> monstersInRange = board.getEntitiesInRange(hw);
        
        if (monstersInRange.isEmpty()) {
            ValorDisplay.noTargetsInRange();
            return false;
        }
        
        // Show spells
        ValorDisplay.showSpells(hero, availableSpells);
        int spellChoice = ValorInput.getSpellChoice(availableSpells.size());
        
        if (spellChoice < 0) {
            ValorDisplay.actionCancelled();
            return false;
        }
        
        Spell spell = availableSpells.get(spellChoice);
        
        // Check mana
        if (hero.getMp() < spell.getManaCost()) {
            ValorDisplay.insufficientMana(spell, hero.getMp());
            return false;
        }
        
        // Select target
        List<Monster> targets = new ArrayList<>();
        for (BoardEntity entity : monstersInRange) {
            if (entity instanceof MonsterWrapper) {
                targets.add(((MonsterWrapper) entity).getMonster());
            }
        }
        
        ValorDisplay.showTargets(targets);
        int targetChoice = ValorInput.getTargetChoice(targets.size());
        
        if (targetChoice < 0) {
            ValorDisplay.actionCancelled();
            return false;
        }
        
        Monster target = targets.get(targetChoice);
        
        // Consume mana
        hero.setMp(hero.getMp() - spell.getManaCost());
        
        // Check dodge
        SpellEffect effect = monsterDebuffs.get(target);
        if (effect == null) {
            effect = new SpellEffect();
            monsterDebuffs.put(target, effect);
        }
        
        double dodgeMult = effect.dodgeMultiplier;
        double dodgeChance = target.getDodgeChance() * ValorGameConstants.MONSTER_DODGE_MULTIPLIER * dodgeMult;
        
        if (Math.random() < dodgeChance) {
            ValorDisplay.showDodge(target.getName(), hero.getName());
            return true; // Mana still consumed
        }
        
        // Calculate spell damage
        double spellDamageMultiplier = 1.0 + (hero.getDexterity() / ValorGameConstants.SPELL_DEXTERITY_DIVISOR);
        int spellDamage = (int)(spell.getDamage() * spellDamageMultiplier);
        
        // Apply defense
        int actualDefense = (int)(target.getDefense() * effect.defenseMultiplier);
        int finalDamage = Math.max(0, spellDamage - actualDefense);
        
        target.takeDamage(finalDamage);
        
        // Apply spell debuff
        applySpellDebuff(target, spell.getType(), effect);
        
        ValorDisplay.showSpellCast(hero, target, spell, finalDamage);
        
        if (!target.isAlive()) {
            ValorDisplay.monsterKilled(target);
            distributeRewards(target);
            removeDeadMonster(target);
        }
        
        // Remove spell from inventory (single use)
        hero.getInventory().remove(spell);
        
        return true;
    }
    
    /**
     * Applies spell debuff to monster.
     */
    private void applySpellDebuff(Monster monster, String spellType, SpellEffect effect) {
        effect.roundsRemaining = MonstersAndHeroesGameConstants.SPELL_DEBUFF_ROUNDS;
        
        switch (spellType) {
            case MonstersAndHeroesGameConstants.SPELL_TYPE_FIRE:
                effect.defenseMultiplier = MonstersAndHeroesGameConstants.SPELL_DEBUFF_MULTIPLIER;
                break;
            case MonstersAndHeroesGameConstants.SPELL_TYPE_ICE:
                effect.damageMultiplier = MonstersAndHeroesGameConstants.SPELL_DEBUFF_MULTIPLIER;
                break;
            case MonstersAndHeroesGameConstants.SPELL_TYPE_LIGHTNING:
                effect.dodgeMultiplier = MonstersAndHeroesGameConstants.SPELL_DEBUFF_MULTIPLIER;
                break;
        }
    }
    
    /**
     * Handles hero use potion action.
     * @return true if action was taken.
     */
    private boolean handleUsePotion(Hero hero) {
        List<Potion> availablePotions = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Potion && item.getRequiredLevel() <= hero.getLevel()) {
                availablePotions.add((Potion) item);
            }
        }
        
        if (availablePotions.isEmpty()) {
            ValorDisplay.noPotionsAvailable();
            return false;
        }
        
        ValorDisplay.showPotions(hero, availablePotions);
        int choice = ValorInput.getPotionChoice(availablePotions.size());
        
        if (choice < 0) {
            ValorDisplay.actionCancelled();
            return false;
        }
        
        Potion potion = availablePotions.get(choice);
        applyPotion(hero, potion);
        hero.getInventory().remove(potion);
        ValorDisplay.showPotionUsed(hero, potion);
        
        return true;
    }
    
    /**
     * Applies potion effect to hero.
     */
    private void applyPotion(Hero hero, Potion potion) {
        String attribute = potion.getAttributeAffected();
        int increase = potion.getAttributeIncrease();
        
        if (attribute.contains("Health") || attribute.contains("All")) {
            int newHp = Math.min(hero.getHp() + increase, hero.getMaxHp());
            hero.setHp(newHp);
        }
        if (attribute.contains("Mana") || attribute.contains("All")) {
            int newMp = Math.min(hero.getMp() + increase, hero.getMaxMp());
            hero.setMp(newMp);
        }
        if (attribute.contains("Strength") || attribute.contains("All")) {
            hero.setStrength(hero.getStrength() + increase);
        }
        if (attribute.contains("Dexterity") || attribute.contains("All")) {
            hero.setDexterity(hero.getDexterity() + increase);
        }
        if (attribute.contains("Agility") || attribute.contains("All")) {
            hero.setAgility(hero.getAgility() + increase);
        }
    }
    
    /**
     * Handles hero equip item action.
     * @return true if action was taken.
     */
    private boolean handleEquip(Hero hero) {
        List<Item> equippableItems = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if ((item instanceof Weapon || item instanceof Armor) && item.getRequiredLevel() <= hero.getLevel()) {
                equippableItems.add(item);
            }
        }
        
        if (equippableItems.isEmpty()) {
            ValorDisplay.noEquippableItems();
            return false;
        }
        
        ValorDisplay.showEquippableItems(hero, equippableItems);
        int choice = ValorInput.getEquipChoice(equippableItems.size());
        
        if (choice < 0) {
            ValorDisplay.actionCancelled();
            return false;
        }
        
        Item item = equippableItems.get(choice);
        
        if (item instanceof Weapon) {
            Weapon oldWeapon = hero.getEquippedWeapon();
            if (oldWeapon != null) {
                hero.getInventory().add(oldWeapon);
            }
            hero.equipWeapon((Weapon) item);
            hero.getInventory().remove(item);
        } else if (item instanceof Armor) {
            Armor oldArmor = hero.getEquippedArmor();
            if (oldArmor != null) {
                hero.getInventory().add(oldArmor);
            }
            hero.equipArmor((Armor) item);
            hero.getInventory().remove(item);
        }
        
        ValorDisplay.showItemEquipped(hero, item);
        return true;
    }
    
    /**
     * Handles hero move action.
     * @return true if action was taken.
     */
    private boolean handleMove(Hero hero) {
        char direction = ValorInput.getMovementDirection();
        
        if (direction == 'X') {
            ValorDisplay.actionCancelled();
            return false;
        }
        
        // Convert WASD to NSEW
        char boardDirection;
        switch (direction) {
            case 'W': boardDirection = 'N'; break;
            case 'S': boardDirection = 'S'; break;
            case 'A': boardDirection = 'W'; break;
            case 'D': boardDirection = 'E'; break;
            default: boardDirection = 'S'; break;
        }
        
        boolean success = board.moveHero(hero, boardDirection);
        ValorDisplay.showMoveResult(hero, success, direction);
        
        return success;
    }
    
    /**
     * Handles hero teleport action.
     * @return true if action was taken.
     */
    private boolean handleTeleport(Hero hero, int heroIndex) {
        HeroWrapper hw = board.getHeroWrapper(hero);
        int heroLane = ValorBoardUtilities.getLaneForColumn(hw.getCol());
        
        // Find heroes in other lanes
        List<Hero> validTargets = new ArrayList<>();
        List<Integer> targetIndices = new ArrayList<>();
        
        for (int i = 0; i < heroes.size(); i++) {
            if (i == heroIndex) continue;
            
            Hero otherHero = heroes.get(i);
            if (!otherHero.isAlive()) continue;
            
            HeroWrapper otherHw = board.getHeroWrapper(otherHero);
            int otherLane = ValorBoardUtilities.getLaneForColumn(otherHw.getCol());
            
            if (otherLane != heroLane && otherLane != ValorBoardConstants.INVALID_LANE) {
                validTargets.add(otherHero);
                targetIndices.add(i + 1);
            }
        }
        
        if (validTargets.isEmpty()) {
            ValorDisplay.cannotTeleport("No valid heroes in other lanes.");
            return false;
        }
        
        ValorDisplay.showHeroesForTeleport(hero, validTargets, targetIndices);
        int choice = ValorInput.getHeroSelection(validTargets.size());
        
        if (choice < 0) {
            ValorDisplay.actionCancelled();
            return false;
        }
        
        Hero targetHero = validTargets.get(choice);
        boolean success = board.teleport(hero, targetHero);
        ValorDisplay.showTeleportResult(hero, targetHero, success);
        
        return success;
    }
    
    /**
     * Handles hero recall action.
     * @return true if action was taken.
     */
    private boolean handleRecall(Hero hero) {
        boolean success = board.recall(hero);
        ValorDisplay.showRecallResult(hero, success);
        return success;
    }
    
    /**
     * Handles market access (doesn't consume action).
     */
    private void handleMarket(Hero hero) {
        HeroWrapper hw = board.getHeroWrapper(hero);
        ValorTile tile = board.getTile(hw.getRow(), hw.getCol());
        
        if (tile.getState() instanceof board.valor.tiletypes.NexusTile) {
            board.valor.tiletypes.NexusTile nexus = (board.valor.tiletypes.NexusTile) tile.getState();
            if (nexus.isHeroesNexus()) {
                // Create single-hero list for market compatibility
                List<Hero> singleHero = new ArrayList<>();
                singleHero.add(hero);
                Market market = new Market();
                market.enter(singleHero);
            } else {
                ValorDisplay.notAtNexus();
            }
        } else {
            ValorDisplay.notAtNexus();
        }
    }
    
    /**
     * Handles obstacle removal action.
     * @return true if action was taken.
     */
    private boolean handleRemoveObstacle(Hero hero) {
        HeroWrapper hw = board.getHeroWrapper(hero);
        int row = hw.getRow();
        int col = hw.getCol();
        
        // Check adjacent cells for obstacles
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            ValorTile tile = board.getTile(newRow, newCol);
            if (tile != null && tile.getState().isRemovable()) {
                board.removeObstacle(newRow, newCol);
                ValorDisplay.showObstacleRemoved(hero);
                return true;
            }
        }
        
        ValorDisplay.noAdjacentObstacle();
        return false;
    }
    
    /**
     * End of round processing: regeneration and debuff countdown.
     */
    private void endOfRound() {
        ValorDisplay.showRegeneration();
        
        // Regenerate HP and MP for alive heroes
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                int hpRegen = Math.max(1, (int)(hero.getHp() * ValorGameConstants.HP_REGEN_PERCENTAGE));
                int mpRegen = Math.max(1, (int)(hero.getMp() * ValorGameConstants.MP_REGEN_PERCENTAGE));
                
                hero.setHp(Math.min(hero.getHp() + hpRegen, hero.getMaxHp()));
                hero.setMp(Math.min(hero.getMp() + mpRegen, hero.getMaxMp()));
            }
        }
        
        // Decrement debuff durations
        Iterator<Map.Entry<Monster, SpellEffect>> iterator = monsterDebuffs.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Monster, SpellEffect> entry = iterator.next();
            SpellEffect effect = entry.getValue();
            
            if (effect.roundsRemaining > 0) {
                effect.roundsRemaining--;
                if (effect.roundsRemaining <= 0) {
                    effect.damageMultiplier = 1.0;
                    effect.defenseMultiplier = 1.0;
                    effect.dodgeMultiplier = 1.0;
                }
            }
        }
    }
    
    /**
     * Spawns new monsters in each lane.
     */
    public void spawnMonsters() {
        int monsterLevel = getHighestHeroLevel();
        
        List<String[]> dragonData = DataLoader.readData("Dragons.txt");
        List<String[]> exoskeletonData = DataLoader.readData("Exoskeletons.txt");
        List<String[]> spiritData = DataLoader.readData("Spirits.txt");
        
        int spawned = 0;
        
        for (int lane = 0; lane < ValorGameConstants.NUM_LANES; lane++) {
            monsterCounter++;
            Monster monster = createRandomMonster(dragonData, exoskeletonData, spiritData, monsterLevel);
            
            board.spawnMonster(monster, monsterCounter, lane);
            monsterDebuffs.put(monster, new SpellEffect());
            spawned++;
        }
        
        ValorDisplay.monstersSpawned(spawned);
    }
    
    /**
     * Creates a random monster.
     */
    private Monster createRandomMonster(List<String[]> dragons, List<String[]> exos, List<String[]> spirits, int level) {
        double random = Math.random();
        
        if (random < Percentages.DRAGON) {
            String[] data = dragons.get((int)(Math.random() * dragons.size()));
            return MonsterFactory.createMonster("Dragon", data, level);
        } else if (random < Percentages.DRAGON + Percentages.EXOSKELETON) {
            String[] data = exos.get((int)(Math.random() * exos.size()));
            return MonsterFactory.createMonster("Exoskeleton", data, level);
        } else {
            String[] data = spirits.get((int)(Math.random() * spirits.size()));
            return MonsterFactory.createMonster("Spirit", data, level);
        }
    }
    
    /**
     * Gets the highest level among heroes.
     */
    private int getHighestHeroLevel() {
        int maxLevel = 1;
        for (Hero hero : heroes) {
            if (hero.getLevel() > maxLevel) {
                maxLevel = hero.getLevel();
            }
        }
        return maxLevel;
    }
    
    /**
     * Distributes rewards for killing a monster to all heroes.
     */
    private void distributeRewards(Monster monster) {
        int gold = monster.getLevel() * ValorGameConstants.GOLD_PER_MONSTER_LEVEL;
        int exp = monster.getLevel() * ValorGameConstants.EXP_PER_MONSTER_LEVEL;
        
        ValorDisplay.showRewards(gold, exp);
        
        for (Hero hero : heroes) {
            hero.setGold(hero.getGold() + gold);
            hero.addExperience(exp);
        }
    }
    
    /**
     * Removes a dead monster from the board.
     */
    private void removeDeadMonster(Monster monster) {
        for (BoardEntity entity : board.getMonsters()) {
            if (entity instanceof MonsterWrapper) {
                MonsterWrapper mw = (MonsterWrapper) entity;
                if (mw.getMonster() == monster) {
                    board.removeEntity(entity);
                    break;
                }
            }
        }
    }
    
    /**
     * Respawns a dead hero at their nexus.
     */
    private void respawnHero(Hero hero, int heroIndex) {
        // Reset HP and MP to full
        hero.setHp(hero.getMaxHp());
        hero.setMp(hero.getMaxMp());
        
        // Respawn at nexus (handled by recall mechanism, but we need to place them back)
        int lane = heroLanes.get(heroIndex);
        int[] columns = ValorBoardUtilities.getColumnsForLane(lane);
        int spawnRow = ValorBoardConstants.BOARD_SIZE - ValorBoardConstants.HEROES_NEXUS_ROW_OFFSET;
        int spawnCol = columns[ValorBoardConstants.SPAWN_COLUMN_INDEX_0];
        
        HeroWrapper hw = board.getHeroWrapper(hero);
        
        // Remove from current position if still there
        ValorTile oldTile = board.getTile(hw.getRow(), hw.getCol());
        if (oldTile != null) {
            oldTile.removeHero();
        }
        
        // Place at nexus
        ValorTile nexusTile = board.getTile(spawnRow, spawnCol);
        if (nexusTile != null && !nexusTile.hasHero()) {
            nexusTile.placeHero(hero);
            hw.setPosition(spawnRow, spawnCol);
        }
        
        ValorDisplay.heroRespawned(hero);
    }
    
    /**
     * Checks if heroes have won (any hero reached monsters' nexus).
     * @return true if heroes won.
     */
    public boolean checkVictory() {
        for (Hero hero : heroes) {
            if (!hero.isAlive()) continue;
            
            HeroWrapper hw = board.getHeroWrapper(hero);
            if (hw.getRow() == ValorGameConstants.MONSTERS_NEXUS_ROW) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if monsters have won (any monster reached heroes' nexus).
     * @return true if monsters won.
     */
    public boolean checkDefeat() {
        for (BoardEntity entity : board.getMonsters()) {
            if (!entity.isAlive()) continue;
            
            if (entity.getRow() >= ValorGameConstants.HEROES_NEXUS_ROW) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns whether the game is over.
     */
    public boolean isGameOver() {
        return gameOver;
    }
    
    /**
     * Returns whether heroes won.
     */
    public boolean didHeroesWin() {
        return heroesWon;
    }
    
    /**
     * Gets the current round number.
     */
    public int getCurrentRound() {
        return currentRound;
    }
}
