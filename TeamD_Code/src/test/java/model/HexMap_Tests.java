package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.easymock.EasyMock;
import org.junit.Test;

import exception.*;

public class HexMap_Tests {
    
    @Test
    public void testHexMapExists() {
        assertFalse(new HexMap() == null);
    }
    
    @Test
    public void testHexMapReturnsRightSizeMap() {
        HexMap testHexMap = new HexMap();
        Hex[][] hexes = testHexMap.hexes;
        assertEquals(5, hexes.length);
        assertEquals(3, hexes[0].length);
        assertEquals(4, hexes[1].length);
        assertEquals(5, hexes[2].length);
        assertEquals(4, hexes[3].length);
        assertEquals(3, hexes[4].length);
    }
    
    @Test
    public void testBeginnersMapResources() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();
        Hex[][] hexes = testHexMap.hexes;
        Hex[][] correctHexMap = getCorrectBeginnersHexMap();
        for (int i = 0; i < 5; i++) {
            int endPieces = i;
            if (i > 2) {
                endPieces = 4 - i;
            }
            for (int j = 0; j < 3 + endPieces; j++) {
                assertEquals(correctHexMap[i][j].getResource(), hexes[i][j].getResource());
            }
        }
    }
    
    @Test
    public void testBeginnersMapRollNumbers() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();
        Hex[][] hexes = testHexMap.hexes;
        Hex[][] correctHexMap = getCorrectBeginnersHexMap();
        for (int i = 0; i < 5; i++) {
            int endPieces = i;
            if (i > 2) {
                endPieces = 4 - i;
            }
            for (int j = 0; j < 3 + endPieces; j++) {
                assertEquals(correctHexMap[i][j].getRollResourceNumber(), hexes[i][j].getRollResourceNumber());
            }
        }
    }
    
    @Test
    public void testBeginnersMapRobberPosition() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();
        Hex correctHex = testHexMap.getHex(new MapPosition(2, 2));
        assertEquals(correctHex, testHexMap.robber.getHexBeingRobbed());
    }
    
    @Test
    public void testGetHexesOnMap() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();
        Hex[][] hexes = testHexMap.hexes;
        
        for(int i=0; i<5; i++) {
            assertEquals(hexes[i][0], testHexMap.getHex(new MapPosition(i, 0)));
            assertEquals(hexes[i][hexes[i].length-1], testHexMap.getHex(new MapPosition(i, hexes[i].length-1)));
        }
    }
    
    @Test
    public void testGetHexesNotOnMap() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();

        getHexOutOfBounds(testHexMap, -1, 0);
        getHexOutOfBounds(testHexMap, 0, -1);
        getHexOutOfBounds(testHexMap, 0, 3);
        getHexOutOfBounds(testHexMap, 1, -1);
        getHexOutOfBounds(testHexMap, 1, 4);
        getHexOutOfBounds(testHexMap, 2, -1);
        getHexOutOfBounds(testHexMap, 2, 5);
        getHexOutOfBounds(testHexMap, 3, -1);
        getHexOutOfBounds(testHexMap, 3, 4);
        getHexOutOfBounds(testHexMap, 4, -1);
        getHexOutOfBounds(testHexMap, 4, 3);
        getHexOutOfBounds(testHexMap, 5, -1);
        getHexOutOfBounds(testHexMap, 5, 0);
    }

    public void getHexOutOfBounds(HexMap map, int row, int col) {
        try {
            map.getHex(new MapPosition(row, col));
            fail("Expected exception");
        } catch (InvalidHexPositionException e) {}
    }
    
    @Test
    public void testRetrieveHexByCommonNumberBeginner() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();

        ArrayList<Hex> hexes = testHexMap.getHexesByResourceNumber(3);
        assertTrue(hexes.size() == 2);
        Hex testHex0 = hexes.get(0);
        assertEquals(Resource.ORE, testHex0.getResource());
        assertEquals(3, testHex0.getRollResourceNumber());

        Hex testHex1 = hexes.get(1);
        assertEquals(Resource.LUMBER, testHex1.getResource());
        assertEquals(3, testHex1.getRollResourceNumber());
    }
    
    @Test
    public void testRetrieveHexByNumber12and2Beginner() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();

        ArrayList<Hex> hexList2 = testHexMap.getHexesByResourceNumber(2);
        assertTrue(hexList2.size() == 1);
        Hex testHex2 = hexList2.get(0);
        assertTrue(testHex2.getResource() == Resource.WOOL);
        assertTrue(testHex2.getRollResourceNumber() == 2);

        ArrayList<Hex> hexList12 = testHexMap.getHexesByResourceNumber(12);
        assertTrue(hexList12.size() == 1);
        Hex testHex12 = hexList12.get(0);
        assertEquals(Resource.GRAIN, testHex12.getResource());
        assertEquals(12, testHex12.getRollResourceNumber());
    }
    
    @Test
    public void testRetrieveHexForBadNumbersBeginner() {
        HexMap testHexMap = new HexMap();
        testHexMap.setUpBeginnerMap();

        try {
            ArrayList<Hex> hexList7 = testHexMap.getHexesByResourceNumber(7);
            fail();
        } catch (IllegalArgumentException e) {}

        try {
            ArrayList<Hex> hexList1 = testHexMap.getHexesByResourceNumber(1);
            fail();
        } catch (IllegalArgumentException e) {}

        try {
            ArrayList<Hex> hexList13 = testHexMap.getHexesByResourceNumber(13);
            fail();
        } catch (IllegalArgumentException e) {}
    }
    
    @Test
    public void testRandomHexMapResourceCount() {
        HexMap testHexMap = new HexMap();
        Random rand = new Random(0);
        testHexMap.setUpAdvancedMap(rand);
        Hex[][] hexMap = testHexMap.hexes;
        
        int hillCount = 0;
        int mountainCount = 0;
        int forestCount = 0;
        int fieldCount = 0;
        int pastureCount = 0;
        int desertCount = 0;
        
        for (Hex[] hexes : hexMap) {
            for (Hex hex : hexes) {
                switch (hex.getResource()) {
                    case BRICK:
                        hillCount++;
                        break;
                    case ORE:
                        mountainCount++;
                        break;
                    case LUMBER:
                        forestCount++;
                        break;
                    case GRAIN:
                        fieldCount++;
                        break;
                    case WOOL:
                        pastureCount++;
                        break;
                    case DESERT:
                        desertCount++;
                        break;
                    default:
                        fail("Unsupported resource type");
                }
            }
        }
        assertEquals(3, hillCount);
        assertEquals(3, mountainCount);
        assertEquals(4, forestCount);
        assertEquals(4, pastureCount);
        assertEquals(4, fieldCount);
        assertEquals(1, desertCount);
    }
    
    @Test
    public void testResourceNumberShuffle() {
        HexMap testHexMap = new HexMap();
        Random rand = new Random(0);
        List<Integer> resourceNumbers = testHexMap.getShuffledResourceNumbers(rand);
        List<Integer> correctNumbers = getRandomizedResourceNumbersSeed0();
        for(int i=0; i<18; i++) {
            assertEquals(correctNumbers.get(i), resourceNumbers.get(i));
        }
    }
    
    @Test
    public void testResourceShuffle() {
        HexMap testHexMap = new HexMap();
        Random rand = new Random(0);
        List<Resource> resources = testHexMap.getShuffledResources(rand);
        List<Resource> correctResources = getRandomizedResourcesSeed0();
        for(int i=0; i<19; i++) {
            assertEquals(correctResources.get(i), resources.get(i));
        }
    }
    
    @Test
    public void testRandomHexMapPlacementMocked() {
        List<Resource> correctResources = getRandomizedResourcesSeed0();
        List<Integer> correctNumbers = getRandomizedResourceNumbersSeed0();
        Random rand = new Random(0);
        HexMap testHexMap = EasyMock.partialMockBuilder(HexMap.class)
                            .withConstructor()
                            .addMockedMethod("getShuffledResourceNumbers")
                            .addMockedMethod("getShuffledResources")
                            .createMock();
        EasyMock.expect(testHexMap.getShuffledResourceNumbers(rand)).andReturn(correctNumbers);
        EasyMock.expect(testHexMap.getShuffledResources(rand)).andReturn(correctResources);
        EasyMock.replay(testHexMap);
        
        testHexMap.setUpAdvancedMap(rand);
        Hex[][] hexMap = testHexMap.hexes;
        int fullNumberChecked = 0;
        int numberNonDesertsChecked = 0;
        for(int i=0; i<5; i++) {
            for(int j=0; j<hexMap[i].length; j++) {
                assertEquals(correctResources.get(fullNumberChecked), hexMap[i][j].getResource());
                
                if(hexMap[i][j].getResource() == Resource.DESERT) {
                    assertEquals(0, hexMap[i][j].getRollResourceNumber());
                }else {
                    assertTrue(correctNumbers.get(numberNonDesertsChecked).equals(hexMap[i][j].getRollResourceNumber()));
                    numberNonDesertsChecked++;
                }
                
                fullNumberChecked ++;
            }
        }
        EasyMock.verify(testHexMap);
    }
    
    @Test
    public void testAdvancedMapRobberPosition() {
        HexMap testHexMap = new HexMap();
        Random rand = new Random(0);
        testHexMap.setUpAdvancedMap(rand);
        Hex correctHex = testHexMap.getHex(new MapPosition(1, 3));
        assertEquals(correctHex, testHexMap.robber.getHexBeingRobbed());
    }
    
    @Test
    public void testRetrieveHexByCommonNumberAdvanced() {
        List<Resource> correctResources = getRandomizedResourcesSeed0();
        List<Integer> correctNumbers = getRandomizedResourceNumbersSeed0();
        Random rand = new Random(0);
        HexMap testHexMap = EasyMock.partialMockBuilder(HexMap.class)
                            .withConstructor()
                            .addMockedMethod("getShuffledResourceNumbers")
                            .addMockedMethod("getShuffledResources")
                            .createMock();
        EasyMock.expect(testHexMap.getShuffledResourceNumbers(rand)).andReturn(correctNumbers);
        EasyMock.expect(testHexMap.getShuffledResources(rand)).andReturn(correctResources);
        EasyMock.replay(testHexMap);
        
        testHexMap.setUpAdvancedMap(rand);

        ArrayList<Hex> hexes = testHexMap.getHexesByResourceNumber(6);
        assertTrue(hexes.size() == 2);
        Hex testHex0 = hexes.get(0);
        assertEquals(Resource.LUMBER, testHex0.getResource());
        assertEquals(6, testHex0.getRollResourceNumber());

        Hex testHex1 = hexes.get(1);
        assertEquals(Resource.WOOL, testHex1.getResource());
        assertEquals(6, testHex1.getRollResourceNumber());
        
        EasyMock.verify(testHexMap);
    }
    
    @Test
    public void testRetrieveHexByNumber12and2Advanced() {
        List<Resource> correctResources = getRandomizedResourcesSeed0();
        List<Integer> correctNumbers = getRandomizedResourceNumbersSeed0();
        Random rand = new Random(0);
        HexMap testHexMap = EasyMock.partialMockBuilder(HexMap.class)
                            .withConstructor()
                            .addMockedMethod("getShuffledResourceNumbers")
                            .addMockedMethod("getShuffledResources")
                            .createMock();
        EasyMock.expect(testHexMap.getShuffledResourceNumbers(rand)).andReturn(correctNumbers);
        EasyMock.expect(testHexMap.getShuffledResources(rand)).andReturn(correctResources);
        EasyMock.replay(testHexMap);
        
        testHexMap.setUpAdvancedMap(rand);

        ArrayList<Hex> hexList2 = testHexMap.getHexesByResourceNumber(2);
        assertTrue(hexList2.size() == 1);
        Hex testHex2 = hexList2.get(0);
        assertEquals(Resource.WOOL, testHex2.getResource());
        assertEquals(2, testHex2.getRollResourceNumber());

        ArrayList<Hex> hexList12 = testHexMap.getHexesByResourceNumber(12);
        assertTrue(hexList12.size() == 1);
        Hex testHex12 = hexList12.get(0);
        assertEquals(Resource.GRAIN, testHex12.getResource());
        assertEquals(12, testHex12.getRollResourceNumber());
        
        EasyMock.verify(testHexMap);
    }
    
    @Test
    public void testRetrieveHexForBadNumbersAdvanced() {
        List<Resource> correctResources = getRandomizedResourcesSeed0();
        List<Integer> correctNumbers = getRandomizedResourceNumbersSeed0();
        Random rand = new Random(0);
        HexMap testHexMap = EasyMock.partialMockBuilder(HexMap.class)
                            .withConstructor()
                            .addMockedMethod("getShuffledResourceNumbers")
                            .addMockedMethod("getShuffledResources")
                            .createMock();
        EasyMock.expect(testHexMap.getShuffledResourceNumbers(rand)).andReturn(correctNumbers);
        EasyMock.expect(testHexMap.getShuffledResources(rand)).andReturn(correctResources);
        EasyMock.replay(testHexMap);
        
        testHexMap.setUpAdvancedMap(rand);

        try {
            ArrayList<Hex> hexList7 = testHexMap.getHexesByResourceNumber(7);
            fail();
        } catch (IllegalArgumentException e) {}

        try {
            ArrayList<Hex> hexList1 = testHexMap.getHexesByResourceNumber(1);
            fail();
        } catch (IllegalArgumentException e) {}

        try {
            ArrayList<Hex> hexList13 = testHexMap.getHexesByResourceNumber(13);
            fail();
        } catch (IllegalArgumentException e) {}
        
        EasyMock.verify(testHexMap);
    }
    
    private Hex[][] getCorrectBeginnersHexMap() {
        Hex[][] correctHexMap = new Hex[5][];
        correctHexMap[0] = new Hex[3];
        correctHexMap[1] = new Hex[4];
        correctHexMap[2] = new Hex[5];
        correctHexMap[3] = new Hex[4];
        correctHexMap[4] = new Hex[3];

        correctHexMap[0][0] = new Hex(Resource.BRICK, 5);
        correctHexMap[0][1] = new Hex(Resource.GRAIN, 6);
        correctHexMap[0][2] = new Hex(Resource.WOOL, 11);

        correctHexMap[1][0] = new Hex(Resource.LUMBER, 8);
        correctHexMap[1][1] = new Hex(Resource.ORE, 3);
        correctHexMap[1][2] = new Hex(Resource.GRAIN, 4);
        correctHexMap[1][3] = new Hex(Resource.WOOL, 5);

        correctHexMap[2][0] = new Hex(Resource.GRAIN, 9);
        correctHexMap[2][1] = new Hex(Resource.LUMBER, 11);
        correctHexMap[2][2] = new Hex();
        correctHexMap[2][3] = new Hex(Resource.LUMBER, 3);
        correctHexMap[2][4] = new Hex(Resource.ORE, 8);

        correctHexMap[3][0] = new Hex(Resource.GRAIN, 12);
        correctHexMap[3][1] = new Hex(Resource.BRICK, 6);
        correctHexMap[3][2] = new Hex(Resource.WOOL, 4);
        correctHexMap[3][3] = new Hex(Resource.BRICK, 10);

        correctHexMap[4][0] = new Hex(Resource.ORE, 10);
        correctHexMap[4][1] = new Hex(Resource.WOOL, 2);
        correctHexMap[4][2] = new Hex(Resource.LUMBER, 9);

        return correctHexMap;
    }
    
    private List<Integer> getRandomizedResourceNumbersSeed0(){
        ArrayList<Integer> nums = new ArrayList<Integer>();
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.add(11);
        nums.add(8);
        nums.add(5);
        nums.add(6);
        nums.add(6);
        nums.add(10);
        nums.add(9);
        nums.add(12);
        nums.add(10);
        nums.add(8);
        nums.add(11);
        nums.add(3);
        nums.add(4);
        nums.add(9);
        nums.add(5);
        return nums;
    }
    
    private List<Resource> getRandomizedResourcesSeed0(){
        ArrayList<Resource> resources = new ArrayList<Resource>();
        resources.add(Resource.WOOL);
        resources.add(Resource.LUMBER);
        resources.add(Resource.BRICK);
        resources.add(Resource.GRAIN);
        resources.add(Resource.GRAIN);
        resources.add(Resource.WOOL);
        resources.add(Resource.DESERT);
        resources.add(Resource.LUMBER);
        resources.add(Resource.WOOL);
        resources.add(Resource.ORE);
        resources.add(Resource.BRICK);
        resources.add(Resource.GRAIN);
        resources.add(Resource.WOOL);
        resources.add(Resource.LUMBER);
        resources.add(Resource.ORE);
        resources.add(Resource.LUMBER);
        resources.add(Resource.BRICK);
        resources.add(Resource.GRAIN);
        resources.add(Resource.ORE);
        return resources;
    }
    
    @Test
    public void testGameMapHasHexMap() {
        GameMap testGM = new GameMap();
        testGM.setUpAdvancedMap(new Random(0));
        assertEquals(Resource.WOOL, testGM.getHex(0, 0).getResource());
    }
}
