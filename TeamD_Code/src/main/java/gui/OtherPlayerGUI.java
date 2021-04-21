package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import control.ObjectToColorConverter;
import model.Resource;

public class OtherPlayerGUI extends Drawable{
    
    private final int xPlayerPosition = 1225;
    private final int yPlayerSpaceInitial = 250;
    private final int yPlayerSpace = 20;
    private final int playerWidth = 300;
    private final int playerHeight = 150;
    private final int borderWidth = 10;
    private final int fontSize = 25;
    private final Font writingFont = new Font("serif", Font.BOLD, fontSize);
    private final int initalCardOffsetX = xPlayerPosition + borderWidth;
    private final int relativeDataOffsetY = 40;
    private final int relativeLabelOffsetY = 90;
    private final int cardWidth = 40;
    private final int cardHeight = 50;
    
    Color playerColor;
    private int numOfResources;
    private int numOfDevelopmentCards;
    int playersPosition;
    private String playerDisplay;
    private ObjectToColorConverter colorConverter;
    int numKnights;

    public OtherPlayerGUI(Color colorOfPlayer, Collection<Integer> numbersOfResources,
            Collection<Integer> numbersOfDevCards, int position, int playerOrder, int numKnights) {
        this.playerColor = colorOfPlayer;
        this.playersPosition = position;
        this.numKnights = numKnights;
        this.colorConverter = new ObjectToColorConverter();
        
        for(int i : numbersOfResources) {
            numOfResources += i;
        }
        
        for(int i : numbersOfDevCards) {
            numOfDevelopmentCards += i;
        }
        
        this.playerDisplay = MessageFormat.format("Player {0}", playerOrder + 1);
    }

    @Override
    public Color getColor() {
        return this.playerColor;
    }

    @Override
    public Shape getShape() {
        return new Rectangle(xPlayerPosition, yPlayerSpaceInitial + playersPosition * (yPlayerSpace + playerHeight),
                playerWidth, playerHeight);
    }
    
    @Override
    public void drawComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        super.drawComponent(g);
        drawPlayerDisplay(g2);
        drawResourceCardNum(g2);
        drawDevCardNum(g2);
        drawKnightNum(g2);
    }
    
    private void drawPlayerDisplay(Graphics2D g2) {
        int boxYPos = yPlayerSpaceInitial + playersPosition * (yPlayerSpace + playerHeight);
        g2.setColor(Color.black);
        g2.setFont(writingFont);
        g2.drawString(this.playerDisplay, xPlayerPosition + borderWidth, boxYPos + borderWidth + fontSize);
    }
    
    private void drawResourceCardNum(Graphics2D g2) {
        int cardX = initalCardOffsetX;
        int boxYPos = yPlayerSpaceInitial + playersPosition * (yPlayerSpace + playerHeight);
        int cardY = boxYPos + relativeDataOffsetY;

        g2.setColor(Color.white);
        g2.fillRect(cardX, cardY, cardWidth, cardHeight);

        g2.setColor(Color.black);
        g2.drawRect(cardX, cardY, cardWidth, cardHeight);
        g2.drawString(
                MessageFormat.format("{0}", numOfResources),
                cardX + borderWidth, cardY + fontSize + borderWidth);
        
        cardY = boxYPos + relativeLabelOffsetY;

        g2.setColor(Color.gray);
        g2.fillRect(cardX, cardY, cardWidth, cardHeight);

        g2.setColor(Color.black);
        g2.drawRect(cardX, cardY, cardWidth, cardHeight);
        String developmentCardString = "RC";
        g2.drawString(developmentCardString, cardX + borderWidth / 2, cardY + fontSize + borderWidth);
    }
    
    private void drawDevCardNum(Graphics2D g2) {
        int cardX = initalCardOffsetX + (cardWidth + borderWidth);
        int boxYPos = yPlayerSpaceInitial + playersPosition * (yPlayerSpace + playerHeight);
        int cardY = boxYPos + relativeDataOffsetY;

        g2.setColor(Color.white);
        g2.fillRect(cardX, cardY, cardWidth, cardHeight);

        g2.setColor(Color.black);
        g2.drawRect(cardX, cardY, cardWidth, cardHeight);
        g2.drawString(
                MessageFormat.format("{0}", numOfDevelopmentCards),
                cardX + borderWidth, cardY + fontSize + borderWidth);
        
        cardY = boxYPos + relativeLabelOffsetY;

        g2.setColor(Color.gray);
        g2.fillRect(cardX, cardY, cardWidth, cardHeight);

        g2.setColor(Color.black);
        g2.drawRect(cardX, cardY, cardWidth, cardHeight);
        String developmentCardString = "DC";
        g2.drawString(developmentCardString, cardX + borderWidth / 2, cardY + fontSize + borderWidth);
    }
    
    private void drawKnightNum(Graphics2D g2) {
        int cardX = initalCardOffsetX + 2 * (cardWidth + borderWidth);
        int boxYPos = yPlayerSpaceInitial + playersPosition * (yPlayerSpace + playerHeight);
        int cardY = boxYPos + relativeDataOffsetY;

        g2.setColor(Color.white);
        g2.fillRect(cardX, cardY, cardWidth, cardHeight);

        g2.setColor(Color.black);
        g2.drawRect(cardX, cardY, cardWidth, cardHeight);
        g2.drawString(
                MessageFormat.format("{0}", numKnights),
                cardX + borderWidth, cardY + fontSize + borderWidth);
        
        cardY = boxYPos + relativeLabelOffsetY;

        g2.setColor(Color.gray);
        g2.fillRect(cardX, cardY, cardWidth, cardHeight);

        g2.setColor(Color.black);
        g2.drawRect(cardX, cardY, cardWidth, cardHeight);
        String developmentCardString = "DC";
        g2.drawString(developmentCardString, cardX + borderWidth / 2, cardY + fontSize + borderWidth);
    }

}
