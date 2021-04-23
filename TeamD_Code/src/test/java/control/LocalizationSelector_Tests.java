package control;

import org.easymock.EasyMock;
import org.junit.Test;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class LocalizationSelector_Tests {
    @Test
    public void testLocalizationSelection() {
        JFrame mockedSelectorFrame = EasyMock.niceMock(JFrame.class);
        JComboBox<String> mockedLangSelector = EasyMock.niceMock(JComboBox.class);
        JButton selectButton = new JButton();

        ResourceBundle mockedRB = ResourceBundle.getBundle("messages", new Locale("en"));

        Function<Object, Void> onSelect = selected -> {
            assertEquals(mockedRB, selected);
            return null;
        };

        LocalizationSelector uut = EasyMock.partialMockBuilder(LocalizationSelector.class)
                .withConstructor(JFrame.class, JComboBox.class, JButton.class)
                .withArgs(mockedSelectorFrame, mockedLangSelector, selectButton)
                .addMockedMethod("getLocaleNames")
                .mock();

        EasyMock.expect(uut.getLocaleNames()).andReturn(new String[] {"en", "zh"});
        EasyMock.expect(mockedLangSelector.getSelectedItem()).andReturn("en");

        EasyMock.replay(mockedSelectorFrame, mockedLangSelector);

        uut.promptForLocalizationBundle(onSelect);
        selectButton.getActionListeners()[0].actionPerformed(null);

        EasyMock.verify(mockedSelectorFrame, mockedLangSelector);
    }
}
