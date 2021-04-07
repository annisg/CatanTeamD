package control;

import gui.Drawable;
import gui.GUIcomparisons;
import gui.PlayerDisplayBackground;
import gui.PlayerGUI;
import model.*;
import org.easymock.EasyMock;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerGUIRefactoringDevelopmentCardTest {

    @Test
    public void testNewPlayerGUIConstuctor() {
        GUIcomparisons comp = new GUIcomparisons();
        Player currentPlayer = new Player(PlayerColor.WHITE);
        TurnTracker threePlayers = EasyMock.strictMock(TurnTracker.class);
        PlayerGUI correctGUI = EasyMock.partialMockBuilder(PlayerGUI.class).withConstructor(Color.WHITE,
                new HashMap<Resource, Integer>(), new HashMap<DevelopmentCard, Integer>(), 0, 0, null).mock();

        EasyMock.expect(threePlayers.getNumPlayers()).andReturn(3);
        EasyMock.expect(threePlayers.getCurrentPlayer()).andReturn(currentPlayer);
        EasyMock.expect(threePlayers.getPlayer(0)).andReturn(currentPlayer);
        EasyMock.replay(threePlayers, correctGUI);

        PlayerPlacer testPP = new PlayerPlacer(threePlayers, null);

        ArrayList<Drawable> results = testPP.getCurrentPlayerGUI();

        assertEquals(2, results.size());
        assertEquals(PlayerDisplayBackground.class, results.get(0).getClass());
        assertEquals(PlayerGUI.class, results.get(1).getClass());
        assertTrue(comp.playerGUISimilar(correctGUI, (PlayerGUI) results.get(1)));
        EasyMock.verify(threePlayers, correctGUI);
    }

    @Test
    public void testCalculateExactCards(){
        Player currentPlayer = new Player(PlayerColor.WHITE);
        HashMap<DevelopmentCard, Integer> numOfEachCard = new HashMap<DevelopmentCard, Integer>();

        numOfEachCard.add(new KnightCard(null, null), 1);
        numOfEachCard.add(new VictoryPointCard(null), 1);
        numOfEachCard.add(new MonopolyCard(null), 4);
        numOfEachCard.add(new YearOfPlentyCard(null), 8);
        numOfEachCard.add(new RoadBuildingCard(null), 10);

        PlayerGUI playerGUI = new PlayerGUI(Color.WHITE, null, numOfEachCard, 0, 0, null);
        int numberOfMonopoly = playerGUI.getAmountOfSpecificCard(new MonopolyCard(null));
        assertEquals(4, numberOfMonopoly);

    }
}
