package model;

import static org.junit.Assert.*;

import java.util.*;

import org.easymock.EasyMock;
import org.junit.Test;

import exception.*;

public class Player_Tests {

    Resource[] resources = {Resource.GRAIN, Resource.BRICK, Resource.ORE, Resource.LUMBER, Resource.WOOL};
    ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

    @Test
    public void testPlayerColors() {

        Player redPlayer = new Player(PlayerColor.RED);
        Player bluePlayer = new Player(PlayerColor.BLUE);
        Player whitePlayer = new Player(PlayerColor.WHITE);
        Player orangePlayer = new Player(PlayerColor.ORANGE);

        assertEquals(PlayerColor.RED, redPlayer.getColor());
        assertEquals(PlayerColor.BLUE, bluePlayer.getColor());
        assertEquals(PlayerColor.WHITE, whitePlayer.getColor());
        assertEquals(PlayerColor.ORANGE, orangePlayer.getColor());

    }

    @Test
    public void testEmptyStartingHand() {

        Player testPlayer = new Player(PlayerColor.RED);

        assertEquals(0, testPlayer.getResourceHandSize());
        assertEquals(0, testPlayer.getDevelopmentCards().size());

        for (Resource res : resources) {
            assertEquals(0, testPlayer.getResourceCount(res));
        }

    }

    @Test
    public void testGetResourceCountDesert() {

        Player testPlayer = new Player(PlayerColor.RED);

        try {
            testPlayer.getResourceCount(Resource.DESERT);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }

    }

    @Test
    public void testGiveOneResource() {

        Player testPlayer = new Player(PlayerColor.RED);

        for (int i = 0; i < resources.length; i++) {
            testPlayer.giveResource(resources[i], 1);
            assertEquals(1, testPlayer.getResourceCount(resources[i]));
            assertEquals(i + 1, testPlayer.getResourceHandSize());
        }

    }

    @Test
    public void testGiveResourceDesert() {

        Player testPlayer = new Player(PlayerColor.RED);
        tryIllegalGive(testPlayer, Resource.DESERT, 1);

    }

    @Test
    public void testGiveNegativeResources() {

        Player testPlayer = new Player(PlayerColor.RED);

        tryIllegalGive(testPlayer, Resource.GRAIN, -1);
        tryIllegalGive(testPlayer, Resource.GRAIN, Integer.MIN_VALUE);

    }

