package control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        getResourceBundle();
    }

    private static void getResourceBundle() {
        JFrame selectorFrame = new JFrame();
        JComboBox<String> langSelector = new JComboBox<String>(getLocales());
        langSelector.setSelectedIndex(0);
        JButton select = new JButton("     ");
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ResourceBundle messages = ResourceBundle.getBundle("messages",
                        new Locale((String) langSelector.getSelectedItem()));
                selectorFrame.dispose();
                runGame(messages);
            }
        });

        selectorFrame.add(langSelector);
        selectorFrame.add(select, BorderLayout.SOUTH);
        selectorFrame.pack();
        selectorFrame.setVisible(true);
        selectorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void runGame(ResourceBundle message) {
        CatanGame catan = new CatanGame(message);
        catan.startGame();
    }

    private static String[] getLocales() {
        String localString = Messages.getString("Main.0");
        ArrayList<String> languageStrings = new ArrayList<String>();

        int start = 0;
        for (int i = 0; i < localString.length(); i++) {
            if (localString.codePointAt(i) == ',') {
                languageStrings.add(localString.substring(start, i));
                start = i + 1;
            }
        }
        languageStrings.add(localString.substring(start));

        String[] locales = new String[languageStrings.size()];
        for (int i = 0; i < languageStrings.size(); i++) {
            locales[i] = languageStrings.get(i);
        }

        return locales;
    }

}
