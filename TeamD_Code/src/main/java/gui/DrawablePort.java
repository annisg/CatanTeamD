package gui;

import control.ObjectToColorConverter;
import model.MapPosition;
import model.Port;
import model.SpecificPort;

import java.awt.*;

public class DrawablePort extends Drawable {
    public static final int PORT_HEIGHT = 100;
    public static final Color GENERIC_COLOR = Color.BLACK;
    public static final Color BORDER_COLOR = Color.DARK_GRAY;
    private static final int PORT_WIDTH = 100;
    private final Port port;
    private final MapPosition position;
    private final int x;
    private final int y;
    private final ObjectToColorConverter colorConverter;

    public DrawablePort(Port port, MapPosition pos, int x, int y) {
        this.port = port;
        this.position = pos;
        this.x = x;
        this.y = y;
        colorConverter = new ObjectToColorConverter();
    }

    @Override
    public Color getColor() {
        if (port instanceof SpecificPort) {
            SpecificPort specificPort = ((SpecificPort) port);

            return colorConverter.resourceToColor(specificPort.getPortResource());
        } else {
            return GENERIC_COLOR;
        }

    }

    @Override
    public Shape getShape() {
        return null;
    }

    @Override
    public void drawComponent(Graphics g) {
        g.setColor(getColor());

        g.fillOval(x - (PORT_WIDTH / 2), y - (PORT_HEIGHT / 2), PORT_WIDTH, PORT_HEIGHT);

        g.setColor(BORDER_COLOR);
        g.drawOval(x - (PORT_WIDTH / 2), y - (PORT_HEIGHT / 2), PORT_WIDTH, PORT_HEIGHT);
    }
}
