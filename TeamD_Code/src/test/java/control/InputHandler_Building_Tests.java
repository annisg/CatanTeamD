package control;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.Test;

import gui.Select1Frame;
import gui.Select2Frame;
import model.PieceBuilder;
import model.ResourceProducer;

public class InputHandler_Building_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    @Test
    public void testPlaceInitialSettlement() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedCG);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        Select2Frame mockedMandatoryIntersectionSelector = EasyMock.mock(Select2Frame.class);
        testIH.mandatoryIntersectionSelector = mockedMandatoryIntersectionSelector;

        mockedMandatoryIntersectionSelector.selectAndApply("Select intersection to place initial settlement:",
                testIH.placeInitialSettlement);
        EasyMock.replay(mockedRP, mockedPB, mockedMandatoryIntersectionSelector);

        testIH.placeInitialSettlement();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedMandatoryIntersectionSelector);
    }

    @Test
    public void testPlaceInitialSettlementRound2() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedCG);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        Select2Frame mockedMandatoryIntersectionSelector = EasyMock.mock(Select2Frame.class);
        testIH.mandatoryIntersectionSelector = mockedMandatoryIntersectionSelector;

        mockedMandatoryIntersectionSelector.selectAndApply("Select intersection to place initial settlement:",
                testIH.placeInitialSettlementRound2);
        EasyMock.replay(mockedRP, mockedPB, mockedMandatoryIntersectionSelector);

        testIH.placeInitialSettlementRound2();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedMandatoryIntersectionSelector);
    }

    @Test
    public void testPlaceSettlement() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedCG);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        BuildingHandler mockedPropertyBuilder = EasyMock.mock(BuildingHandler.class);
        testIH.propertyBuilder = mockedPropertyBuilder;

        Select2Frame mockedOptionalIntersectionSelector = EasyMock.mock(Select2Frame.class);
        testIH.optionalIntersectionSelector = mockedOptionalIntersectionSelector;

        EasyMock.expect(mockedPropertyBuilder.canPlaceSettlement(testIH.hasNotRolled)).andReturn(true);
        mockedOptionalIntersectionSelector.selectAndApply("Select intersection to place settlement:",
                testIH.placeSettlement);

        EasyMock.replay(mockedRP, mockedPB, mockedOptionalIntersectionSelector, mockedPropertyBuilder);

        testIH.placeSettlement();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedOptionalIntersectionSelector, mockedPropertyBuilder);
    }

    @Test
    public void testPlaceSettlementFails() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedCG);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        BuildingHandler mockedPropertyBuilder = EasyMock.mock(BuildingHandler.class);
        testIH.propertyBuilder = mockedPropertyBuilder;

        Select2Frame mockedOptionalIntersectionSelector = EasyMock.mock(Select2Frame.class);
        testIH.optionalIntersectionSelector = mockedOptionalIntersectionSelector;

        EasyMock.expect(mockedPropertyBuilder.canPlaceSettlement(testIH.hasNotRolled)).andReturn(false);

        EasyMock.replay(mockedRP, mockedPB, mockedOptionalIntersectionSelector, mockedPropertyBuilder);

        testIH.placeSettlement();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedOptionalIntersectionSelector, mockedPropertyBuilder);
    }

    @Test
    public void testPlaceCity() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedCG);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        BuildingHandler mockedPropertyBuilder = EasyMock.mock(BuildingHandler.class);
        testIH.propertyBuilder = mockedPropertyBuilder;

        Select2Frame mockedOptionalIntersectionSelector = EasyMock.mock(Select2Frame.class);
        testIH.optionalIntersectionSelector = mockedOptionalIntersectionSelector;

        EasyMock.expect(mockedPropertyBuilder.canPlaceCity(testIH.hasNotRolled)).andReturn(true);
        mockedOptionalIntersectionSelector.selectAndApply("Select intersection to place city:", testIH.placeCity);

        EasyMock.replay(mockedRP, mockedPB, mockedOptionalIntersectionSelector, mockedPropertyBuilder);

        testIH.placeCity();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedOptionalIntersectionSelector, mockedPropertyBuilder);
    }

    @Test
    public void testPlaceCityFails() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedCG);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        BuildingHandler mockedPropertyBuilder = EasyMock.mock(BuildingHandler.class);
        testIH.propertyBuilder = mockedPropertyBuilder;

        Select2Frame mockedOptionalIntersectionSelector = EasyMock.mock(Select2Frame.class);
        testIH.optionalIntersectionSelector = mockedOptionalIntersectionSelector;

        EasyMock.expect(mockedPropertyBuilder.canPlaceCity(testIH.hasNotRolled)).andReturn(false);

        EasyMock.replay(mockedRP, mockedPB, mockedOptionalIntersectionSelector, mockedPropertyBuilder);

        testIH.placeCity();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedOptionalIntersectionSelector, mockedPropertyBuilder);
    }

    @Test
    public void testPlaceInitialRoad() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedCG);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        Select2Frame mockedMandatoryEdgeSelector = EasyMock.mock(Select2Frame.class);
        testIH.mandatoryEdgeSelector = mockedMandatoryEdgeSelector;

        mockedMandatoryEdgeSelector.selectAndApply("Select edge to place initial road:", testIH.placeInitialRoad);
        EasyMock.replay(mockedRP, mockedPB, mockedMandatoryEdgeSelector);

        testIH.placeInitialRoad();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedMandatoryEdgeSelector);
    }

    @Test
    public void testPlaceRoad() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedCG);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        BuildingHandler mockedPropertyBuilder = EasyMock.mock(BuildingHandler.class);
        testIH.propertyBuilder = mockedPropertyBuilder;

        Select2Frame mockedOptionalEdgeSelector = EasyMock.mock(Select2Frame.class);
        testIH.optionalEdgeSelector = mockedOptionalEdgeSelector;

        EasyMock.expect(mockedPropertyBuilder.canPlaceRoad(testIH.hasNotRolled)).andReturn(true);
        mockedOptionalEdgeSelector.selectAndApply("Select edge to place road:", testIH.placeRoad);

        EasyMock.replay(mockedRP, mockedPB, mockedOptionalEdgeSelector, mockedPropertyBuilder);

        testIH.placeRoad();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedOptionalEdgeSelector, mockedPropertyBuilder);
    }

    @Test
    public void testPlaceRoadFails() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedCG);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        BuildingHandler mockedPropertyBuilder = EasyMock.mock(BuildingHandler.class);
        testIH.propertyBuilder = mockedPropertyBuilder;

        Select2Frame mockedOptionalEdgeSelector = EasyMock.mock(Select2Frame.class);
        testIH.optionalEdgeSelector = mockedOptionalEdgeSelector;

        EasyMock.expect(mockedPropertyBuilder.canPlaceRoad(testIH.hasNotRolled)).andReturn(false);

        EasyMock.replay(mockedRP, mockedPB, mockedOptionalEdgeSelector, mockedPropertyBuilder);

        testIH.placeRoad();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedOptionalEdgeSelector, mockedPropertyBuilder);
    }

    @Test
    public void testBuyDevelopmentCard() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedCG);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        BuildingHandler mockedPropertyBuilder = EasyMock.mock(BuildingHandler.class);
        testIH.propertyBuilder = mockedPropertyBuilder;

        mockedPropertyBuilder.buyDevelopmentCard(testIH.hasNotRolled);
        EasyMock.replay(mockedRP, mockedPB, mockedPropertyBuilder);

        testIH.buyDevelopmentCard();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedPropertyBuilder);
    }

    @Test
    public void testUseDevCard() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.replay(mockedCG);

        InputHandler testIH = new InputHandler(mockedRP, mockedCG, mockedPB);

        Select1Frame mockedDevCardSelector = EasyMock.mock(Select1Frame.class);
        testIH.devCardSelector = mockedDevCardSelector;

        mockedDevCardSelector.selectAndApply("Select card type to play:", testIH.useSelectedDevCard);
        EasyMock.replay(mockedRP, mockedPB, mockedDevCardSelector);

        testIH.useDevCard();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedDevCardSelector);
    }

}
