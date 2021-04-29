package control;

import static org.junit.jupiter.api.Assertions.*;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import exception.*;
import model.*;

public class InputHandler_End_Turn_And_Exception_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    @Test
    public void testEndTurnSwitchesPlayers() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        VictoryPointCalculator mockedPC = EasyMock.strictMock(VictoryPointCalculator.class);

        EasyMock.expect(mockedRP.rollDice()).andReturn(4);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(5);

        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        EasyMock.expect(mockedCG.getPointCalculator()).andReturn(mockedPC);
        EasyMock.expect(mockedPC.isWinning(playerTracker.getCurrentPlayer())).andReturn(false);

        mockedCG.endTurn();
        EasyMock.replay(mockedRP, mockedCG, mockedPB, mockedPC);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);
        testIH.rollDice();
        assertEquals(PlayerColor.WHITE, playerTracker.getCurrentPlayer().getColor());
        testIH.endTurn();
        assertEquals(PlayerColor.ORANGE, playerTracker.getCurrentPlayer().getColor());

        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedPC);
    }

    @Test
    public void testEndTurnUpdatesDevCards() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        VictoryPointCalculator mockedPC = EasyMock.strictMock(VictoryPointCalculator.class);
        Player mockedPlayer = EasyMock.strictMock(Player.class);
        TurnTracker mockedTT = EasyMock.strictMock(TurnTracker.class);

        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(5);
        EasyMock.expect(mockedRP.rollDice()).andReturn(4);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(mockedTT);
        EasyMock.expect(mockedTT.getCurrentPlayer()).andReturn(mockedPlayer);
        mockedPlayer.letAllDevelopmentCardsBePlayed();
        EasyMock.expect(mockedCG.getPointCalculator()).andReturn(mockedPC);
        EasyMock.expect(mockedPC.isWinning(mockedPlayer)).andReturn(false);
        mockedTT.passTurn();
        mockedCG.endTurn();
        EasyMock.replay(mockedRP, mockedCG, mockedPB, mockedPC, mockedPlayer, mockedTT);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);
        testIH.rollDice();
        testIH.endTurn();

        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedPC, mockedPlayer, mockedTT);
    }

    @Test
    public void testCannotEndTurnBeforeRolling() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedRP, mockedCG, mockedPB);

        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                .withConstructor(mockedRP, mockedCG, mockedPB).addMockedMethod("displayMessage").mock();
        testIH.displayMessage("You must roll the dice before ending your turn.");
        EasyMock.replay(testIH);

        assertEquals(PlayerColor.WHITE, playerTracker.getCurrentPlayer().getColor());
        testIH.endTurn();
        assertEquals(PlayerColor.WHITE, playerTracker.getCurrentPlayer().getColor());
        EasyMock.verify(mockedRP, mockedCG, mockedPB, testIH);
    }

    @Test
    public void testWinAtTurnEnd() {

        int[] scores = { 10, 11, Integer.MAX_VALUE };

        for (int score : scores) {
            ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
            CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
            PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
            VictoryPointCalculator mockedPC = EasyMock.mock(VictoryPointCalculator.class);
            EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);

            EasyMock.expect(mockedRP.rollDice()).andReturn(4);

            TurnTracker playerTracker = new TurnTracker(new Random(0));
            playerTracker.setupPlayers(3);
            EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);

            EasyMock.expect(mockedCG.getPointCalculator()).andReturn(mockedPC);
            EasyMock.expect(mockedPC.isWinning(playerTracker.getCurrentPlayer())).andReturn(true);
            EasyMock.expect(mockedPC.calculateForPlayer((playerTracker.getCurrentPlayer()))).andReturn(score);

            EasyMock.expect(mockedCG.getMessages()).andReturn(messages);

            mockedCG.endTurn();
            EasyMock.replay(mockedRP, mockedCG, mockedPB, mockedPC);
            InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                    .withConstructor(mockedRP, mockedCG, mockedPB).addMockedMethod("displayMessage").createStrictMock();
            testIH.displayMessage(MessageFormat.format(messages.getString("InputHandler.20"), score));
            EasyMock.replay(testIH);

            testIH.rollDice();
            assertEquals(PlayerColor.WHITE, playerTracker.getCurrentPlayer().getColor());
            testIH.endTurn();
            assertEquals(PlayerColor.WHITE, playerTracker.getCurrentPlayer().getColor());

            EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedPC, testIH);
        }
    }

    private void testHandlesException(Exception e, String desiredMessage, int n) {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedRP, mockedCG, mockedPB);

        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                .withConstructor(mockedRP, mockedCG, mockedPB).addMockedMethod("displayMessage").mock();
        testIH.displayMessage(desiredMessage);
        EasyMock.replay(testIH);

        testIH.handleException(e, n, n);
        EasyMock.verify(testIH, mockedRP, mockedCG, mockedPB);
    }

    @Test
    public void testHandlesAllNeededExceptions() {
        testHandlesException(new InvalidHexPositionException(), "(0,0) is not a valid hex position.", 0);
        testHandlesException(new InvalidHexPositionException(), "(1,1) is not a valid hex position.", 1);
        testHandlesException(new IllegalRobberMoveException(),
                "Robber is already at (0,0). Must move robber to new hex.", 0);
        testHandlesException(new IllegalRobberMoveException(),
                "Robber is already at (1,1). Must move robber to new hex.", 1);
        testHandlesException(new InvalidEdgePositionException(), "(0,0) is not a valid edge position.", 0);
        testHandlesException(new InvalidEdgePositionException(), "(1,1) is not a valid edge position.", 1);
        testHandlesException(new InvalidIntersectionPositionException(), "(0,0) is not a valid intersection position.",
                0);
        testHandlesException(new InvalidIntersectionPositionException(), "(1,1) is not a valid intersection position.",
                1);
        testHandlesException(new PlaceBuildingException("Some error."), "Some error.", 0);
        testHandlesException(new PlaceBuildingException("Some other error."), "Some other error.", 0);
        testHandlesException(new ItemNotFoundException("Some error."),
                "You do not have a card of that type that can be played.", 0);
        testHandlesException(new ItemNotFoundException("Some other error."),
                "You do not have a card of that type that can be played.", 0);

        testHandlesException(new RuntimeException("Test Error"), "Something went wrong! Test Error", 0);
        testHandlesException(new RuntimeException("Test Error 2"), "Something went wrong! Test Error 2", 0);
    }

}
