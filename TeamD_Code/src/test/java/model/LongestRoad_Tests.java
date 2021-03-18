package model;

import static org.junit.Assert.*;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;
import java.util.*;

public class LongestRoad_Tests {

    PlayerColor[] colors = { PlayerColor.BLUE, PlayerColor.ORANGE, PlayerColor.RED, PlayerColor.WHITE };
    
    @Test
    public void testInitialState() {
        
        CandidateRoadFinder finder = EasyMock.mock(CandidateRoadFinder.class);
        LongestRoad longestRoad = new LongestRoad(finder);
        
        EasyMock.replay(finder);
        assertNobodyHasLongestRoad(longestRoad);
        EasyMock.verify(finder);
        
    }

    private void assertNobodyHasLongestRoad(LongestRoad longestRoad) {
        assertEquals(PlayerColor.NONE, longestRoad.getHolder());
        assertEquals(0, longestRoad.getLength());
    }
    
    @Test
    public void testEmptyMap() {
        
        CandidateRoadFinder finder = EasyMock.mock(CandidateRoadFinder.class);
        LongestRoad longestRoad = new LongestRoad(finder);
        GameMap board = EasyMock.mock(GameMap.class);
        EdgeMap edgeMap = EasyMock.partialMockBuilder(EdgeMap.class).addMockedMethod("getEdge").withConstructor().createMock();
        Edge emptyEdge = EasyMock.mock(Edge.class);
        
        EasyMock.expect(emptyEdge.getRoadColor()).andStubReturn(PlayerColor.NONE);
        EasyMock.expect(edgeMap.getEdge(EasyMock.anyObject(MapPosition.class))).andStubReturn(emptyEdge);
        EasyMock.expect(board.getEdgeMap()).andReturn(edgeMap);
        EasyMock.expect(finder.getCandidateRoadFromEdge(emptyEdge)).andStubReturn(new CandidateRoad(PlayerColor.NONE, 0));
        
        EasyMock.replay(board, edgeMap, emptyEdge, finder);
        longestRoad.updateLongestRoad(board);
        assertNobodyHasLongestRoad(longestRoad);
        EasyMock.verify(board, edgeMap, emptyEdge, finder);
        
    }
    
    @Test
    public void testRoadLength5() {
        
        for(PlayerColor color: colors) {
            CandidateRoad fiveRoad = new CandidateRoad(color, 5);
            testRoadLength(fiveRoad, fiveRoad);
        }
        
    }
    
    @Test
    public void testRoadLength4() {
        
        for(PlayerColor color: colors) {
            testRoadLength(new CandidateRoad(PlayerColor.NONE, 0), new CandidateRoad(color, 4));
        }
        
    }
    
    @Test
    public void testRoadLengthMax() {
        
        for(PlayerColor color: colors) {
            CandidateRoad maxRoad = new CandidateRoad(color, Integer.MAX_VALUE);
            testRoadLength(maxRoad, maxRoad);
        }
        
    }
    
    private void testRoadLength(CandidateRoad expected, CandidateRoad inputRoad) {
        testUpdateLongestRoad(expected, Arrays.asList(inputRoad), new CandidateRoad(PlayerColor.NONE, 0));
    }
    
    private void testUpdateLongestRoad(CandidateRoad expected, List<CandidateRoad> candidateList, CandidateRoad previousLongest) {
        
        GameMap board = EasyMock.mock(GameMap.class);
        LongestRoad longestRoad = EasyMock.partialMockBuilder(LongestRoad.class)
                .addMockedMethod("getCandidateRoads")
                .withConstructor(previousLongest, new CandidateRoadFinder(board))
                .createMock();
                
        EasyMock.expect(longestRoad.getCandidateRoads(board)).andReturn(candidateList);
        
        EasyMock.replay(board, longestRoad);
        
        longestRoad.updateLongestRoad(board);
        
        assertEquals(expected.color, longestRoad.getHolder());
        assertEquals(expected.length, longestRoad.getLength());
        
        EasyMock.verify(board, longestRoad);
    }
    
    @Test
    public void testShorterRoadDoesNotOverwriteLongerRoad() {
        
        CandidateRoad expected = new CandidateRoad(PlayerColor.BLUE, 6);
        CandidateRoad previous = expected;

        List<CandidateRoad> candidates = new ArrayList<CandidateRoad>(Arrays.asList(new CandidateRoad(PlayerColor.RED, 5), expected));
        
        testUpdateLongestRoad(expected, candidates, previous);
        
    }
    
