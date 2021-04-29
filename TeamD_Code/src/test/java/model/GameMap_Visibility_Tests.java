package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class GameMap_Visibility_Tests {

    @Test
    public void testEmptyEdgeVisible() {
        GameMap map = EasyMock.partialMockBuilder(GameMap.class)
                .addMockedMethod("getAllIntersectionsFromEdge", Edge.class)
                .addMockedMethod("getAllEdgesFromIntersection", Intersection.class)
                .mock();
        Edge testEdge = EasyMock.mock(Edge.class);
        EasyMock.expect(testEdge.getRoadColor()).andReturn(PlayerColor.NONE);
        
        EasyMock.replay(map, testEdge);
        assertTrue(map.canSeeEdge(testEdge, PlayerColor.BLUE));
        EasyMock.verify(map, testEdge);
    }
    
    @Test
    public void testInvisibleEdge() {
        GameMap map = EasyMock.partialMockBuilder(GameMap.class)
                .addMockedMethod("getAllIntersectionsFromEdge", Edge.class)
                .addMockedMethod("getAllEdgesFromIntersection", Intersection.class)
                .mock();
        Edge testEdge = EasyMock.mock(Edge.class);
        EasyMock.expect(testEdge.getRoadColor()).andStubReturn(PlayerColor.RED);
        
        ArrayList<Intersection> adjacentIntersections = new ArrayList<Intersection>();
        ArrayList<Edge> adjacentEdges = new ArrayList<Edge>();
        
        EasyMock.expect(map.getAllIntersectionsFromEdge(testEdge)).andReturn(adjacentIntersections);
        for(int i = 0; i < 3; i++) {
            Intersection intersection = EasyMock.mock(Intersection.class);
            adjacentIntersections.add(intersection);
            EasyMock.expect(intersection.getBuildingColor()).andReturn(PlayerColor.NONE);
            EasyMock.expect(map.getAllEdgesFromIntersection(intersection)).andReturn(adjacentEdges);
            
            Edge edge = EasyMock.mock(Edge.class);
            adjacentEdges.add(edge);
            EasyMock.expect(edge.getRoadColor()).andStubReturn(PlayerColor.NONE);
            EasyMock.replay(intersection, edge);
        }
        
        EasyMock.replay(map, testEdge);
        assertFalse(map.canSeeEdge(testEdge, PlayerColor.BLUE));
        EasyMock.verify(map, testEdge);
        EasyMock.verify(adjacentIntersections.toArray());
        EasyMock.verify(adjacentEdges.toArray());
    }
    
    @Test
    public void testVisibleEdgeOwned() {
        GameMap map = EasyMock.partialMockBuilder(GameMap.class)
                .addMockedMethod("getAllIntersectionsFromEdge", Edge.class)
                .addMockedMethod("getAllEdgesFromIntersection", Intersection.class)
                .mock();
        Edge testEdge = EasyMock.mock(Edge.class);
        EasyMock.expect(testEdge.getRoadColor()).andStubReturn(PlayerColor.ORANGE);

        
        EasyMock.replay(map, testEdge);
        assertTrue(map.canSeeEdge(testEdge, PlayerColor.ORANGE));
        EasyMock.verify(map, testEdge);
    }
    
    @Test
    public void testVisibleEdgeOtherPlayer() {
        GameMap map = EasyMock.partialMockBuilder(GameMap.class)
                .addMockedMethod("getAllIntersectionsFromEdge", Edge.class)
                .addMockedMethod("getAllEdgesFromIntersection", Intersection.class)
                .mock();
        Edge testEdge = EasyMock.mock(Edge.class);
        EasyMock.expect(testEdge.getRoadColor()).andStubReturn(PlayerColor.WHITE);
        
        ArrayList<Intersection> adjacentIntersections = new ArrayList<Intersection>();
        ArrayList<Edge> adjacentEdges = new ArrayList<Edge>();
        
        EasyMock.expect(map.getAllIntersectionsFromEdge(testEdge)).andReturn(adjacentIntersections);
        for(int i = 0; i < 3; i++) {
            Intersection intersection = EasyMock.mock(Intersection.class);
            adjacentIntersections.add(intersection);
            EasyMock.expect(intersection.getBuildingColor()).andStubReturn(PlayerColor.NONE);
            EasyMock.expect(map.getAllEdgesFromIntersection(intersection)).andStubReturn(adjacentEdges);
            
            Edge edge = EasyMock.mock(Edge.class);
            adjacentEdges.add(edge);
            EasyMock.expect(edge.getRoadColor()).andStubReturn(PlayerColor.RED);
            EasyMock.replay(intersection, edge);
        }
        
        EasyMock.replay(map, testEdge);
        assertTrue(map.canSeeEdge(testEdge, PlayerColor.RED));
        EasyMock.verify(map, testEdge);
        EasyMock.verify(adjacentIntersections.toArray());
        EasyMock.verify(adjacentEdges.toArray());
    }
    
    @Test
    public void testEmptyIntersectionVisible() {
        GameMap map = EasyMock.partialMockBuilder(GameMap.class)
                .addMockedMethod("getAllIntersectionsFromEdge", Edge.class)
                .addMockedMethod("getAllEdgesFromIntersection", Intersection.class)
                .mock();
        Intersection testIntersection = EasyMock.mock(Intersection.class);
        EasyMock.expect(testIntersection.getBuildingColor()).andReturn(PlayerColor.NONE);
        
        EasyMock.replay(map, testIntersection);
        assertTrue(map.canSeeIntersection(testIntersection, PlayerColor.RED));
        EasyMock.verify(map, testIntersection);
    }
    
    @Test
    public void testVisibleIntersectionOwned() {
        GameMap map = EasyMock.partialMockBuilder(GameMap.class)
                .addMockedMethod("getAllIntersectionsFromEdge", Edge.class)
                .addMockedMethod("getAllEdgesFromIntersection", Intersection.class)
                .mock();
        Intersection testIntersection = EasyMock.mock(Intersection.class);
        EasyMock.expect(testIntersection.getBuildingColor()).andStubReturn(PlayerColor.WHITE);

        
        EasyMock.replay(map, testIntersection);
        assertTrue(map.canSeeIntersection(testIntersection, PlayerColor.WHITE));
        EasyMock.verify(map, testIntersection);
    }
    
    @Test
    public void testVisibleIntersectionOtherPlayer() {
        GameMap map = EasyMock.partialMockBuilder(GameMap.class)
                .addMockedMethod("getAllIntersectionsFromEdge", Edge.class)
                .addMockedMethod("getAllEdgesFromIntersection", Intersection.class)
                .mock();
        Intersection testIntersection = EasyMock.mock(Intersection.class);
        EasyMock.expect(testIntersection.getBuildingColor()).andStubReturn(PlayerColor.ORANGE);
        
        ArrayList<Edge> adjacentEdges = new ArrayList<Edge>();
        
        EasyMock.expect(map.getAllEdgesFromIntersection(testIntersection)).andReturn(adjacentEdges);
        for(int i = 0; i < 3; i++) {
            Edge edge = EasyMock.mock(Edge.class);
            adjacentEdges.add(edge);
            EasyMock.expect(edge.getRoadColor()).andStubReturn(PlayerColor.BLUE);
            EasyMock.replay(edge);
        }
        
        EasyMock.replay(map, testIntersection);
        assertTrue(map.canSeeIntersection(testIntersection, PlayerColor.BLUE));
        EasyMock.verify(map, testIntersection);
        EasyMock.verify(adjacentEdges.toArray());
    }

}
