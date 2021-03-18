package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.easymock.EasyMock;
import org.junit.Test;

public class CandidateRoadFinder_Tests {

    PlayerColor[] colors = { PlayerColor.BLUE, PlayerColor.ORANGE, PlayerColor.RED, PlayerColor.WHITE };
    Edge emptyEdge = mockedEdge(PlayerColor.NONE);
    
    @Test
    public void testEmptyEdge() {
        
        GameMap board = EasyMock.mock(GameMap.class);
        CandidateRoadFinder finder = new CandidateRoadFinder(board);        
        
        EasyMock.replay(board);
        
        assertEquals(PlayerColor.NONE, finder.getCandidateRoadFromEdge(emptyEdge).color);
        assertEquals(0, finder.getCandidateRoadFromEdge(emptyEdge).length);
        
        EasyMock.verify(board);
        
    }
    
    @Test
    public void testRoadLengthOne() {
                
        for(PlayerColor color : colors) {
            
            Edge aloneRoad = mockedEdge(color);
            
            Intersection northIntersection = mockedIntersection(PlayerColor.NONE, false, false);
            Intersection southIntersection = mockedIntersection(PlayerColor.NONE, false, false);
            
            GameMap board = EasyMock.mock(GameMap.class);
            CandidateRoadFinder finder = new CandidateRoadFinder(board);
            
            EasyMock.expect(board.getAllIntersectionsFromEdge(aloneRoad))
                    .andStubReturn(new ArrayList<Intersection>(Arrays.asList(northIntersection, southIntersection)));
            
            EasyMock.expect(board.getAllEdgesFromIntersection(northIntersection))
                    .andStubReturn(new ArrayList<Edge>(Arrays.asList(aloneRoad, emptyEdge, emptyEdge)));
            EasyMock.expect(board.getAllEdgesFromIntersection(southIntersection))
                    .andStubReturn(new ArrayList<Edge>(Arrays.asList(emptyEdge, emptyEdge, aloneRoad)));
            
            EasyMock.replay(board);
            
            CandidateRoad actual = finder.getCandidateRoadFromEdge(aloneRoad);
            
            assertEquals(color, actual.color);
            assertEquals(1, actual.length);
            
            EasyMock.verify(board);
        
        }
        
    }
    
    @Test
    public void testRoadLengthTwo() {
                
        for(PlayerColor color : colors) {
            testTwoPieceRoad(color, color, 2);
        }
        
    }
    
    @Test
    public void testOnlyUsesSameColorPieces() {
        
        for(int i = 0; i < colors.length; i++) {
            
            PlayerColor color = colors[i];
            PlayerColor otherColor = colors[(i + 1) % colors.length];
            
            testTwoPieceRoad(color, otherColor, 1);
        
        }
        
    }
    
    @Test
    public void testBuildingOfOtherColorBreaksRoad() {
        
        for(int i = 0; i < colors.length; i++) {
            
            PlayerColor color = colors[i];
            PlayerColor otherColor = colors[(i + 1) % colors.length];
            
            testTwoPieceRoadWithBuilding(color, mockedIntersection(otherColor, true, false), color, 1);
            testTwoPieceRoadWithBuilding(color, mockedIntersection(otherColor, false, true), color, 1);
        
        }
        
    }
    
    @Test
    public void testBuildingOfSameColorAllowsRoad() {
        
        for(int i = 0; i < colors.length; i++) {
            
            PlayerColor color = colors[i];
            PlayerColor otherColor = colors[(i + 1) % colors.length];
            
            testTwoPieceRoadWithBuilding(color, mockedIntersection(color, true, false), color, 2);
            testTwoPieceRoadWithBuilding(color, mockedIntersection(color, false, true), color, 2);
        
        }
        
    }
    
    @Test
    public void testRoadLengthThree() {
        
        for(PlayerColor color : colors) {
            testThreePieceRoad(color, color, color, 3);
        }
    }
    
