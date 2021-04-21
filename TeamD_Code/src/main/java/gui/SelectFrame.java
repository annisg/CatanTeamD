package gui;

import control.InputHandler;

import javax.swing.*;
import java.awt.*;

public abstract class SelectFrame {

    private final boolean isOptional;
    InputHandler handler;
    JFrame selectionFrame;
    JPanel selectionPanel;
    private JPanel endPanel;

    public SelectFrame(InputHandler handler, boolean isOptional) {
        this.handler = handler;
        this.isOptional = isOptional;
    }

    void resetComponents() {
        selectionFrame = new JFrame();
        selectionPanel = new JPanel();
        endPanel = new JPanel();
    }

    void formatDialogBox(JButton submitButton) {
        JButton cancelButton = new JButton(handler.getMessages().getString("SelectFrame.1"));
        cancelButton.addActionListener(e -> {
            if (isOptional) {
                selectionFrame.dispose();
            } else {
                handler.displayMessage(handler.getMessages().getString("SelectFrame.2"));
            }
        });

        selectionFrame.add(selectionPanel);
        endPanel.add(cancelButton);
        endPanel.add(submitButton);
        selectionFrame.add(endPanel, BorderLayout.SOUTH);
        selectionFrame.pack();
        selectionFrame.setVisible(true);
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
