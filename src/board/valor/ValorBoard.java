package board.valor;

import board.valor.tiletypes.*;
import board.common.BoardUtilities;
import board.common.BoardEntity;
import board.common.MovementStrategy;
import Utilities.ValorBoardConstants;
import Player.Heroes.Hero;
import Player.Monsters.Monster;

import java.util.*;

public class ValorBoard {

    private ValorTile[][] grid;
    private EntityCollection heroes;
    private EntityCollection monsters;
    private Map<Hero, HeroWrapper> heroMap;
    private Map<Hero, int[]> heroNexusPositions;
    private MovementStrategy heroMovementStrategy;
    private MovementStrategy monsterMovementStrategy;

    public ValorBoard() {
        this.grid = new ValorTile[ValorBoardConstants.BOARD_SIZE][ValorBoardConstants.BOARD_SIZE];
        this.heroes = new EntityCollection();
        this.monsters = new EntityCollection();
        this.heroMap = new HashMap<>();
        this.heroNexusPositions = new HashMap<>();
        this.heroMovementStrategy = new HeroMovementStrategy();
        this.monsterMovementStrategy = new MonsterMovementStrategy();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < ValorBoardConstants.BOARD_SIZE; row++) {
            for (int col = 0; col < ValorBoardConstants.BOARD_SIZE; col++) {
                grid[row][col] = new ValorTile(new PlainTile());
            }
        }

        for (int col = 0; col < ValorBoardConstants.BOARD_SIZE; col++) {
            if (!ValorBoardUtilities.isWallColumn(col)) {
                grid[ValorBoardConstants.MONSTERS_NEXUS_ROW][col] = new ValorTile(new NexusTile(false));
                grid[ValorBoardConstants.BOARD_SIZE - ValorBoardConstants.HEROES_NEXUS_ROW_OFFSET][col] = new ValorTile(new NexusTile(true));
            }
        }

        for (int row = 0; row < ValorBoardConstants.BOARD_SIZE; row++) {
            grid[row][ValorBoardConstants.WALL_COLUMN_1] = new ValorTile(new InaccessibleTile());
            grid[row][ValorBoardConstants.WALL_COLUMN_2] = new ValorTile(new InaccessibleTile());
        }

        Random random = new Random();
        List<ValorTileType> terrainPool = new ArrayList<>();

        int columnsPerLane = ValorBoardConstants.LANE_0_COLUMNS.length;
        int totalLaneCells = (ValorBoardConstants.BOARD_SIZE - ValorBoardConstants.NEXUS_ROW_COUNT) * columnsPerLane * ValorBoardConstants.LANE_COUNT;
        int specialCount = (int) (totalLaneCells * ValorBoardConstants.SPECIAL_TERRAIN_PERCENTAGE);
        int plainCount = totalLaneCells - (specialCount * ValorBoardConstants.TERRAIN_TYPE_COUNT);

        for (int i = 0; i < plainCount; i++) {
            terrainPool.add(new PlainTile());
        }
        for (int i = 0; i < specialCount; i++) {
            terrainPool.add(new BushTile());
            terrainPool.add(new CaveTile());
            terrainPool.add(new KoulouTile());
        }

        Collections.shuffle(terrainPool);
        int poolIndex = 0;

        for (int row = 1; row < ValorBoardConstants.BOARD_SIZE - ValorBoardConstants.HEROES_NEXUS_ROW_OFFSET; row++) {
            for (int col = 0; col < ValorBoardConstants.BOARD_SIZE; col++) {
                if (!ValorBoardUtilities.isWallColumn(col)) {
                    if (poolIndex < terrainPool.size()) {
                        grid[row][col] = new ValorTile(terrainPool.get(poolIndex++));
                    }
                }
            }
        }

        int obstacleCount = random.nextInt(ValorBoardConstants.OBSTACLE_MAX_COUNT - ValorBoardConstants.OBSTACLE_MIN_COUNT + 1) + ValorBoardConstants.OBSTACLE_MIN_COUNT;
        int placed = 0;
        int attempts = 0;
        int maxAttempts = obstacleCount * 10;

