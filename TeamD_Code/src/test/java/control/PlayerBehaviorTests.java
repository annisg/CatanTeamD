package control;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class PlayerBehaviorTests {

    SuperPlayer superPlayer;
    @BeforeEach
    public void setSuperPlayer(){
        superPlayer = new SuperPlayer(PlayerColor.WHITE);
    }
    @Test
    public void testSetAndGetPlayerName(){
        superPlayer.setPlayerName("Emma");
        String playerName = superPlayer.getName();
        assertEquals("Emma", playerName);
    }

    @Test
    public void testAddResources(){
        int resources = superPlayer.getResourceHandSize();
        assertEquals(18, resources);
    }

    @Test
    public void testAddDevelopmentCards(){
        superPlayer.addDevelopmentCard();
        int size = superPlayer.getDevelopmentCards().size();
        assertEquals(7, size);
    }

   @Test
    public void testGiveResourceCards(){
        superPlayer.giveResource(Resource.GRAIN, 1);
        int grainSize = superPlayer.getResourceCount(Resource.GRAIN);
        assertEquals(11, grainSize);
   }

   @Test
    public void testAllCardsPlayable(){
        superPlayer.addDevelopmentCard();
        superPlayer.letAllDevelopmentCardsBePlayed();
        boolean canPlay = superPlayer.getAllCardsPlayed();
        assertEquals(true, canPlay);
   }

   @Test
    public void testStealAllResources(){
        SuperPlayer toSteal = new SuperPlayer(PlayerColor.BLUE);
        superPlayer.stealAllOfResourceFrom(toSteal, Resource.GRAIN);
        assertEquals(0, toSteal.getResourceCount(Resource.GRAIN));
   }
}
