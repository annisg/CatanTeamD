
package model;

import java.awt.*;
import java.util.*;

import exception.*;

public class GameMap {

    private HexMap hexMap;
    private IntersectionMap intersectionMap;
    private EdgeMap edgeMap;
    private PortMap portMap;

    public GameMap() {
        this.hexMap = new HexMap();
        this.edgeMap = new EdgeMap();
        this.intersectionMap = new IntersectionMap();
    }

    public void setUpBeginnerMap(int numPlayers) {
        if (numPlayers != 3 && numPlayers != 4) {
            throw new IllegalArgumentException();
        }

        this.hexMap.setUpBeginnerMap();
        this.portMap = new PortMap();
        this.portMap.addPortsToIntersectionMap(intersectionMap);
        placeBeginnerPieces(numPlayers);
    }

    public void setUpAdvancedMap() {
        setUpAdvancedMap(new Random());
    }

    void setUpAdvancedMap(Random rand) {
        this.hexMap.setUpAdvancedMap(rand);
        this.portMap = new PortMap(rand);
        this.portMap.addPortsToIntersectionMap(intersectionMap);
    }

    public HexMap getHexMap() {
        return this.hexMap;
    }

    public IntersectionMap getIntersectionMap() {
        return this.intersectionMap;
    }

    public EdgeMap getEdgeMap() {
        return this.edgeMap;
    }

    public PortMap getPortMap() {
        return this.portMap;
    }

    public Hex getHex(int row, int col) {
        return this.hexMap.getHex(new MapPosition(row, col));
    }

    public Intersection getIntersection(int row, int col) {
        return this.intersectionMap.getIntersection(new MapPosition(row, col));
    }

    public Edge getClosestEdgeToPoint(Point point) {
        return this.edgeMap.getClosestEdgeToPoint(point);
    }
    
    public Intersection getClosestIntersectionToPoint(Point point) {
        return this.intersectionMap.getClosestIntersectionToPoint(point);
    }

    public Edge getEdge(int row, int col) {
        return this.edgeMap.getEdge(new MapPosition(row, col));
    }

    public void moveRobberToClosestHex(Point mousePosition) {
        this.hexMap.moveRobberToPosition(getClosestValidRobberPosition(mousePosition));
    }

    public MapPosition getClosestValidRobberPosition(Point mousePosition) {
        return this.hexMap.getClosestValidRobberPosition(mousePosition);
    }

    private void placeBeginnerPieces(int numPlayers) {

        getIntersection(7, 1).setSettlement(PlayerColor.WHITE);
        getIntersection(5, 4).setSettlement(PlayerColor.WHITE);
        getEdge(6, 2).setRoad(PlayerColor.WHITE);
        getEdge(5, 4).setRoad(PlayerColor.WHITE);

        getIntersection(3, 1).setSettlement(PlayerColor.BLUE);
        getIntersection(3, 3).setSettlement(PlayerColor.BLUE);
        getEdge(2, 2).setRoad(PlayerColor.BLUE);
        getEdge(3, 3).setRoad(PlayerColor.BLUE);

        getIntersection(3, 2).setSettlement(PlayerColor.ORANGE);
        getIntersection(8, 3).setSettlement(PlayerColor.ORANGE);
        getEdge(2, 4).setRoad(PlayerColor.ORANGE);
        getEdge(8, 5).setRoad(PlayerColor.ORANGE);

        if (numPlayers == 4) {
            getIntersection(5, 1).setSettlement(PlayerColor.RED);
            getIntersection(9, 1).setSettlement(PlayerColor.RED);
            getEdge(4, 2).setRoad(PlayerColor.RED);
            getEdge(8, 3).setRoad(PlayerColor.RED);
        }

    }

    public ArrayList<Hex> getHexesByResourceNumber(int resourceNumber) {
        return this.hexMap.getHexesByResourceNumber(resourceNumber);
    }

    public Port getPortFromIntersection(Intersection givenIntersection) {
        MapPosition intersectionPosition = this.intersectionMap.findIntersectionPosition(givenIntersection);
        return this.portMap.getPortFromPosition(intersectionPosition);
    }

    public Port getPortFromIntersection(MapPosition intersectionPos) {
        return this.portMap.getPortFromPosition(intersectionPos);
    }

    public Hex getSpecificHexFromIntersection(Intersection givenIntersection, Direction givenDirection) {
        MapPosition hexPosition = this.intersectionMap.getAdjacentHex(givenIntersection, givenDirection);
        try {
            return this.hexMap.getHex(hexPosition);
        } catch (InvalidHexPositionException e) {
            throw new IllegalArgumentException();
        }
    }

    public ArrayList<Hex> getAllHexesFromIntersection(Intersection givenIntersection) {
        MapPosition intersectionPos = this.intersectionMap.findIntersectionPosition(givenIntersection);
        return getAllHexesFromIntersection(intersectionPos.getRow(), intersectionPos.getColumn());
    }

    public ArrayList<Hex> getAllHexesFromIntersection(int intersectionRow, int intersectionCol) {
        MapPosition intersectionPos = new MapPosition(intersectionRow, intersectionCol);
        ArrayList<MapPosition> hexPositions = this.intersectionMap.getAllAdjacentHexes(intersectionPos);
        ArrayList<Hex> hexes = new ArrayList<Hex>();

        for (int i = 0; i < hexPositions.size(); i++) {
            try {
                MapPosition currentHex = hexPositions.get(i);
                hexes.add(this.hexMap.getHex(currentHex));
            } catch (InvalidHexPositionException e) {
            }
        }

        return hexes;
    }

