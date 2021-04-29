package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import model.*;

public class DevelopmentDeck_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
    
    @Test
    public void testDevelopmentDeckSize() {
        LargestArmy largestArmy = EasyMock.mock(LargestArmy.class);
        DevelopmentDeck deck = new DevelopmentDeck(largestArmy, messages);
        EasyMock.replay(largestArmy);
        
        assertEquals(25, deck.size());
        EasyMock.verify(largestArmy);
    }

    @Test
    public void testDrawCardFromFullDeck() {
        LargestArmy largestArmy = EasyMock.mock(LargestArmy.class);
        DevelopmentDeck deck = new DevelopmentDeck(largestArmy, messages);
        EasyMock.replay(largestArmy);
        
        DevelopmentCard draw = deck.drawCard();
        assertEquals(24, deck.size());
        EasyMock.verify(largestArmy);
    }

    @Test
    public void testDrawLastCardInDeck() {
        LargestArmy largestArmy = EasyMock.mock(LargestArmy.class);
        DevelopmentDeck deck = new DevelopmentDeck(largestArmy, messages);
        EasyMock.replay(largestArmy);

        drawCards(deck, 24);

        DevelopmentCard draw = deck.drawCard();
        assertEquals(0, deck.size());
        EasyMock.verify(largestArmy);
    }

    @Test
    public void testDrawFromEmptyDeck() {
        LargestArmy largestArmy = EasyMock.mock(LargestArmy.class);
        DevelopmentDeck deck = new DevelopmentDeck(largestArmy, messages);
        EasyMock.replay(largestArmy);
        
        drawCards(deck, 25);

        try {
            DevelopmentCard draw = deck.drawCard();
            fail("");
        } catch (IllegalStateException e) {

        }
        
        EasyMock.verify(largestArmy);
    }

    @Test
    public void testContainsCorrectCards() {
        
        LargestArmy largestArmy = EasyMock.mock(LargestArmy.class);
        DevelopmentDeck deck = new DevelopmentDeck(largestArmy, messages);
        ArrayList<DevelopmentCard> cards = drawCards(deck, 25);

        EasyMock.replay(largestArmy);
        
        int knightCount = 0;
        int victoryPointCount = 0;
        int monopolyCount = 0;
        int yearOfPlentyCount = 0;
        int roadBuildingCount = 0;

        for (int i = 0; i < cards.size(); i++) {
            DevelopmentCard currentCard = cards.get(i);

            if (currentCard instanceof KnightCard && ((KnightCard) currentCard).largestArmy == largestArmy) {
                knightCount++;
            } else if (currentCard instanceof VictoryPointCard) {
                victoryPointCount++;
            } else if (currentCard instanceof MonopolyCard) {
                monopolyCount++;
            } else if (currentCard instanceof YearOfPlentyCard) {
                yearOfPlentyCount++;
            } else if (currentCard instanceof RoadBuildingCard) {
                roadBuildingCount++;
            }
        }

        assertEquals(14, knightCount);
        assertEquals(5, victoryPointCount);
        assertEquals(2, monopolyCount);
        assertEquals(2, yearOfPlentyCount);
        assertEquals(2, roadBuildingCount);
        EasyMock.verify(largestArmy);

    }

    @Test
    public void testDeckIsShuffled() {

        Random random = new Random(0);
        LargestArmy largestArmy = EasyMock.mock(LargestArmy.class);
        DevelopmentDeck deck = new DevelopmentDeck(largestArmy, random, messages);
        EasyMock.replay(largestArmy);
        
        ArrayList<DevelopmentCard> actualCards = drawCards(deck, 25);
        ArrayList<DevelopmentCard> expectedCards = randomDeckOrdering(largestArmy);

        verifySameOrdering(expectedCards, actualCards);
        EasyMock.verify(largestArmy);
    }

    ArrayList<DevelopmentCard> drawCards(DevelopmentDeck deck, int numberToDraw) {

        ArrayList<DevelopmentCard> list = new ArrayList<DevelopmentCard>();

        for (int i = 0; i < numberToDraw; i++) {
            list.add(deck.drawCard());
        }

        return list;
    }

    ArrayList<DevelopmentCard> randomDeckOrdering(LargestArmy largestArmy) {

        ArrayList<DevelopmentCard> list = new ArrayList<DevelopmentCard>(
                Arrays.asList(new VictoryPointCard(messages), new KnightCard(largestArmy, messages), new VictoryPointCard(messages), new VictoryPointCard(messages),
                        new VictoryPointCard(messages), new YearOfPlentyCard(messages), new KnightCard(largestArmy, messages),
                        new KnightCard(largestArmy, messages), new MonopolyCard(messages), new YearOfPlentyCard(messages), new VictoryPointCard(messages),
                        new RoadBuildingCard(messages), new KnightCard(largestArmy, messages), new KnightCard(largestArmy, messages), new KnightCard(largestArmy, messages), new KnightCard(largestArmy, messages),
                        new KnightCard(largestArmy, messages), new KnightCard(largestArmy, messages), new RoadBuildingCard(messages), new KnightCard(largestArmy, messages), new KnightCard(largestArmy, messages), 
                        new KnightCard(largestArmy, messages), new MonopolyCard(messages), new KnightCard(largestArmy, messages), new KnightCard(largestArmy, messages)));

        return list;
    }

    public void verifySameOrdering(ArrayList<DevelopmentCard> expectedList, ArrayList<DevelopmentCard> actualList) {

        if (expectedList.size() != actualList.size()) {
            fail("Deck is incorrect size");
        }

        for (int i = 0; i < expectedList.size(); i++) {
            if (!expectedList.get(i).getClass().equals(actualList.get(i).getClass())) {
                fail("Unexpected ordering of cards");
            }
        }

    }
}
