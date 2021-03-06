package model;

import javax.swing.*;
import java.util.*;

public class TurnTracker {

    PlayerColor[] colors = { PlayerColor.BLUE, PlayerColor.ORANGE, PlayerColor.WHITE, PlayerColor.RED };

    static List<Player> players;
    Random random;

    int currentPlayerIndex;
    boolean isInitialFirstRound;
    boolean wantPlayerNames = false;
    public TurnTracker(Random random) {

        this.players = new ArrayList<Player>();
        this.random = random;
        this.currentPlayerIndex = 0;
        this.isInitialFirstRound = true;
        wantPlayerNames = false;

    }

    public void enablePlayerNames(){
        wantPlayerNames = true;
    }

    public void disablePlayerNames(){
        wantPlayerNames = false;
    }
    public int getNumPlayers() {
        return players.size();
    }

    Player makePlayer(PlayerColor color) {
        return new Player(color);
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }
    
    public Player getPlayer(PlayerColor color) {
        for(Player p: players) {
            if(p.getColor() == color) {
                return p;
            }
        }
        return null;
    }

    public void setupPlayers(int numPlayers) {

        if (numPlayers != 3 && numPlayers != 4) {
            throw new IllegalArgumentException("Illegal number of players provided to setup.");
        }

        for (int i = 0; i < numPlayers; i++) {
            players.add(makePlayer(colors[i]));
        }

        for(int i =0; i<numPlayers; i++) {

            if (wantPlayerNames ==true){
                String playerName = "";
                playerName = promptForPlayerName(i);
                if(playerName==null || playerName.length()==0){
                    playerName = players.get(i).getColor().toString();
                }
                players.get(i).setPlayerName(playerName);
            }
            else{
                players.get(i).setPlayerName(players.get(i).getColor().toString());

            }
            players.get(i).addTracker(this);
        }
        Collections.shuffle(players, random);

    }

    public String promptForPlayerName(int i) {
        return JOptionPane.showInputDialog(null, "Enter in the name for Player " + ("" + (i + 1)));
    }

    public void setupBeginnerResourcesAndPieces() {

        for (int i = 0; i < getNumPlayers(); i++) {

            Player player = getPlayer(i);
            switch (player.getColor()) {

            case WHITE:
                player.giveResource(Resource.GRAIN, 1);
                player.giveResource(Resource.BRICK, 1);
                player.giveResource(Resource.LUMBER, 1);
                break;
            case RED:
                player.giveResource(Resource.GRAIN, 1);
                player.giveResource(Resource.LUMBER, 2);
                break;
            case BLUE:
                player.giveResource(Resource.BRICK, 1);
                player.giveResource(Resource.LUMBER, 1);
                player.giveResource(Resource.ORE, 1);
                break;
            case ORANGE:
                player.giveResource(Resource.GRAIN, 2);
                player.giveResource(Resource.ORE, 1);
                break;
            default:
                throw new RuntimeException("Illegal Player in tracker.");
            }

            for (int j = 0; j < 2; j++) {
                player.decrementSettlementCount();
                player.decrementRoadCount();
            }
        }

    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public List<Player> getPlayers(){
        return this.players;
    }
    public void passTurn() {

        currentPlayerIndex++;

        if (currentPlayerIndex == getNumPlayers()) {
            currentPlayerIndex = 0;
        }
    }

    public void passInitialTurn() {

        if (isInitialFirstRound) {
            currentPlayerIndex++;
            if (currentPlayerIndex == getNumPlayers()) {
                currentPlayerIndex--;
                isInitialFirstRound = false;
            }
        } else {
            if (currentPlayerIndex != 0) {
                currentPlayerIndex--;
            }
        }

    }

}
