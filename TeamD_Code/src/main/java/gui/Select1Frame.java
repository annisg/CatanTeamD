package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.*;

import control.InputHandler;

public class Select1Frame {
    private String[] visibleOptions;
    private Object[] actualOptions;
    private InputHandler handler;
    private boolean isOptional;

    public Select1Frame(String[] optionNames, Object[] objectOptions, boolean isOptional, InputHandler handler) {
        this.visibleOptions = optionNames;
        this.actualOptions = objectOptions;
        this.handler = handler;
        this.isOptional = isOptional;
    }

    public void selectAndApply(String selectMessage, Function<Object, Void> inputHandlerFunc) {
        JFrame selectionFrame = new JFrame();
        JPanel selectionPanel = new JPanel();
        JPanel endPanel = new JPanel();
        JComboBox<String> objectSelector = new JComboBox<String>(this.visibleOptions);
        objectSelector.setSelectedIndex(0);
        JButton submitButton = new JButton(handler.getMessages().getString("Select1Frame.0"));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = objectSelector.getSelectedIndex();
                Object selectedObject = actualOptions[selectedIndex];

                try {
                    inputHandlerFunc.apply(selectedObject);
                    selectionFrame.dispose();
                } catch (Exception exception) {
                    handler.handleException(exception, 0, 0);
                }
            }
        });
        JButton quitButton = new JButton(handler.getMessages().getString("Select1Frame.1"));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOptional) {
                    selectionFrame.dispose();
                } else {
                    handler.displayMessage(handler.getMessages().getString("Select1Frame.2"));
                }
            }
        });

        selectionFrame.add(new JLabel(selectMessage), BorderLayout.NORTH);
        selectionPanel.add(objectSelector);
        selectionFrame.add(selectionPanel);
        endPanel.add(quitButton);
        endPanel.add(submitButton);
        selectionFrame.add(endPanel, BorderLayout.SOUTH);
        selectionFrame.pack();
        selectionFrame.setVisible(true);
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
