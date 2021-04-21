package control;

import static org.junit.Assert.*;

import java.util.*;

import org.easymock.EasyMock;
import org.junit.Test;

import gui.*;
import model.*;

public class CatanGame_tests {

    private CatanGame testCatan = null;
    private TurnTracker mockedTurnTracker = null;
    private GameMap mockedGameMap = null;
    private HexMap mockedHexMap = null;
    private GameOptionSelector mockedOptions = null;
    private GameBoard mockedGUI = null;
    private HexPlacer mockedHexPlacer = null;
    private PlayerPlacer mockedPlayerPlacer = null;
    private SpecialCardPlacer mockedCardPlacer = null;
    private Random rand = null;
    private InputComponent component = null;
    private InputHandler handler = null;
    private ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    private void setupGame() {
        this.mockedTurnTracker = EasyMock.strictMock(TurnTracker.class);
        this.mockedGameMap = EasyMock.strictMock(GameMap.class);
        this.mockedHexMap = EasyMock.strictMock(HexMap.class);
        this.mockedOptions = EasyMock.strictMock(GameOptionSelector.class);
        this.mockedGUI = EasyMock.strictMock(GameBoard.class);
        this.mockedHexPlacer = EasyMock.strictMock(HexPlacer.class);
        this.mockedPlayerPlacer = EasyMock.strictMock(PlayerPlacer.class);
        this.mockedCardPlacer = EasyMock.strictMock(SpecialCardPlacer.class);
        this.rand = new Random(0);
        this.component = EasyMock.strictMock(InputComponent.class);
        this.handler = EasyMock.strictMock(InputHandler.class);

        this.testCatan.messages = messages;
        this.testCatan.turnTracker = this.mockedTurnTracker;
        this.testCatan.model = this.mockedGameMap;
        this.testCatan.options = this.mockedOptions;
        this.testCatan.gui = this.mockedGUI;
        this.testCatan.hexesAndHexNumPlacer = this.mockedHexPlacer;
        this.testCatan.playerPlacer = this.mockedPlayerPlacer;
        this.testCatan.specialCardPlacer = this.mockedCardPlacer;
        this.testCatan.random = this.rand;
        this.testCatan.input = this.component;
        this.testCatan.inputHandler = this.handler;
        EasyMock.expect(this.mockedGameMap.getHexMap()).andStubReturn(mockedHexMap);
    }

    private void replayAll() {
        EasyMock.replay(testCatan, mockedTurnTracker, mockedGameMap, mockedHexMap, mockedOptions, mockedGUI, mockedHexPlacer,
                mockedPlayerPlacer, mockedCardPlacer, component, handler);
    }

    private void verifyAll() {
        EasyMock.verify(testCatan, mockedTurnTracker, mockedGameMap, mockedHexMap, mockedOptions, mockedGUI, mockedHexPlacer,
                mockedPlayerPlacer, mockedCardPlacer, component, handler);
    }

