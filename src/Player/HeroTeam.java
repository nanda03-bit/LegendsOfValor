package Player;

import Player.Heroes.Hero;

import java.util.*;

/**
 * Represents a team of heroes that work together.
 * Implements Iterable to allow easy iteration over team members.
 */
public class HeroTeam implements Iterable<Hero> {

    private List<Hero> members;
    private int maxSize;
    private String teamName;

    /**
     * Creates a new hero team with default max size of 3.
     */
    public HeroTeam() {
        this.members = new ArrayList<>();
        this.maxSize = 3;
        this.teamName = "Hero Party";
    }

    /**
     * Creates a new hero team with specified max size.
     *
     * @param maxSize Maximum number of heroes allowed in team.
     */
    public HeroTeam(int maxSize) {
        this.members = new ArrayList<>();
        this.maxSize = maxSize;
        this.teamName = "Hero Party";
    }

    /**
     * Creates a new hero team with name and max size.
     *
     * @param teamName The name of the team.
     * @param maxSize  Maximum number of heroes allowed.
     */
    public HeroTeam(String teamName, int maxSize) {
        this.members = new ArrayList<>();
        this.maxSize = maxSize;
        this.teamName = teamName;
    }

    /**
     * Adds a hero to the team.
     *
     * @param hero The hero to add.
     * @return true if added successfully, false if team is full.
     */
    public boolean addHero(Hero hero) {
        if (members.size() >= maxSize) {
            return false;
        }
        if (members.contains(hero)) {
            return false;
        }
        members.add(hero);
        return true;
    }

    /**
     * Removes a hero from the team.
     *
     * @param hero The hero to remove.
     * @return true if removed, false if not found.
     */
    public boolean removeHero(Hero hero) {
        return members.remove(hero);
    }

    /**
     * Gets a hero by index.
     *
     * @param index The index of the hero.
     * @return The hero at the specified index.
     */
    public Hero getHero(int index) {
        if (index < 0 || index >= members.size()) {
            return null;
        }
        return members.get(index);
    }

    /**
     * Gets the team leader (first hero).
     *
     * @return The team leader, or null if team is empty.
     */
    public Hero getLeader() {
        if (members.isEmpty()) {
            return null;
        }
        return members.get(0);
    }

    /**
     * Gets the current size of the team.
     *
     * @return Number of heroes in the team.
     */
    public int size() {
        return members.size();
    }

    /**
     * Checks if the team is empty.
     *
     * @return true if no heroes in team.
     */
    public boolean isEmpty() {
        return members.isEmpty();
    }

    /**
     * Checks if the team is full.
     *
     * @return true if team has reached max size.
     */
    public boolean isFull() {
        return members.size() >= maxSize;
    }

    /**
     * Gets the maximum team size.
     *
     * @return Maximum allowed heroes.
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Gets the team name.
     *
     * @return The team's name.
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Sets the team name.
     *
     * @param teamName The new team name.
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Gets a list of all heroes (as unmodifiable list).
     *
     * @return List of heroes.
     */
    public List<Hero> getMembers() {
        return Collections.unmodifiableList(members);
    }

    /**
     * Gets a mutable list of heroes for compatibility.
     *
     * @return Mutable list of heroes.
     */
    public List<Hero> getMembersList() {
        return members;
    }

    /**
     * Checks if any hero in the team is alive.
     *
     * @return true if at least one hero is alive.
     */
    public boolean hasAliveMembers() {
        for (Hero hero : members) {
            if (hero.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets all alive heroes in the team.
     *
     * @return List of alive heroes.
     */
    public List<Hero> getAliveMembers() {
        List<Hero> alive = new ArrayList<>();
        for (Hero hero : members) {
            if (hero.isAlive()) {
                alive.add(hero);
            }
        }
        return alive;
    }

    /**
     * Gets the highest level among all heroes.
     *
     * @return The highest hero level.
     */
    public int getHighestLevel() {
        int maxLevel = 1;
        for (Hero hero : members) {
            if (hero.getLevel() > maxLevel) {
                maxLevel = hero.getLevel();
            }
        }
        return maxLevel;
    }

    /**
     * Gets total gold across all heroes.
     *
     * @return Total gold.
     */
    public int getTotalGold() {
        int total = 0;
        for (Hero hero : members) {
            total += hero.getGold();
        }
        return total;
    }

    /**
     * Checks if team contains a hero by name.
     *
     * @param heroName The name to check.
     * @return true if a hero with that name exists.
     */
    public boolean containsHeroByName(String heroName) {
        for (Hero hero : members) {
            if (hero.getName().equals(heroName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Hero> iterator() {
        return members.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(teamName).append(" (").append(members.size()).append("/").append(maxSize).append(" heroes):\n");
        for (int i = 0; i < members.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(members.get(i).getName()).append("\n");
        }
        return sb.toString();
    }
}
