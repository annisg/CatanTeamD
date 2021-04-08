package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.JarURLConnection;
import java.util.ResourceBundle;

import javax.swing.*;

import control.*;

public class InputComponent extends JPanel {
    private InputHandler handler;

    public InputComponent(InputHandler handler, ResourceBundle messages) {
        this.handler = handler;

        JButton rollDiceButton = new JButton(messages.getString("InputComponent.0"));
        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.tryToRollDice();
            }
        });

        JButton buildRoad = new JButton(messages.getString("InputComponent.1"));
        buildRoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.placeRoad();
            }
        });

        JButton buildSettlement = new JButton(messages.getString("InputComponent.2"));
        buildSettlement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.placeSettlement();
            }
        });

        JButton buildCity = new JButton(messages.getString("InputComponent.3"));
        buildCity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.placeCity();
            }
        });

        JButton buyDevCard = new JButton(messages.getString("InputComponent.4"));
        buyDevCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.buyDevelopmentCard();
            }
        });

        JButton useDevCard = new JButton(messages.getString("InputComponent.5"));
        useDevCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.useDevCard();
            }
        });

        JButton endTurn = new JButton(messages.getString("InputComponent.6"));
        endTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.endTurn();
            }
        });

        JButton tradeWithPlayer = new JButton("Trade");
        tradeWithPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        this.add(tradeWithPlayer);
        this.add(new JLabel(messages.getString("InputComponent.7")));
        this.add(rollDiceButton);
        this.add(new JLabel(messages.getString("InputComponent.8")));
        this.add(buildRoad);
        this.add(buildSettlement);
        this.add(buildCity);
        this.add(new JLabel(messages.getString("InputComponent.9")));
        this.add(buyDevCard);
        this.add(useDevCard);
        this.add(new JLabel(messages.getString("InputComponent.10")));
        this.add(endTurn);
    }

    public void selectInitialRoadPlacement() {
        handler.placeInitialRoad();
    }

    public void selectInitialPlaceSettlement() {
        handler.placeInitialSettlement();
    }

    public void selectInitialSettlementPlacementRound2() {
        handler.placeInitialSettlementRound2();
    }

}
