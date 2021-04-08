package control;

import exception.*;

import javax.swing.*;
import java.text.MessageFormat;

public class ExceptionHandler {

    public InputHandler input;
    public ExceptionHandler(InputHandler handler){
        this.input = handler;
    }
    public  void displayMessage(String message) {
        // Removing the following line is not tested for because it is only visually
        // impactful and based on a static method.
        JOptionPane.showMessageDialog(null, message);
    }

    public void handleException(Exception e, int row, int col) {
        if (e instanceof InvalidHexPositionException) {
            displayMessage(MessageFormat.format(input.catanGame.getMessages().getString("InputHandler.21"), row, col));

        } else if (e instanceof IllegalRobberMoveException) {
            displayMessage(MessageFormat.format(input.catanGame.getMessages().getString("InputHandler.22"), row, col));

        } else if (e instanceof InvalidEdgePositionException) {
            displayMessage(MessageFormat.format(input.catanGame.getMessages().getString("InputHandler.23"), row, col));

        } else if (e instanceof InvalidIntersectionPositionException) {
            displayMessage(MessageFormat.format(input.catanGame.getMessages().getString("InputHandler.24"), row, col));

        } else if (e instanceof PlaceBuildingException) {
            displayMessage(e.getMessage());

        } else if (e instanceof ItemNotFoundException) {
            displayMessage(input.catanGame.getMessages().getString("InputHandler.25"));

        } else {
            displayMessage(
                    MessageFormat.format(input.catanGame.getMessages().getString("InputHandler.26"), e.getMessage()));
        }
    }
}