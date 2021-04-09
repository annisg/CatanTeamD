package control;

import exception.*;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ExceptionHandler {

    private InputHandler input;
    private ResourceBundle messages;
    public ExceptionHandler(InputHandler handler, ResourceBundle message) {
        this.input = handler;
        this.messages = message;
    }


    public void handleException(Exception e, int row, int col) {
        if (e instanceof InvalidHexPositionException) {
            input.displayMessage(MessageFormat.format(messages.getString("InputHandler.21"), row, col));

        } else if (e instanceof IllegalRobberMoveException) {
            input.displayMessage(MessageFormat.format(messages.getString("InputHandler.22"), row, col));

        } else if (e instanceof InvalidEdgePositionException) {
            input.displayMessage(MessageFormat.format(messages.getString("InputHandler.23"), row, col));

        } else if (e instanceof InvalidIntersectionPositionException) {
            input.displayMessage(MessageFormat.format(messages.getString("InputHandler.24"), row, col));

        } else if (e instanceof PlaceBuildingException) {
            input.displayMessage(e.getMessage());

        } else if (e instanceof ItemNotFoundException) {
            input.displayMessage(messages.getString("InputHandler.25"));

        } else {
            input.displayMessage(
                    MessageFormat.format(messages.getString("InputHandler.26"), e.getMessage()));
        }
    }
}