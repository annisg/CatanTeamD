package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import exception.*;

public class IntersectionMap_Tests {

    @Test
    public void testIntersectionMapSize() {
        IntersectionMap testInterMap = new IntersectionMap();
        assertEquals(12, testInterMap.getNumberOfRows());
        assertEquals(3, testInterMap.getNumberOfIntersectionsInRow(0));
        assertEquals(4, testInterMap.getNumberOfIntersectionsInRow(1));
        assertEquals(4, testInterMap.getNumberOfIntersectionsInRow(2));
        assertEquals(5, testInterMap.getNumberOfIntersectionsInRow(3));
        assertEquals(5, testInterMap.getNumberOfIntersectionsInRow(4));
        assertEquals(6, testInterMap.getNumberOfIntersectionsInRow(5));
        assertEquals(6, testInterMap.getNumberOfIntersectionsInRow(6));
        assertEquals(5, testInterMap.getNumberOfIntersectionsInRow(7));
        assertEquals(5, testInterMap.getNumberOfIntersectionsInRow(8));
        assertEquals(4, testInterMap.getNumberOfIntersectionsInRow(9));
        assertEquals(4, testInterMap.getNumberOfIntersectionsInRow(10));
        assertEquals(3, testInterMap.getNumberOfIntersectionsInRow(11));
    }

    @Test
    public void testIntersectionMapSizeBadRow() {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            testInterMap.getNumberOfIntersectionsInRow(-1);
            fail("did not throw invalid position exception");
        } catch (InvalidIntersectionPositionException e) {
        }

