package control;
import gui.PortSelector;
import model.Player;
import model.Resource;
import org.easymock.EasyMock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MaritimeTradeManager_Tests {
	@Test
	public void testTradeNotRolled() {
		InputHandler mockedInputHandler = EasyMock.strictMock(InputHandler.class);
		CatanGame mockedCatanGame = EasyMock.strictMock(CatanGame.class);
		MaritimeTradeManager maritimeTradeManager = new MaritimeTradeManager(mockedInputHandler, mockedCatanGame);
		mockedInputHandler.hasNotRolled = true;
		mockedInputHandler.displayMessage("You must roll before trading.");
		EasyMock.replay(mockedInputHandler, mockedCatanGame);
		maritimeTradeManager.trade();

		EasyMock.verify(mockedInputHandler, mockedCatanGame);
	}

	@Test
	public void testTradeWithGenericPortPlayerDoesntOwn() {
		InputHandler mockedInputHandler = EasyMock.strictMock(InputHandler.class);
		CatanGame mockedCatanGame = EasyMock.strictMock(CatanGame.class);
		MaritimeTradeManager maritimeTradeManager = new MaritimeTradeManager(mockedInputHandler, mockedCatanGame);
		EasyMock.expect(mockedCatanGame.doesCurrentPlayerOwnGenericHarbor()).andReturn(false);
		mockedInputHandler.displayMessage("You do not own a Generic Harbor.");
		EasyMock.replay(mockedInputHandler, mockedCatanGame);

		maritimeTradeManager.genericPortTrade();

		EasyMock.verify(mockedInputHandler, mockedCatanGame);
	}

	@Test
	public void testTradeWithGenericPort() {
		InputHandler mockedInputHandler = EasyMock.strictMock(InputHandler.class);
		CatanGame mockedCatanGame = EasyMock.strictMock(CatanGame.class);
		Player mockedPlayer = EasyMock.strictMock(Player.class);
		Resource soldResource = Resource.BRICK;
		Resource boughtResource = Resource.GRAIN;
		MaritimeTradeManager maritimeTradeManager = new MaritimeTradeManager(mockedInputHandler, mockedCatanGame);
		maritimeTradeManager.soldResource = soldResource;
		maritimeTradeManager.boughtResource = boughtResource;
		EasyMock.expect(mockedCatanGame.doesCurrentPlayerOwnGenericHarbor()).andReturn(true);
		EasyMock.expect(mockedInputHandler.getCurrentPlayer()).andReturn(mockedPlayer);
		mockedPlayer.removeResource(soldResource, MaritimeTradeManager.GENERIC_TRADE_COST);
		mockedPlayer.giveResource(boughtResource, 1);
		mockedCatanGame.drawPlayers();

		EasyMock.replay(mockedInputHandler, mockedCatanGame, mockedPlayer);

		maritimeTradeManager.genericPortTrade();

		EasyMock.verify(mockedInputHandler, mockedCatanGame, mockedPlayer);
	}

	@Test
	public void testTradeWithSpecificPortPlayerDoesntOwn() {
		InputHandler mockedInputHandler = EasyMock.strictMock(InputHandler.class);
		CatanGame mockedCatanGame = EasyMock.strictMock(CatanGame.class);
		Resource resource = Resource.BRICK;
		MaritimeTradeManager maritimeTradeManager = new MaritimeTradeManager(mockedInputHandler, mockedCatanGame);
		maritimeTradeManager.soldResource = resource;
		EasyMock.expect(mockedCatanGame.doesCurrentPlayerOwnSpecialHarbor(resource)).andReturn(false);
		mockedInputHandler.displayMessage("You do not own a Special Harbor for brick");
		EasyMock.replay(mockedInputHandler, mockedCatanGame);

		maritimeTradeManager.specialPortTrade();

		EasyMock.verify(mockedInputHandler, mockedCatanGame);
	}

	@Test
	public void testTradeWithSpecificPort() {
		InputHandler mockedInputHandler = EasyMock.strictMock(InputHandler.class);
		CatanGame mockedCatanGame = EasyMock.strictMock(CatanGame.class);
		Player mockedPlayer = EasyMock.strictMock(Player.class);
		Resource soldResource = Resource.BRICK;
		Resource boughtResource = Resource.GRAIN;
		MaritimeTradeManager maritimeTradeManager = new MaritimeTradeManager(mockedInputHandler, mockedCatanGame);
		maritimeTradeManager.soldResource = soldResource;
		maritimeTradeManager.boughtResource = boughtResource;
		EasyMock.expect(mockedCatanGame.doesCurrentPlayerOwnSpecialHarbor(soldResource)).andReturn(true);
		EasyMock.expect(mockedInputHandler.getCurrentPlayer()).andReturn(mockedPlayer);
		mockedPlayer.removeResource(soldResource, MaritimeTradeManager.SPECIAL_TRADE_COST);
		mockedPlayer.giveResource(boughtResource, 1);
		mockedCatanGame.drawPlayers();

		EasyMock.replay(mockedInputHandler, mockedCatanGame, mockedPlayer);

		maritimeTradeManager.specialPortTrade();

		EasyMock.verify(mockedInputHandler, mockedCatanGame, mockedPlayer);
	}
}