    @Test
    public void testSearchDoesNotBridgeGaps() {
        
        for(PlayerColor color : colors) {
            testThreePieceRoad(color, PlayerColor.NONE, color, 1);
        }
    }
    
    @Test
    public void testOnlyLongestBranchCounted() {
                
        for(PlayerColor color : colors) {
            
            Edge initialRoad = mockedEdge(color);
            Edge branchLeftRoad = mockedEdge(color);
            Edge branchRightRoad = mockedEdge(color);
            Edge branchRightExtensionRoad = mockedEdge(color);
            
            Intersection southIntersection = mockedIntersection(PlayerColor.NONE, false, false);
            Intersection branchIntersection = mockedIntersection(PlayerColor.NONE, false, false);
            Intersection leftIntersection = mockedIntersection(PlayerColor.NONE, false, false);
            Intersection rightIntersection = mockedIntersection(PlayerColor.NONE, false, false);
            Intersection rightExtensionIntersection = mockedIntersection(PlayerColor.NONE, false, false);

            
            GameMap board = EasyMock.mock(GameMap.class);
            CandidateRoadFinder finder = new CandidateRoadFinder(board);
            
            EasyMock.expect(board.getAllIntersectionsFromEdge(initialRoad))
                    .andStubReturn(new ArrayList<Intersection>(Arrays.asList(southIntersection, branchIntersection)));
            EasyMock.expect(board.getAllIntersectionsFromEdge(branchLeftRoad))
                    .andStubReturn(new ArrayList<Intersection>(Arrays.asList(branchIntersection, leftIntersection)));
            EasyMock.expect(board.getAllIntersectionsFromEdge(branchRightRoad))
                    .andStubReturn(new ArrayList<Intersection>(Arrays.asList(branchIntersection, rightIntersection)));
            EasyMock.expect(board.getAllIntersectionsFromEdge(branchRightExtensionRoad))
                    .andStubReturn(new ArrayList<Intersection>(Arrays.asList(rightIntersection, rightExtensionIntersection)));
            
            EasyMock.expect(board.getAllEdgesFromIntersection(southIntersection))
                    .andStubReturn(new ArrayList<Edge>(Arrays.asList(initialRoad, emptyEdge, emptyEdge)));
            EasyMock.expect(board.getAllEdgesFromIntersection(branchIntersection))
                    .andStubReturn(new ArrayList<Edge>(Arrays.asList(initialRoad, branchLeftRoad, branchRightRoad)));
            EasyMock.expect(board.getAllEdgesFromIntersection(leftIntersection))
                    .andStubReturn(new ArrayList<Edge>(Arrays.asList(branchLeftRoad, emptyEdge, emptyEdge)));
            EasyMock.expect(board.getAllEdgesFromIntersection(rightIntersection))
                    .andStubReturn(new ArrayList<Edge>(Arrays.asList(branchRightRoad, branchRightExtensionRoad, emptyEdge)));
            EasyMock.expect(board.getAllEdgesFromIntersection(rightExtensionIntersection))
                    .andStubReturn(new ArrayList<Edge>(Arrays.asList(branchRightExtensionRoad, emptyEdge, emptyEdge)));
            
            EasyMock.replay(board);
            
            CandidateRoad result = finder.getCandidateRoadFromEdge(initialRoad);
            
            assertEquals(color, result.color);
            assertEquals(3, result.length);
            
            EasyMock.verify(board);
            
        }
        
    }

