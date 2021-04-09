package control;

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

        testIH.selectAndUseDevCard();
        EasyMock.verify(mockedRP, mockedCG, mockedPB, mockedDevCardSelector);
    }

}
