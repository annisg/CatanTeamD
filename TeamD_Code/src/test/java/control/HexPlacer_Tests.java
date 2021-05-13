package control;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import exception.InvalidHexPositionException;
import gui.*;
import model.*;


public class HexPlacer_Tests {

    GUIcomparisons compGUI = new GUIcomparisons();
    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    @Test
    public void testGetHexDrawable() {
        HexMap mockedHM = EasyMock.niceMock(HexMap.class);
        EasyMock.replay(mockedHM);
        
        HexPlacer testHP = new HexPlacer(mockedHM, messages);
        getHexDrawableTest(testHP, Resource.BRICK, 0, 0, 600, 800);
        getHexDrawableTest(testHP, Resource.GRAIN, 1, 3, 975, 670);
        getHexDrawableTest(testHP, Resource.LUMBER, 2, 4, 1050, 540);
        getHexDrawableTest(testHP, Resource.ORE, 3, 0, 525, 410);
        getHexDrawableTest(testHP, Resource.WOOL, 4, 2, 900, 280);
        
        EasyMock.verify(mockedHM);
    }
    
    private void getHexDrawableTest(HexPlacer placerUnderTest, Resource correctResource, int row, int col, int x, int y) {
        Hex mockedHex = EasyMock.niceMock(Hex.class);
        EasyMock.expect(mockedHex.getResource()).andReturn(correctResource);
        EasyMock.replay(mockedHex);
        
        HexGUI correctHexGUI = new HexGUI(correctResource, x, y);
        HexGUI resultHexGUI = placerUnderTest.getHexDrawable(mockedHex, row, col);
        
        assertTrue(compGUI.hexGUIequals(correctHexGUI, resultHexGUI));
        EasyMock.verify(mockedHex);
    }
    
    @Test
    public void testGetHexNumDrawable() {
        HexMap mockedHM = EasyMock.niceMock(HexMap.class);
        EasyMock.replay(mockedHM);
        
        HexPlacer testHP = new HexPlacer(mockedHM, messages);
        getHexNumDrawableTest(testHP, 4, false, 0, 0, 600, 800);
        getHexNumDrawableTest(testHP, 6, true, 1, 3, 975, 670);
        getHexNumDrawableTest(testHP, 12, true, 2, 4, 1050, 540);
        getHexNumDrawableTest(testHP, 8, false, 3, 0, 525, 410);
        getHexNumDrawableTest(testHP, 0, false, 4, 2, 900, 280);
        EasyMock.verify(mockedHM);
    }
    
    private void getHexNumDrawableTest(HexPlacer placerUnderTest, int resourceNum, boolean hasRobber, int row, int col, int x, int y) {
        Hex mockedHex = EasyMock.niceMock(Hex.class);
        EasyMock.expect(mockedHex.getRollResourceNumber()).andReturn(resourceNum);
        EasyMock.expect(mockedHex.hasRobber()).andReturn(hasRobber);
        EasyMock.replay(mockedHex);
        
        HexNumGUI correctHexNumGUI = new HexNumGUI(resourceNum, x, y, hasRobber, messages);
        HexNumGUI resultHexNumGUI = placerUnderTest.getHexNumDrawable(mockedHex, row, col);
        
        assertTrue(compGUI.hexNumGUIequals(correctHexNumGUI, resultHexNumGUI));
        EasyMock.verify(mockedHex);
    }
    
    @Test
    public void testGetAllDrawables1() {
        HexMap mockedHM = EasyMock.niceMock(HexMap.class);
        Hex hex1 = EasyMock.niceMock(Hex.class);
        Hex hex2 = EasyMock.niceMock(Hex.class);
        
        EasyMock.expect(mockedHM.getNumberOfRows()).andReturn(2);
        EasyMock.expect(mockedHM.getNumberOfHexesInRow(0)).andReturn(1);
        EasyMock.expect(mockedHM.getHex(new MapPosition(0, 0))).andReturn(hex1);
        
        EasyMock.expect(hex1.getResource()).andReturn(Resource.BRICK);
        EasyMock.expect(hex1.getRollResourceNumber()).andReturn(4);
        EasyMock.expect(hex1.hasRobber()).andReturn(false);
        
        EasyMock.expect(mockedHM.getNumberOfHexesInRow(0)).andReturn(1);
        EasyMock.expect(mockedHM.getNumberOfRows()).andReturn(2);
        EasyMock.expect(mockedHM.getNumberOfHexesInRow(1)).andReturn(1);
        EasyMock.expect(mockedHM.getHex(new MapPosition(1, 0))).andReturn(hex2);
        
        EasyMock.expect(hex2.getResource()).andReturn(Resource.GRAIN);
        EasyMock.expect(hex2.getRollResourceNumber()).andReturn(5);
        EasyMock.expect(hex2.hasRobber()).andReturn(true);
        
        EasyMock.expect(mockedHM.getNumberOfHexesInRow(1)).andReturn(1);
        EasyMock.expect(mockedHM.getNumberOfRows()).andReturn(2);
        
        EasyMock.replay(mockedHM, hex1, hex2);
        
        HexPlacer testHP = new HexPlacer(mockedHM, messages);
        ArrayList<Drawable> result = testHP.getAllDrawables();
        
        EasyMock.verify(mockedHM, hex1, hex2);
        assertEquals(4, result.size());
        assertTrue(compGUI.hexGUIequals((HexGUI)result.get(0), new HexGUI(Resource.BRICK, 600, 800)));
        assertTrue(compGUI.hexNumGUIequals((HexNumGUI)result.get(1), new HexNumGUI(4, 600, 800, false, messages)));
        assertTrue(compGUI.hexGUIequals((HexGUI)result.get(2), new HexGUI(Resource.GRAIN, 525, 670)));
        assertTrue(compGUI.hexNumGUIequals((HexNumGUI)result.get(3), new HexNumGUI(5, 525, 670, true, messages)));
    }
    