        while (placed < obstacleCount && attempts < maxAttempts) {
            attempts++;
            int row = random.nextInt(ValorBoardConstants.BOARD_SIZE - ValorBoardConstants.NEXUS_ROW_COUNT) + 1; // Rows 1-6 only
            int col;
            do {
                col = random.nextInt(ValorBoardConstants.BOARD_SIZE);
            }
            while (ValorBoardUtilities.isWallColumn(col));

            // Check if this cell already has an obstacle
            if (grid[row][col].getState() instanceof ObstacleTile) {
                continue;
            }

            // Check if placing an obstacle here would block the lane
            // Find which lane this column belongs to and check if the other column in the same row is passable
            int[] laneColumns = getLaneColumnsForCol(col);
            if (laneColumns != null) {
                int otherCol = (col == laneColumns[0]) ? laneColumns[1] : laneColumns[0];
                // Only place if the other column is passable (not obstacle, not wall)
                if (grid[row][otherCol].getState() instanceof ObstacleTile ||
                        grid[row][otherCol].getState() instanceof InaccessibleTile) {
                    continue; // Would block the lane, skip
                }
            }

            grid[row][col] = new ValorTile(new ObstacleTile());
            placed++;
        }
    }

    /**
     * Gets the lane columns for a given column.
     */
    private int[] getLaneColumnsForCol(int col) {
        if (col == 0 || col == 1) return ValorBoardConstants.LANE_0_COLUMNS;
        if (col == 3 || col == 4) return ValorBoardConstants.LANE_1_COLUMNS;
        if (col == 6 || col == 7) return ValorBoardConstants.LANE_2_COLUMNS;
        return null;
    }

    public void spawnHero(Hero hero, int heroIndex, int lane) {
        int[] columns = ValorBoardUtilities.getColumnsForLane(lane);
        int spawnRow = ValorBoardConstants.BOARD_SIZE - ValorBoardConstants.HEROES_NEXUS_ROW_OFFSET;
        int spawnCol = columns[ValorBoardConstants.SPAWN_COLUMN_INDEX_0];

        HeroWrapper wrapper = new HeroWrapper(hero, heroIndex, spawnRow, spawnCol);
        heroMap.put(hero, wrapper);
        heroes.add(wrapper);
        heroNexusPositions.put(hero, new int[]{spawnRow, spawnCol});

        grid[spawnRow][spawnCol].placeHero(hero);
    }

    public void spawnMonster(Monster monster, int monsterIndex, int lane) {
        int[] columns = ValorBoardUtilities.getColumnsForLane(lane);
        int spawnRow = ValorBoardConstants.MONSTERS_NEXUS_ROW;
        int spawnCol = columns[ValorBoardConstants.SPAWN_COLUMN_INDEX_1];

        MonsterWrapper wrapper = new MonsterWrapper(monster, monsterIndex, spawnRow, spawnCol);
        monsters.add(wrapper);

        grid[spawnRow][spawnCol].placeMonster(monster);
    }

    public ValorTile getTile(int row, int col) {
        if (!BoardUtilities.isValidCoordinate(row, col, ValorBoardConstants.BOARD_SIZE)) {
            return null;
        }
        return grid[row][col];
    }

    public boolean moveHero(Hero hero, char direction) {
        HeroWrapper wrapper = heroMap.get(hero);
        if (wrapper == null || !wrapper.isAlive()) {
            return false;
        }

        return heroMovementStrategy.move(this, wrapper, direction);
    }

    public boolean moveMonster(MonsterWrapper monster) {
        if (monster == null || !monster.isAlive()) {
            return false;
        }

        return monsterMovementStrategy.move(this, monster, 'S');
    }

    // made some changes to apply/remove terrain effects
    // CHECK FOR CORRECTNESS
    public boolean moveEntityTo(BoardEntity entity, int newRow, int newCol) {
        if (!BoardUtilities.isValidCoordinate(newRow, newCol, ValorBoardConstants.BOARD_SIZE)) {
            return false;
        }

        // moved this to top of the function, because we must know the old tile before apply any terrain logic
        // old position must be resolved before movement validation sie effects are made
        int oldRow = entity.getRow();
        int oldCol = entity.getCol();

        // prevent reapplying effects if entity does not move
        if (oldRow == newRow && oldCol == newCol) {
            return false;
        }

        ValorTile targetTile = grid[newRow][newCol];
        if (!targetTile.canEnter()) {
            return false;
        }


        ValorTile oldTile = grid[oldRow][oldCol];

        boolean success = false;
        if (entity instanceof HeroWrapper) {
            Hero hero = ((HeroWrapper) entity).getHero();
            oldTile.removeHero();
            success = targetTile.placeHero(hero);
            if (!success) {
                // rollback
                oldTile.placeHero(hero);
                oldTile.applyTerrainEffect(hero); // restore buff if move failed
            } else {
                entity.setPosition(newRow, newCol);
                // apply terrain effect after entering new tile
                targetTile.applyTerrainEffect(hero);
            }
        }
        // no terrain effects for monsters!
        else if (entity instanceof MonsterWrapper) {
            Monster monster = ((MonsterWrapper) entity).getMonster();
            oldTile.removeMonster();
            success = targetTile.placeMonster(monster);
            if (!success) {
                oldTile.placeMonster(monster);
            } else {
                entity.setPosition(newRow, newCol);
            }
        }

        return success;
    }

    public List<BoardEntity> getEntitiesInRange(int row, int col, boolean isHero) {
        List<BoardEntity> inRange = new ArrayList<>();
        List<int[]> rangeCoords = ValorBoardUtilities.getCoordinatesInRange(row, col, ValorBoardConstants.BOARD_SIZE);

        for (int[] coord : rangeCoords) {
            ValorTile tile = grid[coord[0]][coord[1]];
            if (isHero && tile.hasMonster()) {
                for (BoardEntity entity : monsters) {
                    if (entity.getRow() == coord[0] && entity.getCol() == coord[1]) {
                        inRange.add(entity);
                        break;
                    }
                }
            } else if (!isHero && tile.hasHero()) {
                for (BoardEntity entity : heroes) {
                    if (entity.getRow() == coord[0] && entity.getCol() == coord[1]) {
                        inRange.add(entity);
                        break;
                    }
                }
            }
        }

        return inRange;
    }

    public List<BoardEntity> getEntitiesInRange(BoardEntity entity) {
        boolean isHero = entity instanceof HeroWrapper;
        return getEntitiesInRange(entity.getRow(), entity.getCol(), isHero);
    }

    public boolean teleport(Hero hero, Hero targetHero) {
        HeroWrapper wrapper = heroMap.get(hero);
        HeroWrapper targetWrapper = heroMap.get(targetHero);

        if (wrapper == null || targetWrapper == null) {
            return false;
        }

        int heroLane = ValorBoardUtilities.getLaneForColumn(wrapper.getCol());
        int targetLane = ValorBoardUtilities.getLaneForColumn(targetWrapper.getCol());

        if (heroLane == targetLane || heroLane == ValorBoardConstants.INVALID_LANE || targetLane == ValorBoardConstants.INVALID_LANE) {
            return false;
        }

        int targetRow = targetWrapper.getRow();
        int targetCol = targetWrapper.getCol();

        List<int[]> validPositions = new ArrayList<>();

        for (int[] dir : ValorBoardConstants.MOVEMENT_DIRECTIONS) {
            int newRow = targetRow + dir[0];
            int newCol = targetCol + dir[1];

            if (BoardUtilities.isValidCoordinate(newRow, newCol, ValorBoardConstants.BOARD_SIZE)) {
                ValorTile tile = grid[newRow][newCol];

                if (tile.hasHero()) {
                    continue;
                }

                if (tile.hasMonster()) {
                    continue;
                }

                if (newRow < targetRow) {
                    continue;
                }

                if (tile.canEnter()) {
                    validPositions.add(new int[]{newRow, newCol});
                }
            }
        }

        if (!validPositions.isEmpty()) {
            int[] pos = validPositions.get(ValorBoardConstants.FIRST_VALID_POSITION_INDEX);
            return moveEntityTo(wrapper, pos[0], pos[1]);
        }

        return false;
    }

    public boolean recall(Hero hero) {
        HeroWrapper wrapper = heroMap.get(hero);
        if (wrapper == null) {
            return false;
        }

        int[] nexusPos = heroNexusPositions.get(hero);
        if (nexusPos == null) {
            return false;
        }

        return moveEntityTo(wrapper, nexusPos[0], nexusPos[1]);
    }

    public boolean removeObstacle(int row, int col) {
        ValorTile tile = getTile(row, col);
        if (tile == null || !tile.getState().isRemovable()) {
            return false;
        }

        tile.removeObstacle();
        return true;
    }

    public void printBoard() {
        Display.Valor.ValorBoardDisplay.printBoard(this);
    }

    public int getSize() {
        return ValorBoardConstants.BOARD_SIZE;
    }

    public EntityCollection getHeroes() {
        return heroes;
    }

    public EntityCollection getMonsters() {
        return monsters;
    }

    public HeroWrapper getHeroWrapper(Hero hero) {
        return heroMap.get(hero);
    }

    public void removeEntity(BoardEntity entity) {
        if (entity instanceof HeroWrapper) {
            heroes.remove(entity);
            grid[entity.getRow()][entity.getCol()].removeHero();
        } else if (entity instanceof MonsterWrapper) {
            monsters.remove(entity);
            grid[entity.getRow()][entity.getCol()].removeMonster();
        }
    }
}

