package model;

import java.time.Year;
import java.util.ResourceBundle;

public class YearOfPlentyCard extends DevelopmentCard {

    private ResourceBundle messages;

    public YearOfPlentyCard(ResourceBundle messages) {
        this.messages = messages;
    }

    @Override
    public String getName() {
        return messages.getString("YearOfPlentyCard.0");
    }

    @Override
    public void use(Player owner) {
    	owner.removeDevelopmentCard(this);
    }


}
