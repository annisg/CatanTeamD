package control;

import java.awt.Color;

import model.PlayerColor;
import model.Resource;

public class ObjectToColorConverter {
    final Color redPlayer = new Color(212, 91, 91);
    final Color bluePlayer = new Color(91, 208, 212);
    final Color whitePlayer = Color.white;
    final Color orangePlayer = Color.ORANGE;

    final Color grainColor = new Color(255, 255, 128);
    final Color brickColor = new Color(204, 51, 0);
    final Color oreColor = new Color(117, 117, 163);
    final Color lumberColor = new Color(92, 214, 92);
    final Color woolColor = new Color(217, 217, 217);
    final Color desertColor = new Color(255, 191, 128);

    public Color playerColorToColor(PlayerColor playerColor) {
        switch (playerColor) {
        case RED:
            return redPlayer;
        case BLUE:
            return bluePlayer;
        case WHITE:
            return whitePlayer;
        case ORANGE:
            return orangePlayer;
        case NONE:
        default:
            return Color.black;
        }
    }

    public Color resourceToColor(Resource resource) {
        switch (resource) {
        case GRAIN:
            return grainColor;
        case BRICK:
            return brickColor;
        case ORE:
            return oreColor;
        case LUMBER:
            return lumberColor;
        case WOOL:
            return woolColor;
        case DESERT:
        default:
            return desertColor;
        }
    }
}
