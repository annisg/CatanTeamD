package control;

import static org.junit.Assert.*;

import java.util.*;

import org.easymock.EasyMock;
import org.junit.Test;

import exception.ItemNotFoundException;
import gui.Select1Frame;
import gui.Select2Frame;
import model.*;

public class InputHandler_Custom_Board_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    @Test
    public void testSetCustomHexPlacementIntermediateStep() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        
        EasyMock.replay(mockedCG);
        
        Select1Frame mockedRS = EasyMock.strictMock(Select1Frame.class);
        Select1Frame mockedRNS = EasyMock.strictMock(Select1Frame.class);
        
        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                .withConstructor(mockedRP, mockedCG, mockedPB)
                .addMockedMethod("buildResourceSelector")
                .addMockedMethod("buildResourceNumberSelector")
                .mock();
        
        List<Resource> availableResources = Arrays.asList(Resource.WOOL, Resource.BRICK);
        List<Integer> availableNumbers = Arrays.asList(2, 6 ,12);
    
        
        EasyMock.expect(testIH.buildResourceSelector(availableResources)).andReturn(mockedRS);
        EasyMock.expect(testIH.buildResourceNumberSelector(availableNumbers)).andReturn(mockedRNS);
        mockedRS.selectAndApply("Select a resource for the next hex:", testIH.selectResource);
        
        EasyMock.replay(mockedRP, mockedPB, testIH, mockedRS, mockedRNS);

        testIH.selectCustomHexPlacement(availableResources, availableNumbers);
        EasyMock.verify(mockedRP, mockedCG, mockedPB, testIH, mockedRS, mockedRNS);
    }

    @Test
    public void testSetCustomHexPlacementFinished() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        GameMap mockedGM = EasyMock.strictMock(GameMap.class);
        HexMap mockedHM = EasyMock.strictMock(HexMap.class);
        
        List<Resource> availableResources = Arrays.asList();
        List<Integer> availableNumbers = Arrays.asList();
        
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        EasyMock.expect(mockedCG.getGameMap()).andReturn(mockedGM);
        EasyMock.expect(mockedGM.getHexMap()).andReturn(mockedHM);
        mockedHM.setUpCustomMap(availableResources, availableNumbers);
        mockedCG.buildModelFrame();
        mockedCG.advancedInitialPlacement();
        EasyMock.replay(mockedCG, mockedGM, mockedHM);
        
        Select1Frame mockedRS = EasyMock.strictMock(Select1Frame.class);
        Select1Frame mockedRNS = EasyMock.strictMock(Select1Frame.class);
        
        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                .withConstructor(mockedRP, mockedCG, mockedPB)
                .addMockedMethod("buildResourceSelector")
                .addMockedMethod("buildResourceNumberSelector")
                .mock();
        
        EasyMock.replay(mockedRP, mockedPB, testIH, mockedRS, mockedRNS);

        testIH.selectCustomHexPlacement(availableResources, availableNumbers);
        EasyMock.verify(mockedRP, mockedCG, mockedPB, testIH, mockedRS, mockedRNS, mockedGM, mockedHM);
    }
    
    @Test
    public void testSetCustomHexPlacementResourceSelected() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        
        EasyMock.replay(mockedCG);
        
        Select1Frame mockedRS = EasyMock.strictMock(Select1Frame.class);
        Select1Frame mockedRNS = EasyMock.strictMock(Select1Frame.class);
        
        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                .withConstructor(mockedRP, mockedCG, mockedPB)
                .addMockedMethod("buildResourceSelector")
                .addMockedMethod("buildResourceNumberSelector")
                .mock();
        
        List<Resource> availableResources = new ArrayList<Resource>(Arrays.asList(Resource.WOOL, Resource.BRICK));
        List<Integer> availableNumbers = new ArrayList<Integer>(Arrays.asList(2, 6 ,12));
    
        
        EasyMock.expect(testIH.buildResourceSelector(availableResources)).andReturn(mockedRS);
        EasyMock.expect(testIH.buildResourceNumberSelector(availableNumbers)).andReturn(mockedRNS);
        mockedRNS.selectAndApply(messages.getString("InputHandler.28"), testIH.selectResourceNumber);
        
        EasyMock.replay(mockedRP, mockedPB, testIH, mockedRS, mockedRNS);

        testIH.hexPlacementResources = availableResources;
        testIH.hexPlacementNumbers = availableNumbers;
        testIH.buildCustomSelectors(availableResources, availableNumbers);
        testIH.applyCustomHexResource(Resource.WOOL);
        
        assertFalse(availableResources.contains(Resource.WOOL));
        assertTrue(availableResources.contains(Resource.BRICK));
        assertEquals(1, availableResources.size());

        EasyMock.verify(mockedRP, mockedCG, mockedPB, testIH, mockedRS, mockedRNS);
    }
    
    @Test
    public void testSetCustomHexPlacementDesertSelected() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        
        EasyMock.replay(mockedCG);
        
        Select1Frame mockedRS = EasyMock.strictMock(Select1Frame.class);
        Select1Frame mockedRNS = EasyMock.strictMock(Select1Frame.class);
        
        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                .withConstructor(mockedRP, mockedCG, mockedPB)
                .addMockedMethod("buildResourceSelector")
                .addMockedMethod("buildResourceNumberSelector")
                .mock();
        
        List<Resource> availableResources = new ArrayList<Resource>(Arrays.asList(Resource.ORE, Resource.DESERT));
        List<Integer> availableNumbers = new ArrayList<Integer>(Arrays.asList(12));
    
        
        EasyMock.expect(testIH.buildResourceSelector(availableResources)).andReturn(mockedRS).times(2);
        EasyMock.expect(testIH.buildResourceNumberSelector(availableNumbers)).andReturn(mockedRNS).times(2);
        mockedRS.selectAndApply("Select a resource for the next hex:", testIH.selectResource);
        
        EasyMock.replay(mockedRP, mockedPB, testIH, mockedRS, mockedRNS);

        testIH.hexPlacementResources = availableResources;
        testIH.hexPlacementNumbers = availableNumbers;
        testIH.buildCustomSelectors(availableResources, availableNumbers);
        testIH.applyCustomHexResource(Resource.DESERT);
        
        assertFalse(availableResources.contains(Resource.DESERT));
        assertTrue(availableResources.contains(Resource.ORE));
        assertEquals(1, availableResources.size());

        EasyMock.verify(mockedRP, mockedCG, mockedPB, testIH, mockedRS, mockedRNS);
    }
    
    @Test
    public void testSetCustomHexPlacementNumberSelected() {
        ResourceProducer mockedRP = EasyMock.strictMock(ResourceProducer.class);
        CatanGame mockedCG = EasyMock.strictMock(CatanGame.class);
        PieceBuilder mockedPB = EasyMock.strictMock(PieceBuilder.class);
        EasyMock.expect(mockedCG.getMessages()).andStubReturn(messages);
        
        EasyMock.replay(mockedCG);
        
        Select1Frame mockedRS = EasyMock.strictMock(Select1Frame.class);
        Select1Frame mockedRNS = EasyMock.strictMock(Select1Frame.class);
        
        InputHandler testIH = EasyMock.partialMockBuilder(InputHandler.class)
                .withConstructor(mockedRP, mockedCG, mockedPB)
                .addMockedMethod("buildResourceSelector")
                .addMockedMethod("buildResourceNumberSelector")
                .mock();
        
        List<Resource> availableResources = new ArrayList<Resource>(Arrays.asList(Resource.ORE, Resource.DESERT));
        List<Integer> availableNumbers = new ArrayList<Integer>(Arrays.asList(11, 12));
    
        
        EasyMock.expect(testIH.buildResourceSelector(availableResources)).andReturn(mockedRS).times(2);
        EasyMock.expect(testIH.buildResourceNumberSelector(availableNumbers)).andReturn(mockedRNS).times(2);
        mockedRS.selectAndApply("Select a resource for the next hex:", testIH.selectResource);
        
        EasyMock.replay(mockedRP, mockedPB, testIH, mockedRS, mockedRNS);

        testIH.hexPlacementResources = availableResources;
        testIH.hexPlacementNumbers = availableNumbers;
        testIH.buildCustomSelectors(availableResources, availableNumbers);
        testIH.applyCustomResourceNumber(12);
        
        assertFalse(availableNumbers.contains(12));
        assertTrue(availableNumbers.contains(11));
        assertEquals(1, availableNumbers.size());

        EasyMock.verify(mockedRP, mockedCG, mockedPB, testIH, mockedRS, mockedRNS);
    }

}
