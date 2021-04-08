package control;

import java.text.MessageFormat;
import java.util.*;

import java.util.function.Function;

import javax.swing.JOptionPane;

import exception.*;
import gui.Select1Frame;
import gui.Select2Frame;
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
    private final Object[] possibleResources = {Resource.BRICK, Resource.GRAIN, Resource.LUMBER, Resource.ORE,
            Resource.WOOL};
    Select2Frame optionalIntersectionSelector;
    Select2Frame optionalEdgeSelector;
    Select2Frame mandatoryIntersectionSelector;
    Select2Frame mandatoryEdgeSelector;
    Select2Frame hexSelector;
    Select1Frame devCardSelector;
    Select1Frame resourceNumberSelector;
    Select1Frame resourceSelector;
    BuildingHandler propertyBuilder;
    public Function<Integer[], Void> placeInitialSettlement = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] intersectionCoordinates) {
            propertyBuilder.placeInitialSettlement(intersectionCoordinates[0], intersectionCoordinates[1]);
            return null;
        }
    };
    public Function<Integer[], Void> placeInitialSettlementRound2 = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] intersectionCoordinates) {
            propertyBuilder.placeInitialSettlementRound2(intersectionCoordinates[0], intersectionCoordinates[1]);
            return null;
        }
    };
    public Function<Integer[], Void> placeSettlement = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] intersectionCoordinates) {
            propertyBuilder.placeSettlement(intersectionCoordinates[0], intersectionCoordinates[1]);
            return null;
        }
    };
    public Function<Integer[], Void> placeCity = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] intersectionCoordinates) {
            propertyBuilder.placeCity(intersectionCoordinates[0], intersectionCoordinates[1]);
            return null;
        }
    };
    public Function<Integer[], Void> placeInitialRoad = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] edgeCoordinates) {
            propertyBuilder.placeInitialRoad(edgeCoordinates[0], edgeCoordinates[1]);
            return null;
        }
    };
    public Function<Integer[], Void> placeRoad = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] edgeCoordinates) {
            propertyBuilder.placeRoad(edgeCoordinates[0], edgeCoordinates[1], true);
            return null;
        }
    };
    private final Function<Integer[], Void> placeFreeRoad = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] edgeCoordinates) {
            propertyBuilder.placeRoad(edgeCoordinates[0], edgeCoordinates[1], false);
            return null;
        }
    };
    boolean hasNotRolled;
    List<Integer> orderedResourceNumbers;
    List<Resource> orderedResources;
    private String[] possibleDevCardNames;
    private String[] possibleResourceNames;
    private ResourceProducer resourceProducer;
    private CatanGame catanGame;

    public Function<Object, Void> useSelectedDevCard = selected -> {
        playDevelopmentCard((Class) selected);
        return null;
    };
    private final Function<Object, Void> addResource = selected -> {
        giveResourceToCurrentPlayer((Resource) selected);
        return null;
    };

    private List<Resource> hexPlacementResources;
    private List<Integer> hexPlacementNumbers;
    public Function<Object, Void> selectResourceNumber = new Function<Object, Void>() {
        @Override
        public Void apply(Object number) {
            Integer currentResourceNumber = (Integer) number;
            orderedResourceNumbers.add(currentResourceNumber);
            for (int i = 0; i < hexPlacementNumbers.size(); i++) {
                if (hexPlacementNumbers.get(i).equals(currentResourceNumber)) {
                    hexPlacementNumbers.remove(i);
                    break;
                }
            }

            selectCustomHexPlacement(hexPlacementResources, hexPlacementNumbers);
            return null;
        }
    };
    public Function<Object, Void> selectResource = new Function<Object, Void>() {
        @Override
        public Void apply(Object resource) {
            Resource currentResource = (Resource) resource;
            orderedResources.add(currentResource);
            for (int i = 0; i < hexPlacementResources.size(); i++) {
                if (hexPlacementResources.get(i).equals(currentResource)) {
                    hexPlacementResources.remove(i);
                    break;
                }
            }
            if (!resource.equals(Resource.DESERT)) {
                resourceNumberSelector.selectAndApply(catanGame.getMessages().getString("InputHandler.28"),
                        selectResourceNumber);
            } else {
                selectCustomHexPlacement(hexPlacementResources, hexPlacementNumbers);
            }
            return null;
        }
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
        // TODO: extract strings
        possibleResourceNames = new String[]{"Brick", "Grain", "Lumber", "Ore", "Wool"};
        optionalIntersectionSelector = new Select2Frame(possibleIntersectionRows, possibleIntersectionCols, true, this);
        optionalEdgeSelector = new Select2Frame(possibleEdgeRows, possibleEdgeCols, true, this);
        mandatoryIntersectionSelector = new Select2Frame(possibleIntersectionRows, possibleIntersectionCols, false,
                this);
        mandatoryEdgeSelector = new Select2Frame(possibleEdgeRows, possibleEdgeCols, false, this);
        hexSelector = new Select2Frame(possibleHexRows, possibleHexCols, false, this);
        devCardSelector = new Select1Frame(possibleDevCardNames, possibleDevCards, true, this);
        resourceSelector = new Select1Frame(possibleResourceNames, possibleResources, false, this);
    }

    public ResourceBundle getMessages() {
        return this.catanGame.getMessages();
    }

    public void placeInitialSettlement() {
        mandatoryIntersectionSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.5"),
                placeInitialSettlement);
    }

    public void placeInitialSettlementRound2() {
        mandatoryIntersectionSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.6"),
                placeInitialSettlementRound2);
    }

    public void placeSettlement() {
        if (this.propertyBuilder.canPlaceSettlement(this.hasNotRolled)) {
            optionalIntersectionSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.7"),
                    this.placeSettlement);
        }
    }

    public void placeCity() {
        if (this.propertyBuilder.canPlaceCity(this.hasNotRolled)) {
            optionalIntersectionSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.8"),
                    this.placeCity);
        }
    }

    public void placeInitialRoad() {
        mandatoryEdgeSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.9"),
                placeInitialRoad);
    }

    public void placeRoad() {
        if (this.propertyBuilder.canPlaceRoad(this.hasNotRolled)) {
            optionalEdgeSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.10"),
                    this.placeRoad);
        }
    }

    public void selectCustomHexPlacement(List<Resource> availableResources, List<Integer> availableNumbers) {
        hexPlacementResources = availableResources;
        hexPlacementNumbers = availableNumbers;

        if (availableResources.size() != 0) {
            resourceSelector = buildResourceSelector(availableResources);
            resourceNumberSelector = buildResourceNumberSelector(availableNumbers);
            resourceSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.27"),
                    this.selectResource);
        } else {
            this.catanGame.getGameMap().getHexMap().setUpCustomMap(orderedResources, orderedResourceNumbers);
            this.catanGame.buildModelFrame();
            this.catanGame.advancedInitialPlacement();
        }
    }

    Select1Frame buildResourceNumberSelector(List<Integer> availableNumbers) {
        String[] numberStrings = new String[availableNumbers.size()];
        Object[] numberObjs = new Object[availableNumbers.size()];

        for (int i = 0; i < availableNumbers.size(); i++) {
            numberStrings[i] = availableNumbers.get(i).toString();
            numberObjs[i] = availableNumbers.get(i);
        }

        return new Select1Frame(numberStrings, numberObjs, false, this);
    }

    Select1Frame buildResourceSelector(List<Resource> availableResources) {
        String[] resourceStrings = new String[availableResources.size()];
        Object[] resourceObjs = new Object[availableResources.size()];

        for (int i = 0; i < availableResources.size(); i++) {
            resourceStrings[i] = availableResources.get(i).name();
            resourceObjs[i] = availableResources.get(i);
        }

        return new Select1Frame(resourceStrings, resourceObjs, false, this);
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
            promptToMoveRobber();
        } else if (card instanceof YearOfPlentyCard) {
            offerPlayerTwoFreeResources();
        } else if (card instanceof RoadBuildingCard) {
            offerPlayerTwoFreeRoads();
        }

        try {
            card.use(player);
        } catch (VictoryPointPlayedException e) {
            displayMessage(this.catanGame.getMessages().getString("InputHandler.14"));
        }
    }

    public void tryToRollDice() {
        int numRolled;

        try {
            numRolled = this.rollDice();
        } catch (IllegalStateException resourceException) {
            this.displayMessage(this.catanGame.getMessages().getString("InputHandler.18"));
            return;
        }

        if (this.isRobberTurn(numRolled)) {
            this.rolledSeven();
        } else {
            this.produceResources(numRolled);
        }
    }

    private void rolledSeven() {
        this.displayMessage(this.catanGame.getMessages().getString("InputHandler.15"));

        // TODO @Pavani: All Players w/ >7 Cards Discard Here.
        promptToMoveRobber();
    }

    private void promptToMoveRobber() {
        hexSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.13"),
                this.moveRobberTo);
    }

    public Function<Integer[], Void> moveRobberTo = hexCoordinates -> {
        moveRobberTo(hexCoordinates[0], hexCoordinates[1]);
        return null;
    };

    void moveRobberTo(int row, int col) {
        updateRobberPositionOnBoard(row, col);
        selectPlayerToStealFrom(row, col);
    }

