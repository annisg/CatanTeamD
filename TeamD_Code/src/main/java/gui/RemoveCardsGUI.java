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
class RemoveCardsGUI extends JFrame implements ItemListener, ActionListener {

    // frame
    static JFrame f;

    // label
    static JLabel l, l1;

    // combobox
    static JComboBox c1;

    // textfield to add and delete items
    static JTextField tf;

    // main class
    public static void main(String[] args)
    {
        // create a new frame
        f = new JFrame("frame");

        // create a object
       RemoveCardsGUI s = new RemoveCardsGUI();

        // set layout of frame
        f.setLayout(new FlowLayout());

        Player player = new Player(PlayerColor.WHITE);
        Map<Resource, Integer> resources = player.getResourceCards();
        // array of string contating cities
        ArrayList<String> listOfResource = new ArrayList<String>();
        ArrayList<String> numsOfResource = new ArrayList<String>();
        for(Map.Entry<Resource, Integer> entry: resources.entrySet()){
            listOfResource.add(entry.getKey().name());
            numsOfResource.add(entry.getValue().toString());
        }

        String s1[] = { "Jalpaiguri", "Mumbai", "Noida", "Kolkata", "New Delhi" };
        String []entries = (String[])listOfResource.toArray();
        String [] values = (String []) numsOfResource.toArray();

        // create checkbox
        c1 = new JComboBox(s1);
        JComboBox c2 = new JComboBox(values);
        // create textfield
        tf = new JTextField(16);

        // create add and remove buttons
       JButton b = new JButton("ADD");
        JButton b1 = new JButton("REMOVE");

        // add action listener
        b.addActionListener(s);
        b1.addActionListener(s);

        // add ItemListener
        c1.addItemListener(s);

        // create labels
        l = new JLabel("Remove your card ");
        l1 = new JLabel("Jalpaiguri selected");

        // set color of text
        l.setForeground(Color.red);
        l1.setForeground(Color.blue);

        // create a new panel
        JPanel p = new JPanel();

        p.add(l);

        // add combobox to panel
        p.add(c1);
        p.add(c2);
        p.add(l1);
        p.add(tf);
        p.add(b);
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
        String s = e.getActionCommand();
        if (s.equals("ADD")) {
            c1.addItem(tf.getText());
        }
        else {
            c1.removeItem(tf.getText());
        }
    }

    public void itemStateChanged(ItemEvent e)
    {
        // if the state combobox is changed
        if (e.getSource() == c1) {

            l1.setText(c1.getSelectedItem() + " selected");
        }
    }
}