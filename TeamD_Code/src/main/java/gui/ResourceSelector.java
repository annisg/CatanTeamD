package gui;

import control.InputHandler;

public class ResourceSelector extends Select1Frame {
    public ResourceSelector(boolean isOptional, InputHandler handler) {
        super(handler.possibleResourceNames, handler.possibleResources, isOptional, handler);
    }
}
