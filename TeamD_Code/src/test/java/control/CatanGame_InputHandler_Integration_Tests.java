package control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import control.CatanGame;
import control.InputHandler;
import gui.Select1Frame;
import model.GameMap;
import model.HexMap;
import model.PieceBuilder;
import model.Player;
import model.Resource;
import model.ResourceProducer;
import model.TurnTracker;
import model.VictoryPointCalculator;

public class CatanGame_InputHandler_Integration_Tests {
    
    private CatanGame testCatan = null;
    private InputHandler testHandler = null;
    
    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
    
    public List<Resource> getStandardResources() {
        Resource[] resourceArray = new Resource[] { Resource.DESERT, Resource.BRICK, Resource.BRICK, Resource.BRICK,
                Resource.ORE, Resource.ORE, Resource.ORE, Resource.LUMBER, Resource.LUMBER, Resource.LUMBER,
                Resource.LUMBER, Resource.WOOL, Resource.WOOL, Resource.WOOL, Resource.WOOL, Resource.GRAIN,
                Resource.GRAIN, Resource.GRAIN, Resource.GRAIN };
        List<Resource> resources = new ArrayList<Resource>(Arrays.asList(resourceArray));
        return resources;
    }
    
    public List<Integer> getStandardResourceNumbers() {
        Integer[] resourceTokens = new Integer[] { 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12 };
        List<Integer> resourceNumbers = new ArrayList<Integer>(Arrays.asList(resourceTokens));
        return resourceNumbers;
    }
    
    @Test
    public void testCustomBoardInteraction() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class)
                .addMockedMethod("getGameMap").addMockedMethod("buildModelFrame")
                .addMockedMethod("advancedInitialPlacement").addMockedMethod("getMessages").niceMock();
        
        ResourceProducer mockedRP = EasyMock.niceMock(ResourceProducer.class);
        PieceBuilder mockedPB = EasyMock.niceMock(PieceBuilder.class);
        GameMap mockedGameMap = EasyMock.niceMock(GameMap.class);
        Select1Frame mockedRS = EasyMock.niceMock(Select1Frame.class);
        HexMap mockedHexMap = EasyMock.niceMock(HexMap.class);    
        
        testCatan.model = mockedGameMap;
        EasyMock.expect(mockedGameMap.getHexMap()).andStubReturn(mockedHexMap);
        EasyMock.expect(mockedHexMap.getStandardResources()).andStubReturn(getStandardResources());
        EasyMock.expect(mockedHexMap.getStandardResourceNumbers()).andStubReturn(getStandardResourceNumbers());
        EasyMock.expect(this.testCatan.getMessages()).andStubReturn(messages);
        EasyMock.replay(testCatan, mockedRP, mockedPB, mockedGameMap, mockedRS, mockedHexMap);
        
        this.testHandler = EasyMock.partialMockBuilder(InputHandler.class).addMockedMethod("buildCustomSelectors").withConstructor(mockedRP, testCatan, mockedPB).niceMock();
        testHandler.resourceSelector = mockedRS;
        testCatan.inputHandler = testHandler;
        EasyMock.replay(testHandler);
        testHandler.selectCustomHexPlacement(getStandardResources(), getStandardResourceNumbers());
                
        testCatan.customHexPlacement();
        EasyMock.verify(testCatan, testHandler);
    }
    
    @Test
    public void testEndTurnInteraction() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).addMockedMethod("endTurn").addMockedMethod("getMessages")
                .addMockedMethod("getPlayerTracker").addMockedMethod("getPointCalculator").niceMock();
        this.testHandler = EasyMock.partialMockBuilder(InputHandler.class).mock();
        
        TurnTracker mockedTT = EasyMock.niceMock(TurnTracker.class);
        Player mockedPlayer = EasyMock.niceMock(Player.class);
        VictoryPointCalculator mockedVPC = EasyMock.niceMock(VictoryPointCalculator.class);
        EasyMock.expect(this.testCatan.getPlayerTracker()).andReturn(mockedTT);
        EasyMock.expect(mockedTT.getCurrentPlayer()).andReturn(mockedPlayer);
        EasyMock.expect(this.testCatan.getPointCalculator()).andReturn(mockedVPC);
        EasyMock.replay(mockedTT, mockedPlayer, mockedVPC);
             
        testCatan.inputHandler = testHandler;
        testHandler.catanGame = testCatan;
        EasyMock.replay(testCatan, testHandler);
        testHandler.endTurn();
        EasyMock.verify(testCatan, testHandler);
        
    }
}
