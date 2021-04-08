package gui;

import control.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class Select2Frame extends SelectFrame {
    private final Integer[] rows;
    private final Integer[] cols;

    public Select2Frame(Integer[] rowOptions, Integer[] colOptions, boolean isOptional, InputHandler handler) {
        super(handler, isOptional);
        this.rows = rowOptions;
        this.cols = colOptions;
    }

    public void selectAndApply(String selectMessage, Function<Integer[], Void> inputHandlerFunc) {
        resetComponents();

        JComboBox<Integer> rowSelector = new JComboBox<>(rows);
        JComboBox<Integer> colSelector = new JComboBox<>(cols);
        rowSelector.setSelectedIndex(0);
        colSelector.setSelectedIndex(0);

        JButton submitButton = new JButton(handler.getMessages().getString("Select2Frame.0"));
        submitButton.addActionListener(e -> {
            int selectedRow = rowSelector.getSelectedIndex();
            int selectedCol = colSelector.getSelectedIndex();

            try {
                inputHandlerFunc.apply(new Integer[]{selectedRow, selectedCol});
                selectionFrame.dispose();
            } catch (Exception exception) {
                handler.handleException(exception, selectedRow + 1, selectedCol + 1);
            }
        });

        selectionFrame.add(new JLabel(selectMessage), BorderLayout.NORTH);
        selectionPanel.add(new JLabel(handler.getMessages().getString("Select2Frame.3")));
        selectionPanel.add(rowSelector);
        selectionPanel.add(new JLabel(handler.getMessages().getString("Select2Frame.4")));
        selectionPanel.add(colSelector);

        formatDialogBox(submitButton);
    }
}
