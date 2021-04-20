package gui;

import model.Player;
import model.PlayerColor;
import model.Resource;

import java.awt.event.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import javax.swing.*;

public class RemoveCardsGUI extends JFrame implements ItemListener, ActionListener {
    JTextField tf;
    Player player;
    int numberOfResourceCards;
    JComboBox c2;
    int sizeOfDeck;
    // frame
    JLabel chooseNumberInstruction;
    JFrame frame;
    JButton discardButton;
    JComboBox typeOfCardToDiscard;
    // label
    JLabel l, l1;
    JLabel numberOfResourcesLabel;
    JComboBox numOfResourceToDiscard;
    JLabel playerResourcesLabel;
    // combobox
    JComboBox c1;
    int numberTimeDiscard = 0;
    JLabel totalCards;
    // textfield to add and delete items
    ArrayList<JLabel> labelsOfStuff = new ArrayList<JLabel>();
    JButton quitButton;
    // main class

    public RemoveCardsGUI(Player p) {
        this.player = p;
        this.sizeOfDeck = p.getResourceHandSize();
        beginning();
    }


    public String[] listOfPlayerResource() {
        Set<Resource> set = this.player.getResourceTypes();
        Iterator<Resource> itr = set.iterator();
        String[] array2 = new String[set.size()];
        int index = 0;
        for (Resource r : set) {
            array2[index] = r.toString();
            System.out.println("Resources: " + r);
            index++;
        }
        return array2;

    }

    public Resource getResourceObject(String choice) {
        if (choice.equals("grain")) {
            return Resource.GRAIN;
        } else if (choice.equals("ore")) {
            return Resource.ORE;
        } else if (choice.equals("brick")) {
            return Resource.BRICK;
        } else if (choice.equals("lumber")) {
            return Resource.LUMBER;
        } else {
            return Resource.WOOL;
        }
    }

    public String[] numOfResources(String resource) {
        int count = player.getResourceCount(getResourceObject(resource));
        String[] array = new String[count + 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = "" + i;
        }
        return array;
    }

    public void beginning() {
        // create a new frame
        frame = new JFrame("Remove your resource cards");

        // create a object

        System.out.println("console");
        // set layout of frame
        frame = new JFrame("My Trading GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new GridLayout(10, 1));

        //set the player name on the center
        JLabel playerNameLabel = new JLabel(this.player.getName(), SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Verdana", Font.BOLD, 40));

        //display the list of cards a player has
        playerResourcesLabel = new JLabel(this.player.getResourceCards().toString(), SwingConstants.CENTER);
        playerResourcesLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        //create a menu box for the number of resources a player has
        numOfResourceToDiscard = new JComboBox();

        //display the number of the resources a player has
        numberOfResourcesLabel = new JLabel("You have  " + ("" + this.player.getResourceHandSize()) + " cards", SwingConstants.CENTER);
        numberOfResourcesLabel.setFont(new Font("Serif", Font.BOLD, 30));

        //create combobox for showing which resource to discard and populating it
        String[] array = listOfPlayerResource();
        typeOfCardToDiscard = new JComboBox(array);
        typeOfCardToDiscard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] quantity = numOfResources((String) typeOfCardToDiscard.getSelectedItem());
                numOfResourceToDiscard.removeAllItems();
                for (String s : quantity) {
                    numOfResourceToDiscard.addItem(s);
                }
            }
        });

        JLabel selectResourceToDiscardInstruction = new JLabel("Select the resource below you want to discard", SwingConstants.CENTER);
        selectResourceToDiscardInstruction.setFont(new Font("SansSerif", Font.PLAIN, 20));
        discardButton = new JButton("REMOVE");

        chooseNumberInstruction = new JLabel("Select the number of resource below you want to discard", SwingConstants.CENTER);
        chooseNumberInstruction.setFont(new Font("SansSerif", Font.PLAIN, 20));
        discardButton.addActionListener(this);
        quitButton = new JButton("CANCEL PAYMENT");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.add(playerNameLabel);
        frame.add(playerResourcesLabel);
        frame.add(numberOfResourcesLabel);
        frame.add(selectResourceToDiscardInstruction);
        frame.add(typeOfCardToDiscard);
        frame.add(chooseNumberInstruction);
        frame.add(numOfResourceToDiscard);
        frame.add(discardButton);
        frame.add(quitButton);
        frame.setVisible(true);
        System.out.println("I am in the beginning");
    }

    // if button is pressed
    public void actionPerformed(ActionEvent e) {
        System.out.println("I am in remove");
        String particularResourceToDiscardAmt = (String) numOfResourceToDiscard.getSelectedItem();
        int particularAmt = Integer.parseInt(particularResourceToDiscardAmt);
        numberTimeDiscard = numberTimeDiscard + particularAmt;

        System.out.println("Player hand before: " + player.getResourceCards().toString());
        //if the discard amount is too much leave
        if (numberTimeDiscard > player.getResourceHandSize() / 2) {
            JOptionPane.showMessageDialog(null, "You cannot discard anymore cards");
            frame.dispose();
            return;
        }

        //handle the discard
        player.removeResource(player.getResourceByName((String) typeOfCardToDiscard.getSelectedItem()), particularAmt);
        playerResourcesLabel.setText(player.getResourceCards().toString());
        numberOfResourcesLabel.setText("You have: " + player.getResourceHandSize() + " cards");

        //repopulate the menu items updated
        numOfResourceToDiscard.removeAllItems();
        String[] quantity = numOfResources((String) typeOfCardToDiscard.getSelectedItem());
        for (int i = 0; i < quantity.length; i++) {
            numOfResourceToDiscard.addItem(quantity[i]);
        }


    }

    public void updateJLabels() {
        HashMap<Resource, Integer> resourceMap = (HashMap<Resource, Integer>) player.getResourceCards();
        int index = 0;
        for (Map.Entry<Resource, Integer> entry : resourceMap.entrySet()) {
            labelsOfStuff.get(index).setText(entry.getKey().name() + " with " + "" + entry.getValue() + " remaining");
            // labelsOfStuff.add(new JLabel(entry.getKey().name() + " with " + "" + entry.getValue() + "remaining"));
        }


        //change
    }

    public void itemStateChanged(ItemEvent e) {

    }


}

