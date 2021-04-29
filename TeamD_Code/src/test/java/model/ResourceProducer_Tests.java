package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class ResourceProducer_Tests {
    
    private PlayerColor colors[] = { PlayerColor.RED, PlayerColor.BLUE, PlayerColor.WHITE, PlayerColor.ORANGE };
    private Resource resources[] = { Resource.GRAIN, Resource.BRICK, Resource.LUMBER, Resource.ORE, Resource.WOOL };

    @Test
    public void testRollDice() {
        
        Random rand = EasyMock.mock(Random.class);
        ResourceProducer producer = new ResourceProducer(rand);
        
        EasyMock.expect(rand.nextInt(5)).andReturn(1);
        EasyMock.expect(rand.nextInt(5)).andReturn(1);

        EasyMock.replay(rand);
        
        assertEquals(4, producer.rollDice());
        
        EasyMock.verify(rand);
        
    }
    
    @Test
    public void testProduceResourcesTwoHexesProduce() {
        
        int[] rolls = { 3, 6, 8, 11 };
        
        ResourceProducer producer = EasyMock.partialMockBuilder(ResourceProducer.class)
                .addMockedMethod("rollDice").addMockedMethod("produceHex").createMock();
        GameMap map = EasyMock.mock(GameMap.class);
        
        ArrayList<Player> players = mockedPlayerList();
        
        ArrayList<Hex> producingHexes = new ArrayList<Hex>();
        producingHexes.add(EasyMock.mock(Hex.class));
        producingHexes.add(EasyMock.mock(Hex.class));
        
        
        testProduceResourceWithRolls(rolls, producer, map, players, producingHexes);
        
    }
    
    @Test
    public void testProduceResourcesOneHexProduces() {
        
        int[] rolls = { 2, 12 };
        
        ResourceProducer producer = EasyMock.partialMockBuilder(ResourceProducer.class)
                .addMockedMethod("rollDice").addMockedMethod("produceHex").createMock();
        GameMap map = EasyMock.mock(GameMap.class);
        ArrayList<Player> players = mockedPlayerList();
        
        ArrayList<Hex> producingHexes = new ArrayList<Hex>();
        producingHexes.add(EasyMock.mock(Hex.class));        
        
        testProduceResourceWithRolls(rolls, producer, map, players, producingHexes);
        
    }

    private void testProduceResourceWithRolls(int[] rolls, ResourceProducer producer, GameMap map,
            ArrayList<Player> players, ArrayList<Hex> producingHexes) {
        
        TurnTracker tracker = mockedTurnTracker(players);
        
        for(int i = 0; i < rolls.length; i++) {
            
            EasyMock.expect(producer.rollDice()).andReturn(i);
            EasyMock.expect(map.getHexesByResourceNumber(i)).andReturn(producingHexes);
            
            for(Hex hex : producingHexes) {
                producer.produceHex(hex, map, tracker);
            }

        }
        
        EasyMock.replay(producer, map, tracker);
        EasyMock.replay(producingHexes.toArray());
        EasyMock.replay(players.toArray());
        
        for(int i = 0; i < rolls.length; i++) {
            producer.produceResources(map, tracker, producer.rollDice());
        }
        
        EasyMock.verify(producer, map, tracker);
        EasyMock.verify(producingHexes.toArray());
        EasyMock.verify(players.toArray());
    }

    @Test
    public void testProduceResourcesActivateRobber() {
        
        ResourceProducer producer = EasyMock.partialMockBuilder(ResourceProducer.class)
                .addMockedMethod("rollDice").addMockedMethod("produceHex").createMock();
        GameMap map = EasyMock.mock(GameMap.class);
        ArrayList<Player> players = mockedPlayerList();
        TurnTracker tracker = mockedTurnTracker(players);
        
        EasyMock.expect(producer.rollDice()).andReturn(7);
        
        EasyMock.replay(producer, map, tracker);
        EasyMock.replay(players.toArray());
        
        producer.produceResources(map, tracker, producer.rollDice());
        
        EasyMock.verify(producer, map, tracker);
        EasyMock.verify(players.toArray());
        
    }
    
    @Test
    public void testProduceHex() {
        
        GameMap map = EasyMock.mock(GameMap.class);
        
        ArrayList<Hex> hexesToProduce = new ArrayList<Hex>();
        for(Resource resource: resources) {
            hexesToProduce.add(mockedHex(resource));
        }
        
        ArrayList<Player> players = mockedPlayerList();
        TurnTracker tracker = mockedTurnTracker(players);
        
        ArrayList<Intersection> surroundingIntersections = new ArrayList<Intersection>();
        for(int i = 0; i < 4; i++) {
            surroundingIntersections.add(mockedSettlementIntersection(colors[i]));
        }
        surroundingIntersections.add(mockedEmptyIntersection());
        surroundingIntersections.add(mockedCityIntersection(PlayerColor.RED));
        
        ResourceProducer producer = EasyMock.partialMockBuilder(ResourceProducer.class).addMockedMethod("produceIntersection").createMock();

        for(Hex hex : hexesToProduce) {
            EasyMock.expect(map.getAllIntersectionsFromHex(hex)).andReturn(surroundingIntersections);
        }
        
        for(Intersection intersection: surroundingIntersections) {
            for(Resource resource: resources) {
                producer.produceIntersection(intersection, resource, tracker);
            }
        }
        
        EasyMock.replay(map, producer, tracker);
        EasyMock.replay(hexesToProduce.toArray());
        EasyMock.replay(players.toArray());
        EasyMock.replay(surroundingIntersections.toArray());
        
        for(Hex hex: hexesToProduce) {
            producer.produceHex(hex, map, tracker);
        }
        
        EasyMock.verify(map, producer, tracker);
        EasyMock.verify(hexesToProduce.toArray());
        EasyMock.verify(players.toArray());
        EasyMock.verify(surroundingIntersections.toArray());
        
    }

    private Hex mockedHex(Resource resource) {
        
        Hex hex = EasyMock.mock(Hex.class);
        EasyMock.expect(hex.getResource()).andStubReturn(resource);
        hex.hasRobber = false;
        
        if(resource != Resource.DESERT) {
            EasyMock.expect(hex.hasRobber()).andStubReturn(false);
        }
        
        return hex;
        
    }
    
    @Test
    public void testProduceHexWithRobber() {
        
        Random rand = EasyMock.mock(Random.class);
        GameMap map = EasyMock.mock(GameMap.class);
        ResourceProducer producer = new ResourceProducer(rand);
        Hex robberHex = mockedRobberHex(Resource.ORE);
        
        ArrayList<Player> players = mockedPlayerList();
        TurnTracker tracker = mockedTurnTracker(players);
                
        EasyMock.replay(map, rand, robberHex, tracker);
        EasyMock.replay(players.toArray());
        
        producer.produceHex(robberHex, map, tracker);
        
        EasyMock.verify(map, rand, robberHex, tracker);
        EasyMock.verify(players.toArray());
        
    }
    
    @Test
    public void testProduceDesertHexNoRobber() {
        
        Random rand = EasyMock.mock(Random.class);
        GameMap map = EasyMock.mock(GameMap.class);
        ResourceProducer producer = new ResourceProducer(rand);
        Hex desertHex = EasyMock.mock(Hex.class);
        EasyMock.expect(desertHex.getResource()).andStubReturn(Resource.DESERT);
        EasyMock.expect(desertHex.hasRobber()).andStubReturn(false);
        desertHex.hasRobber = false;
        
        ArrayList<Player> players = mockedPlayerList();
        TurnTracker tracker = mockedTurnTracker(players);
                
        EasyMock.replay(map, rand, desertHex, tracker);
        EasyMock.replay(players.toArray());
        
        producer.produceHex(desertHex, map, tracker);
        
        EasyMock.verify(map, rand, desertHex, tracker);
        EasyMock.verify(players.toArray());
        
    }
    
    private Hex mockedRobberHex(Resource resource) {
        Hex hex = EasyMock.mock(Hex.class);
        EasyMock.expect(hex.getResource()).andStubReturn(resource);
        EasyMock.expect(hex.hasRobber()).andStubReturn(true);
        hex.hasRobber = true;
        return hex;
    }
    
    

    private Intersection mockedCityIntersection(PlayerColor color) {
        Intersection cityIntersection = EasyMock.mock(Intersection.class);
        EasyMock.expect(cityIntersection.hasSettlement()).andStubReturn(false);
        EasyMock.expect(cityIntersection.hasCity()).andStubReturn(true);
        EasyMock.expect(cityIntersection.getBuildingColor()).andStubReturn(color);
        
        return cityIntersection;
    }

    private Intersection mockedEmptyIntersection() {
        Intersection emptyIntersection = EasyMock.mock(Intersection.class);
        EasyMock.expect(emptyIntersection.hasSettlement()).andStubReturn(false);
        EasyMock.expect(emptyIntersection.hasCity()).andStubReturn(false);
    
        return emptyIntersection;
    }

    private Intersection mockedSettlementIntersection(PlayerColor color) {
        Intersection intersection = EasyMock.mock(Intersection.class);
        EasyMock.expect(intersection.hasSettlement()).andStubReturn(true);
        EasyMock.expect(intersection.hasCity()).andStubReturn(false);
        EasyMock.expect(intersection.getBuildingColor()).andStubReturn(color);
    
        return intersection;
    }
    
    private TurnTracker mockedTurnTracker(ArrayList<Player> players) {
        
        TurnTracker tracker = EasyMock.mock(TurnTracker.class);
        
        EasyMock.expect(tracker.getNumPlayers()).andStubReturn(players.size());
        
        for(int i = 0; i < players.size(); i++) {
            EasyMock.expect(tracker.getPlayer(i)).andStubReturn(players.get(i));
        }
        
        return tracker;
    }
    
    private ArrayList<Player> mockedPlayerList() {
        ArrayList<Player> list = new ArrayList<Player>();
        
        for(PlayerColor color : colors) {
            Player player = EasyMock.mock(Player.class);
            EasyMock.expect(player.getColor()).andStubReturn(color);
            
            list.add(player);
        }
        
        return list;
    }
    
    @Test
    public void testProduceEmptyIntersection() {
        
        Random rand = EasyMock.mock(Random.class);
        Intersection emptyIntersection = mockedEmptyIntersection();
        ArrayList<Player> players = mockedPlayerList();
        TurnTracker tracker = mockedTurnTracker(players);
        
        ResourceProducer producer = new ResourceProducer(rand);
        
        EasyMock.replay(rand, emptyIntersection, tracker);
        EasyMock.replay(players.toArray());
        
        producer.produceIntersection(emptyIntersection, Resource.ORE, tracker);
        
        EasyMock.verify(rand, emptyIntersection, tracker);
        EasyMock.verify(players.toArray());
        
    }
    
    @Test
    public void testProduceSettlementIntersection() {
        
        Random rand = EasyMock.mock(Random.class);
        
        ArrayList<Intersection> intersections = new ArrayList<Intersection>();
        for(PlayerColor color : colors) {
            Intersection intersection = mockedSettlementIntersection(color);
            intersections.add(intersection);
        }
            
        ArrayList<Player> players = mockedPlayerList();
        for(int i = 0; i < players.size(); i++) {
            players.get(i).giveResource(resources[i], 1);
        }
        
        TurnTracker tracker = mockedTurnTracker(players);
        
        
        ResourceProducer producer = new ResourceProducer(rand);
        
        EasyMock.replay(rand, tracker);
        EasyMock.replay(players.toArray());
        EasyMock.replay(intersections.toArray());
        
        for(int i = 0; i < intersections.size(); i++ ) {
            producer.produceIntersection(intersections.get(i), resources[i], tracker);
        }
        
        EasyMock.verify(rand, tracker);
        EasyMock.verify(players.toArray()); 
        EasyMock.verify(intersections.toArray());

    }
    
    @Test
    public void testProduceCityIntersection() {
        
        Random rand = EasyMock.mock(Random.class);
        
        ArrayList<Intersection> intersections = new ArrayList<Intersection>();
        for(PlayerColor color : colors) {
            Intersection intersection = mockedCityIntersection(color);
            intersections.add(intersection);
        }

        ArrayList<Player> players = mockedPlayerList();
        for(int i = 0; i < players.size(); i++) {
            players.get(i).giveResource(resources[i], 2);
        }
        
        TurnTracker tracker = mockedTurnTracker(players);



        ResourceProducer producer = new ResourceProducer(rand);

        EasyMock.replay(rand, tracker);
        EasyMock.replay(players.toArray());
        EasyMock.replay(intersections.toArray());

        for(int i = 0; i < intersections.size(); i++ ) {
            producer.produceIntersection(intersections.get(i), resources[i], tracker);
        }

        EasyMock.verify(rand, tracker);
        EasyMock.verify(players.toArray()); 
        EasyMock.verify(intersections.toArray());

    }

}
