package model;

import static org.junit.jupiter.api.Assertions.*;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import exception.*;
import model.Hex;
import model.Resource;
import model.Robber;

public class Robber_Tests {

    @Test
    public void testGetCurrentlyRobbedHex() {
        Hex desert = EasyMock.niceMock(Hex.class);
        desert.placeRobber();
        EasyMock.replay(desert);
        Robber testRobber = new Robber(desert);
        EasyMock.verify(desert);
        assertEquals(desert, testRobber.getHexBeingRobbed());

        Hex nonDesert = EasyMock.partialMockBuilder(Hex.class).withConstructor(Resource.BRICK, 2).createMock();
        nonDesert.placeRobber();
        EasyMock.replay(nonDesert);
        testRobber = new Robber(nonDesert);
        EasyMock.verify(nonDesert);
        assertEquals(nonDesert, testRobber.getHexBeingRobbed());
    }

    @Test
    public void testMoveRobberSuccessful() {
        Hex desert = EasyMock.niceMock(Hex.class);
        Hex nonDesert = EasyMock.partialMockBuilder(Hex.class).withConstructor(Resource.BRICK, 2).createMock();
        desert.placeRobber();
        desert.removeRobber();
        nonDesert.placeRobber();
        EasyMock.replay(desert, nonDesert);

        Robber testRobber = new Robber(desert);
        testRobber.moveRobberTo(nonDesert);
        assertEquals(nonDesert, testRobber.getHexBeingRobbed());
        EasyMock.verify(desert, nonDesert);
    }

    @Test
    public void testMoveRobberToSameSpace() {
        Hex desert = EasyMock.niceMock(Hex.class);
        Robber testRobber = new Robber(desert);
        try {
            testRobber.moveRobberTo(desert);
            fail("Does not throw exception");
        } catch (IllegalRobberMoveException e) {
            // passes
        } finally {
            assertEquals(desert, testRobber.getHexBeingRobbed());
        }
    }

}
