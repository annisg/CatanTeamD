package model;

public class VictoryPointCalculator {

    private LongestRoad longestRoad;
    private LargestArmy largestArmy;

    public VictoryPointCalculator(LongestRoad longestRoad, LargestArmy largestArmy) {
        this.longestRoad = longestRoad;
        this.largestArmy = largestArmy;
    }

    public int calculateForPlayer(Player player) {
        final int beginSettlementCount = player.getInitialSettlementCount();
        final int beginCityCount = player.getInitialCityCount();

        int points = 0;
        points += beginSettlementCount - player.getSettlementCount();
        points += (beginCityCount - player.getCityCount()) * 2;
        if (longestRoad.getHolder() == player.getColor()) {
            points += 2;
        }
        if (largestArmy.getHolder() == player.getColor()) {
            points += 2;
        }
        for (DevelopmentCard card : player.getDevelopmentCards()) {
            if (card instanceof VictoryPointCard) {
                points += 1;
            }
        }
        return points;
    }

    public boolean isWinning(Player player) {
        return calculateForPlayer(player) >= 10;
    }
}
