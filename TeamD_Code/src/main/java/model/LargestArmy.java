package model;

public class LargestArmy {

    private TurnTracker turnTracker;
    private PlayerColor holder;
    private int size;

    public LargestArmy(TurnTracker players) {
        this.turnTracker = players;
        this.holder = PlayerColor.NONE;
        this.size = 0;
    }

    public LargestArmy(TurnTracker players, PlayerColor holder, int size) {
        this.turnTracker = players;
        this.holder = holder;
        this.size = size;
    }

    public PlayerColor getHolder() {
        return holder;
    }

    public int getSize() {
        return size;
    }

    public void updateLargestArmy() {

        for (int i = 0; i < turnTracker.getNumPlayers(); i++) {

            Player currentPlayer = turnTracker.getPlayer(i);
            int currentSize = currentPlayer.getNumKnights();

            if (currentSize >= 3 && currentSize > size) {
                this.size = currentSize;
                this.holder = currentPlayer.getColor();
            }
        }
    }

}
