package control;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.Test;

import exception.ItemNotFoundException;
import gui.Select2Frame;
import model.*;

public class InputHandler_Dev_Card_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    @Test
    public void testConnotUseDevCardBeforeRolling() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(6);
        EasyMock.replay(mockedCG);
        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                .withConstructor(mockedRP, mockedCG, mockedPB).addMockedMethod("displayMessage").mock();

        testIH.displayMessage(messages.getString("InputHandler.12"));
        EasyMock.replay(mockedRP, mockedPB, testIH);

        testIH.canUseDevCard(KnightCard.class);
        EasyMock.verify(mockedRP, mockedCG, mockedPB, testIH);
    }

    @Test
    public void testCanUseKnightCard() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        TurnTracker mockedTT = EasyMock.strictMock(TurnTracker.class);
        Player mockedPlayer = EasyMock.strictMock(Player.class);
        KnightCard mockedKC = EasyMock.partialMockBuilder(KnightCard.class).addMockedMethod("use").mock();
        Select2Frame mockedSelector = EasyMock.strictMock(Select2Frame.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(mockedTT);
        EasyMock.replay(mockedCG);
        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);
        testIH.hexSelector = mockedSelector;

        EasyMock.expect(mockedRP.rollDice()).andReturn(4);
        EasyMock.expect(mockedTT.getCurrentPlayer()).andReturn(mockedPlayer);
        EasyMock.expect(mockedPlayer.findDevelopmentCard(KnightCard.class)).andReturn(mockedKC);
        mockedSelector.selectAndApply("Select hex to place robber:", testIH.performRobberTurn);
        mockedKC.use(mockedPlayer);
        EasyMock.replay(mockedRP, mockedPB, mockedTT, mockedSelector, mockedPlayer, mockedKC);

        testIH.rollDice();
        testIH.canUseDevCard(KnightCard.class);
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedTT, mockedSelector, mockedPlayer, mockedKC);
    }

    @Test
    public void testCanUseVictoryCard() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        TurnTracker mockedTT = EasyMock.strictMock(TurnTracker.class);
        Player mockedPlayer = EasyMock.strictMock(Player.class);
        VictoryPointCard mockedVPC = EasyMock.strictMock(VictoryPointCard.class);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(5);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(mockedTT);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.replay(mockedCG);
        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                .withConstructor(mockedRP, mockedCG, mockedPB).addMockedMethod("displayMessage").mock();

        EasyMock.expect(mockedRP.rollDice()).andReturn(4);
        EasyMock.expect(mockedTT.getCurrentPlayer()).andReturn(mockedPlayer);
        EasyMock.expect(mockedPlayer.findDevelopmentCard(KnightCard.class)).andReturn(mockedVPC);
        testIH.displayMessage("You do not need to play Victory Point Cards, they are always counted.");
        EasyMock.replay(mockedRP, mockedPB, mockedTT, mockedPlayer, mockedVPC, testIH);

        testIH.rollDice();
        testIH.canUseDevCard(KnightCard.class);
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedTT, mockedPlayer, mockedVPC, testIH);
    }

    @Test
    public void testCannotUseCardWhenNotFound() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        TurnTracker mockedTT = EasyMock.strictMock(TurnTracker.class);
        Player mockedPlayer = EasyMock.strictMock(Player.class);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.expectLastCall().times(5);
        EasyMock.expect(mockedCG.getPlayerTracker()).andReturn(mockedTT);
        EasyMock.replay(mockedCG);
        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        EasyMock.expect(mockedRP.rollDice()).andReturn(4);
        EasyMock.expect(mockedTT.getCurrentPlayer()).andReturn(mockedPlayer);
        EasyMock.expect(mockedPlayer.findDevelopmentCard(KnightCard.class)).andThrow(new ItemNotFoundException(""));
        EasyMock.replay(mockedRP, mockedPB, mockedTT, mockedPlayer);

        try {
            testIH.rollDice();
            testIH.canUseDevCard(KnightCard.class);
            fail();
        } catch (ItemNotFoundException e) {
        }
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedTT, mockedPlayer);
    }

}
