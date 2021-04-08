package gui;

import model.Player;
import model.Resource;
import model.TurnTracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class TradeWithSpecificPlayerGUI implements ItemListener, ActionListener {

    static JComboBox typeOfCardToTrade;
    static JTextField numberOfCardsToTrade;
    static JLabel instruction;
    static Player currentPlayer;
    static Player playerToTrade;
    static JLabel playerToTradeInstruction;
    static JComboBox playerMenu;
    JFrame frame;
    public TradeWithSpecificPlayerGUI(Player player) {
        this.currentPlayer = player;
        startGUI();
    }


    public void startGUI() {
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 300);
        frame.setLayout(new FlowLayout());
        JButton tradeButton = new JButton("Trade");
        playerToTradeInstruction = new JLabel("choose the player you want to trade with according to his id");
        String[] array = {"LUMBER", "BRICK", "ORE", "GRAIN", "WOOL", "DESERT"};
        typeOfCardToTrade = new JComboBox(array);
        numberOfCardsToTrade = new JTextField("Set the text");
        instruction = new JLabel("type the number of resource you want to trade");
        playerMenu = new JComboBox(populateComboBox());
        frame.add(typeOfCardToTrade);
        frame.add(instruction);
        frame.add(numberOfCardsToTrade);
        frame.add(playerToTradeInstruction);
        frame.add(playerMenu);
        frame.add(tradeButton); // Adds Button to content pane of frame
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //chose the resource to trade
        //pick the player number
        //read the number in the text
        Resource source = currentPlayer.getResourceByName((String) typeOfCardToTrade.getSelectedItem());
        playerToTrade = currentPlayer.getTracker().getPlayer((int) playerMenu.getSelectedItem());
        int numToTrade = Integer.parseInt(numberOfCardsToTrade.getText());
        ArrayList<Resource> stuff = currentPlayer.giveResourceForTrading(source, numToTrade);
        playerToTrade.receiveResourceForTrading(stuff);
        frame.dispose();
        //need to do stuff the other way around
       // currentPlayer = playerToTrade;
        TradeWithSpecificPlayerGUI swap = new TradeWithSpecificPlayerGUI(playerToTrade);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    static public String[] populateComboBox() {
        String[] nums = new String[2];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = "" + (i);
        }
        return nums;
    }
}
