package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exception.*;

public class Hex_Tests {

    @Test
    public void testHexReturnsCorrectResource() {
        Hex testHex = new Hex(Resource.ORE, 6);
        assertEquals(Resource.ORE, testHex.getResource());

        testHex = new Hex(Resource.BRICK, 6);
        assertEquals(Resource.BRICK, testHex.getResource());

        testHex = new Hex(Resource.GRAIN, 6);
        assertEquals(Resource.GRAIN, testHex.getResource());

        testHex = new Hex(Resource.LUMBER, 6);
        assertEquals(Resource.LUMBER, testHex.getResource());

        testHex = new Hex(Resource.WOOL, 6);
        assertEquals(Resource.WOOL, testHex.getResource());

        testHex = new Hex();
        assertEquals(Resource.DESERT, testHex.getResource());
    }

    @Test
    public void testHexReturnsCorrectResourceNumber() {
        Hex testHex = new Hex(Resource.ORE, 2);
        assertEquals(2, testHex.getRollResourceNumber());

        testHex = new Hex(Resource.BRICK, 6);
        assertEquals(6, testHex.getRollResourceNumber());

        testHex = new Hex(Resource.ORE, 8);
        assertEquals(8, testHex.getRollResourceNumber());

        testHex = new Hex(Resource.BRICK, 12);
        assertEquals(12, testHex.getRollResourceNumber());
    }

    @Test
    public void testHexWhenTryingToGetResourceNumberFromDesert() {
        Hex testHex = new Hex();
        assertEquals(0, testHex.getRollResourceNumber());
    }

    @Test
    public void testHexTryingToAddDesertWrongWay() {
        try {
            Hex testHex = new Hex(Resource.DESERT, 6);
            fail("Expected IAE");
        } catch (IllegalArgumentException e) {
            assertEquals("Use the single arg hex constructor for the desert.", e.getMessage());
        }
    }

    @Test
    public void testHexFailsOnBadResourceNumbers() {
        errorOnBadResourceNumber(1, "Number must be between 2 and 12 inclusive but not 7.");
        errorOnBadResourceNumber(7, "Number must be between 2 and 12 inclusive but not 7.");
        errorOnBadResourceNumber(13, "Number must be between 2 and 12 inclusive but not 7.");
    }

    private void errorOnBadResourceNumber(int illegalNumber, String msg) {
        try {
            Hex testHex = new Hex(Resource.BRICK, illegalNumber);
            fail("Expected IAE");
        } catch (IllegalArgumentException e) {}
    }
    
    @Test
    public void testHasRobber() {
        Hex desert = new Hex();
        assertTrue(desert.hasRobber());
        
        Hex nonDesert = new Hex(Resource.BRICK, 4);
        assertFalse(nonDesert.hasRobber());
    }
}
