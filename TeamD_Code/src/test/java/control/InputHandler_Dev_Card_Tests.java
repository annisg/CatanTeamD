package control;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import gui.InputComponent;
import org.easymock.EasyMock;
import org.junit.Test;

import exception.ItemNotFoundException;
import exception.VictoryPointPlayedException;
import gui.Select1Frame;
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

        testIH.playDevelopmentCard(KnightCard.class);
        EasyMock.verify(mockedRP, mockedCG, mockedPB, testIH);
    }

    @Test
    public void testCanUseKnightCard() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        Player mockedPlayer = EasyMock.strictMock(Player.class);
        KnightCard mockedKC = EasyMock.partialMockBuilder(KnightCard.class).addMockedMethod("use").mock();
        Select2Frame mockedSelector = EasyMock.strictMock(Select2Frame.class);
        mockedCG.input = EasyMock.strictMock(InputComponent.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.expect(mockedCG.getCurrentPlayer()).andReturn(mockedPlayer);
        EasyMock.replay(mockedCG);
        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);
        testIH.hexSelector = mockedSelector;

        EasyMock.expect(mockedRP.rollDice()).andReturn(4);
        EasyMock.expect(mockedPlayer.getDevelopmentCard(KnightCard.class)).andReturn(mockedKC);
        mockedKC.use(mockedPlayer);
        EasyMock.replay(mockedRP, mockedPB, mockedSelector, mockedPlayer, mockedKC);

        testIH.rollDice();
        testIH.playDevelopmentCard(KnightCard.class);
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedSelector, mockedPlayer, mockedKC);
    }

    @Test
    public void testCanUseRoadBuildingCard() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        TurnTracker mockedTT = EasyMock.strictMock(TurnTracker.class);
        Player mockedPlayer = EasyMock.strictMock(Player.class);
        RoadBuildingCard mockedRBC = EasyMock.partialMockBuilder(RoadBuildingCard.class).addMockedMethod("use").mock();
        Select2Frame mockedSelector = EasyMock.niceMock(Select2Frame.class);
        InputComponent mockedInputComponent = EasyMock.strictMock(InputComponent.class);
        mockedCG.input = mockedInputComponent;
        mockedInputComponent.placeRoadWithCard();
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.expect(mockedCG.getCurrentPlayer()).andReturn(mockedPlayer);
        EasyMock.replay(mockedCG);
        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);
        testIH.optionalEdgeSelector = mockedSelector;

        EasyMock.expect(mockedRP.rollDice()).andReturn(4);
        EasyMock.expect(mockedPlayer.getDevelopmentCard(RoadBuildingCard.class)).andReturn(mockedRBC);
        //mockedSelector.selectAndApply("Select edge to place road:", testIH.placeRoadWithCard);
        mockedRBC.use(mockedPlayer);
        EasyMock.replay(mockedRP, mockedPB, mockedTT, mockedSelector, mockedPlayer, mockedRBC);

        testIH.rollDice();
        testIH.playDevelopmentCard(RoadBuildingCard.class);
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedTT, mockedSelector, mockedPlayer, mockedRBC);
    }

    @Test
    public void testCanUseYOPCard() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        TurnTracker mockedTT = EasyMock.strictMock(TurnTracker.class);
        Player mockedPlayer = EasyMock.strictMock(Player.class);
        YearOfPlentyCard mockedYOPC = EasyMock.partialMockBuilder(YearOfPlentyCard.class).addMockedMethod("use").mock();
        Select1Frame mockedSelector = EasyMock.strictMock(Select1Frame.class);
        Select1Frame mockedSelector2 = EasyMock.strictMock(Select1Frame.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.expect(mockedCG.getCurrentPlayer()).andReturn(mockedPlayer);
        EasyMock.replay(mockedCG);
        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);
        testIH.resourceSelector = mockedSelector;
        testIH.resourceSelector2 = mockedSelector2;

        EasyMock.expect(mockedRP.rollDice()).andReturn(4);

        EasyMock.expect(mockedPlayer.getDevelopmentCard(RoadBuildingCard.class)).andReturn(mockedYOPC);
        mockedSelector.selectAndApply("Select a resource", testIH.addResource);
        mockedSelector2.selectAndApply("Select a resource", testIH.addResource);

        mockedYOPC.use(mockedPlayer);
        EasyMock.replay(mockedRP, mockedPB, mockedTT, mockedSelector, mockedSelector2, mockedPlayer, mockedYOPC);

        testIH.rollDice();
        testIH.playDevelopmentCard(RoadBuildingCard.class);
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedTT, mockedSelector, mockedSelector2, mockedPlayer, mockedYOPC);
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
        EasyMock.expect(mockedCG.getCurrentPlayer()).andReturn(mockedPlayer);
        EasyMock.expect(mockedCG.getMessages()).andReturn(messages);
        EasyMock.replay(mockedCG);
        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                .withConstructor(mockedRP, mockedCG, mockedPB).addMockedMethod("displayMessage").mock();

        EasyMock.expect(mockedRP.rollDice()).andReturn(4);
        EasyMock.expect(mockedPlayer.getDevelopmentCard(KnightCard.class)).andReturn(mockedVPC);
        testIH.displayMessage("You do not need to play Victory Point Cards, they are always counted.");
        mockedVPC.use(mockedPlayer);
        EasyMock.expectLastCall().andThrow(new VictoryPointPlayedException("unlike every other easter egg, this one is funny"));
        EasyMock.replay(mockedRP, mockedPB, mockedTT, mockedPlayer, mockedVPC, testIH);

        testIH.rollDice();
        testIH.playDevelopmentCard(KnightCard.class);
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
        EasyMock.expect(mockedCG.getCurrentPlayer()).andReturn(mockedPlayer);
        EasyMock.replay(mockedCG);
        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        EasyMock.expect(mockedRP.rollDice()).andReturn(4);
        EasyMock.expect(mockedPlayer.getDevelopmentCard(KnightCard.class)).andThrow(new ItemNotFoundException(""));
        EasyMock.replay(mockedRP, mockedPB, mockedTT, mockedPlayer);

        try {
            testIH.rollDice();
            testIH.playDevelopmentCard(KnightCard.class);
            fail();
        } catch (ItemNotFoundException e) {
        }
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedTT, mockedPlayer);
    }

}
