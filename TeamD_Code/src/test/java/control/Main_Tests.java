package control;

import org.easymock.EasyMock;
import org.junit.Test;

public class Main_Tests {
    @Test
    public void testMain() {
        LocalizationSelector lsMock = EasyMock.strictMock(LocalizationSelector.class);
        Main.localizationSelector = lsMock;
        lsMock.promptForLocalizationBundle(Main.useResourceBundle);

        EasyMock.replay(lsMock);

        Main.main(new String[0]);

        EasyMock.verify(lsMock);
    }
}
