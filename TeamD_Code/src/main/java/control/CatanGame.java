package control;

import gui.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.ResourceBundle;

import static control.GameStartState.ADVANCED;

public class CatanGame {
    GameBoard gui;
    InputComponent input;
    GameMap model;
    HexPlacer hexesAndHexNumPlacer;
    PlayerPlacer playerPlacer;
    SpecialCardPlacer specialCardPlacer;
    Random random;
    TurnTracker turnTracker;
    GameOptionSelector options;
    InputHandler inputHandler;
    VictoryPointCalculator pointCalculator;
    int initialRound;
    boolean showAllPlayers = false;
    ResourceBundle messages;

    public CatanGame(ResourceBundle message) {
        this.messages = message;
        gui = new GameBoard();
        model = new GameMap();
        options = new GameOptionSelector();
        random = new Random();
        turnTracker = new TurnTracker(this.random);
        playerPlacer = new PlayerPlacer(this.turnTracker, this.messages);
        hexesAndHexNumPlacer = new HexPlacer(model.getHexMap(), this.messages);

        LongestRoad longestRoad = new LongestRoad(new CandidateRoadFinder(model));
        LargestArmy largestArmy = new LargestArmy(turnTracker);
        PieceBuilder builder = new PieceBuilder(model, new PropertyPlacer(longestRoad, this.messages),
                new DevelopmentDeck(largestArmy, this.messages));
        pointCalculator = new VictoryPointCalculator(longestRoad, largestArmy);
        inputHandler = new InputHandler(new ResourceProducer(random), this, builder);
        input = new InputComponent(this.inputHandler, this.messages);
        specialCardPlacer = new SpecialCardPlacer(longestRoad, largestArmy, this.messages);

        initialRound = 0;
    }

    void startGame() {
        this.displayOptions();
    }

    void displayOptions() {
        options.getOptionsFromUser(this);
    }

    public GameMap getGameMap() {
        return this.model;
    }

    public TurnTracker getPlayerTracker() {
        return this.turnTracker;
    }

    public VictoryPointCalculator getPointCalculator() {
        return this.pointCalculator;
    }

    public ResourceBundle getMessages() {
        return this.messages;
    }

    public void makeBoard(GameStartState state, int numPlayers) {
        this.turnTracker.setupPlayers(numPlayers);
        this.playerPlacer.refreshPlayerNumber();
        if(state == ADVANCED) {
            model.setUpAdvancedMap();
        } else {
            model.setUpBeginnerMap(numPlayers);
        }
        buildModelFrame();
    }

    public void initialPlacement(GameStartState state) {
        if(state == ADVANCED) {
            advancedInitialPlacement();
        } else {
            this.turnTracker.setupBeginnerResourcesAndPieces();
        }
    }

    public void advancedInitialPlacement() {
        int numberOfPlayers = turnTracker.getNumPlayers();
        if (initialRound < numberOfPlayers) {
            initialRound++;
            advancedInitialPlacementOneTurn();
        } else if (initialRound < numberOfPlayers * 2) {
            initialRound++;
            advancedInitialPlacementRoundTwoOneTurn();
        }
    }

    public void advancedInitialPlacementOneTurn() {
        input.selectInitialPlaceSettlement();
        input.selectInitialRoadPlacement();
    }

    public void advancedInitialPlacementRoundTwoOneTurn() {
        input.selectInitialSettlementPlacementRound2();
        input.selectInitialRoadPlacement();
    }

    public void buildModelFrame() {
        JFrame gameFrame = new JFrame(Messages.getString("CatanGame.0"));
        gameFrame.setSize(1600, 1000);

        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameFrame.add(this.gui, BorderLayout.CENTER);
        gameFrame.add(this.input, BorderLayout.SOUTH);
        input.addMouseListenerToParent();
        drawScreen();
    }

    public void drawScreen() {
        drawMap();
        drawPlayers();
        drawSpecialCards();
    }

    public void drawMap() {
        gui.fullResetMap();

        this.hexesAndHexNumPlacer.refreshHexes(model.getHexMap());
        gui.addHexesAndHexNums(this.hexesAndHexNumPlacer.getAllDrawables());

        gui.drawEdges(model.getEdgeMap());
        gui.drawIntersections(model.getIntersectionMap());

        gui.drawFullMap();
    }

    public void justDrawProperty() {
        gui.drawEdges(model.getEdgeMap());
        gui.drawIntersections(model.getIntersectionMap());
        gui.drawProperty();
    }

    public void drawPlayers() {
        if (this.showAllPlayers) {
            gui.addPlayerViews(this.playerPlacer.getAllPlayerGUIs());
        } else {
            gui.addPlayerViews(this.playerPlacer.getCurrentPlayerGUI());
        }
    }

    public void drawSpecialCards() {
        gui.addSpecialCards(this.specialCardPlacer.getSpecialCards());
    }
}