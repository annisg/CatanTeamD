package model;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.Test;

public class RoadBuildingCard_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
    
    @Test
    public void testRoadBuildingName() {
        DevelopmentCard testCard = new RoadBuildingCard(messages);
        assertEquals("Road Building", testCard.getName());
    }
    
    @Test
    public void testUseRoadBuildingCard() {
        Player player = EasyMock.mock(Player.class);
        EasyMock.replay(player);
        
        try {
            DevelopmentCard testCard = new RoadBuildingCard(messages);
            testCard.use(player);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Road Building Cards are unimplemented.", e.getMessage());
        }
        EasyMock.verify(player);
    }
    
    @Test
    public void testCanBePlayed() {
        DevelopmentCard testCard = new RoadBuildingCard(messages);
        assertFalse(testCard.canBePlayed());
        testCard.makePlayable();
        assertTrue(testCard.canBePlayed());
    }

}
