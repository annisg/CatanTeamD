package model;

import java.util.List;
import java.util.ResourceBundle;

public class SuperPlayer extends Player{
    ResourceBundle messages = null;
    boolean allCardsPlayed = false;
    public SuperPlayer(PlayerColor color) {
        super(color);
        populateResources();
    }
    public void populateResources(){
        addResourec(Resource.GRAIN, 10);
        addResourec(Resource.WOOL, 4);
        addResourec(Resource.LUMBER, 4);
    }
    public void addDevelopmentCard(){
        giveDevelopmentCard(new MonopolyCard(null));
        giveDevelopmentCard(new MonopolyCard(null));
        giveDevelopmentCard(new MonopolyCard(null));
        giveDevelopmentCard(new MonopolyCard(null));
        giveDevelopmentCard(new MonopolyCard(null));
        giveDevelopmentCard(new KnightCard(null, null));
        giveDevelopmentCard(new YearOfPlentyCard(null));
    }

    @Override
    public void letAllDevelopmentCardsBePlayed(){
        List<DevelopmentCard> cards = this.getDevelopmentCards();
        for (DevelopmentCard card : cards) {
            card.makePlayable();
            if(card.canBePlayed()==true)
                allCardsPlayed = true;
            else
                allCardsPlayed= false;
        }
    }
    public boolean getAllCardsPlayed(){
        return allCardsPlayed;
    }

}
