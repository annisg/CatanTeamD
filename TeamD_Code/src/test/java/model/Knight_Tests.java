package model;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.easymock.EasyMock;
import org.junit.Test;

import exception.ItemNotFoundException;

public class Knight_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
    
    @Test
    public void testKnightName() {
        DevelopmentCard testCard = new KnightCard(null, messages);
        assertEquals("Knight", testCard.getName());
    }
    
    @Test
    public void testUseKnightIncrementsPlayerKnightCountAndRemovesCard() {
        
        Player testPlayer = EasyMock.mock(Player.class);
        LargestArmy largestArmy = EasyMock.mock(LargestArmy.class);
        DevelopmentCard knight = new KnightCard(largestArmy, messages);
        
        testPlayer.incrementKnightCount();
        testPlayer.removeDevelopmentCard(knight);
        largestArmy.updateLargestArmy();
        EasyMock.replay(testPlayer, largestArmy);
        
        knight.use(testPlayer);
        
        EasyMock.verify(testPlayer, largestArmy);
        
    }
    
    @Test
    public void testUseKnightNotInHandDoesNotIncreaseArmySize() {
        
        Player testPlayer = EasyMock.mock(Player.class);
        LargestArmy largestArmy = EasyMock.mock(LargestArmy.class);
        KnightCard knight = new KnightCard(largestArmy, messages);    
        
        testPlayer.removeDevelopmentCard(knight);
        EasyMock.expectLastCall().andThrow(new ItemNotFoundException(""));
        EasyMock.replay(testPlayer, largestArmy);
        
        try {
            knight.use(testPlayer);
            fail("Expected ItemNotFoundException");
        } catch (ItemNotFoundException e) {
            
        }
        
        EasyMock.verify(testPlayer, largestArmy);
        
    }
    
    @Test
    public void testCanBePlayed() {
        LargestArmy largestArmy = EasyMock.mock(LargestArmy.class);
        DevelopmentCard testCard = new KnightCard(largestArmy, messages); 
        EasyMock.replay(largestArmy);
        
        assertFalse(testCard.canBePlayed());
        testCard.makePlayable();
        assertTrue(testCard.canBePlayed());
        EasyMock.verify(largestArmy);
    }

}
