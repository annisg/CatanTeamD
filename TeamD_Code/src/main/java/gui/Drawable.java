package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.JComponent;

public abstract class Drawable extends JComponent {

    int xCoord, yCoord;

    public void drawComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(this.getColor());
        g2.fill(getShape());

        g2.setColor(Color.BLACK);
        g2.draw(getShape());
    }

    public abstract Color getColor();

    public abstract Shape getShape();
}
