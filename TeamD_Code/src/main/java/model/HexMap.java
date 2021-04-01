package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import exception.*;

public class HexMap {

    Hex[][] hexes;
    private HashMap<Integer, ArrayList<Hex>> resourceHashMap;
    Robber robber;

    public HexMap() {
        hexes = new Hex[5][];
        hexes[0] = new Hex[3];
        hexes[1] = new Hex[4];
        hexes[2] = new Hex[5];
        hexes[3] = new Hex[4];
        hexes[4] = new Hex[3];

        this.resourceHashMap = new HashMap<Integer, ArrayList<Hex>>();
    }

    public Hex getHex(MapPosition hexPosition) {
        if (isInvalidHexPosition(hexPosition)) {
            throw new InvalidHexPositionException();
        }
        return this.hexes[hexPosition.getRow()][hexPosition.getColumn()];
    }

    public ArrayList<Hex> getHexesByResourceNumber(int resourceNumber) {
        if (isInvalidResourceNumber(resourceNumber)) {
            throw new IllegalArgumentException();
        }
        return resourceHashMap.get(new Integer(resourceNumber));
    }

    public void moveRobberToPosition(MapPosition newRobberPosition) {
        Hex newRobberHex = this.getHex(newRobberPosition);
        this.robber.moveRobberTo(newRobberHex);
    }

    public ArrayList<MapPosition> getAllAdjacentIntersections(Hex givenHex) {
        MapPosition hexPosition = findHexPosition(givenHex);
        return getAllAdjacentIntersections(hexPosition);
    }

    public ArrayList<MapPosition> getAllAdjacentIntersections(MapPosition hexPosition) {
        ArrayList<MapPosition> allIntersectionPositions = new ArrayList<MapPosition>();
        allIntersectionPositions.add(getIntersectionDirection0(hexPosition));
        allIntersectionPositions.add(getIntersectionDirection1(hexPosition));
        allIntersectionPositions.add(getIntersectionDirection2(hexPosition));
        allIntersectionPositions.add(getIntersectionDirection3(hexPosition));
        allIntersectionPositions.add(getIntersectionDirection4(hexPosition));
        allIntersectionPositions.add(getIntersectionDirection5(hexPosition));
        return allIntersectionPositions;
    }

    public MapPosition getAdjacentIntersection(Hex givenHex, Direction givenDirection) {
        MapPosition hexPosition = findHexPosition(givenHex);
        return getAdjacentIntersection(hexPosition, givenDirection);
    }

    public MapPosition getAdjacentIntersection(MapPosition hexPosition, Direction givenDirection) {
        switch (givenDirection) {
        case ZERO:
            return getIntersectionDirection0(hexPosition);
        case ONE:
            return getIntersectionDirection1(hexPosition);
        case TWO:
            return getIntersectionDirection2(hexPosition);
        case THREE:
            return getIntersectionDirection3(hexPosition);
        case FOUR:
            return getIntersectionDirection4(hexPosition);
        case FIVE:
        default:
            return getIntersectionDirection5(hexPosition);
        }
    }

    public MapPosition getIntersectionDirection0(MapPosition hexPosition) {
        if (isInvalidHexPosition(hexPosition)) {
            throw new InvalidHexPositionException();
        }
        MapPosition intersectionPosition = new MapPosition(2 * hexPosition.getRow() + 3, hexPosition.getColumn());
        if (isInBottomHalf(hexPosition)) {
            intersectionPosition.setPosition(intersectionPosition.getRow(), hexPosition.getColumn() + 1);
        }
        return intersectionPosition;
    }

    public MapPosition getIntersectionDirection1(MapPosition hexPosition) {
        if (isInvalidHexPosition(hexPosition)) {
            throw new InvalidHexPositionException();
        }
        return new MapPosition(2 * hexPosition.getRow() + 2, hexPosition.getColumn() + 1);
    }

