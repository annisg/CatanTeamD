package control;

import gui.PortSelector;
import gui.ResourceSelector;
import model.Player;
import model.Resource;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class MaritimeTradeManager_Tests {
    InputHandler handlerM;
    CatanGame gameM;

    ResourceSelector sellingSelector;
    ResourceSelector buyingSelector;
    PortSelector portSelector;

    @BeforeEach
    public void setUp() {
        handlerM = EasyMock.niceMock(InputHandler.class);
        gameM = EasyMock.strictMock(CatanGame.class);

        sellingSelector = EasyMock.strictMock(ResourceSelector.class);
        buyingSelector = EasyMock.strictMock(ResourceSelector.class);
        portSelector = EasyMock.strictMock(PortSelector.class);
    }

    @Test
    public void testExitsIfNotRolled() {
        MaritimeTradeManager mtm = EasyMock.partialMockBuilder(MaritimeTradeManager.class)
                .withConstructor(InputHandler.class, CatanGame.class)
                .withArgs(handlerM, gameM)
                .addMockedMethod("sellResource", Resource.class)
                .mock();

        handlerM.hasNotRolled = true;
        handlerM.displayMessage("You must roll before trading.");
        EasyMock.replay(handlerM, mtm);

        mtm.trade();

        EasyMock.verify(handlerM, mtm);
    }

    @Test
    public void testCallsSellResourceIfRolled() {
        MaritimeTradeManager mtm = EasyMock.partialMockBuilder(MaritimeTradeManager.class)
                .withConstructor(InputHandler.class, CatanGame.class)
                .withArgs(handlerM, gameM)
                .addMockedMethod("sellResource", Resource.class)
                .mock();

        addMockedSelectors(mtm);

        sellingSelector.selectAndApply("Select the resource you would like to sell.", mtm.sellResource);

        EasyMock.replay(handlerM, mtm);

        mtm.trade();

        EasyMock.verify(handlerM, mtm);
    }

    @Test
    public void testSellResource() {
        MaritimeTradeManager mtm = EasyMock.partialMockBuilder(MaritimeTradeManager.class)
                .withConstructor(InputHandler.class, CatanGame.class)
                .withArgs(handlerM, gameM)
                .addMockedMethod("buyResource", Resource.class)
                .mock();

        addMockedSelectors(mtm);

        buyingSelector.selectAndApply("Select the resource you would like to buy.", mtm.buyResource);

        EasyMock.replay(handlerM, mtm, sellingSelector, buyingSelector);

        mtm.sellResource(Resource.BRICK);

        assertEquals(Resource.BRICK, mtm.soldResource);
        EasyMock.verify(handlerM, mtm, sellingSelector, buyingSelector);
    }

    @Test
    public void testBuyResource() {
        MaritimeTradeManager mtm = EasyMock.partialMockBuilder(MaritimeTradeManager.class)
                .withConstructor(InputHandler.class, CatanGame.class)
                .withArgs(handlerM, gameM)
                .addMockedMethod("tradeViaPort", PortSelector.PortTypes.class)
                .mock();

        addMockedSelectors(mtm);

        portSelector.selectAndApply("Select the port you would like to trade with.", mtm.tradeViaPort);

        EasyMock.replay(handlerM, mtm, sellingSelector, buyingSelector);

        mtm.buyResource(Resource.GRAIN);

        assertEquals(Resource.GRAIN, mtm.boughtResource);
        EasyMock.verify(handlerM, mtm, sellingSelector, buyingSelector);
    }

    @Test
    public void testTradeWithDefaultPort() {
        MaritimeTradeManager mtm = EasyMock.partialMockBuilder(MaritimeTradeManager.class)
                .withConstructor(InputHandler.class, CatanGame.class)
                .withArgs(handlerM, gameM)
                .addMockedMethod("exchangeResourcesWithBank", Integer.TYPE)
                .mock();

        mtm.exchangeResourcesWithBank(4);

        EasyMock.replay(handlerM, mtm, sellingSelector, buyingSelector);

        mtm.tradeViaPort(PortSelector.PortTypes.DEFAULT);

        EasyMock.verify(handlerM, mtm, sellingSelector, buyingSelector);
    }

    @Test
    public void testTradeWithGenericPort() {
        MaritimeTradeManager mtm = EasyMock.partialMockBuilder(MaritimeTradeManager.class)
                .withConstructor(InputHandler.class, CatanGame.class)
                .withArgs(handlerM, gameM)
                .addMockedMethod("exchangeResourcesWithBank", Integer.TYPE)
                .addMockedMethod("playerOwnsGenericHarbor")
                .mock();

        mtm.exchangeResourcesWithBank(3);
        EasyMock.expect(mtm.playerOwnsGenericHarbor()).andReturn(true);

        EasyMock.replay(handlerM, mtm, sellingSelector, buyingSelector);

        mtm.tradeViaPort(PortSelector.PortTypes.GENERIC);

        EasyMock.verify(handlerM, mtm, sellingSelector, buyingSelector);
    }

    @Test
    public void testTradeWithSpecialPort() {
        MaritimeTradeManager mtm = EasyMock.partialMockBuilder(MaritimeTradeManager.class)
                .withConstructor(InputHandler.class, CatanGame.class)
                .withArgs(handlerM, gameM)
                .addMockedMethod("exchangeResourcesWithBank", Integer.TYPE)
                .addMockedMethod("playerOwnsSpecialHarbor", Resource.class)
                .mock();

        mtm.soldResource = Resource.ORE;
        mtm.exchangeResourcesWithBank(2);
        EasyMock.expect(mtm.playerOwnsSpecialHarbor(Resource.ORE)).andReturn(true);

        EasyMock.replay(handlerM, mtm, sellingSelector, buyingSelector);

        mtm.tradeViaPort(PortSelector.PortTypes.SPECIAL);

        EasyMock.verify(handlerM, mtm, sellingSelector, buyingSelector);
    }

    @Test
    public void testTradeWithSpecialPortWhileUnowned() {
        MaritimeTradeManager mtm = EasyMock.partialMockBuilder(MaritimeTradeManager.class)
                .withConstructor(InputHandler.class, CatanGame.class)
                .withArgs(handlerM, gameM)
                .addMockedMethod("exchangeResourcesWithBank", Integer.TYPE)
                .addMockedMethod("playerOwnsSpecialHarbor", Resource.class)
                .mock();

        mtm.soldResource = Resource.ORE;
        EasyMock.expect(mtm.playerOwnsSpecialHarbor(Resource.ORE)).andReturn(false);
        handlerM.displayMessage("You do not own a Special Harbor for ore");

        EasyMock.replay(handlerM, mtm, sellingSelector, buyingSelector);

        mtm.tradeViaPort(PortSelector.PortTypes.SPECIAL);

        EasyMock.verify(handlerM, mtm, sellingSelector, buyingSelector);
    }

    @Test
    public void testExchangeResourcesWithBank() {
        MaritimeTradeManager mtm = EasyMock.partialMockBuilder(MaritimeTradeManager.class)
                .withConstructor(InputHandler.class, CatanGame.class)
                .withArgs(handlerM, gameM)
                .mock();
        Player playerM = EasyMock.strictMock(Player.class);
        EasyMock.expect(handlerM.getCurrentPlayer()).andReturn(playerM);

        mtm.soldResource = Resource.ORE;
        mtm.boughtResource = Resource.GRAIN;

        playerM.removeResource(Resource.ORE, 4);

        EasyMock.replay(handlerM, mtm, sellingSelector, buyingSelector);

        mtm.exchangeResourcesWithBank(4);

        EasyMock.verify(handlerM, mtm, sellingSelector, buyingSelector);
    }

    private void addMockedSelectors(MaritimeTradeManager mtm) {
        mtm.buyingResourceSelector = buyingSelector;
        mtm.sellingResourceSelector = sellingSelector;
        mtm.portSelector = portSelector;
    }

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
	public void testTradeWithGenericPort2() {
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
