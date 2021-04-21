package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import control.*;

public class GameOptionSelector {
    private GameStartState selectedState = GameStartState.BEGINNER;
    private GameMode selectedGameMode = GameMode.NORMAL;
    private int numPlayers = 3;
    private boolean isDebug = false;

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

        ButtonGroup gamemodeSelector = new ButtonGroup();
        JPanel gamemodePanel = new JPanel();
        gamemodePanel.add(new JLabel(Messages.getString("GameOptionSelector.12")));

        JRadioButton normalMode = createGameModeRadioButton(Messages.getString("GameOptionSelector.13"), GameMode.NORMAL, gamemodeSelector, gamemodePanel);
        JRadioButton fogMode = createGameModeRadioButton(Messages.getString("GameOptionSelector.14"), GameMode.FOG, gamemodeSelector, gamemodePanel);
        normalMode.setSelected(true);
        
        ButtonGroup debugSelector = new ButtonGroup();
        JPanel debugPanel = new JPanel();
        debugPanel.add(new JLabel(Messages.getString("GameOptionSelector.11")));

        JRadioButton debugDisabled = createDebugRadioButton(Messages.getString("GameOptionSelector.9"), false, debugSelector, debugPanel);
        JRadioButton debugEnabled = createDebugRadioButton(Messages.getString("GameOptionSelector.10"), true, debugSelector, debugPanel);
        debugDisabled.setSelected(true);
        
        JButton startButton = new JButton(Messages.getString("GameOptionSelector.7"));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                startFrame.dispose();
                catanController.makeBoard(selectedState, numPlayers, isDebug);
            }
        });
        
        JPanel optionsPanel =  new JPanel(new GridLayout(4,1));
        optionsPanel.add(difficultyPanel);
        optionsPanel.add(playersPanel);
        optionsPanel.add(gamemodePanel);
        optionsPanel.add(debugPanel);
        
        startFrame.add(optionsPanel, BorderLayout.CENTER);
        startFrame.add(startButton, BorderLayout.SOUTH);

        startFrame.pack();
        startFrame.setVisible(true);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private JRadioButton createPlayerRadioButton(String message, int newNumPlayers, ButtonGroup numPlayersSelector, JPanel playersPanel) {
        JRadioButton button = createLinkedRadioButton(message, numPlayersSelector, playersPanel, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numPlayers = newNumPlayers;
            }
        });
        return button;
    }

    private JRadioButton createStateRadioButton(String message, GameStartState state, ButtonGroup startStateSelector, JPanel difficultyPanel) {
        JRadioButton button = createLinkedRadioButton(message, startStateSelector, difficultyPanel,
            new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedState = state;
            }
        });
        return button;
    }
    
    private JRadioButton createGameModeRadioButton(String message, GameMode state, ButtonGroup gamemodeSelector, JPanel gamemodePanel) {
        JRadioButton button = createLinkedRadioButton(message, gamemodeSelector, gamemodePanel, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedGameMode = state;
            }
        });
        return button;
    }

    private JRadioButton createDebugRadioButton(String message, boolean state, ButtonGroup debugSelector, JPanel debugPanel) {
        JRadioButton button = createLinkedRadioButton(message, debugSelector, debugPanel, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDebug = state;
            }
        });
        return button;
    }
    
    private JRadioButton createLinkedRadioButton(String message, ButtonGroup group, JPanel panel, ActionListener listener) {
        JRadioButton button = new JRadioButton(message);
        group.add(button);
        panel.add(button);
        button.addActionListener(listener);
        return button;
    }
}
