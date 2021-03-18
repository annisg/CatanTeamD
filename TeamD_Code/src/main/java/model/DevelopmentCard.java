package model;

public abstract class DevelopmentCard {

    private boolean canBePlayed = false;

    abstract public String getName();

    abstract public void use(Player owner);

    public boolean canBePlayed() {
        return this.canBePlayed;
    }

    public void makePlayable() {
        this.canBePlayed = true;
    }
}
