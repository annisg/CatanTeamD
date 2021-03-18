package model;

import java.util.*;

public class PieceBuilder {

    private GameMap board;
    private PropertyPlacer placer;
    private DevelopmentDeck developmentDeck;

    private ArrayList<Resource> settlementCost, cityCost, roadCost, developmentCardCost;

    public PieceBuilder(GameMap board, PropertyPlacer placer, DevelopmentDeck developmentDeck) {

        this.board = board;
        this.placer = placer;
        this.developmentDeck = developmentDeck;

        settlementCost = new ArrayList<Resource>(
                Arrays.asList(Resource.GRAIN, Resource.BRICK, Resource.LUMBER, Resource.WOOL));
        cityCost = new ArrayList<Resource>(
                Arrays.asList(Resource.GRAIN, Resource.GRAIN, Resource.ORE, Resource.ORE, Resource.ORE));
        roadCost = new ArrayList<Resource>(Arrays.asList(Resource.BRICK, Resource.LUMBER));
        developmentCardCost = new ArrayList<Resource>(Arrays.asList(Resource.GRAIN, Resource.ORE, Resource.WOOL));

    }

    public void buildInitialSettlement(Player player, Intersection intersection) {
        placer.placeInitialSettlement(player, board, intersection);
    }

    public void buildInitialSettlementRound2(Player player, Intersection intersection) {
        placer.placeInitialSettlementRound2(player, board, intersection);
    }

    public void buildInitialRoad(Player player, Edge edge) {
        placer.placeInitialRoad(player, board, edge);
    }

    public void buildSettlement(Player player, Intersection intersection) {

        if (hasResourcesToBuildSettlement(player)) {
            placer.placeRegularSettlementOnMap(player, board, intersection);
            removeResources(player, settlementCost);

        } else {
            throw new IllegalStateException();
        }
    }

    public boolean hasResourcesToBuildSettlement(Player player) {
        return hasSufficientResources(player, settlementCost);
    }

    public void buildCity(Player player, Intersection intersection) {

        if (hasResourcesToBuildCity(player)) {
            placer.placeCityOnMap(player, intersection);
            removeResources(player, cityCost);

        } else {
            throw new IllegalStateException();
        }
    }

    public boolean hasResourcesToBuildCity(Player player) {
        return hasSufficientResources(player, cityCost);
    }

    public void buildRoad(Player player, Edge edge) {

        if (hasResourcesToBuildRoad(player)) {
            placer.placeRoadOnMap(player, board, edge);
            removeResources(player, roadCost);

        } else {
            throw new IllegalStateException();
        }
    }

    public boolean hasResourcesToBuildRoad(Player player) {
        return hasSufficientResources(player, roadCost);
    }

    public String buildDevelopmentCard(Player player) {

        if (hasResourcesToBuildDevelopmentCard(player)) {
            DevelopmentCard card = developmentDeck.drawCard();

            player.giveDevelopmentCard(card);
            removeResources(player, developmentCardCost);

            return card.getName();
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean hasResourcesToBuildDevelopmentCard(Player player) {
        return hasSufficientResources(player, developmentCardCost);
    }

    private boolean hasSufficientResources(Player player, ArrayList<Resource> cost) {

        return player.getResourceCount(Resource.GRAIN) >= Collections.frequency(cost, Resource.GRAIN)
                && player.getResourceCount(Resource.BRICK) >= Collections.frequency(cost, Resource.BRICK)
                && player.getResourceCount(Resource.ORE) >= Collections.frequency(cost, Resource.ORE)
                && player.getResourceCount(Resource.LUMBER) >= Collections.frequency(cost, Resource.LUMBER)
                && player.getResourceCount(Resource.WOOL) >= Collections.frequency(cost, Resource.WOOL);
    }

    private void removeResources(Player player, ArrayList<Resource> cost) {

        for (Resource resource : Resource.values()) {

            int resourceCount = Collections.frequency(cost, resource);

            if (resourceCount > 0) {
                player.removeResource(resource, resourceCount);
            }
        }
    }

}
