package model;

import java.util.ResourceBundle;

public class KnightCard extends DevelopmentCard {

    LargestArmy largestArmy;
    private ResourceBundle messages;

    public KnightCard(LargestArmy largestArmy, ResourceBundle messages) {
        this.largestArmy = largestArmy;
        this.messages = messages;
    }

    @Override
    public void use(Player owner) {
        owner.removeDevelopmentCard(this);
        owner.incrementKnightCount();
        largestArmy.updateLargestArmy();

    }

    @Override
    public String getName() {
        return messages.getString("KnightCard.0");
    }

}
