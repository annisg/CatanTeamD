package control;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.Test;

import exception.*;
import model.*;

public class BuildingHandler_Build_Property_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    @Test
    public void testMustRollDiceBeforePlacingRoad() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        mockedIH.displayMessage("You must roll before placing a road.");
        EasyMock.replay(mockedCG, mockedIH, mockedPB);

        assertFalse(testBH.canPlaceRoad(true));
        EasyMock.verify(mockedCG, mockedIH, mockedPB);
    }

    @Test
    public void testCannotPlaceRoadWithInsufficientResources() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        EasyMock.expect(mockedPB.hasResourcesToBuildRoad(playerTracker.getPlayer(0))).andReturn(false);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        mockedIH.displayMessage("You do not have enough resources to place a road.");
        EasyMock.replay(mockedCG, mockedIH, mockedPB);

        assertFalse(testBH.canPlaceRoad(false));
        EasyMock.verify(mockedCG, mockedIH, mockedPB);
    }

    @Test
    public void testCanPlaceRoad() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        EasyMock.expect(mockedPB.hasResourcesToBuildRoad(playerTracker.getPlayer(0))).andReturn(true);
        EasyMock.replay(mockedCG, mockedIH, mockedPB);

        assertTrue(testBH.canPlaceRoad(false));
        EasyMock.verify(mockedCG, mockedIH, mockedPB);
    }

    @Test
    public void testPlaceRoadBadPosition() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        GameMap mockedGM = EasyMock.strictMock(GameMap.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
        EasyMock.expect(mockedGM.getEdge(0, 4)).andThrow(new InvalidEdgePositionException());
        EasyMock.replay(mockedCG, mockedPB, mockedGM);

        try {
            testBH.placeRoad(0, 4);
            fail();
        } catch (InvalidEdgePositionException e) {
        }
        EasyMock.verify(mockedCG, mockedPB, mockedGM);
    }

    @Test
    public void testPlaceRoadBadEdge() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        GameMap mockedGM = EasyMock.strictMock(GameMap.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);
        Edge selectedEdge = new Edge();
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
        EasyMock.expect(mockedGM.getEdge(0, 0)).andReturn(selectedEdge);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        mockedPB.buildRoad(playerTracker.getCurrentPlayer(), selectedEdge);
        EasyMock.expectLastCall().andThrow(new PlaceBuildingException(""));
        EasyMock.replay(mockedCG, mockedPB, mockedGM);

        try {
            testBH.placeRoad(0, 0);
            fail();
        } catch (PlaceBuildingException e) {
        }
        EasyMock.verify(mockedCG, mockedPB, mockedGM);
    }

    @Test
    public void testPlaceRoadSuccessful() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        GameMap mockedGM = EasyMock.strictMock(GameMap.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);
        Edge selectedEdge = new Edge();
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
        EasyMock.expect(mockedGM.getEdge(0, 0)).andReturn(selectedEdge);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        mockedPB.buildRoad(playerTracker.getCurrentPlayer(), selectedEdge);
        mockedCG.justDrawProperty();
        mockedCG.drawPlayers();
        mockedCG.drawSpecialCards();
        EasyMock.replay(mockedCG, mockedPB, mockedGM);

        testBH.placeRoad(0, 0);
        EasyMock.verify(mockedCG, mockedPB, mockedGM);
    }

    @Test
    public void testMustRollDiceBeforePlacingSettlement() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        mockedIH.displayMessage("You must roll before placing a settlement.");
        EasyMock.replay(mockedCG, mockedIH, mockedPB);

        assertFalse(testBH.canPlaceSettlement(true));
        EasyMock.verify(mockedCG, mockedIH, mockedPB);
    }

    @Test
    public void testCannotPlaceSettlementWithInsufficientResources() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        EasyMock.expect(mockedPB.hasResourcesToBuildSettlement(playerTracker.getPlayer(0))).andReturn(false);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        mockedIH.displayMessage("You do not have enough resources to place a settlement.");
        EasyMock.replay(mockedCG, mockedIH, mockedPB);

        assertFalse(testBH.canPlaceSettlement(false));
        EasyMock.verify(mockedCG, mockedIH, mockedPB);
    }

    @Test
    public void testCanPlaceSettlement() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        EasyMock.expect(mockedPB.hasResourcesToBuildSettlement(playerTracker.getPlayer(0))).andReturn(true);
        EasyMock.replay(mockedCG, mockedIH, mockedPB);

        assertTrue(testBH.canPlaceSettlement(false));
        EasyMock.verify(mockedCG, mockedIH, mockedPB);
    }

    @Test
    public void testPlaceSettlementBadPosition() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        GameMap mockedGM = EasyMock.strictMock(GameMap.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
        EasyMock.expect(mockedGM.getIntersection(0, 10)).andThrow(new InvalidIntersectionPositionException());
        EasyMock.replay(mockedCG, mockedPB, mockedGM);

        try {
            testBH.placeSettlement(0, 10);
            fail();
        } catch (InvalidIntersectionPositionException e) {
        }
        EasyMock.verify(mockedCG, mockedPB, mockedGM);
    }

    @Test
    public void testPlaceSettlementBadEdge() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        GameMap mockedGM = EasyMock.strictMock(GameMap.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);
        Intersection selectedIntersection = new Intersection();
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
        EasyMock.expect(mockedGM.getIntersection(0, 0)).andReturn(selectedIntersection);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        mockedPB.buildSettlement(playerTracker.getCurrentPlayer(), selectedIntersection);
        EasyMock.expectLastCall().andThrow(new PlaceBuildingException(""));
        EasyMock.replay(mockedCG, mockedPB, mockedGM);

        try {
            testBH.placeSettlement(0, 0);
            fail();
        } catch (PlaceBuildingException e) {
        }
        EasyMock.verify(mockedCG, mockedPB, mockedGM);
    }

    @Test
    public void testPlaceSettlementSuccessful() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        GameMap mockedGM = EasyMock.strictMock(GameMap.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);
        Intersection selectedIntersection = new Intersection();
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
        EasyMock.expect(mockedGM.getIntersection(0, 0)).andReturn(selectedIntersection);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        mockedPB.buildSettlement(playerTracker.getCurrentPlayer(), selectedIntersection);
        mockedCG.justDrawProperty();
        mockedCG.drawPlayers();
        mockedCG.drawSpecialCards();
        EasyMock.replay(mockedCG, mockedPB, mockedGM);

        testBH.placeSettlement(0, 0);
        EasyMock.verify(mockedCG, mockedPB, mockedGM);
    }

    @Test
    public void testMustRollDiceBeforePlacingCity() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        mockedIH.displayMessage("You must roll before placing a city.");
        EasyMock.replay(mockedCG, mockedIH, mockedPB);

        assertFalse(testBH.canPlaceCity(true));
        EasyMock.verify(mockedCG, mockedIH, mockedPB);
    }

    @Test
    public void testCannotPlaceCityWithInsufficientResources() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        EasyMock.expect(mockedPB.hasResourcesToBuildCity(playerTracker.getPlayer(0))).andReturn(false);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        mockedIH.displayMessage("You do not have enough resources to place a city.");
        EasyMock.replay(mockedCG, mockedIH, mockedPB);

        assertFalse(testBH.canPlaceCity(false));
        EasyMock.verify(mockedCG, mockedIH, mockedPB);
    }

    @Test
    public void testCanPlaceCity() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        EasyMock.expect(mockedPB.hasResourcesToBuildCity(playerTracker.getPlayer(0))).andReturn(true);
        EasyMock.replay(mockedCG, mockedIH, mockedPB);

        assertTrue(testBH.canPlaceCity(false));
        EasyMock.verify(mockedCG, mockedIH, mockedPB);
    }

    @Test
    public void testPlaceCityBadPosition() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        GameMap mockedGM = EasyMock.strictMock(GameMap.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
        EasyMock.expect(mockedGM.getIntersection(0, 10)).andThrow(new InvalidIntersectionPositionException());
        EasyMock.replay(mockedCG, mockedPB, mockedGM);

        try {
            testBH.placeCity(0, 10);
            fail();
        } catch (InvalidIntersectionPositionException e) {
        }
        EasyMock.verify(mockedCG, mockedPB, mockedGM);
    }

    @Test
    public void testPlaceCityBadEdge() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        GameMap mockedGM = EasyMock.strictMock(GameMap.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);
        Intersection selectedIntersection = new Intersection();
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
        EasyMock.expect(mockedGM.getIntersection(0, 0)).andReturn(selectedIntersection);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        mockedPB.buildCity(playerTracker.getCurrentPlayer(), selectedIntersection);
        EasyMock.expectLastCall().andThrow(new PlaceBuildingException(""));
        EasyMock.replay(mockedCG, mockedPB, mockedGM);

        try {
            testBH.placeCity(0, 0);
            fail();
        } catch (PlaceBuildingException e) {
        }
        EasyMock.verify(mockedCG, mockedPB, mockedGM);
    }

    @Test
    public void testPlaceCitySuccessful() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        GameMap mockedGM = EasyMock.strictMock(GameMap.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);
        Intersection selectedIntersection = new Intersection();
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
        EasyMock.expect(mockedGM.getIntersection(0, 0)).andReturn(selectedIntersection);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        mockedPB.buildCity(playerTracker.getCurrentPlayer(), selectedIntersection);
        mockedCG.justDrawProperty();
        mockedCG.drawPlayers();
        EasyMock.replay(mockedCG, mockedPB, mockedGM);

        testBH.placeCity(0, 0);
        EasyMock.verify(mockedCG, mockedPB, mockedGM);
    }

    @Test
    public void testCanPlaceInitialSettlementSecondRound() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        GameMap mockedGM = EasyMock.strictMock(GameMap.class);
        Intersection selectedIntersection = new Intersection();
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
        EasyMock.expect(mockedGM.getIntersection(1, 1)).andReturn(selectedIntersection);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        mockedPB.buildInitialSettlementRound2(playerTracker.getCurrentPlayer(), selectedIntersection);
        mockedCG.justDrawProperty();
        mockedCG.drawPlayers();

        EasyMock.replay(mockedCG, mockedIH, mockedPB, mockedGM);

        testBH.placeInitialSettlementRound2(1, 1);

        EasyMock.verify(mockedCG, mockedIH, mockedPB, mockedGM);
    }

    @Test
    public void testPlaceInitialSettlement() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {

                CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
                PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
                InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
                GameMap mockedGM = EasyMock.strictMock(GameMap.class);
                Intersection mockedIntersection = EasyMock.mock(Intersection.class);
                BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);
                TurnTracker mockedTracker = EasyMock.mock(TurnTracker.class);
                Player mockedPlayer = EasyMock.mock(Player.class);

                EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
                EasyMock.expect(mockedGM.getIntersection(i, j)).andReturn(mockedIntersection);
                EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(mockedTracker);
                EasyMock.expect(mockedTracker.getCurrentPlayer()).andReturn(mockedPlayer);
                mockedPB.buildInitialSettlement(mockedPlayer, mockedIntersection);
                mockedCG.justDrawProperty();
                mockedCG.drawPlayers();

                EasyMock.replay(mockedCG, mockedPB, mockedIH, mockedGM, mockedIntersection, mockedTracker,
                        mockedPlayer);

                testBH.placeInitialSettlement(i, j);

                EasyMock.verify(mockedCG, mockedPB, mockedIH, mockedGM, mockedIntersection, mockedTracker,
                        mockedPlayer);
            }
        }
    }

    @Test
    public void testPlaceInitialRoad() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {

                CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
                PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
                InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
                GameMap mockedGM = EasyMock.strictMock(GameMap.class);
                Edge mockedEdge = EasyMock.mock(Edge.class);
                BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);
                TurnTracker mockedTracker = EasyMock.mock(TurnTracker.class);
                Player mockedPlayer = EasyMock.mock(Player.class);

                EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
                EasyMock.expect(mockedGM.getEdge(i, j)).andReturn(mockedEdge);
                EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(mockedTracker);
                EasyMock.expect(mockedTracker.getCurrentPlayer()).andReturn(mockedPlayer);
                mockedPB.buildInitialRoad(mockedPlayer, mockedEdge);
                mockedTracker.passInitialTurn();
                mockedCG.justDrawProperty();
                mockedCG.drawPlayers();
                mockedCG.advancedInitialPlacement();

                EasyMock.replay(mockedCG, mockedPB, mockedIH, mockedGM, mockedEdge, mockedTracker, mockedPlayer);

                testBH.placeInitialRoad(i, j);

                EasyMock.verify(mockedCG, mockedPB, mockedIH, mockedGM, mockedEdge, mockedTracker, mockedPlayer);
            }
        }
    }
}