    @Test
    public void testMakeBoardBeginner3() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).addMockedMethod("buildModelFrame").mock();
        setupGame();
        mockedGameMap.setUpBeginnerMap(3);
        mockedTurnTracker.setupPlayers(3);
        mockedTurnTracker.setupBeginnerResourcesAndPieces();
        mockedPlayerPlacer.refreshPlayerNumber();
        testCatan.buildModelFrame();
        runTestsMakeBoard(GameStartState.BEGINNER, 3);
    }

    @Test
    public void testMakeBoardBeginner4() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).addMockedMethod("buildModelFrame").mock();
        setupGame();
        mockedGameMap.setUpBeginnerMap(4);
        mockedTurnTracker.setupPlayers(4);
        mockedTurnTracker.setupBeginnerResourcesAndPieces();
        mockedPlayerPlacer.refreshPlayerNumber();
        testCatan.buildModelFrame();
        runTestsMakeBoard(GameStartState.BEGINNER, 4);
    }

    @Test
    public void testMakeBoardAdvanced3() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).addMockedMethod("buildModelFrame").mock();
        setupGame();
        mockedGameMap.setUpAdvancedMap();
        mockedTurnTracker.setupPlayers(3);
        mockedPlayerPlacer.refreshPlayerNumber();
        testCatan.buildModelFrame();
        runTestsMakeBoard(GameStartState.ADVANCED, 3);
    }

    @Test
    public void testMakeBoardAdvanced4() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).addMockedMethod("buildModelFrame").mock();
        setupGame();
        mockedGameMap.setUpAdvancedMap();
        mockedTurnTracker.setupPlayers(4);
        mockedPlayerPlacer.refreshPlayerNumber();
        testCatan.buildModelFrame();
        runTestsMakeBoard(GameStartState.ADVANCED, 4);
    }
    
    @Test
    public void testMakeBoardCustom3() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).addMockedMethod("buildModelFrame").mock();
        setupGame();
        
        List<Resource> resources = Arrays.asList(Resource.WOOL);
        List<Integer> availableNumbers = Arrays.asList(2);
        EasyMock.expect(mockedHexMap.getStandardResources()).andReturn(resources);
        EasyMock.expect(mockedHexMap.getStandardResourceNumbers()).andReturn(availableNumbers);
        
        mockedTurnTracker.setupPlayers(3);
        mockedPlayerPlacer.refreshPlayerNumber();
        handler.selectCustomHexPlacement(resources, availableNumbers);
        //No build model frame, covered in InputHandler
        runTestsMakeBoard(GameStartState.CUSTOM, 3);
    }
    
    @Test
    public void testMakeBoardCustom4() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).addMockedMethod("buildModelFrame").mock();
        setupGame();
        
        List<Resource> resources = Arrays.asList(Resource.WOOL);
        List<Integer> availableNumbers = Arrays.asList(2);
        EasyMock.expect(mockedHexMap.getStandardResources()).andReturn(resources);
        EasyMock.expect(mockedHexMap.getStandardResourceNumbers()).andReturn(availableNumbers);
        
        mockedTurnTracker.setupPlayers(4);
        mockedPlayerPlacer.refreshPlayerNumber();
        handler.selectCustomHexPlacement(resources, availableNumbers);
        //No build model frame, covered in InputHandler
        runTestsMakeBoard(GameStartState.CUSTOM, 4);
    }

    private void runTestsMakeBoard(GameStartState testState, int testNumPlayers) {
        if (testState == GameStartState.ADVANCED && testNumPlayers >= 3 && testNumPlayers <= 4) {
            EasyMock.expect(mockedTurnTracker.getNumPlayers()).andStubReturn(testNumPlayers);
            this.component.selectInitialPlaceSettlement();
            this.component.selectInitialRoadPlacement();
        }
        replayAll();
        testCatan.makeBoard(testState, testNumPlayers);
        verifyAll();
    }

    @Test
    public void testDrawModelCallsEverything() {
        ArrayList<Drawable> playerGUIs = new ArrayList<>();
        ArrayList<Drawable> otherPlayerGUIs = new ArrayList<>();
        ArrayList<Drawable> cardGUIs = new ArrayList<>();

        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).mock();
        setupGame();
        this.testCatan.showAllPlayers = false;
        drawModelAlwaysCalls();
        EasyMock.expect(this.mockedPlayerPlacer.getCurrentPlayerGUI()).andReturn(playerGUIs);
        EasyMock.expect(this.mockedPlayerPlacer.getOtherPlayerGUIs()).andReturn(otherPlayerGUIs);
        EasyMock.expect(this.mockedCardPlacer.getSpecialCards()).andReturn(cardGUIs);
        this.mockedGUI.addPlayerViews(playerGUIs);
        this.mockedGUI.addOtherPlayerViews(otherPlayerGUIs);
        this.mockedGUI.addSpecialCards(cardGUIs);
        runTestDrawModel();

        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).mock();
        setupGame();
        this.testCatan.showAllPlayers = true;
        drawModelAlwaysCalls();
        EasyMock.expect(this.mockedPlayerPlacer.getAllPlayerGUIs()).andReturn(playerGUIs);
        EasyMock.expect(this.mockedCardPlacer.getSpecialCards()).andReturn(cardGUIs);
        this.mockedGUI.addPlayerViews(playerGUIs);
        this.mockedGUI.addSpecialCards(cardGUIs);
        runTestDrawModel();
    }

    private void drawModelAlwaysCalls() {
        HexMap mockedHM = EasyMock.strictMock(HexMap.class);
        EdgeMap mockedEM = EasyMock.strictMock(EdgeMap.class);
        IntersectionMap mockedIM = EasyMock.strictMock(IntersectionMap.class);
        ArrayList<Drawable> hexDrawables = new ArrayList<>();

        this.mockedGUI.fullResetMap();
        EasyMock.expect(this.mockedGameMap.getHexMap()).andReturn(mockedHM);
        this.mockedHexPlacer.refreshHexes(mockedHM);
        EasyMock.expect(this.mockedHexPlacer.getAllDrawables()).andReturn(hexDrawables);
        this.mockedGUI.addHexesAndHexNums(hexDrawables);
        EasyMock.expect(this.mockedGameMap.getEdgeMap()).andReturn(mockedEM);
        this.mockedGUI.drawEdges(mockedEM);
        EasyMock.expect(this.mockedGameMap.getIntersectionMap()).andReturn(mockedIM);
        this.mockedGUI.drawIntersections(mockedIM);
        this.mockedGUI.drawFullMap();
    }

    private void runTestDrawModel() {
        replayAll();
        testCatan.drawScreen();
        verifyAll();
    }

    @Test
    public void testConstructorBuildsEverything() {
        this.testCatan = new CatanGame(messages);

        assertFalse(this.testCatan.gui == null);
        assertFalse(this.testCatan.model == null);
        assertFalse(this.testCatan.hexesAndHexNumPlacer == null);
        assertFalse(this.testCatan.playerPlacer == null);
        assertFalse(this.testCatan.random == null);
        assertFalse(this.testCatan.turnTracker == null);
        assertFalse(this.testCatan.options == null);
        assertFalse(this.testCatan.pointCalculator == null);
    }

    @Test
    public void testDisplayOptions() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).mock();
        setupGame();
        this.mockedOptions.getOptionsFromUser(testCatan);
        replayAll();

        this.testCatan.displayOptions();

        verifyAll();
    }

    @Test
    public void testStartGame() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).addMockedMethod("displayOptions").mock();
        setupGame();
        testCatan.displayOptions();
        replayAll();

        this.testCatan.startGame();
        verifyAll();
    }

    @Test
    public void testGetters() {
        this.testCatan = new CatanGame(messages);
        assertEquals(testCatan.model, testCatan.getGameMap());
        assertEquals(testCatan.turnTracker, testCatan.getPlayerTracker());
        assertEquals(testCatan.pointCalculator, testCatan.getPointCalculator());
    }

    @Test
    public void testJustDrawProperty() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).mock();
        setupGame();

        EdgeMap edgeMap = EasyMock.mock(EdgeMap.class);
        EasyMock.expect(mockedGameMap.getEdgeMap()).andReturn(edgeMap);
        IntersectionMap intersectionMap = EasyMock.mock(IntersectionMap.class);
        EasyMock.expect(mockedGameMap.getIntersectionMap()).andReturn(intersectionMap);

        mockedGUI.drawEdges(edgeMap);
        mockedGUI.drawIntersections(intersectionMap);
        mockedGUI.drawProperty();

        replayAll();
        EasyMock.replay(edgeMap, intersectionMap);

        this.testCatan.justDrawProperty();

        verifyAll();
        EasyMock.verify(edgeMap, intersectionMap);
    }

    @Test
    public void testAdvanceInitialPlacementFirstHalf() {
        int[] playerNums = { 3, 4 };

        for (int num : playerNums) {
            for (int i = 0; i < num; i++) {
                this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).withConstructor(messages)
                        .addMockedMethod("advancedInitialPlacementOneTurn")
                        .addMockedMethod("advancedInitialPlacementRoundTwoOneTurn").mock();
                setupGame();

                EasyMock.expect(this.mockedTurnTracker.getNumPlayers()).andStubReturn(num);
                this.testCatan.advancedInitialPlacementOneTurn();
                this.testCatan.initialRound = i;
                replayAll();

                this.testCatan.advancedInitialPlacement();
                assertEquals(i + 1, this.testCatan.initialRound);
                verifyAll();
            }
        }
    }

    @Test
    public void testAdvanceInitialPlacementSecondHalf() {
        int[] playerNums = { 3, 4 };

        for (int num : playerNums) {
            for (int i = num; i < num * 2; i++) {
                this.testCatan = EasyMock.partialMockBuilder(CatanGame.class)
                        .addMockedMethod("advancedInitialPlacementOneTurn")
                        .addMockedMethod("advancedInitialPlacementRoundTwoOneTurn").mock();
                setupGame();

                EasyMock.expect(this.mockedTurnTracker.getNumPlayers()).andStubReturn(num);
                this.testCatan.advancedInitialPlacementRoundTwoOneTurn();
                this.testCatan.initialRound = i;
                replayAll();

                this.testCatan.advancedInitialPlacement();
                assertEquals(i + 1, this.testCatan.initialRound);
                verifyAll();
            }
        }
    }

    @Test
    public void testAdvanceInitialPlacementMaxLimit() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).mock();
        setupGame();
        EasyMock.expect(mockedTurnTracker.getNumPlayers()).andStubReturn(3);
        this.testCatan.initialRound = 6;
        replayAll();

        this.testCatan.advancedInitialPlacement();
        verifyAll();
    }

    @Test
    public void testAdvancedInitialPlacementOneTurn() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).mock();
        setupGame();
        this.component.selectInitialPlaceSettlement();
        this.component.selectInitialRoadPlacement();

        replayAll();
        this.testCatan.advancedInitialPlacementOneTurn();
        verifyAll();
    }

    @Test
    public void testAdvancedInitialPlacementRoundTwoOneTurn() {
        this.testCatan = EasyMock.partialMockBuilder(CatanGame.class).mock();
        setupGame();
        this.component.selectInitialSettlementPlacementRound2();
        this.component.selectInitialRoadPlacement();

        replayAll();
        this.testCatan.advancedInitialPlacementRoundTwoOneTurn();
        verifyAll();
    }

}
