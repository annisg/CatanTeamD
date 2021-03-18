package model;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.Test;

public class VictoryCard_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
    
    @Test
    public void testVictoryCardName() {
        DevelopmentCard testCard = new VictoryPointCard(messages);
        assertEquals("Victory Point", testCard.getName());
    }
    
    @Test
    public void testUseVictoryCard() {
        Player player = EasyMock.mock(Player.class);
        EasyMock.replay(player);
        
        try {
            DevelopmentCard testCard = new VictoryPointCard(messages);
            testCard.use(player);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Victory Point Cards cannot be played.", e.getMessage());
        }
        EasyMock.verify(player);
    }
    
    @Test
    public void testCanBePlayed() {
        DevelopmentCard testCard = new VictoryPointCard(messages);
        assertTrue(testCard.canBePlayed());
        testCard.makePlayable();
        assertTrue(testCard.canBePlayed());
    }

}
