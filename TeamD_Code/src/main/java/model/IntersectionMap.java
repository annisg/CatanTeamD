package model;

import java.awt.*;
import java.util.ArrayList;

import exception.*;
import gui.EdgeDirection;

public class IntersectionMap {

    private Intersection[][] intersectionMap;

    public IntersectionMap() {
        Intersection[][] intersectMap = new Intersection[12][];

        for (int i = 0; i < getNumberOfRows(); i++) {
            int length = (6 - Math.abs(3 - (int) Math.ceil(i / 2.0)));
            intersectMap[i] = new Intersection[length];

            for (int j = 0; j < length; j++) {
                intersectMap[i][j] = new Intersection();
            }
        }

        this.intersectionMap = intersectMap;
    }

    public ArrayList<MapPosition> getAllAdjacentHexes(Intersection knownIntersection) {
        MapPosition intersectionPos = this.findIntersectionPosition(knownIntersection);
        return getAllAdjacentHexes(intersectionPos);
    }

    public ArrayList<MapPosition> getAllAdjacentHexes(MapPosition intersectionPos) {
        ArrayList<MapPosition> hexPositions = new ArrayList<MapPosition>();
        hexPositions.add(getHexDirection0(intersectionPos));
        hexPositions.add(getHexDirection1(intersectionPos));
        hexPositions.add(getHexDirection2(intersectionPos));
        return hexPositions;
    }

    public MapPosition getAdjacentHex(Intersection givenIntersection, Direction givenDirection) {
        MapPosition intersectionPos = findIntersectionPosition(givenIntersection);
        return getAdjacentHex(intersectionPos, givenDirection);
    }

    public MapPosition getAdjacentHex(MapPosition intersectionPos, Direction givenDirection) {
        switch (givenDirection) {
        case ZERO:
            return getHexDirection0(intersectionPos);
        case ONE:
            return getHexDirection1(intersectionPos);
        case TWO:
            return getHexDirection2(intersectionPos);
        default:
            throw new IllegalArgumentException();
        }
    }

    public MapPosition getHexDirection0(MapPosition intersectionPos) {
        if (isNotValidPosition(intersectionPos)) {
            throw new InvalidIntersectionPositionException();
        }

        int row = intersectionPos.getRow();
        int col = intersectionPos.getColumn();
        MapPosition hexPosition = new MapPosition(row / 2, col);

        if (isEven(row) && isInTopHalf(intersectionPos)) {
            hexPosition.setPosition(row / 2, col - 1);
        }

        return hexPosition;
    }

    public MapPosition getHexDirection1(MapPosition intersectionPos) {
        if (isNotValidPosition(intersectionPos)) {
            throw new InvalidIntersectionPositionException();
        }

        int row = intersectionPos.getRow();
        int col = intersectionPos.getColumn();
        MapPosition hexPosition = new MapPosition(row / 2 - 1, col);

        if (isOdd(row) && isInBottomHalf(intersectionPos)) {
            hexPosition.setPosition(row / 2 - 1, col - 1);
        }

        return hexPosition;
    }

    public MapPosition getHexDirection2(MapPosition intersectionPos) {
        if (isNotValidPosition(intersectionPos)) {
            throw new InvalidIntersectionPositionException();
        }

        int row = intersectionPos.getRow();
        int col = intersectionPos.getColumn();
        MapPosition hexPosition = new MapPosition(row / 2, col - 1);

        if (isEven(row)) {
            hexPosition.setPosition(row / 2 - 1, col - 1);
        }

        return hexPosition;
    }

    public ArrayList<MapPosition> getAllAdjacentEdges(Intersection knownIntersection) {
        MapPosition intersectionPos = findIntersectionPosition(knownIntersection);
        return getAllAdjacentEdges(intersectionPos);
    }

    public ArrayList<MapPosition> getAllAdjacentEdges(MapPosition intersectionPos) {
        ArrayList<MapPosition> foundEdges = new ArrayList<MapPosition>();
        foundEdges.add(getEdgeDirection0(intersectionPos));
        foundEdges.add(getEdgeDirection1(intersectionPos));
        foundEdges.add(getEdgeDirection2(intersectionPos));
        return foundEdges;
    }

    public MapPosition getAdjacentEdge(Intersection givenIntersection, Direction givenDirection) {
        MapPosition intersectionPos = findIntersectionPosition(givenIntersection);
        return getAdjacentEdge(intersectionPos, givenDirection);
    }

    public MapPosition getAdjacentEdge(MapPosition intersectionPos, Direction givenDirection) {
        switch (givenDirection) {
        case ZERO:
            return getEdgeDirection0(intersectionPos);
        case ONE:
            return getEdgeDirection1(intersectionPos);
        case TWO:
            return getEdgeDirection2(intersectionPos);
        default:
            throw new IllegalArgumentException();
        }
    }

    public MapPosition getEdgeDirection0(MapPosition intersectionPos) {
        if (isNotValidPosition(intersectionPos)) {
            throw new InvalidIntersectionPositionException();
        }

        int row = intersectionPos.getRow();
        int col = intersectionPos.getColumn();
        MapPosition edgePosition = new MapPosition();

        if (isOdd(row)) {
            edgePosition.setPosition(row, col);
        } else {
            if (isInTopHalf(intersectionPos)) {
                edgePosition.setPosition(row, 2 * col);
            } else {
                edgePosition.setPosition(row, 2 * col + 1);
            }
        }

        return edgePosition;
    }

