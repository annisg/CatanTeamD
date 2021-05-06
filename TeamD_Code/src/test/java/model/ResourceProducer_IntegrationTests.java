package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

import exception.*;

public class ResourceProducer_IntegrationTests {

    ResourceProducer producer;
    GameMap gmTest;
    TurnTracker tracker;
    Random random;
    
    public void setupObjects() {
        random = new Random(0);
        gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        tracker = new TurnTracker(random);
        tracker.setupPlayers(3);
        tracker.setupBeginnerResourcesAndPieces();
        producer = new ResourceProducer(random);
    }
    
    @Test
    public void testRollNoResourcesProduced() {
        setupObjects();
        producer.produceResources(gmTest, tracker, 2);
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.WHITE), Arrays.asList(1,1,0,1,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.ORANGE), Arrays.asList(2,0,1,0,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.BLUE), Arrays.asList(0,1,1,1,0));

    }
    
    private void verifyPlayerHasResources(Player player, List<Integer> resourceNums) {
        List<Resource> resources = Arrays.asList(Resource.GRAIN, Resource.BRICK, Resource.ORE, Resource.LUMBER, Resource.WOOL);
        for(int i = 0; i < resources.size(); i++) {
            assertEquals(player.getResourceCount(resources.get(i)), resourceNums.get(i));
        }
    }

}
