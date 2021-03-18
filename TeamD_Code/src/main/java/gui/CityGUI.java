package gui;

import java.awt.Rectangle;
import java.awt.Shape;

import model.PlayerColor;

@SuppressWarnings("serial")
public class CityGUI extends SettlementGUI {

    public CityGUI(PlayerColor playerColor, int x, int y, boolean rotate) {
        super(playerColor, x, y, rotate);
    }

    @Override
    public Shape getShape() {
        return new Rectangle(xCoord - 15, yCoord - 15, 30, 30);
    }

}