    @Test
    public void testEqualRoadDoesNotOverwriteCurrentRoad() {
        
        CandidateRoad expected = new CandidateRoad(PlayerColor.BLUE, 6);
        CandidateRoad previous = expected;

        List<CandidateRoad> candidates = new ArrayList<CandidateRoad>(Arrays.asList(expected, new CandidateRoad(PlayerColor.RED, 6)));
        
        testUpdateLongestRoad(expected, candidates, previous);
        
    }
    
    @Test
    public void testLongerRoadClaimsLongestRoad() {
        
        CandidateRoad expected = new CandidateRoad(PlayerColor.BLUE, 6);
        CandidateRoad previous = new CandidateRoad(PlayerColor.RED, 5);

        List<CandidateRoad> candidates = new ArrayList<CandidateRoad>(Arrays.asList(expected, previous));
        
        testUpdateLongestRoad(expected, candidates, previous);
    }
    
    @Test
    public void testNoRoadsLongEnoughAnymore() {
        
        CandidateRoad expected = new CandidateRoad(PlayerColor.NONE, 0);
        CandidateRoad previous = new CandidateRoad(PlayerColor.RED, 5);

        List<CandidateRoad> candidates = new ArrayList<CandidateRoad>(
                Arrays.asList(new CandidateRoad(PlayerColor.RED, 4), new CandidateRoad(PlayerColor.BLUE, 4)));
        
        testUpdateLongestRoad(expected, candidates, previous);
    }
    
    @Test
    public void testShortenedRoadGoesToNextHighest() {
        
        CandidateRoad expected = new CandidateRoad(PlayerColor.BLUE, 5);
        CandidateRoad previous = new CandidateRoad(PlayerColor.RED, 6);

        List<CandidateRoad> candidates = new ArrayList<CandidateRoad>(
                Arrays.asList(new CandidateRoad(PlayerColor.RED, 4), expected));
        
        testUpdateLongestRoad(expected, candidates, previous);
    }
    
    @Test
    public void testOwnerKeepsLongestRoadAfterDecrease() {
        
        CandidateRoad expected = new CandidateRoad(PlayerColor.RED, 6);
        CandidateRoad previous = new CandidateRoad(PlayerColor.RED, 7);

        List<CandidateRoad> candidates = new ArrayList<CandidateRoad>(
                Arrays.asList(new CandidateRoad(PlayerColor.ORANGE, 5), expected));
        
        testUpdateLongestRoad(expected, candidates, previous);
    }
    
    @Test
    public void testOwnerKeepsLongestRoadInTie() {
        
        CandidateRoad expected = new CandidateRoad(PlayerColor.RED, 5);
        CandidateRoad previous = new CandidateRoad(PlayerColor.RED, 6);

        List<CandidateRoad> candidates = new ArrayList<CandidateRoad>(
                Arrays.asList(new CandidateRoad(PlayerColor.ORANGE, 5), expected));
        
        testUpdateLongestRoad(expected, candidates, previous);
    }
    
    @Test
    public void testOwnerLosesLongestRoadInTie() {
        
        CandidateRoad expected = new CandidateRoad(PlayerColor.NONE, 0);
        CandidateRoad previous = new CandidateRoad(PlayerColor.RED, 6);

        List<CandidateRoad> candidates = new ArrayList<CandidateRoad>(
                Arrays.asList(new CandidateRoad(PlayerColor.ORANGE, 5),
                        new CandidateRoad(PlayerColor.RED, 4),
                        new CandidateRoad(PlayerColor.BLUE, 5)));
        
        testUpdateLongestRoad(expected, candidates, previous);
    }
    
    @Test
    public void testGetControllerRoadLength() {
        
        for(int i = 0; i < colors.length; i++) {
            
            CandidateRoadFinder finder = EasyMock.mock(CandidateRoadFinder.class);
            LongestRoad longestRoad = new LongestRoad(new CandidateRoad(colors[i], i), finder);
            
            List<CandidateRoad> candidates = new ArrayList<CandidateRoad>();
            candidates.add(new CandidateRoad(colors[(i + 1) % colors.length], 0));
            candidates.add(new CandidateRoad(colors[i], i + 1));
            
            EasyMock.replay(finder);
            assertEquals(i + 1, longestRoad.getControllerRoadLength(candidates));
            EasyMock.verify(finder);
        }
        
    }
    
