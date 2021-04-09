package model;

public enum Resource {
    GRAIN, BRICK, ORE, LUMBER, WOOL, DESERT;

    @Override
    public String toString() {
        switch(this) {
            case DESERT:
                return "none";
            default:
                return this.name().toLowerCase();
        }
    }
}
