package gui;

import control.InputHandler;
import model.Resource;

public class ResourceSelector extends Select1Frame {

    private static final String[] POSSIBLE_RESOURCE_NAMES = new String[]{"Brick", "Grain", "Lumber", "Ore", "Wool"};
    private static final Object[] POSSIBLE_RESOURCES = {Resource.BRICK, Resource.GRAIN, Resource.LUMBER, Resource.ORE,
            Resource.WOOL};

    public ResourceSelector(boolean isOptional, InputHandler handler) {
        super(POSSIBLE_RESOURCE_NAMES, POSSIBLE_RESOURCES, isOptional, handler);
    }
}
