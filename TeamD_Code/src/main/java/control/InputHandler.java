package control;

import java.awt.*;
import java.text.MessageFormat;

import java.util.*;
import java.util.List;
import java.util.function.Function;

import javax.swing.JOptionPane;

import exception.*;
import gui.ResourceSelector;
import gui.Select1Frame;
import gui.Select2Frame;
import gui.TradeWithSpecificPlayerGUI;
import model.*;

public class InputHandler {
    private final Integer[] possibleIntersectionRows = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private final Integer[] possibleIntersectionCols = {1, 2, 3, 4, 5, 6};
    private final Integer[] possibleEdgeRows = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private final Integer[] possibleEdgeCols = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final Integer[] possibleHexRows = {1, 2, 3, 4, 5};
    private final Integer[] possibleHexCols = {1, 2, 3, 4, 5};
    private final Object[] possibleDevCards = {KnightCard.class, MonopolyCard.class, YearOfPlentyCard.class,
            VictoryPointCard.class, RoadBuildingCard.class};
    Select2Frame optionalIntersectionSelector;
    Select2Frame optionalEdgeSelector;
    Select2Frame mandatoryIntersectionSelector;
    Select2Frame mandatoryEdgeSelector;
    Select2Frame hexSelector;
    Select1Frame devCardSelector;
    Select1Frame resourceNumberSelector;
    Select1Frame resourceSelector;
    Select1Frame resourceSelector2;

    BuildingHandler propertyBuilder;
    
