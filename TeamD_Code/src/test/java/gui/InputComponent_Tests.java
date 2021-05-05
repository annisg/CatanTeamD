package gui;

import control.InputHandler;
import control.MaritimeTradeManager;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputComponent_Tests {

	ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en"));
	@Test
	public void testRollDice() {
		InputHandler mockedInputHandler = EasyMock.strictMock(InputHandler.class);
		MaritimeTradeManager mockedMaritimeTradeManager = EasyMock.strictMock(MaritimeTradeManager.class);
		InputComponent inputComponent = new InputComponent(mockedInputHandler, messages, mockedMaritimeTradeManager);
		JButton rollDiceButton = new JButton();
		JButton buyDevCard = new JButton();
		JButton useDevCard = new JButton();
		JButton endTurn = new JButton();
		mockedInputHandler.tryToRollDice();

		EasyMock.replay(mockedInputHandler, mockedMaritimeTradeManager);
		inputComponent.rollDiceClicked(mockedInputHandler, rollDiceButton, buyDevCard, useDevCard, endTurn);
		assertFalse(rollDiceButton.isEnabled());
		assertTrue(buyDevCard.isEnabled());
		assertTrue(useDevCard.isEnabled());
		assertTrue(endTurn.isEnabled());

		EasyMock.verify(mockedInputHandler, mockedMaritimeTradeManager);
	}

	@Test
	public void testEndTurn() {
		InputHandler mockedInputHandler = EasyMock.strictMock(InputHandler.class);
		MaritimeTradeManager mockedMaritimeTradeManager = EasyMock.strictMock(MaritimeTradeManager.class);
		InputComponent inputComponent = new InputComponent(mockedInputHandler, messages, mockedMaritimeTradeManager);
		JButton rollDiceButton = new JButton();
		JButton buyDevCard = new JButton();
		JButton useDevCard = new JButton();
		JButton endTurn = new JButton();
		mockedInputHandler.endTurn();

		EasyMock.replay(mockedInputHandler,  mockedMaritimeTradeManager);
		inputComponent.endTurnClicked(mockedInputHandler, rollDiceButton, buyDevCard, useDevCard, endTurn);
		assertTrue(rollDiceButton.isEnabled());
		assertFalse(buyDevCard.isEnabled());
		assertFalse(useDevCard.isEnabled());
		assertFalse(endTurn.isEnabled());

		EasyMock.verify(mockedInputHandler, mockedMaritimeTradeManager);
	}

}
