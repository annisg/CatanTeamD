package model;

import exception.VictoryPointPlayedException;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.time.Year;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class DevelopmentCardBehaviorTest {
    @Before
    public void setUpCards() {

    }

    @Test
    public void testGetDevelopmentCardName() {
        DevelopmentCard card = new KnightCard(null, null);
        assertEquals("Knight", card.getName());
    }

    @Test
    public void testMakeCardPlayable() {
        DevelopmentCard card = new YearOfPlentyCard(null);
        card.makePlayable();
        assertTrue(card.canBePlayed());
    }

    @Test
    public void testVictoryPointCardThrowsException() {
        DevelopmentCard card = new MonopolyCard(null);
        Player owner = new Player(PlayerColor.WHITE);
        int size1 = owner.getDevelopmentCards().size();
        owner.giveDevelopmentCard(card);

        int size2 = owner.getDevelopmentCards().size();
        assertEquals(-1, size1 - size2);

    }

    @Test
    public void testRecognizeDifferentCards() {
        DevelopmentCard card1 = new MonopolyCard(null);
        DevelopmentCard card2 = new YearOfPlentyCard(null);
        DevelopmentCard card3 = new RoadBuildingCard(null);
        DevelopmentCard card4 = new KnightCard(null, null);
        assertTrue(card1 instanceof DevelopmentCard);
        assertTrue(card2 instanceof YearOfPlentyCard);
    }

    @Test
    public void testDifferentCardsNulls() {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale((String) "en"));
        DevelopmentDeck deck = new DevelopmentDeck(null, messages);
        deck.populateDeckUniqueCard();
        int victoryPointCardNum = 0, monopolyCardNum = 0, roadbuildingCardNum = 0, knightCardNum = 0;
        List<DevelopmentCard> list = deck.getDeck();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof KnightCard) {
                knightCardNum++;
            } else if (list.get(i) instanceof RoadBuildingCard)
                roadbuildingCardNum++;
            else if (list.get(i) instanceof YearOfPlentyCard) {
                victoryPointCardNum++;
            } else {
                monopolyCardNum++;
            }
        }
        assertEquals(16, knightCardNum);
        assertEquals(4, victoryPointCardNum);
        assertEquals(4, roadbuildingCardNum);
        assertEquals(9, monopolyCardNum);


    }
}