    public Edge getSpecificEdgeFromIntersection(Intersection givenIntersection, Direction givenDirection) {
        MapPosition edgePosition = this.intersectionMap.getAdjacentEdge(givenIntersection, givenDirection);
        try {
            return this.edgeMap.getEdge(edgePosition);
        } catch (InvalidEdgePositionException e) {
            throw new IllegalArgumentException();
        }
    }

    public ArrayList<Edge> getAllEdgesFromIntersection(Intersection givenIntersection) {
        MapPosition intersectionPos = this.intersectionMap.findIntersectionPosition(givenIntersection);
        return getAllEdgesFromIntersection(intersectionPos.getRow(), intersectionPos.getColumn());
    }

    public ArrayList<Edge> getAllEdgesFromIntersection(int intersectionRow, int intersectionCol) {
        MapPosition intersectionPos = new MapPosition(intersectionRow, intersectionCol);
        ArrayList<MapPosition> edgePositions = this.intersectionMap.getAllAdjacentEdges(intersectionPos);
        ArrayList<Edge> edges = new ArrayList<Edge>();

        for (int i = 0; i < edgePositions.size(); i++) {
            try {
                Edge currentEdge = this.edgeMap.getEdge(edgePositions.get(i));
                edges.add(currentEdge);
            } catch (InvalidEdgePositionException e) {
            }
        }

        return edges;
    }

    public Intersection getSpecificIntersectionFromEdge(Edge givenEdge, Direction intersectionDirection) {
        MapPosition intersectionPosition = this.edgeMap.getAdjacentIntersection(givenEdge, intersectionDirection);
        return this.intersectionMap.getIntersection(intersectionPosition);
    }

    public ArrayList<Intersection> getAllIntersectionsFromEdge(Edge givenEdge) {
        MapPosition edgePosition = this.edgeMap.findEdgePosition(givenEdge);
        return getAllIntersectionsFromEdge(edgePosition.getRow(), edgePosition.getColumn());
    }

    public ArrayList<Intersection> getAllIntersectionsFromEdge(int edgeRow, int edgeCol) {
        ArrayList<MapPosition> intersectionPositions = this.edgeMap
                .getAllAdjacentIntersections(new MapPosition(edgeRow, edgeCol));
        ArrayList<Intersection> intersections = new ArrayList<Intersection>();

        for (int i = 0; i < intersectionPositions.size(); i++) {
            MapPosition currentPos = intersectionPositions.get(i);
            Intersection foundIntersection = this.intersectionMap.getIntersection(currentPos);
            intersections.add(foundIntersection);
        }

        return intersections;
    }

    public Intersection getSpecificIntersectionFromHex(Hex givenHex, Direction intersectionDirection) {
        MapPosition intersectionPos = this.hexMap.getAdjacentIntersection(givenHex, intersectionDirection);
        return this.intersectionMap.getIntersection(intersectionPos);
    }

    public ArrayList<Intersection> getAllIntersectionsFromHex(Hex givenHex) {
        MapPosition hexPosition = this.hexMap.findHexPosition(givenHex);
        return getAllIntersectionsFromHex(hexPosition.getRow(), hexPosition.getColumn());
    }

    public ArrayList<Intersection> getAllIntersectionsFromHex(int hexRow, int hexCol) {
        MapPosition hexPos = new MapPosition(hexRow, hexCol);
        ArrayList<MapPosition> intersectionPositions = this.hexMap.getAllAdjacentIntersections(hexPos);
        ArrayList<Intersection> intersections = new ArrayList<Intersection>();

        for (int i = 0; i < intersectionPositions.size(); i++) {
            try {
                Intersection currentIntersection = this.intersectionMap.getIntersection(intersectionPositions.get(i));
                intersections.add(currentIntersection);
            } catch (InvalidIntersectionPositionException e) {
            }
        }

        return intersections;
    }
    
    public boolean canSeeIntersection(Intersection givenIntersection, PlayerColor color) {
        
        if(color == PlayerColor.NONE || givenIntersection.getBuildingColor() == PlayerColor.NONE) {
            return true;
        }
        
        if(givenIntersection.getBuildingColor() == color) {
            return true;
        } else {
            ArrayList<Edge> adjacentEdges = getAllEdgesFromIntersection(givenIntersection);
            for(Edge edge : adjacentEdges) {
                if(edge.getRoadColor() == color) {
                    return true;
                }
                //Don't check adjacent intersections because a building cannot be physically placed there
            }
        }
        
        return false;
    }
    
    public boolean canSeeEdge(Edge givenEdge, PlayerColor color) {
        
        if(color == PlayerColor.NONE || givenEdge.getRoadColor() == PlayerColor.NONE) {
            return true;
        }
        
        if(givenEdge.getRoadColor() == color) {
            return true;
        } else {
            ArrayList<Intersection> adjacentIntersections = getAllIntersectionsFromEdge(givenEdge);
            for(Intersection intersection : adjacentIntersections) {
                if(intersection.getBuildingColor() == color) {
                    return true;
                }
                ArrayList<Edge> adjacentEdges = getAllEdgesFromIntersection(intersection);
                for(Edge edge : adjacentEdges) {
                    if(edge.getRoadColor() == color) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
}
