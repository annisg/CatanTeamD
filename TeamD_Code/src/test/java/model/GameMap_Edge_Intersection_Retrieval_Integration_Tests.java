package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import exception.*;

public class GameMap_Edge_Intersection_Retrieval_Integration_Tests {

    @Test
    public void testSizeOfGetIntersectionsFromHexIndexes() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        ArrayList<Intersection> intersections = gmTest.getAllIntersectionsFromHex(0, 0);
        assertEquals(6, intersections.size());

        try {
            intersections = gmTest.getAllIntersectionsFromHex(-1, -1);
            fail();
        } catch (InvalidHexPositionException e) {}
    }

    @Test
    public void testGetIntersectionsFromHexIndexes() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        ArrayList<Intersection> intersections = gmTest.getAllIntersectionsFromHex(0, 0);
        assertTrue(intersections.contains(gmTest.getIntersection(0, 0)));
        assertTrue(intersections.contains(gmTest.getIntersection(1, 0)));
        assertTrue(intersections.contains(gmTest.getIntersection(1, 1)));
        assertTrue(intersections.contains(gmTest.getIntersection(2, 0)));
        assertTrue(intersections.contains(gmTest.getIntersection(2, 1)));
        assertTrue(intersections.contains(gmTest.getIntersection(3, 1)));

        intersections = gmTest.getAllIntersectionsFromHex(4, 2);
        assertTrue(intersections.contains(gmTest.getIntersection(8, 3)));
        assertTrue(intersections.contains(gmTest.getIntersection(9, 2)));
        assertTrue(intersections.contains(gmTest.getIntersection(9, 3)));
        assertTrue(intersections.contains(gmTest.getIntersection(10, 2)));
        assertTrue(intersections.contains(gmTest.getIntersection(10, 3)));
        assertTrue(intersections.contains(gmTest.getIntersection(11, 2)));

        intersections = gmTest.getAllIntersectionsFromHex(2, 4);
        assertTrue(intersections.contains(gmTest.getIntersection(4, 4)));
        assertTrue(intersections.contains(gmTest.getIntersection(5, 4)));
        assertTrue(intersections.contains(gmTest.getIntersection(5, 5)));
        assertTrue(intersections.contains(gmTest.getIntersection(6, 4)));
        assertTrue(intersections.contains(gmTest.getIntersection(6, 5)));
        assertTrue(intersections.contains(gmTest.getIntersection(7, 4)));
    }

    @Test
    public void testGetIntersectionsFromHexObject() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        ArrayList<Intersection> intersections = gmTest.getAllIntersectionsFromHex(gmTest.getHex(2, 0));
        assertTrue(intersections.contains(gmTest.getIntersection(4, 0)));
        assertTrue(intersections.contains(gmTest.getIntersection(5, 0)));
        assertTrue(intersections.contains(gmTest.getIntersection(5, 1)));
        assertTrue(intersections.contains(gmTest.getIntersection(6, 0)));
        assertTrue(intersections.contains(gmTest.getIntersection(6, 1)));
        assertTrue(intersections.contains(gmTest.getIntersection(7, 0)));

        try {
            gmTest.getAllIntersectionsFromHex(new Hex());
            fail();
        } catch(InvalidHexPositionException e) {}
    }

    @Test
    public void testSizeOfGetIntersectionsFromEdgeIndexes() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        ArrayList<Intersection> intersections = gmTest.getAllIntersectionsFromEdge(0, 0);
        assertEquals(2, intersections.size());

        try {
            intersections = gmTest.getAllIntersectionsFromEdge(-1, -1);
            fail("did not throw exception");
        } catch (InvalidEdgePositionException e) {
        }
    }

    @Test
    public void testGetIntersectionsFromEdgeIndexes() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        ArrayList<Intersection> intersections = gmTest.getAllIntersectionsFromEdge(0, 0);
        assertTrue(intersections.contains(gmTest.getIntersection(0, 0)));
        assertTrue(intersections.contains(gmTest.getIntersection(1, 0)));

        intersections = gmTest.getAllIntersectionsFromEdge(6, 9);
        assertTrue(intersections.contains(gmTest.getIntersection(6, 5)));
        assertTrue(intersections.contains(gmTest.getIntersection(7, 4)));

        intersections = gmTest.getAllIntersectionsFromEdge(10, 0);
        assertTrue(intersections.contains(gmTest.getIntersection(10, 0)));
        assertTrue(intersections.contains(gmTest.getIntersection(11, 0)));

        intersections = gmTest.getAllIntersectionsFromEdge(0, 5);
        assertTrue(intersections.contains(gmTest.getIntersection(0, 2)));
        assertTrue(intersections.contains(gmTest.getIntersection(1, 3)));

        intersections = gmTest.getAllIntersectionsFromEdge(5, 5);
        assertTrue(intersections.contains(gmTest.getIntersection(5, 5)));
        assertTrue(intersections.contains(gmTest.getIntersection(6, 5)));

        intersections = gmTest.getAllIntersectionsFromEdge(5, 0);
        assertTrue(intersections.contains(gmTest.getIntersection(5, 0)));
        assertTrue(intersections.contains(gmTest.getIntersection(6, 0)));
    }

    @Test
    public void testGetIntersectionsFromEdgeObject() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        ArrayList<Intersection> intersections = gmTest.getAllIntersectionsFromEdge(gmTest.getEdge(4, 0));
        assertTrue(intersections.contains(gmTest.getIntersection(5, 0)));
        assertTrue(intersections.contains(gmTest.getIntersection(4, 0)));

        intersections = gmTest.getAllIntersectionsFromEdge(gmTest.getEdge(6, 9));
        assertTrue(intersections.contains(gmTest.getIntersection(6, 5)));
        assertTrue(intersections.contains(gmTest.getIntersection(7, 4)));

        intersections = gmTest.getAllIntersectionsFromEdge(gmTest.getEdge(6, 0));
        assertTrue(intersections.contains(gmTest.getIntersection(6, 0)));
        assertTrue(intersections.contains(gmTest.getIntersection(7, 0)));

        intersections = gmTest.getAllIntersectionsFromEdge(gmTest.getEdge(4, 9));
        assertTrue(intersections.contains(gmTest.getIntersection(5, 5)));
        assertTrue(intersections.contains(gmTest.getIntersection(4, 4)));

        intersections = gmTest.getAllIntersectionsFromEdge(gmTest.getEdge(5, 5));
        assertTrue(intersections.contains(gmTest.getIntersection(5, 5)));
        assertTrue(intersections.contains(gmTest.getIntersection(6, 5)));

        intersections = gmTest.getAllIntersectionsFromEdge(gmTest.getEdge(5, 0));
        assertTrue(intersections.contains(gmTest.getIntersection(5, 0)));
        assertTrue(intersections.contains(gmTest.getIntersection(6, 0)));

        try {
            gmTest.getAllIntersectionsFromEdge(new Edge());
            fail();
        }catch(InvalidEdgePositionException e) {}
    }

    @Test
    public void testSizeOfGetEdgesFromIntersectionIndexes() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        ArrayList<Edge> edges = gmTest.getAllEdgesFromIntersection(0, 0);
        assertEquals(2, edges.size());
        edges = gmTest.getAllEdgesFromIntersection(11, 2);
        assertEquals(2, edges.size());
        edges = gmTest.getAllEdgesFromIntersection(6, 5);
        assertEquals(2, edges.size());
        edges = gmTest.getAllEdgesFromIntersection(3, 3);
        assertEquals(3, edges.size());
        edges = gmTest.getAllEdgesFromIntersection(10, 2);
        assertEquals(3, edges.size());
        edges = gmTest.getAllEdgesFromIntersection(2, 0);
        assertEquals(3, edges.size());

        try {
            edges = gmTest.getAllEdgesFromIntersection(-1, -1);
        } catch (InvalidIntersectionPositionException e) {
        }
    }

    @Test
    public void testGetEdgesFromIntersectionIndexes() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        ArrayList<Edge> edges = gmTest.getAllEdgesFromIntersection(0, 0);
        assertTrue(edges.contains(gmTest.getEdge(0, 0)));
        assertTrue(edges.contains(gmTest.getEdge(0, 1)));

        edges = gmTest.getAllEdgesFromIntersection(11, 2);
        assertTrue(edges.contains(gmTest.getEdge(10, 4)));
        assertTrue(edges.contains(gmTest.getEdge(10, 5)));

        edges = gmTest.getAllEdgesFromIntersection(6, 5);
        assertTrue(edges.contains(gmTest.getEdge(6, 9)));
        assertTrue(edges.contains(gmTest.getEdge(5, 5)));

        edges = gmTest.getAllEdgesFromIntersection(3, 3);
        assertTrue(edges.contains(gmTest.getEdge(2, 5)));
        assertTrue(edges.contains(gmTest.getEdge(2, 6)));
        assertTrue(edges.contains(gmTest.getEdge(3, 3)));

        edges = gmTest.getAllEdgesFromIntersection(10, 2);
        assertTrue(edges.contains(gmTest.getEdge(10, 4)));
        assertTrue(edges.contains(gmTest.getEdge(10, 3)));
        assertTrue(edges.contains(gmTest.getEdge(9, 2)));

        edges = gmTest.getAllEdgesFromIntersection(2, 0);
        assertTrue(edges.contains(gmTest.getEdge(2, 0)));
        assertTrue(edges.contains(gmTest.getEdge(2, 1)));
        assertTrue(edges.contains(gmTest.getEdge(1, 0)));
    }

    @Test
    public void testGetEdgesFromIntersectionObject() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        ArrayList<Edge> edges = gmTest.getAllEdgesFromIntersection(gmTest.getIntersection(0, 0));
        assertTrue(edges.contains(gmTest.getEdge(0, 0)));
        assertTrue(edges.contains(gmTest.getEdge(0, 1)));

        edges = gmTest.getAllEdgesFromIntersection(gmTest.getIntersection(11, 2));
        assertTrue(edges.contains(gmTest.getEdge(10, 4)));
        assertTrue(edges.contains(gmTest.getEdge(10, 5)));

        edges = gmTest.getAllEdgesFromIntersection(gmTest.getIntersection(6, 5));
        assertTrue(edges.contains(gmTest.getEdge(6, 9)));
        assertTrue(edges.contains(gmTest.getEdge(5, 5)));

        edges = gmTest.getAllEdgesFromIntersection(gmTest.getIntersection(3, 3));
        assertTrue(edges.contains(gmTest.getEdge(2, 5)));
        assertTrue(edges.contains(gmTest.getEdge(2, 6)));
        assertTrue(edges.contains(gmTest.getEdge(3, 3)));

        edges = gmTest.getAllEdgesFromIntersection(gmTest.getIntersection(10, 2));
        assertTrue(edges.contains(gmTest.getEdge(10, 4)));
        assertTrue(edges.contains(gmTest.getEdge(10, 3)));
        assertTrue(edges.contains(gmTest.getEdge(9, 2)));

        edges = gmTest.getAllEdgesFromIntersection(gmTest.getIntersection(2, 0));
        assertTrue(edges.contains(gmTest.getEdge(2, 0)));
        assertTrue(edges.contains(gmTest.getEdge(2, 1)));
        assertTrue(edges.contains(gmTest.getEdge(1, 0)));
    }

    @Test
    public void testGetEdgesFromIntersectionBadObject() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        try {
            gmTest.getAllEdgesFromIntersection(new Intersection());
            fail();
        }catch(InvalidIntersectionPositionException e) {}
    }

    @Test
    public void testSizeOfGetHexesFromIntersectionIndexes() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        ArrayList<Hex> hexes = gmTest.getAllHexesFromIntersection(0, 0);
        assertEquals(1, hexes.size());
        hexes = gmTest.getAllHexesFromIntersection(5, 5);
        assertEquals(1, hexes.size());
        hexes = gmTest.getAllHexesFromIntersection(11, 2);
        assertEquals(1, hexes.size());
        hexes = gmTest.getAllHexesFromIntersection(2, 3);
        assertEquals(2, hexes.size());
        hexes = gmTest.getAllHexesFromIntersection(7, 0);
        assertEquals(2, hexes.size());
        hexes = gmTest.getAllHexesFromIntersection(1, 2);
        assertEquals(2, hexes.size());
        hexes = gmTest.getAllHexesFromIntersection(9, 1);
        assertEquals(3, hexes.size());
        hexes = gmTest.getAllHexesFromIntersection(2, 1);
        assertEquals(3, hexes.size());

        try {
            hexes = gmTest.getAllHexesFromIntersection(-1, -1);
        } catch (InvalidIntersectionPositionException e) {
        }
    }

    @Test
    public void testGetHexesFromIntersectionIndexes() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        ArrayList<Hex> hexes = gmTest.getAllHexesFromIntersection(0, 0);
        assertTrue(hexes.contains(gmTest.getHex(0, 0)));

        hexes = gmTest.getAllHexesFromIntersection(5, 5);
        assertTrue(hexes.contains(gmTest.getHex(2, 4)));

        hexes = gmTest.getAllHexesFromIntersection(11, 2);
        assertTrue(hexes.contains(gmTest.getHex(4, 2)));

        hexes = gmTest.getAllHexesFromIntersection(7, 0);
        assertTrue(hexes.contains(gmTest.getHex(3, 0)));
        assertTrue(hexes.contains(gmTest.getHex(2, 0)));

        hexes = gmTest.getAllHexesFromIntersection(2, 3);
        assertTrue(hexes.contains(gmTest.getHex(1, 3)));
        assertTrue(hexes.contains(gmTest.getHex(0, 2)));

        hexes = gmTest.getAllHexesFromIntersection(2, 2);
        assertTrue(hexes.contains(gmTest.getHex(1, 2)));
        assertTrue(hexes.contains(gmTest.getHex(0, 1)));
        assertTrue(hexes.contains(gmTest.getHex(0, 2)));

        hexes = gmTest.getAllHexesFromIntersection(8, 3);
        assertTrue(hexes.contains(gmTest.getHex(4, 2)));
        assertTrue(hexes.contains(gmTest.getHex(3, 3)));
        assertTrue(hexes.contains(gmTest.getHex(3, 2)));

        hexes = gmTest.getAllHexesFromIntersection(3, 2);
        assertTrue(hexes.contains(gmTest.getHex(1, 1)));
        assertTrue(hexes.contains(gmTest.getHex(1, 2)));
        assertTrue(hexes.contains(gmTest.getHex(0, 1)));

        hexes = gmTest.getAllHexesFromIntersection(6, 4);
        assertTrue(hexes.contains(gmTest.getHex(3, 3)));
        assertTrue(hexes.contains(gmTest.getHex(2, 3)));
        assertTrue(hexes.contains(gmTest.getHex(2, 4)));
    }

    @Test
    public void testGetHexesFromIntersectionObject() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        ArrayList<Hex> hexes = gmTest.getAllHexesFromIntersection(gmTest.getIntersection(0, 0));
        assertTrue(hexes.contains(gmTest.getHex(0, 0)));

        hexes = gmTest.getAllHexesFromIntersection(gmTest.getIntersection(5, 5));
        assertTrue(hexes.contains(gmTest.getHex(2, 4)));

        hexes = gmTest.getAllHexesFromIntersection(gmTest.getIntersection(11, 2));
        assertTrue(hexes.contains(gmTest.getHex(4, 2)));

        hexes = gmTest.getAllHexesFromIntersection(gmTest.getIntersection(7, 0));
        assertTrue(hexes.contains(gmTest.getHex(3, 0)));
        assertTrue(hexes.contains(gmTest.getHex(2, 0)));

        hexes = gmTest.getAllHexesFromIntersection(gmTest.getIntersection(2, 3));
        assertTrue(hexes.contains(gmTest.getHex(1, 3)));
        assertTrue(hexes.contains(gmTest.getHex(0, 2)));

        hexes = gmTest.getAllHexesFromIntersection(gmTest.getIntersection(2, 2));
        assertTrue(hexes.contains(gmTest.getHex(1, 2)));
        assertTrue(hexes.contains(gmTest.getHex(0, 1)));
        assertTrue(hexes.contains(gmTest.getHex(0, 2)));

        hexes = gmTest.getAllHexesFromIntersection(gmTest.getIntersection(8, 3));
        assertTrue(hexes.contains(gmTest.getHex(4, 2)));
        assertTrue(hexes.contains(gmTest.getHex(3, 3)));
        assertTrue(hexes.contains(gmTest.getHex(3, 2)));

        hexes = gmTest.getAllHexesFromIntersection(gmTest.getIntersection(3, 2));
        assertTrue(hexes.contains(gmTest.getHex(1, 1)));
        assertTrue(hexes.contains(gmTest.getHex(1, 2)));
        assertTrue(hexes.contains(gmTest.getHex(0, 1)));

        hexes = gmTest.getAllHexesFromIntersection(gmTest.getIntersection(6, 4));
        assertTrue(hexes.contains(gmTest.getHex(3, 3)));
        assertTrue(hexes.contains(gmTest.getHex(2, 3)));
        assertTrue(hexes.contains(gmTest.getHex(2, 4)));
    }

    @Test
    public void testGetHexesFromIntersectionBadObject() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        try {
            gmTest.getAllHexesFromIntersection(new Intersection());
            fail();
        }catch(InvalidIntersectionPositionException e) {}
    }
}