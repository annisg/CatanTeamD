package control;

import gui.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.List;

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
    private boolean isFogOfWar;

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

        isFogOfWar = false;
        initialRound = 0;
    }
    
    void setFogOfWar(boolean value) {
        this.isFogOfWar = value;
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

    public void makeBoard(GameStartState state, int numPlayers, GameMode gamemode, boolean isDebug) {
        if (numPlayers < 3 || numPlayers > 4) {
            options.getOptionsFromUser(this);
        } else {

            this.input.setDebugStatus(isDebug);
            this.turnTracker.setupPlayers(numPlayers);

            switch (state) {
            case ADVANCED:
                model.setUpAdvancedMap();
                break;
            case BEGINNER:
                model.setUpBeginnerMap(numPlayers);
                this.turnTracker.setupBeginnerResourcesAndPieces();
                break;
            case CUSTOM:
            default:
                customHexPlacement();
            }

            this.playerPlacer.refreshPlayerNumber();
            
            if(gamemode == GameMode.FOG) {
                setFogOfWar(true);
            }
            
            if (state != GameStartState.CUSTOM) {
                buildModelFrame();
                if (state != GameStartState.BEGINNER) {
                    advancedInitialPlacement();
                }
            }
            
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
    
    private void customHexPlacement() {
        List<Resource> remainingResources = model.getHexMap().getStandardResources();
        List<Integer> remainingNumbers = model.getHexMap().getStandardResourceNumbers();
        inputHandler.selectCustomHexPlacement(remainingResources, remainingNumbers);
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

        PlayerColor currentColor = getColorForFogOfWar();
        gui.drawEdges(model, currentColor);
        gui.drawIntersections(model, currentColor);

        gui.drawFullMap();
    }

    public void justDrawProperty() {
        PlayerColor currentColor = getColorForFogOfWar();
        gui.drawEdges(model, currentColor);
        gui.drawIntersections(model, currentColor);
        gui.drawProperty();
    }

    public void drawPlayers() {
        if (this.showAllPlayers) {
            gui.addPlayerViews(this.playerPlacer.getAllPlayerGUIs());
        } else {
            gui.addPlayerViews(this.playerPlacer.getCurrentPlayerGUI());
            gui.addOtherPlayerViews(this.playerPlacer.getOtherPlayerGUIs());
        }
    }
    
    private PlayerColor getColorForFogOfWar() {
        if(isFogOfWar) {
            return turnTracker.getCurrentPlayer().getColor();
        }
        else {
            return PlayerColor.NONE;
        }
    }

    public void drawSpecialCards() {
        gui.addSpecialCards(this.specialCardPlacer.getSpecialCards());
    }

    public Player getCurrentPlayer() {
        return getPlayerTracker().getCurrentPlayer();
    }

    public void endTurn() {
        gui.showPopup();
        drawScreen();
    }
}

