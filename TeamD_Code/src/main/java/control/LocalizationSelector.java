package control;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;

public class LocalizationSelector {
    final JFrame frame;
    final JComboBox<String> langSelector;
    final JButton selectButton;

    public LocalizationSelector() {
        frame = new JFrame();
        langSelector = new JComboBox<>(getLocaleNames());
        selectButton = new JButton("     ");
    }

    public LocalizationSelector(JFrame frame, JComboBox<String> langSelector, JButton selectButton) {
        this.frame = frame;
        this.langSelector = langSelector;
        this.selectButton = selectButton;
    }

    void promptForLocalizationBundle(Function<Object, Void> onSelect) {
        selectButton.addActionListener(event -> {
            ResourceBundle messages = getBundle((String) langSelector.getSelectedItem());
            frame.dispose();
            onSelect.apply(messages);
        });

        frame.add(langSelector);
        frame.add(selectButton, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    String[] getLocaleNames() {
        String localeString = Messages.getString("LocalizationSelector.0");

        return localeString.split(",");
    }

    ResourceBundle getBundle(String locale) {
        return ResourceBundle.getBundle("messages", new Locale(locale));
    }
}
