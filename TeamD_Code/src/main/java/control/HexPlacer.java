package control;

import java.util.ArrayList;
import java.util.ResourceBundle;

import exception.*;
import gui.*;
import model.*;

public class HexPlacer {
    private static final int height = 800;
    private static final int heightOffset = 130;
    private static final int widthOffset = 75;
    private static final int leftMin = 450;

    HexMap hexes;
    private ResourceBundle messages;

    public HexPlacer(HexMap hexes, ResourceBundle messages) {
        this.hexes = hexes;
        this.messages = messages;
    }

    public void refreshHexes(HexMap newHexes) {
        this.hexes = newHexes;
    }

    public ArrayList<Drawable> getAllDrawables() {
        ArrayList<Drawable> drawables = new ArrayList<Drawable>();

        for (int row = 0; row < this.hexes.getNumberOfRows(); row++) {

            for (int col = 0; col < this.hexes.getNumberOfHexesInRow(row); col++) {

                Hex currentHex = this.hexes.getHex(new MapPosition(row, col));

                drawables.add(getHexDrawable(currentHex, row, col));
                drawables.add(getHexNumDrawable(currentHex, row, col));
            }
        }

        return drawables;
    }

    HexGUI getHexDrawable(Hex hexObject, int row, int col) {
        int[] position = calculatePosition(row, col);
        return new HexGUI(hexObject.getResource(), position[0], position[1]);
    }

    HexNumGUI getHexNumDrawable(Hex hexObject, int row, int col) {
        int[] position = calculatePosition(row, col);
        return new HexNumGUI(hexObject.getRollResourceNumber(), position[0], position[1], hexObject.hasRobber(),
                messages);
    }

    public static int[] calculatePosition(int row, int col) {
        int x = col * widthOffset * 2;
        int y = -row * heightOffset + height;
        if (row == 0 || row == 4) {
            x += leftMin + 2 * widthOffset;

        } else if (row == 1 || row == 3) {
            x += leftMin + widthOffset;

        } else if (row == 2) {
            x += leftMin;

        } else {
            throw new InvalidHexPositionException();

        }
        return new int[] { x, y };
    }
}
