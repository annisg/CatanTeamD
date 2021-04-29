package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class RoadBuildingCard_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
    
    @Test
    public void testRoadBuildingName() {
        DevelopmentCard testCard = new RoadBuildingCard(messages);
        assertEquals("Road Building", testCard.getName());
    }
    
    @Test
    public void testUseRoadBuildingRemovesCard() {
        Player player = EasyMock.mock(Player.class);
        DevelopmentCard testCard = new RoadBuildingCard(messages);
        
        player.removeDevelopmentCard(testCard);
        EasyMock.replay(player);
        
        testCard.use(player);
       
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
