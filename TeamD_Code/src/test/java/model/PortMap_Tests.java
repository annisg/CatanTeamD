package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;

import exception.*;
import model.*;

public class PortMap_Tests {
    
    @Test
    public void testSizeOfMap() {
        PortMap testPortMap = new PortMap();
        assertEquals(9, testPortMap.getNumberOfPorts());
        assertEquals(18, testPortMap.getNumberOfPortIntersections());
        assertEquals(9, testPortMap.getAllPorts().size());
        assertEquals(18, testPortMap.positionsToPorts.size());
    }
    
    @Test
    public void testMapContainsAllPorts() {
        PortMap testPortMap = new PortMap();
        for(Object currentPort : testPortMap.getAllPorts()) {
            assertEquals(Port.class, currentPort.getClass().getInterfaces()[0]);
        }
    }

    @Test
    public void testMapContainsCorrectPorts() {
        PortMap testPortMap = new PortMap();
        Set<Port> allPorts = testPortMap.getAllPorts();
        hashMapKeySetContainsAndRemoveGenericPorts(allPorts);
        hashMapKeySetContainsAndRemoveGenericPorts(allPorts);
        hashMapKeySetContainsAndRemoveGenericPorts(allPorts);
        hashMapKeySetContainsAndRemoveGenericPorts(allPorts);
        hashMapKeySetContainsAndRemoveSpecificPorts(allPorts, Resource.BRICK);
        hashMapKeySetContainsAndRemoveSpecificPorts(allPorts, Resource.GRAIN);
        hashMapKeySetContainsAndRemoveSpecificPorts(allPorts, Resource.LUMBER);
        hashMapKeySetContainsAndRemoveSpecificPorts(allPorts, Resource.ORE);
        hashMapKeySetContainsAndRemoveSpecificPorts(allPorts, Resource.WOOL);
        assertTrue(allPorts.isEmpty());
        
    }
    
    private void hashMapKeySetContainsAndRemoveGenericPorts(Set<Port> ports) {
        for(Port currentPort : ports) {
            if(currentPort.getClass().equals(GenericPort.class)) {
                ports.remove(currentPort);
                return;
            }
        }
        fail("Could not find port matching class");
    }
    
    private void hashMapKeySetContainsAndRemoveSpecificPorts(Set<Port> ports, Resource resourceToCheck) {
        for(Port currentPort : ports) {
            if(currentPort.getClass().equals(SpecificPort.class) && ((SpecificPort)currentPort).portResource == resourceToCheck) {
                ports.remove(currentPort);
                return;
            }
        }
        fail("Could not find port matching resource");
    }
    
    @Test
    public void testMapContainsCorrectMapPositions() {
        PortMap testPortMap = new PortMap();
        try {
            testPortMap.getPortFromPosition(new MapPosition(10, 0));
            testPortMap.getPortFromPosition(new MapPosition(11, 0));
            testPortMap.getPortFromPosition(new MapPosition(11, 1));
            testPortMap.getPortFromPosition(new MapPosition(10, 2));
            testPortMap.getPortFromPosition(new MapPosition(9, 3));
            testPortMap.getPortFromPosition(new MapPosition(8, 4));
            testPortMap.getPortFromPosition(new MapPosition(6, 5));
            testPortMap.getPortFromPosition(new MapPosition(5, 5));
            testPortMap.getPortFromPosition(new MapPosition(3, 4));
            testPortMap.getPortFromPosition(new MapPosition(2, 3));
            testPortMap.getPortFromPosition(new MapPosition(0, 1));
            testPortMap.getPortFromPosition(new MapPosition(1, 2));
            testPortMap.getPortFromPosition(new MapPosition(1, 0));
            testPortMap.getPortFromPosition(new MapPosition(0, 0));
            testPortMap.getPortFromPosition(new MapPosition(4, 0));
            testPortMap.getPortFromPosition(new MapPosition(3, 0));
            testPortMap.getPortFromPosition(new MapPosition(8, 0));
            testPortMap.getPortFromPosition(new MapPosition(7, 0));
        } catch (InvalidPortPositionException e) {
            fail();
        }
    }
    
    @Test
    public void testBasicSetupMapsPositionsToCorrectPorts() {
        PortMap testPortMap = new PortMap();
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(10, 0)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(11, 0)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(1, 0)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(0, 0)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(0, 1)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(1, 2)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(6, 5)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(5, 5)).getClass());
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(4, 0)), Resource.BRICK);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(3, 0)), Resource.BRICK);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(11, 1)), Resource.GRAIN);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(10, 2)), Resource.GRAIN);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(8, 0)), Resource.LUMBER);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(7, 0)), Resource.LUMBER);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(9, 3)), Resource.ORE);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(8, 4)), Resource.ORE);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(3, 4)), Resource.WOOL);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(2, 3)), Resource.WOOL);
    }
    
    private void testSpecificPortIsCorrectResource(Port port, Resource resource) {
        if(!port.getClass().equals(SpecificPort.class) || ((SpecificPort)port).portResource != resource) {
            fail();
        }
    }
    
    @Test
    public void testAdvancedSetupMapsPositionsToPorts() {
        Random rand = new Random(0);
        PortMap testPortMap = new PortMap(rand);
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(10, 0)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(11, 0)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(0, 1)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(1, 2)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(6, 5)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(5, 5)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(11, 1)).getClass());
        assertEquals(GenericPort.class, testPortMap.getPortFromPosition(new MapPosition(10, 2)).getClass());
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(1, 0)), Resource.WOOL);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(0, 0)), Resource.WOOL);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(8, 0)), Resource.LUMBER);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(7, 0)), Resource.LUMBER);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(9, 3)), Resource.GRAIN);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(8, 4)), Resource.GRAIN);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(2, 3)), Resource.BRICK);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(3, 4)), Resource.BRICK);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(4, 0)), Resource.ORE);
        testSpecificPortIsCorrectResource(testPortMap.getPortFromPosition(new MapPosition(3, 0)), Resource.ORE);
    }
    
    @Test
    public void testGetPortFromBadPosition() {
        PortMap testPortMap = new PortMap();
        try {
            testPortMap.getPortFromPosition(new MapPosition(0, 2));
            fail();
        } catch (InvalidPortPositionException e) {}
        
        try {
            testPortMap.getPortFromPosition(new MapPosition(-1, 3));
            fail();
        } catch (InvalidPortPositionException e) {}
    }
    
}
