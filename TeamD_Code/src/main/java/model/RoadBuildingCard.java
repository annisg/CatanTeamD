package model;

import java.util.ResourceBundle;

public class RoadBuildingCard extends DevelopmentCard {

    private ResourceBundle messages;

    public RoadBuildingCard(ResourceBundle messages) {
        this.messages = messages;
    }

    @Override
    public String getName() {
        return messages.getString("RoadBuildingCard.0");
    }

    @Override
    public void use(Player owner) {
        throw new RuntimeException(messages.getString("RoadBuildingCard.1"));
    }

}