    @Test
    public void testPieceCountedOnceInLoop() {
        
        for(PlayerColor color : colors) {
            
            GameMap board = EasyMock.mock(GameMap.class);
            CandidateRoadFinder finder = new CandidateRoadFinder(board);
            
            Edge northNorthEastRoad = mockedEdge(color);
            Edge eastRoad = mockedEdge(color);
            Edge southSouthEastRoad = mockedEdge(color);
            Edge southSouthWestRoad = mockedEdge(color);
            Edge westRoad = mockedEdge(color);
            Edge northNorthWestRoad = mockedEdge(color);
            
            ArrayList<Edge> edges = new ArrayList<Edge>(
                    Arrays.asList(northNorthEastRoad, eastRoad, southSouthEastRoad, southSouthWestRoad, westRoad, northNorthWestRoad));
            
            Intersection northIntersection = mockedIntersection(PlayerColor.NONE, false, false);
            Intersection northEastIntersection = mockedIntersection(PlayerColor.NONE, false, false);
            Intersection southEastIntersection = mockedIntersection(PlayerColor.NONE, false, false);
            Intersection southIntersection = mockedIntersection(PlayerColor.NONE, false, false);
            Intersection southWestIntersection = mockedIntersection(PlayerColor.NONE, false, false);
            Intersection northWestIntersection = mockedIntersection(PlayerColor.NONE, false, false);
            
            ArrayList<Intersection> intersections = new ArrayList<Intersection>(
                    Arrays.asList(northIntersection, northEastIntersection, southEastIntersection, southIntersection, southWestIntersection, northWestIntersection));
            
            for(int i = 0; i < edges.size(); i++) {
                
                int firstIntersection = i;
                int secondIntersection = (i+1)%intersections.size();
                
                EasyMock.expect(board.getAllIntersectionsFromEdge(edges.get(i)))
                    .andStubReturn(new ArrayList<Intersection>(Arrays.asList(intersections.get(firstIntersection), intersections.get(secondIntersection))));
            }
            
            for(int i = 0; i < intersections.size(); i++) {
                
                int firstEdge = i;
                int secondEdge = Math.floorMod(i, edges.size());
                EasyMock.expect(board.getAllEdgesFromIntersection(intersections.get(i)))
                        .andStubReturn(new ArrayList<Edge>(Arrays.asList(emptyEdge, edges.get(firstEdge), edges.get(secondEdge))));
            }
            
            EasyMock.replay(board);
            
            CandidateRoad result = finder.getCandidateRoadFromEdge(northNorthEastRoad);
            
            assertEquals(6, result.length);
            assertEquals(color, result.color);
            
            EasyMock.verify(board);
            
            
        }
        
    }
    
    private void testTwoPieceRoad(PlayerColor color, PlayerColor otherColor, int expectedLength) {
        testTwoPieceRoadWithBuilding(color, mockedIntersection(PlayerColor.NONE, false, false), otherColor, expectedLength);
    }
    
    private void testTwoPieceRoadWithBuilding(PlayerColor color, Intersection middle, PlayerColor otherColor, int expectedLength) {
        Edge northRoad = mockedEdge(color);
        Edge southRoad = mockedEdge(otherColor);
        
        Intersection northIntersection = mockedIntersection(PlayerColor.NONE, false, false);
        Intersection middleIntersection = middle;
        Intersection southIntersection = mockedIntersection(PlayerColor.NONE, false, false);
        
        GameMap board = EasyMock.mock(GameMap.class);
        CandidateRoadFinder finder = new CandidateRoadFinder(board);
        
        EasyMock.expect(board.getAllIntersectionsFromEdge(northRoad))
                .andStubReturn(new ArrayList<Intersection>(Arrays.asList(northIntersection, middleIntersection)));
        EasyMock.expect(board.getAllIntersectionsFromEdge(southRoad))
                .andStubReturn(new ArrayList<Intersection>(Arrays.asList(southIntersection, middleIntersection)));
        
        EasyMock.expect(board.getAllEdgesFromIntersection(northIntersection))
                .andStubReturn(new ArrayList<Edge>(Arrays.asList(northRoad, emptyEdge, emptyEdge)));
        EasyMock.expect(board.getAllEdgesFromIntersection(middleIntersection))
            .andStubReturn(new ArrayList<Edge>(Arrays.asList(northRoad, southRoad, emptyEdge)));
        EasyMock.expect(board.getAllEdgesFromIntersection(southIntersection))
            .andStubReturn(new ArrayList<Edge>(Arrays.asList(southRoad, emptyEdge, emptyEdge)));
        
        EasyMock.replay(board);
        
        CandidateRoad result = finder.getCandidateRoadFromEdge(northRoad);
        
        assertEquals(color, result.color);
        assertEquals(expectedLength, result.length);
        
        EasyMock.verify(board);
    }
    
