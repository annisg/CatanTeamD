package control;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.*;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import control.*;
import gui.Drawable;
import gui.GUIcomparisons;
import gui.PlayerDisplayBackground;
import gui.PlayerGUI;
import model.*;

public class PlayerPlacer_Tests {

    private PlayerColor colors[] = { PlayerColor.RED, PlayerColor.BLUE, PlayerColor.WHITE, PlayerColor.ORANGE };
    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    @Test
    public void testSizeOfGetAllPlayers() {

        TurnTracker threePlayers = mockedTurnTracker(3);
        TurnTracker fourPlayers = mockedTurnTracker(4);

        EasyMock.replay(threePlayers, fourPlayers);

        PlayerPlacer testPP = new PlayerPlacer(threePlayers, messages);
        assertEquals(3, testPP.getTurnTracker().getNumPlayers());

        testPP = new PlayerPlacer(fourPlayers, messages);
        assertEquals(4, testPP.getTurnTracker().getNumPlayers());

        EasyMock.verify(threePlayers, fourPlayers);

    }

    @Test
    public void testPlayerPlacerGoodNumberOfPlayers() {
        PlayerPlacer testPP = new PlayerPlacer(mockedImproperlySetupTurnTracker(3), messages);
        assertEquals(3, testPP.numberOfPlayers);

        testPP = new PlayerPlacer(mockedImproperlySetupTurnTracker(4), messages);
        assertEquals(4, testPP.numberOfPlayers);
    }

    @Test
    public void testPlayerPlacerBadNumberOfPlayers() {

        int[] badValues = { Integer.MAX_VALUE, 5, 2, Integer.MIN_VALUE };

        for (int i = 0; i < badValues.length; i++) {
            PlayerPlacer testPP = new PlayerPlacer(mockedImproperlySetupTurnTracker(badValues[i]), messages);
            assertEquals(3, testPP.numberOfPlayers);
        }

    }

    private TurnTracker mockedImproperlySetupTurnTracker(int numPlayers) {
        TurnTracker improperlySetupTracker = EasyMock.mock(TurnTracker.class);
        EasyMock.expect(improperlySetupTracker.getNumPlayers()).andStubReturn(numPlayers);

        EasyMock.replay(improperlySetupTracker);
        return improperlySetupTracker;
    }

    @Test
    public void testSizeOfGetAllPlayerGUIs() {

        TurnTracker threePlayers = mockedTurnTracker(3);
        TurnTracker fourPlayers = mockedTurnTracker(4);

        EasyMock.replay(threePlayers, fourPlayers);

        PlayerPlacer testPP = new PlayerPlacer(threePlayers, messages);
        assertEquals(4, testPP.getAllPlayerGUIs().size());

        testPP = new PlayerPlacer(fourPlayers, messages);
        assertEquals(5, testPP.getAllPlayerGUIs().size());

        EasyMock.verify(threePlayers, fourPlayers);

    }

    @Test
    public void testGetCurrentPlayerGUI() {
        GUIcomparisons comp = new GUIcomparisons();
        Player currentPlayer = new Player(PlayerColor.WHITE);
        TurnTracker threePlayers = EasyMock.strictMock(TurnTracker.class);
        PlayerGUI correctGUI = EasyMock.partialMockBuilder(PlayerGUI.class).withConstructor(Color.WHITE,
                new HashMap<Resource, Integer>(), new HashMap<String, Integer>(), 0, 0, messages, "noob").mock();

        EasyMock.expect(threePlayers.getNumPlayers()).andReturn(3);
        EasyMock.expect(threePlayers.getCurrentPlayer()).andReturn(currentPlayer);
        EasyMock.expect(threePlayers.getPlayer(0)).andReturn(currentPlayer);
        EasyMock.replay(threePlayers, correctGUI);

        PlayerPlacer testPP = new PlayerPlacer(threePlayers, messages);

        ArrayList<Drawable> results = testPP.getCurrentPlayerGUI();

        assertEquals(2, results.size());
        assertEquals(PlayerDisplayBackground.class, results.get(0).getClass());
        assertEquals(PlayerGUI.class, results.get(1).getClass());
        assertTrue(comp.playerGUISimilar(correctGUI, (PlayerGUI) results.get(1)));
        EasyMock.verify(threePlayers, correctGUI);
    }

    private TurnTracker mockedTurnTracker(int numPlayers) {

        TurnTracker tracker = EasyMock.mock(TurnTracker.class);
        ArrayList<Player> players = mockedPlayerList(numPlayers);

        EasyMock.expect(tracker.getNumPlayers()).andStubReturn(players.size());

        for (int i = 0; i < players.size(); i++) {
            EasyMock.expect(tracker.getPlayer(i)).andStubReturn(players.get(i));
        }

        return tracker;
    }

    private ArrayList<Player> mockedPlayerList(int numPlayers) {

        ArrayList<Player> list = new ArrayList<Player>();

        for (int i = 0; i < numPlayers; i++) {

            Player player = EasyMock.mock(Player.class);

            EasyMock.expect(player.getColor()).andStubReturn(colors[i]);
            EasyMock.expect(player.getResourceCount(EasyMock.anyObject())).andStubReturn(0);
            EasyMock.expect(player.getDevelopmentCards()).andStubReturn(new ArrayList<DevelopmentCard>());

            list.add(player);
        }

        EasyMock.replay(list.toArray());

        return list;
    }

