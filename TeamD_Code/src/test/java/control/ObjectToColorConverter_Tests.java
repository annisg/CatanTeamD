package control;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import model.PlayerColor;
import model.Resource;

public class ObjectToColorConverter_Tests {

    @Test
    public void testGetColorFromResource() {
        ObjectToColorConverter testConverter = new ObjectToColorConverter();
        assertEquals(testConverter.brickColor, testConverter.resourceToColor(Resource.BRICK));
        assertEquals(testConverter.desertColor, testConverter.resourceToColor(Resource.DESERT));
        assertEquals(testConverter.grainColor, testConverter.resourceToColor(Resource.GRAIN));
        assertEquals(testConverter.lumberColor, testConverter.resourceToColor(Resource.LUMBER));
        assertEquals(testConverter.oreColor, testConverter.resourceToColor(Resource.ORE));
        assertEquals(testConverter.woolColor, testConverter.resourceToColor(Resource.WOOL));
    }

    @Test
    public void testGetColorFromPlayerColor() {
        ObjectToColorConverter testConverter = new ObjectToColorConverter();
        assertEquals(testConverter.bluePlayer, testConverter.playerColorToColor(PlayerColor.BLUE));
        assertEquals(testConverter.orangePlayer, testConverter.playerColorToColor(PlayerColor.ORANGE));
        assertEquals(testConverter.redPlayer, testConverter.playerColorToColor(PlayerColor.RED));
        assertEquals(testConverter.whitePlayer, testConverter.playerColorToColor(PlayerColor.WHITE));
        assertEquals(Color.black, testConverter.playerColorToColor(PlayerColor.NONE));
    }

}
