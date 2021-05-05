package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.GenericPort;
import model.Hex;
import model.Port;
import model.Resource;
import model.SpecificPort;

public class Port_Tests {

    @Test
    public void testIllegalSpecificPort() {
        try {
            Port testPort = new SpecificPort(Resource.DESERT);
            fail("Expected IAE");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot have a desert port.", e.getMessage());
        }
    }

    @Test
    public void testTradeRatioGenericLegalResources() {
        Port testPort = new GenericPort();
        assertEquals(3, testPort.tradeRatioXto1ForResource(Resource.BRICK));
        assertEquals(3, testPort.tradeRatioXto1ForResource(Resource.GRAIN));
        assertEquals(3, testPort.tradeRatioXto1ForResource(Resource.LUMBER));
        assertEquals(3, testPort.tradeRatioXto1ForResource(Resource.ORE));
        assertEquals(3, testPort.tradeRatioXto1ForResource(Resource.WOOL));
    }

    @Test
    public void testTradeRatioGenericIllegalResource() {
        Port testPort = new GenericPort();
        assertEquals(Integer.MAX_VALUE, testPort.tradeRatioXto1ForResource(Resource.DESERT));
    }

    @Test
    public void testTradeRatioSpecificLegalResources() {
        Port testPort = new SpecificPort(Resource.LUMBER);
        assertEquals(4, testPort.tradeRatioXto1ForResource(Resource.BRICK));
        assertEquals(4, testPort.tradeRatioXto1ForResource(Resource.GRAIN));
        assertEquals(2, testPort.tradeRatioXto1ForResource(Resource.LUMBER));
        assertEquals(4, testPort.tradeRatioXto1ForResource(Resource.ORE));
        assertEquals(4, testPort.tradeRatioXto1ForResource(Resource.WOOL));

        testPort = new SpecificPort(Resource.ORE);
        assertEquals(4, testPort.tradeRatioXto1ForResource(Resource.BRICK));
        assertEquals(4, testPort.tradeRatioXto1ForResource(Resource.GRAIN));
        assertEquals(4, testPort.tradeRatioXto1ForResource(Resource.LUMBER));
        assertEquals(2, testPort.tradeRatioXto1ForResource(Resource.ORE));
        assertEquals(4, testPort.tradeRatioXto1ForResource(Resource.WOOL));
    }

    @Test
    public void testTradeRatioSpecificIllegalResource() {
        Port testPort = new SpecificPort(Resource.BRICK);
        try {
            testPort.tradeRatioXto1ForResource(Resource.DESERT);
            fail();
        } catch(IllegalArgumentException e) {}
    }

}
