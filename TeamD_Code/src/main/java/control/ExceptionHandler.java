package control;

import exception.*;

import javax.swing.*;
import java.text.MessageFormat;

public class ExceptionHandler {

    public InputHandler input;
    public ExceptionHandler(InputHandler handler){
        this.input = handler;
    }


    public void handleException(Exception e, int row, int col) {
        if (e instanceof InvalidHexPositionException) {
            input.displayMessage(MessageFormat.format(input.catanGame.getMessages().getString("InputHandler.21"), row, col));

        } else if (e instanceof IllegalRobberMoveException) {
            input.displayMessage(MessageFormat.format(input.catanGame.getMessages().getString("InputHandler.22"), row, col));

        } else if (e instanceof InvalidEdgePositionException) {
            input.displayMessage(MessageFormat.format(input.catanGame.getMessages().getString("InputHandler.23"), row, col));

        } else if (e instanceof InvalidIntersectionPositionException) {
            input.displayMessage(MessageFormat.format(input.catanGame.getMessages().getString("InputHandler.24"), row, col));

        } else if (e instanceof PlaceBuildingException) {
            input.displayMessage(e.getMessage());

        } else if (e instanceof ItemNotFoundException) {
            input.displayMessage(input.catanGame.getMessages().getString("InputHandler.25"));

        } else {
            input.displayMessage(
                    MessageFormat.format(input.catanGame.getMessages().getString("InputHandler.26"), e.getMessage()));
        }
    }
}