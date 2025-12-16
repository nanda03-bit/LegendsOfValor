/**
 * Filename: BuffManager.java
 * Author: Olivia Ma
 * Date: 2025-Dec
 * Description: Composition-based decorator for temporary buffs and debuffs.
 */

package Buffs;


import Player.Heroes.Hero;
import Player.Monsters.Monster;


import java.util.*;


/**
* Composition-based decorator for temporary buffs and debuffs.
* It stores active buff entries applied to heroes/monsters and provides helpers
* to apply/remove them. This avoids invasive inheritance changes and follows the Decorator idea
* by wrapping behavior (attributes are changed while buff is active).
*/

public class BuffManager {
    private static class BuffEntry {
        String id; // e.g., "terrain:bush" or "spell:fireDebuff"
        int roundsRemaining;
        double multiplier; // multiplicative factor e.g., 1.1 for +10%
        String attribute; // which attribute is affected: "strength","dexterity","agility","attack","dodge"
    }

    private Map<Hero, List<BuffEntry>> heroBuffs = new HashMap<>();
    private Map<Monster, List<BuffEntry>> monsterBuffs = new HashMap<>();

    public void applyHeroBuff(Hero hero, String id, String attribute, double multiplier, int rounds) {
        heroBuffs.computeIfAbsent(hero, h -> new ArrayList<>());
        BuffEntry e = new BuffEntry();
        e.id = id; e.attribute = attribute; e.multiplier = multiplier; e.roundsRemaining = rounds;
        heroBuffs.get(hero).add(e);

        // apply immediately
        applyBuffEffect(hero, e, true);
    }

    public void removeHeroBuff(Hero hero, String id) {
        List<BuffEntry> list = heroBuffs.get(hero);
        if (list == null) return;
        Iterator<BuffEntry> it = list.iterator();
        while (it.hasNext()) {
            BuffEntry e = it.next();
            if (e.id.equals(id)) {
                applyBuffEffect(hero, e, false); // remove
                it.remove();
            }
        }
    }


    // call at end of round to decrement durations and remove expired
    public void endRound() {
        for (Map.Entry<Hero, List<BuffEntry>> entry : new ArrayList<>(heroBuffs.entrySet())) {
            Hero hero = entry.getKey();
            List<BuffEntry> list = entry.getValue();
            Iterator<BuffEntry> it = list.iterator();
            while (it.hasNext()) {
                BuffEntry e = it.next();
                e.roundsRemaining--;
                if (e.roundsRemaining <= 0) {
                    applyBuffEffect(hero, e, false);
                    it.remove();
                }
            }
        if (list.isEmpty()) heroBuffs.remove(hero);
        }
        // monsters can be handled similarly if desired
    }

    private void applyBuffEffect(Hero hero, BuffEntry e, boolean apply) {
        // apply==true => multiply attribute
        // apply==false => divide by multiplier
        double factor = apply ? e.multiplier : (1.0 / e.multiplier);
        switch (e.attribute.toLowerCase()) {
            case "strength":
                hero.setStrength((int)(hero.getStrength() * factor));
                break;
            case "dexterity":
                hero.setDexterity((int)(hero.getDexterity() * factor));
                break;
            case "agility":
                hero.setAgility((int)(hero.getAgility() * factor));
                break;
        }
    }
}