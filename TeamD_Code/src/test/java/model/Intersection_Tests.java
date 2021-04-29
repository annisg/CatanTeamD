package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Intersection;
import model.PlayerColor;

public class Intersection_Tests {

    @Test
    public void testEmptyIntersection() {
        Intersection intersection = new Intersection();

        assertFalse(intersection.hasSettlement());
        assertFalse(intersection.hasCity());
        assertTrue(intersection.isEmpty());
    }

    @Test
    public void testWhiteSettlement() {
        Intersection intersection = new Intersection();
        intersection.setSettlement(PlayerColor.WHITE);

        assertTrue(intersection.hasSettlement());
        assertFalse(intersection.hasCity());
        assertEquals(PlayerColor.WHITE, intersection.getBuildingColor());
    }

    @Test
    public void testOrangeCity() {
        Intersection intersection = new Intersection();
        intersection.setCity(PlayerColor.ORANGE);

        assertFalse(intersection.hasSettlement());
        assertTrue(intersection.hasCity());
        assertEquals(PlayerColor.ORANGE, intersection.getBuildingColor());
    }

    @Test
    public void testPutCityOnSettlement() {
        Intersection intersection = new Intersection();
        intersection.setSettlement(PlayerColor.BLUE);

        assertTrue(intersection.hasSettlement());
        assertFalse(intersection.hasCity());
        assertEquals(PlayerColor.BLUE, intersection.getBuildingColor());

        intersection.setCity(PlayerColor.RED);

        assertFalse(intersection.hasSettlement());
        assertTrue(intersection.hasCity());
        assertEquals(PlayerColor.RED, intersection.getBuildingColor());
    }

    @Test
    public void testPutSettlementOnCity() {
        Intersection intersection = new Intersection();
        intersection.setCity(PlayerColor.RED);

        assertFalse(intersection.hasSettlement());
        assertTrue(intersection.hasCity());
        assertEquals(PlayerColor.RED, intersection.getBuildingColor());

        intersection.setSettlement(PlayerColor.BLUE);

        assertTrue(intersection.hasSettlement());
        assertFalse(intersection.hasCity());
        assertEquals(PlayerColor.BLUE, intersection.getBuildingColor());
    }

    @Test
    public void testClearSettlement() {

        Intersection intersection = new Intersection();
        intersection.setSettlement(PlayerColor.BLUE);

        assertTrue(intersection.hasSettlement());
        assertFalse(intersection.hasCity());
        assertEquals(PlayerColor.BLUE, intersection.getBuildingColor());

        intersection.setSettlement(PlayerColor.NONE);
        assertFalse(intersection.hasSettlement());
        assertFalse(intersection.hasCity());
        assertEquals(PlayerColor.NONE, intersection.getBuildingColor());

    }

    @Test
    public void testClearCity() {

        Intersection intersection = new Intersection();
        intersection.setCity(PlayerColor.BLUE);

        assertFalse(intersection.hasSettlement());
        assertTrue(intersection.hasCity());
        assertEquals(PlayerColor.BLUE, intersection.getBuildingColor());

        intersection.setCity(PlayerColor.NONE);
        assertFalse(intersection.hasSettlement());
        assertFalse(intersection.hasCity());
        assertEquals(PlayerColor.NONE, intersection.getBuildingColor());

    }

}