    public MapPosition getEdgeDirection1(MapPosition intersectionPos) {
        if (isNotValidPosition(intersectionPos)) {
            throw new InvalidIntersectionPositionException();
        }

        int row = intersectionPos.getRow();
        int col = intersectionPos.getColumn();
        MapPosition edgePosition = new MapPosition();

        if (isEven(row)) {
            edgePosition.setPosition(row - 1, col);
        } else {
            if (isInBottomHalf(intersectionPos)) {
                edgePosition.setPosition(row - 1, 2 * col);
            } else {
                edgePosition.setPosition(row - 1, 2 * col + 1);
            }
        }

        return edgePosition;
    }

    public MapPosition getEdgeDirection2(MapPosition intersectionPos) {
        if (isNotValidPosition(intersectionPos)) {
            throw new InvalidIntersectionPositionException();
        }

        MapPosition edgePosition = new MapPosition();
        int row = intersectionPos.getRow();
        int col = intersectionPos.getColumn() * 2;

        if ((isInTopHalf(intersectionPos) && isEven(row)) || (isInBottomHalf(intersectionPos) && isOdd(row))) {
            col = intersectionPos.getColumn() * 2 - 1;
        }

        if (isOdd(row)) {
            edgePosition.setPosition(row - 1, col);
        } else {
            edgePosition.setPosition(row, col);
        }

        return edgePosition;
    }

    public Intersection getIntersection(MapPosition pos) {
        if (isNotValidPosition(pos)) {
            throw new InvalidIntersectionPositionException();
        }
        return this.intersectionMap[pos.getRow()][pos.getColumn()];
    }
    
    public Intersection getClosestIntersectionToPoint(Point point) {
        Intersection closestIntersection = intersectionMap[3][3];
        for(int i = 0; i < intersectionMap.length; i++) {
            for (int j = 0; j < intersectionMap.length; j++) {
                if(!isNotValidPosition(new MapPosition(i, j))) {
                    if(closestIntersection == null) {
                        closestIntersection = intersectionMap[i][j];
                    }
                    if (getIntersectionDistanceFromPoint(new MapPosition(i, j), point) <
                            getIntersectionDistanceFromPoint(findIntersectionPosition(closestIntersection), point)) {
                        closestIntersection = intersectionMap[i][j];
                    }
                }
            }
        }
        return closestIntersection;
    }
    
    public double getIntersectionDistanceFromPoint(MapPosition mapPosition, Point point) {
        int row = mapPosition.getRow();
        int column = mapPosition.getColumn();
    
        int intersectionX = column * 150;
        int intersectionY = -row * 64 + 890;
        if ((row == 0 || row == 11) && column <= 2) {
            intersectionX += 600;
        } else if ((row == 1 || row == 2 || row == 9 || row == 10) && column <= 3) {
            intersectionX += 525;
        } else if ((row == 3 || row == 4 || row == 7 || row == 8) && column <= 4) {
            intersectionX += 450;
        } else if ((row == 5 || row == 6) && column <= 5) {
            intersectionX += 375;
        }
    
        return Math.sqrt((intersectionX - point.x) * (intersectionX - point.x) + (intersectionY - point.y) * (intersectionY - point.y));
    }

    public MapPosition findIntersectionPosition(Intersection knownIntersection) {
        MapPosition foundPosition = new MapPosition();

        for (int i = 0; i < this.getNumberOfRows(); i++) {
            for (int j = 0; j < this.getNumberOfIntersectionsInRow(i); j++) {
                if (this.intersectionMap[i][j].equals(knownIntersection)) {
                    foundPosition.setPosition(i, j);
                    return foundPosition;
                }
            }
        }
        throw new InvalidIntersectionPositionException();
    }

    private boolean isNotValidPosition(MapPosition positionToTest) {
        int rowNumber = positionToTest.getRow();
        int colNumber = positionToTest.getColumn();
        return isNotValidRow(rowNumber) || colNumber < 0 || colNumber >= getNumberOfIntersectionsInRow(rowNumber);
    }

    private boolean isNotValidRow(int rowNumber) {
        return rowNumber < 0 || rowNumber >= getNumberOfRows();
    }

    private boolean isInTopHalf(MapPosition pos) {
        return pos.getRow() >= getNumberOfRows() / 2;
    }

    private boolean isInBottomHalf(MapPosition pos) {
        // changing < to <= produces equivalent code
        return pos.getRow() < getNumberOfRows() / 2;
    }

    private boolean isEven(int n) {
        return n % 2 == 0;
    }

    private boolean isOdd(int n) {
        return !isEven(n);
    }

    public int getNumberOfIntersectionsInRow(int row) {
        if (isNotValidRow(row)) {
            throw new InvalidIntersectionPositionException();
        }
        return this.intersectionMap[row].length;
    }

    public int getNumberOfRows() {
        return 12;
    }
}
