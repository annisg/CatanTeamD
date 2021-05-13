package control;

import model.Player;
import model.PlayerColor;
import model.Resource;
import model.TurnTracker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlayerPlayerTrackerIntegration_Test {
    @Test
    public void testAddPlayerTracker(){
        Player player = new Player(PlayerColor.BLUE);
        Random random = new Random();
        TurnTracker turnTracker = new TurnTracker(random);
        turnTracker.setupPlayers(3);
        player.addTracker(turnTracker);
        assertEquals(3, player.getPlayersInGame());
    }

    @Test
    public void testPlayerGetsResources(){
        Random random = new Random();
        TurnTracker turnTracker = new TurnTracker(random);
        turnTracker.setupPlayers(3);
        turnTracker.setupBeginnerResourcesAndPieces();
        List<Player> players = turnTracker.getPlayers();
        for(Player p: players){
            switch(p.getColor()){
                case WHITE:
                    assertEquals(1, p.getResourceCount(Resource.GRAIN));
                    assertEquals(1, p.getResourceCount(Resource.BRICK));
                    assertEquals(1, p.getResourceCount(Resource.LUMBER));
                    break;
                case RED:
                    assertEquals(1, p.getResourceCount(Resource.GRAIN));
                   assertEquals(2, p.getResourceCount(Resource.LUMBER));
                    break;

                case ORANGE:
                    assertEquals(2, p.getResourceCount(Resource.GRAIN));
                    assertEquals(1, p.getResourceCount(Resource.ORE));
                    break;

            }
        }

    }

    @Test
    public void testGetPlayerColor(){
        Random random = new Random();
        TurnTracker turnTracker = new TurnTracker(random);
        turnTracker.setupPlayers(3);
        List<Player> players = turnTracker.getPlayers();
        ArrayList<PlayerColor> colors = new ArrayList<>();
        for(Player p: players){
            colors.add(p.getColor());
        }
        for(int i=0; i<colors.size(); i++){
            assertEquals(turnTracker.getPlayer(colors.get(i)), players.get(i));
        }

    }

    @Test
    public void testPassTurn(){
        TurnTracker turnTracker = new TurnTracker(new Random());
        turnTracker.setupPlayers(3);
        Player before = turnTracker.getCurrentPlayer();
        turnTracker.passInitialTurn();
        Player after = turnTracker.getCurrentPlayer();
        assertNotEquals(before, after);
    }


}
