package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import exception.*;

public class GameMap_Direction_Retrieval_Tests {

    @Test
    public void getSpecificIntersectionFromHexTest() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        Intersection testIntersection = gmTest.getSpecificIntersectionFromHex(gmTest.getHex(0, 0), Direction.ZERO);
        assertEquals(gmTest.getIntersection(3, 1), testIntersection);
        testIntersection = gmTest.getSpecificIntersectionFromHex(gmTest.getHex(0, 0), Direction.ONE);
        assertEquals(gmTest.getIntersection(2, 1), testIntersection);
        testIntersection = gmTest.getSpecificIntersectionFromHex(gmTest.getHex(0, 0), Direction.TWO);
        assertEquals(gmTest.getIntersection(1, 1), testIntersection);
        testIntersection = gmTest.getSpecificIntersectionFromHex(gmTest.getHex(0, 0), Direction.THREE);
        assertEquals(gmTest.getIntersection(0, 0), testIntersection);
        testIntersection = gmTest.getSpecificIntersectionFromHex(gmTest.getHex(0, 0), Direction.FOUR);
        assertEquals(gmTest.getIntersection(1, 0), testIntersection);
        testIntersection = gmTest.getSpecificIntersectionFromHex(gmTest.getHex(0, 0), Direction.FIVE);
        assertEquals(gmTest.getIntersection(2, 0), testIntersection);
    }

    @Test
    public void getSpecificIntersectionFromBadHexTest() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        try {
            Intersection testIntersection = gmTest.getSpecificIntersectionFromHex(new Hex(), Direction.ZERO);
            fail("did not throw IAE");
        } catch (InvalidHexPositionException e) {}
    }

    @Test
    public void getSpecificIntersectionFromEdge() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        Intersection testIntersection = gmTest.getSpecificIntersectionFromEdge(gmTest.getEdge(0, 0), Direction.ZERO);
        assertEquals(gmTest.getIntersection(1, 0), testIntersection);
        testIntersection = gmTest.getSpecificIntersectionFromEdge(gmTest.getEdge(0, 0), Direction.ONE);
        assertEquals(gmTest.getIntersection(0, 0), testIntersection);
        testIntersection = gmTest.getSpecificIntersectionFromEdge(gmTest.getEdge(10, 0), Direction.ZERO);
        assertEquals(gmTest.getIntersection(11, 0), testIntersection);
        testIntersection = gmTest.getSpecificIntersectionFromEdge(gmTest.getEdge(10, 0), Direction.ONE);
        assertEquals(gmTest.getIntersection(10, 0), testIntersection);
        testIntersection = gmTest.getSpecificIntersectionFromEdge(gmTest.getEdge(5, 5), Direction.ZERO);
        assertEquals(gmTest.getIntersection(6, 5), testIntersection);
        testIntersection = gmTest.getSpecificIntersectionFromEdge(gmTest.getEdge(5, 5), Direction.ONE);
        assertEquals(gmTest.getIntersection(5, 5), testIntersection);
    }

    @Test
    public void getSpecificIntersectionFromBadEdgeTest() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        try {
            Intersection testIntersection = gmTest.getSpecificIntersectionFromEdge(new Edge(), Direction.ZERO);
            fail("did not throw IAE");
        } catch (InvalidEdgePositionException e) {
        }
    }

    @Test
    public void getSpecificIntersectionFromEdgeBadDirection() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        edgeToIntersectionBadDirection(gmTest, Direction.TWO);
        edgeToIntersectionBadDirection(gmTest, Direction.THREE);
        edgeToIntersectionBadDirection(gmTest, Direction.FOUR);
        edgeToIntersectionBadDirection(gmTest, Direction.FIVE);
    }

    public void edgeToIntersectionBadDirection(GameMap gmTest, Direction badDirection) {
        try {
            Intersection testIntersection = gmTest.getSpecificIntersectionFromEdge(gmTest.getEdge(0, 0), badDirection);
            fail("did not throw IAE");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testGetSpecificEdgesFromIntersection() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        Edge testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(0, 0), Direction.ZERO);
        assertEquals(gmTest.getEdge(0, 1), testEdge);
        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(0, 0), Direction.TWO);
        assertEquals(gmTest.getEdge(0, 0), testEdge);

        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(11, 2), Direction.ONE);
        assertEquals(gmTest.getEdge(10, 5), testEdge);
        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(11, 2), Direction.TWO);
        assertEquals(gmTest.getEdge(10, 4), testEdge);

        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(6, 5), Direction.ONE);
        assertEquals(gmTest.getEdge(5, 5), testEdge);
        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(6, 5), Direction.TWO);
        assertEquals(gmTest.getEdge(6, 9), testEdge);

        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(3, 3), Direction.ZERO);
        assertEquals(gmTest.getEdge(3, 3), testEdge);
        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(3, 3), Direction.ONE);
        assertEquals(gmTest.getEdge(2, 6), testEdge);
        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(3, 3), Direction.TWO);
        assertEquals(gmTest.getEdge(2, 5), testEdge);

        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(10, 2), Direction.ZERO);
        assertEquals(gmTest.getEdge(10, 4), testEdge);
        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(10, 2), Direction.ONE);
        assertEquals(gmTest.getEdge(9, 2), testEdge);
        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(10, 2), Direction.TWO);
        assertEquals(gmTest.getEdge(10, 3), testEdge);

        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(2, 0), Direction.ZERO);
        assertEquals(gmTest.getEdge(2, 1), testEdge);
        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(2, 0), Direction.ONE);
        assertEquals(gmTest.getEdge(1, 0), testEdge);
        testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(2, 0), Direction.TWO);
        assertEquals(gmTest.getEdge(2, 0), testEdge);
    }

    @Test
    public void getSpecificEdgeFromBadIntersectionTest() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        try {
            Intersection testIntersection = gmTest.getSpecificIntersectionFromEdge(new Edge(), Direction.ZERO);
            fail("did not throw IAE");
        } catch (InvalidEdgePositionException e) {
        }
    }

    @Test
    public void getSpecificEdgeFromIntersectionBadDirection() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        intersectionToEdgeBadDirection(gmTest, Direction.THREE);
        intersectionToEdgeBadDirection(gmTest, Direction.FOUR);
        intersectionToEdgeBadDirection(gmTest, Direction.FIVE);
    }

    @Test
    public void getSpecificEdgeFromNotFullIntersection() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        intersectionToEdgeBadDirection(gmTest, gmTest.getIntersection(0, 0), Direction.ONE);
        intersectionToEdgeBadDirection(gmTest, gmTest.getIntersection(1, 0), Direction.TWO);
        intersectionToEdgeBadDirection(gmTest, gmTest.getIntersection(1, 3), Direction.ONE);
        intersectionToEdgeBadDirection(gmTest, gmTest.getIntersection(10, 0), Direction.TWO);
        intersectionToEdgeBadDirection(gmTest, gmTest.getIntersection(11, 2), Direction.ZERO);
        intersectionToEdgeBadDirection(gmTest, gmTest.getIntersection(10, 3), Direction.ZERO);
    }

    public void intersectionToEdgeBadDirection(GameMap gmTest, Direction badDirection) {
        try {
            Edge testEdge = gmTest.getSpecificEdgeFromIntersection(gmTest.getIntersection(5, 3), badDirection);
            fail("did not throw exception");
        } catch (IllegalArgumentException e) {
        }
    }

    public void intersectionToEdgeBadDirection(GameMap gmTest, Intersection givenIntersection, Direction badDirection) {
        try {
            Edge testEdge = gmTest.getSpecificEdgeFromIntersection(givenIntersection, badDirection);
            fail("did not throw excpetion");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testGetSpecificHexeFromIntersection() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        Hex testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(0, 0), Direction.ZERO);
        assertEquals(gmTest.getHex(0, 0), testHex);

        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(5, 5), Direction.TWO);
        assertEquals(gmTest.getHex(2, 4), testHex);

        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(11, 2), Direction.ONE);
        assertEquals(gmTest.getHex(4, 2), testHex);

        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(7, 0), Direction.ZERO);
        assertEquals(gmTest.getHex(3, 0), testHex);
        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(7, 0), Direction.ONE);
        assertEquals(gmTest.getHex(2, 0), testHex);

        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(2, 3), Direction.ZERO);
        assertEquals(gmTest.getHex(1, 3), testHex);
        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(2, 3), Direction.TWO);
        assertEquals(gmTest.getHex(0, 2), testHex);

        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(2, 2), Direction.ZERO);
        assertEquals(gmTest.getHex(1, 2), testHex);
        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(2, 2), Direction.TWO);
        assertEquals(gmTest.getHex(0, 1), testHex);
        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(2, 2), Direction.ONE);
        assertEquals(gmTest.getHex(0, 2), testHex);

        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(8, 3), Direction.ZERO);
        assertEquals(gmTest.getHex(4, 2), testHex);
        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(8, 3), Direction.ONE);
        assertEquals(gmTest.getHex(3, 3), testHex);
        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(8, 3), Direction.TWO);
        assertEquals(gmTest.getHex(3, 2), testHex);

        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(3, 2), Direction.TWO);
        assertEquals(gmTest.getHex(1, 1), testHex);
        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(3, 2), Direction.ZERO);
        assertEquals(gmTest.getHex(1, 2), testHex);
        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(3, 2), Direction.ONE);
        assertEquals(gmTest.getHex(0, 1), testHex);

        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(6, 4), Direction.ZERO);
        assertEquals(gmTest.getHex(3, 3), testHex);
        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(6, 4), Direction.TWO);
        assertEquals(gmTest.getHex(2, 3), testHex);
        testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(6, 4), Direction.ONE);
        assertEquals(gmTest.getHex(2, 4), testHex);
    }

    @Test
    public void getSpecificHexFromIntersectionBadDirection() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        intersectionToHexBadDirection(gmTest, Direction.THREE);
        intersectionToHexBadDirection(gmTest, Direction.FOUR);
        intersectionToHexBadDirection(gmTest, Direction.FIVE);
    }

    public void intersectionToHexBadDirection(GameMap gmTest, Direction badDirection) {
        try {
            Hex testHex = gmTest.getSpecificHexFromIntersection(gmTest.getIntersection(5, 3), badDirection);
            fail("did not throw exception");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void getSpecificHexFromNotFullIntersection() {
        GameMap gmTest = new GameMap();
        gmTest.setUpBeginnerMap(3);
        intersectionToHexBadDirection(gmTest, gmTest.getIntersection(0, 0), Direction.ONE);
        intersectionToHexBadDirection(gmTest, gmTest.getIntersection(0, 0), Direction.TWO);
        intersectionToHexBadDirection(gmTest, gmTest.getIntersection(5, 5), Direction.ONE);
        intersectionToHexBadDirection(gmTest, gmTest.getIntersection(5, 5), Direction.ZERO);
        intersectionToHexBadDirection(gmTest, gmTest.getIntersection(11, 2), Direction.ZERO);
        intersectionToHexBadDirection(gmTest, gmTest.getIntersection(11, 2), Direction.TWO);
        intersectionToHexBadDirection(gmTest, gmTest.getIntersection(7, 0), Direction.TWO);
        intersectionToHexBadDirection(gmTest, gmTest.getIntersection(2, 3), Direction.ONE);
    }

    public void intersectionToHexBadDirection(GameMap gmTest, Intersection givenIntersection, Direction badDirection) {
        try {
            Hex testHex = gmTest.getSpecificHexFromIntersection(givenIntersection, badDirection);
            fail("did not throw IAE");
        } catch (IllegalArgumentException e) {
        }
    }
}
