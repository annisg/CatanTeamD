package control;

import java.awt.Color;
import java.time.Year;
import java.util.*;

import gui.*;
import model.*;

public class PlayerPlacer {

    private final int xPlayerBackground = 1200;
    private final int yPlayerBackground = 25;
    private final int playerBackgroundWidth = 350;

    private int playerBackgroundHeight = 600;

    int numberOfPlayers = 0;
    TurnTracker turnTracker;

    private ObjectToColorConverter colorConverter;
    private ResourceBundle messages;

    public PlayerPlacer(TurnTracker turnTracker, ResourceBundle messages) {
        this.messages = messages;
        this.turnTracker = turnTracker;
        refreshPlayerNumber();
        this.colorConverter = new ObjectToColorConverter();

    }

    public void refreshPlayerNumber() {
        this.numberOfPlayers = turnTracker.getNumPlayers();
        playerBackgroundHeight = 600;
        // changing these next 4 lines produces 'equivalent' code, just visual
        // inspection
        if (numberOfPlayers < 3 || numberOfPlayers > 4) {
            numberOfPlayers = 3;
        } else if (numberOfPlayers == 4) {
            playerBackgroundHeight = 800;
        }
    }

    public TurnTracker getTurnTracker() {
        return turnTracker;
    }

    public ArrayList<Drawable> getCurrentPlayerGUI() {
        ArrayList<Drawable> playerGUI = new ArrayList<Drawable>();

        // changing / to * will not fail a test, it is only visually inspected
        playerGUI.add(new PlayerDisplayBackground(xPlayerBackground, yPlayerBackground, playerBackgroundWidth,
                playerBackgroundHeight / numberOfPlayers));

        Player currentPlayer = this.turnTracker.getCurrentPlayer();
        int currentPlayerOrder = findPlayerIndex(currentPlayer);
        Color realColorOfPlayer = getColorFromPlayerColor(currentPlayer.getColor());
        HashMap<Resource, Integer> resourceAmounts = getAllNonDesertResourceMap(currentPlayer);
        HashMap<String, Integer> developmentCardAmounts = getDevelopmentCardMapAmount(currentPlayer);
        playerGUI.add(new PlayerGUI(realColorOfPlayer, resourceAmounts, developmentCardAmounts, 0, currentPlayerOrder,
                messages, currentPlayer.getName()));

        return playerGUI;
    }

    int findPlayerIndex(Player playerToFind) {
        for (int i = 0; i < this.numberOfPlayers; i++) {
            if (this.turnTracker.getPlayer(i).equals(playerToFind)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Drawable> getAllPlayerGUIs() {
        ArrayList<Drawable> allPlayerGUIs = new ArrayList<Drawable>();

        allPlayerGUIs.add(new PlayerDisplayBackground(xPlayerBackground, yPlayerBackground, playerBackgroundWidth,
                playerBackgroundHeight));

        for (int i = 0; i < this.numberOfPlayers; i++) {
            Player ithPlayer = this.turnTracker.getPlayer(i);
            Color realColorOfPlayer = getColorFromPlayerColor(ithPlayer.getColor());
            HashMap<Resource, Integer> resourceAmounts = getAllNonDesertResourceMap(ithPlayer);
            HashMap<String, Integer> developmentCardAmounts = getDevelopmentCardMapAmount(ithPlayer);
            //adding the player name
            //String playerName = ithPlayer.getName();
            allPlayerGUIs
                    .add(new PlayerGUI(realColorOfPlayer, resourceAmounts, developmentCardAmounts, i, i, messages, "noon"));
        }

        return allPlayerGUIs;
    }
    
    public ArrayList<Drawable> getOtherPlayerGUIs() {
        ArrayList<Drawable> otherPlayerGUIs = new ArrayList<Drawable>();
        
        for(int i = 0; i < this.numberOfPlayers; i++) {
            Player ithPlayer = this.turnTracker.getPlayer(i);
            Color realColorOfPlayer = getColorFromPlayerColor(ithPlayer.getColor());
            HashMap<Resource, Integer> resourceAmounts = getAllNonDesertResourceMap(ithPlayer);
            HashMap<String, Integer> developmentCardAmounts = getDevelopmentCardMapAmount(ithPlayer);
            
            OtherPlayerGUI opg = new OtherPlayerGUI(realColorOfPlayer, resourceAmounts.values(), developmentCardAmounts.values(), 
                                            i, i, ithPlayer.getNumKnights(), ithPlayer.getName());
            otherPlayerGUIs.add(opg);
        }
        
        return otherPlayerGUIs;
    }

    private Color getColorFromPlayerColor(PlayerColor playerColor) {
        return this.colorConverter.playerColorToColor(playerColor);
    }

    private HashMap<Resource, Integer> getAllNonDesertResourceMap(Player player) {

        HashMap<Resource, Integer> resourceMap = new HashMap<Resource, Integer>();

        for (Resource resource : Resource.values()) {
            try {
                int numResources = player.getResourceCount(resource);
                resourceMap.put(resource, numResources);
            } catch (IllegalArgumentException e) {
            }
        }
        // changing this line to null will not fail a test because we are not doing null
        // checks
        return resourceMap;
    }

    public HashMap<String, Integer> getDevelopmentCardMap(Player player) {
        String[] abbreviations = {"K", "M", "R", "V", "Y"};
        DevelopmentCard [] devCards = {new KnightCard(new LargestArmy(new TurnTracker(null)), messages),
                new VictoryPointCard(messages), new MonopolyCard(messages),
                new RoadBuildingCard(messages), new YearOfPlentyCard(messages)};
        HashMap<String, Integer> cardMap = new HashMap<String, Integer>();
        for (String abbreviation : abbreviations) {
            cardMap.put(abbreviation, 0);
        }



        for (DevelopmentCard card : player.getDevelopmentCards()) {
           String abbreviation;
            if (card instanceof KnightCard) {
                abbreviation = "K";
            } else if (card instanceof MonopolyCard) {
                abbreviation = "M";
            } else if (card instanceof RoadBuildingCard) {
                abbreviation = "R";
            } else if (card instanceof VictoryPointCard) {
                abbreviation = "V";
            } else if (card instanceof YearOfPlentyCard) {
                abbreviation ="Y";
            } else {
                throw new RuntimeException();
            }

            int previousCount = cardMap.get(abbreviation);
            cardMap.put(abbreviation, previousCount + 1);
        }

        return cardMap;
    }

    public HashMap<String, Integer> getDevelopmentCardMapAmount(Player player) {
//        DevelopmentCard[] cards = {new KnightCard(new LargestArmy(player.getTracker()), messages), new MonopolyCard(messages), new RoadBuildingCard(messages),
//                new VictoryPointCard(messages), new YearOfPlentyCard(messages)};
        String [] abbreviations = {"K", "M", "R", "V", "Y"};
        HashMap<String, Integer> cardMap = new HashMap<String, Integer>();
        for (String card : abbreviations) {
            cardMap.put(card, 0);
        }

        for (DevelopmentCard card : player.getDevelopmentCards()) {

            String s;
            if (card instanceof KnightCard) {

                    s="K";

            } else if (card instanceof MonopolyCard) {

                    s="M";
            } else if (card instanceof  RoadBuildingCard) {

                   s="R";

            } else if (card instanceof VictoryPointCard) {

                    s="V";

            } else if (card instanceof YearOfPlentyCard) {

                    s = "Y";

            } else {
                throw new RuntimeException();
            }

            int previousCount = cardMap.get(s);
            cardMap.put(s, previousCount + 1);


        }

        return cardMap;
    }

}
