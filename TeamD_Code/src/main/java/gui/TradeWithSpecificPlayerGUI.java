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
import java.util.*;

public class TradeWithSpecificPlayerGUI implements ItemListener, ActionListener {

    static JComboBox typeOfCardToTrade;
    static JTextField numberOfCardsToTrade;
    static JLabel instruction;
    static Player currentPlayer;
    static Player playerToTrade;
    static JLabel playerToTradeInstruction;
    static JComboBox playerMenu;
    JFrame frame;
    ArrayList<JLabel> labelsOfStuff = new ArrayList<JLabel>();
    public TradeWithSpecificPlayerGUI(Player player) {
        this.currentPlayer = player;
        startGUI();
    }



    public void startGUI() {
        frame = new JFrame("My Trading GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 300);
        frame.setLayout(new FlowLayout());
        JButton tradeButton = new JButton("Trade");
        tradeButton.addActionListener(this);
        playerToTradeInstruction = new JLabel("choose the player you want to trade with according to his id");
        String[] array = listOfPlayerResource();
        typeOfCardToTrade = new JComboBox(array);
        numberOfCardsToTrade = new JTextField("Set the text");
        instruction = new JLabel("type the number of resource you want to trade");
        playerMenu = new JComboBox(populateComboBox());
        for(JLabel l: labelsOfStuff){
            frame.add(l);
        }
        frame.add(typeOfCardToTrade);
        frame.add(instruction);
        frame.add(numberOfCardsToTrade);
        frame.add(playerToTradeInstruction);
        frame.add(playerMenu);
        frame.add(tradeButton); // Adds Button to content pane of frame
        frame.setVisible(true);
    }

    public String [] listOfPlayerResource(){
        Set<Resource> set = currentPlayer.getResourceTypes();
        Iterator<Resource> itr = set.iterator();
        String [] array2 = new String[set.size()];
        int index =0;
        for(Resource r: set){
            array2[index] = r.toString();
            System.out.println("Resources: " + r);
            index++;
        }
        return array2;

    }

    public int parseInteger(){
        int num=0;
        try{
            num= Integer.parseInt(numberOfCardsToTrade.getText());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Enter a number please!");

        }
        return num;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //chose the resource to trade
        //pick the player number
        //read the number in the text
        Resource source = currentPlayer.getResourceByName((String) typeOfCardToTrade.getSelectedItem());
        playerToTrade = currentPlayer.getTracker().getPlayer((Integer.parseInt((String) playerMenu.getSelectedItem())));
        int numToTrade = parseInteger();

        try {
            ArrayList<Resource> stuff = currentPlayer.giveResourceForTrading(source, numToTrade);
            String message = "You have traded " + ("" + numToTrade) + " of "  + source.toString();
            JOptionPane.showMessageDialog(null, message);

            playerToTrade.receiveResourceForTrading(stuff);
            frame.dispose();
        }
        catch(Exception e2){
            frame.dispose();
            // currentPlayer = playerToTrade;
            // TradeWithSpecificPlayerGUI swap = new TradeWithSpecificPlayerGUI(playerToTrade);
            // startGUI();

            JOptionPane.showMessageDialog(null, "You don't have enough resources to traide");



        }
        //need to do stuff the other way around


    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    public void updateJLabels(){
        HashMap<Resource, Integer> resourceMap = (HashMap<Resource, Integer>) currentPlayer.getResourceCards();
        int index = 0;
        for (Map.Entry<Resource,Integer> entry : resourceMap.entrySet()){
            labelsOfStuff.get(index).setText(entry.getKey().name() + " with " + "" + entry.getValue() + " remaining");
            // labelsOfStuff.add(new JLabel(entry.getKey().name() + " with " + "" + entry.getValue() + "remaining"));
        }

    }

    static public String[] populateComboBox() {
        String[] nums = new String[currentPlayer.getPlayersInGame()];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = "" + (i + 1);
        }
        return nums;
    }
}
