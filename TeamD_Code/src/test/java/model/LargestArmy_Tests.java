package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.Test;

public class LargestArmy_Tests {

    PlayerColor[] colors = { PlayerColor.BLUE, PlayerColor.ORANGE, PlayerColor.RED, PlayerColor.WHITE };
    
    @Test
    public void testInitialState() {
        
        TurnTracker tracker = EasyMock.mock(TurnTracker.class);
        LargestArmy largestArmy = new LargestArmy(tracker);
        EasyMock.replay(tracker);
        
        assertNobodyHasLargestArmy(largestArmy);
        
        for(int i = 0; i < colors.length; i++) {
            largestArmy = new LargestArmy(tracker, colors[i], i);
            
            assertEquals(colors[i], largestArmy.getHolder());
            assertEquals(i, largestArmy.getSize());
        }
        
        EasyMock.verify(tracker);
        
    }

    private void assertNobodyHasLargestArmy(LargestArmy largestArmy) {
        assertEquals(PlayerColor.NONE, largestArmy.getHolder());
        assertEquals(0, largestArmy.getSize());
    }
    
    @Test
    public void testNoArmyLargeEnough() {
        
        int[][] armySizes = {
                {0, 0, 0, 0},
                {2, 2, 2, 2}
        };
        
        for(int i = 0; i < armySizes.length; i++) {
            ArrayList<Player> playerList = mockedPlayerList(armySizes[i]);
            TurnTracker tracker = mockedTurnTracker(playerList);
            
            EasyMock.replay(playerList.toArray());
            EasyMock.replay(tracker);
            
            LargestArmy largestArmy = new LargestArmy(tracker);
            largestArmy.updateLargestArmy();
            assertNobodyHasLargestArmy(largestArmy);
        
            EasyMock.verify(playerList.toArray());
            EasyMock.verify(tracker);
        }   
    }
    
    @Test
    public void testThreeKnightsGetsLargestArmy() {
        
        int[][] armySizes = {
                {3, 2, 2, 2},
                {1, 3, 0, 1},
                {0, 0, 3, 0},
                {0, 0, 0, 3}
        };
        
        for(int i = 0; i < armySizes.length; i++) {
            ArrayList<Player> playerList = mockedPlayerList(armySizes[i]);
            TurnTracker tracker = mockedTurnTracker(playerList);
            
            EasyMock.replay(playerList.toArray());
            EasyMock.replay(tracker);
            
            LargestArmy largestArmy = new LargestArmy(tracker);
            largestArmy.updateLargestArmy();
            
            assertEquals(colors[i], largestArmy.getHolder());
            assertEquals(3, largestArmy.getSize());
        
            EasyMock.verify(playerList.toArray());
            EasyMock.verify(tracker);
        }   
    }
    
    @Test
    public void testLargestArmyNotLostOnTie() {
        
        int[][] armySizes = {
                {3, 2, 3, 2},
                {1, 4, 4, 1},
                {0, 0, 5, 5},
                {0, 5, 0, 5}
        };
        
        for(int i = 0; i < armySizes.length; i++) {
            ArrayList<Player> playerList = mockedPlayerList(armySizes[i]);
            TurnTracker tracker = mockedTurnTracker(playerList);
            
            EasyMock.replay(playerList.toArray());
            EasyMock.replay(tracker);
            
            LargestArmy largestArmy = new LargestArmy(tracker, colors[i], 3+i);
            largestArmy.updateLargestArmy();
            
            assertEquals(colors[i], largestArmy.getHolder());
            assertEquals(3+i, largestArmy.getSize());
        
            EasyMock.verify(playerList.toArray());
            EasyMock.verify(tracker);
        }   
    }
    
    @Test
    public void testLargestArmyChangesHands() {
        
        int[][] armySizes = {
                {3, 2, 4, 2},
                {1, 4, 1, 5},
                {6, 0, 5, 0},
                {0, 7, 0, 6}
        };
        
        for(int i = 0; i < armySizes.length; i++) {
            ArrayList<Player> playerList = mockedPlayerList(armySizes[i]);
            TurnTracker tracker = mockedTurnTracker(playerList);
            
            EasyMock.replay(playerList.toArray());
            EasyMock.replay(tracker);
            
            LargestArmy largestArmy = new LargestArmy(tracker, colors[i], 3+i);
            largestArmy.updateLargestArmy();
            
            assertEquals(colors[(i+2) % colors.length], largestArmy.getHolder());
            assertEquals(4+i, largestArmy.getSize());
        
            EasyMock.verify(playerList.toArray());
            EasyMock.verify(tracker);
        }   
    }
    
    @Test
    public void testHolderCreatesLargerArmy() {
        
        int[][] armySizes = {
                {4, 0, 0, 0},
                {0, 5, 0, 0},
                {0, 0, 6, 0},
                {0, 0, 0, 7}
        };
        
        for(int i = 0; i < armySizes.length; i++) {
            ArrayList<Player> playerList = mockedPlayerList(armySizes[i]);
            TurnTracker tracker = mockedTurnTracker(playerList);
            
            EasyMock.replay(playerList.toArray());
            EasyMock.replay(tracker);
            
            LargestArmy largestArmy = new LargestArmy(tracker, colors[i], 3+i);
            largestArmy.updateLargestArmy();
            
            assertEquals(colors[i], largestArmy.getHolder());
            assertEquals(4+i, largestArmy.getSize());
        
            EasyMock.verify(playerList.toArray());
            EasyMock.verify(tracker);
        }   
    }
    
    private ArrayList<Player> mockedPlayerList(int[] armySizes) {
        
        ArrayList<Player> players = new ArrayList<Player>();
        
        for(int i = 0; i < armySizes.length; i++) {
            Player player = EasyMock.mock(Player.class);
            EasyMock.expect(player.getNumKnights()).andStubReturn(armySizes[i]);
            EasyMock.expect(player.getColor()).andStubReturn(colors[i]);
            players.add(player);
        }
        
        return players;
        
    }

    private TurnTracker mockedTurnTracker(ArrayList<Player> players) {
        
        TurnTracker tracker = EasyMock.mock(TurnTracker.class);
        EasyMock.expect(tracker.getNumPlayers()).andStubReturn(players.size());
        
        for(int i = 0; i < players.size(); i++) {
            EasyMock.expect(tracker.getPlayer(i)).andStubReturn(players.get(i));
        }
        
        return tracker;
    }

}