//    private Player playerToStealFrom;
//    private final Function<Object, Void> stealOneResource = selected -> {
//        stealOneResource((Resource) selected);
//        return null;
//    };

    private void selectPlayerToStealFrom(int row, int col) {
        ArrayList<Intersection> intersections = catanGame.getGameMap().getAllIntersectionsFromHex(row, col);

        HashSet<PlayerColor> adjacentColors = new HashSet<>();
        HashSet<String> adjacentColorNames = new HashSet<String>();
        for (Intersection i : intersections) {
            PlayerColor color = i.getBuildingColor();
            adjacentColors.add(color);
            adjacentColorNames.add(color.name());
        }
        adjacentColors.remove(PlayerColor.NONE);
        adjacentColors.add(PlayerColor.NONE);
        adjacentColorNames.add(PlayerColor.NONE.name());

        Select1Frame playerSelector =
                new Select1Frame(adjacentColorNames.toArray(new String[0]), adjacentColors.toArray(), false, this);
        playerSelector.selectAndApply("Select a player to steal a resource from", stealFromPlayer);
    }

    private final Function<Object, Void> stealFromPlayer = selected -> {
        stealFromPlayer((PlayerColor) selected);
        return null;
    };

    public int rollDice() {
        if (this.hasNotRolled) {
            this.hasNotRolled = false;
            return this.resourceProducer.rollDice();
        }
        throw new IllegalStateException();
    }

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
            optionalEdgeSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.10"),
                    this.placeFreeRoad);
        }
    }

    private void offerPlayerTwoFreeResources() {
        for (int i = 0; i < 2; i++) {
            resourceSelector.selectAndApply("Select a resource", addResource);
        }
    }

    private void giveResourceToCurrentPlayer(Resource resource) {
        Player currentPlayer = getCurrentPlayer();

        currentPlayer.giveResource(resource, 1);
        this.catanGame.drawPlayers();
    }

    void updateRobberPositionOnBoard(int row, int col) {
        this.catanGame.getGameMap().moveRobberToPosition(row, col);
        this.catanGame.drawScreen();
    }

    public void endTurn() {
        if (this.hasNotRolled) {
            displayMessage(this.catanGame.getMessages().getString("InputHandler.19"));
            return;
        }
        TurnTracker playerTracker = getPlayerTracker();
        Player currentPlayer = getCurrentPlayer();
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
        this.catanGame.drawPlayers();
    }

    private Player getCurrentPlayer() {
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
        if (e instanceof InvalidHexPositionException) {
            displayMessage(MessageFormat.format(this.catanGame.getMessages().getString("InputHandler.21"), row, col));

        } else if (e instanceof IllegalRobberMoveException) {
            displayMessage(MessageFormat.format(this.catanGame.getMessages().getString("InputHandler.22"), row, col));

        } else if (e instanceof InvalidEdgePositionException) {
            displayMessage(MessageFormat.format(this.catanGame.getMessages().getString("InputHandler.23"), row, col));

        } else if (e instanceof InvalidIntersectionPositionException) {
            displayMessage(MessageFormat.format(this.catanGame.getMessages().getString("InputHandler.24"), row, col));

        } else if (e instanceof PlaceBuildingException) {
            displayMessage(e.getMessage());

        } else if (e instanceof ItemNotFoundException) {
            displayMessage(this.catanGame.getMessages().getString("InputHandler.25"));

        } else {
            displayMessage(
                    MessageFormat.format(this.catanGame.getMessages().getString("InputHandler.26"), e.getMessage()));
        }
    }

}
