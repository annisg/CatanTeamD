package model;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Test;
import java.util.*;

public class TurnTracker_Tests {
    
    PlayerColor[] colors = { PlayerColor.BLUE, PlayerColor.ORANGE, PlayerColor.WHITE, PlayerColor.RED };
    
    @Test
    public void testSetupThreePlayers() {

        testSetupWithXPlayers(3, 0, new ArrayList<PlayerColor>(
                Arrays.asList(PlayerColor.WHITE, PlayerColor.ORANGE, PlayerColor.BLUE)));
        
        testSetupWithXPlayers(3, 1, new ArrayList<PlayerColor>(
                Arrays.asList(PlayerColor.ORANGE, PlayerColor.WHITE, PlayerColor.BLUE)));
    }
    
    @Test
    public void testSetupFourPlayers() {

        testSetupWithXPlayers(4, 0, new ArrayList<PlayerColor>(
                Arrays.asList(PlayerColor.RED, PlayerColor.BLUE, PlayerColor.ORANGE, PlayerColor.WHITE)));
        
        testSetupWithXPlayers(4, 2, new ArrayList<PlayerColor>(
                Arrays.asList(PlayerColor.RED, PlayerColor.ORANGE, PlayerColor.BLUE, PlayerColor.WHITE)));
    }
    
    
    @Test
    public void testIllegalSetups() {

        int[] illegalNumbers = {Integer.MIN_VALUE, -1, 2, 5, Integer.MAX_VALUE};
        
        for (int i = 0; i < illegalNumbers.length; i++) {
            
            testIllegalSetupNumber(illegalNumbers[i]);
        }
    }

    @Test
    public void testMakePlayer() {
        
        TurnTracker tracker = new TurnTracker(new Random());
        
        for(PlayerColor color : colors) {
            
            Player player = tracker.makePlayer(color);
            
            assertEquals(color, player.getColor());
            assertEquals(0, player.getResourceHandSize());
        }
    }
    
    @Test
    public void testCurrentPlayerSetup() {
        
        int[] playerNums = {3, 4};
        
        for(int i = 0; i < playerNums.length; i++) {
        
            TurnTracker tracker = makeTurnTrackerWithPlayerMocks(new Random(0));
            EasyMock.replay(tracker);
            tracker.setupPlayers(playerNums[i]);
            
            assertEquals(tracker.getPlayer(0), tracker.getCurrentPlayer());
            EasyMock.verify(tracker);
        }
    }
    
    @Test
    public void testPassTurn() {
        
        int[] playerNums = {3, 4};
        
        for(int i = 0; i < playerNums.length; i++) {
        
            TurnTracker tracker = makeTurnTrackerWithPlayerMocks(new Random(0));
            EasyMock.replay(tracker);
            tracker.setupPlayers(playerNums[i]);
            
            for(int j = 0; j < playerNums[i] * 2; j++) {
            
                tracker.passTurn();
                assertEquals(tracker.getPlayer((j + 1) % playerNums[i]), tracker.getCurrentPlayer());
                
            }
            EasyMock.verify(tracker);
        }
    }
    
    @Test
    public void testSetupBeginnerResourcesAndPieces() {
        
        int[] playerNums = {3, 4};
        Resource[] resources  = { Resource.GRAIN, Resource.BRICK, Resource.ORE, Resource.LUMBER, Resource.WOOL };
        
        HashMap<PlayerColor, List<Integer>> startingResources = new HashMap<PlayerColor, List<Integer>>();
        startingResources.put(PlayerColor.BLUE, Arrays.asList(0, 1, 1, 1, 0));
        startingResources.put(PlayerColor.ORANGE, Arrays.asList(2, 0, 1, 0, 0));
        startingResources.put(PlayerColor.WHITE, Arrays.asList(1, 1, 0, 1, 0));
        startingResources.put(PlayerColor.RED, Arrays.asList(1, 0, 0, 2, 0));
        
        for(int i = 0; i < playerNums.length; i++) {
            TurnTracker tracker = EasyMock.partialMockBuilder(TurnTracker.class)
                    .addMockedMethod("getPlayer", Integer.TYPE).addMockedMethod("getNumPlayers").createMock();
            ArrayList<Player> players = new ArrayList<Player>();
            
            for(int j = 0; j < playerNums[i]; j++) {
                Player testPlayer = EasyMock.mock(Player.class);
                players.add(testPlayer);
                
                int index = (i + j) % playerNums[i];
                
                EasyMock.expect(testPlayer.getColor()).andStubReturn(colors[index]);
                EasyMock.expect(tracker.getPlayer(index)).andStubReturn(testPlayer);
                
                List<Integer> currentList = startingResources.get(colors[index]);
                for(int k = 0; k < currentList.size(); k++) {
                    if(currentList.get(k) != 0) {
                        testPlayer.giveResource(resources[k], currentList.get(k));
                    }
                }
                
                for(int k = 0; k < 2; k++) {
                    testPlayer.decrementSettlementCount();
                    testPlayer.decrementRoadCount();
                }
                
            }
            
            EasyMock.expect(tracker.getNumPlayers()).andStubReturn(playerNums[i]);
            
            EasyMock.replay(tracker);
            EasyMock.replay(players.toArray());
            
            tracker.setupBeginnerResourcesAndPieces();
            
            EasyMock.verify(tracker);
            EasyMock.verify(players.toArray());
        }
    }
    
