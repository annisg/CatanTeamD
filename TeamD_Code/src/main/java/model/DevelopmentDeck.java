package model;

import java.util.*;

public class DevelopmentDeck {

    private ArrayList<DevelopmentCard> deck;
    private LargestArmy largestArmy;
    private ResourceBundle messages;

    public DevelopmentDeck(LargestArmy largestArmy, ResourceBundle messages) {
        this(largestArmy, new Random(), messages);
    }

    public DevelopmentDeck(LargestArmy largestArmy, Random random, ResourceBundle messages) {
        this.largestArmy = largestArmy;
        this.deck = new ArrayList<DevelopmentCard>();
        this.messages = messages;
        populateDeck();
        Collections.shuffle(deck, random);
    }

    private void populateDeck() {

        for (int i = 0; i < 14; i++) {
            deck.add(new KnightCard(largestArmy, messages));
        }

        for (int i = 0; i < 5; i++) {
            deck.add(new VictoryPointCard(messages));
        }

        for (int i = 0; i < 2; i++) {
            deck.add(new MonopolyCard(messages));
            deck.add(new YearOfPlentyCard(messages));
            deck.add(new RoadBuildingCard(messages));
        }

    }

    public void populateDeckUniqueCard(){
        deck.add(new VictoryPointCard(messages));
        deck.add(new KnightCard(largestArmy, messages));
        deck.add(new RoadBuildingCard(messages));
        deck.add(new YearOfPlentyCard(messages));
        deck.add(new VictoryPointCard(null));
        deck.add(new KnightCard(null, null));
        deck.add(new RoadBuildingCard(null));
        deck.add(new YearOfPlentyCard(null));
    }

    public List<DevelopmentCard> getDeck(){
        return deck;
    }
    public int size() {
        return deck.size();
    }

    public DevelopmentCard drawCard() {

        try {
            DevelopmentCard topCard = deck.remove(0);
            return topCard;
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException();
        }
    }

}
