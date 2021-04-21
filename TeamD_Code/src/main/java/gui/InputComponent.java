package gui;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.util.function.Function;

import javax.swing.*;

import exception.PlaceBuildingException;
import static java.lang.Thread.sleep;

import control.*;

public class InputComponent extends JPanel {
    private InputHandler handler;

    private Queue<Function<Point, Void>> clickFunctionQueue = new LinkedList<>();
    
    private JButton cheatResources;

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
                clickFunctionQueue.add(handler.placeRoad);
            }
        });

        JButton buildSettlement = new JButton(messages.getString("InputComponent.2"));
        buildSettlement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickFunctionQueue.add(handler.placeSettlement);
            }
        });

        JButton buildCity = new JButton(messages.getString("InputComponent.3"));
        buildCity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickFunctionQueue.add(handler.placeCity);
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
                handler.selectAndUseDevCard();
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
                handler.tradeWithPlayer();
            }
        });
        
        cheatResources = new JButton("Cheat Resources");
        cheatResources.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: CHEAT
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
        this.add(cheatResources);
        cheatResources.setVisible(false);
    }

    public void addMouseListenerToParent() {
        this.getParent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Function<Point, Void> function = clickFunctionQueue.peek();
                if(function != null) {
                    try {
                        function.apply(mouseEvent.getPoint());
                        clickFunctionQueue.poll();
                    } catch (PlaceBuildingException exception) {
                    
                    } catch (Exception exception) {
                        clickFunctionQueue.poll();
                    }
                }
            }
        });
    }

    public void selectInitialRoadPlacement() {
        clickFunctionQueue.add(handler.placeInitialRoad);
    }

    public void selectInitialPlaceSettlement() {
        clickFunctionQueue.add(handler.placeInitialSettlement);
    }

    public void selectInitialSettlementPlacementRound2() {
        clickFunctionQueue.add(handler.placeInitialSettlementRound2);
    }

    public void placeRoadWithCard() {
        clickFunctionQueue.add(handler.placeRoadWithCard);
    }

    public void setDebugStatus(boolean isDebug) {
        this.cheatResources.setVisible(isDebug);
    }
}