    @Test
    public void testSetupBeginnerResourcesAndPiecesIllegalPlayer() {
        TurnTracker tracker = EasyMock.partialMockBuilder(TurnTracker.class)
                .addMockedMethod("getPlayer", Integer.TYPE).addMockedMethod("getNumPlayers").createMock();
        Player testPlayer = EasyMock.mock(Player.class);
        EasyMock.expect(testPlayer.getColor()).andStubReturn(PlayerColor.NONE);
        
        EasyMock.expect(tracker.getNumPlayers()).andReturn(1);
        EasyMock.expect(tracker.getPlayer(0)).andReturn(testPlayer);
        
        EasyMock.replay(tracker, testPlayer);
        
        try {
            tracker.setupBeginnerResourcesAndPieces();
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Illegal Player in tracker.", e.getMessage());
        }
        EasyMock.verify(tracker, testPlayer);
    }
    
    private void testSetupWithXPlayers(int numPlayers, long randomSeed, List<PlayerColor> expectedOrder) {
        
        TurnTracker tracker = makeTurnTrackerWithPlayerMocks(new Random(randomSeed));
        EasyMock.replay(tracker);
        
        tracker.setupPlayers(numPlayers);
        
        assertEquals(numPlayers, tracker.getNumPlayers());
        
        for(int i = 0; i < numPlayers; i++) {
            
            Player player = tracker.getPlayer(i);
            
            assertEquals(expectedOrder.get(i), player.getColor());
            
        }
        
        EasyMock.verify(tracker);
    }
    
    private void testIllegalSetupNumber(int numPlayers) {
        
        try {
            testSetupWithXPlayers(numPlayers, 0, new ArrayList<PlayerColor>());
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            assertEquals("Illegal number of players provided to setup.", e.getMessage());
        }
        
    }
    
    private TurnTracker makeTurnTrackerWithPlayerMocks(Random random) {
        
        TurnTracker tracker = EasyMock.partialMockBuilder(TurnTracker.class).addMockedMethod("makePlayer").withConstructor(random).createMock();
                
        for(PlayerColor color : colors) {
            EasyMock.expect(tracker.makePlayer(color)).andStubReturn(mockedPlayer(color, tracker));
        }
        
        return tracker;
    }
    
    private Player mockedPlayer(PlayerColor color, TurnTracker tracker) {
        
        Player player = EasyMock.mock(Player.class);
        
        EasyMock.expect(player.getColor()).andStubReturn(color);
        player.addTracker(tracker);
        EasyMock.replay(player);
        
        return player;
        
    }

    @Test
    public void testPassInitialTurn() {

        int[] playerNums = {3, 4};

        for(int i = 0; i < playerNums.length; i++) {

            TurnTracker tracker = makeTurnTrackerWithPlayerMocks(new Random(0));

            EasyMock.replay(tracker);

            tracker.setupPlayers(playerNums[i]);

            assertEquals(tracker.getPlayer(0), tracker.getCurrentPlayer());

            for(int j = 0; j < playerNums[i]-1; j++) {
                tracker.passInitialTurn();
                assertEquals(tracker.getPlayer(j+1), tracker.getCurrentPlayer());
            }

            for(int j = playerNums[i]-1; j > 0; j--) {
                tracker.passInitialTurn();
                assertEquals(tracker.getPlayer(j), tracker.getCurrentPlayer());
            }

            tracker.passInitialTurn();
            assertEquals(0,tracker.currentPlayerIndex);

            tracker.passInitialTurn();
            assertEquals(0,tracker.currentPlayerIndex);

            EasyMock.verify(tracker);

        }

    }

}