    private void testThreePieceRoad(PlayerColor northColor, PlayerColor middleColor, PlayerColor southColor, int expectedLength) {
        
        Edge northRoad = mockedEdge(northColor);
        Edge middleRoad = mockedEdge(middleColor);
        Edge southRoad = mockedEdge(southColor);
        
        Intersection northIntersection = mockedIntersection(PlayerColor.NONE, false, false);
        Intersection upperMiddleIntersection = mockedIntersection(PlayerColor.NONE, false, false);
        Intersection lowerMiddleIntersection = mockedIntersection(PlayerColor.NONE, false, false);
        Intersection southIntersection = mockedIntersection(PlayerColor.NONE, false, false);
        
        GameMap board = EasyMock.mock(GameMap.class);
        CandidateRoadFinder finder = new CandidateRoadFinder(board);
        
        EasyMock.expect(board.getAllIntersectionsFromEdge(northRoad))
                .andStubReturn(new ArrayList<Intersection>(Arrays.asList(northIntersection, upperMiddleIntersection)));
        EasyMock.expect(board.getAllIntersectionsFromEdge(middleRoad))
                .andStubReturn(new ArrayList<Intersection>(Arrays.asList(upperMiddleIntersection, lowerMiddleIntersection)));
        EasyMock.expect(board.getAllIntersectionsFromEdge(southRoad))
                .andStubReturn(new ArrayList<Intersection>(Arrays.asList(southIntersection, lowerMiddleIntersection)));
        
        EasyMock.expect(board.getAllEdgesFromIntersection(northIntersection))
                .andStubReturn(new ArrayList<Edge>(Arrays.asList(northRoad, emptyEdge, emptyEdge)));
        EasyMock.expect(board.getAllEdgesFromIntersection(upperMiddleIntersection))
            .andStubReturn(new ArrayList<Edge>(Arrays.asList(northRoad, middleRoad, emptyEdge)));
        EasyMock.expect(board.getAllEdgesFromIntersection(lowerMiddleIntersection))
                .andStubReturn(new ArrayList<Edge>(Arrays.asList(middleRoad, southRoad, emptyEdge)));
        EasyMock.expect(board.getAllEdgesFromIntersection(southIntersection))
            .andStubReturn(new ArrayList<Edge>(Arrays.asList(southRoad, emptyEdge, emptyEdge)));
        
        EasyMock.replay(board);
        
        CandidateRoad result = finder.getCandidateRoadFromEdge(northRoad);
        
        assertEquals(northColor, result.color);
        assertEquals(expectedLength, result.length);
        
        EasyMock.verify(board);
    }

    private Edge mockedEdge(PlayerColor color) {
        
        Edge edge = EasyMock.mock(Edge.class);
        EasyMock.expect(edge.getRoadColor()).andStubReturn(color);
        EasyMock.replay(edge);
        
        return edge;
    }
    
    private Intersection mockedIntersection(PlayerColor color, boolean hasSettlement, boolean hasCity) {
        
        Intersection intersection = EasyMock.mock(Intersection.class);
        EasyMock.expect(intersection.getBuildingColor()).andStubReturn(color);
        EasyMock.expect(intersection.hasSettlement()).andStubReturn(hasSettlement);
        EasyMock.expect(intersection.hasCity()).andStubReturn(hasCity);
        EasyMock.replay(intersection);
        
        return intersection;
        
    }

}
