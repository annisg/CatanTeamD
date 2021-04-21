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
import java.util.List;

public class TradeWithSpecificPlayerGUI implements ItemListener, ActionListener {

    static JComboBox typeOfCardToTrade;
    static JTextField numberOfCardsToTrade;
    static JLabel instruction;
    static Player currentPlayer;
    static Player playerToTrade;
    static JLabel playerToTradeInstruction;
    static JComboBox playerMenu;
    static JLabel playerNameLabel;
    static JFrame frame;
    static ArrayList<JLabel> labelsOfStuff = new ArrayList<JLabel>();
    static List<Player> players;
    static JComboBox numOfResourceToTrade;
    public TradeWithSpecificPlayerGUI(Player player, List<Player> playerList) {
        this.currentPlayer = player;
        this.players = playerList;
        startGUI();
    }



    public void startGUI() {
        frame = new JFrame("My Trading GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new GridLayout(10,1));

        JButton tradeButton = new JButton("Trade");
        tradeButton.addActionListener(this);
        playerToTradeInstruction = new JLabel("choose the player you want to trade with according to his name", SwingConstants.CENTER);
        playerNameLabel = new JLabel(this.currentPlayer.getName(), SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Verdana", Font.BOLD, 40));
        String[] array = listOfPlayerResource();
        typeOfCardToTrade = new JComboBox(array);
        numOfResourceToTrade = new JComboBox();
        typeOfCardToTrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String [] quantity = numOfResources((String)typeOfCardToTrade.getSelectedItem());
                numOfResourceToTrade.removeAllItems();
                for(String s: quantity){
                    numOfResourceToTrade.addItem(s);
                }

            }
        });
        numberOfCardsToTrade = new JTextField("Set the text");
        instruction = new JLabel("select the quantity you want to trade", SwingConstants.CENTER);
        instruction.setFont(new Font("Arial", Font.PLAIN, 20));
        playerMenu = new JComboBox(populateComboBox());
        for(JLabel l: labelsOfStuff){
            frame.add(l);
        }
        JLabel resourceInstruction = new JLabel("Choose the resource you want to trade", SwingConstants.CENTER);
        resourceInstruction.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(playerNameLabel);
        frame.add(resourceInstruction);
        frame.add(typeOfCardToTrade);
        frame.add(playerToTradeInstruction);
        frame.add(playerMenu);
        frame.add(instruction);
       // frame.add(numberOfCardsToTrade);
        frame.add(numOfResourceToTrade);
        //frame.add(playerMenu);
        frame.add(tradeButton); // Adds Button to content pane of frame
        frame.setVisible(true);
    }

    public String [] numOfResources(String resource){
       int count = currentPlayer.getResourceCount(getResourceObject(resource));
        String [] array = new String [count +1];
        for(int i =0; i<array.length; i++){
            array[i] = "" + i;
        }
        return array;
    }
    public Resource getResourceObject(String choice){
        if(choice.equals("grain")){
            return Resource.GRAIN;
        }
        else if (choice.equals("ore")){
            return Resource.ORE;
        }
        else if(choice.equals("brick")){
            return Resource.BRICK;
        }
        else if(choice.equals("lumber")){
            return Resource.LUMBER;
        }
        else {
            return Resource.WOOL;
        }
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

            num= Integer.parseInt((String)numOfResourceToTrade.getSelectedItem());

        return num;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //chose the resource to trade
        //pick the player number
        //read the number in the text
        System.out.println("You selected: " + typeOfCardToTrade.getSelectedItem());
        Resource source = currentPlayer.getResourceByName((String) typeOfCardToTrade.getSelectedItem());
        playerToTrade = getPlayerByName((String)playerMenu.getSelectedItem());
        int numToTrade = parseInteger();
        System.out.print("I want to trade this many cards: " + numToTrade);
        //huge try catch just to take care of exceptions
          if(numToTrade==0){
              JOptionPane.showMessageDialog(null, "You don't have any of that resource");
              frame.dispose();
              return;
          }
           System.out.println("Current player  before : " + currentPlayer.getResourceCards().toString());
            ArrayList<Resource> stuff = currentPlayer.giveResourceForTrading(source, numToTrade);
            String message = "You have traded " + ("" + numToTrade) + " of "  + source.toString();
            JOptionPane.showMessageDialog(null, message);
        System.out.println("Current player  after: " + currentPlayer.getResourceCards().toString());
        System.out.println("Player to trade before: " + playerToTrade.getResourceCards().toString());
            playerToTrade.receiveResourceForTrading(stuff);
        System.out.println("Player to trade after : " + playerToTrade.getResourceCards().toString());
            frame.dispose();



    }

    public Player getPlayerByName(String name){
        Player peep=null;
        for(Player p : players){
            if(p.getName().equals(name)){
                peep = p;
            }
        }
        return peep;
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
        String[] playerNames = new String[players.size()-1];
        int index=0;
        for (int i = 0; i < players.size(); i++) {
            if(currentPlayer.getName()!=players.get(i).getName()) {
                playerNames[index] = players.get(i).getName();
                index++;
            }
        }
        return playerNames;
    }
}
