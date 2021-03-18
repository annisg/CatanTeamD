package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.Test;

import exception.*;

public class HexMap_Retrieval_Tests {
    
    @Test
    public void testGetNumberOfRows() {
        HexMap testHexMap = new HexMap();
        assertEquals(5, testHexMap.getNumberOfRows());
    }
    
    @Test
    public void testGetNumberOfHexesPerGoodRow() {
        HexMap testHexMap = new HexMap();
        assertEquals(3, testHexMap.getNumberOfHexesInRow(0));
        assertEquals(4, testHexMap.getNumberOfHexesInRow(1));
        assertEquals(5, testHexMap.getNumberOfHexesInRow(2));
        assertEquals(4, testHexMap.getNumberOfHexesInRow(3));
        assertEquals(3, testHexMap.getNumberOfHexesInRow(4));
    }
    
    @Test
    public void testGetNumberOfHexesPerBadRow() {
        HexMap testHexMap = new HexMap();
        try {
            testHexMap.getNumberOfHexesInRow(-1);
            fail();
        }catch(InvalidHexPositionException e) {}
        try {
            testHexMap.getNumberOfHexesInRow(5);
            fail();
        }catch(InvalidHexPositionException e) {}
    }
    
    @Test
    public void testGetIntersectionDirection0GoodPosition() {
        HexMap testHexMap = new HexMap();
        assertTrue(testHexMap.getIntersectionDirection0(new MapPosition(0, 0)).equals(new MapPosition(3, 1)));
        assertTrue(testHexMap.getIntersectionDirection0(new MapPosition(1, 3)).equals(new MapPosition(5, 4)));
        assertTrue(testHexMap.getIntersectionDirection0(new MapPosition(2, 4)).equals(new MapPosition(7, 4)));
        assertTrue(testHexMap.getIntersectionDirection0(new MapPosition(4, 2)).equals(new MapPosition(11, 2)));
    }
    
    @Test
    public void testGetIntersectionDirection0BadPosition() {
        HexMap testHexMap = new HexMap();
        try {
            testHexMap.getIntersectionDirection0(new MapPosition());
            fail();
        } catch(InvalidHexPositionException e) {}
    }
    
    @Test
    public void testGetIntersectionDirection1GoodPosition() {
        HexMap testHexMap = new HexMap();
        assertTrue(testHexMap.getIntersectionDirection1(new MapPosition(0, 0)).equals(new MapPosition(2, 1)));
        assertTrue(testHexMap.getIntersectionDirection1(new MapPosition(4, 2)).equals(new MapPosition(10, 3)));
    }
    
    @Test
    public void testGetIntersectionDirection1BadPosition() {
        HexMap testHexMap = new HexMap();
        try {
            testHexMap.getIntersectionDirection1(new MapPosition());
            fail();
        } catch(InvalidHexPositionException e) {}
    }
    
    @Test
    public void testGetIntersectionDirection2GoodPosition() {
        HexMap testHexMap = new HexMap();
        assertTrue(testHexMap.getIntersectionDirection2(new MapPosition(0, 0)).equals(new MapPosition(1, 1)));
        assertTrue(testHexMap.getIntersectionDirection2(new MapPosition(4, 2)).equals(new MapPosition(9, 3)));
    }
    
    @Test
    public void testGetIntersectionDirection2BadPosition() {
        HexMap testHexMap = new HexMap();
        try {
            testHexMap.getIntersectionDirection2(new MapPosition());
            fail();
        } catch(InvalidHexPositionException e) {}
    }
    
    @Test
    public void testGetIntersectionDirection3GoodPosition() {
        HexMap testHexMap = new HexMap();
        assertTrue(testHexMap.getIntersectionDirection3(new MapPosition(0, 0)).equals(new MapPosition(0, 0)));
        assertTrue(testHexMap.getIntersectionDirection3(new MapPosition(2, 4)).equals(new MapPosition(4, 4)));
        assertTrue(testHexMap.getIntersectionDirection3(new MapPosition(3, 3)).equals(new MapPosition(6, 4)));
        assertTrue(testHexMap.getIntersectionDirection3(new MapPosition(4, 2)).equals(new MapPosition(8, 3)));
    }
    
    @Test
    public void testGetIntersectionDirection3BadPosition() {
        HexMap testHexMap = new HexMap();
        try {
            testHexMap.getIntersectionDirection3(new MapPosition());
            fail();
        } catch(InvalidHexPositionException e) {}
    }
    
