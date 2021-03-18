package gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;

public class PlayerDisplayBackground extends Drawable {

    private int x, y, width, height;

    public PlayerDisplayBackground(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public Color getColor() {
        return Color.LIGHT_GRAY;
    }

    @Override
    public Shape getShape() {
        return new Rectangle(x, y, width, height);
    }

}