package control;

import java.util.ResourceBundle;
import java.util.function.Function;

public class Main {
    static final Function<Object, Void> useResourceBundle = selected -> {
        runGame((ResourceBundle) selected);
        return null;
    };
    static LocalizationSelector localizationSelector = new LocalizationSelector();

    public static void main(String[] args) {
        localizationSelector.promptForLocalizationBundle(useResourceBundle);
    }

    static void runGame(ResourceBundle message) {
        CatanGame catan = new CatanGame(message);
        catan.startGame();
    }
}
