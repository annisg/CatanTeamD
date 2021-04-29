package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import exception.*;

public class EdgeMap_Tests {

    @Test
    public void testEdgeMapSize() {
        EdgeMap testEdgeMap = new EdgeMap();
        assertEquals(11, testEdgeMap.getNumberOfRows());
        assertEquals(6, testEdgeMap.getNumberOfEdgesInRow(0));
        assertEquals(4, testEdgeMap.getNumberOfEdgesInRow(1));
        assertEquals(8, testEdgeMap.getNumberOfEdgesInRow(2));
        assertEquals(5, testEdgeMap.getNumberOfEdgesInRow(3));
        assertEquals(10, testEdgeMap.getNumberOfEdgesInRow(4));
        assertEquals(6, testEdgeMap.getNumberOfEdgesInRow(5));
        assertEquals(10, testEdgeMap.getNumberOfEdgesInRow(6));
        assertEquals(5, testEdgeMap.getNumberOfEdgesInRow(7));
        assertEquals(8, testEdgeMap.getNumberOfEdgesInRow(8));
        assertEquals(4, testEdgeMap.getNumberOfEdgesInRow(9));
        assertEquals(6, testEdgeMap.getNumberOfEdgesInRow(10));
    }

    @Test
    public void testGetNumberOfEdgesInBadRow() {
        EdgeMap testEdgeMap = new EdgeMap();
        try {
            testEdgeMap.getNumberOfEdgesInRow(11);
            fail("Failed to throw illegal edge location exception");
        } catch (InvalidEdgePositionException e) {
            // passes
        }

        try {
            testEdgeMap.getNumberOfEdgesInRow(-1);
            fail("Failed to throw illegal edge location exception");
        } catch (InvalidEdgePositionException e) {
            // passes
        }
    }

    @Test
    public void testEdgeMapHasAllEmptyEdges() {
        EdgeMap testEdgeMap = new EdgeMap();
        for (int i = 0; i < testEdgeMap.getNumberOfRows(); i++) {
            for (int j = 0; j < testEdgeMap.getNumberOfEdgesInRow(i); j++) {
                Edge testEdge = testEdgeMap.getEdge(new MapPosition(i, j));
                assertEquals(PlayerColor.NONE, testEdge.getRoadColor());
                assertFalse(testEdge.hasRoad());
            }
        }
    }

    @Test
    public void testGetEdgeBadIndexes() {
        EdgeMap testEdgeMap = new EdgeMap();
        try {
            testEdgeMap.getEdge(new MapPosition(-1, 0));
            fail("Failed to throw illegal edge location exception");
        } catch (InvalidEdgePositionException e) {
        } // passes
        for (int i = 0; i < testEdgeMap.getNumberOfRows(); i++) {
            try {
                testEdgeMap.getEdge(new MapPosition(i, -1));
                fail("Failed to throw illegal edge location exception");
            } catch (InvalidEdgePositionException e) {
            } // passes

            try {
                testEdgeMap.getEdge(new MapPosition(i, testEdgeMap.getNumberOfEdgesInRow(i)));
                fail("Failed to throw illegal edge location exception");
            } catch (InvalidEdgePositionException e) {
            } // passes
        }
        try {
            testEdgeMap.getEdge(new MapPosition(testEdgeMap.getNumberOfRows(), 0));
            fail("Failed to throw illegal edge location exception");
        } catch (InvalidEdgePositionException e) {
        } // passes
    }

    @Test
    public void testGetIndexesFromEdge() {
        EdgeMap testEdgeMap = new EdgeMap();
        Edge testEdge;
        MapPosition indexes;
        for (int i = 0; i < testEdgeMap.getNumberOfRows(); i++) {
            testEdge = testEdgeMap.getEdge(new MapPosition(i, 0));
            indexes = testEdgeMap.findEdgePosition(testEdge);
            assertEquals(i, indexes.getRow());
            assertEquals(0, indexes.getColumn());

            testEdge = testEdgeMap.getEdge(new MapPosition(i, testEdgeMap.getNumberOfEdgesInRow(i) - 1));
            indexes = testEdgeMap.findEdgePosition(testEdge);
            assertEquals(i, indexes.getRow());
            assertEquals(testEdgeMap.getNumberOfEdgesInRow(i) - 1, indexes.getColumn());
        }
    }

