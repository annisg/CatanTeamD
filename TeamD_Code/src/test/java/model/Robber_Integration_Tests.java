package model;

import static org.junit.Assert.*;

import org.junit.Test;

import exception.*;

import java.awt.*;

public class Robber_Integration_Tests {

    @Test
    public void testMoveRobberFromHexMap() {
        HexMap testHM = new HexMap();
        testHM.setUpBeginnerMap();
        testHM.moveRobberToPosition(new MapPosition(1, 1));
        assertTrue(testHM.getHex(new MapPosition(1, 1)).hasRobber);
        assertFalse(testHM.getHex(new MapPosition(2, 2)).hasRobber);
    }
    
    @Test
    public void testMoveRobberFromHexMapBadPosition() {
        HexMap testHM = new HexMap();
        testHM.setUpBeginnerMap();
        try {
            testHM.moveRobberToPosition(new MapPosition(-1, 0));
            fail();
        }catch(InvalidHexPositionException e) {}
    }
    
    @Test
    public void testMoveRobberFromHexMapSameRobberPosition() {
        HexMap testHM = new HexMap();
        testHM.setUpBeginnerMap();
        try {
            testHM.moveRobberToPosition(new MapPosition(2, 2));
            fail();
        }catch(IllegalRobberMoveException e) {
            assertTrue(testHM.getHex(new MapPosition(2, 2)).hasRobber);
        }
    }

    @Test
    public void testMoveRobberFromGameMap() {
        GameMap testGM = new GameMap();
        testGM.setUpBeginnerMap(3);
        testGM.moveRobberToClosestHex(new Point(0, 0));
        assertTrue(testGM.getHex(4, 0).hasRobber);
        assertFalse(testGM.getHex(2, 2).hasRobber);
    }
}
