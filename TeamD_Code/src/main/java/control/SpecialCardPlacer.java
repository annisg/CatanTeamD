package control;

import java.awt.Color;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gui.*;
import model.*;

public class SpecialCardPlacer {

    private LongestRoad longestRoad;
    private LargestArmy largestArmy;
    private ObjectToColorConverter converter;
    private ResourceBundle messages;

    public SpecialCardPlacer(LongestRoad longestRoad, LargestArmy largestArmy, ResourceBundle messages) {
        this.longestRoad = longestRoad;
        this.largestArmy = largestArmy;
        this.converter = new ObjectToColorConverter();
        this.messages = messages;
    }

    public ArrayList<Drawable> getSpecialCards() {
        ArrayList<Drawable> cards = new ArrayList<Drawable>();

        Color longestRoadColor = this.converter.playerColorToColor(this.longestRoad.getHolder());
        Color largestArmyColor = this.converter.playerColorToColor(this.largestArmy.getHolder());
        cards.add(new SpecialCardGUI(messages.getString("SpecialCardPlacer.0"), longestRoadColor, 1));
        cards.add(new SpecialCardGUI(messages.getString("SpecialCardPlacer.1"), largestArmyColor, 2));

        return cards;
    }

}
