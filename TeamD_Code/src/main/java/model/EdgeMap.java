package model;

import java.util.ArrayList;

import exception.*;

public class EdgeMap {
    private Edge[][] edges;

    public EdgeMap() {
        this.edges = new Edge[11][];

        for (int i = 1; i < getNumberOfRows(); i += 2) {
            int length = 4 + ((4 - Math.abs(5 - i)) / 2);
            this.edges[i] = new Edge[length];

            for (int j = 0; j < length; j++) {
                this.edges[i][j] = new Edge();
            }
        }

        // changing < to <= in for loop produces equivalent code
        for (int i = 0; i < 11; i += 2) {
            int length = 10 - (Math.abs(5 - i) - 1);
            this.edges[i] = new Edge[length];

            for (int j = 0; j < length; j++) {
                this.edges[i][j] = new Edge();
            }
        }
    }

    public ArrayList<MapPosition> getAllAdjacentIntersections(Edge givenEdge) {
        ArrayList<MapPosition> intersectionPositions = new ArrayList<MapPosition>();
        try {
            intersectionPositions.add(getIntersectionDirection0(givenEdge));
            intersectionPositions.add(getIntersectionDirection1(givenEdge));
        } catch (InvalidEdgePositionException e) {
            return new ArrayList<MapPosition>();
        }
        return intersectionPositions;
    }

    public ArrayList<MapPosition> getAllAdjacentIntersections(MapPosition edgePosition) {
        ArrayList<MapPosition> intersectionPositions = new ArrayList<MapPosition>();
        intersectionPositions.add(getIntersectionDirection0(edgePosition));
        intersectionPositions.add(getIntersectionDirection1(edgePosition));
        return intersectionPositions;
    }

    public MapPosition getAdjacentIntersection(Edge givenEdge, Direction givenDirection) {
        switch (givenDirection) {
        case ZERO:
            return getIntersectionDirection0(givenEdge);
        case ONE:
            return getIntersectionDirection1(givenEdge);
        default:
            throw new IllegalArgumentException();
        }
    }

    public MapPosition getIntersectionDirection0(Edge givenEdge) {
        MapPosition edgePosition = this.findEdgePosition(givenEdge);
        return getIntersectionDirection0(edgePosition);
    }

    public MapPosition getIntersectionDirection0(MapPosition edgePosition) {
        if (isNotValidPosition(edgePosition)) {
            throw new InvalidEdgePositionException();
        }

        MapPosition intersectionPos = new MapPosition();
        int row = edgePosition.getRow();
        int col = edgePosition.getColumn();

        if (isOdd(row)) {
            intersectionPos.setPosition(row + 1, col);
        } else if (isInBottomHalf(edgePosition) && isOdd(col)) {
            intersectionPos.setPosition(row + 1, col / 2 + 1);
        } else {
            intersectionPos.setPosition(row + 1, col / 2);
        }

        return intersectionPos;
    }

    public MapPosition getIntersectionDirection1(Edge givenEdge) {
        MapPosition edgePosition = this.findEdgePosition(givenEdge);
        return getIntersectionDirection1(edgePosition);
    }

    public MapPosition getIntersectionDirection1(MapPosition edgePosition) {
        if (isNotValidPosition(edgePosition)) {
            throw new InvalidEdgePositionException();
        }

        MapPosition intersectionPos = new MapPosition();
        int row = edgePosition.getRow();
        int col = edgePosition.getColumn();

        if (isOdd(row)) {
            intersectionPos.setPosition(row, col);
        } else if (isInTopHalf(edgePosition) && isOdd(col)) {
            intersectionPos.setPosition(row, col / 2 + 1);
        } else {
            intersectionPos.setPosition(row, col / 2);
        }

        return intersectionPos;
    }

    public Edge getEdge(MapPosition position) {
        if (isNotValidPosition(position)) {
            throw new InvalidEdgePositionException();
        }
        return this.edges[position.getRow()][position.getColumn()];
    }

    public MapPosition findEdgePosition(Edge givenEdge) {
        MapPosition foundIndexes = new MapPosition();

        for (int i = 0; i < getNumberOfRows(); i++) {
            for (int j = 0; j < getNumberOfEdgesInRow(i); j++) {
                MapPosition current = new MapPosition(i, j);
                if (getEdge(current).equals(givenEdge)) {
                    foundIndexes.setPosition(i, j);
                    return foundIndexes;
                }
            }
        }
        throw new InvalidEdgePositionException();
    }

    public int getNumberOfEdgesInRow(int rowNumber) {
        if (isNotValidRow(rowNumber)) {
            throw new InvalidEdgePositionException();
        }
        return this.edges[rowNumber].length;
    }

    private boolean isNotValidPosition(MapPosition positionToTest) {
        int rowNumber = positionToTest.getRow();
        int colNumber = positionToTest.getColumn();
        return isNotValidRow(rowNumber) || colNumber < 0 || colNumber >= getNumberOfEdgesInRow(rowNumber);
    }

    private boolean isNotValidRow(int rowNumber) {
        return rowNumber < 0 || rowNumber >= getNumberOfRows();
    }

    public int getNumberOfRows() {
        return 11;
    }

    private boolean isInBottomHalf(MapPosition pos) {
        // changing <= to < produces equivalent code
        return pos.getRow() <= getNumberOfRows() / 2;
    }

    private boolean isInTopHalf(MapPosition pos) {
        // changing >= to > produces equivalent code
        return pos.getRow() >= getNumberOfRows() / 2;
    }

    private boolean isOdd(int n) {
        return n % 2 != 0;
    }
}
