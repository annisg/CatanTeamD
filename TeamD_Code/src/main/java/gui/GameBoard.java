package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;
import model.*;

@SuppressWarnings("serial")
public class GameBoard extends JComponent {

    ArrayList<Drawable> hexesAndNumbersToDraw;
    ArrayList<Drawable> propertyToDraw;
    ArrayList<Drawable> playersToDraw;
    ArrayList<Drawable> otherPlayersToDraw;
    ArrayList<Drawable> specialCardsToDraw;
    JTextPane popup;

    public GameBoard() {
        this.hexesAndNumbersToDraw = new ArrayList<Drawable>();
        this.propertyToDraw = new ArrayList<Drawable>();
        this.playersToDraw = new ArrayList<Drawable>();
        this.otherPlayersToDraw = new ArrayList<Drawable>();
        this.specialCardsToDraw = new ArrayList<Drawable>();
        
        popup = new JTextPane();
        popup.setText("Hand computer to next player. Press OK when ready to continue");
        popup.setPreferredSize(new Dimension(1550, 900));
    }

    public void addHexesAndHexNums(ArrayList<Drawable> hexesAndNums) {
        hexesAndNumbersToDraw = hexesAndNums;
    }

    public void addPlayerViews(ArrayList<Drawable> players) {
        this.playersToDraw = players;
        this.drawObjects(this.getGraphics(), this.playersToDraw);
    }
    
    public void addOtherPlayerViews(ArrayList<Drawable> otherPlayers) {
        this.otherPlayersToDraw = otherPlayers;
        this.drawObjects(this.getGraphics(), this.otherPlayersToDraw);
    }

    public void addSpecialCards(ArrayList<Drawable> cards) {
        this.specialCardsToDraw = cards;
        this.drawObjects(this.getGraphics(), this.specialCardsToDraw);
    }

    public void fullResetMap() {
        this.hexesAndNumbersToDraw.clear();
        this.propertyToDraw.clear();
    }

    public void drawFullMap() {
        this.drawObjects(this.getGraphics(), this.hexesAndNumbersToDraw);
        this.drawObjects(this.getGraphics(), this.propertyToDraw);
    }

    public void drawProperty() {
        this.drawObjects(this.getGraphics(), this.propertyToDraw);
    }

    public void drawIntersections(IntersectionMap intMap) {
        int x = 0;
        int y = 0;

        for (int i = 0; i < intMap.getNumberOfRows(); i++) {
            for (int j = 0; j < intMap.getNumberOfIntersectionsInRow(i); j++) {
                x = j * 150;
                y = -i * 64 + 890;
                if ((i == 0 || i == 11) && j <= 2) {
                    x += 600;
                } else if ((i == 1 || i == 2 || i == 9 || i == 10) && j <= 3) {
                    x += 525;
                } else if ((i == 3 || i == 4 || i == 7 || i == 8) && j <= 4) {
                    x += 450;
                } else if ((i == 5 || i == 6) && j <= 5) {
                    x += 375;
                } else {
                    continue;
                }

                MapPosition pos = new MapPosition(i, j);
                if (intMap.getIntersection(pos).hasSettlement()) {
                    this.propertyToDraw
                            .add(new SettlementGUI(intMap.getIntersection(pos).getBuildingColor(), x, y, i % 2 != 0));
                } else if (intMap.getIntersection(pos).hasCity()) {
                    if (i % 2 != 0) {
                        y += 10;
                    } else {
                        y -= 5;
                    }

                    this.propertyToDraw
                            .add(new CityGUI(intMap.getIntersection(pos).getBuildingColor(), x, y, i % 2 != 0));
                }
            }
        }

    }

    public void drawEdges(EdgeMap edgeMap) {
        int x = 0;
        int y = 0;
        EdgeDirection direction;

        for (int i = 0; i < edgeMap.getNumberOfRows(); i++) {
            for (int j = 0; j < edgeMap.getNumberOfEdgesInRow(i); j++) {
                if (i % 2 != 0) {
                    direction = EdgeDirection.UP;
                } else {
                    if ((j % 2 == 0 && i < 5) || (j % 2 != 0 && i > 5)) {
                        direction = EdgeDirection.LEFT;
                    } else {
                        direction = EdgeDirection.RIGHT;
                    }
                }

                x = j * 75;
                if (direction == EdgeDirection.UP) {
                    x *= 2;
                }

                if (i == 0 || i == 10) {
                    x += 565;
                } else if (i == 1 || i == 9) {
                    x += 525;
                } else if (i == 2 || i == 8) {
                    x += 485;
                } else if (i == 3 || i == 7) {
                    x += 450;
                } else if (i == 4 || i == 6) {
                    x += 410;
                } else if (i == 5) {
                    x += 375;
                } else {
                    continue;
                }

                y = -i * 65 + 865;

                MapPosition pos = new MapPosition(i, j);
                if (edgeMap.getEdge(pos).hasRoad()) {
                    this.propertyToDraw.add(new EdgeGUI(edgeMap.getEdge(pos).getRoadColor(), x, y, direction));
                }
            }
        }
    }

    protected void paintComponent(Graphics g) {
        drawObjects(g, this.hexesAndNumbersToDraw);
        drawObjects(g, this.playersToDraw);
        drawObjects(g, this.otherPlayersToDraw);
        drawObjects(g, this.propertyToDraw);
        drawObjects(g, this.specialCardsToDraw);
    }

    private void drawObjects(Graphics g, ArrayList<Drawable> objects) {
        for (Drawable h : objects) {
            h.drawComponent(g);
        }
    }

    public void showPopup() {
       JOptionPane.showMessageDialog(this, popup, "Title", JOptionPane.INFORMATION_MESSAGE);
    }
}
