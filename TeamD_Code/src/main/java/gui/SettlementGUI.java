package gui;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;

import control.ObjectToColorConverter;
import model.PlayerColor;

@SuppressWarnings("serial")
public class SettlementGUI extends Drawable {

    private PlayerColor playerColor;
    private boolean rotate;
    private ObjectToColorConverter colorConverter;

    public SettlementGUI(PlayerColor playerColor2, int x, int y, boolean rotate) {
        this.playerColor = playerColor2;
        this.xCoord = x;
        this.yCoord = y;
        this.rotate = rotate;
        this.colorConverter = new ObjectToColorConverter();
    }

    @Override
    public Color getColor() {
        return this.colorConverter.playerColorToColor(playerColor);
    }

    @Override
    public Shape getShape() {
        Polygon p = new Polygon();
        if (rotate) {
            p.addPoint(xCoord - 20, yCoord + 20);
            p.addPoint(xCoord + 20, yCoord + 20);
            p.addPoint(xCoord, yCoord - 15);
        } else {
            p.addPoint(xCoord + 20, yCoord - 20);
            p.addPoint(xCoord - 20, yCoord - 20);
            p.addPoint(xCoord, yCoord + 15);
        }
        return p;
    }

}
