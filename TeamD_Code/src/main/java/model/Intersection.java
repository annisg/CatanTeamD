package model;

public class Intersection {
    private boolean hasSettlement = false;
    private boolean hasCity = false;
    private PlayerColor buildingColor = PlayerColor.NONE;
    private Port port;

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public boolean hasSettlement() {
        return hasSettlement;
    }

    public boolean hasCity() {
        return hasCity;
    }

    public boolean isEmpty() {
        return getBuildingColor() == PlayerColor.NONE;
    }

    public PlayerColor getBuildingColor() {
        return buildingColor;
    }

    public void setSettlement(PlayerColor color) {
        hasSettlement = color != PlayerColor.NONE;
        hasCity = false;
        buildingColor = color;
    }

    public void setCity(PlayerColor color) {
        hasSettlement = false;
        hasCity = color != PlayerColor.NONE;
        buildingColor = color;
    }

    public boolean hasPort() {
        return port != null;
    }
}
