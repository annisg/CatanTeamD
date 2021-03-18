package gui;

import java.awt.*;

public class SpecialCardGUI extends Drawable {

    private final int fontSize = 25;
    private final Font writingFont = new Font("serif", Font.BOLD, fontSize);
    private final int strokeWidth = 10;
    private final Stroke stroke = new java.awt.BasicStroke(strokeWidth);
    private final int cardBoxHeight = 75;
    private final int cardBoxWidth = 200;
    private final int cardBoxXOffset = 50;
    private final int cardBoxYOffset = 150;
    private final int textXOffset = 25;
    private final int textYOffset = 50;

    Color holderColor;
    int order;
    String cardName;

    public SpecialCardGUI(String cardName, Color holderColor, int order) {
        this.holderColor = holderColor;
        this.order = order;
        this.cardName = cardName;
    }

    @Override
    public Color getColor() {
        return Color.LIGHT_GRAY;
    }

    @Override
    public Shape getShape() {
        return getRectangle();
    }

    private Rectangle getRectangle() {
        return new Rectangle(0, 0, 0, 0);
    }

    @Override
    public void drawComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getColor());
        g2.fillRect(cardBoxXOffset, cardBoxYOffset * order, cardBoxWidth, cardBoxHeight);

        g2.setColor(this.holderColor);
        g2.setStroke(stroke);
        g2.drawRect(cardBoxXOffset, cardBoxYOffset * order, cardBoxWidth, cardBoxHeight);

        g2.setColor(Color.black);
        g2.setFont(writingFont);
        g2.drawString(this.cardName, cardBoxXOffset + textXOffset, cardBoxYOffset * order + textYOffset);
    }

}
