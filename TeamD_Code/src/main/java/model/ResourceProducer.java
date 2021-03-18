package model;

import java.util.*;

public class ResourceProducer {

    private Random random;

    public ResourceProducer(Random random) {
        this.random = random;
    }

    public void produceResources(GameMap map, TurnTracker players) {

        int roll = rollDice();

        produceResources(map, players, roll);
    }

    public void produceResources(GameMap map, TurnTracker players, int roll) {
        if (roll != 7) {
            ArrayList<Hex> producingHexes = map.getHexesByResourceNumber(roll);

            for (Hex hex : producingHexes) {
                produceHex(hex, map, players);
            }
        }
    }

    public int rollDice() {
        return random.nextInt(5) + random.nextInt(5) + 2;
    }

    void produceHex(Hex hex, GameMap map, TurnTracker players) {

        if (hex.getResource() == Resource.DESERT || hex.hasRobber()) {
            return;
        }

        ArrayList<Intersection> surroundingIntersections = map.getAllIntersectionsFromHex(hex);

        for (Intersection intersection : surroundingIntersections) {
            produceIntersection(intersection, hex.getResource(), players);
        }
    }

    void produceIntersection(Intersection intersection, Resource resource, TurnTracker players) {

        if (intersection.hasSettlement() || intersection.hasCity()) {

            int numToAdd = 1;
            if (intersection.hasCity()) {
                numToAdd = 2;
            }

            for (int i = 0; i < players.getNumPlayers(); i++) {

                if (players.getPlayer(i).getColor() == intersection.getBuildingColor()) {
                    players.getPlayer(i).giveResource(resource, numToAdd);
                }
            }
        }
    }
}
