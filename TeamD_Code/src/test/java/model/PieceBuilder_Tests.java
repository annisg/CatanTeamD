package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import exception.PlaceBuildingException;

public class PieceBuilder_Tests {

    private GameMap board;
    private PropertyPlacer placer;
    private PieceBuilder builder;
    private Player testPlayer;
    private Intersection testIntersection;
    private Edge testEdge;
    private DevelopmentDeck testDeck;
    private DevelopmentCard testCard;
    
    @Test
    public void testBuildSettlementNotEnoughResources() {
        
        int[][] resourcesToTry = {
                { 0, 0, 0, 0, 0 },
                { 0, 1, 0, 1, 1 },
                { 1, 0, 0, 1, 1 }, 
                { 1, 1, 0, 0, 1 },
                { 1, 1, 0, 1, 0 }
                };
        
        for(int i = 0; i < resourcesToTry.length; i++) {
            testFailToBuildSettlement(resourcesToTry[i]);
        }
                
    }
    
    @Test
    public void testBuildSettlementEnoughResources() {
        
        int[][] resourcesToTry = {
                { 1, 1, 0, 1, 1 },
                { 2, 2, 1, 2, 2 }
        };
        
        for(int i = 0; i < resourcesToTry.length; i++) {
        
            testSetup(resourcesToTry[i]);
            
            placer.placeRegularSettlementOnMap(testPlayer, board, testIntersection);
            
            testPlayer.removeResource(Resource.GRAIN, 1);
            testPlayer.removeResource(Resource.BRICK, 1);
            testPlayer.removeResource(Resource.LUMBER, 1);
            testPlayer.removeResource(Resource.WOOL, 1);
            
            EasyMock.replay(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
                    
            builder.buildSettlement(testPlayer, testIntersection);
          
            EasyMock.verify(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
        
        }
        
    }
    
    @Test
    public void testBuildSettlementPlacementFailure() {
        
        int[] resourcesToTry = { 1, 1, 0, 1, 1 };
            
        testSetup(resourcesToTry);
        
        placer.placeRegularSettlementOnMap(testPlayer, board, testIntersection);
        EasyMock.expectLastCall().andThrow(new PlaceBuildingException(""));

        EasyMock.replay(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);

        try {

            builder.buildSettlement(testPlayer, testIntersection);

            fail("Expected PlaceBuildingException");

        } catch (PlaceBuildingException e) {

        }

        EasyMock.verify(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
        
        
    }
    
    @Test
    public void testBuildCityNotEnoughResources() {
        
        int[][] resourcesToTry = {
                { 0, 0, 0, 0, 0 },
                { 1, 0, 3, 0, 0 },
                { 2, 0, 2, 0, 0 }
        };
        
        
        for(int i = 0; i < resourcesToTry.length; i++) {
            testFailToBuildCity(resourcesToTry[i]);
        }
        
    }
    
    @Test
    public void testBuildCityEnoughResources() {
        
        int[][] resourcesToTry = {
                { 2, 0, 3, 0, 0 },
                { 3, 1, 4, 1, 1 }
        };
        
        
        for(int i = 0; i < resourcesToTry.length; i++) {
            
            testSetup(resourcesToTry[i]);
            
            testPlayer.removeResource(Resource.GRAIN, 2);
            testPlayer.removeResource(Resource.ORE, 3);
            placer.placeCityOnMap(testPlayer, testIntersection);
            
            EasyMock.replay(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);

            builder.buildCity(testPlayer, testIntersection);
            
            EasyMock.verify(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
        }
        
    }
    
    @Test
    public void testBuildCityPlacementFailure() {
        
        int[][] resourcesToTry = {
                { 2, 0, 3, 0, 0 },
                { 3, 1, 4, 1, 1 }
        };
        
        
        for(int i = 0; i < resourcesToTry.length; i++) {
            
            testSetup(resourcesToTry[i]);
            
            placer.placeCityOnMap(testPlayer, testIntersection);
            EasyMock.expectLastCall().andThrow(new PlaceBuildingException(""));
            
            EasyMock.replay(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
            
            try {
                builder.buildCity(testPlayer, testIntersection);
                fail("Expected PlaceBuildingException");
            } catch (PlaceBuildingException e) {
                
            }
            
            EasyMock.verify(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
        }
        
    }
    
    @Test
    public void testBuildRoadNotEnoughResources() {
        
        int[][] resourcesToTry = {
                { 0, 0, 0, 0, 0 },
                { 0, 1, 0, 0, 0 },
                { 0, 0, 0, 1, 0 }
                };
        
        for(int i = 0; i < resourcesToTry.length; i++) {
            testFailToBuildRoad(resourcesToTry[i]);
        }
    }
    
    @Test
    public void testBuildRoadEnoughResources() {
        
        int[][] resourcesToTry = {
                { 0, 1, 0, 1, 0 },
                { 1, 2, 1, 2, 1 }
                };
        
        for(int i = 0; i < resourcesToTry.length; i++) {
            
            testSetup(resourcesToTry[i]);
            
            testPlayer.removeResource(Resource.BRICK, 1);
            testPlayer.removeResource(Resource.LUMBER, 1);
            placer.placeRoadOnMap(testPlayer, board, testEdge);
            EasyMock.replay(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
            
            builder.buildRoad(testPlayer, testEdge);
            
            EasyMock.verify(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);

        }
    }
    
    @Test
    public void testBuildRoadPlacementFailure() {
        
        int[][] resourcesToTry = {
                { 0, 1, 0, 1, 0 },
                { 1, 2, 1, 2, 1 }
                };
        
        for(int i = 0; i < resourcesToTry.length; i++) {
            
            testSetup(resourcesToTry[i]);
            
            placer.placeRoadOnMap(testPlayer, board, testEdge);
            EasyMock.expectLastCall().andThrow(new PlaceBuildingException(""));
            EasyMock.replay(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
            
            try {
                builder.buildRoad(testPlayer, testEdge);
                fail("Expected PlaceBuildingException");
            } catch (PlaceBuildingException e) {
                
            }
            EasyMock.verify(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
        }
    }

    @Test
    public void testBuildDevelopmentCardNotEnoughResources() {
        
        int[][] resourcesToTry = {
                { 0, 0, 0, 0, 0 },
                { 0, 0, 1, 0, 1 },
                { 1, 0, 0, 0, 1 }, 
                { 1, 0, 1, 0, 0 },
                };
        
        for(int i = 0; i < resourcesToTry.length; i++) {
            testFailToBuildDevelopmentCard(resourcesToTry[i]);
        }
    }
    
    @Test
    public void testBuildDevelopmentCardEnoughResources() {
        
        int[][] resourcesToTry = {
                { 1, 0, 1, 0, 1 },
                { 2, 1, 2, 1, 2 }
                };
        
        for(int i = 0; i < resourcesToTry.length; i++) {

            testSetup(resourcesToTry[i]);
            EasyMock.expect(testDeck.drawCard()).andStubReturn(testCard);
            EasyMock.expect(testCard.getName()).andReturn("Card Name");
            testPlayer.removeResource(Resource.GRAIN, 1);
            testPlayer.removeResource(Resource.ORE, 1);
            testPlayer.removeResource(Resource.WOOL, 1);
            testPlayer.giveDevelopmentCard(testCard);
            EasyMock.replay(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
            
            assertEquals("Card Name", builder.buildDevelopmentCard(testPlayer));
            
            EasyMock.verify(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
        }
    }
    
    @Test
    public void testBuildDevelopmentCardEmptyDeck() {
        
        int[][] resourcesToTry = {
                { 1, 0, 1, 0, 1 },
                { 2, 1, 2, 1, 2 }
                };
        
        for(int i = 0; i < resourcesToTry.length; i++) {

            testSetup(resourcesToTry[i]);
            EasyMock.expect(testDeck.drawCard()).andThrow(new IllegalStateException());
            EasyMock.replay(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
            
            try {
                builder.buildDevelopmentCard(testPlayer);
                fail("Expected EmptyDeckException");
            } catch (IllegalStateException e) {
                
            }
            EasyMock.verify(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
        }
    }

    @Test
    public void testBuildInitialSettlement(){
        PropertyPlacer propertyPlacer = EasyMock.mock(PropertyPlacer.class);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        DevelopmentDeck developmentDeck = EasyMock.mock(DevelopmentDeck.class);
        Player player = EasyMock.mock(Player.class);
        Intersection intersection = EasyMock.mock(Intersection.class);
        PieceBuilder pieceBuilder = new PieceBuilder(gameMap,propertyPlacer,developmentDeck);
        propertyPlacer.placeInitialSettlement(player,gameMap,intersection);

        EasyMock.replay(propertyPlacer);
        pieceBuilder.buildInitialSettlement(player,intersection);
        EasyMock.verify(propertyPlacer);

    }

    @Test
    public void testBuildInitialSettlementRound2(){
        PropertyPlacer propertyPlacer = EasyMock.mock(PropertyPlacer.class);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        DevelopmentDeck developmentDeck = EasyMock.mock(DevelopmentDeck.class);
        Player player = EasyMock.mock(Player.class);
        Intersection intersection = EasyMock.mock(Intersection.class);
        PieceBuilder pieceBuilder = new PieceBuilder(gameMap,propertyPlacer,developmentDeck);
        propertyPlacer.placeInitialSettlementRound2(player,gameMap,intersection);

        EasyMock.replay(propertyPlacer);
        pieceBuilder.buildInitialSettlementRound2(player,intersection);
        EasyMock.verify(propertyPlacer);

    }

    @Test
    public void testBuildInitialRoad(){
        PropertyPlacer propertyPlacer = EasyMock.mock(PropertyPlacer.class);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        DevelopmentDeck developmentDeck = EasyMock.mock(DevelopmentDeck.class);
        Player player = EasyMock.mock(Player.class);
        Edge edge = EasyMock.mock(Edge.class);
        PieceBuilder pieceBuilder = new PieceBuilder(gameMap,propertyPlacer,developmentDeck);
        propertyPlacer.placeInitialRoad(player,gameMap,edge);

        EasyMock.replay(propertyPlacer);
        pieceBuilder.buildInitialRoad(player,edge);
        EasyMock.verify(propertyPlacer);

    }

    private void testFailToBuildSettlement(int[] resourcesToTry) {
        
        testSetup(resourcesToTry);
        EasyMock.replay(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
                
        try {
            builder.buildSettlement(testPlayer, testIntersection);
            fail("Expected InsufficientResourcesException");
        } catch (IllegalStateException e) {
            
        }      
        EasyMock.verify(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
    }
    
    private void testFailToBuildCity(int[] resourcesToTry) {
        
        testSetup(resourcesToTry);
        EasyMock.replay(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
        
        try {
            builder.buildCity(testPlayer, testIntersection);
            fail("Expected InsufficientResourcesException");
        } catch (IllegalStateException e) {
            
        }   
        EasyMock.verify(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
        
    }

    
    private void testFailToBuildRoad(int[] resourcesToTry) {
        
        testSetup(resourcesToTry);
        
        EasyMock.replay(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
        
        try {
            builder.buildRoad(testPlayer, testEdge);
            fail("Expected InsufficientResourcesException");
        } catch (IllegalStateException e) {
            
        }
        
        EasyMock.verify(board, placer, testPlayer, testIntersection, testEdge, testDeck, testCard);
        
    }
    
    private void testFailToBuildDevelopmentCard(int[] resourcesToTry) {
        
        testSetup(resourcesToTry);
        
        EasyMock.replay(testPlayer, board, placer);
        
        try {
            builder.buildDevelopmentCard(testPlayer);
            fail("Expected InsufficientResourcesException");
        } catch (IllegalStateException e) {
            
        }
        
        EasyMock.verify(testPlayer, board, placer);
        
    }
    
    private void testSetup(int[] resourcesToTry) {
        
        board = EasyMock.mock(GameMap.class);
        placer = EasyMock.mock(PropertyPlacer.class);
        testPlayer = mockedPlayerWithResources(resourcesToTry);
        testIntersection = EasyMock.mock(Intersection.class);
        testEdge = EasyMock.mock(Edge.class);
        
        testDeck = EasyMock.mock(DevelopmentDeck.class);
        testCard = EasyMock.mock(DevelopmentCard.class);
        
        builder = new PieceBuilder(board, placer, testDeck);
        
    }
    
    private Player mockedPlayerWithResources(int[] resources) {
        return mockedPlayerWithResources(resources[0], resources[1], resources[2], resources[3], resources[4]);    
    }
    
    
    private Player mockedPlayerWithResources(int grain, int brick, int ore, int lumber, int wool) {
        
        Player player = EasyMock.mock(Player.class);
        
        EasyMock.expect(player.getResourceCount(Resource.GRAIN)).andStubReturn(grain);
        EasyMock.expect(player.getResourceCount(Resource.BRICK)).andStubReturn(brick);
        EasyMock.expect(player.getResourceCount(Resource.ORE)).andStubReturn(ore);
        EasyMock.expect(player.getResourceCount(Resource.LUMBER)).andStubReturn(lumber);
        EasyMock.expect(player.getResourceCount(Resource.WOOL)).andStubReturn(wool);
        
        return player;
    }

    
}
