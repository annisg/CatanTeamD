package model;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.Test;

public class YearOfPlentyCard_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
    
    @Test
    public void testYearOfPlentyName() {
        DevelopmentCard testCard = new YearOfPlentyCard(messages);
        assertEquals("Year of Plenty", testCard.getName());
    }
    
    @Test
    public void testUseYearOfPlentyCard() {
        Player player = EasyMock.mock(Player.class);
        EasyMock.replay(player);
        
        try {
            DevelopmentCard testCard = new YearOfPlentyCard(messages);
            testCard.use(player);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Year of Plenty Cards are unimplemented.", e.getMessage());
        }
        EasyMock.verify(player);
    }
    
    @Test
    public void testCanBePlayed() {
        DevelopmentCard testCard = new YearOfPlentyCard(messages);
        assertFalse(testCard.canBePlayed());
        testCard.makePlayable();
        assertTrue(testCard.canBePlayed());
    }

}
