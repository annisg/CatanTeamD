package model;

import org.easymock.EasyMock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

public class VictoryPointCalculator_Test {
    
    PlayerColor[] colors = { PlayerColor.BLUE, PlayerColor.ORANGE, PlayerColor.RED, PlayerColor.WHITE };
    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    @Test
    public void TestVictoryPointCalculatorAtGameBegin(){
        for(PlayerColor color: colors) {
            LongestRoad longestRoad = mockedLongestRoad(PlayerColor.NONE);
            LargestArmy largestArmy = mockedLargestArmy(PlayerColor.NONE);
            VictoryPointCalculator calculator = new VictoryPointCalculator(longestRoad, largestArmy);
            Player player = mockPlayerWith(color, 5, 4, 0);
            EasyMock.replay(longestRoad, largestArmy, player);
            int point = calculator.calculateForPlayer(player);
            EasyMock.verify(longestRoad, largestArmy, player);
            assertEquals(0,point);
        }
    }

    @Test
    public void TestVictoryPointCalculatorWithOneSettlement(){
        for(PlayerColor color: colors) {
            LongestRoad longestRoad = mockedLongestRoad(PlayerColor.NONE);
            LargestArmy largestArmy = mockedLargestArmy(PlayerColor.NONE);
            VictoryPointCalculator calculator = new VictoryPointCalculator(longestRoad, largestArmy);
            Player player = mockPlayerWith(color, 4, 4,0);
            EasyMock.replay(longestRoad, largestArmy, player);
            int point = calculator.calculateForPlayer(player);
            EasyMock.verify(longestRoad, largestArmy, player);
            assertEquals(1,point);
        }
    }

    @Test
    public void TestVictoryPointCalculatorWithOneCity(){
        for(PlayerColor color: colors) {
            LongestRoad longestRoad = mockedLongestRoad(PlayerColor.NONE);
            LargestArmy largestArmy = mockedLargestArmy(PlayerColor.NONE);
            VictoryPointCalculator calculator = new VictoryPointCalculator(longestRoad, largestArmy);
            Player player = mockPlayerWith(color, 5, 3,0);
            EasyMock.replay(longestRoad, largestArmy, player);
            int point = calculator.calculateForPlayer(player);
            EasyMock.verify(longestRoad, largestArmy, player);
            assertEquals(2,point);
        }
    }

    @Test
    public void TestVictoryPointCalculatorWithLargestArmy(){
        for(PlayerColor color: colors) {
            LongestRoad longestRoad = mockedLongestRoad(PlayerColor.NONE);
            LargestArmy largestArmy = mockedLargestArmy(color);
            VictoryPointCalculator calculator = new VictoryPointCalculator(longestRoad, largestArmy);
            Player player = mockPlayerWith(color, 5, 4,0);
            EasyMock.replay(longestRoad, largestArmy, player);
            int point = calculator.calculateForPlayer(player);
            EasyMock.verify(longestRoad, largestArmy, player);
            assertEquals(2,point);
        }
    }

    @Test
    public void TestVictoryPointCalculatorWithLongestRoad(){
        for(PlayerColor color: colors) {
            LongestRoad longestRoad = mockedLongestRoad(color);
            LargestArmy largestArmy = mockedLargestArmy(PlayerColor.NONE);
            VictoryPointCalculator calculator = new VictoryPointCalculator(longestRoad, largestArmy);
            Player player = mockPlayerWith(color, 5, 4,0);
            EasyMock.replay(longestRoad, largestArmy, player);
            int point = calculator.calculateForPlayer(player);
            EasyMock.verify(longestRoad, largestArmy, player);
            assertEquals(2,point);
        }
    }

    @Test
    public void TestVictoryPointCalculatorWithDevCard(){
        for(PlayerColor color: colors) {
            LongestRoad longestRoad = mockedLongestRoad(PlayerColor.NONE);
            LargestArmy largestArmy = mockedLargestArmy(PlayerColor.NONE);
            VictoryPointCalculator calculator = new VictoryPointCalculator(longestRoad, largestArmy);
            Player player = mockPlayerWith(color, 5, 4,1);
            EasyMock.replay(longestRoad, largestArmy, player);
            int point = calculator.calculateForPlayer(player);
            EasyMock.verify(longestRoad, largestArmy, player);
            assertEquals(1,point);
        }
    }
    