    @Test
    public void testGetAllDrawables2() {
        HexMap mockedHM = EasyMock.niceMock(HexMap.class);
        Hex hex1 = EasyMock.niceMock(Hex.class);
        Hex hex2 = EasyMock.niceMock(Hex.class);
        
        EasyMock.expect(mockedHM.getNumberOfRows()).andReturn(1);
        EasyMock.expect(mockedHM.getNumberOfHexesInRow(0)).andReturn(2);
        EasyMock.expect(mockedHM.getHex(new MapPosition(0, 0))).andReturn(hex1);
        
        EasyMock.expect(hex1.getResource()).andReturn(Resource.BRICK);
        EasyMock.expect(hex1.getRollResourceNumber()).andReturn(4);
        EasyMock.expect(hex1.hasRobber()).andReturn(false);
        
        EasyMock.expect(mockedHM.getNumberOfHexesInRow(0)).andReturn(2);
        EasyMock.expect(mockedHM.getHex(new MapPosition(0, 1))).andReturn(hex2);
        
        EasyMock.expect(hex2.getResource()).andReturn(Resource.GRAIN);
        EasyMock.expect(hex2.getRollResourceNumber()).andReturn(5);
        EasyMock.expect(hex2.hasRobber()).andReturn(true);
        
        EasyMock.expect(mockedHM.getNumberOfHexesInRow(0)).andReturn(2);
        EasyMock.expect(mockedHM.getNumberOfRows()).andReturn(2);
        
        EasyMock.replay(mockedHM, hex1, hex2);
        
        HexPlacer testHP = new HexPlacer(mockedHM, messages);
        ArrayList<Drawable> result = testHP.getAllDrawables();
        
        EasyMock.verify(mockedHM, hex1, hex2);
        assertEquals(4, result.size());
        assertTrue(compGUI.hexGUIequals((HexGUI)result.get(0), new HexGUI(Resource.BRICK, 600, 800)));
        assertTrue(compGUI.hexNumGUIequals((HexNumGUI)result.get(1), new HexNumGUI(4, 600, 800, false, messages)));
        assertTrue(compGUI.hexGUIequals((HexGUI)result.get(2), new HexGUI(Resource.GRAIN, 750, 800)));
        assertTrue(compGUI.hexNumGUIequals((HexNumGUI)result.get(3), new HexNumGUI(5, 750, 800, true, messages)));
    }
    
    @Test
    public void testSizeGetAllDrawables() {
        HexMap hm = new HexMap();
        hm.setUpBeginnerMap();
        HexPlacer testHP = new HexPlacer(hm, messages);
        assertEquals(38, testHP.getAllDrawables().size());
    }
    
    @Test
    public void testRefreshHexes() {
        HexMap mockedHM1 = EasyMock.niceMock(HexMap.class);
        HexMap mockedHM2 = EasyMock.niceMock(HexMap.class);
        EasyMock.replay(mockedHM1, mockedHM2);
        
        HexPlacer testHP = new HexPlacer(mockedHM1, messages);
        assertTrue(testHP.hexes.equals(mockedHM1));
        testHP.refreshHexes(mockedHM2);
        assertTrue(testHP.hexes.equals(mockedHM2));
        
        EasyMock.verify(mockedHM1, mockedHM2);
    }
    
    @Test
    public void testCalculatePositionWithBadPosition() {
        HexPlacer testHP = EasyMock.partialMockBuilder(HexPlacer.class).mock();
        Hex hex1 = EasyMock.niceMock(Hex.class);
        try {
            testHP.getHexDrawable(hex1, -1, 0);
            fail();
        }catch(InvalidHexPositionException e) {}
    }
}
