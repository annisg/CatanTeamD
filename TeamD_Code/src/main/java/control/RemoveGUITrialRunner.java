package control;

import model.Player;
import model.PlayerColor;
import model.Resource;

public class RemoveGUITrialRunner {
    public static void main (String [] args){
        Player player = new Player(PlayerColor.WHITE);
        player.addResourec(Resource.GRAIN, 2);
        player.addResourec(Resource.LUMBER, 3);
        System.out.println("Player has this many LUMBER: " + player.getResourceCount(Resource.GRAIN));
        player.discardHalfResourceHand();
    }
}