    @Test
    public void testGetIntersectionDirection4GoodPosition() {
        HexMap testHexMap = new HexMap();
        assertTrue(testHexMap.getIntersectionDirection4(new MapPosition(0, 0)).equals(new MapPosition(1, 0)));
        assertTrue(testHexMap.getIntersectionDirection4(new MapPosition(4, 2)).equals(new MapPosition(9, 2)));
    }
    
    @Test
    public void testGetIntersectionDirection4BadPosition() {
        HexMap testHexMap = new HexMap();
        try {
            testHexMap.getIntersectionDirection4(new MapPosition());
            fail();
        } catch(InvalidHexPositionException e) {}
    }
    
    @Test
    public void testGetIntersectionDirection5GoodPosition() {
        HexMap testHexMap = new HexMap();
        assertTrue(testHexMap.getIntersectionDirection5(new MapPosition(0, 0)).equals(new MapPosition(2, 0)));
        assertTrue(testHexMap.getIntersectionDirection5(new MapPosition(4, 2)).equals(new MapPosition(10, 2)));
    }
    
    @Test
    public void testGetIntersectionDirection5BadPosition() {
        HexMap testHexMap = new HexMap();
        try {
            testHexMap.getIntersectionDirection5(new MapPosition());
            fail();
        } catch(InvalidHexPositionException e) {}
    }
    
    @Test
    public void testGetIntersectionAnyDirectionGoodPosition() {
        HexMap mockedMap = EasyMock.partialMockBuilder(HexMap.class)
                           .addMockedMethod("getIntersectionDirection0")
                           .addMockedMethod("getIntersectionDirection1")
                           .addMockedMethod("getIntersectionDirection2")
                           .addMockedMethod("getIntersectionDirection3")
                           .addMockedMethod("getIntersectionDirection4")
                           .addMockedMethod("getIntersectionDirection5")
                           .createMock();
        MapPosition hexPos = new MapPosition(0, 0);
        EasyMock.expect(mockedMap.getIntersectionDirection0(hexPos)).andReturn(new MapPosition(3, 1));
        EasyMock.expect(mockedMap.getIntersectionDirection1(hexPos)).andReturn(new MapPosition(2, 1));
        EasyMock.expect(mockedMap.getIntersectionDirection2(hexPos)).andReturn(new MapPosition(1, 1));
        EasyMock.expect(mockedMap.getIntersectionDirection3(hexPos)).andReturn(new MapPosition(0, 0));
        EasyMock.expect(mockedMap.getIntersectionDirection4(hexPos)).andReturn(new MapPosition(1, 0));
        EasyMock.expect(mockedMap.getIntersectionDirection5(hexPos)).andReturn(new MapPosition(2, 0));
        EasyMock.replay(mockedMap);
        
        mockedMap.getAdjacentIntersection(hexPos, Direction.ZERO);
        mockedMap.getAdjacentIntersection(hexPos, Direction.ONE);
        mockedMap.getAdjacentIntersection(hexPos, Direction.TWO);
        mockedMap.getAdjacentIntersection(hexPos, Direction.THREE);
        mockedMap.getAdjacentIntersection(hexPos, Direction.FOUR);
        mockedMap.getAdjacentIntersection(hexPos, Direction.FIVE);
        
        EasyMock.verify(mockedMap);
    }
    
    @Test
    public void testGetIntersectionAnyDirectionBadPosition() {
        HexMap mockedMap = new HexMap();
        try {
            mockedMap.getAdjacentIntersection(new MapPosition(), Direction.ZERO);
            fail();
        } catch (InvalidHexPositionException e) {}
    }
    