    @Test
    public void testRefresh() {

        int[] badValues = { Integer.MAX_VALUE, 5, 2, Integer.MIN_VALUE };
        for (int i = 0; i < badValues.length; i++) {
            PlayerPlacer testPP = new PlayerPlacer(mockedImproperlySetupTurnTracker2(badValues[i]), messages);
            testPP.refreshPlayerNumber();
            assertEquals(3, testPP.numberOfPlayers);
        }

        int[] goodValues = { 3, 4 };
        for (int i = 0; i < goodValues.length; i++) {
            PlayerPlacer testPP = new PlayerPlacer(mockedImproperlySetupTurnTracker2(goodValues[i]), messages);
            testPP.refreshPlayerNumber();
            assertEquals(goodValues[i], testPP.numberOfPlayers);
        }
    }

    private TurnTracker mockedImproperlySetupTurnTracker2(int numPlayers) {
        TurnTracker improperlySetupTracker = EasyMock.mock(TurnTracker.class);
        EasyMock.expect(improperlySetupTracker.getNumPlayers()).andStubReturn(numPlayers);
        EasyMock.expect(improperlySetupTracker.getNumPlayers()).andStubReturn(numPlayers);

        EasyMock.replay(improperlySetupTracker);
        return improperlySetupTracker;
    }

    @Test
    public void testFindPlayerIndex() {
        TurnTracker players = new TurnTracker(new Random(0));
        players.setupPlayers(4);
        PlayerPlacer testPP = new PlayerPlacer(players, messages);
        assertEquals(0, testPP.findPlayerIndex(players.getPlayer(0)));
        assertEquals(1, testPP.findPlayerIndex(players.getPlayer(1)));
        assertEquals(2, testPP.findPlayerIndex(players.getPlayer(2)));
        assertEquals(3, testPP.findPlayerIndex(players.getPlayer(3)));
        assertEquals(-1, testPP.findPlayerIndex(new Player(PlayerColor.ORANGE)));
    }

    @Test
    public void testGetDevelopmentCardMapEmptyHand() {
        Player player = EasyMock.mock(Player.class);
        List<DevelopmentCard> cards = new ArrayList<DevelopmentCard>();
        PlayerPlacer testPP = new PlayerPlacer(mockedImproperlySetupTurnTracker2(3), messages);

        EasyMock.expect(player.getDevelopmentCards()).andReturn(cards);
        EasyMock.replay(player);

        String[] devCards = {"K", "V", "M", "R", "Y"};

        HashMap<String, Integer> cardMap = testPP.getDevelopmentCardMap(player);
        assertEquals(0, cardMap.get(devCards[0]).intValue());
        assertEquals(0, cardMap.get(devCards[2]).intValue());
        assertEquals(0, cardMap.get(devCards[3]).intValue());
        assertEquals(0, cardMap.get(devCards[1]).intValue());
        assertEquals(0, cardMap.get(devCards[4]).intValue());

        EasyMock.verify(player);
    }

    @Test
    public void testGetDevelopmentCardMapWithCards() {
        Player player = EasyMock.mock(Player.class);
        List<DevelopmentCard> cards = new ArrayList<DevelopmentCard>();
        for (int i = 0; i < 6; i++) {
            if (i <= 1) {
                cards.add(EasyMock.mock(KnightCard.class));
            }
            if (i <= 2) {
                cards.add(EasyMock.mock(MonopolyCard.class));
            }
            if (i <= 3) {
                cards.add(EasyMock.mock(RoadBuildingCard.class));
            }
            if (i <= 4) {
                cards.add(EasyMock.mock(VictoryPointCard.class));
            }
            if (i <= 5) {
                cards.add(EasyMock.mock(YearOfPlentyCard.class));
            }
        }

        PlayerPlacer testPP = new PlayerPlacer(mockedImproperlySetupTurnTracker2(3), messages);
        EasyMock.expect(player.getDevelopmentCards()).andReturn(cards);
        EasyMock.replay(player);
        EasyMock.replay(cards.toArray());

        String[] devCards = {"K", "V", "M", "R", "Y"};

        HashMap<String, Integer> cardMap = testPP.getDevelopmentCardMap(player);
        assertEquals(2, cardMap.get(devCards[0]).intValue());
        assertEquals(3, cardMap.get(devCards[2]).intValue());
        assertEquals(4, cardMap.get(devCards[3]).intValue());
        assertEquals(5, cardMap.get(devCards[1]).intValue());
        assertEquals(6, cardMap.get(devCards[4]).intValue());

        EasyMock.verify(player);
        EasyMock.verify(cards.toArray());
    }

    @Test
    public void testGetDevelopmentCardMapBadCard() {
        Player player = EasyMock.mock(Player.class);
        List<DevelopmentCard> cards = new ArrayList<DevelopmentCard>();
        cards.add(EasyMock.mock(DevelopmentCard.class));

        PlayerPlacer testPP = new PlayerPlacer(mockedImproperlySetupTurnTracker2(3), messages);
        EasyMock.expect(player.getDevelopmentCards()).andReturn(cards);
        EasyMock.replay(player);
        EasyMock.replay(cards.toArray());

        try {
            HashMap<String, Integer> cardMap = testPP.getDevelopmentCardMap(player);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
        }

        EasyMock.verify(player);
        EasyMock.verify(cards.toArray());
    }

}
