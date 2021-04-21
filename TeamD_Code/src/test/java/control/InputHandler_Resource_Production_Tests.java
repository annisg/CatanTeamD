package control;

import static org.junit.Assert.*;

import java.util.Locale;
import java.text.MessageFormat;
import java.util.Random;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.Test;

import exception.*;
import gui.Select2Frame;
import model.*;

public class InputHandler_Resource_Production_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    @Test
    public void testConstructor() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(5);
        EasyMock.replay(mockedRP, mockedCG, mockedPB);
        new InputHandler(mockedRP, mockedCG, mockedPB);
        EasyMock.verify(mockedRP, mockedCG, mockedPB);
    }

    @Test
    public void testRollDice() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(5);
        EasyMock.expect(mockedRP.rollDice()).andReturn(4);
        EasyMock.replay(mockedRP, mockedCG, mockedPB);
        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);
        assertEquals(4, testIH.rollDice());
        EasyMock.verify(mockedRP, mockedCG, mockedPB);
    }

    @Test
    public void testRollDiceTwice() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(5);
        EasyMock.expect(mockedRP.rollDice()).andReturn(4);
        EasyMock.replay(mockedRP, mockedCG, mockedPB);
        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        assertEquals(4, testIH.rollDice());
        try {
            testIH.rollDice();
            fail();
        } catch (IllegalStateException e) {
        }
        EasyMock.verify(mockedRP, mockedCG, mockedPB);
    }

    @Test
    public void testEndTurnResetsRollDice() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        VictoryPointCalculator mockedPC = EasyMock.mock(VictoryPointCalculator.class);

        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(5);
        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(mockedRP.rollDice()).andReturn(4);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        EasyMock.expect(mockedCG.getPointCalculator()).andReturn(mockedPC);
        EasyMock.expect(mockedPC.isWinning(playerTracker.getCurrentPlayer())).andReturn(false);

        mockedCG.drawPlayers();
        EasyMock.expect(mockedRP.rollDice()).andReturn(5);
        EasyMock.replay(mockedRP, mockedCG, mockedPB);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);
        assertEquals(4, testIH.rollDice());
        testIH.endTurn();
        assertEquals(5, testIH.rollDice());

        EasyMock.verify(mockedRP, mockedCG, mockedPB);
    }

    @Test
    public void testIsRobberTurnValidNumbers() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(5);
        EasyMock.replay(mockedRP, mockedCG, mockedPB);
        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);
        assertTrue(testIH.isRobberTurn(7));
        assertFalse(testIH.isRobberTurn(2));
        assertFalse(testIH.isRobberTurn(6));
        assertFalse(testIH.isRobberTurn(8));
        assertFalse(testIH.isRobberTurn(12));
        EasyMock.verify(mockedRP, mockedCG, mockedPB);
    }

    @Test
    public void testIsRobberTurnInvalidNumbers() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(5);
        EasyMock.replay(mockedRP, mockedCG, mockedPB);
        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        try {
            testIH.isRobberTurn(1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            testIH.isRobberTurn(13);
            fail();
        } catch (IllegalArgumentException e) {
        }

        EasyMock.verify(mockedRP, mockedCG, mockedPB);
    }

    @Test
    public void testProduceResources() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        GameMap mockedGM = EasyMock.strictMock(GameMap.class);
        TurnTracker mockedTT = EasyMock.strictMock(TurnTracker.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);

        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(mockedTT);
        mockedRP.produceResources(mockedGM, mockedTT, 4);
        mockedCG.drawPlayers();

        EasyMock.replay(mockedRP, mockedGM, mockedCG, mockedTT, mockedPB);
        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class).withConstructor(mockedRP, mockedCG,
                mockedPB)
                .addMockedMethod("displayMessage").mock();
        testIH.displayMessage("Rolled a 4 for resource production.");
        EasyMock.replay(testIH);

        testIH.produceResources(4);
        EasyMock.verify(mockedRP, mockedGM, mockedTT, mockedCG, mockedPB, testIH);
    }

    @Test
    public void testMoveRobber() {
        GameMap testGM = new GameMap();
        testGM.setUpBeginnerMap(3);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);

        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.expect(mockedCG.getGameMap()).andStubReturn(testGM);
        mockedCG.drawScreen();
        EasyMock.replay(mockedRP, mockedCG, mockedPB);

        // Above mocks test calls in constructor, hence the separate replay below
        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class).withConstructor(mockedRP, mockedCG,
                mockedPB).addMockedMethod("selectPlayerToStealFrom", Integer.TYPE, Integer.TYPE).mock();
        testIH.selectPlayerToStealFrom(1,1);
        EasyMock.replay(testIH);

        testIH.moveRobberTo(1, 1);
        assertTrue(testGM.getHex(1, 1).hasRobber());
        assertFalse(testGM.getHex(2, 2).hasRobber());

        EasyMock.verify(mockedRP, mockedCG, mockedPB, testIH);
    }

    @Test
    public void testMoveRobberSamePosition() {
        GameMap testGM = new GameMap();
        testGM.setUpBeginnerMap(3);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);

        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(5);
        EasyMock.expect(mockedCG.getGameMap()).andReturn(testGM);
        EasyMock.replay(mockedRP, mockedCG, mockedPB);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);
        try {
            testIH.moveRobberTo(2, 2);
            fail();
        } catch (IllegalRobberMoveException e) {
        }
        EasyMock.verify(mockedRP, mockedCG, mockedPB);
    }

    @Test
    public void testMoveRobberBadPositions() {
        GameMap testGM = new GameMap();
        testGM.setUpBeginnerMap(3);
        CatanGame mockedCM = EasyMock.strictMock(CatanGame.class);
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);

        EasyMock.expect(mockedCM.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(5);
        EasyMock.expect(mockedCM.getGameMap()).andReturn(testGM);
        EasyMock.expect(mockedCM.getGameMap()).andReturn(testGM);
        EasyMock.replay(mockedRP, mockedCM, mockedPB);

        InputHandler testIH = new InputHandler(mockedRP, mockedCM, mockedPB);
        try {
            testIH.moveRobberTo(0, 3);
            fail();
        } catch (InvalidHexPositionException e) {
        }

        try {
            testIH.moveRobberTo(3, 4);
            fail();
        } catch (InvalidHexPositionException e) {
        }

        EasyMock.verify(mockedRP, mockedCM, mockedPB);
    }

    @Test
    public void testTryToRollDiceRobberTurn() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedRP, mockedCG, mockedPB);

        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                .withConstructor(mockedRP, mockedCG, mockedPB).addMockedMethod("rollDice")
                .addMockedMethod("isRobberTurn").addMockedMethod("displayMessage").createMock();
        Select2Frame mockedHexSelector = EasyMock.mock(Select2Frame.class);
        testIH.hexSelector = mockedHexSelector;

        EasyMock.expect(testIH.rollDice()).andReturn(7);
        EasyMock.expect(testIH.isRobberTurn(7)).andReturn(true);
        testIH.displayMessage(messages.getString("InputHandler.15"));
        mockedHexSelector.selectAndApply(messages.getString("InputHandler.16"), testIH.moveRobberTo);

        EasyMock.replay(testIH, mockedHexSelector);

        testIH.tryToRollDice();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, testIH, mockedHexSelector);
    }

    @Test
    public void testTryToRollDiceNormalTurn() {
        for (int i = 0; i < 12; i++) {
            if (i != 7) {
                ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
                CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
                PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
                EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
                EasyMock.replay(mockedRP, mockedCG, mockedPB);

                InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                        .withConstructor(mockedRP, mockedCG, mockedPB).addMockedMethod("rollDice")
                        .addMockedMethod("isRobberTurn").addMockedMethod("displayMessage")
                        .addMockedMethod("produceResources").createMock();

                EasyMock.expect(testIH.rollDice()).andReturn(6);
                EasyMock.expect(testIH.isRobberTurn(6)).andReturn(false);
                testIH.produceResources(6);

                EasyMock.replay(testIH);

                testIH.tryToRollDice();
                EasyMock.verify(mockedRP, mockedCG, mockedPB, testIH);
            }
        }
    }

    @Test
    public void testTryToRollDiceMoreThanOnceATurn() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedRP, mockedCG, mockedPB);

        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                .withConstructor(mockedRP, mockedCG, mockedPB).addMockedMethod("rollDice")
                .addMockedMethod("isRobberTurn").addMockedMethod("displayMessage").createMock();
        Select2Frame mockedHexSelector = EasyMock.mock(Select2Frame.class);

        EasyMock.expect(testIH.rollDice()).andThrow(new IllegalStateException());
        testIH.displayMessage(messages.getString("InputHandler.18"));

        EasyMock.replay(testIH);

        testIH.tryToRollDice();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, testIH);
    }
    
    @Test
    public void testCheatResources() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.mock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        Player mockedPlayer = EasyMock.mock(Player.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.expect(mockedCG.getCurrentPlayer()).andReturn(mockedPlayer).times(5);
        mockedCG.drawPlayers();
        EasyMock.expectLastCall().times(5);
        
        for(Resource r: Resource.values()) {
            if(r != Resource.DESERT) {
                mockedPlayer.giveResource(r, 1);
            }
        }
        
        EasyMock.replay(mockedRP, mockedCG, mockedPB, mockedPlayer);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        testIH.cheatResources();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedPlayer);
    }
}
