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

        ButtonGroup difficultySelector = new ButtonGroup();
        JRadioButton beginnerMode = new JRadioButton(Messages.getString("GameOptionSelector.1"));
        beginnerMode.setSelected(true);
        beginnerMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedState = GameStartState.BEGINNER;
            }
        });
        JRadioButton advancedMode = new JRadioButton(Messages.getString("GameOptionSelector.2"));
        advancedMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedState = GameStartState.ADVANCED;
            }
        });
        JRadioButton customMode = new JRadioButton(Messages.getString("GameOptionSelector.8"));
        customMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedState = GameStartState.CUSTOM;
            }
        });
        difficultySelector.add(beginnerMode);
        difficultySelector.add(advancedMode);
        difficultySelector.add(customMode);

        JPanel difficultyPanel = new JPanel();
        difficultyPanel.add(new JLabel(Messages.getString("GameOptionSelector.3")));
        difficultyPanel.add(beginnerMode);
        difficultyPanel.add(advancedMode);
        difficultyPanel.add(customMode);

        ButtonGroup numPlayersSelector = new ButtonGroup();
        JRadioButton threePlayers = new JRadioButton(Messages.getString("GameOptionSelector.4"));
        threePlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numPlayers = 3;
            }
        });
        JRadioButton fourPlayers = new JRadioButton(Messages.getString("GameOptionSelector.5"));
        fourPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numPlayers = 4;
            }
        });
        threePlayers.setSelected(true);
        numPlayersSelector.add(threePlayers);
        numPlayersSelector.add(fourPlayers);

        JPanel playersPanel = new JPanel();
        playersPanel.add(new JLabel(Messages.getString("GameOptionSelector.6")));
        playersPanel.add(threePlayers);
        playersPanel.add(fourPlayers);

        JButton startButton = new JButton(Messages.getString("GameOptionSelector.7"));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                catanController.makeBoard(selectedState, numPlayers);
                startFrame.dispose();
            }
        });

        startFrame.add(difficultyPanel, BorderLayout.NORTH);
        startFrame.add(playersPanel, BorderLayout.CENTER);
        startFrame.add(startButton, BorderLayout.SOUTH);

        startFrame.pack();
        startFrame.setVisible(true);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
