package control;

import model.Player;
import model.PlayerColor;
import model.Resource;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PlayerTrading_Test {
    @Test
    public void testTradeWithOneCard(){
        Player p1 = new Player((PlayerColor.WHITE) );
        Player p2 = new Player((PlayerColor.RED));
        p1.addResourec(Resource.LUMBER, 4);
        p2.addResourec(Resource.GRAIN, 4);

        ArrayList<Resource> thing = p1.giveResourceForTrading(Resource.LUMBER, 2);
        assertEquals(2, p1.getResourceHandSize());
        p2.receiveResourceForTrading(thing);
        assertEquals(6, p2.getResourceHandSize());
    }

    @Test
    public void testTradeMultipleCards(){
        Player p1 = new Player((PlayerColor.WHITE) );
        Player p2 = new Player((PlayerColor.RED));
        p1.addResourec(Resource.LUMBER, 4);
        p2.addResourec(Resource.GRAIN, 6);

        ArrayList<Resource> thing = p1.giveResourceForTrading(Resource.LUMBER, 2);
        assertEquals(2, p1.getResourceHandSize());
        p2.receiveResourceForTrading(thing);
        assertEquals(8, p2.getResourceHandSize());



    }
}
