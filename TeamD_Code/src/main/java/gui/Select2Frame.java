package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.*;

import control.InputHandler;

public class Select2Frame {
    private Integer[] rows, cols;
    private InputHandler handler;
    private boolean isOptional;

    public Select2Frame(Integer[] rowOptions, Integer[] colOptions, boolean isOptional, InputHandler handler) {
        this.rows = rowOptions;
        this.cols = colOptions;
        this.handler = handler;
        this.isOptional = isOptional;
    }

    public void selectAndApply(String selectMessage, Function<Integer[], Void> inputHandlerFunc) {
        JFrame selectionFrame = new JFrame();
        JPanel selectionPanel = new JPanel();
        JPanel endPanel = new JPanel();
        JComboBox<Integer> rowSelector = new JComboBox<Integer>(rows);
        rowSelector.setSelectedIndex(0);
        JComboBox<Integer> colSelector = new JComboBox<Integer>(cols);
        colSelector.setSelectedIndex(0);
        JButton submitButton = new JButton(handler.getMessages().getString("Select2Frame.0"));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer selectedRow = new Integer(rowSelector.getSelectedIndex());
                Integer selectedCol = new Integer(colSelector.getSelectedIndex());

                try {
                    inputHandlerFunc.apply(new Integer[] { selectedRow, selectedCol });
                    selectionFrame.dispose();
                } catch (Exception exception) {
                    handler.handleException(exception, selectedRow + 1, selectedCol + 1);
                }
            }
        });
        JButton quitButton = new JButton(handler.getMessages().getString("Select2Frame.1"));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOptional) {
                    selectionFrame.dispose();
                } else {
                    handler.displayMessage(handler.getMessages().getString("Select2Frame.2"));
                }
            }
        });

        selectionFrame.add(new JLabel(selectMessage), BorderLayout.NORTH);
        selectionPanel.add(new JLabel(handler.getMessages().getString("Select2Frame.3")));
        selectionPanel.add(rowSelector);
        selectionPanel.add(new JLabel(handler.getMessages().getString("Select2Frame.4")));
        selectionPanel.add(colSelector);
        selectionFrame.add(selectionPanel);
        endPanel.add(quitButton);
        endPanel.add(submitButton);
        selectionFrame.add(endPanel, BorderLayout.SOUTH);
        selectionFrame.pack();
        selectionFrame.setVisible(true);
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
