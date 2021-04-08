package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import control.*;

public class GameOptionSelector {
    private GameStartState selectedState = GameStartState.BEGINNER;
    private int numPlayers = 3;

    public void getOptionsFromUser(CatanGame catanController) {
        JFrame startFrame = new JFrame(Messages.getString("GameOptionSelector.0"));

        ButtonGroup startStateSelector = new ButtonGroup();
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.add(new JLabel(Messages.getString("GameOptionSelector.3")));
        
        JRadioButton beginnerMode = createStateRadioButton(Messages.getString("GameOptionSelector.1"), GameStartState.BEGINNER,
                startStateSelector, difficultyPanel);
        JRadioButton advancedMode = createStateRadioButton(Messages.getString("GameOptionSelector.2"), GameStartState.ADVANCED,
                startStateSelector, difficultyPanel);
        JRadioButton customMode = createStateRadioButton(Messages.getString("GameOptionSelector.8"), GameStartState.CUSTOM,
                startStateSelector, difficultyPanel);
        beginnerMode.setSelected(true);

        ButtonGroup numPlayersSelector = new ButtonGroup();
        JPanel playersPanel = new JPanel();
        playersPanel.add(new JLabel(Messages.getString("GameOptionSelector.6")));
        
        JRadioButton threePlayers = createPlayerRadioButton(Messages.getString("GameOptionSelector.4"), 3, numPlayersSelector, playersPanel);
        JRadioButton fourPlayers = createPlayerRadioButton(Messages.getString("GameOptionSelector.5"), 4, numPlayersSelector, playersPanel);
        threePlayers.setSelected(true);

        JButton startButton = new JButton(Messages.getString("GameOptionSelector.7"));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                startFrame.dispose();
                catanController.makeBoard(selectedState, numPlayers);
                catanController.initialPlacement(selectedState);
            }
        });

        startFrame.add(difficultyPanel, BorderLayout.NORTH);
        startFrame.add(playersPanel, BorderLayout.CENTER);
        startFrame.add(startButton, BorderLayout.SOUTH);

        startFrame.pack();
        startFrame.setVisible(true);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private JRadioButton createPlayerRadioButton(String message, int newNumPlayers, ButtonGroup numPlayersSelector, JPanel playersPanel) {
        JRadioButton button = new JRadioButton(message);
        numPlayersSelector.add(button);
        playersPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numPlayers = newNumPlayers;
            }
        });
        return button;
    }

    private JRadioButton createStateRadioButton(String message, GameStartState state, ButtonGroup startStateSelector, JPanel difficultyPanel) {
        JRadioButton button = new JRadioButton(message);
        startStateSelector.add(button);
        difficultyPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedState = GameStartState.BEGINNER;
            }
        });
        
        return button;
    }
}
