package gui;

import control.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class Select1Frame extends SelectFrame {
    private final String[] visibleOptions;
    private final Object[] actualOptions;

    public Select1Frame(String[] optionNames, Object[] objectOptions, boolean isOptional, InputHandler handler) {
        super(handler, isOptional);
        this.visibleOptions = optionNames;
        this.actualOptions = objectOptions;
    }

    public void selectAndApply(String selectMessage, Function<Object, Void> inputHandlerFunc) {
        resetComponents();

        JComboBox<String> objectSelector = new JComboBox<>(this.visibleOptions);
        objectSelector.setSelectedIndex(0);
        JButton submitButton = new JButton(handler.getMessages().getString("Select1Frame.0"));
        submitButton.addActionListener(e -> {
            int selectedIndex = objectSelector.getSelectedIndex();
            Object selectedObject = actualOptions[selectedIndex];

            try {
                inputHandlerFunc.apply(selectedObject);
                selectionFrame.dispose();
            } catch (Exception exception) {
                handler.handleException(exception, 0, 0);
            }
        });

        selectionFrame.add(new JLabel(selectMessage), BorderLayout.NORTH);
        selectionPanel.add(objectSelector);

        formatDialogBox(submitButton);
    }
}
