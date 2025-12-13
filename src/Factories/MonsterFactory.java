package Factories;
import Player.Monsters.*;

public class MonsterFactory {

    public static Monster createMonster(
        String type,
        String[] data,
        int level
    ) {
        switch (type) {
            // // If the random value falls within the dragon probability range, then picks a random dragon, then cast to an int (random dragon added to list)
            // if (random < dragonProb) {
            //     String[] dragon = dragonData.get( (int)(Math.random() * dragonData.size()));
            //     monsters.add(new Dragon(dragon[0], monsterLevel, Integer.parseInt(dragon[2]), Integer.parseInt(dragon[3]), Integer.parseInt(dragon[4])));
            // }

            // // If the random number wasn't small enough to create a Dragon, but is still within range of Dragon + Exoskeleton probability, then we create an Exoskeleton monster instead.
            // else if (random < dragonProb + exoskeletonProb) {
            //     String[] exoskeleton = exoskeletonData.get((int) (Math.random() * exoskeletonData.size()));
            //     monsters.add(new Exoskeleton(exoskeleton[0], monsterLevel, Integer.parseInt(exoskeleton[2]), Integer.parseInt(exoskeleton[3]), Integer.parseInt(exoskeleton[4])));
            // }

            // // Last probablility is spirit monster
            // else {
            //     String[] spirit = spiritData.get((int) (Math.random() * spiritData.size()));
            //     monsters.add(new Spirit(spirit[0], monsterLevel, Integer.parseInt(spirit[2]), Integer.parseInt(spirit[3]), Integer.parseInt(spirit[4])));
            // }
            case "Dragon":
                return new Dragon(data[0], level,
                    Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]));
            case "Exoskeleton":
                return new Exoskeleton(data[0], level,
                    Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]));
            case "Spirit":
                return new Spirit(data[0], level,
                    Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]));
            default:
                throw new IllegalArgumentException("Unknown monster type");
        }
    }
}