    @Test
    public void testGetAllCandidateRoadsChecksEveryEdge() {
    
        EdgeMap edgeMap = EasyMock.partialMockBuilder(EdgeMap.class)
                .addMockedMethod("getEdge")
                .withConstructor()
                .createMock();
        
        CandidateRoadFinder finder = EasyMock.mock(CandidateRoadFinder.class);
        LongestRoad longestRoad = new LongestRoad(finder);
        
        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<Capture<MapPosition>> captures = new ArrayList<Capture<MapPosition>>();
        ArrayList<CandidateRoad> expected = new ArrayList<CandidateRoad>();
        
        for(int i = 0; i < edgeMap.getNumberOfRows(); i++) {
            
            for(int j = 0; j < edgeMap.getNumberOfEdgesInRow(i); j++) {
                
                Capture<MapPosition> capture = EasyMock.newCapture();
                Edge testEdge = EasyMock.mock(Edge.class);
            
                EasyMock.expect(edgeMap.getEdge((MapPosition) EasyMock.and(
                        EasyMock.capture(capture),
                        EasyMock.isA(MapPosition.class)))).andReturn(testEdge);
            
                CandidateRoad road =  new CandidateRoad(PlayerColor.NONE, 0);
                expected.add(road);
                
                EasyMock.expect(finder.getCandidateRoadFromEdge(testEdge)).andReturn(road);
            
                edges.add(testEdge);
                captures.add(capture);
            }
        }
        
        
        EasyMock.replay(finder, edgeMap);
        EasyMock.replay(edges.toArray());
        
        List<CandidateRoad> actual = longestRoad.getAllCandidateRoads(edgeMap);
        
        EasyMock.verify(finder, edgeMap);
        EasyMock.verify(edges.toArray());
        
        Iterator<Capture<MapPosition>> captureIterator = captures.iterator();
        
        for(int i = 0; i < edgeMap.getNumberOfRows(); i++) {
            
            for(int j = 0; j < edgeMap.getNumberOfEdgesInRow(i); j++) {
                
                Capture<MapPosition> currentCapture = captureIterator.next();
                
                assertEquals(i, currentCapture.getValue().getRow());
                assertEquals(j, currentCapture.getValue().getColumn());

            }
        }
        
        assertTrue(expected.equals(actual));
        
    }
    
    @Test
    public void testGetCandidateRoadsGetsBestOfEachColor() {
        
        GameMap board = EasyMock.mock(GameMap.class);
        EdgeMap edgeMap = EasyMock.mock(EdgeMap.class);
        
        ArrayList<CandidateRoad> candidateList = new ArrayList<CandidateRoad>();
        ArrayList<CandidateRoad> expected = new ArrayList<CandidateRoad>();
        
        EasyMock.expect(board.getEdgeMap()).andReturn(edgeMap);
        
        LongestRoad longestRoad = EasyMock.partialMockBuilder(LongestRoad.class)
                .addMockedMethod("getBestCandidateForColor")
                .addMockedMethod("getAllCandidateRoads")
                .createMock();
        
        EasyMock.expect(longestRoad.getAllCandidateRoads(edgeMap)).andReturn(candidateList);
        
        for(PlayerColor color : colors) {
            
            CandidateRoad road = new CandidateRoad(color, 1);
            expected.add(road);
            
            EasyMock.expect(longestRoad.getBestCandidateForColor(color, candidateList)).andReturn(road);
        }
        
        EasyMock.replay(longestRoad, edgeMap, board);
        
        List<CandidateRoad> actual = longestRoad.getCandidateRoads(board);
        
        EasyMock.verify(longestRoad, edgeMap, board);
        
        assertEquals(4, actual.size());
        
        for(CandidateRoad road : actual) {
            assertTrue(expected.contains(road));
        }
        
    }
    
