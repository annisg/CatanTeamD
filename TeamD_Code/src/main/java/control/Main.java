package control;

import java.util.ResourceBundle;
import java.util.function.Function;

public class Main {
    static final Function<Object, Void> useResourceBundle = selected -> {
        runGame((ResourceBundle) selected);
        return null;
    };
    static LocalizationSelector localizationSelector = null;

    public static void main(String[] args) {
        if (localizationSelector == null) {
            localizationSelector = new LocalizationSelector();
        }

        localizationSelector.promptForLocalizationBundle(useResourceBundle);
    }

    static void runGame(ResourceBundle message) {
        CatanGame catan = new CatanGame(message);
        catan.startGame();
    }
}