    public MapPosition getIntersectionDirection2(MapPosition hexPosition) {
        if (isInvalidHexPosition(hexPosition)) {
            throw new InvalidHexPositionException();
        }
        return new MapPosition(2 * hexPosition.getRow() + 1, hexPosition.getColumn() + 1);
    }

    public MapPosition getIntersectionDirection3(MapPosition hexPosition) {
        if (isInvalidHexPosition(hexPosition)) {
            throw new InvalidHexPositionException();
        }
        MapPosition intersectionPosition = new MapPosition(2 * hexPosition.getRow(), hexPosition.getColumn());
        if (isInTopHalf(hexPosition)) {
            intersectionPosition.setPosition(intersectionPosition.getRow(), hexPosition.getColumn() + 1);
        }
        return intersectionPosition;
    }

    public MapPosition getIntersectionDirection4(MapPosition hexPosition) {
        if (isInvalidHexPosition(hexPosition)) {
            throw new InvalidHexPositionException();
        }
        return new MapPosition(2 * hexPosition.getRow() + 1, hexPosition.getColumn());
    }

    public MapPosition getIntersectionDirection5(MapPosition hexPosition) {
        if (isInvalidHexPosition(hexPosition)) {
            throw new InvalidHexPositionException();
        }
        return new MapPosition(2 * hexPosition.getRow() + 2, hexPosition.getColumn());
    }

    private boolean isInvalidResourceNumber(int resourceNumber) {
        return resourceNumber < 2 || resourceNumber > 12 || resourceNumber == 7;
    }

    private boolean isInvalidHexPosition(MapPosition posToCheck) {
        int row = posToCheck.getRow();
        int col = posToCheck.getColumn();
        boolean invalidRow = row < 0 || row >= hexes.length;
        boolean invalidCol = invalidRow || col < 0 || col >= hexes[row].length;
        return invalidRow || invalidCol;
    }

    public MapPosition findHexPosition(Hex givenHex) {
        for (int i = 0; i < this.hexes.length; i++) {
            for (int j = 0; j < this.hexes[i].length; j++) {
                if (this.hexes[i][j].equals(givenHex)) {
                    return new MapPosition(i, j);
                }
            }
        }
        throw new InvalidHexPositionException();
    }

    public void setUpAdvancedMap(Random random) {
        List<Resource> resources = getShuffledResources(random);
        List<Integer> resourceNumberTokens = getShuffledResourceNumbers(random);

        ArrayList<Hex> hexOrder = new ArrayList<Hex>();
        int addedDesert = 0;
        for (int i = 0; i < resources.size(); i++) {
            if (resources.get(i) == Resource.DESERT) {
                hexOrder.add(new Hex());
                addedDesert = 1;
            } else {
                hexOrder.add(new Hex(resources.get(i), resourceNumberTokens.get(i - addedDesert)));
            }
        }
        placeHexArrayOnMap(hexOrder);
        populateResourceNumberHashMap();
    }

    List<Integer> getShuffledResourceNumbers(Random random) {
        List<Integer> resourceNumbers = getStandardResourceNumbers();
        Collections.shuffle(resourceNumbers, random);
        return resourceNumbers;
    }

    private List<Integer> getStandardResourceNumbers() {
        Integer[] resourceTokens = new Integer[] { 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12 };
        List<Integer> resourceNumbers = Arrays.asList(resourceTokens);
        return resourceNumbers;
    }

    List<Resource> getShuffledResources(Random random) {
        List<Resource> resources = getStandardResources();
        Collections.shuffle(resources, random);
        return resources;
    }

    private List<Resource> getStandardResources() {
        Resource[] resourceArray = new Resource[] { Resource.DESERT, Resource.BRICK, Resource.BRICK, Resource.BRICK,
                Resource.ORE, Resource.ORE, Resource.ORE, Resource.LUMBER, Resource.LUMBER, Resource.LUMBER,
                Resource.LUMBER, Resource.WOOL, Resource.WOOL, Resource.WOOL, Resource.WOOL, Resource.GRAIN,
                Resource.GRAIN, Resource.GRAIN, Resource.GRAIN };
        List<Resource> resources = Arrays.asList(resourceArray);
        return resources;
    }

