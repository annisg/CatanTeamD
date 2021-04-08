package control;

import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

import javax.swing.JOptionPane;

import exception.*;
import gui.Select1Frame;
import gui.Select2Frame;
import gui.TradeWithSpecificPlayerGUI;
import model.*;

public class InputHandler {
    private final Integer[] possibleIntersectionRows = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
    private final Integer[] possibleIntersectionCols = { 1, 2, 3, 4, 5, 6 };
    private final Integer[] possibleEdgeRows = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
    private final Integer[] possibleEdgeCols = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    private final Integer[] possibleHexRows = { 1, 2, 3, 4, 5 };
    private final Integer[] possibleHexCols = { 1, 2, 3, 4, 5 };
    private final Object[] possibleDevCards = { KnightCard.class, MonopolyCard.class, YearOfPlentyCard.class,
            VictoryPointCard.class, RoadBuildingCard.class };
    private String[] possibleDevCardNames;
    Select2Frame optionalIntersectionSelector;
    Select2Frame optionalEdgeSelector;
    Select2Frame mandatoryIntersectionSelector;
    Select2Frame mandatoryEdgeSelector;
    Select2Frame hexSelector;
    Select1Frame devCardSelector;

    private ResourceProducer resourceProducer;
    private CatanGame catanGame;
    boolean hasNotRolled;
    BuildingHandler propertyBuilder;

    public InputHandler(ResourceProducer resourceProducer, CatanGame game, PieceBuilder builder) {
        this.resourceProducer = resourceProducer;
        this.catanGame = game;
        this.hasNotRolled = true;
        this.propertyBuilder = new BuildingHandler(game, builder, this);

        possibleDevCardNames = new String[] { this.catanGame.getMessages().getString("InputHandler.4"),
                this.catanGame.getMessages().getString("InputHandler.3"),
                this.catanGame.getMessages().getString("InputHandler.2"),
                this.catanGame.getMessages().getString("InputHandler.1"),
                this.catanGame.getMessages().getString("InputHandler.0") };
        optionalIntersectionSelector = new Select2Frame(possibleIntersectionRows, possibleIntersectionCols, true, this);
        optionalEdgeSelector = new Select2Frame(possibleEdgeRows, possibleEdgeCols, true, this);
        mandatoryIntersectionSelector = new Select2Frame(possibleIntersectionRows, possibleIntersectionCols, false,
                this);
        mandatoryEdgeSelector = new Select2Frame(possibleEdgeRows, possibleEdgeCols, false, this);
        hexSelector = new Select2Frame(possibleHexRows, possibleHexCols, false, this);
        devCardSelector = new Select1Frame(possibleDevCardNames, possibleDevCards, true, this);
    }

    public ResourceBundle getMessages() {
        return this.catanGame.getMessages();
    }

