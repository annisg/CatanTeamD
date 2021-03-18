package model;

public class Edge {

    private PlayerColor color = PlayerColor.NONE;

    public boolean hasRoad() {
        return color != PlayerColor.NONE;
    }

    public void setRoad(PlayerColor color) {
        this.color = color;
    }

    public PlayerColor getRoadColor() {
        return this.color;
    }

}
