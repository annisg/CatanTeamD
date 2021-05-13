package model;

import exception.PlaceBuildingException;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class ProperyPlace_Place_Road_Tests {

    PlayerColor[] colors = { PlayerColor.BLUE, PlayerColor.ORANGE, PlayerColor.RED, PlayerColor.WHITE };
    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
    
    @Test
    public void TestPlaceRoadWithoutRemainingPieces(){
        
        for(PlayerColor color: colors) {
            Player player = mockedPlayer(color, 0);
            GameMap board = EasyMock.mock(GameMap.class);
            Edge testEdge = mockedEdge(PlayerColor.NONE);
            LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
            PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
            EasyMock.replay(longestRoad, player, board);
            
            try {
                propertyPlacer.placeRoadOnMap(player, board, testEdge);
                fail("Player placed more roads than max possible count.");
            }catch (PlaceBuildingException e){
                assertEquals("A road cannot be placed because all road pieces have been used.",e.getMessage());
            }
            EasyMock.verify(longestRoad, player, board);
        }
    }

    @Test
    public void TestPlaceRoadOnExistingSameColorRoad(){
        
        for(PlayerColor color: colors) {
            Player player = mockedPlayer(color, 1);
            GameMap board = EasyMock.mock(GameMap.class);
            Edge testEdge = mockedEdge(color);
            LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
            PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
            EasyMock.replay(longestRoad, player, board);
            
            try {
                propertyPlacer.placeRoadOnMap(player, board, testEdge);
                fail("Player placed a road on an existing road");
            }catch (PlaceBuildingException e){
                assertEquals("A road cannot be placed because the edge is not empty.", e.getMessage());
            }
            EasyMock.verify(longestRoad, player, board);
        }
    }

    @Test
    public void TestPlaceRoadOnExistingDifferentColorRoad(){
        
        for(int i = 0; i < colors.length; i++) {
            Player player = mockedPlayer(colors[i], 1);
            GameMap board = EasyMock.mock(GameMap.class);
            Edge testEdge = mockedEdge(colors[(i + 1) % colors.length]);
            LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
            PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
            EasyMock.replay(longestRoad, player, board);
            
            try {
                propertyPlacer.placeRoadOnMap(player, board, testEdge);
                fail("Player placed a road on an existing road");
            }catch (PlaceBuildingException e){
                assertEquals("A road cannot be placed because the edge is not empty.", e.getMessage());
            }
            EasyMock.verify(longestRoad, player, board);
        }
    }

    @Test
    public void testPlaceNotConnected(){
        
        for(int i = 0; i < colors.length; i++) {
            GameMap board = EasyMock.mock(GameMap.class);
            Edge testEdge = mockedEdge(PlayerColor.NONE);
            Player player = mockedPlayer(colors[i], 1);
            
            Intersection northIntersection = mockedIntersection(PlayerColor.NONE);
            Edge northEastEdge = mockedEdge(colors[(i + 1) % colors.length]);
            Edge northWestEdge = mockedEdge(colors[(i + 2) % colors.length]);
            
            Intersection southIntersection = mockedIntersection(PlayerColor.NONE);
            Edge southEastEdge = mockedEdge(colors[(i + 3) % colors.length]);
            Edge southWestEdge = mockedEdge(PlayerColor.NONE);
            
            ArrayList<Intersection> surroundingIntersections = new ArrayList<Intersection>(Arrays.asList(northIntersection, southIntersection));
            EasyMock.expect(board.getAllIntersectionsFromEdge(testEdge)).andStubReturn(surroundingIntersections);
            
            ArrayList<Edge> northEdges = new ArrayList<Edge>(Arrays.asList(northEastEdge, northWestEdge, testEdge));
            EasyMock.expect(board.getAllEdgesFromIntersection(northIntersection)).andStubReturn(northEdges);
            ArrayList<Edge> southEdges = new ArrayList<Edge>(Arrays.asList(southEastEdge, southWestEdge, testEdge));
            EasyMock.expect(board.getAllEdgesFromIntersection(southIntersection)).andStubReturn(southEdges);
            
            LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
            
            EasyMock.replay(longestRoad, player, board);
            
            PropertyPlacer testPlacer = new PropertyPlacer(longestRoad, messages);
            
            try {
                testPlacer.placeRoadOnMap(player, board, testEdge);
                fail("Player placed a road on a not connected edge.");
            } catch (PlaceBuildingException e){
                assertEquals("A road cannot be placed because the edge is not connected." ,e.getMessage());
            }
            
            EasyMock.verify(longestRoad, player, board);
        }
    }
    
    @Test
    public void testPlaceRoadBlockedByBuilding() {
        
        for(int i = 0; i < colors.length; i++) {
            GameMap board = EasyMock.mock(GameMap.class);
            Edge testEdge = mockedEdge(PlayerColor.NONE);
            Player player = mockedPlayer(colors[i], 1);
            
            Intersection northIntersection = mockedIntersection(colors[(i + 1) % colors.length]);
            Edge northEastEdge = mockedEdge(colors[i]);
            Edge northWestEdge = mockedEdge(PlayerColor.NONE);
            
            Intersection southIntersection = mockedIntersection(PlayerColor.NONE);
            Edge southEastEdge = mockedEdge(PlayerColor.NONE);
            Edge southWestEdge = mockedEdge(PlayerColor.NONE);
            
            ArrayList<Intersection> surroundingIntersections = new ArrayList<Intersection>(Arrays.asList(northIntersection, southIntersection));
            EasyMock.expect(board.getAllIntersectionsFromEdge(testEdge)).andStubReturn(surroundingIntersections);
            
            ArrayList<Edge> northEdges = new ArrayList<Edge>(Arrays.asList(northEastEdge, northWestEdge, testEdge));
            EasyMock.expect(board.getAllEdgesFromIntersection(northIntersection)).andStubReturn(northEdges);
            ArrayList<Edge> southEdges = new ArrayList<Edge>(Arrays.asList(southEastEdge, southWestEdge, testEdge));
            EasyMock.expect(board.getAllEdgesFromIntersection(southIntersection)).andStubReturn(southEdges);
            
            LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
            
            EasyMock.replay(longestRoad, player, board);
            
            PropertyPlacer testPlacer = new PropertyPlacer(longestRoad, messages);
            
            try {
                testPlacer.placeRoadOnMap(player, board, testEdge);
                fail("Expected PlaceBuildingException");
            }
            catch (PlaceBuildingException e) {
                assertEquals("A road cannot be placed because the edge is not connected.", e.getMessage());
            }
            
            EasyMock.verify(longestRoad, player, board);
        }
        
    }

    @Test
    public void TestPlaceRoadSuccessfulByBuilding(){
        GameMap board = EasyMock.mock(GameMap.class);
        Edge testEdge = EasyMock.mock(Edge.class);
        EasyMock.expect(testEdge.getRoadColor()).andStubReturn(PlayerColor.NONE);
        Player player = mockedPlayer(PlayerColor.RED, 1);
        
        Intersection northIntersection = mockedIntersection(PlayerColor.RED);
        Edge northEastEdge = mockedEdge(PlayerColor.NONE);
        Edge northWestEdge = mockedEdge(PlayerColor.NONE);
        
        Intersection southIntersection = mockedIntersection(PlayerColor.NONE);
        Edge southEastEdge = mockedEdge(PlayerColor.NONE);
        Edge southWestEdge = mockedEdge(PlayerColor.NONE);
        
        ArrayList<Intersection> surroundingIntersections = new ArrayList<Intersection>(Arrays.asList(northIntersection, southIntersection));
        EasyMock.expect(board.getAllIntersectionsFromEdge(testEdge)).andStubReturn(surroundingIntersections);
        
        ArrayList<Edge> northEdges = new ArrayList<Edge>(Arrays.asList(northEastEdge, northWestEdge, testEdge));
        EasyMock.expect(board.getAllEdgesFromIntersection(northIntersection)).andStubReturn(northEdges);
        ArrayList<Edge> southEdges = new ArrayList<Edge>(Arrays.asList(southEastEdge, southWestEdge, testEdge));
        EasyMock.expect(board.getAllEdgesFromIntersection(southIntersection)).andStubReturn(southEdges);
        
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        
        player.decrementRoadCount();
        testEdge.setRoad(PlayerColor.RED);
        longestRoad.updateLongestRoad(board);
        
        EasyMock.replay(testEdge, longestRoad, player, board);
        
        PropertyPlacer testPlacer = new PropertyPlacer(longestRoad, messages);
        
        testPlacer.placeRoadOnMap(player, board, testEdge);
        
        EasyMock.verify(testEdge, longestRoad, player, board);
    }
    
    @Test
    public void TestPlaceRoadSuccessfulByEdge(){
        
        for(int i = 0; i < colors.length; i++) {
            GameMap board = EasyMock.mock(GameMap.class);
            Edge testEdge = EasyMock.mock(Edge.class);
            EasyMock.expect(testEdge.getRoadColor()).andStubReturn(PlayerColor.NONE);
            Player player = mockedPlayer(colors[i], 1);
            
            Intersection northIntersection = mockedIntersection(colors[(i + 1) % colors.length]);
            Edge northEastEdge = mockedEdge(PlayerColor.NONE);
            Edge northWestEdge = mockedEdge(colors[(i + 1) % colors.length]);
            
            Intersection southIntersection = mockedIntersection(PlayerColor.NONE);
            Edge southEastEdge = mockedEdge(PlayerColor.NONE);
            Edge southWestEdge = mockedEdge(colors[i]);
            
            ArrayList<Intersection> surroundingIntersections = new ArrayList<Intersection>(Arrays.asList(northIntersection, southIntersection));
            EasyMock.expect(board.getAllIntersectionsFromEdge(testEdge)).andStubReturn(surroundingIntersections);
            
            ArrayList<Edge> northEdges = new ArrayList<Edge>(Arrays.asList(northEastEdge, northWestEdge, testEdge));
            EasyMock.expect(board.getAllEdgesFromIntersection(northIntersection)).andStubReturn(northEdges);
            ArrayList<Edge> southEdges = new ArrayList<Edge>(Arrays.asList(southEastEdge, southWestEdge, testEdge));
            EasyMock.expect(board.getAllEdgesFromIntersection(southIntersection)).andStubReturn(southEdges);
            
            LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
            
            player.decrementRoadCount();
            testEdge.setRoad(colors[i]);
            longestRoad.updateLongestRoad(board);
            
            EasyMock.replay(testEdge, longestRoad, player, board);
            
            PropertyPlacer testPlacer = new PropertyPlacer(longestRoad, messages);
            
            testPlacer.placeRoadOnMap(player, board, testEdge);
            
            EasyMock.verify(testEdge, longestRoad, player, board);
        }
    }

    @Test
    public void TestPlaceInitialRoadInvalidAttachment(){
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        PropertyPlacer placer = new PropertyPlacer(longestRoad, messages);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        Edge testEdge = mockedEdge(PlayerColor.NONE);
        Player player = mockedPlayer(PlayerColor.BLUE, 12);

        Intersection mockedIntersection = mockedIntersection(PlayerColor.BLUE);
        Edge edge1 = mockedEdge(PlayerColor.NONE);
        Edge edge2 = mockedEdge(PlayerColor.BLUE);

        ArrayList<Intersection> nearbyIntersections = new ArrayList<>();
        nearbyIntersections.add(mockedIntersection);

        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(edge1);
        edges.add(edge2);
        edges.add(testEdge);

        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(testEdge)).andStubReturn(nearbyIntersections);
        EasyMock.expect(gameMap.getAllEdgesFromIntersection(mockedIntersection)).andStubReturn(edges);

        EasyMock.replay(gameMap,player);
        try{
            placer.placeInitialRoad(player, gameMap, testEdge);
            fail("Initial road placed has to connect to the initial settlement in the same round");
        } catch ( PlaceBuildingException e ){
            assertEquals("An initial road can only be place nearby the initial settlement in the same round", e.getMessage());
        }

        EasyMock.verify(gameMap, testEdge, player, mockedIntersection, edge1, edge2);
    }

    @Test
    public void TestPlaceInitialRoadInvalidAttachment2Case(){
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        PropertyPlacer placer = new PropertyPlacer(longestRoad, messages);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        Edge testEdge = mockedEdge(PlayerColor.NONE);
        Player player = mockedPlayer(PlayerColor.BLUE, 12);

        Intersection mockedIntersection = mockedIntersection(PlayerColor.NONE);
        Edge edge1 = mockedEdge(PlayerColor.NONE);
        Edge edge2 = mockedEdge(PlayerColor.BLUE);

        ArrayList<Intersection> nearbyIntersections = new ArrayList<>();
        nearbyIntersections.add(mockedIntersection);

        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(edge1);
        edges.add(edge2);
        edges.add(testEdge);

        Intersection mockedIntersection2 = mockedIntersection(PlayerColor.NONE);
        nearbyIntersections.add(mockedIntersection2);

        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(testEdge)).andStubReturn(nearbyIntersections);
        EasyMock.expect(gameMap.getAllEdgesFromIntersection(mockedIntersection)).andStubReturn(edges);

        EasyMock.replay(gameMap,player);
        try{
            placer.placeInitialRoad(player, gameMap, testEdge);
            fail("Initial road placed has to connect to the initial settlement in the same round");
        } catch ( PlaceBuildingException e ){
            assertEquals("An initial road can only be place nearby the initial settlement in the same round", e.getMessage());
        }

        EasyMock.verify(gameMap, testEdge, player, mockedIntersection, edge1, edge2);
    }

    @Test
    public void TestPlaceInitialRoadValidAttachment(){
        PropertyPlacer placer = EasyMock.partialMockBuilder(PropertyPlacer.class).addMockedMethod("placeRoadOnMap").createMock();
        GameMap gameMap = EasyMock.mock(GameMap.class);
        Edge testEdge = mockedEdge(PlayerColor.NONE);
        Player player = mockedPlayer(PlayerColor.BLUE, 12);

        Intersection mockedIntersection = mockedIntersection(PlayerColor.BLUE);
        Edge edge1 = mockedEdge(PlayerColor.NONE);
        Edge edge2 = mockedEdge(PlayerColor.NONE);

        ArrayList<Intersection> nearbyIntersections = new ArrayList<>();
        nearbyIntersections.add(mockedIntersection);

        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(edge1);
        edges.add(edge2);
        edges.add(testEdge);

        Intersection mockedIntersection2 = mockedIntersection(PlayerColor.NONE);
        nearbyIntersections.add(mockedIntersection2);

        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(testEdge)).andStubReturn(nearbyIntersections);
        EasyMock.expect(gameMap.getAllEdgesFromIntersection(mockedIntersection)).andStubReturn(edges);
        placer.placeRoadOnMap(player,gameMap,testEdge);

        EasyMock.replay(placer, gameMap, player);
        placer.placeInitialRoad(player, gameMap, testEdge);


        EasyMock.verify(placer, gameMap, testEdge, player, mockedIntersection, edge1, edge2);
    }
    
    public Edge mockedEdge(PlayerColor color) {
        Edge edge = EasyMock.mock(Edge.class);
        EasyMock.expect(edge.getRoadColor()).andStubReturn(color);
        EasyMock.replay(edge);
        return edge;
    }
    
    public Intersection mockedIntersection(PlayerColor color) {
        Intersection intersection = EasyMock.mock(Intersection.class);
        EasyMock.expect(intersection.getBuildingColor()).andStubReturn(color);
        EasyMock.replay(intersection);
        return intersection;
    }
    
    public Player mockedPlayer(PlayerColor color, int roadPieces) {
        Player player = EasyMock.mock(Player.class);
        EasyMock.expect(player.getColor()).andStubReturn(color);
        EasyMock.expect(player.getRoadCount()).andStubReturn(roadPieces);
        return player;
    }
}