    public void placeInitialSettlement() {
        mandatoryIntersectionSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.5"),
                placeInitialSettlement);
    }

    public Function<Integer[], Void> placeInitialSettlement = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] intersectionCoordinates) {
            propertyBuilder.placeInitialSettlement(intersectionCoordinates[0], intersectionCoordinates[1]);
            return null;
        }
    };

    public void placeInitialSettlementRound2() {
        mandatoryIntersectionSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.6"),
                placeInitialSettlementRound2);
    }

    public Function<Integer[], Void> placeInitialSettlementRound2 = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] intersectionCoordinates) {
            propertyBuilder.placeInitialSettlementRound2(intersectionCoordinates[0], intersectionCoordinates[1]);
            return null;
        }
    };

    public void placeSettlement() {
        if (this.propertyBuilder.canPlaceSettlement(this.hasNotRolled)) {
            optionalIntersectionSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.7"),
                    this.placeSettlement);
        }
    }

    public Function<Integer[], Void> placeSettlement = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] intersectionCoordinates) {
            propertyBuilder.placeSettlement(intersectionCoordinates[0], intersectionCoordinates[1]);
            return null;
        }
    };

    public void placeCity() {
        if (this.propertyBuilder.canPlaceCity(this.hasNotRolled)) {
            optionalIntersectionSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.8"),
                    this.placeCity);
        }
    }

    public Function<Integer[], Void> placeCity = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] intersectionCoordinates) {
            propertyBuilder.placeCity(intersectionCoordinates[0], intersectionCoordinates[1]);
            return null;
        }
    };

    public void placeInitialRoad() {
        mandatoryEdgeSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.9"),
                placeInitialRoad);
    }

    public Function<Integer[], Void> placeInitialRoad = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] edgeCoordinates) {
            propertyBuilder.placeInitialRoad(edgeCoordinates[0], edgeCoordinates[1]);
            return null;
        }
    };

    public void placeRoad() {
        if (this.propertyBuilder.canPlaceRoad(this.hasNotRolled)) {
            optionalEdgeSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.10"),
                    this.placeRoad);
        }
    }

    public Function<Integer[], Void> placeRoad = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] edgeCoordinates) {
            propertyBuilder.placeRoad(edgeCoordinates[0], edgeCoordinates[1]);
            return null;
        }
    };

    public void buyDevelopmentCard() {
        this.propertyBuilder.buyDevelopmentCard(this.hasNotRolled);
    }

    public void useDevCard() {
        devCardSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.11"), useSelectedDevCard);
    }

    public Function<Object, Void> useSelectedDevCard = new Function<Object, Void>() {
        @Override
        public Void apply(Object selected) {
            canUseDevCard((Class) selected);
            return null;
        }
    };

    void canUseDevCard(Class devCardSelected) {
        if (this.hasNotRolled) {
            displayMessage(this.catanGame.getMessages().getString("InputHandler.12"));
            return;
        }
        TurnTracker playerTracker = this.catanGame.getPlayerTracker();
        Player currentPlayer = playerTracker.getCurrentPlayer();
        DevelopmentCard cardToUse = currentPlayer.findDevelopmentCard(devCardSelected);
        if (cardToUse instanceof KnightCard) {
            hexSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.13"),
                    this.performRobberTurn);
            cardToUse.use(currentPlayer);
        }
        if (cardToUse instanceof VictoryPointCard) {
            displayMessage(this.catanGame.getMessages().getString("InputHandler.14"));
        }
    }

    public void tryToRollDice() {
        try {
            int diceRoll = this.rollDice();
            //adding the seven feature
            if(diceRoll==7){
                discardCardsForEveryPlayer();
            }
            if (this.isRobberTurn(diceRoll)) {
                this.displayMessage(this.catanGame.getMessages().getString("InputHandler.15"));
                hexSelector.selectAndApply(this.catanGame.getMessages().getString("InputHandler.16"),
                        this.performRobberTurn);
            } else {
                this.displayMessage(
                        MessageFormat.format(this.catanGame.getMessages().getString("InputHandler.17"), diceRoll));
                this.produceResources(diceRoll);
            }
        } catch (IllegalStateException resourceException) {
            this.displayMessage(this.catanGame.getMessages().getString("InputHandler.18"));
        }
    }

    public void discardCardsForEveryPlayer(){
        List<Player> people = catanGame.turnTracker.getPlayers();
        for(Player p : people){
            if(p.getResourceHandSize()>7){
                p.discardHalfResourceHand();
            }
        }
    }

    public void tradeWithPlayer(){
        System.out.println("i am in trade with player");
        Player p = this.catanGame.getPlayerTracker().getCurrentPlayer();
        TradeWithSpecificPlayerGUI tradeGUI = new TradeWithSpecificPlayerGUI(p);

    }
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
        this.resourceProducer.produceResources(this.catanGame.getGameMap(), this.catanGame.getPlayerTracker(),
                numRolled);
        this.catanGame.drawPlayers();
    }

    public Function<Integer[], Void> performRobberTurn = new Function<Integer[], Void>() {
        @Override
        public Void apply(Integer[] hexCoordinates) {
            performRobberTurn(hexCoordinates[0], hexCoordinates[1]);
            return null;
        }
    };

    void performRobberTurn(int row, int col) {
        this.catanGame.getGameMap().moveRobberToPosition(row, col);
        this.catanGame.drawScreen();
    }

    public void endTurn() {
        if (this.hasNotRolled) {
            displayMessage(this.catanGame.getMessages().getString("InputHandler.19"));
            return;
        }
        TurnTracker playerTracker = this.catanGame.getPlayerTracker();
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
        this.catanGame.drawPlayers();
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