        try {
            testInterMap.getNumberOfIntersectionsInRow(12);
            fail("did not throw invalid position exception");
        } catch (InvalidIntersectionPositionException e) {
        }
    }

    @Test
    public void testAllEmptyIntersections() {
        IntersectionMap testInterMap = new IntersectionMap();
        for (int i = 0; i < testInterMap.getNumberOfRows(); i++) {
            for (int j = 0; j < testInterMap.getNumberOfIntersectionsInRow(i); j++) {
                Intersection testIntersection = testInterMap.getIntersection(new MapPosition(i, j));
                assertEquals(PlayerColor.NONE, testIntersection.getBuildingColor());
                assertFalse(testIntersection.hasCity());
                assertFalse(testIntersection.hasSettlement());
            }
        }
    }

    @Test
    public void testGetIntersectionBadIndexes() {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            testInterMap.getIntersection(new MapPosition(-1, 0));
            fail("did not throw invalid position exception");
        } catch (InvalidIntersectionPositionException e) {
        }
        for (int i = 0; i < testInterMap.getNumberOfRows(); i++) {
            try {
                testInterMap.getIntersection(new MapPosition(i, -1));
                fail("did not throw invalid position exception");
            } catch (InvalidIntersectionPositionException e) {
            }

            try {
                testInterMap.getIntersection(new MapPosition(0, testInterMap.getNumberOfIntersectionsInRow(i)));
                fail("did not throw invalid position exception");
            } catch (InvalidIntersectionPositionException e) {
            }
        }
        try {
            testInterMap.getIntersection(new MapPosition(testInterMap.getNumberOfRows(), 0));
            fail("did not throw invalid position exception");
        } catch (InvalidIntersectionPositionException e) {
        }
    }

    @Test
    public void testGetIndexesFromIntersection() {
        IntersectionMap testInterMap = new IntersectionMap();
        Intersection testEdge;
        MapPosition indexes;
        for (int i = 0; i < testInterMap.getNumberOfRows(); i++) {
            testEdge = testInterMap.getIntersection(new MapPosition(i, 0));
            indexes = testInterMap.findIntersectionPosition(testEdge);
            assertEquals(i, indexes.getRow());
            assertEquals(0, indexes.getColumn());

            testEdge = testInterMap
                    .getIntersection(new MapPosition(i, testInterMap.getNumberOfIntersectionsInRow(i) - 1));
            indexes = testInterMap.findIntersectionPosition(testEdge);
            assertEquals(i, indexes.getRow());
            assertEquals(testInterMap.getNumberOfIntersectionsInRow(i) - 1, indexes.getColumn());
        }
    }

    @Test
    public void testGetPositionFromBadIntersection() {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            testInterMap.findIntersectionPosition(new Intersection());
            fail("failed to throw Invalid Intersection Object Excpetion");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testGetEdgeFromPositionDirection0() {
        IntersectionMap testInterMap = new IntersectionMap();
        MapPosition edgePosition = testInterMap.getEdgeDirection0(new MapPosition(0, 0));
        assertEquals(0, edgePosition.getRow());
        assertEquals(1, edgePosition.getColumn());
        edgePosition = testInterMap.getEdgeDirection0(new MapPosition(5, 5));
        assertEquals(5, edgePosition.getRow());
        assertEquals(5, edgePosition.getColumn());
        edgePosition = testInterMap.getEdgeDirection0(new MapPosition(9, 1));
        assertEquals(9, edgePosition.getRow());
        assertEquals(1, edgePosition.getColumn());
        edgePosition = testInterMap.getEdgeDirection0(new MapPosition(6, 4));
        assertEquals(6, edgePosition.getRow());
        assertEquals(8, edgePosition.getColumn());
    }

    @Test
    public void testGetEdgeDirection0BadPosition() {
        getEdgeIndexFromBadIntersectionPositionDirection0(new MapPosition(-1, 0));
        getEdgeIndexFromBadIntersectionPositionDirection0(new MapPosition(0, -1));
        getEdgeIndexFromBadIntersectionPositionDirection0(new MapPosition(12, 0));
        getEdgeIndexFromBadIntersectionPositionDirection0(new MapPosition(0, 3));
        getEdgeIndexFromBadIntersectionPositionDirection0(new MapPosition(5, 6));
    }

    public void getEdgeIndexFromBadIntersectionPositionDirection0(MapPosition pos) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition edgePosition = testInterMap.getEdgeDirection0(pos);
            fail("Did not throw InvalidIntersectionPositionException");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testGetEdgeFromPositionDirection1() {
        IntersectionMap testInterMap = new IntersectionMap();
        MapPosition edgePosition = testInterMap.getEdgeDirection1(new MapPosition(1, 0));
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getEdgeDirection1(new MapPosition(5, 2));
        assertEquals(4, edgePosition.getRow());
        assertEquals(4, edgePosition.getColumn());
        edgePosition = testInterMap.getEdgeDirection1(new MapPosition(11, 2));
        assertEquals(10, edgePosition.getRow());
        assertEquals(5, edgePosition.getColumn());
        edgePosition = testInterMap.getEdgeDirection1(new MapPosition(6, 1));
        assertEquals(5, edgePosition.getRow());
        assertEquals(1, edgePosition.getColumn());
    }

    @Test
    public void testGetEdgeDirection1BadPosition() {
        getEdgeIndexFromBadIntersectionPositionDirection1(new MapPosition(-1, 0));
        getEdgeIndexFromBadIntersectionPositionDirection1(new MapPosition(0, -1));
        getEdgeIndexFromBadIntersectionPositionDirection1(new MapPosition(12, 0));
        getEdgeIndexFromBadIntersectionPositionDirection1(new MapPosition(0, 3));
        getEdgeIndexFromBadIntersectionPositionDirection1(new MapPosition(5, 6));
    }

    public void getEdgeIndexFromBadIntersectionPositionDirection1(MapPosition pos) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition edgePosition = testInterMap.getEdgeDirection1(pos);
            fail("Did not throw InvalidIntersectionPositionException");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testGetEdgeFromPositionDirection2() {
        IntersectionMap testInterMap = new IntersectionMap();
        MapPosition edgePosition = testInterMap.getEdgeDirection2(new MapPosition(1, 3));
        assertEquals(0, edgePosition.getRow());
        assertEquals(5, edgePosition.getColumn());
        edgePosition = testInterMap.getEdgeDirection2(new MapPosition(0, 0));
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getEdgeDirection2(new MapPosition(11, 2));
        assertEquals(10, edgePosition.getRow());
        assertEquals(4, edgePosition.getColumn());
        edgePosition = testInterMap.getEdgeDirection2(new MapPosition(6, 5));
        assertEquals(6, edgePosition.getRow());
        assertEquals(9, edgePosition.getColumn());
    }

    @Test
    public void testGetEdgeDirection2BadPosition() {
        getEdgeIndexFromBadIntersectionPositionDirection2(new MapPosition(-1, 0));
        getEdgeIndexFromBadIntersectionPositionDirection2(new MapPosition(0, -1));
        getEdgeIndexFromBadIntersectionPositionDirection2(new MapPosition(12, 0));
        getEdgeIndexFromBadIntersectionPositionDirection2(new MapPosition(0, 3));
        getEdgeIndexFromBadIntersectionPositionDirection2(new MapPosition(5, 6));
    }

    public void getEdgeIndexFromBadIntersectionPositionDirection2(MapPosition pos) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition edgePosition = testInterMap.getEdgeDirection2(pos);
            fail("Did not throw InvalidIntersectionPositionException");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testGetEdgeFromPositionAnyDirection() {
        IntersectionMap testInterMap = new IntersectionMap();
        MapPosition edgePosition = testInterMap.getAdjacentEdge(new MapPosition(1, 3), Direction.TWO);
        assertEquals(0, edgePosition.getRow());
        assertEquals(5, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(new MapPosition(0, 0), Direction.TWO);
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(new MapPosition(11, 2), Direction.TWO);
        assertEquals(10, edgePosition.getRow());
        assertEquals(4, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(new MapPosition(6, 5), Direction.TWO);
        assertEquals(6, edgePosition.getRow());
        assertEquals(9, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(new MapPosition(1, 0), Direction.ONE);
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(new MapPosition(2, 3), Direction.ONE);
        assertEquals(1, edgePosition.getRow());
        assertEquals(3, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(new MapPosition(11, 2), Direction.ONE);
        assertEquals(10, edgePosition.getRow());
        assertEquals(5, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(new MapPosition(8, 0), Direction.ONE);
        assertEquals(7, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(new MapPosition(0, 0), Direction.ZERO);
        assertEquals(0, edgePosition.getRow());
        assertEquals(1, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(new MapPosition(3, 4), Direction.ZERO);
        assertEquals(3, edgePosition.getRow());
        assertEquals(4, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(new MapPosition(7, 0), Direction.ZERO);
        assertEquals(7, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(new MapPosition(8, 3), Direction.ZERO);
        assertEquals(8, edgePosition.getRow());
        assertEquals(6, edgePosition.getColumn());
    }

    @Test
    public void testGetEdgeAnyDirectionBadPosition() {
        getEdgeIndexFromBadIntersectionPositionAnyDirection(new MapPosition(-1, 0));
        getEdgeIndexFromBadIntersectionPositionAnyDirection(new MapPosition(0, -1));
        getEdgeIndexFromBadIntersectionPositionAnyDirection(new MapPosition(12, 0));
        getEdgeIndexFromBadIntersectionPositionAnyDirection(new MapPosition(0, 3));
        getEdgeIndexFromBadIntersectionPositionAnyDirection(new MapPosition(5, 6));
    }

    public void getEdgeIndexFromBadIntersectionPositionAnyDirection(MapPosition pos) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition edgePosition = testInterMap.getAdjacentEdge(pos, Direction.ZERO);
            fail("Did not throw InvalidIntersectionPositionException");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testGetEdgeAnyDirectionBadDirection() {
        getEdgeIndexFromIntersectionPositionBadDirection(Direction.THREE);
        getEdgeIndexFromIntersectionPositionBadDirection(Direction.FOUR);
        getEdgeIndexFromIntersectionPositionBadDirection(Direction.FIVE);
    }

    public void getEdgeIndexFromIntersectionPositionBadDirection(Direction direction) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition edgePosition = testInterMap.getAdjacentEdge(new MapPosition(0, 0), direction);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
        } // passes
    }

    @Test
    public void testGetEdgeFromObjectAnyDirection() {
        IntersectionMap testInterMap = new IntersectionMap();
        MapPosition edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(1, 3)),
                Direction.TWO);
        assertEquals(0, edgePosition.getRow());
        assertEquals(5, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(0, 0)), Direction.TWO);
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(11, 2)),
                Direction.TWO);
        assertEquals(10, edgePosition.getRow());
        assertEquals(4, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(6, 5)), Direction.TWO);
        assertEquals(6, edgePosition.getRow());
        assertEquals(9, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(1, 0)), Direction.ONE);
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(2, 3)), Direction.ONE);
        assertEquals(1, edgePosition.getRow());
        assertEquals(3, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(11, 2)),
                Direction.ONE);
        assertEquals(10, edgePosition.getRow());
        assertEquals(5, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(8, 0)), Direction.ONE);
        assertEquals(7, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(0, 0)),
                Direction.ZERO);
        assertEquals(0, edgePosition.getRow());
        assertEquals(1, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(3, 4)),
                Direction.ZERO);
        assertEquals(3, edgePosition.getRow());
        assertEquals(4, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(7, 0)),
                Direction.ZERO);
        assertEquals(7, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(8, 3)),
                Direction.ZERO);
        assertEquals(8, edgePosition.getRow());
        assertEquals(6, edgePosition.getColumn());
    }

    @Test
    public void testGetEdgeObjectAnyDirectionBadDirection() {
        getEdgeObjectFromIntersectionPositionBadDirection(Direction.THREE);
        getEdgeObjectFromIntersectionPositionBadDirection(Direction.FOUR);
        getEdgeObjectFromIntersectionPositionBadDirection(Direction.FIVE);
    }

    public void getEdgeObjectFromIntersectionPositionBadDirection(Direction direction) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition edgePosition = testInterMap.getAdjacentEdge(testInterMap.getIntersection(new MapPosition(0, 0)),
                    direction);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
        } // passes
    }

    @Test
    public void testGetEdgeAnyDirectionBadObject() {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition edgePosition = testInterMap.getAdjacentEdge(new Intersection(), Direction.ZERO);
            fail("Did not throw InvalidIntersectionPositionException");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testSizeGetAllEdges() {
        IntersectionMap testInterMap = new IntersectionMap();
        assertEquals(3, testInterMap.getAllAdjacentEdges(new MapPosition(0, 0)).size());
        assertEquals(3, testInterMap.getAllAdjacentEdges(testInterMap.getIntersection(new MapPosition(0, 0))).size());
    }

    @Test
    public void testGetAllEdgesFromPosition() {
        IntersectionMap testInterMap = new IntersectionMap();
        ArrayList<MapPosition> foundEdges = testInterMap.getAllAdjacentEdges(new MapPosition(5, 1));
        assertTrue(foundEdges.get(0).equals(new MapPosition(5, 1)));
        assertTrue(foundEdges.get(1).equals(new MapPosition(4, 2)));
        assertTrue(foundEdges.get(2).equals(new MapPosition(4, 1)));
        foundEdges = testInterMap.getAllAdjacentEdges(new MapPosition(2, 3));
        assertTrue(foundEdges.get(0).equals(new MapPosition(2, 7)));
        assertTrue(foundEdges.get(1).equals(new MapPosition(1, 3)));
        assertTrue(foundEdges.get(2).equals(new MapPosition(2, 6)));
        foundEdges = testInterMap.getAllAdjacentEdges(new MapPosition(9, 3));
        assertTrue(foundEdges.get(0).equals(new MapPosition(9, 3)));
        assertTrue(foundEdges.get(1).equals(new MapPosition(8, 7)));
        assertTrue(foundEdges.get(2).equals(new MapPosition(8, 6)));
        foundEdges = testInterMap.getAllAdjacentEdges(new MapPosition(6, 1));
        assertTrue(foundEdges.get(0).equals(new MapPosition(6, 2)));
        assertTrue(foundEdges.get(1).equals(new MapPosition(5, 1)));
        assertTrue(foundEdges.get(2).equals(new MapPosition(6, 1)));
    }

    @Test
    public void testGetAllEdgesFromBadPosition() {
        getAllEdgeIndexesFromIntersectionBadPosition(new MapPosition(-1, 0));
        getAllEdgeIndexesFromIntersectionBadPosition(new MapPosition(0, -1));
        getAllEdgeIndexesFromIntersectionBadPosition(new MapPosition(12, 0));
        getAllEdgeIndexesFromIntersectionBadPosition(new MapPosition(0, 3));
        getAllEdgeIndexesFromIntersectionBadPosition(new MapPosition(5, 6));
    }

    public void getAllEdgeIndexesFromIntersectionBadPosition(MapPosition pos) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            testInterMap.getAllAdjacentEdges(pos);
            fail("Did not throw exception");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testGetAllEdgesFromObject() {
        IntersectionMap testInterMap = new IntersectionMap();
        ArrayList<MapPosition> foundEdges = testInterMap
                .getAllAdjacentEdges(testInterMap.getIntersection(new MapPosition(5, 1)));
        assertTrue(foundEdges.get(0).equals(new MapPosition(5, 1)));
        assertTrue(foundEdges.get(1).equals(new MapPosition(4, 2)));
        assertTrue(foundEdges.get(2).equals(new MapPosition(4, 1)));
        foundEdges = testInterMap.getAllAdjacentEdges(testInterMap.getIntersection(new MapPosition(2, 3)));
        assertTrue(foundEdges.get(0).equals(new MapPosition(2, 7)));
        assertTrue(foundEdges.get(1).equals(new MapPosition(1, 3)));
        assertTrue(foundEdges.get(2).equals(new MapPosition(2, 6)));
        foundEdges = testInterMap.getAllAdjacentEdges(testInterMap.getIntersection(new MapPosition(9, 3)));
        assertTrue(foundEdges.get(0).equals(new MapPosition(9, 3)));
        assertTrue(foundEdges.get(1).equals(new MapPosition(8, 7)));
        assertTrue(foundEdges.get(2).equals(new MapPosition(8, 6)));
        foundEdges = testInterMap.getAllAdjacentEdges(testInterMap.getIntersection(new MapPosition(6, 1)));
        assertTrue(foundEdges.get(0).equals(new MapPosition(6, 2)));
        assertTrue(foundEdges.get(1).equals(new MapPosition(5, 1)));
        assertTrue(foundEdges.get(2).equals(new MapPosition(6, 1)));
    }

    @Test
    public void getAllEdgeIndexesFromIntersectionBadObject() {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            testInterMap.getAllAdjacentEdges(new Intersection());
            fail("Did not throw exception");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testGetHexFromPositionDirection0() {
        IntersectionMap testInterMap = new IntersectionMap();
        MapPosition edgePosition = testInterMap.getHexDirection0(new MapPosition(0, 0));
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getHexDirection0(new MapPosition(5, 4));
        assertEquals(2, edgePosition.getRow());
        assertEquals(4, edgePosition.getColumn());
        edgePosition = testInterMap.getHexDirection0(new MapPosition(7, 0));
        assertEquals(3, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getHexDirection0(new MapPosition(6, 3));
        assertEquals(3, edgePosition.getRow());
        assertEquals(2, edgePosition.getColumn());
    }

    @Test
    public void testGetHexDirection0BadPosition() {
        getHexIndexFromBadIntersectionPositionDirection0(new MapPosition(-1, 0));
        getHexIndexFromBadIntersectionPositionDirection0(new MapPosition(0, -1));
        getHexIndexFromBadIntersectionPositionDirection0(new MapPosition(12, 0));
        getHexIndexFromBadIntersectionPositionDirection0(new MapPosition(0, 3));
        getHexIndexFromBadIntersectionPositionDirection0(new MapPosition(5, 6));
    }

    public void getHexIndexFromBadIntersectionPositionDirection0(MapPosition pos) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition hexPosition = testInterMap.getHexDirection0(pos);
            fail("Did not throw InvalidIntersectionPositionException");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testGetHexFromPositionDirection1() {
        IntersectionMap testInterMap = new IntersectionMap();
        MapPosition edgePosition = testInterMap.getHexDirection1(new MapPosition(2, 0));
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getHexDirection1(new MapPosition(5, 4));
        assertEquals(1, edgePosition.getRow());
        assertEquals(3, edgePosition.getColumn());
        edgePosition = testInterMap.getHexDirection1(new MapPosition(7, 0));
        assertEquals(2, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getHexDirection1(new MapPosition(6, 3));
        assertEquals(2, edgePosition.getRow());
        assertEquals(3, edgePosition.getColumn());
    }

    @Test
    public void testGetHexDirection1BadPosition() {
        getHexIndexFromBadIntersectionPositionDirection1(new MapPosition(-1, 0));
        getHexIndexFromBadIntersectionPositionDirection1(new MapPosition(0, -1));
        getHexIndexFromBadIntersectionPositionDirection1(new MapPosition(12, 0));
        getHexIndexFromBadIntersectionPositionDirection1(new MapPosition(0, 3));
        getHexIndexFromBadIntersectionPositionDirection1(new MapPosition(5, 6));
    }

    public void getHexIndexFromBadIntersectionPositionDirection1(MapPosition pos) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition hexPosition = testInterMap.getHexDirection1(pos);
            fail("Did not throw InvalidIntersectionPositionException");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testGetHexFromPositionDirection2() {
        IntersectionMap testInterMap = new IntersectionMap();
        MapPosition edgePosition = testInterMap.getHexDirection2(new MapPosition(2, 1));
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getHexDirection2(new MapPosition(5, 4));
        assertEquals(2, edgePosition.getRow());
        assertEquals(3, edgePosition.getColumn());
        edgePosition = testInterMap.getHexDirection2(new MapPosition(7, 1));
        assertEquals(3, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getHexDirection2(new MapPosition(6, 3));
        assertEquals(2, edgePosition.getRow());
        assertEquals(2, edgePosition.getColumn());
    }

    @Test
    public void testGetHexDirection2BadPosition() {
        getHexIndexFromBadIntersectionPositionDirection2(new MapPosition(-1, 0));
        getHexIndexFromBadIntersectionPositionDirection2(new MapPosition(0, -1));
        getHexIndexFromBadIntersectionPositionDirection2(new MapPosition(12, 0));
        getHexIndexFromBadIntersectionPositionDirection2(new MapPosition(0, 3));
        getHexIndexFromBadIntersectionPositionDirection2(new MapPosition(5, 6));
    }

    public void getHexIndexFromBadIntersectionPositionDirection2(MapPosition pos) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition hexPosition = testInterMap.getHexDirection2(pos);
            fail("Did not throw InvalidIntersectionPositionException");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testGexHexFromPositionAnyDirection() {
        IntersectionMap testInterMap = new IntersectionMap();
        MapPosition edgePosition = testInterMap.getAdjacentHex(new MapPosition(2, 1), Direction.TWO);
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(new MapPosition(5, 4), Direction.TWO);
        assertEquals(2, edgePosition.getRow());
        assertEquals(3, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(new MapPosition(7, 1), Direction.TWO);
        assertEquals(3, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(new MapPosition(6, 3), Direction.TWO);
        assertEquals(2, edgePosition.getRow());
        assertEquals(2, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(new MapPosition(2, 0), Direction.ONE);
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(new MapPosition(5, 4), Direction.ONE);
        assertEquals(1, edgePosition.getRow());
        assertEquals(3, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(new MapPosition(7, 0), Direction.ONE);
        assertEquals(2, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(new MapPosition(6, 3), Direction.ONE);
        assertEquals(2, edgePosition.getRow());
        assertEquals(3, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(new MapPosition(0, 0), Direction.ZERO);
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(new MapPosition(5, 4), Direction.ZERO);
        assertEquals(2, edgePosition.getRow());
        assertEquals(4, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(new MapPosition(7, 0), Direction.ZERO);
        assertEquals(3, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(new MapPosition(6, 3), Direction.ZERO);
        assertEquals(3, edgePosition.getRow());
        assertEquals(2, edgePosition.getColumn());
    }

    @Test
    public void testGetHexAnyDirectionBadPosition() {
        getHexIndexFromBadIntersectionPositionDirection2(new MapPosition(-1, 0));
        getHexIndexFromBadIntersectionPositionDirection2(new MapPosition(0, -1));
        getHexIndexFromBadIntersectionPositionDirection2(new MapPosition(12, 0));
        getHexIndexFromBadIntersectionPositionDirection2(new MapPosition(0, 3));
        getHexIndexFromBadIntersectionPositionDirection2(new MapPosition(5, 6));
    }

    public void getHexIndexFromBadIntersectionPositionAnyDirection(MapPosition pos) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition hexPosition = testInterMap.getAdjacentHex(pos, Direction.ZERO);
            fail("Did not throw InvalidIntersectionPositionException");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testGetHexFromPositionBadDirection() {
        getHexIndexFromIntersectionPositionBadDirection(Direction.THREE);
        getHexIndexFromIntersectionPositionBadDirection(Direction.FOUR);
        getHexIndexFromIntersectionPositionBadDirection(Direction.FIVE);
    }

    public void getHexIndexFromIntersectionPositionBadDirection(Direction direct) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition hexPosition = testInterMap.getAdjacentHex(new MapPosition(0, 0), direct);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
        } // passes
    }

    @Test
    public void testGexHexFromObjectAnyDirection() {
        IntersectionMap testInterMap = new IntersectionMap();
        MapPosition edgePosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(2, 1)),
                Direction.TWO);
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(5, 4)), Direction.TWO);
        assertEquals(2, edgePosition.getRow());
        assertEquals(3, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(7, 1)), Direction.TWO);
        assertEquals(3, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(6, 3)), Direction.TWO);
        assertEquals(2, edgePosition.getRow());
        assertEquals(2, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(2, 0)), Direction.ONE);
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(5, 4)), Direction.ONE);
        assertEquals(1, edgePosition.getRow());
        assertEquals(3, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(7, 0)), Direction.ONE);
        assertEquals(2, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(6, 3)), Direction.ONE);
        assertEquals(2, edgePosition.getRow());
        assertEquals(3, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(0, 0)), Direction.ZERO);
        assertEquals(0, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(5, 4)), Direction.ZERO);
        assertEquals(2, edgePosition.getRow());
        assertEquals(4, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(7, 0)), Direction.ZERO);
        assertEquals(3, edgePosition.getRow());
        assertEquals(0, edgePosition.getColumn());
        edgePosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(6, 3)), Direction.ZERO);
        assertEquals(3, edgePosition.getRow());
        assertEquals(2, edgePosition.getColumn());
    }

    @Test
    public void getHexIndexFromBadIntersectionObjectAnyDirection() {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition hexPosition = testInterMap.getAdjacentHex(new Intersection(), Direction.ZERO);
            fail("Did not throw InvalidIntersectionPositionException");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void testGetHexFromObjectBadDirection() {
        getHexIndexFromIntersectionObjectBadDirection(Direction.THREE);
        getHexIndexFromIntersectionObjectBadDirection(Direction.FOUR);
        getHexIndexFromIntersectionObjectBadDirection(Direction.FIVE);
    }

    public void getHexIndexFromIntersectionObjectBadDirection(Direction direct) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            MapPosition hexPosition = testInterMap.getAdjacentHex(testInterMap.getIntersection(new MapPosition(0, 0)),
                    direct);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
        } // passes
    }

    @Test
    public void getSizeOfAllHexPositionsFromIntersectionPosition() {
        IntersectionMap testInterMap = new IntersectionMap();
        ArrayList<MapPosition> hexPositions = testInterMap.getAllAdjacentHexes(new MapPosition(2, 2));
        assertEquals(3, hexPositions.size());
    }

    @Test
    public void getAllHexPositionsFromIntersectionPosition() {
        IntersectionMap testInterMap = new IntersectionMap();
        ArrayList<MapPosition> hexPositions = testInterMap.getAllAdjacentHexes(new MapPosition(2, 2));
        assertTrue(hexPositions.get(0).equals(new MapPosition(1, 2)));
        assertTrue(hexPositions.get(1).equals(new MapPosition(0, 2)));
        assertTrue(hexPositions.get(2).equals(new MapPosition(0, 1)));
        hexPositions = testInterMap.getAllAdjacentHexes(new MapPosition(5, 1));
        assertTrue(hexPositions.get(0).equals(new MapPosition(2, 1)));
        assertTrue(hexPositions.get(1).equals(new MapPosition(1, 0)));
        assertTrue(hexPositions.get(2).equals(new MapPosition(2, 0)));
        hexPositions = testInterMap.getAllAdjacentHexes(new MapPosition(6, 4));
        assertTrue(hexPositions.get(0).equals(new MapPosition(3, 3)));
        assertTrue(hexPositions.get(1).equals(new MapPosition(2, 4)));
        assertTrue(hexPositions.get(2).equals(new MapPosition(2, 3)));
        hexPositions = testInterMap.getAllAdjacentHexes(new MapPosition(9, 1));
        assertTrue(hexPositions.get(0).equals(new MapPosition(4, 1)));
        assertTrue(hexPositions.get(1).equals(new MapPosition(3, 1)));
        assertTrue(hexPositions.get(2).equals(new MapPosition(4, 0)));
    }

    @Test
    public void testGetAllHexesFromBadPosition() {
        getAllHexIndexesFromIntersectionBadPosition(new MapPosition(-1, 0));
        getAllHexIndexesFromIntersectionBadPosition(new MapPosition(0, -1));
        getAllHexIndexesFromIntersectionBadPosition(new MapPosition(12, 0));
        getAllHexIndexesFromIntersectionBadPosition(new MapPosition(0, 3));
        getAllHexIndexesFromIntersectionBadPosition(new MapPosition(5, 6));
    }

    public void getAllHexIndexesFromIntersectionBadPosition(MapPosition pos) {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            testInterMap.getAllAdjacentHexes(pos);
            fail("Did not throw exception");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

    @Test
    public void getSizeOfAllHexPositionsFromIntersectionObject() {
        IntersectionMap testInterMap = new IntersectionMap();
        ArrayList<MapPosition> hexPositions = testInterMap
                .getAllAdjacentHexes(testInterMap.getIntersection(new MapPosition(2, 2)));
        assertEquals(3, hexPositions.size());
    }

    @Test
    public void getAllHexPositionsFromIntersectionObject() {
        IntersectionMap testInterMap = new IntersectionMap();
        ArrayList<MapPosition> hexPositions = testInterMap
                .getAllAdjacentHexes(testInterMap.getIntersection(new MapPosition(2, 2)));
        assertTrue(hexPositions.get(0).equals(new MapPosition(1, 2)));
        assertTrue(hexPositions.get(1).equals(new MapPosition(0, 2)));
        assertTrue(hexPositions.get(2).equals(new MapPosition(0, 1)));
        hexPositions = testInterMap.getAllAdjacentHexes(testInterMap.getIntersection(new MapPosition(5, 1)));
        assertTrue(hexPositions.get(0).equals(new MapPosition(2, 1)));
        assertTrue(hexPositions.get(1).equals(new MapPosition(1, 0)));
        assertTrue(hexPositions.get(2).equals(new MapPosition(2, 0)));
        hexPositions = testInterMap.getAllAdjacentHexes(testInterMap.getIntersection(new MapPosition(6, 4)));
        assertTrue(hexPositions.get(0).equals(new MapPosition(3, 3)));
        assertTrue(hexPositions.get(1).equals(new MapPosition(2, 4)));
        assertTrue(hexPositions.get(2).equals(new MapPosition(2, 3)));
        hexPositions = testInterMap.getAllAdjacentHexes(testInterMap.getIntersection(new MapPosition(9, 1)));
        assertTrue(hexPositions.get(0).equals(new MapPosition(4, 1)));
        assertTrue(hexPositions.get(1).equals(new MapPosition(3, 1)));
        assertTrue(hexPositions.get(2).equals(new MapPosition(4, 0)));
    }

    @Test
    public void getAllHexIndexesFromIntersectionBadObject() {
        IntersectionMap testInterMap = new IntersectionMap();
        try {
            testInterMap.getAllAdjacentHexes(new Intersection());
            fail("Did not throw exception");
        } catch (InvalidIntersectionPositionException e) {
        } // passes
    }

}
