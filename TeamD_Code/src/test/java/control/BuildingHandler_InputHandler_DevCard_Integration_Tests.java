package control;

import model.PieceBuilder;
import model.TurnTracker;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class BuildingHandler_InputHandler_DevCard_Integration_Tests {
	ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));

	@Test
	public void testCannotBuyDevCardBeforeRolling() {
		CatanGame mockedCatanGame = EasyMock.strictMock(CatanGame.class);
		PieceBuilder mockedPieceBuilder = EasyMock.strictMock(PieceBuilder.class);
		EasyMock.expect(mockedCatanGame.getMessages()).andStubReturn(messages);

		InputHandler inputHandler = EasyMock.createMockBuilder(InputHandler.class).addMockedMethod("displayMessage").createMock();
		inputHandler.displayMessage("You must roll before buying a development card.");
		EasyMock.replay(mockedCatanGame, mockedPieceBuilder, inputHandler);
		BuildingHandler buildingHandler = new BuildingHandler(mockedCatanGame, mockedPieceBuilder, inputHandler);
		inputHandler.propertyBuilder = buildingHandler;
		inputHandler.hasNotRolled = true;

		inputHandler.buyDevelopmentCard();

		EasyMock.verify(mockedCatanGame, mockedPieceBuilder, inputHandler);
	}

	@Test
	public void testCannotBuyDevCardWithInsufficientResources() {
		CatanGame mockedCatanGame = EasyMock.strictMock(CatanGame.class);
		PieceBuilder mockedPieceBuilder = EasyMock.strictMock(PieceBuilder.class);
		InputHandler inputHandler = EasyMock.createMockBuilder(InputHandler.class).addMockedMethod("displayMessage").createMock();
		inputHandler.displayMessage("You do not have enough resources to buy a development card.");

		TurnTracker playerTracker = new TurnTracker(new Random(0));
		playerTracker.setupPlayers(3);

		EasyMock.expect(mockedCatanGame.getPlayerTracker()).andReturn(playerTracker);
		EasyMock.expect(mockedPieceBuilder.hasResourcesToBuildDevelopmentCard(playerTracker.getPlayer(0))).andReturn(false);
		EasyMock.expect(mockedCatanGame.getMessages()).andStubReturn(messages);
		EasyMock.replay(mockedCatanGame, inputHandler, mockedPieceBuilder);

		BuildingHandler buildingHandler = new BuildingHandler(mockedCatanGame, mockedPieceBuilder, inputHandler);
		inputHandler.propertyBuilder = buildingHandler;
		inputHandler.hasNotRolled = false;
		inputHandler.buyDevelopmentCard();

		EasyMock.verify(mockedCatanGame, inputHandler, mockedPieceBuilder, inputHandler);
	}

	@Test
	public void testCanBuyDevCard() {
		CatanGame mockedCatanGame = EasyMock.strictMock(CatanGame.class);
		PieceBuilder mockedPieceBuilder = EasyMock.strictMock(PieceBuilder.class);
		InputHandler inputHandler = EasyMock.createMockBuilder(InputHandler.class).addMockedMethod("displayMessage").createMock();
		inputHandler.displayMessage("You have drawn a TEST TYPE card.");

		TurnTracker playerTracker = new TurnTracker(new Random(0));
		playerTracker.setupPlayers(3);

		EasyMock.expect(mockedCatanGame.getPlayerTracker()).andReturn(playerTracker).times(2);
		EasyMock.expect(mockedCatanGame.getMessages()).andStubReturn(messages);
		EasyMock.expect(mockedPieceBuilder.hasResourcesToBuildDevelopmentCard(playerTracker.getPlayer(0))).andReturn(true);
		EasyMock.expect(mockedPieceBuilder.buildDevelopmentCard(playerTracker.getCurrentPlayer())).andReturn("TEST TYPE");
		mockedCatanGame.drawPlayers();
		EasyMock.replay(mockedCatanGame, mockedPieceBuilder, inputHandler);

		BuildingHandler buildingHandler = new BuildingHandler(mockedCatanGame, mockedPieceBuilder, inputHandler);
		inputHandler.propertyBuilder = buildingHandler;
		inputHandler.hasNotRolled = false;
		inputHandler.buyDevelopmentCard();

		EasyMock.verify(mockedCatanGame, mockedPieceBuilder, inputHandler);
	}

	@Test
	public void testBuyDevCardEmptyDeck() {
		CatanGame mockedCatanGame = EasyMock.strictMock(CatanGame.class);
		PieceBuilder mockedPieceBuilder = EasyMock.strictMock(PieceBuilder.class);
		InputHandler inputHandler = EasyMock.createMockBuilder(InputHandler.class).addMockedMethod("displayMessage").createMock();
		inputHandler.displayMessage("The deck is empty, you cannot buy a development card.");

		TurnTracker playerTracker = new TurnTracker(new Random(0));
		playerTracker.setupPlayers(3);

		EasyMock.expect(mockedCatanGame.getPlayerTracker()).andReturn(playerTracker).times(2);
		EasyMock.expect(mockedCatanGame.getMessages()).andStubReturn(messages);
		EasyMock.expect(mockedPieceBuilder.hasResourcesToBuildDevelopmentCard(playerTracker.getPlayer(0))).andReturn(true);
		EasyMock.expect(mockedPieceBuilder.buildDevelopmentCard(playerTracker.getCurrentPlayer())).andThrow(new IllegalStateException());
		EasyMock.replay(mockedCatanGame, mockedPieceBuilder, inputHandler);

		BuildingHandler buildingHandler = new BuildingHandler(mockedCatanGame, mockedPieceBuilder, inputHandler);
		inputHandler.propertyBuilder = buildingHandler;
		inputHandler.hasNotRolled = false;
		inputHandler.buyDevelopmentCard();

		EasyMock.verify(mockedCatanGame, mockedPieceBuilder, inputHandler);
	}
}
