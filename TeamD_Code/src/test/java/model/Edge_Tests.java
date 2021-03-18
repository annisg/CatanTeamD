package model;

import static org.junit.Assert.*;

import org.junit.Test;
import model.Edge;
import model.PlayerColor;

public class Edge_Tests {

    @Test
    public void testEmptyEdge() {
        Edge edge = new Edge();
        assertFalse(edge.hasRoad());
        assertEquals(PlayerColor.NONE, edge.getRoadColor());
    }

    @Test
    public void testRedRoad() {
        Edge edge = new Edge();
        edge.setRoad(PlayerColor.RED);

        assertTrue(edge.hasRoad());
        assertEquals(PlayerColor.RED, edge.getRoadColor());
    }

    @Test
    public void testClearEdge() {
        Edge edge = new Edge();
        edge.setRoad(PlayerColor.BLUE);

        assertTrue(edge.hasRoad());
        assertEquals(PlayerColor.BLUE, edge.getRoadColor());

        edge.setRoad(PlayerColor.NONE);

        assertFalse(edge.hasRoad());
        assertEquals(PlayerColor.NONE, edge.getRoadColor());

    }

}
