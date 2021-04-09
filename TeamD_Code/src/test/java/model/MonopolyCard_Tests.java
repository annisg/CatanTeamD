package model;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.Test;

public class MonopolyCard_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
    
    @Test
    public void testMonopolyName() {
        DevelopmentCard testCard = new MonopolyCard(messages);
        assertEquals("Monopoly", testCard.getName());
    }
    
    @Test
    public void testUseMonopolyCard() {
        DevelopmentCard testCard = new MonopolyCard(messages);
        Player player = EasyMock.mock(Player.class);
        player.removeDevelopmentCard(testCard);
        EasyMock.replay(player);
        
        try {
            testCard.use(player);
            //TODO: Likely Dialog Box, tests
            //fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Monopoly Cards are unimplemented.", e.getMessage());
        }
        EasyMock.verify(player);
    }
    
    @Test
    public void testCanBePlayed() {
        DevelopmentCard testCard = new MonopolyCard(messages);
        assertFalse(testCard.canBePlayed());
        testCard.makePlayable();
        assertTrue(testCard.canBePlayed());
    }

}
