package Utilities;

public class ValorGameConstants {
    private ValorGameConstants() {
    }

    // Game settings
    public static final int NUM_HEROES = 3;
    public static final int NUM_LANES = 3;

    // Spawn settings
    public static final int MONSTER_SPAWN_INTERVAL_EASY = 8;
    public static final int MONSTER_SPAWN_INTERVAL_MEDIUM = 6;
    public static final int MONSTER_SPAWN_INTERVAL_HARD = 4;
    public static final int DEFAULT_SPAWN_INTERVAL = 8;

    // Rewards
    public static final int GOLD_PER_MONSTER_LEVEL = 500;
    public static final int EXP_PER_MONSTER_LEVEL = 2;

    // Regeneration
    public static final double HP_REGEN_PERCENTAGE = 0.10;
    public static final double MP_REGEN_PERCENTAGE = 0.10;

    // Combat
    public static final double DAMAGE_MULTIPLIER = 1;
    public static final double HERO_DODGE_MULTIPLIER = 0.002;
    public static final double MONSTER_DODGE_MULTIPLIER = 0.01;
    public static final double SPELL_DEXTERITY_DIVISOR = 10000.0;
    public static final double SPELL_DEBUFF_REDUCTION = 0.10;

    // Win/Loss conditions
    public static final int MONSTERS_NEXUS_ROW = 0;
    public static final int HEROES_NEXUS_ROW = 7;

    // Difficulty levels
    public static final int DIFFICULTY_EASY = 1;
    public static final int DIFFICULTY_MEDIUM = 2;
    public static final int DIFFICULTY_HARD = 3;

    // Hero actions
    public static final char ACTION_ATTACK = 'A';
    public static final char ACTION_CAST_SPELL = 'C';
    public static final char ACTION_USE_POTION = 'P';
    public static final char ACTION_EQUIP = 'E';
    public static final char ACTION_MOVE = 'M';
    public static final char ACTION_TELEPORT = 'T';
    public static final char ACTION_RECALL = 'R';
    public static final char ACTION_MARKET = 'B';
    public static final char ACTION_INFO = 'I';
    public static final char ACTION_PASS = 'X';
    public static final char ACTION_QUIT = 'Q';
    public static final char ACTION_REMOVE_OBSTACLE = 'O';

    // Movement directions
    public static final char MOVE_NORTH = 'W';
    public static final char MOVE_SOUTH = 'S';
    public static final char MOVE_EAST = 'D';
    public static final char MOVE_WEST = 'A';
}
