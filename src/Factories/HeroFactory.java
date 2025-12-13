package Factories;

import Player.Heroes.*;
import Utilities.MonstersAndHeroesGameConstants;

public class HeroFactory {
// simple factory: creates by type string "Warrior", "Sorcerer", "Paladin"
public static Hero createHero(String type, String name, int mana, int strength, int agility, int dexterity, int startingMoney, int startingExperience) {
    switch(type.toLowerCase()) {
        case "warrior":
            return new Warrior(name, mana, strength, agility, dexterity, startingMoney, startingExperience);
        case "sorcerer":
            return new Sorcerer(name, mana, strength, agility, dexterity, startingMoney, startingExperience);
        case "paladin":
            return new Paladin(name, mana, strength, agility, dexterity, startingMoney, startingExperience);
        default:
            // fallback: a generic hero using warrior as default
            return new Warrior(name, mana, strength, agility, dexterity, startingMoney, startingExperience);
        
        }
    }
}