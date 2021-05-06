package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

public class ResourceProducer_IntegrationTests {

    ResourceProducer producer;
    GameMap gmTest;
    TurnTracker tracker;
    Random random;
    
    public void setupObjects(int numPlayers) {
        random = new Random(0);
        gmTest = new GameMap();
        gmTest.setUpBeginnerMap(numPlayers);
        tracker = new TurnTracker(random);
        tracker.setupPlayers(numPlayers);
        tracker.setupBeginnerResourcesAndPieces();
        producer = new ResourceProducer(random);
    }
    
    @Test
    public void testRollNoResourcesProducedWith3() {
        setupObjects(3);
        producer.produceResources(gmTest, tracker, 2);
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.WHITE), Arrays.asList(1,1,0,1,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.ORANGE), Arrays.asList(2,0,1,0,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.BLUE), Arrays.asList(0,1,1,1,0));
    }
    
    @Test
    public void testRoll12BeginnerMapWith3() {
        setupObjects(3);
        producer.produceResources(gmTest, tracker, 12);
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.WHITE), Arrays.asList(2,1,0,1,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.ORANGE), Arrays.asList(2,0,1,0,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.BLUE), Arrays.asList(0,1,1,1,0));
    }
    
    @Test
    public void testRoll12BeginnerMapWith4() {
        setupObjects(4);
        producer.produceResources(gmTest, tracker, 12);
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.WHITE), Arrays.asList(2,1,0,1,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.ORANGE), Arrays.asList(2,0,1,0,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.BLUE), Arrays.asList(0,1,1,1,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.RED), Arrays.asList(1,0,0,2,0));
    }
    
    @Test
    public void testRoll8BeginnerMapWith3() {
        setupObjects(3);
        producer.produceResources(gmTest, tracker, 8);
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.WHITE), Arrays.asList(1,1,1,1,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.ORANGE), Arrays.asList(2,0,1,0,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.BLUE), Arrays.asList(0,1,1,2,0));
    }
    
    @Test
    public void testRoll8BeginnerMapWith4() {
        setupObjects(4);
        producer.produceResources(gmTest, tracker, 8);
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.WHITE), Arrays.asList(1,1,1,1,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.ORANGE), Arrays.asList(2,0,1,0,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.BLUE), Arrays.asList(0,1,1,2,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.RED), Arrays.asList(1,0,0,3,0));
    }
    
    @Test
    public void testRoll3BeginnerMapWith3() {
        setupObjects(3);
        producer.produceResources(gmTest, tracker, 3);
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.WHITE), Arrays.asList(1,1,0,2,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.ORANGE), Arrays.asList(2,0,2,0,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.BLUE), Arrays.asList(0,1,2,1,0));
    }
    
    @Test
    public void testRoll3BeginnerMapWith4() {
        setupObjects(4);
        producer.produceResources(gmTest, tracker, 3);
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.WHITE), Arrays.asList(1,1,0,2,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.ORANGE), Arrays.asList(2,0,2,0,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.BLUE), Arrays.asList(0,1,2,1,0));
        verifyPlayerHasResources(tracker.getPlayer(PlayerColor.RED), Arrays.asList(1,0,0,2,0));
    }
    
    private void verifyPlayerHasResources(Player player, List<Integer> resourceNums) {
        List<Resource> resources = Arrays.asList(Resource.GRAIN, Resource.BRICK, Resource.ORE, Resource.LUMBER, Resource.WOOL);
        for(int i = 0; i < resources.size(); i++) {
            assertEquals(player.getResourceCount(resources.get(i)), resourceNums.get(i));
        }
    }

}
