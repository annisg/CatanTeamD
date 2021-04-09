package control;

import exception.TooFewItemsException;
import gui.PlayerGUI;
import model.*;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.Test.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class RemoveHalfCardsTest {
    private PlayerColor colors[] = { PlayerColor.RED, PlayerColor.BLUE, PlayerColor.WHITE, PlayerColor.ORANGE };
    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
    @Test
    public void addResourcesTest() {

        ArrayList<Player> players = realPlayer(3);
        for(Player p : players){
            p.addResourec(Resource.GRAIN, 4);
            p.addResourec(Resource.LUMBER, 5);
            p.addResourec(Resource.BRICK, 4);
        }

        for(Player p : players){
            int expect = 13;
            assertEquals(expect, p.getResourceHandSize());
        }

    }

    @Test
    public void removeResourcesWhenSevenTest(){

        ArrayList<Player> players = realPlayer(3);
        for(Player p : players){
            p.addResourec(Resource.GRAIN, 4);
            p.addResourec(Resource.LUMBER, 5);
            p.addResourec(Resource.BRICK, 4);
        }

        for(Player p : players){
            p.discardResourceCard(Resource.GRAIN);
            p.discardResourceCard(Resource.LUMBER);
            p.discardResourceCard(Resource.GRAIN);
            p.discardResourceCard(Resource.LUMBER);
            p.discardResourceCard(Resource.GRAIN);
            p.discardResourceCard(Resource.LUMBER);

        }

        for(Player p : players){
            int size = p.getResourceHandSize();
            assertEquals(7, size);
        }
    }
    public void removeTooManyCardsTest(){
        Player p1 = new Player((PlayerColor.WHITE) );
        Player p2 = new Player((PlayerColor.RED));
        p1.addResourec(Resource.LUMBER, 4);
        p2.addResourec(Resource.GRAIN, 4);
        Exception exception = assertThrows(TooFewItemsException.class, () -> {
            p1.removeResource(Resource.LUMBER, 8);
        });
    }


    private ArrayList<Player> realPlayer(int num){
        ArrayList<Player> list = new ArrayList<Player>();
        for(int i =0; i<num; i++){
            list.add(new Player(PlayerColor.RED));
        }
        return list;
    }
    private ArrayList<Player> mockedPlayerList(int numPlayers) {

        ArrayList<Player> list = new ArrayList<Player>();

        for (int i = 0; i < numPlayers; i++) {

            Player player = EasyMock.mock(Player.class);

            EasyMock.expect(player.getColor()).andStubReturn(colors[i]);
            EasyMock.expect(player.getResourceCount(EasyMock.anyObject())).andStubReturn(0);
            EasyMock.expect(player.getDevelopmentCards()).andStubReturn(new ArrayList<DevelopmentCard>());

            list.add(player);
        }

        EasyMock.replay(list.toArray());

        return list;
    }

}