    private void tryIllegalGive(Player testPlayer, Resource resource, int amount) {
        try {
            testPlayer.giveResource(resource, amount);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGiveTwoResources() {

        Player testPlayer = new Player(PlayerColor.RED);

        for (int i = 0; i < resources.length; i++) {
            testPlayer.giveResource(resources[i], 2);
            assertEquals(2, testPlayer.getResourceCount(resources[i]));
            assertEquals(2 * (i + 1), testPlayer.getResourceHandSize());
        }
    }

    @Test
    public void testGiveZeroResources() {

        Player testPlayer = new Player(PlayerColor.RED);

        for (int i = 0; i < resources.length; i++) {
            testPlayer.giveResource(resources[i], 0);
            assertEquals(0, testPlayer.getResourceCount(resources[i]));
        }

        assertEquals(0, testPlayer.getResourceHandSize());

    }

    @Test
    public void testRemoveOneResource() {

        Player testPlayer = new Player(PlayerColor.RED);

        for (int i = 0; i < resources.length; i++) {

            testPlayer.giveResource(resources[i], 1);
            assertEquals(1, testPlayer.getResourceCount(resources[i]));

            testPlayer.removeResource(resources[i], 1);
            assertEquals(0, testPlayer.getResourceCount(resources[i]));
            assertEquals(0, testPlayer.getResourceHandSize());
        }

    }

    @Test
    public void testRemoveTwoResources() {

        Player testPlayer = new Player(PlayerColor.RED);

        for (int i = 0; i < resources.length; i++) {

            testPlayer.giveResource(resources[i], 2);
            assertEquals(2, testPlayer.getResourceCount(resources[i]));

            testPlayer.removeResource(resources[i], 2);
            assertEquals(0, testPlayer.getResourceCount(resources[i]));
            assertEquals(0, testPlayer.getResourceHandSize());
        }

    }

    @Test
    public void testRemoveZeroResources() {

        Player testPlayer = new Player(PlayerColor.RED);

        for (int i = 0; i < resources.length; i++) {

            testPlayer.giveResource(resources[i], 2);
            assertEquals(2, testPlayer.getResourceCount(resources[i]));

            testPlayer.removeResource(resources[i], 0);
            assertEquals(2, testPlayer.getResourceCount(resources[i]));

        }
    }

    @Test
    public void testRemoveFromEmptyHand() {

        Player testPlayer = new Player(PlayerColor.RED);

        for (int i = 0; i < resources.length; i++) {

            try {
                testPlayer.removeResource(resources[i], 1);
                fail("Expected RuntimeException");

            } catch (RuntimeException e) {
                assertEquals("Not enough cards to remove in hand.", e.getMessage());
            }
        }
    }

    @Test
    public void testRemoveMoreCardsThanHeld() {

        Player testPlayer = new Player(PlayerColor.RED);

        for (int i = 0; i < resources.length; i++) {

            try {
                testPlayer.giveResource(resources[i], i + 1);

                testPlayer.removeResource(resources[i], i + 2);
                fail("Expected RuntimeException");

            } catch (RuntimeException e) {
                assertEquals("Not enough cards to remove in hand.", e.getMessage());
            }
        }

    }

    @Test
    public void testRemoveDesertResource() {

        Player testPlayer = new Player(PlayerColor.RED);

        tryIllegalRemoveResource(testPlayer, Resource.DESERT, 1);

    }

    @Test
    public void testRemoveResourceNegativeAmount() {

        Player testPlayer = new Player(PlayerColor.RED);

        tryIllegalRemoveResource(testPlayer, Resource.GRAIN, -1);
        tryIllegalRemoveResource(testPlayer, Resource.GRAIN, Integer.MIN_VALUE);

    }

    void tryIllegalRemoveResource(Player testPlayer, Resource resource, int amount) {
        try {
            testPlayer.removeResource(resource, amount);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGiveDevelopmentCards() {

        Player testPlayer = new Player(PlayerColor.RED);
        DevelopmentCard cardOne = EasyMock.mock(DevelopmentCard.class);
        DevelopmentCard cardTwo = EasyMock.mock(DevelopmentCard.class);

        EasyMock.replay(cardOne, cardTwo);

        testPlayer.giveDevelopmentCard(cardOne);

        assertEquals(1, testPlayer.getDevelopmentCards().size());
        assertTrue(testPlayer.getDevelopmentCards().contains(cardOne));
        assertFalse(testPlayer.getDevelopmentCards().contains(cardTwo));

        testPlayer.giveDevelopmentCard(cardTwo);

        assertEquals(2, testPlayer.getDevelopmentCards().size());
        assertTrue(testPlayer.getDevelopmentCards().contains(cardOne));
        assertTrue(testPlayer.getDevelopmentCards().contains(cardTwo));

        EasyMock.verify(cardOne, cardTwo);
    }

    @Test
    public void testRemoveDevelopmentCards() {

        Player testPlayer = new Player(PlayerColor.RED);
        DevelopmentCard cardOne = EasyMock.mock(DevelopmentCard.class);
        DevelopmentCard cardTwo = EasyMock.mock(DevelopmentCard.class);

        EasyMock.replay(cardOne, cardTwo);

        testPlayer.giveDevelopmentCard(cardOne);
        testPlayer.giveDevelopmentCard(cardTwo);

        assertEquals(2, testPlayer.getDevelopmentCards().size());

        testPlayer.removeDevelopmentCard(cardOne);

        assertEquals(1, testPlayer.getDevelopmentCards().size());
        assertFalse(testPlayer.getDevelopmentCards().contains(cardOne));
        assertTrue(testPlayer.getDevelopmentCards().contains(cardTwo));

        testPlayer.removeDevelopmentCard(cardTwo);

        assertEquals(0, testPlayer.getDevelopmentCards().size());
        assertFalse(testPlayer.getDevelopmentCards().contains(cardOne));
        assertFalse(testPlayer.getDevelopmentCards().contains(cardTwo));

        EasyMock.verify(cardOne, cardTwo);

    }

    @Test
    public void testRemoveDevelopmentCardNotInHand() {

        Player testPlayer = new Player(PlayerColor.RED);
        DevelopmentCard card = EasyMock.mock(DevelopmentCard.class);

        EasyMock.replay(card);

        try {
            testPlayer.removeDevelopmentCard(card);
            fail("Expected ItemNotFoundException");
        } catch (ItemNotFoundException e) {
            assertEquals("Attempted to remove a development card that was not in hand.", e.getMessage());
        }

        EasyMock.verify(card);

    }

    @Test
    public void testIncrementKnightCount() {

        Player testPlayer = new Player(PlayerColor.RED);

        assertEquals(0, testPlayer.getNumKnights());

        for (int i = 1; i < 5; i++) {
            testPlayer.incrementKnightCount();
            assertEquals(i, testPlayer.getNumKnights());
        }

    }

    @Test
    public void testRoadCount() {
        Player testPlayer = new Player(PlayerColor.RED);
        assertEquals(15, testPlayer.getRoadCount());
        for (int i = 1; i < 14; i++) {
            testPlayer.decrementRoadCount();
            assertEquals(15 - i, testPlayer.getRoadCount());
        }
    }

    @Test
    public void testSettlementCount() {
        Player testPlayer = new Player(PlayerColor.RED);

        assertEquals(5, testPlayer.getInitialSettlementCount());
        assertEquals(5, testPlayer.getSettlementCount());
        for (int i = 1; i < 5; i++) {
            testPlayer.decrementSettlementCount();
            assertEquals(5 - i, testPlayer.getSettlementCount());
        }
        for (int i = 2; i < 5; i++) {
            testPlayer.incrementSettlementCount();
            assertEquals(i, testPlayer.getSettlementCount());
        }
    }

    @Test
    public void testCityCount() {
        Player testPlayer = new Player(PlayerColor.RED);

        assertEquals(4, testPlayer.getInitialCityCount());
        assertEquals(4, testPlayer.getCityCount());
        for (int i = 1; i < 3; i++) {
            testPlayer.decrementCityCount();
            assertEquals(4 - i, testPlayer.getCityCount());
        }
    }

    @Test
    public void testFindDevelopmentCardNotFound() {
        Player testPlayer = EasyMock.partialMockBuilder(Player.class)
                .addMockedMethod("getDevelopmentCards")
                .mock();
        ArrayList<DevelopmentCard> cards = new ArrayList<DevelopmentCard>();
        EasyMock.expect(testPlayer.getDevelopmentCards()).andReturn(cards);
        EasyMock.replay(testPlayer);

        try {
            testPlayer.getDevelopmentCard(KnightCard.class);
            fail();
        } catch (ItemNotFoundException e) {
        }
        ;
        EasyMock.verify(testPlayer);
    }

    @Test
    public void testFindDevelopmentCardFoundButNotPlayable() {
        Player testPlayer = EasyMock.partialMockBuilder(Player.class)
                .addMockedMethod("getDevelopmentCards")
                .mock();
        MonopolyCard correctCard = new MonopolyCard(messages);
        ArrayList<DevelopmentCard> cards = new ArrayList<DevelopmentCard>(Arrays.asList(correctCard,
                new VictoryPointCard(messages)));
        EasyMock.expect(testPlayer.getDevelopmentCards()).andReturn(cards);
        EasyMock.replay(testPlayer);

        try {
            testPlayer.getDevelopmentCard(MonopolyCard.class);
            fail();
        } catch (ItemNotFoundException e) {
        }
        ;
        EasyMock.verify(testPlayer);
    }

    @Test
    public void testFindDevelopmentCardFoundFirst() {
        Player testPlayer = EasyMock.partialMockBuilder(Player.class)
                .addMockedMethod("getDevelopmentCards")
                .mock();
        MonopolyCard correctCard = new MonopolyCard(messages);
        correctCard.makePlayable();
        ArrayList<DevelopmentCard> cards = new ArrayList<DevelopmentCard>(Arrays.asList(correctCard,
                new VictoryPointCard(messages)));
        EasyMock.expect(testPlayer.getDevelopmentCards()).andReturn(cards);
        EasyMock.replay(testPlayer);

        assertEquals(correctCard, testPlayer.getDevelopmentCard(MonopolyCard.class));
        EasyMock.verify(testPlayer);
    }

    @Test
    public void testFindDevelopmentCardFoundLast() {
        Player testPlayer = EasyMock.partialMockBuilder(Player.class)
                .addMockedMethod("getDevelopmentCards")
                .mock();
        MonopolyCard correctCard = new MonopolyCard(messages);
        correctCard.makePlayable();
        ArrayList<DevelopmentCard> cards =
                new ArrayList<DevelopmentCard>(Arrays.asList(new VictoryPointCard(messages), correctCard));
        EasyMock.expect(testPlayer.getDevelopmentCards()).andReturn(cards);
        EasyMock.replay(testPlayer);

        assertEquals(correctCard, testPlayer.getDevelopmentCard(MonopolyCard.class));
        EasyMock.verify(testPlayer);
    }

    @Test
    public void testFindDevelopmentCardMultiple() {
        Player testPlayer = EasyMock.partialMockBuilder(Player.class)
                .addMockedMethod("getDevelopmentCards")
                .mock();
        MonopolyCard correctCard = new MonopolyCard(messages);
        correctCard.makePlayable();
        ArrayList<DevelopmentCard> cards = new ArrayList<DevelopmentCard>(Arrays.asList(correctCard,
                new MonopolyCard(messages)));
        EasyMock.expect(testPlayer.getDevelopmentCards()).andReturn(cards);
        EasyMock.replay(testPlayer);

        assertEquals(correctCard, testPlayer.getDevelopmentCard(MonopolyCard.class));
        EasyMock.verify(testPlayer);
    }

    @Test
    public void testLetAllCardsBePlayed() {
        Player testPlayer = EasyMock.partialMockBuilder(Player.class)
                .addMockedMethod("getDevelopmentCards")
                .mock();
        MonopolyCard mockedCard1 = EasyMock.strictMock(MonopolyCard.class);
        KnightCard mockedCard2 = EasyMock.strictMock(KnightCard.class);
        ArrayList<DevelopmentCard> cards = new ArrayList<DevelopmentCard>(Arrays.asList(mockedCard1, mockedCard2));

        EasyMock.expect(testPlayer.getDevelopmentCards()).andReturn(cards);
        mockedCard1.makePlayable();
        mockedCard2.makePlayable();
        EasyMock.replay(testPlayer, mockedCard1, mockedCard2);

        testPlayer.letAllDevelopmentCardsBePlayed();
        EasyMock.verify(testPlayer, mockedCard1, mockedCard2);
    }

    @Test
    public void testStealingFromNoResources() {
        Random randomMock = EasyMock.strictMock(Random.class);
        Player robber = new Player(PlayerColor.RED);
        Player victim = new Player(PlayerColor.BLUE);
        robber.setRandom(randomMock);
        EasyMock.replay(randomMock);

        Resource actual = robber.stealRandomResourceFrom(victim);

        assertEquals(Resource.DESERT, actual);  // Desert used as null/none object
        EasyMock.verify(randomMock);
    }

    @Test
    public void testStealingFromWithResources() {
        Random randomMock = EasyMock.strictMock(Random.class);
        Player robber = new Player(PlayerColor.RED);
        Player victim = new Player(PlayerColor.BLUE);

        // Because resources are stored as a hashmap, removal is not a "conveniently" predicable order.
        //  As such, only one type is used.
        victim.giveResource(Resource.BRICK, 4);

        robber.setRandom(randomMock);
        EasyMock.expect(randomMock.nextInt(4)).andReturn(2);
        EasyMock.replay(randomMock);

        Resource actual = robber.stealRandomResourceFrom(victim);

        assertEquals(Resource.BRICK, actual);
        assertEquals(1, robber.getResourceCount(Resource.BRICK));
        assertEquals(3, victim.getResourceHandSize());
        EasyMock.verify(randomMock);
    }

    @Test
    public void testStealingFromSelf() {
        Random randomMock = EasyMock.strictMock(Random.class);
        Player robber = new Player(PlayerColor.RED);

        robber.giveResource(Resource.BRICK, 4);

        robber.setRandom(randomMock);
        EasyMock.expect(randomMock.nextInt(4)).andReturn(2);
        EasyMock.replay(randomMock);

        Resource actual = robber.stealRandomResourceFrom(robber);

        assertEquals(Resource.BRICK, actual);
        assertEquals(4, robber.getResourceCount(Resource.BRICK));
        assertEquals(4, robber.getResourceHandSize());
        EasyMock.verify(randomMock);
    }

    @Test
    public void testStealingAll() {
        Player robber = new Player(PlayerColor.RED);
        Player victim = new Player(PlayerColor.BLUE);

        robber.giveResource(Resource.BRICK, 1);

        victim.giveResource(Resource.GRAIN, 3);
        victim.giveResource(Resource.BRICK, 4);
        victim.giveResource(Resource.ORE, 5);


        int actual = robber.stealAllOfResourceFrom(victim, Resource.BRICK);

        assertEquals(4, actual);
        assertEquals(5, robber.getResourceCount(Resource.BRICK));
        assertEquals(5, robber.getResourceHandSize());

        assertEquals(0, victim.getResourceCount(Resource.BRICK));
        assertEquals(3 + 5, victim.getResourceHandSize());
    }

    @Test
    public void testStealingAllFromSelf() {
        Player robber = new Player(PlayerColor.RED);

        robber.giveResource(Resource.GRAIN, 3);
        robber.giveResource(Resource.BRICK, 4);
        robber.giveResource(Resource.ORE, 5);

        int actual = robber.stealAllOfResourceFrom(robber, Resource.BRICK);

        assertEquals(4, actual);
        assertEquals(3, robber.getResourceCount(Resource.GRAIN));
        assertEquals(4, robber.getResourceCount(Resource.BRICK));
        assertEquals(5, robber.getResourceCount(Resource.ORE));
        assertEquals(3 + 4 + 5, robber.getResourceHandSize());

    }
}
