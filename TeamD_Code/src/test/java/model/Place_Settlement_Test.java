package model;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import exception.PlaceBuildingException;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class Place_Settlement_Test {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
    
    @Test
    public void TestPlaceSettlementSucceed(){
        for(PlayerColor color: PlayerColor.values()) {
            if(color != PlayerColor.NONE) {
                Player player = mockedPlayer(color, 1);
                GameMap gameMap = EasyMock.mock(GameMap.class);
                Intersection intersection = mockedIntersection(PlayerColor.NONE);
                LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
                
                intersection.setSettlement(color);
                longestRoad.updateLongestRoad(gameMap);
                player.decrementSettlementCount();
                EasyMock.replay(player, gameMap, longestRoad, intersection);
                
                PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
                propertyPlacer.placeSettlement(player, gameMap, intersection);
                
                EasyMock.verify(player, gameMap, longestRoad, intersection);
            }
        }
    }

    @Test
    public void TestPlaceSettlementWithNoRemainingPieces(){
        Player player = mockedPlayer(PlayerColor.BLUE, 0);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
        Intersection intersection = mockedIntersection(PlayerColor.NONE);
        
        EasyMock.replay(player, longestRoad, gameMap, intersection);
        
        try {
            propertyPlacer.placeSettlement(player, gameMap, intersection);
            fail("Player placed more settlement than max possible count.");
        }catch (PlaceBuildingException e){
            //pass
        }
        EasyMock.verify(player, longestRoad, gameMap, intersection);
    }

    @Test
    public void TestPlaceSettlementOnTopOfSettlement(){
        Player player = mockedPlayer(PlayerColor.BLUE, 2);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        Intersection intersection = mockedIntersection(PlayerColor.BLUE);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
        EasyMock.replay(player, gameMap, longestRoad, intersection);
        
        try {
            propertyPlacer.placeSettlement(player, gameMap, intersection);
            fail("Settlement cannot be place on top of each other");
        }catch (PlaceBuildingException e){
            assertEquals("A settlement cannot be placed because of a building already exist in this intersection.", e.getMessage());
        }
        EasyMock.verify(player, gameMap, longestRoad, intersection);
    }

    @Test
    public void TestPlaceSettlementWithNoConnectedRoadFromCurrentPlayer(){
        Player player = new Player(PlayerColor.BLUE);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        Intersection mockedIntersection = EasyMock.mock(Intersection.class);
        Edge edge1 = EasyMock.mock(Edge.class);
        Edge edge2 = EasyMock.mock(Edge.class);
        Edge edge3 = EasyMock.mock(Edge.class);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        
        ArrayList<Edge> arrEdge = new ArrayList<>();
        arrEdge.add(edge1);
        arrEdge.add(edge2);
        arrEdge.add(edge3);
        EasyMock.expect(gameMap.getIntersection(0, 0)).andReturn(mockedIntersection);
        EasyMock.expect(gameMap.getAllEdgesFromIntersection(mockedIntersection)).andReturn(arrEdge);
        EasyMock.expect(edge1.getRoadColor()).andReturn(PlayerColor.NONE);
        EasyMock.expect(edge2.getRoadColor()).andReturn(PlayerColor.RED);
        EasyMock.expect(edge3.getRoadColor()).andReturn(PlayerColor.ORANGE);

        EasyMock.replay(gameMap, mockedIntersection, edge1, edge2, edge3, longestRoad);
        Intersection intersection = gameMap.getIntersection(0,0);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
        try {
            propertyPlacer.placeRegularSettlementOnMap(player, gameMap, intersection);
            fail("Settlement cannot be place without connected Road.");
        }catch (PlaceBuildingException e){
            //pass
        }

        EasyMock.verify(gameMap, mockedIntersection, edge1, edge2, edge3, longestRoad);
    }



    @Test
    public void TestPlaceSettlementAdjacentToAnotherSettlementFails(){
        Player player = new Player(PlayerColor.BLUE);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        Intersection mockedIntersection = EasyMock.mock(Intersection.class);
        Edge edge0 = EasyMock.mock(Edge.class);
        Edge edge1 = EasyMock.mock(Edge.class);
        Edge edge2 = EasyMock.mock(Edge.class);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        
        ArrayList<Edge> arrEdge = new ArrayList<>();
        arrEdge.add(edge0);
        arrEdge.add(edge1);
        arrEdge.add(edge2);
        EasyMock.expect(gameMap.getIntersection(0,0)).andReturn(mockedIntersection);
        EasyMock.expect(gameMap.getAllEdgesFromIntersection(mockedIntersection)).andReturn(arrEdge);
        EasyMock.expect(edge0.getRoadColor()).andReturn(PlayerColor.NONE);

        ArrayList<Intersection> dir0EdgeIntersections = new ArrayList<>();
        dir0EdgeIntersections.add(mockedIntersection);
        Intersection dir0Intersection = EasyMock.mock(Intersection.class);
        dir0EdgeIntersections.add(dir0Intersection);
        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge0)).andReturn(dir0EdgeIntersections);
        EasyMock.expect(dir0Intersection.getBuildingColor()).andReturn(PlayerColor.ORANGE);

        EasyMock.expect(edge1.getRoadColor()).andStubReturn(PlayerColor.BLUE);
        EasyMock.expect(edge2.getRoadColor()).andStubReturn(PlayerColor.RED);


        EasyMock.replay(gameMap,mockedIntersection,edge0,edge1,edge2,dir0Intersection,longestRoad);
        Intersection intersection = gameMap.getIntersection(0,0);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
        try {
            propertyPlacer.placeRegularSettlementOnMap(player,gameMap,intersection);
            fail("Settlement cannot be place adjacent to another settlement.");
        }catch (PlaceBuildingException e){
            //pass
        }

        EasyMock.verify(gameMap,mockedIntersection,edge0,edge1,edge2,dir0Intersection,longestRoad);
    }

    @Test
    public void TestPlaceSettlementOnMapSuccessful(){
        Player player = new Player(PlayerColor.BLUE);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        Intersection notMockedIntersection = new Intersection();
        Edge edge0 = EasyMock.mock(Edge.class);
        Edge edge1 = EasyMock.mock(Edge.class);
        Edge edge2 = EasyMock.mock(Edge.class);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);

        ArrayList<Edge> arrEdge = new ArrayList<>();
        arrEdge.add(edge0);
        arrEdge.add(edge1);
        arrEdge.add(edge2);
        EasyMock.expect(gameMap.getIntersection(0,0)).andReturn(notMockedIntersection);
        EasyMock.expect(gameMap.getAllEdgesFromIntersection(notMockedIntersection)).andReturn(arrEdge);
        EasyMock.expect(edge0.getRoadColor()).andStubReturn(PlayerColor.NONE);

        ArrayList<Intersection> noAdjacentIntersections = new ArrayList<>();
        noAdjacentIntersections.add(notMockedIntersection);
        Intersection dir0Intersection = EasyMock.mock(Intersection.class);
        noAdjacentIntersections.add(dir0Intersection);
        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge0)).andReturn(noAdjacentIntersections);
        EasyMock.expect(dir0Intersection.getBuildingColor()).andReturn(PlayerColor.NONE);

        EasyMock.expect(edge1.getRoadColor()).andStubReturn(PlayerColor.BLUE);
        noAdjacentIntersections = new ArrayList<>();
        noAdjacentIntersections.add(notMockedIntersection);
        Intersection dir1Intersection = EasyMock.mock(Intersection.class);
        noAdjacentIntersections.add(dir1Intersection);
        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge1)).andReturn(noAdjacentIntersections);
        EasyMock.expect(dir1Intersection.getBuildingColor()).andReturn(PlayerColor.NONE);

        EasyMock.expect(edge2.getRoadColor()).andStubReturn(PlayerColor.RED);
        noAdjacentIntersections = new ArrayList<>();
        noAdjacentIntersections.add(notMockedIntersection);
        Intersection dir2Intersection = EasyMock.mock(Intersection.class);
        noAdjacentIntersections.add(dir2Intersection);
        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge2)).andReturn(noAdjacentIntersections);
        EasyMock.expect(dir2Intersection.getBuildingColor()).andReturn(PlayerColor.NONE);

        longestRoad.updateLongestRoad(gameMap);
        
        EasyMock.replay(gameMap,edge0,edge1,edge2,dir0Intersection,dir1Intersection,dir2Intersection,longestRoad);
        Intersection intersection = gameMap.getIntersection(0,0);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);



        propertyPlacer.placeRegularSettlementOnMap(player,gameMap,intersection);


        EasyMock.verify(gameMap,edge0,edge1,edge2,dir0Intersection,dir1Intersection,dir2Intersection,longestRoad);

        assertEquals(intersection.getBuildingColor(),PlayerColor.BLUE);
        assertEquals(4, player.getSettlementCount());
    }

    @Test
    public void TestPlaceInitialSettlementAdjacentToAnotherSettlement(){
        Player player = new Player(PlayerColor.BLUE);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        Intersection mockedIntersection = EasyMock.mock(Intersection.class);
        Edge edge0 = EasyMock.mock(Edge.class);
        Edge edge1 = EasyMock.mock(Edge.class);
        Edge edge2 = EasyMock.mock(Edge.class);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);

        ArrayList<Edge> arrEdge = new ArrayList<>();
        arrEdge.add(edge0);
        arrEdge.add(edge1);
        arrEdge.add(edge2);
        EasyMock.expect(gameMap.getIntersection(0,0)).andReturn(mockedIntersection);
        EasyMock.expect(gameMap.getAllEdgesFromIntersection(mockedIntersection)).andReturn(arrEdge);

        ArrayList<Intersection> dir0EdgeIntersections = new ArrayList<>();
        dir0EdgeIntersections.add(mockedIntersection);
        Intersection dir0Intersection = EasyMock.mock(Intersection.class);
        dir0EdgeIntersections.add(dir0Intersection);
        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge0)).andReturn(dir0EdgeIntersections);
        EasyMock.expect(dir0Intersection.getBuildingColor()).andReturn(PlayerColor.ORANGE);

        EasyMock.expect(edge1.getRoadColor()).andStubReturn(PlayerColor.BLUE);
        EasyMock.expect(edge2.getRoadColor()).andStubReturn(PlayerColor.RED);


        EasyMock.replay(gameMap,mockedIntersection,edge0,edge1,edge2,dir0Intersection,longestRoad);
        Intersection intersection = gameMap.getIntersection(0,0);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
        try {
            propertyPlacer.placeInitialSettlement(player,gameMap,intersection);
            fail("Settlement cannot be place adjacent to another settlement.");
        }catch (PlaceBuildingException e){
            //pass
        }

        EasyMock.verify(gameMap,mockedIntersection,edge0,edge1,edge2,dir0Intersection,longestRoad);
    }

    @Test
    public void TestPlaceInitialSettlementNormalFlow(){
        Player player = new Player(PlayerColor.BLUE);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        Intersection notMockedIntersection = new Intersection();
        Edge edge0 = EasyMock.mock(Edge.class);
        Edge edge1 = EasyMock.mock(Edge.class);
        Edge edge2 = EasyMock.mock(Edge.class);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);

        ArrayList<Edge> arrEdge = new ArrayList<>();
        arrEdge.add(edge0);
        arrEdge.add(edge1);
        arrEdge.add(edge2);
        EasyMock.expect(gameMap.getIntersection(0,0)).andReturn(notMockedIntersection);
        EasyMock.expect(gameMap.getAllEdgesFromIntersection(notMockedIntersection)).andReturn(arrEdge);
        EasyMock.expect(edge0.getRoadColor()).andStubReturn(PlayerColor.NONE);

        ArrayList<Intersection> noAdjacentIntersections = new ArrayList<>();
        noAdjacentIntersections.add(notMockedIntersection);
        Intersection dir0Intersection = EasyMock.mock(Intersection.class);
        noAdjacentIntersections.add(dir0Intersection);
        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge0)).andReturn(noAdjacentIntersections);
        EasyMock.expect(dir0Intersection.getBuildingColor()).andReturn(PlayerColor.NONE);

        EasyMock.expect(edge1.getRoadColor()).andStubReturn(PlayerColor.BLUE);
        noAdjacentIntersections = new ArrayList<>();
        noAdjacentIntersections.add(notMockedIntersection);
        Intersection dir1Intersection = EasyMock.mock(Intersection.class);
        noAdjacentIntersections.add(dir1Intersection);
        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge1)).andReturn(noAdjacentIntersections);
        EasyMock.expect(dir1Intersection.getBuildingColor()).andReturn(PlayerColor.NONE);

        EasyMock.expect(edge2.getRoadColor()).andStubReturn(PlayerColor.RED);
        noAdjacentIntersections = new ArrayList<>();
        noAdjacentIntersections.add(notMockedIntersection);
        Intersection dir2Intersection = EasyMock.mock(Intersection.class);
        noAdjacentIntersections.add(dir2Intersection);
        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge2)).andReturn(noAdjacentIntersections);
        EasyMock.expect(dir2Intersection.getBuildingColor()).andReturn(PlayerColor.NONE);

        longestRoad.updateLongestRoad(gameMap);

        EasyMock.replay(gameMap,edge0,edge1,edge2,dir0Intersection,dir1Intersection,dir2Intersection,longestRoad);
        Intersection intersection = gameMap.getIntersection(0,0);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);



        propertyPlacer.placeInitialSettlement(player,gameMap,intersection);


        EasyMock.verify(gameMap,edge0,edge1,edge2,dir0Intersection,dir1Intersection,dir2Intersection,longestRoad);

        assertEquals(intersection.getBuildingColor(),PlayerColor.BLUE);
        assertEquals(4, player.getSettlementCount());
    }

    @Test
    public void TestPlaceInitialSettlementRound2(){

        Player player = new Player(PlayerColor.BLUE);
        GameMap gameMap = EasyMock.mock(GameMap.class);
        Intersection notMockedIntersection = new Intersection();
        Edge edge0 = EasyMock.mock(Edge.class);
        Edge edge1 = EasyMock.mock(Edge.class);
        Edge edge2 = EasyMock.mock(Edge.class);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);

        ArrayList<Edge> arrEdge = new ArrayList<>();
        arrEdge.add(edge0);
        arrEdge.add(edge1);
        arrEdge.add(edge2);
        EasyMock.expect(gameMap.getIntersection(0,0)).andReturn(notMockedIntersection);
        EasyMock.expect(gameMap.getAllEdgesFromIntersection(notMockedIntersection)).andReturn(arrEdge);
        EasyMock.expect(edge0.getRoadColor()).andStubReturn(PlayerColor.NONE);

        ArrayList<Intersection> noAdjacentIntersections = new ArrayList<>();
        noAdjacentIntersections.add(notMockedIntersection);
        Intersection dir0Intersection = EasyMock.mock(Intersection.class);
        noAdjacentIntersections.add(dir0Intersection);
        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge0)).andReturn(noAdjacentIntersections);
        EasyMock.expect(dir0Intersection.getBuildingColor()).andReturn(PlayerColor.NONE);

        EasyMock.expect(edge1.getRoadColor()).andStubReturn(PlayerColor.BLUE);
        noAdjacentIntersections = new ArrayList<>();
        noAdjacentIntersections.add(notMockedIntersection);
        Intersection dir1Intersection = EasyMock.mock(Intersection.class);
        noAdjacentIntersections.add(dir1Intersection);
        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge1)).andReturn(noAdjacentIntersections);
        EasyMock.expect(dir1Intersection.getBuildingColor()).andReturn(PlayerColor.NONE);

        EasyMock.expect(edge2.getRoadColor()).andStubReturn(PlayerColor.RED);
        noAdjacentIntersections = new ArrayList<>();
        noAdjacentIntersections.add(notMockedIntersection);
        Intersection dir2Intersection = EasyMock.mock(Intersection.class);
        noAdjacentIntersections.add(dir2Intersection);
        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge2)).andReturn(noAdjacentIntersections);
        EasyMock.expect(dir2Intersection.getBuildingColor()).andReturn(PlayerColor.NONE);

        ArrayList<Hex> mockedHexes = new ArrayList<>();
        mockedHexes.add(new Hex(Resource.BRICK,3));
        mockedHexes.add(new Hex(Resource.WOOL,2));
        mockedHexes.add(new Hex());

        longestRoad.updateLongestRoad(gameMap);

        EasyMock.expect(gameMap.getAllHexesFromIntersection(notMockedIntersection)).andStubReturn(mockedHexes);

        EasyMock.replay(gameMap,edge0,edge1,edge2,dir0Intersection,dir1Intersection,dir2Intersection,longestRoad);
        Intersection intersection = gameMap.getIntersection(0,0);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);



        propertyPlacer.placeInitialSettlementRound2(player,gameMap,intersection);

        assertEquals(1,player.getResourceCount(Resource.BRICK));
        assertEquals(1,player.getResourceCount(Resource.WOOL));


        EasyMock.verify(gameMap,edge0,edge1,edge2,dir0Intersection,dir1Intersection,dir2Intersection,longestRoad);


    }
    
    public Player mockedPlayer(PlayerColor color, int numSettlements) {
        Player player = EasyMock.mock(Player.class);
        EasyMock.expect(player.getColor()).andStubReturn(color);
        EasyMock.expect(player.getSettlementCount()).andStubReturn(numSettlements);
        return player;
    }
    
    private Intersection mockedIntersection(PlayerColor color) {
        Intersection intersection = EasyMock.mock(Intersection.class);
        EasyMock.expect(intersection.getBuildingColor()).andStubReturn(color);
        return intersection;
    }
}