package gui;

import model.Player;
import model.PlayerColor;
import model.Resource;

import java.awt.event.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
public class RemoveCardsGUI extends JFrame implements ItemListener, ActionListener {
    static JTextField tf;
    Player player;
    static  int numberOfResourceCards;
    static  JComboBox c2;
    int sizeOfDeck;
    // frame
    static JFrame f;

    // label
    static JLabel l, l1;

    // combobox
    static JComboBox c1;
    int numberTimeDiscard = 0;
    JLabel totalCards;
    // textfield to add and delete items
    ArrayList<JLabel> labelsOfStuff = new ArrayList<JLabel>();
    // main class

    public RemoveCardsGUI(Player p){
        this.player = p;
         this.sizeOfDeck = p.getResourceHandSize();
        beginning();
    }



    public void beginning()
    {
        // create a new frame
        f = new JFrame("Remove your resource cards");

        // create a object

        System.out.println("console");
        // set layout of frame
        f.setLayout(new FlowLayout());

        Player player = new Player(PlayerColor.WHITE);
        Map<Resource, Integer> resources = player.getResourceCards();
        numberOfResourceCards= player.getResourceHandSize();
        // array of string contating cities
        ArrayList<String> listOfResource = new ArrayList<String>();
        ArrayList<String> numsOfResource = new ArrayList<String>();
        for(Map.Entry<Resource, Integer> entry: resources.entrySet()){
            listOfResource.add(entry.getKey().name());
            numsOfResource.add(entry.getValue().toString());
        }


        String []entries  = new String[listOfResource.size()];
        for(int i =0; i< listOfResource.size(); i++){
            entries[i] = listOfResource.get(i);
        }
        String [] values  = new String[listOfResource.size()];
        for(int i =0; i<numsOfResource.size(); i++){
            values[i]=numsOfResource.get(i);
        }


        // create checkbox
        c1 = new JComboBox(entries);
       // JComboBox c2 = new JComboBox(values);
        // create textfield
      //  tf = new JTextField(16);

        // create add and remove buttons
       //JButton b = new JButton("ADD");
        JButton b1 = new JButton("REMOVE");

        // add action listener
       // b.addActionListener(s);
       //
         b1.addActionListener(this);

        // add ItemListener
        c1.addItemListener(this);


        // create labels
        totalCards = new JLabel("You have " + "" + player.getResourceHandSize() + " cards" );
        l = new JLabel("Remove your card ");
        l1 = new JLabel("Blank selected");

        //institate and show what stuff are there:
        HashMap<Resource, Integer> resourceMap = (HashMap<Resource, Integer>) player.getResourceCards();
        for (Map.Entry<Resource,Integer> entry : resourceMap.entrySet()){
            labelsOfStuff.add(new JLabel(entry.getKey().name() + " with " + "" + entry.getValue() + "remaining"));
        }

        // set color of text
        l.setForeground(Color.red);
        l1.setForeground(Color.blue);

        // create a new panel
        JPanel p = new JPanel();
       // c2 = new JComboBox();
        for(JLabel l: labelsOfStuff){
            p.add(l);
        }
        p.add(l);
        p.add(totalCards);
        // add combobox to panel
        p.add(c1);
       // p.add(c2);
        p.add(l1);
       // p.add(tf);
        //p.add(b);
        p.add(b1);

        f.setLayout(new FlowLayout());

        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setSize(700, 200);

        f.show();
    }
    // if button is pressed
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("I am in resource");
        if(numberTimeDiscard <sizeOfDeck/2) {
            Resource r = player.getResourceByName((String) c1.getSelectedItem());
            player.discardResourceCard(r);
            numberTimeDiscard++;
            totalCards.setText("you have this many cards " + "" + player.getResourceHandSize() );
            updateJLabels();
        }
        else{
            this.dispose();
        }
        tf.setText("" + numberTimeDiscard);

    }

    public void updateJLabels(){
        HashMap<Resource, Integer> resourceMap = (HashMap<Resource, Integer>) player.getResourceCards();
        int index = 0;
        for (Map.Entry<Resource,Integer> entry : resourceMap.entrySet()){
            labelsOfStuff.get(index).setText(entry.getKey().name() + " with " + "" + entry.getValue() + " remaining");
           // labelsOfStuff.add(new JLabel(entry.getKey().name() + " with " + "" + entry.getValue() + "remaining"));
        }

    }

    public void itemStateChanged(ItemEvent e)
    {
        // if the state combobox is changed
        System.out.println("I am in item state change");
        if (e.getSource() == c1) {

            l1.setText(c1.getSelectedItem() + " selected");
            int count = player.getResourceCountString((String)c1.getSelectedItem());
            if(count==0){
                String [] num = {"0"};
                c2 = new JComboBox(num);
            }
            else {
                String[] numToRemove = new String[count + 1];
                for (int i = 0; i < numToRemove.length; i++) {
                    numToRemove[i] = "" + i;
                }

                c2 = new JComboBox(numToRemove);
            }
        }

    }
}