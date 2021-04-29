package model;

import org.junit.jupiter.api.Test;

import exception.PlaceBuildingException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.easymock.EasyMock;

public class Place_City_Tests {

    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
    
    @Test
    public void TestPlaceCityExist(){
        Player player = new Player(PlayerColor.BLUE);
        Intersection intersection = new Intersection();
        intersection.setSettlement(PlayerColor.BLUE);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
        EasyMock.replay(longestRoad);
        
        propertyPlacer.placeCityOnMap(player, intersection);
        EasyMock.verify(longestRoad);
    }

    @Test
    public void TestPlaceCityWithoutRemainingPiece(){
        Player player = new Player(PlayerColor.BLUE);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
        EasyMock.replay(longestRoad);
        
        for (int i = 0; i < 4; i++) {
            Intersection intersection = new Intersection();
            intersection.setSettlement(PlayerColor.BLUE);
            propertyPlacer.placeCityOnMap(player, intersection);
        }
        try {
            Intersection intersection = new Intersection();
            intersection.setSettlement(PlayerColor.BLUE);
            propertyPlacer.placeCityOnMap(player, intersection);
            fail("Player placed more city than max possible count.");
        } catch (PlaceBuildingException e) {
            //pass
        }
        EasyMock.verify(longestRoad);
    }

    @Test
    public void TestPlaceCityOnEmptyIntersection(){
        Player player = new Player(PlayerColor.BLUE);
        Intersection intersection = new Intersection();
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
        EasyMock.replay(longestRoad);
        
        try {
            propertyPlacer.placeCityOnMap(player, intersection);
            fail("Player cannot place a city on an empty intersection");
        } catch (PlaceBuildingException e) {
            //pass
        }
        EasyMock.verify(longestRoad);

    }

    @Test
    public void TestPlaceCityOnOtherColorBlock(){
        Player player = new Player(PlayerColor.BLUE);
        Intersection intersection = new Intersection();
        intersection.setSettlement(PlayerColor.ORANGE);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
        EasyMock.replay(longestRoad);
        
        try {
            propertyPlacer.placeCityOnMap(player,intersection);
            fail("Player cannot place a city on another player's intersection");
        } catch (PlaceBuildingException e) {
            //pass
        }
        EasyMock.verify(longestRoad);
    }

    @Test
    public void TestPlaceCityOnSameColorCity(){
        Player player = new Player(PlayerColor.BLUE);
        Intersection intersection = new Intersection();
        intersection.setCity(PlayerColor.BLUE);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
        EasyMock.replay(longestRoad);
        
        try {
            propertyPlacer.placeCityOnMap(player, intersection);
            fail("Player cannot place a city on top of your existing city");
        } catch (PlaceBuildingException e) {
            //pass
        }
        EasyMock.verify(longestRoad);
    }

    @Test
    public void TestSettlementIncrementAfterCitySuccessfullyBuilt(){
        Player player = new Player(PlayerColor.BLUE);
        Intersection intersection = new Intersection();
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
        EasyMock.replay(longestRoad);
        
        intersection.setSettlement(PlayerColor.BLUE);
        player.decrementSettlementCount();
        propertyPlacer.placeCityOnMap(player, intersection);
        assertEquals(5, player.getSettlementCount());
        EasyMock.verify(longestRoad);
    }

    @Test
    public void TestPlaceCitySuccessful(){
        Player player = new Player(PlayerColor.BLUE);
        Intersection intersection = new Intersection();
        intersection.setSettlement(PlayerColor.BLUE);
        LongestRoad longestRoad = EasyMock.mock(LongestRoad.class);
        PropertyPlacer propertyPlacer = new PropertyPlacer(longestRoad, messages);
        EasyMock.replay(longestRoad);
        
        propertyPlacer.placeCityOnMap(player, intersection);
        assertTrue(intersection.hasCity());
        assertEquals(intersection.getBuildingColor(), PlayerColor.BLUE);
        EasyMock.verify(longestRoad);
    }

}
