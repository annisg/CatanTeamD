package control;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.Test;

import exception.*;
import model.*;

public class BuildingHandler_Dev_Card_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    @Test
    public void testCannotBuyDevCardBeforeRolling() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        mockedIH.displayMessage("You must roll before buying a development card.");
        EasyMock.replay(mockedCG, mockedIH, mockedPB);

        assertFalse(testBH.canBuyDevCard(true));
        EasyMock.verify(mockedCG, mockedIH, mockedPB);
    }

    @Test
    public void testCannotBuyDevCardWithInsufficientResources() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        EasyMock.expect(mockedPB.hasResourcesToBuildDevelopmentCard(playerTracker.getPlayer(0))).andReturn(false);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        mockedIH.displayMessage("You do not have enough resources to buy a development card.");
        EasyMock.replay(mockedCG, mockedIH, mockedPB);

        assertFalse(testBH.canBuyDevCard(false));
        EasyMock.verify(mockedCG, mockedIH, mockedPB);
    }

    @Test
    public void testCanBuyDevCard() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = new BuildingHandler(mockedCG, mockedPB, mockedIH);

        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        EasyMock.expect(mockedPB.hasResourcesToBuildDevelopmentCard(playerTracker.getPlayer(0))).andReturn(true);
        EasyMock.replay(mockedCG, mockedIH, mockedPB);

        assertTrue(testBH.canBuyDevCard(false));
        EasyMock.verify(mockedCG, mockedIH, mockedPB);
    }

    @Test
    public void testBuyDevCardEmptyDeck() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = EasyMock.partialMockBuilder(BuildingHandler.class)
                .withConstructor(mockedCG, mockedPB, mockedIH).addMockedMethod("canBuyDevCard").mock();

        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(testBH.canBuyDevCard(false)).andReturn(true);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        EasyMock.expect(mockedPB.buildDevelopmentCard(playerTracker.getCurrentPlayer()))
                .andThrow(new IllegalStateException());
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        mockedIH.displayMessage("The deck is empty, you cannot buy a development card.");
        EasyMock.replay(mockedCG, mockedPB, mockedIH, testBH);

        testBH.buyDevelopmentCard(false);
        EasyMock.verify(mockedCG, mockedPB, mockedIH, testBH);
    }

    @Test
    public void testCannotBuyDevCard() {
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = EasyMock.partialMockBuilder(BuildingHandler.class)
                .withConstructor(mockedCG, mockedPB, mockedIH).addMockedMethod("canBuyDevCard").mock();

        EasyMock.expect(testBH.canBuyDevCard(true)).andReturn(false);
        EasyMock.replay(mockedCG, mockedPB, mockedIH, testBH);

        testBH.buyDevelopmentCard(true);
        EasyMock.verify(mockedCG, mockedPB, mockedIH, testBH);
    }

    @Test
    public void testCanDevCard() {
        ;
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        InputHandler mockedIH = EasyMock.strictMock(InputHandler.class);
        BuildingHandler testBH = EasyMock.partialMockBuilder(BuildingHandler.class)
                .withConstructor(mockedCG, mockedPB, mockedIH).addMockedMethod("canBuyDevCard").mock();

        TurnTracker playerTracker = new TurnTracker(new Random(0));
        playerTracker.setupPlayers(3);

        EasyMock.expect(testBH.canBuyDevCard(false)).andReturn(true);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(playerTracker);
        EasyMock.expect(mockedPB.buildDevelopmentCard(playerTracker.getCurrentPlayer())).andReturn("CARD NAME");
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        mockedIH.displayMessage("You have drawn a CARD NAME card.");
        mockedCG.drawPlayers();
        EasyMock.replay(mockedCG, mockedPB, mockedIH, testBH);

        testBH.buyDevelopmentCard(false);
        EasyMock.verify(mockedCG, mockedPB, mockedIH, testBH);
    }
}
