package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class Hex_Private_Tests {

    @Test
    public void testInitialRobberStatus() {
        Hex testHex = new Hex(Resource.BRICK, 3);
        assertFalse(testHex.hasRobber);

        testHex = new Hex();
        assertTrue(testHex.hasRobber);
    }

    @Test
    public void testRob() {
        Hex testHex = new Hex(Resource.BRICK, 4);
        assertFalse(testHex.hasRobber);
        testHex.placeRobber();
        assertTrue(testHex.hasRobber);
        testHex.placeRobber();
        assertTrue(testHex.hasRobber);
    }

    @Test
    public void testStopRobbing() {
        Hex testHex = new Hex(Resource.BRICK, 4);
        assertFalse(testHex.hasRobber);
        testHex.removeRobber();
        assertFalse(testHex.hasRobber);
        testHex.hasRobber = true;
        testHex.removeRobber();
        assertFalse(testHex.hasRobber);
    }

    @Test
    public void getResourceWhileBeingRobbed() {
        Hex testHex = new Hex();
        testHex.hasRobber = true;
        assertEquals(Resource.DESERT, testHex.getResource());

        testHex = new Hex(Resource.BRICK, 3);
        testHex.hasRobber = true;
        assertEquals(Resource.BRICK, testHex.getResource());
    }

    // integration tests for robber and hexes
    @Test
    public void testMoveRobberWithHexes() {
        Hex desert = new Hex();
        Robber testRobber = new Robber(desert);
        assertTrue(desert.hasRobber);

        Hex nonDesert = new Hex(Resource.LUMBER, 5);
        assertFalse(nonDesert.hasRobber);

        testRobber.moveRobberTo(nonDesert);
        assertTrue(nonDesert.hasRobber);
        assertFalse(desert.hasRobber);
    }

}
