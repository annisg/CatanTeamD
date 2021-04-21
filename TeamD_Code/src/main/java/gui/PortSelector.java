package gui;

import control.InputHandler;

public class PortSelector extends Select1Frame {
    public enum PortTypes {
        DEFAULT("Default Harbor (4:1)"),
        GENERIC("Basic Harbor (3:1)"),
        SPECIAL("Special Harbor (2:1)");

        private final String title;

        PortTypes(String title) {
            this.title = title;
        }

        static PortTypes[] getTypes() {
            return PortTypes.values();
        }

        static String[] getTitleStrings() {
            PortTypes[] types = PortTypes.getTypes();
            String[] titles = new String[types.length];

            for (int i = 0; i < types.length; i++) {
                titles[i] = types[i].title;
            }

            return titles;
        }
    }

    public PortSelector(InputHandler handler) {
        super(PortTypes.getTitleStrings(), PortTypes.getTypes(), true, handler);
    }
}
