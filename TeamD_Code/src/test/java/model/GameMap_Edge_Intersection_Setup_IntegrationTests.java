package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exception.*;

public class GameMap_Edge_Intersection_Setup_IntegrationTests {

    @Test
    public void testEdgeSetupExists() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        assertFalse(gmTest.getEdgeMap()==null);
        assertFalse(gmTest.getIntersectionMap()==null);
    }

    @Test
    public void testGetLegalIntersections() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        assertEquals(Intersection.class, gmTest.getIntersection(0, 0).getClass());
        assertEquals(Intersection.class, gmTest.getIntersection(11, 2).getClass());
        assertEquals(Intersection.class, gmTest.getIntersection(10, 3).getClass());
        assertEquals(Intersection.class, gmTest.getIntersection(9, 3).getClass());
        assertEquals(Intersection.class, gmTest.getIntersection(8, 4).getClass());
        assertEquals(Intersection.class, gmTest.getIntersection(7, 4).getClass());
        assertEquals(Intersection.class, gmTest.getIntersection(6, 5).getClass());
        assertEquals(Intersection.class, gmTest.getIntersection(5, 5).getClass());
        assertEquals(Intersection.class, gmTest.getIntersection(4, 4).getClass());
        assertEquals(Intersection.class, gmTest.getIntersection(3, 4).getClass());
        assertEquals(Intersection.class, gmTest.getIntersection(2, 3).getClass());
        assertEquals(Intersection.class, gmTest.getIntersection(1, 3).getClass());
        assertEquals(Intersection.class, gmTest.getIntersection(0, 2).getClass());

    }

    @Test
    public void testGetIllegalIntersections() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        getIntersectionOutOfBounds(gmTest, -1, 0);
        getIntersectionOutOfBounds(gmTest, 12, 0);
        getIntersectionOutOfBounds(gmTest, 0, -1);
        getIntersectionOutOfBounds(gmTest, 0, 3);
        getIntersectionOutOfBounds(gmTest, 1, -1);
        getIntersectionOutOfBounds(gmTest, 1, 4);
        getIntersectionOutOfBounds(gmTest, 2, -1);
        getIntersectionOutOfBounds(gmTest, 2, 4);
        getIntersectionOutOfBounds(gmTest, 3, -1);
        getIntersectionOutOfBounds(gmTest, 3, 5);
        getIntersectionOutOfBounds(gmTest, 4, -1);
        getIntersectionOutOfBounds(gmTest, 4, 5);
        getIntersectionOutOfBounds(gmTest, 5, -1);
        getIntersectionOutOfBounds(gmTest, 5, 6);
        getIntersectionOutOfBounds(gmTest, 6, -1);
        getIntersectionOutOfBounds(gmTest, 6, 6);
        getIntersectionOutOfBounds(gmTest, 7, -1);
        getIntersectionOutOfBounds(gmTest, 7, 5);
        getIntersectionOutOfBounds(gmTest, 8, -1);
        getIntersectionOutOfBounds(gmTest, 8, 5);
        getIntersectionOutOfBounds(gmTest, 9, -1);
        getIntersectionOutOfBounds(gmTest, 9, 4);
        getIntersectionOutOfBounds(gmTest, 10, -1);
        getIntersectionOutOfBounds(gmTest, 10, 4);
        getIntersectionOutOfBounds(gmTest, 11, -1);
        getIntersectionOutOfBounds(gmTest, 11, 3);
    }

    public void getIntersectionOutOfBounds(GameMap map, int row, int col) {
        try {
            map.getIntersection(row, col);
            fail("Expected IAE");
        } catch (InvalidIntersectionPositionException e) {
        }
    }

    @Test
    public void testGetLegalEdges() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        assertEquals(Edge.class, gmTest.getEdge(0, 0).getClass());
        assertEquals(Edge.class, gmTest.getEdge(0, 5).getClass());
        assertEquals(Edge.class, gmTest.getEdge(1, 0).getClass());
        assertEquals(Edge.class, gmTest.getEdge(1, 3).getClass());
        assertEquals(Edge.class, gmTest.getEdge(2, 0).getClass());
        assertEquals(Edge.class, gmTest.getEdge(2, 7).getClass());
        assertEquals(Edge.class, gmTest.getEdge(3, 0).getClass());
        assertEquals(Edge.class, gmTest.getEdge(3, 4).getClass());
        assertEquals(Edge.class, gmTest.getEdge(4, 0).getClass());
        assertEquals(Edge.class, gmTest.getEdge(4, 9).getClass());
        assertEquals(Edge.class, gmTest.getEdge(5, 0).getClass());
        assertEquals(Edge.class, gmTest.getEdge(5, 5).getClass());
        assertEquals(Edge.class, gmTest.getEdge(6, 0).getClass());
        assertEquals(Edge.class, gmTest.getEdge(6, 9).getClass());
        assertEquals(Edge.class, gmTest.getEdge(7, 0).getClass());
        assertEquals(Edge.class, gmTest.getEdge(7, 4).getClass());
        assertEquals(Edge.class, gmTest.getEdge(8, 0).getClass());
        assertEquals(Edge.class, gmTest.getEdge(8, 7).getClass());
        assertEquals(Edge.class, gmTest.getEdge(9, 0).getClass());
        assertEquals(Edge.class, gmTest.getEdge(9, 3).getClass());
        assertEquals(Edge.class, gmTest.getEdge(10, 0).getClass());
        assertEquals(Edge.class, gmTest.getEdge(10, 5).getClass());
    }

    @Test
    public void testGetIllegalEdges() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        getEdgeOutOfBounds(gmTest, -1, 0);
        getEdgeOutOfBounds(gmTest, 11, 0);
        getEdgeOutOfBounds(gmTest, 0, -1);
        getEdgeOutOfBounds(gmTest, 0, 6);
        getEdgeOutOfBounds(gmTest, 1, -1);
        getEdgeOutOfBounds(gmTest, 1, 4);
        getEdgeOutOfBounds(gmTest, 2, -1);
        getEdgeOutOfBounds(gmTest, 2, 8);
        getEdgeOutOfBounds(gmTest, 3, -1);
        getEdgeOutOfBounds(gmTest, 3, 5);
        getEdgeOutOfBounds(gmTest, 4, -1);
        getEdgeOutOfBounds(gmTest, 4, 10);
        getEdgeOutOfBounds(gmTest, 5, -1);
        getEdgeOutOfBounds(gmTest, 5, 6);
        getEdgeOutOfBounds(gmTest, 6, -1);
        getEdgeOutOfBounds(gmTest, 6, 10);
        getEdgeOutOfBounds(gmTest, 7, -1);
        getEdgeOutOfBounds(gmTest, 7, 5);
        getEdgeOutOfBounds(gmTest, 8, -1);
        getEdgeOutOfBounds(gmTest, 8, 8);
        getEdgeOutOfBounds(gmTest, 9, -1);
        getEdgeOutOfBounds(gmTest, 9, 4);
        getEdgeOutOfBounds(gmTest, 10, -1);
        getEdgeOutOfBounds(gmTest, 10, 6);
    }

    public void getEdgeOutOfBounds(GameMap map, int row, int col) {
        try {
            map.getEdge(row, col);
            fail("Expected IAE");
        } catch (InvalidEdgePositionException e) {
        }
    }
    
    @Test
    public void test3PlayerBeginnerRoadAndSettlementPlacement() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        
        verifyBeginnerWhitePlacement(gmTest);
        verifyBeginnerBluePlacement(gmTest);
        verifyBeginnerOrangePlacement(gmTest);
        verifyNoRedPlacement(gmTest);
        
    }
    
    @Test
    public void test4PlayerBeginnerRoadAndSettlementPlacement() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(4);
        
        verifyBeginnerWhitePlacement(gmTest);
        verifyBeginnerBluePlacement(gmTest);
        verifyBeginnerOrangePlacement(gmTest);
        verifyBeginnerRedPlacement(gmTest);
        
    }
    
    @Test
    public void testBeginnerSetupInvalidPlayerNumber() {
        
        GameMap gmTest = new GameMap();
        
        int[] illegalInputs = {Integer.MIN_VALUE, -1, 0, 2, 5, Integer.MAX_VALUE };
        
        for(int i = 0; i < illegalInputs.length; i++ ) {
            
            try {
                gmTest.setUpBeginnerMap(illegalInputs[i]);
                fail("Expected IAE");
            } catch (IllegalArgumentException e) {
                
            }
            
        }
        
    }

    private void verifyBeginnerOrangePlacement(GameMap gmTest) {
        testHasSettlementAt(gmTest, 3, 2, PlayerColor.ORANGE);
        testHasSettlementAt(gmTest, 8, 3, PlayerColor.ORANGE);
        testHasRoadAt(gmTest, 2, 4, PlayerColor.ORANGE);
        testHasRoadAt(gmTest, 8, 5, PlayerColor.ORANGE);
    }

    private void verifyBeginnerBluePlacement(GameMap gmTest) {
        testHasSettlementAt(gmTest, 3, 1, PlayerColor.BLUE);
        testHasSettlementAt(gmTest, 3, 3, PlayerColor.BLUE);
        testHasRoadAt(gmTest, 2, 2, PlayerColor.BLUE);
        testHasRoadAt(gmTest, 3, 3, PlayerColor.BLUE);
    }

    private void verifyBeginnerWhitePlacement(GameMap gmTest) {
        testHasSettlementAt(gmTest, 7, 1, PlayerColor.WHITE);
        testHasSettlementAt(gmTest, 5, 4, PlayerColor.WHITE);
        testHasRoadAt(gmTest, 6, 2, PlayerColor.WHITE);
        testHasRoadAt(gmTest, 5, 4, PlayerColor.WHITE);
    }
    
    private void verifyBeginnerRedPlacement(GameMap gmTest) {
        testHasSettlementAt(gmTest, 5, 1, PlayerColor.RED);
        testHasSettlementAt(gmTest, 9, 1, PlayerColor.RED);
        testHasRoadAt(gmTest, 4, 2, PlayerColor.RED);
        testHasRoadAt(gmTest, 8, 3, PlayerColor.RED);
    }
    
    private void verifyNoRedPlacement(GameMap gmTest) {
        
        assertEquals(false, gmTest.getIntersection(5, 1).hasSettlement());
        assertEquals(false, gmTest.getIntersection(9, 1).hasSettlement());
        assertEquals(false, gmTest.getEdge(4, 2).hasRoad());
        assertEquals(false, gmTest.getEdge(8, 3).hasRoad());

    }
    
    private void testHasSettlementAt(GameMap map, int x, int y, PlayerColor expectedColor) {
        Intersection intersection = map.getIntersection(x, y);
        
        assertEquals(true, intersection.hasSettlement());
        assertEquals(expectedColor, intersection.getBuildingColor());
    }
    
    private void testHasRoadAt(GameMap map, int x, int y, PlayerColor expectedColor) {
        Edge edge = map.getEdge(x, y);
        
        assertEquals(true, edge.hasRoad());
        assertEquals(expectedColor, edge.getRoadColor());
    }

}
