package model;

import exception.PlaceBuildingException;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class PropertyPlacer {

    private LongestRoad longestRoad;
    private ResourceBundle messages;

    public PropertyPlacer(LongestRoad longestRoad, ResourceBundle messages) {
        this.longestRoad = longestRoad;
        this.messages = messages;
    }

    public void placeRegularSettlementOnMap(Player player, GameMap gameMap, Intersection intersection) {
        ArrayList<Edge> edges = gameMap.getAllEdgesFromIntersection(intersection);

        verifyIntersectionIsConnected(player, edges);

        verifyIsExistAdjacentBuildings(gameMap, intersection, edges);

        placeSettlement(player, gameMap, intersection);

    }

    private void verifyIsExistAdjacentBuildings(GameMap gameMap, Intersection intersection, ArrayList<Edge> edges) {
        for (Edge edge : edges) {
            ArrayList<Intersection> intersectionsFromEdge = gameMap.getAllIntersectionsFromEdge(edge);
            intersectionsFromEdge.remove(intersection);
            if (intersectionsFromEdge.get(0).getBuildingColor() != PlayerColor.NONE) {
                throw new PlaceBuildingException(messages.getString("PropertyPlacer.0"));
            }
        }
    }

    public void placeInitialSettlement(Player player, GameMap gameMap, Intersection intersection) {
        ArrayList<Edge> edges = gameMap.getAllEdgesFromIntersection(intersection);

        verifyIsExistAdjacentBuildings(gameMap, intersection, edges);

        placeSettlement(player, gameMap, intersection);
    }

    public void placeInitialSettlementRound2(Player player, GameMap gameMap, Intersection intersection) {
        ArrayList<Edge> edges = gameMap.getAllEdgesFromIntersection(intersection);

        verifyIsExistAdjacentBuildings(gameMap, intersection, edges);

        placeSettlement(player, gameMap, intersection);

        for (Hex hex : gameMap.getAllHexesFromIntersection(intersection)) {
            try {
                player.giveResource(hex.getResource(), 1);
            } catch (IllegalArgumentException e) {

            }
        }
    }

    void placeSettlement(Player player, GameMap gameMap, Intersection intersection) {
        if (player.getSettlementCount() > 0) {
            if (intersection.getBuildingColor() != PlayerColor.NONE) {
                throw new PlaceBuildingException(messages.getString("PropertyPlacer.1"));
            }
            intersection.setSettlement(player.getColor());
            longestRoad.updateLongestRoad(gameMap);
            player.decrementSettlementCount();
            return;
        }
        throw new PlaceBuildingException(messages.getString("PropertyPlacer.2"));
    }

    public void placeCityOnMap(Player player, Intersection intersection) {
        if (player.getCityCount() == 0) {
            throw new PlaceBuildingException(messages.getString("PropertyPlacer.3"));
        }
        if (intersection.isEmpty()) {
            throw new PlaceBuildingException(messages.getString("PropertyPlacer.4"));
        } else if (intersection.getBuildingColor() != player.getColor()) {
            throw new PlaceBuildingException(messages.getString("PropertyPlacer.5"));
        } else if (intersection.hasCity()) {
            throw new PlaceBuildingException(messages.getString("PropertyPlacer.6"));
        } else {
            intersection.setCity(player.getColor());
            player.incrementSettlementCount();
        }
        player.decrementCityCount();
    }

    public void placeInitialRoad(Player player, GameMap gameMap, Edge edge) {
        verifyIfInitialEdgeAttachToInitialSettlement(player, gameMap, gameMap.getAllIntersectionsFromEdge(edge));
        placeRoadOnMap(player, gameMap, edge);
    }

    public void placeRoadOnMap(Player player, GameMap gameMap, Edge edge) {
        if (player.getRoadCount() == 0) {
            throw new PlaceBuildingException(messages.getString("PropertyPlacer.7"));
        }
        if (edge.getRoadColor() != PlayerColor.NONE) {
            throw new PlaceBuildingException(messages.getString("PropertyPlacer.8"));
        }

        ArrayList<Intersection> intersections = gameMap.getAllIntersectionsFromEdge(edge);

        verifyIfEdgeIsConnected(player, gameMap, intersections);

        edge.setRoad(player.getColor());
        player.decrementRoadCount();
        longestRoad.updateLongestRoad(gameMap);
    }

    void verifyIntersectionIsConnected(Player player, ArrayList<Edge> edges) {
        for (Edge edge : edges) {
            if (edge.getRoadColor() == player.getColor()) {
                return;
            }
        }
        throw new PlaceBuildingException(messages.getString("PropertyPlacer.9"));
    }

    private void verifyIfEdgeIsConnected(Player player, GameMap gameMap, ArrayList<Intersection> intersections) {
        for (Intersection intersection : intersections) {
            if (intersection.getBuildingColor() == player.getColor()) {
                return;
            } else if (intersection.getBuildingColor() == PlayerColor.NONE) {
                ArrayList<Edge> edges = gameMap.getAllEdgesFromIntersection(intersection);
                for (Edge edge : edges) {
                    if (edge.getRoadColor() == player.getColor()) {
                        return;
                    }
                }
            }
        }
        throw new PlaceBuildingException(messages.getString("PropertyPlacer.10"));
    }

    private void verifyIfInitialEdgeAttachToInitialSettlement(Player player, GameMap gameMap,
            ArrayList<Intersection> intersections) {
        boolean isConnectedToIntersection = false;
        for (Intersection intersection : intersections) {
            if (intersection.getBuildingColor() == player.getColor()) {
                isConnectedToIntersection = true;
                for (Edge edge : gameMap.getAllEdgesFromIntersection(intersection)) {
                    if (edge.getRoadColor() == player.getColor()) {
                        throw new PlaceBuildingException(
                                "An initial road can only be place nearby the initial settlement in the same round");
                    }
                }
            }
        }
        if (isConnectedToIntersection) {
            return;
        }
        throw new PlaceBuildingException(
                "An initial road can only be place nearby the initial settlement in the same round");
    }
}
