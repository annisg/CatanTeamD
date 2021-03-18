package model;

import exception.*;

public class Robber {

    private Hex currentlyPlacedOn;

    public Robber(Hex startingHex) {
        this.currentlyPlacedOn = startingHex;
        this.currentlyPlacedOn.placeRobber();
    }

    public Hex getHexBeingRobbed() {
        return this.currentlyPlacedOn;
    }

    public void moveRobberTo(Hex destination) {
        if (this.currentlyPlacedOn.equals(destination)) {
            throw new IllegalRobberMoveException();
        }

        this.currentlyPlacedOn.removeRobber();
        destination.placeRobber();
        this.currentlyPlacedOn = destination;
    }
}
