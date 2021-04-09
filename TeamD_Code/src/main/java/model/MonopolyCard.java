package model;

import java.util.ResourceBundle;
/*
each opponent must give you 2 cards of that type (brick, grain, ore, lumber, or wool)
 */

/*
junk change
 */
public class MonopolyCard extends DevelopmentCard {

    private ResourceBundle messages;

    public MonopolyCard(ResourceBundle messages) {
        this.messages = messages;
    }

    @Override
    public String getName() {
        return messages.getString("MonopolyCard.0");
    }

    @Override
    public void use(Player owner) {
        owner.removeDevelopmentCard(this);
    }
}
