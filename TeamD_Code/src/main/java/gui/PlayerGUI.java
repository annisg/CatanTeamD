package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.ResourceBundle;

import control.ObjectToColorConverter;
import model.*;

public class PlayerGUI extends Drawable {

    private final int xPlayerPosition = 1225;
    private final int yPlayerSpace = 50;
    private final int playerWidth = 300;
    private final int playerHeight = 150;
    private final int borderWidth = 10;
    private final int fontSize = 25;
    private final Font writingFont = new Font("serif", Font.BOLD, fontSize);
    private final int initalCardOffsetX = xPlayerPosition + borderWidth;
    private final int relativeResourceCardOffsetY = 40;
    private final int relativeDevelopmentCardOffsetY = 90;
    private final int cardWidth = 40;
    private final int cardHeight = 50;

    Color playerColor;
    private HashMap<Resource, Integer> numOfEachResource;
    private HashMap<DevelopmentCard, Integer> numOfEachDevelopmentCard;  //change to development card type
    int playersPosition;
    private String playerOrderDisplay;
    private ObjectToColorConverter colorConverter;
    private ResourceBundle messages;

    public PlayerGUI(Color colorOfPlayer, HashMap<Resource, Integer> numPerResourceMap,
                     HashMap<DevelopmentCard, Integer> numPerDevelopmentCard, int position, int playerOrder, ResourceBundle messages) {
        this.messages = messages;
        this.playerColor = colorOfPlayer;
        this.numOfEachResource = numPerResourceMap;
        this.numOfEachDevelopmentCard = numPerDevelopmentCard;
        this.playersPosition = position;
        this.playerOrderDisplay = MessageFormat.format(messages.getString("PlayerGUI.1"), playerOrder + 1);
        this.colorConverter = new ObjectToColorConverter();
    }


    @Override
    public Color getColor() {
        return this.playerColor;
    }

    @Override
    public Shape getShape() {
        return new Rectangle(xPlayerPosition, yPlayerSpace + playersPosition * (yPlayerSpace + playerHeight),
                playerWidth, playerHeight);
    }

    @Override
    public void drawComponent(Graphics g) {
        super.drawComponent(g);
        int boxYPos = yPlayerSpace + playersPosition * (yPlayerSpace + playerHeight);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.black);
        g2.setFont(writingFont);
        g2.drawString(this.playerOrderDisplay, xPlayerPosition + borderWidth, boxYPos + borderWidth + fontSize);

        int listIndex = 0;
        for (Resource resource : Resource.values()) {
            drawResourceCard(g2, resource, listIndex);
            listIndex++;
        }

        listIndex = 0;
        for (DevelopmentCard developmentCard : numOfEachDevelopmentCard.keySet()) {
            drawDevelopmentCard(g2, developmentCard, listIndex);
            listIndex++;
        }
    }

    private void drawResourceCard(Graphics2D g2, Resource resourceToDraw, int listOrder) {
        if (numOfEachResource.containsKey(resourceToDraw)) {
            int cardX = initalCardOffsetX + listOrder * (cardWidth + borderWidth);
            int boxYPos = yPlayerSpace + playersPosition * (yPlayerSpace + playerHeight);
            int cardY = boxYPos + relativeResourceCardOffsetY;

            g2.setColor(getColorFromResource(resourceToDraw));
            g2.fillRect(cardX, cardY, cardWidth, cardHeight);

            g2.setColor(Color.black);
            g2.drawRect(cardX, cardY, cardWidth, cardHeight);
            g2.drawString(
                    MessageFormat.format(messages.getString("PlayerGUI.2"), numOfEachResource.get(resourceToDraw)),
                    cardX + borderWidth, cardY + fontSize + borderWidth);
        }
    }

    private Color getColorFromResource(Resource resource) {
        return this.colorConverter.resourceToColor(resource);
    }

    private void drawDevelopmentCard(Graphics2D g2, DevelopmentCard developmentCard, int listOrder) {
        int cardX = initalCardOffsetX + listOrder * (cardWidth + borderWidth);
        int boxYPos = yPlayerSpace + playersPosition * (yPlayerSpace + playerHeight);
        int cardY = boxYPos + relativeDevelopmentCardOffsetY;

        g2.setColor(Color.gray);
        g2.fillRect(cardX, cardY, cardWidth, cardHeight);

        g2.setColor(Color.black);
        g2.drawRect(cardX, cardY, cardWidth, cardHeight);
        String developmentCardString = MessageFormat.format(getAbbrForDevelopmentCard(developmentCard),
                numOfEachDevelopmentCard.get(developmentCard));
        g2.drawString(developmentCardString, cardX + borderWidth / 2, cardY + fontSize + borderWidth);
    }

    public int getAmountOfSpecificCard(DevelopmentCard card){
        return numOfEachDevelopmentCard.get(card);
    }
    private String getAbbrForDevelopmentCard(DevelopmentCard developmentCard) {
        DevelopmentCard card = developmentCard;

            if (developmentCard instanceof KnightCard)
                return messages.getString("PlayerGUI.4");
            else if (developmentCard instanceof VictoryPointCard)
                return messages.getString("PlayerGUI.6");
            else if (developmentCard instanceof VictoryPointCard)
                return messages.getString("PlayerGUI.8");
            else if (developmentCard instanceof RoadBuildingCard)
                return messages.getString("PlayerGUI.10");
            else if (developmentCard instanceof MonopolyCard)
                return messages.getString("PlayerGUI.12");
            else
                return messages.getString("PlayerGUI.13");
        }


}