    @Test
    public void TestCalculatorDoesNotIncorrectlyClaimLongestRoadLargestArmy(){
        for(int i = 0; i < colors.length; i++) {
            PlayerColor color = colors[i];
            PlayerColor otherColor = colors[(i+1) % colors.length];
            
            LongestRoad longestRoad = mockedLongestRoad(otherColor);
            LargestArmy largestArmy = mockedLargestArmy(otherColor);
            VictoryPointCalculator calculator = new VictoryPointCalculator(longestRoad, largestArmy);
            Player player = mockPlayerWith(color, 5, 4, 0);
            EasyMock.replay(longestRoad, largestArmy, player);
            int point = calculator.calculateForPlayer(player);
            EasyMock.verify(longestRoad, largestArmy, player);
            assertEquals(0, point);
        }
    }
    
    @Test
    public void TestVictoryPointCalculatorOnlyCountsVictoryPointCards(){
        for(PlayerColor color: colors) {
            LongestRoad longestRoad = mockedLongestRoad(PlayerColor.NONE);
            LargestArmy largestArmy = mockedLargestArmy(PlayerColor.NONE);
            VictoryPointCalculator calculator = new VictoryPointCalculator(longestRoad, largestArmy);
            Player player = mockPlayerWith(color, 5, 4, 0);
            DevelopmentCard valuelessCard = EasyMock.mock(DevelopmentCard.class);
    
            EasyMock.replay(longestRoad, largestArmy, player, valuelessCard);
            player.getDevelopmentCards().add(valuelessCard);
            
            int point = calculator.calculateForPlayer(player);
            EasyMock.verify(player, valuelessCard);
            assertEquals(0 ,point);
        }
    }

    @Test
    public void TestVictoryPointCalculatorWithCombination(){
        for(PlayerColor color: colors) {
            LongestRoad longestRoad = mockedLongestRoad(color);
            LargestArmy largestArmy = mockedLargestArmy(color);
            VictoryPointCalculator calculator = new VictoryPointCalculator(longestRoad, largestArmy);
            Player player = mockPlayerWith(color, 3, 2, 2);
            EasyMock.replay(longestRoad, largestArmy, player);
            int point = calculator.calculateForPlayer(player);
            EasyMock.verify(longestRoad, largestArmy, player);
            assertEquals(12,point);
        }
    }
    
    @Test
    public void testNotWinning() {
        
        int[] testScores = {0, 1, 9};
        
        for(PlayerColor color: colors) {
            for(int score: testScores) {
            
                VictoryPointCalculator calculator = EasyMock.partialMockBuilder(VictoryPointCalculator.class)
                        .addMockedMethod("calculateForPlayer").createMock();
                Player testPlayer = EasyMock.mock(Player.class);
                EasyMock.expect(calculator.calculateForPlayer(testPlayer)).andReturn(score);
                
                EasyMock.replay(calculator, testPlayer);
                assertFalse(calculator.isWinning(testPlayer));
                EasyMock.verify(calculator, testPlayer);
            }
        }
    }
    
    @Test
    public void testIsWinning() {
        
        int[] testScores = {10, 11, Integer.MAX_VALUE};
        
        for(PlayerColor color: colors) {
            for(int score: testScores) {
            
                VictoryPointCalculator calculator = EasyMock.partialMockBuilder(VictoryPointCalculator.class)
                        .addMockedMethod("calculateForPlayer").createMock();
                Player testPlayer = EasyMock.mock(Player.class);
                EasyMock.expect(calculator.calculateForPlayer(testPlayer)).andReturn(score);
                
                EasyMock.replay(calculator, testPlayer);
                assertTrue(calculator.isWinning(testPlayer));
                EasyMock.verify(calculator, testPlayer);
            }
        }
    }
    
    private Player mockPlayerWith(PlayerColor color, int numSettlements, int numCities, int numVictoryPointCards){
        Player player = EasyMock.mock(Player.class);

        EasyMock.expect(player.getInitialSettlementCount()).andStubReturn(5);
        EasyMock.expect(player.getInitialCityCount()).andStubReturn(4);
        EasyMock.expect(player.getSettlementCount()).andStubReturn(numSettlements);
        EasyMock.expect(player.getCityCount()).andStubReturn(numCities);
        EasyMock.expect(player.getColor()).andStubReturn(color);
        ArrayList<DevelopmentCard> developmentCards = new ArrayList<>();
        for ( int i = 0; i< numVictoryPointCards; i++){
           developmentCards.add(new VictoryPointCard(messages));
        }
        EasyMock.expect(player.getDevelopmentCards()).andStubReturn(developmentCards);
        return player;
    }
    
    private LongestRoad mockedLongestRoad(PlayerColor holder) {
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        EasyMock.expect(longestRoad.getHolder()).andStubReturn(holder);
        return longestRoad;
    }
    
    private LargestArmy mockedLargestArmy(PlayerColor holder) {
        LargestArmy largestArmy = EasyMock.mock(LargestArmy.class);
        EasyMock.expect(largestArmy.getHolder()).andStubReturn(holder);
        return largestArmy;
    }
}
