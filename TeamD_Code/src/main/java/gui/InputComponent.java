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
    private final MaritimeTradeManager maritimeTradeManager;

    
    private JButton cheatResources;

    private class ClickFunctionAndDisplayString {
        private Function<Point, Void> function;
        private String descriptorString;

        ClickFunctionAndDisplayString(Function<Point, Void> function, String descriptorString) {
            this.function = function;
            this.descriptorString = descriptorString;
        }

        public String getDescriptorString() {
            return descriptorString;
        }

        public Function<Point, Void> getFunction() {
            return function;
        }
    }

    private JLabel nextClickActionLabel = new JLabel("");

    private Queue<ClickFunctionAndDisplayString> clickFunctionQueue = new LinkedList<>();

    public InputComponent(InputHandler handler, ResourceBundle messages, MaritimeTradeManager maritimeTradeManager) {
        this.handler = handler;
        this.maritimeTradeManager = maritimeTradeManager;

        JButton buildRoad = new JButton(messages.getString("InputComponent.1"));
        buildRoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToQueueAndUpdateNextClickActionText(new ClickFunctionAndDisplayString(handler.placeRoad, "place road."));
            }
        });

        JButton buildSettlement = new JButton(messages.getString("InputComponent.2"));
        buildSettlement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToQueueAndUpdateNextClickActionText(new ClickFunctionAndDisplayString(handler.placeSettlement, "place settlement."));
            }
        });

        JButton buildCity = new JButton(messages.getString("InputComponent.3"));
        buildCity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToQueueAndUpdateNextClickActionText(new ClickFunctionAndDisplayString(handler.placeCity, "place city."));
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

        JButton tradeWithPlayer = new JButton("Domestic Trading");
        tradeWithPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.tradeWithPlayer();
            }
        });
        JButton rollDiceButton = new JButton(messages.getString("InputComponent.0"));
        JButton endTurn = new JButton(messages.getString("InputComponent.6"));
        endTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endTurnClicked(handler, rollDiceButton, useDevCard, buyDevCard, endTurn);
            }
        });

        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDiceClicked(handler, rollDiceButton, buyDevCard, useDevCard, endTurn);
            }
        });
        
        cheatResources = new JButton("Cheat Resources");
        cheatResources.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.cheatResources();
            }
        });

        JButton tradeWithPort = new JButton("Maritime Trading");
        tradeWithPort.addActionListener(e -> maritimeTradeManager.trade());


        buyDevCard.setEnabled(false);
        useDevCard.setEnabled(false);
        endTurn.setEnabled(false);
        
        nextClickActionLabel.setForeground(Color.RED);
        this.add(nextClickActionLabel);
        this.add(tradeWithPlayer);
        this.add(tradeWithPort);
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

    static void rollDiceClicked(InputHandler handler, JButton rollDiceButton, JButton buyDevCard, JButton useDevCard, JButton endTurn) {
        handler.tryToRollDice();
        rollDiceButton.setEnabled(false);
        buyDevCard.setEnabled(true);
        useDevCard.setEnabled(true);
        endTurn.setEnabled(true);
    }

    void endTurnClicked(InputHandler handler, JButton rollDiceButton, JButton useDevCard, JButton buyDevCard, JButton endTurn) {
        if(clickFunctionQueue.isEmpty()) {
            handler.endTurn();
            rollDiceButton.setEnabled(true);
            useDevCard.setEnabled(false);
            buyDevCard.setEnabled(false);
            endTurn.setEnabled(false);
        }
    }

    public void addMouseListenerToParent() {
        this.getParent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(!clickFunctionQueue.isEmpty()) {
                    try {
                        Function<Point, Void> function = clickFunctionQueue.peek().getFunction();
                        function.apply(mouseEvent.getPoint());
                        clickFunctionQueue.poll();
                    } catch (PlaceBuildingException exception) {
                    
                    } catch (Exception exception) {
                        clickFunctionQueue.poll();
                    }
                    updateNextActionLabel();
                }
            }
        });
    }

    public void selectInitialRoadPlacement() {
        addToQueueAndUpdateNextClickActionText(new ClickFunctionAndDisplayString(handler.placeInitialRoad, "place road."));
    }

    public void selectInitialPlaceSettlement() {
        addToQueueAndUpdateNextClickActionText(new ClickFunctionAndDisplayString(handler.placeInitialSettlement, "place settlement."));
    }

    public void selectInitialSettlementPlacementRound2() {
        addToQueueAndUpdateNextClickActionText(new ClickFunctionAndDisplayString(handler.placeInitialSettlementRound2, "place settlement."));
    }

    public void placeRoadWithCard() {
        addToQueueAndUpdateNextClickActionText(new ClickFunctionAndDisplayString(handler.placeRoadWithCard, "place road."));
    }

    private void addToQueueAndUpdateNextClickActionText(ClickFunctionAndDisplayString clickFunctionAndDisplayString) {
        clickFunctionQueue.add(clickFunctionAndDisplayString);
        updateNextActionLabel();
    }

    private void updateNextActionLabel() {
        if(clickFunctionQueue.isEmpty()) {
            nextClickActionLabel.setText("");
        } else {
            nextClickActionLabel.setText("Click to " + clickFunctionQueue.peek().getDescriptorString());
        }
    }

    public void addMoveRobberToQueue() {
        addToQueueAndUpdateNextClickActionText(new ClickFunctionAndDisplayString(handler.moveRobberTo, "place robber."));
    }

    public void setDebugStatus(boolean isDebug) {
        this.cheatResources.setVisible(isDebug);
    }
}