    private final Function<Integer[], Void> placeFreeRoad = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] edgeCoordinates) {
            propertyBuilder.placeRoad(new Point(edgeCoordinates[0], edgeCoordinates[1]), false);
            return null;
        }
    };
    boolean hasNotRolled;
    List<Integer> orderedResourceNumbers;
    List<Resource> orderedResources;
    private String[] possibleDevCardNames;
    private ResourceProducer resourceProducer;
    CatanGame catanGame;

    public Function<Object, Void> useSelectedDevCard = selected -> {
        playDevelopmentCard((Class) selected);
        return null;
    };
    final Function<Object, Void> addResource = selected -> {
        giveResourceToCurrentPlayer((Resource) selected);
        return null;
    };

    public InputHandler(ResourceProducer resourceProducer, CatanGame game, PieceBuilder builder) {
        this.resourceProducer = resourceProducer;
        this.catanGame = game;
        this.hasNotRolled = true;
        this.propertyBuilder = new BuildingHandler(game, builder, this);

        orderedResourceNumbers = new ArrayList<Integer>();
        orderedResources = new ArrayList<Resource>();

        possibleDevCardNames = new String[]{this.catanGame.getMessages().getString("InputHandler.4"),
                this.catanGame.getMessages().getString("InputHandler.3"),
                this.catanGame.getMessages().getString("InputHandler.2"),
                this.catanGame.getMessages().getString("InputHandler.1"),
                this.catanGame.getMessages().getString("InputHandler.0")};
        optionalIntersectionSelector = new Select2Frame(possibleIntersectionRows, possibleIntersectionCols, true, this);
        optionalEdgeSelector = new Select2Frame(possibleEdgeRows, possibleEdgeCols, true, this);
        mandatoryIntersectionSelector = new Select2Frame(possibleIntersectionRows, possibleIntersectionCols, false,
                this);
        mandatoryEdgeSelector = new Select2Frame(possibleEdgeRows, possibleEdgeCols, false, this);
        hexSelector = new Select2Frame(possibleHexRows, possibleHexCols, false, this);
        devCardSelector = new Select1Frame(possibleDevCardNames, possibleDevCards, true, this);
        resourceSelector = new ResourceSelector(false, this);
        resourceSelector2 = new ResourceSelector(false, this);
    }

    public ResourceBundle getMessages() {
        return this.catanGame.getMessages();
    }


    public Function<Point, Void> placeInitialSettlement = new Function<Point, Void>() {
        @Override
        public Void apply(Point mousePosition) {
            propertyBuilder.placeInitialSettlement(mousePosition);
            return null;
        }
    };

    public Function<Point, Void> placeInitialSettlementRound2 = new Function<Point, Void>() {
        @Override
        public Void apply(Point mousePosition) {
            propertyBuilder.placeInitialSettlementRound2(mousePosition);
            return null;
        }
    };

    public Function<Point, Void> placeSettlement = new Function<Point, Void>() {
        @Override
        public Void apply(Point mousePosition) {
            propertyBuilder.placeSettlement(mousePosition);
            return null;
        }
    };

    public Function<Point, Void> placeCity = new Function<Point, Void>() {
        @Override
        public Void apply(Point mousePosition) {
            propertyBuilder.placeCity(mousePosition);
            return null;
        }
    };

    public Function<Point, Void> placeInitialRoad = new Function<Point, Void>() {
        @Override
        public Void apply(Point mouseCoordinates) {
            propertyBuilder.placeInitialRoadAtClosestEdge(mouseCoordinates);
            return null;
        }
    };

    public Function<Point, Void> placeRoad = new Function<Point, Void>() {
        @Override
        public Void apply(Point mousePosition) {
            propertyBuilder.placeRoad(mousePosition, true);
            return null;
        }
    };
    
    List<Resource> hexPlacementResources;
    List<Integer> hexPlacementNumbers;
    public void selectCustomHexPlacement(List<Resource> availableResources, List<Integer> availableNumbers) {
        hexPlacementResources = availableResources;
        hexPlacementNumbers = availableNumbers;

        if (availableResources.size() != 0) {
            buildCustomSelectors(availableResources, availableNumbers);
            resourceSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.27"),
                    this.selectResource);
        } else {
            this.catanGame.getGameMap().getHexMap().setUpCustomMap(orderedResources, orderedResourceNumbers);
            this.catanGame.getGameMap().setUpCustomPorts();
            this.catanGame.buildModelFrame();
            this.catanGame.advancedInitialPlacement();
        }
    }

    void buildCustomSelectors(List<Resource> availableResources, List<Integer> availableNumbers) {
        resourceSelector = buildResourceSelector(availableResources);
        resourceNumberSelector = buildResourceNumberSelector(availableNumbers);
    }
    
    public Function<Object, Void> selectResourceNumber = new Function<Object, Void>() {
        @Override
        public Void apply(Object number) {
            applyCustomResourceNumber((Integer) number);
            return null;
        }
    };
    
    void applyCustomResourceNumber(Integer currentResourceNumber) {
        orderedResourceNumbers.add(currentResourceNumber);
        for (int i = 0; i < hexPlacementNumbers.size(); i++) {
            if (hexPlacementNumbers.get(i).equals(currentResourceNumber)) {
                hexPlacementNumbers.remove(i);
                break;
            }
        }

        selectCustomHexPlacement(hexPlacementResources, hexPlacementNumbers);
    }
    
    public Function<Object, Void> selectResource = new Function<Object, Void>() {
        @Override
        public Void apply(Object resource) {
            applyCustomHexResource((Resource) resource);
            return null;
        }
    };
    
    void applyCustomHexResource(Resource currentResource) {
        orderedResources.add(currentResource);
        for (int i = 0; i < hexPlacementResources.size(); i++) {
            if (hexPlacementResources.get(i).equals(currentResource)) {
                hexPlacementResources.remove(i);
                break;
            }
        }
        if (!currentResource.equals(Resource.DESERT)) {
            resourceNumberSelector.selectAndApply(catanGame.getMessages().getString("InputHandler.28"),
                    selectResourceNumber);
        } else {
            selectCustomHexPlacement(hexPlacementResources, hexPlacementNumbers);
        }
    }

    Select1Frame buildResourceNumberSelector(List<Integer> availableNumbers) {
        String[] numberStrings = new String[availableNumbers.size()];
        Object[] numberObjs = new Object[availableNumbers.size()];

        for (int i = 0; i < availableNumbers.size(); i++) {
            numberStrings[i] = availableNumbers.get(i).toString();
            numberObjs[i] = availableNumbers.get(i);
        }

        return buildNewSelect1Frame(numberStrings, numberObjs);
    }

    Select1Frame buildResourceSelector(List<Resource> availableResources) {
        String[] resourceStrings = new String[availableResources.size()];
        Object[] resourceObjs = new Object[availableResources.size()];

        for (int i = 0; i < availableResources.size(); i++) {
            resourceStrings[i] = availableResources.get(i).name();
            resourceObjs[i] = availableResources.get(i);
        }

        return buildNewSelect1Frame(resourceStrings, resourceObjs);
    }
    
    Select1Frame buildNewSelect1Frame(String[] displayStrings, Object[] underlyingObjs) {
        return new Select1Frame(displayStrings, underlyingObjs, false, this);
    }

    public void buyDevelopmentCard() {
        this.propertyBuilder.buyDevelopmentCard(this.hasNotRolled);
    }

    public void selectAndUseDevCard() {
        devCardSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.11"), useSelectedDevCard);
    }

    void playDevelopmentCard(Class selected) {
        if (this.hasNotRolled) {
            displayMessage(this.catanGame.getMessages().getString("InputHandler.12"));
            return;
        }

        Player player = getCurrentPlayer();
        DevelopmentCard card = player.getDevelopmentCard(selected);

        if (card instanceof KnightCard) {
            moveRobber();
        } else if (card instanceof YearOfPlentyCard) {
            offerPlayerTwoFreeResources();
        } else if (card instanceof RoadBuildingCard) {
            offerPlayerTwoFreeRoads();
        } else if (card instanceof MonopolyCard) {
            promptForStealingAllOfResource();
        }

        try {
            card.use(player);
        } catch (VictoryPointPlayedException e) {
            displayMessage(this.catanGame.getMessages().getString("InputHandler.14"));
        }
    }

    private void promptForStealingAllOfResource() {
        resourceSelector.selectAndApply("Select which resource to steal from all other players", stealAllOfResource);
    }

    private final Function<Object, Void> stealAllOfResource = selected -> {
        stealAllOfResource((Resource) selected);
        return null;
    };

    private void stealAllOfResource(Resource resource) {
        Player currentPlayer = getCurrentPlayer();
        TurnTracker turnTracker = catanGame.getPlayerTracker();

        int numPlayers = turnTracker.getNumPlayers();
        List<Player> inactivePlayers = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            inactivePlayers.add(turnTracker.getPlayer(i));
        }
        inactivePlayers.remove(currentPlayer);

        int countStolen = 0;
        for (Player player : inactivePlayers) {
            countStolen += currentPlayer.stealAllOfResourceFrom(player, resource);
        }
        catanGame.drawPlayers();

        displayMessage(String.format("Stole %d %s in total.", countStolen, resource.toString()));
    }

    public void tryToRollDice() {

        int numRolled;

        try {
            numRolled = this.rollDice();

            if (this.isRobberTurn(numRolled)) {
              this.rolledSeven();
            }
            else {

                this.produceResources(numRolled);
            }
        } catch (IllegalStateException resourceException) {
            this.displayMessage(this.catanGame.getMessages().getString("InputHandler.18"));
            return;
        }

    }

        
          
          
    public void discardCardsForEveryPlayer(){
        if(catanGame==null || catanGame.turnTracker == null){
            return;
        }
        List<Player> people = this.catanGame.getPlayerTracker().getPlayers();
        for(Player p : people){
                if(p.getResourceHandSize()>7) {
                    p.discardHalfResourceHand();
                }

        }
    }

    public void tradeWithPlayer(){
        List<Player> playerList  = this.catanGame.getPlayerTracker().getPlayers();
        Player currentPlayer = this.catanGame.getCurrentPlayer();
        TradeWithSpecificPlayerGUI tradeGUI = new TradeWithSpecificPlayerGUI(currentPlayer, playerList);

    }
    public int rollDice() {
        if (this.hasNotRolled) {
            this.hasNotRolled = false;
            return this.resourceProducer.rollDice();
        }
        throw new IllegalStateException();
    }

    private void rolledSeven() {
        this.displayMessage(this.catanGame.getMessages().getString("InputHandler.15"));
        moveRobber();
        discardCardsForEveryPlayer();
    }

    private void moveRobber() {
        catanGame.input.addMoveRobberToQueue();
    }

    public Function<Point, Void> moveRobberTo = new Function<Point, Void>() {
        @Override
        public Void apply(Point mousePosition) {
            selectPlayerToStealFromAtMapPosition(catanGame.getGameMap().getClosestValidRobberPosition(mousePosition));
            updateRobberPositionOnBoard(mousePosition);
            return null;
        }
    };

    void selectPlayerToStealFromAtMapPosition(MapPosition mapPosition) {
        ArrayList<Intersection> intersections = catanGame.getGameMap().getAllIntersectionsFromHex(mapPosition.getRow(), mapPosition.getColumn());

        LinkedHashSet<PlayerColor> adjacentColorsSet = new LinkedHashSet<>();
        for (Intersection i : intersections) {
            PlayerColor color = i.getBuildingColor();
            adjacentColorsSet.add(color);
        }
        adjacentColorsSet.remove(PlayerColor.NONE);
        adjacentColorsSet.add(PlayerColor.NONE);

        PlayerColor[] colors = adjacentColorsSet.toArray(new PlayerColor[0]);
        String[] colorNames = new String[colors.length];

        for (int i = 0; i < colors.length; i++) {
            colorNames[i] = colors[i].name();
        }

        Select1Frame playerSelector =
                new Select1Frame(colorNames, colors, false, this);
        playerSelector.selectAndApply("Select a player to steal a resource from", stealFromPlayer);
    }

    private final Function<Object, Void> stealFromPlayer = selected -> {
        stealFromPlayer((PlayerColor) selected);
        return null;
    };

    public boolean isRobberTurn(int numRolled) {
        if (numRolled < 2 || numRolled > 12) {
            throw new IllegalArgumentException();
        }
        return numRolled == 7;
    }

    public void produceResources(int numRolled) {
        this.displayMessage(
                MessageFormat.format(this.catanGame.getMessages().getString("InputHandler.17"), numRolled));

        this.resourceProducer.produceResources(this.catanGame.getGameMap(), getPlayerTracker(), numRolled);
        this.catanGame.drawPlayers();
    }

    private void stealFromPlayer(PlayerColor selectedPlayerColor) {
        if (selectedPlayerColor == PlayerColor.NONE) return;

        Player selectedPlayer = getPlayerTracker().getPlayer(selectedPlayerColor);

        Player currentPlayer = getCurrentPlayer();

        String stolenResource = currentPlayer.stealRandomResourceFrom(selectedPlayer).toString();
        catanGame.drawScreen();

        displayMessage(String.format("Stole %s from %s.", stolenResource, selectedPlayerColor.name().toLowerCase()));
    }

    private void offerPlayerTwoFreeRoads() {
        for (int i = 0; i < 2; i++) {
            catanGame.input.placeRoadWithCard();
        }
    }

    void offerPlayerTwoFreeResources() {
        resourceSelector.selectAndApply("Select a resource", addResource);
        resourceSelector2.selectAndApply("Select a resource", addResource);
    }

    private void giveResourceToCurrentPlayer(Resource resource) {
        Player currentPlayer = getCurrentPlayer();

        currentPlayer.giveResource(resource, 1);
        this.catanGame.drawPlayers();
    }

    void updateRobberPositionOnBoard(Point mousePosition) {
        this.catanGame.getGameMap().moveRobberToClosestHex(mousePosition);
        this.catanGame.drawScreen();
    }
    
    public Function<Point, Void> placeRoadWithCard = new Function<Point, Void>() {
        @Override
        public Void apply(Point mousePosition) {
            propertyBuilder.placeRoad(mousePosition, false);
            return null;
        }
    };

    public void endTurn() {
        if (this.hasNotRolled) {
            displayMessage(this.catanGame.getMessages().getString("InputHandler.19"));
            return;
        }
        TurnTracker playerTracker = getPlayerTracker();
        Player currentPlayer = playerTracker.getCurrentPlayer();
        currentPlayer.letAllDevelopmentCardsBePlayed();
        VictoryPointCalculator pointCalculator = this.catanGame.getPointCalculator();
        boolean hasWon = pointCalculator.isWinning(currentPlayer);
        if (hasWon) {
            int score = pointCalculator.calculateForPlayer(currentPlayer);
            displayMessage(MessageFormat.format(this.catanGame.getMessages().getString("InputHandler.20"), score));
        } else {
            this.hasNotRolled = true;
            playerTracker.passTurn();
        }

        this.catanGame.endTurn();
    }

    public Player getCurrentPlayer() {
        return catanGame.getCurrentPlayer();
    }

    private TurnTracker getPlayerTracker() {
        return catanGame.getPlayerTracker();
    }

    public void displayMessage(String message) {
        // Removing the following line is not tested for because it is only visually
        // impactful and based on a static method.
        JOptionPane.showMessageDialog(null, message);
    }

    public void handleException(Exception e, int row, int col) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(this, catanGame.getMessages());
        exceptionHandler.handleException(e, row, col);
    }

    public void cheatResources() {
        for(Resource resource : Resource.values()) {
            if(resource != Resource.DESERT) {
                giveResourceToCurrentPlayer(resource);
            }
        }
    }

}
