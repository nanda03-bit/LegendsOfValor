package Utilities;

public class MonstersAndHeroesGameConstants {
    private MonstersAndHeroesGameConstants() {}

    public static final int HERO_STARTING_LEVEL = 1;
    public static final int HERO_STARTING_HP = 100;
    public static final int HP_PER_LEVEL = 100;
    public static final double MP_INCREASE_ON_LEVEL_UP = 1.1;
    public static final double BASE_STAT_INCREASE_ON_LEVEL_UP = 1.05;
    public static final int EXPERIENCE_THRESHOLD_MULTIPLIER = 10;
    public static final int EXPERIENCE_PER_MONSTER = 2;
    public static final int GOLD_PER_LEVEL = 100;
    public static final double DEATH_HP_PENALTY = 0.5;
    public static final double DEATH_MP_PENALTY = 0.5;
    
    public static final double FAVORED_STAT_MULTIPLIER = 1.1;
    public static final double NON_FAVORED_STAT_MULTIPLIER = 1.05;
    
    public static final int MONSTER_HP_PER_LEVEL = 100;
    public static final double MONSTER_TYPE_BONUS = 1.1;
    
    public static final double SPELL_DEBUFF_MULTIPLIER = 0.9;
    public static final int SPELL_DEBUFF_ROUNDS = 3;
    public static final double BASE_DAMAGE_MULTIPLIER = 1.0;
    public static final int DODGE_CALCULATION_BASE = 100;
    public static final double REGENERATION_PERCENTAGE = 0.10;
    public static final int MIN_REGENERATION = 1;
    public static final int SPELL_DEXTERITY_DIVISOR = 100;
    
    public static final int MIN_PARTY_SIZE = 1;
    public static final int MAX_PARTY_SIZE = 3;
    
    public static final int MIN_HERO_TYPE = 1;
    public static final int MAX_HERO_TYPE = 3;
    public static final int WARRIOR_TYPE = 1;
    public static final int SORCERER_TYPE = 2;
    public static final int PALADIN_TYPE = 3;
    
    public static final int MIN_WORLD_SIZE = 4;
    public static final int MAX_WORLD_SIZE = 10;
    
    public static final int SELL_PRICE_DIVISOR = 2;
    
    public static final String SPELL_TYPE_FIRE = "Fire";
    public static final String SPELL_TYPE_ICE = "Ice";
    public static final String SPELL_TYPE_LIGHTNING = "Lightning";
}