    @Test
    public void testFindHex() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();
        for(int i=0; i<5; i++) {
            assertTrue(testHexMap.findHexPosition(testHexMap.getHex(new MapPosition(i, 0))).equals(new MapPosition(i, 0)));
            int endIndex = 4 - Math.abs(2-i);
            assertTrue(testHexMap.findHexPosition(testHexMap.getHex(new MapPosition(i, endIndex))).equals(new MapPosition(i, endIndex)));
        }
    }
    
    @Test
    public void testFindBadHex() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();
        try {
            testHexMap.findHexPosition(new Hex());
            fail();
        }catch(InvalidHexPositionException e) {}
    }
    
    @Test
    public void testGetIntersectionAnyDirectionGoodObject() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();
        Hex testHex = testHexMap.getHex(new MapPosition(0, 0));
        assertTrue(testHexMap.getAdjacentIntersection(testHex, Direction.ZERO).equals(new MapPosition(3, 1)));
        assertTrue(testHexMap.getAdjacentIntersection(testHex, Direction.ONE).equals(new MapPosition(2, 1)));
        assertTrue(testHexMap.getAdjacentIntersection(testHex, Direction.TWO).equals(new MapPosition(1, 1)));
        assertTrue(testHexMap.getAdjacentIntersection(testHex, Direction.THREE).equals(new MapPosition(0, 0)));
        assertTrue(testHexMap.getAdjacentIntersection(testHex, Direction.FOUR).equals(new MapPosition(1, 0)));
        assertTrue(testHexMap.getAdjacentIntersection(testHex, Direction.FIVE).equals(new MapPosition(2, 0)));
    }
    
    @Test
    public void testGetIntersectionAnyDirectionBadObject() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();
        try {
            testHexMap.getAdjacentIntersection(new Hex(), Direction.ZERO);
            fail();
        }catch(InvalidHexPositionException e) {}
    }
    
    @Test
    public void testGetAllIntersectionsGoodPosition() {
        HexMap mockedMap = EasyMock.partialMockBuilder(HexMap.class)
                           .addMockedMethod("getIntersectionDirection0")
                           .addMockedMethod("getIntersectionDirection1")
                           .addMockedMethod("getIntersectionDirection2")
                           .addMockedMethod("getIntersectionDirection3")
                           .addMockedMethod("getIntersectionDirection4")
                           .addMockedMethod("getIntersectionDirection5")
                           .createMock();
        MapPosition hexPos = new MapPosition(0, 0);
        EasyMock.expect(mockedMap.getIntersectionDirection0(hexPos)).andReturn(new MapPosition(3, 1));
        EasyMock.expect(mockedMap.getIntersectionDirection1(hexPos)).andReturn(new MapPosition(2, 1));
        EasyMock.expect(mockedMap.getIntersectionDirection2(hexPos)).andReturn(new MapPosition(1, 1));
        EasyMock.expect(mockedMap.getIntersectionDirection3(hexPos)).andReturn(new MapPosition(0, 0));
        EasyMock.expect(mockedMap.getIntersectionDirection4(hexPos)).andReturn(new MapPosition(1, 0));
        EasyMock.expect(mockedMap.getIntersectionDirection5(hexPos)).andReturn(new MapPosition(2, 0));
        EasyMock.replay(mockedMap);
        
        ArrayList<MapPosition> intersectionPoss = mockedMap.getAllAdjacentIntersections(hexPos);
        assertEquals(6, intersectionPoss.size());
        
        EasyMock.verify(mockedMap);
    }
    
    @Test
    public void testGetAllIntersectionsBadPosition() {
        HexMap testHexMap = new HexMap();
        try {
            testHexMap.getAllAdjacentIntersections(new MapPosition());
            fail();
        } catch(InvalidHexPositionException e) {}
    }
    
    @Test
    public void testGetAllIntersectionsGoodObject() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();
        ArrayList<MapPosition> intersectionPoss = testHexMap.getAllAdjacentIntersections(testHexMap.getHex(new MapPosition(0, 0)));
        assertEquals(6, intersectionPoss.size());
        assertTrue(intersectionPoss.get(0).equals(new MapPosition(3, 1)));
        assertTrue(intersectionPoss.get(1).equals(new MapPosition(2, 1)));
        assertTrue(intersectionPoss.get(2).equals(new MapPosition(1, 1)));
        assertTrue(intersectionPoss.get(3).equals(new MapPosition(0, 0)));
        assertTrue(intersectionPoss.get(4).equals(new MapPosition(1, 0)));
        assertTrue(intersectionPoss.get(5).equals(new MapPosition(2, 0)));
    }
    
    @Test
    public void testGetAllIntersectionsBadObject() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();
        try {
            testHexMap.getAllAdjacentIntersections(new Hex());
            fail();
        } catch(InvalidHexPositionException e) {}
    }
    
    @Test
    public void testGameMapHasHexMap() {
        GameMap testGM = new GameMap();
        testGM.setUpAdvancedMap();
        assertFalse(testGM.getHexMap() == null);
    }
    
    @Test
    public void testGameMapCanGetHexesByNumber() {
        GameMap testGM = new GameMap();
        testGM.setUpBeginnerMap(3);
        
        ArrayList<Hex> hexList2 = testGM.getHexesByResourceNumber(2);
        assertTrue(hexList2.size() == 1);
        Hex testHex2 = hexList2.get(0);
        assertTrue(testHex2.getResource() == Resource.WOOL);
        assertTrue(testHex2.getRollResourceNumber() == 2);

        ArrayList<Hex> hexList12 = testGM.getHexesByResourceNumber(12);
        assertTrue(hexList12.size() == 1);
        Hex testHex12 = hexList12.get(0);
        assertEquals(Resource.GRAIN, testHex12.getResource());
        assertEquals(12, testHex12.getRollResourceNumber());
    }
}