    @Test
    public void testGetBestCandidateForColorOneCandidate() {
        
        CandidateRoadFinder finder = EasyMock.mock(CandidateRoadFinder.class);
        LongestRoad longestRoad = new LongestRoad(finder);
            
        List<CandidateRoad> candidateList = new ArrayList<CandidateRoad>();
            
        CandidateRoad redExpected = new CandidateRoad(PlayerColor.RED, 100);
        CandidateRoad blueExpected = new CandidateRoad(PlayerColor.BLUE, 100);
        CandidateRoad whiteExpected = new CandidateRoad(PlayerColor.WHITE, 100);
        CandidateRoad orangeExpected = new CandidateRoad(PlayerColor.ORANGE, 100);
            
        candidateList.add(redExpected);
        candidateList.add(blueExpected);
        candidateList.add(whiteExpected);
        candidateList.add(orangeExpected);
            
        EasyMock.replay(finder);
        assertEquals(redExpected, longestRoad.getBestCandidateForColor(PlayerColor.RED, candidateList));
        assertEquals(blueExpected, longestRoad.getBestCandidateForColor(PlayerColor.BLUE, candidateList));
        assertEquals(whiteExpected, longestRoad.getBestCandidateForColor(PlayerColor.WHITE, candidateList));
        assertEquals(orangeExpected, longestRoad.getBestCandidateForColor(PlayerColor.ORANGE, candidateList));
        EasyMock.verify(finder);
        
    }
    
    @Test
    public void testGetBestCandidateForColorDoesntAppear() {
        
        CandidateRoadFinder finder = EasyMock.mock(CandidateRoadFinder.class);
        LongestRoad longestRoad = new LongestRoad(finder);
        
        EasyMock.replay(finder);
        
        List<CandidateRoad> candidateList = new ArrayList<CandidateRoad>();
        
        for(PlayerColor color : colors) {
            
            CandidateRoad actual = longestRoad.getBestCandidateForColor(color, candidateList);
            
            assertEquals(color, actual.color);
            assertEquals(0, actual.length);
            
        }
        
        EasyMock.verify(finder);
    
    }
    
    @Test
    public void testGetBestCandidateForColorMultipleCandidates() {
        
        GameMap board = EasyMock.mock(GameMap.class);
        LongestRoad longestRoad = new LongestRoad(new CandidateRoadFinder(board));        
            
        List<CandidateRoad> candidateList = new ArrayList<CandidateRoad>();
            
        CandidateRoad redExpected = new CandidateRoad(PlayerColor.RED, 100);
        CandidateRoad blueExpected = new CandidateRoad(PlayerColor.BLUE, 100);
        CandidateRoad whiteExpected = new CandidateRoad(PlayerColor.WHITE, 100);
        CandidateRoad orangeExpected = new CandidateRoad(PlayerColor.ORANGE, 100);
            
        candidateList.add(redExpected);
        candidateList.add(new CandidateRoad(PlayerColor.RED, 99));
        candidateList.add(new CandidateRoad(PlayerColor.BLUE, 99));
        candidateList.add(new CandidateRoad(PlayerColor.ORANGE, 99));
        candidateList.add(blueExpected);
        candidateList.add(new CandidateRoad(PlayerColor.NONE, 0));
        candidateList.add(new CandidateRoad(PlayerColor.WHITE, 99));
        candidateList.add(whiteExpected);
        candidateList.add(new CandidateRoad(PlayerColor.BLUE, 99));
        candidateList.add(orangeExpected);
            
        EasyMock.replay(board);
        
        assertEquals(redExpected, longestRoad.getBestCandidateForColor(PlayerColor.RED, candidateList));
        assertEquals(blueExpected, longestRoad.getBestCandidateForColor(PlayerColor.BLUE, candidateList));
        assertEquals(whiteExpected, longestRoad.getBestCandidateForColor(PlayerColor.WHITE, candidateList));
        assertEquals(orangeExpected, longestRoad.getBestCandidateForColor(PlayerColor.ORANGE, candidateList));
        
        EasyMock.verify(board);
    }
    
    @Test
    public void testGetBestCandidateForColorTieBreaksToFirst() {
        
        CandidateRoadFinder finder = EasyMock.mock(CandidateRoadFinder.class);
        LongestRoad longestRoad = new LongestRoad(finder);
            
        List<CandidateRoad> candidateList = new ArrayList<CandidateRoad>();
            
        CandidateRoad redExpected = new CandidateRoad(PlayerColor.RED, 100);
            
        candidateList.add(redExpected);
        candidateList.add(new CandidateRoad(PlayerColor.RED, 100));
        
        EasyMock.replay(finder);
        assertEquals(redExpected, longestRoad.getBestCandidateForColor(PlayerColor.RED, candidateList));
        EasyMock.verify(finder);
        
    }
    
}
