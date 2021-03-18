package gui;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;

import control.ObjectToColorConverter;
import model.PlayerColor;

@SuppressWarnings("serial")
public class EdgeGUI extends Drawable {

    private PlayerColor playerColor;
    EdgeDirection direction;
    private ObjectToColorConverter colorConverter;

    public EdgeGUI(PlayerColor playerColor2, int x, int y, EdgeDirection direction) {
        this.playerColor = playerColor2;
        this.xCoord = x;
        this.yCoord = y;
        this.direction = direction;
        this.colorConverter = new ObjectToColorConverter();
    }

    @Override
    public Color getColor() {
        return this.colorConverter.playerColorToColor(playerColor);
    }

    @Override
    public Shape getShape() {
        Polygon p = new Polygon();

        switch (direction) {
        case UP:
            p.addPoint(xCoord - 5, yCoord + 40);
            p.addPoint(xCoord + 5, yCoord + 40);
            p.addPoint(xCoord + 5, yCoord - 40);
            p.addPoint(xCoord - 5, yCoord - 40);
            break;
        case LEFT:
            p.addPoint(xCoord - 20, yCoord - 20);
            p.addPoint(xCoord - 30, yCoord - 10);
            p.addPoint(xCoord + 20, yCoord + 20);
            p.addPoint(xCoord + 30, yCoord + 10);
            break;
        case RIGHT:
            p.addPoint(xCoord - 20, yCoord + 20);
            p.addPoint(xCoord - 30, yCoord + 10);
            p.addPoint(xCoord + 20, yCoord - 20);
            p.addPoint(xCoord + 30, yCoord - 10);
            break;
        }

        return p;
    }

}