    @Test
    public void testGetPositionFromBadEdge() {
        EdgeMap testEdgeMap = new EdgeMap();

        try {
            testEdgeMap.findEdgePosition(new Edge());
            fail("failed to throw Invalid Edge Object Excpetion");
        } catch (InvalidEdgePositionException e) {
        } // passes
    }

    @Test
    public void testGetIntersectionIndexFromEdgePositionDirection0() {
        EdgeMap testEdgeMap = new EdgeMap();
        MapPosition intersectionPosition = testEdgeMap.getIntersectionDirection0(new MapPosition(0, 0));
        assertEquals(1, intersectionPosition.getRow());
        assertEquals(0, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection0(new MapPosition(5, 5));
        assertEquals(6, intersectionPosition.getRow());
        assertEquals(5, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection0(new MapPosition(2, 1));
        assertEquals(3, intersectionPosition.getRow());
        assertEquals(1, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection0(new MapPosition(8, 2));
        assertEquals(9, intersectionPosition.getRow());
        assertEquals(1, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection0(new MapPosition(10, 3));
        assertEquals(11, intersectionPosition.getRow());
        assertEquals(1, intersectionPosition.getColumn());
    }

    @Test
    public void testGetIntersectionIndexFromBadEdgePositionDirection0() {
        getIntersectionIndexFromBadEdgePositionDirection0(new MapPosition(-1, 0));
        getIntersectionIndexFromBadEdgePositionDirection0(new MapPosition(0, -1));
        getIntersectionIndexFromBadEdgePositionDirection0(new MapPosition(11, 0));
        getIntersectionIndexFromBadEdgePositionDirection0(new MapPosition(0, 6));
        getIntersectionIndexFromBadEdgePositionDirection0(new MapPosition(5, 6));
    }

    public void getIntersectionIndexFromBadEdgePositionDirection0(MapPosition pos) {
        EdgeMap testEdgeMap = new EdgeMap();
        try {
            MapPosition intersectionPosition = testEdgeMap.getIntersectionDirection0(pos);
            fail("Did not throw InvalidEdgePositionException");
        } catch (InvalidEdgePositionException e) {
        } // passes
    }

    @Test
    public void testGetIntersectionIndexFromEdgeObjectDirection0() {
        EdgeMap testEdgeMap = new EdgeMap();
        MapPosition intersectionPosition = testEdgeMap
                .getIntersectionDirection0(testEdgeMap.getEdge(new MapPosition(0, 0)));
        assertEquals(1, intersectionPosition.getRow());
        assertEquals(0, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection0(testEdgeMap.getEdge(new MapPosition(5, 5)));
        assertEquals(6, intersectionPosition.getRow());
        assertEquals(5, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection0(testEdgeMap.getEdge(new MapPosition(2, 1)));
        assertEquals(3, intersectionPosition.getRow());
        assertEquals(1, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection0(testEdgeMap.getEdge(new MapPosition(8, 2)));
        assertEquals(9, intersectionPosition.getRow());
        assertEquals(1, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection0(testEdgeMap.getEdge(new MapPosition(10, 3)));
        assertEquals(11, intersectionPosition.getRow());
        assertEquals(1, intersectionPosition.getColumn());
    }

    @Test
    public void testGetIntersectionIndexFromBadEdgeObjectDirection0() {
        EdgeMap testEdgeMap = new EdgeMap();
        try {
            MapPosition intersectionPosition = testEdgeMap.getIntersectionDirection0(new Edge());
            fail("Did not throw InvalidEdgePositionException");
        } catch (InvalidEdgePositionException e) {
        } // passes
    }

    @Test
    public void testGetIntersectionIndexFromEdgePositionDirection1() {
        EdgeMap testEdgeMap = new EdgeMap();
        MapPosition intersectionPosition = testEdgeMap.getIntersectionDirection1(new MapPosition(0, 0));
        assertEquals(0, intersectionPosition.getRow());
        assertEquals(0, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection1(new MapPosition(5, 5));
        assertEquals(5, intersectionPosition.getRow());
        assertEquals(5, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection1(new MapPosition(2, 1));
        assertEquals(2, intersectionPosition.getRow());
        assertEquals(0, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection1(new MapPosition(8, 2));
        assertEquals(8, intersectionPosition.getRow());
        assertEquals(1, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection1(new MapPosition(10, 3));
        assertEquals(10, intersectionPosition.getRow());
        assertEquals(2, intersectionPosition.getColumn());
    }

    @Test
    public void testGetIntersectionIndexFromBadEdgePositionDirection1() {
        getIntersectionIndexFromBadEdgePositionDirection1(new MapPosition(-1, 0));
        getIntersectionIndexFromBadEdgePositionDirection1(new MapPosition(0, -1));
        getIntersectionIndexFromBadEdgePositionDirection1(new MapPosition(11, 0));
        getIntersectionIndexFromBadEdgePositionDirection1(new MapPosition(0, 6));
        getIntersectionIndexFromBadEdgePositionDirection1(new MapPosition(5, 6));
    }

    public void getIntersectionIndexFromBadEdgePositionDirection1(MapPosition pos) {
        EdgeMap testEdgeMap = new EdgeMap();
        try {
            MapPosition intersectionPosition = testEdgeMap.getIntersectionDirection1(pos);
            fail("Did not throw InvalidEdgePositionException");
        } catch (InvalidEdgePositionException e) {
        } // passes
    }

    @Test
    public void testGetIntersectionIndexFromEdgeObjectDirection1() {
        EdgeMap testEdgeMap = new EdgeMap();
        MapPosition intersectionPosition = testEdgeMap
                .getIntersectionDirection1(testEdgeMap.getEdge(new MapPosition(0, 0)));
        assertEquals(0, intersectionPosition.getRow());
        assertEquals(0, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection1(testEdgeMap.getEdge(new MapPosition(5, 5)));
        assertEquals(5, intersectionPosition.getRow());
        assertEquals(5, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection1(testEdgeMap.getEdge(new MapPosition(2, 1)));
        assertEquals(2, intersectionPosition.getRow());
        assertEquals(0, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection1(testEdgeMap.getEdge(new MapPosition(8, 2)));
        assertEquals(8, intersectionPosition.getRow());
        assertEquals(1, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getIntersectionDirection1(testEdgeMap.getEdge(new MapPosition(10, 3)));
        assertEquals(10, intersectionPosition.getRow());
        assertEquals(2, intersectionPosition.getColumn());
    }

    @Test
    public void testGetIntersectionIndexFromBadEdgeObjectDirection1() {
        EdgeMap testEdgeMap = new EdgeMap();
        try {
            MapPosition intersectionPosition = testEdgeMap.getIntersectionDirection1(new Edge());
            fail("Did not throw InvalidEdgePositionException");
        } catch (InvalidEdgePositionException e) {
        } // passes
    }

    @Test
    public void testGetIntersectionIndexFromEdgeAnyDirection() {
        EdgeMap testEdgeMap = new EdgeMap();
        MapPosition intersectionPosition = testEdgeMap
                .getAdjacentIntersection(testEdgeMap.getEdge(new MapPosition(0, 0)), Direction.ONE);
        assertEquals(0, intersectionPosition.getRow());
        assertEquals(0, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getAdjacentIntersection(testEdgeMap.getEdge(new MapPosition(5, 5)),
                Direction.ONE);
        assertEquals(5, intersectionPosition.getRow());
        assertEquals(5, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getAdjacentIntersection(testEdgeMap.getEdge(new MapPosition(2, 1)),
                Direction.ONE);
        assertEquals(2, intersectionPosition.getRow());
        assertEquals(0, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getAdjacentIntersection(testEdgeMap.getEdge(new MapPosition(8, 2)),
                Direction.ONE);
        assertEquals(8, intersectionPosition.getRow());
        assertEquals(1, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getAdjacentIntersection(testEdgeMap.getEdge(new MapPosition(10, 3)),
                Direction.ONE);
        assertEquals(10, intersectionPosition.getRow());
        assertEquals(2, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getAdjacentIntersection(testEdgeMap.getEdge(new MapPosition(0, 0)),
                Direction.ZERO);
        assertEquals(1, intersectionPosition.getRow());
        assertEquals(0, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getAdjacentIntersection(testEdgeMap.getEdge(new MapPosition(5, 5)),
                Direction.ZERO);
        assertEquals(6, intersectionPosition.getRow());
        assertEquals(5, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getAdjacentIntersection(testEdgeMap.getEdge(new MapPosition(2, 1)),
                Direction.ZERO);
        assertEquals(3, intersectionPosition.getRow());
        assertEquals(1, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getAdjacentIntersection(testEdgeMap.getEdge(new MapPosition(8, 2)),
                Direction.ZERO);
        assertEquals(9, intersectionPosition.getRow());
        assertEquals(1, intersectionPosition.getColumn());
        intersectionPosition = testEdgeMap.getAdjacentIntersection(testEdgeMap.getEdge(new MapPosition(10, 3)),
                Direction.ZERO);
        assertEquals(11, intersectionPosition.getRow());
        assertEquals(1, intersectionPosition.getColumn());
    }

    @Test
    public void testGetIntersectionIndexFromBadEdgeAnyDirection() {
        EdgeMap testEdgeMap = new EdgeMap();
        try {
            MapPosition intersectionPosition = testEdgeMap.getAdjacentIntersection(new Edge(), Direction.ZERO);
            fail("Did not throw InvalidEdgePositionException");
        } catch (InvalidEdgePositionException e) {
        } // passes
    }

    @Test
    public void testGetIntersectionIndexFromEdgeBadDirection() {
        getIntersectionIndexFromEdgeBadDirection(Direction.TWO);
        getIntersectionIndexFromEdgeBadDirection(Direction.THREE);
        getIntersectionIndexFromEdgeBadDirection(Direction.FOUR);
        getIntersectionIndexFromEdgeBadDirection(Direction.FIVE);
    }

    public void getIntersectionIndexFromEdgeBadDirection(Direction direction) {
        EdgeMap testEdgeMap = new EdgeMap();
        try {
            MapPosition intersectionPosition = testEdgeMap
                    .getAdjacentIntersection(testEdgeMap.getEdge(new MapPosition(0, 0)), direction);
            fail("Did not throw InvalidDirectionException");
        } catch (IllegalArgumentException e) {
        } // passes
    }

    @Test
    public void testSizeGetAllIntersectionIndexesFromEdge() {
        EdgeMap testEdgeMap = new EdgeMap();
        ArrayList<MapPosition> positions = testEdgeMap.getAllAdjacentIntersections(new MapPosition(0, 0));
        assertEquals(2, positions.size());

        positions = testEdgeMap.getAllAdjacentIntersections(testEdgeMap.getEdge(new MapPosition(0, 0)));
        assertEquals(2, positions.size());

        try {
            positions = testEdgeMap.getAllAdjacentIntersections(new MapPosition());
            fail("did not throw exception");
        } catch (InvalidEdgePositionException e) {
        } // passes

        positions = testEdgeMap.getAllAdjacentIntersections(new Edge());
        assertTrue(positions.isEmpty());
    }

    @Test
    public void testGetAllIntersectionsIndexesFromEdgeIndexes() {
        EdgeMap testEdgeMap = new EdgeMap();
        ArrayList<MapPosition> intersections = testEdgeMap.getAllAdjacentIntersections(new MapPosition(0, 0));
        assertTrue(intersections.get(1).equals(new MapPosition(0, 0)));
        assertTrue(intersections.get(0).equals(new MapPosition(1, 0)));

        intersections = testEdgeMap.getAllAdjacentIntersections(new MapPosition(6, 9));
        assertTrue(intersections.get(1).equals(new MapPosition(6, 5)));
        assertTrue(intersections.get(0).equals(new MapPosition(7, 4)));

        intersections = testEdgeMap.getAllAdjacentIntersections(new MapPosition(10, 0));
        assertTrue(intersections.get(1).equals(new MapPosition(10, 0)));
        assertTrue(intersections.get(0).equals(new MapPosition(11, 0)));

        intersections = testEdgeMap.getAllAdjacentIntersections(new MapPosition(0, 5));
        assertTrue(intersections.get(1).equals(new MapPosition(0, 2)));
        assertTrue(intersections.get(0).equals(new MapPosition(1, 3)));

        intersections = testEdgeMap.getAllAdjacentIntersections(new MapPosition(5, 5));
        assertTrue(intersections.get(1).equals(new MapPosition(5, 5)));
        assertTrue(intersections.get(0).equals(new MapPosition(6, 5)));

        intersections = testEdgeMap.getAllAdjacentIntersections(new MapPosition(5, 0));
        assertTrue(intersections.get(1).equals(new MapPosition(5, 0)));
        assertTrue(intersections.get(0).equals(new MapPosition(6, 0)));
    }

    @Test
    public void testGetAllIntersectionsIndexesFromEdgeObject() {
        EdgeMap testEdgeMap = new EdgeMap();
        ArrayList<MapPosition> intersections = testEdgeMap
                .getAllAdjacentIntersections(testEdgeMap.getEdge(new MapPosition(0, 0)));
        assertTrue(intersections.get(1).equals(new MapPosition(0, 0)));
        assertTrue(intersections.get(0).equals(new MapPosition(1, 0)));

        intersections = testEdgeMap.getAllAdjacentIntersections(testEdgeMap.getEdge(new MapPosition(6, 9)));
        assertTrue(intersections.get(1).equals(new MapPosition(6, 5)));
        assertTrue(intersections.get(0).equals(new MapPosition(7, 4)));

        intersections = testEdgeMap.getAllAdjacentIntersections(testEdgeMap.getEdge(new MapPosition(10, 0)));
        assertTrue(intersections.get(1).equals(new MapPosition(10, 0)));
        assertTrue(intersections.get(0).equals(new MapPosition(11, 0)));

        intersections = testEdgeMap.getAllAdjacentIntersections(testEdgeMap.getEdge(new MapPosition(0, 5)));
        assertTrue(intersections.get(1).equals(new MapPosition(0, 2)));
        assertTrue(intersections.get(0).equals(new MapPosition(1, 3)));

        intersections = testEdgeMap.getAllAdjacentIntersections(testEdgeMap.getEdge(new MapPosition(5, 5)));
        assertTrue(intersections.get(1).equals(new MapPosition(5, 5)));
        assertTrue(intersections.get(0).equals(new MapPosition(6, 5)));

        intersections = testEdgeMap.getAllAdjacentIntersections(testEdgeMap.getEdge(new MapPosition(5, 0)));
        assertTrue(intersections.get(1).equals(new MapPosition(5, 0)));
        assertTrue(intersections.get(0).equals(new MapPosition(6, 0)));
    }

    @Test
    public void testMapPositionEquals() {
        MapPosition testPos = new MapPosition(5, 5);
        assertTrue(testPos.equals(new MapPosition(5, 5)));
        assertFalse(testPos.equals(new MapPosition(1, 4)));
        assertFalse(testPos.equals(new MapPosition(1, 5)));
        assertFalse(testPos.equals(new MapPosition(5, 4)));
        assertFalse(testPos.equals(new Edge()));
        assertFalse(testPos.equals(null));
    }

}
