package model;

import exception.VictoryPointPlayedException;

import java.util.ResourceBundle;

public class VictoryPointCard extends DevelopmentCard {

    private ResourceBundle messages;

    public VictoryPointCard(ResourceBundle messages) {
        this.messages = messages;
    }

    @Override
    public String getName() {
        return messages.getString("VictoryPointCard.0");
    }

    @Override
    public void use(Player owner) {
        throw new VictoryPointPlayedException(messages.getString("VictoryPointCard.1"));
    }

    @Override
    public boolean canBePlayed() {
        return true;
    }

}
