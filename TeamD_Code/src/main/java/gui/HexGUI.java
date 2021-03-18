package gui;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;

import control.ObjectToColorConverter;
import model.*;

@SuppressWarnings("serial")
public class HexGUI extends Drawable {

    Resource resourceToColor;
    private final int r = 85;
    private ObjectToColorConverter colorConverter = new ObjectToColorConverter();

    public HexGUI(Hex self, int x, int y) {
        this.resourceToColor = self.getResource();
        this.xCoord = x;
        this.yCoord = y;
    }

    public HexGUI(Resource resource, int x, int y) {
        this.resourceToColor = resource;
        this.xCoord = x;
        this.yCoord = y;
    }

    @Override
    public Color getColor() {
        return this.colorConverter.resourceToColor(resourceToColor);
    }

    @Override
    public Shape getShape() {
        Polygon p = new Polygon();

        p.addPoint(xCoord, yCoord + r);
        p.addPoint(getTopRowX(), getLeftHorizontalEdgeY());
        p.addPoint(getTopRowX(), getRightHorizontalEdgeY());
        p.addPoint(xCoord, yCoord - r);
        p.addPoint(getBottomRowX(), getRightHorizontalEdgeY());
        p.addPoint(getBottomRowX(), getLeftHorizontalEdgeY());

        return p;
    }

    private int getBottomRowX() {
        return (int) (Math.ceil(xCoord - Math.sqrt(3) * r / 2));
    }

    private int getTopRowX() {
        return (int) (xCoord + Math.sqrt(3) * r / 2);
    }

    private int getLeftHorizontalEdgeY() {
        return yCoord + r / 2;
    }

    private int getRightHorizontalEdgeY() {
        return yCoord + -r / 2;
    }
}
