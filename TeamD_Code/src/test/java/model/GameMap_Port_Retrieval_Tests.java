package model;

import static org.junit.Assert.*;

import org.junit.Test;

import exception.*;

public class GameMap_Port_Retrieval_Tests {

    @Test
    public void testGameMapHasPortMap() {
        GameMap testGM = new GameMap();
        testGM.setUpBeginnerMap(3);
        PortMap testPM = testGM.getPortMap();
        assertFalse(testPM == null);
    }
    
    @Test
    public void testGameMapHasAdvancedPortMap() {
        GameMap testGM = new GameMap();
        testGM.setUpAdvancedMap();
        PortMap testPM = testGM.getPortMap();
        assertFalse(testPM == null);
    }

    @Test
    public void testGetPortFromIntersectionPosition() {
        GameMap testGM = new GameMap();
        testGM.setUpBeginnerMap(3);
        MapPosition posToTest = new MapPosition(1, 0);
        assertEquals(testGM.getPortMap().getPortFromPosition(posToTest), testGM.getPortFromIntersection(posToTest));
    }
    
    @Test
    public void testGetPortFromBadIntersectionPosition() {
        GameMap testGM = new GameMap();
        testGM.setUpBeginnerMap(3);
        MapPosition posToTest = new MapPosition();
        try {
            testGM.getPortFromIntersection(posToTest);
            fail();
        }catch(InvalidPortPositionException e) {}
    }
    
    @Test
    public void testGetPortFromIntersectionObject() {
        GameMap testGM = new GameMap();
        testGM.setUpBeginnerMap(3);
        Intersection exampleIntersection = testGM.getIntersection(1, 0);
        assertEquals(testGM.getPortMap().getPortFromPosition(new MapPosition(1, 0)), testGM.getPortFromIntersection(exampleIntersection));
    }
    
    @Test
    public void testGetPortFromBadIntersectionObject() {
        GameMap testGM = new GameMap();
        testGM.setUpBeginnerMap(3);
        try {
            testGM.getPortFromIntersection(new Intersection());
            fail();
        }catch(InvalidIntersectionPositionException e) {}
    }
}
