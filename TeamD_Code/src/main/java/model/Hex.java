package model;

import exception.*;

public class Hex {
    private Resource providesResource;
    private int rollResourceNumber;
    boolean hasRobber = false;

    public Hex(Resource providesResource, int rollResourceNumber) {
        this.providesResource = providesResource;
        if (this.providesResource == Resource.DESERT) {
            throw new IllegalArgumentException("Use the single arg hex constructor for the desert.");
        }

        this.rollResourceNumber = rollResourceNumber;
        if (this.rollResourceNumber < 2 || this.rollResourceNumber > 12 || this.rollResourceNumber == 7) {
            throw new IllegalArgumentException();
        }
    }

    public Hex() {
        this.providesResource = Resource.DESERT;
        this.rollResourceNumber = 0;
        this.hasRobber = true;
    }

    public void placeRobber() {
        this.hasRobber = true;
    }

    public void removeRobber() {
        this.hasRobber = false;
    }

    public Resource getResource() {
        return this.providesResource;
    }

    public int getRollResourceNumber() {
        return this.rollResourceNumber;
    }

    public boolean hasRobber() {
        return this.hasRobber;
    }

}
