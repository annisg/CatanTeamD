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
    public void testUseYearOfPlentyCardRemovesCard() {
        Player player = EasyMock.mock(Player.class);
        DevelopmentCard testCard = new YearOfPlentyCard(messages);
        
        player.removeDevelopmentCard(testCard);
        EasyMock.replay(player);

        testCard.use(player);
        
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
