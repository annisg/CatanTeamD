package model;

import exception.PlaceBuildingException;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Player_PropertyPlacer_Integration_Tests {
    @Test
    void testVerifyIfEdgeIsConnectedValid() {
        PropertyPlacer propertyPlacer = new PropertyPlacer(null, ResourceBundle.getBundle("messages"));
        Player blue = new Player(PlayerColor.BLUE);
        ArrayList<Edge> edges = new ArrayList<>();

        Edge edge1 = new Edge();
        edge1.setRoad(PlayerColor.BLUE);
        edges.add(edge1);

        propertyPlacer.verifyIntersectionIsConnected(blue, edges);

        // should not throw error
    }

    @Test
    void testVerifyIfEdgeIsConnectedOnlyOtherPlayers() {
        PropertyPlacer propertyPlacer = new PropertyPlacer(null, ResourceBundle.getBundle("messages"));
        Player blue = new Player(PlayerColor.BLUE);
        ArrayList<Edge> edges = new ArrayList<>();

        Edge edge1 = new Edge();
        edge1.setRoad(PlayerColor.RED);
        edges.add(edge1);

        try {
            propertyPlacer.verifyIntersectionIsConnected(blue, edges);
            fail("Expected verifyIntersectionIsConnected to throw PlaceBuildingException");

        } catch (PlaceBuildingException e) {
            assertEquals("A settlement cannot be place because of no connected road exists.", e.getMessage());
        }
    }

    @Test
    void testVerifyIfEdgeIsConnectedOnlyNoPlayers() {
        PropertyPlacer propertyPlacer = new PropertyPlacer(null, ResourceBundle.getBundle("messages"));
        Player blue = new Player(PlayerColor.BLUE);
        ArrayList<Edge> edges = new ArrayList<>();

        Edge edge1 = new Edge();
        edges.add(edge1);

        try {
            propertyPlacer.verifyIntersectionIsConnected(blue, edges);
            fail("Expected verifyIntersectionIsConnected to throw PlaceBuildingException");
        } catch (PlaceBuildingException e) {
            assertEquals("A settlement cannot be place because of no connected road exists.", e.getMessage());
        }
    }

    @Test
    void testPlaceRoadOnMapValid() {
        LongestRoad longestRoad = EasyMock.niceMock(LongestRoad.class);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, ResourceBundle.getBundle("messages"));
        Player blue = new Player(PlayerColor.BLUE);
        Edge edge = new Edge();
        GameMap gameMap = EasyMock.strictMock(GameMap.class);

        ArrayList<Intersection> intersections = new ArrayList<>();
        Intersection intersection = new Intersection();
        intersection.setSettlement(PlayerColor.BLUE);
        intersections.add(intersection);

        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge)).andReturn(intersections);
        EasyMock.replay(gameMap);

        propertyPlacer.placeRoadOnMap(blue, gameMap, edge);


        assertEquals(14, blue.getRoadCount());
        EasyMock.verify(gameMap);
    }

    @Test
    void testPlaceRoadOnMapNoIntersections() {
        LongestRoad longestRoad = EasyMock.niceMock(LongestRoad.class);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, ResourceBundle.getBundle("messages"));
        Player blue = new Player(PlayerColor.BLUE);
        Edge edge = new Edge();
        GameMap gameMap = EasyMock.strictMock(GameMap.class);

        ArrayList<Intersection> intersections = new ArrayList<>();

        EasyMock.expect(gameMap.getAllIntersectionsFromEdge(edge)).andReturn(intersections);
        EasyMock.replay(gameMap);

        try {
            propertyPlacer.placeRoadOnMap(blue, gameMap, edge);
            fail("Expected placeRoadOnMap to throw PlaceBuildingException");
        } catch (PlaceBuildingException e) {
            assertEquals("A road cannot be placed because the edge is not connected.", e.getMessage());
        }

        assertEquals(15, blue.getRoadCount());  // does not remove road
        EasyMock.verify(gameMap);
    }


    @Test
    void testPlaceRoadOnMapEdgeNotEmpty() {
        LongestRoad longestRoad = EasyMock.niceMock(LongestRoad.class);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, ResourceBundle.getBundle("messages"));
        Player blue = new Player(PlayerColor.BLUE);
        Edge edge = new Edge();
        edge.setRoad(PlayerColor.RED);
        GameMap gameMap = EasyMock.strictMock(GameMap.class);

        try {
            propertyPlacer.placeRoadOnMap(blue, gameMap, edge);
            fail("Expected placeRoadOnMap to throw PlaceBuildingException");
        } catch (PlaceBuildingException e) {
            assertEquals("A road cannot be placed because the edge is not empty.", e.getMessage());
        }

        assertEquals(15, blue.getRoadCount());  // does not remove road
    }
}
