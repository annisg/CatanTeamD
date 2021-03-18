package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.text.MessageFormat;
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public class HexNumGUI extends Drawable {

    private final double robberOutlineWidth = 5;
    private final int diameter = 50;
    private final int insideHexSize = 25;

    int num;
    boolean hasOutline;
    private ResourceBundle messages;

    public HexNumGUI(int num, int x, int y, boolean hasRobber, ResourceBundle messages) {
        this.messages = messages;
        this.num = num;
        this.hasOutline = hasRobber;
        xCoord = x;
        yCoord = y;
    }

    @Override
    public Color getColor() {
        return Color.lightGray;
    }

    @Override
    public Shape getShape() {
        return new Ellipse2D.Double(xCoord - insideHexSize, yCoord - insideHexSize, diameter, diameter);
    }

    @Override
    public void drawComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        if (this.hasOutline) {
            Shape outline = new Ellipse2D.Double(xCoord - insideHexSize - robberOutlineWidth,
                    yCoord - insideHexSize - robberOutlineWidth, diameter + 2 * robberOutlineWidth,
                    diameter + 2 * robberOutlineWidth);
            g2.fill(outline);
        }

        if (num != 0) {
            super.drawComponent(g);

            g2.setColor(Color.black);
            g2.drawString(MessageFormat.format(messages.getString("HexNumGUI.0"), num), xCoord - 4, yCoord + 5);
        }
    }

}
