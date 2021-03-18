package control;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.Test;

import gui.*;
import model.*;

public class SpecialCardPlacer_Tests {

    GUIcomparisons comparator = new GUIcomparisons();
    ObjectToColorConverter converter = new ObjectToColorConverter();
    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    @Test
    public void testGetRightSize() {
        LongestRoad mockedLR = EasyMock.strictMock(LongestRoad.class);
        LargestArmy mockedLA = EasyMock.strictMock(LargestArmy.class);

        EasyMock.expect(mockedLR.getHolder()).andReturn(PlayerColor.BLUE);
        EasyMock.expect(mockedLA.getHolder()).andReturn(PlayerColor.RED);
        EasyMock.replay(mockedLR, mockedLA);

        SpecialCardPlacer testPlacer = new SpecialCardPlacer(mockedLR, mockedLA, messages);
        assertEquals(2, testPlacer.getSpecialCards().size());

        EasyMock.verify(mockedLR, mockedLA);
    }

    @Test
    public void testGetRightGUIs1() {
        LongestRoad mockedLR = EasyMock.strictMock(LongestRoad.class);
        LargestArmy mockedLA = EasyMock.strictMock(LargestArmy.class);

        EasyMock.expect(mockedLR.getHolder()).andReturn(PlayerColor.BLUE);
        EasyMock.expect(mockedLA.getHolder()).andReturn(PlayerColor.RED);
        EasyMock.replay(mockedLR, mockedLA);

        SpecialCardPlacer testPlacer = new SpecialCardPlacer(mockedLR, mockedLA, messages);
        ArrayList<Drawable> results = testPlacer.getSpecialCards();

        assertTrue(comparator.SpecialCardGUISimilar((SpecialCardGUI) results.get(0),
                new SpecialCardGUI("Longest Road", converter.bluePlayer, 1)));
        assertTrue(comparator.SpecialCardGUISimilar((SpecialCardGUI) results.get(1),
                new SpecialCardGUI("Largest Army", converter.redPlayer, 2)));
        EasyMock.verify(mockedLR, mockedLA);
    }

    @Test
    public void testGetRightGUIs2() {
        LongestRoad mockedLR = EasyMock.strictMock(LongestRoad.class);
        LargestArmy mockedLA = EasyMock.strictMock(LargestArmy.class);

        EasyMock.expect(mockedLR.getHolder()).andReturn(PlayerColor.NONE);
        EasyMock.expect(mockedLA.getHolder()).andReturn(PlayerColor.WHITE);
        EasyMock.replay(mockedLR, mockedLA);

        SpecialCardPlacer testPlacer = new SpecialCardPlacer(mockedLR, mockedLA, messages);
        ArrayList<Drawable> results = testPlacer.getSpecialCards();

        assertTrue(comparator.SpecialCardGUISimilar((SpecialCardGUI) results.get(0),
                new SpecialCardGUI("Longest Road", Color.black, 1)));
        assertTrue(comparator.SpecialCardGUISimilar((SpecialCardGUI) results.get(1),
                new SpecialCardGUI("Largest Army", converter.whitePlayer, 2)));
        EasyMock.verify(mockedLR, mockedLA);
    }

}