    public void setUpBeginnerMap() {
        ArrayList<Hex> hexOrder = new ArrayList<Hex>();
        hexOrder.add(new Hex(Resource.BRICK, 5));
        hexOrder.add(new Hex(Resource.GRAIN, 6));
        hexOrder.add(new Hex(Resource.WOOL, 11));
        hexOrder.add(new Hex(Resource.LUMBER, 8));
        hexOrder.add(new Hex(Resource.ORE, 3));
        hexOrder.add(new Hex(Resource.GRAIN, 4));
        hexOrder.add(new Hex(Resource.WOOL, 5));
        hexOrder.add(new Hex(Resource.GRAIN, 9));
        hexOrder.add(new Hex(Resource.LUMBER, 11));
        hexOrder.add(new Hex());
        hexOrder.add(new Hex(Resource.LUMBER, 3));
        hexOrder.add(new Hex(Resource.ORE, 8));
        hexOrder.add(new Hex(Resource.GRAIN, 12));
        hexOrder.add(new Hex(Resource.BRICK, 6));
        hexOrder.add(new Hex(Resource.WOOL, 4));
        hexOrder.add(new Hex(Resource.BRICK, 10));
        hexOrder.add(new Hex(Resource.ORE, 10));
        hexOrder.add(new Hex(Resource.WOOL, 2));
        hexOrder.add(new Hex(Resource.LUMBER, 9));
        placeHexArrayOnMap(hexOrder);

        populateResourceNumberHashMap();
    }

    private void populateResourceNumberHashMap() {
        for (int row = 0; row < 5; row++) {
            int colLength = 3;
            if (row == 1 || row == 3) {
                colLength = 4;
            } else if (row == 2) {
                colLength = 5;
            }

            for (int j = 0; j < colLength; j++) {
                Hex currentHex = hexes[row][j];
                Integer currentResourceNumber = new Integer(currentHex.getRollResourceNumber());

                if (resourceHashMap.containsKey(currentResourceNumber)) {
                    resourceHashMap.get(currentResourceNumber).add(currentHex);
                } else {
                    ArrayList<Hex> hexList = new ArrayList<Hex>();
                    hexList.add(currentHex);
                    resourceHashMap.put(currentResourceNumber, hexList);
                }
                if (currentHex.getResource() == Resource.DESERT) {
                    this.robber = new Robber(currentHex);
                }
            }
        }
    }

    private void placeHexArrayOnMap(ArrayList<Hex> hexes) {
        this.hexes[0][0] = hexes.get(0);
        this.hexes[0][1] = hexes.get(1);
        this.hexes[0][2] = hexes.get(2);

        this.hexes[1][0] = hexes.get(3);
        this.hexes[1][1] = hexes.get(4);
        this.hexes[1][2] = hexes.get(5);
        this.hexes[1][3] = hexes.get(6);

        this.hexes[2][0] = hexes.get(7);
        this.hexes[2][1] = hexes.get(8);
        this.hexes[2][2] = hexes.get(9);
        this.hexes[2][3] = hexes.get(10);
        this.hexes[2][4] = hexes.get(11);

        this.hexes[3][0] = hexes.get(12);
        this.hexes[3][1] = hexes.get(13);
        this.hexes[3][2] = hexes.get(14);
        this.hexes[3][3] = hexes.get(15);

        this.hexes[4][0] = hexes.get(16);
        this.hexes[4][1] = hexes.get(17);
        this.hexes[4][2] = hexes.get(18);
    }

    private boolean isInBottomHalf(MapPosition pos) {
        return pos.getRow() < 2;
    }

    private boolean isInTopHalf(MapPosition pos) {
        return pos.getRow() > 2;
    }

    public int getNumberOfRows() {
        return 5;
    }

    public int getNumberOfHexesInRow(int n) {
        if (n < 0 || n >= getNumberOfRows()) {
            throw new InvalidHexPositionException();
        }
        return this.hexes[n].length;
    }
}
