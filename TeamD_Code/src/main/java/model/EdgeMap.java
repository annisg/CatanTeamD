package model;

import java.util.ArrayList;

import exception.*;
import gui.EdgeDirection;
import gui.EdgeGUI;

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

    public Edge getClosestEdge(int x, int y) {
        Edge closestEdge = edges[3][3];
        for(int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges.length; j++) {
                if(!isNotValidPosition(new MapPosition(i, j))) {
                    if(closestEdge == null) {
                        closestEdge = edges[i][j];
                    }
                    if (getRowColumnDistanceFromPoint(i, j, x, y) < getRowColumnDistanceFromPoint(findEdgePosition(closestEdge).getRow(), findEdgePosition(closestEdge).getColumn(), x, y)) {
                        closestEdge = edges[i][j];
                    }
                }
            }
        }
        return closestEdge;
    }

    private double getRowColumnDistanceFromPoint(int row, int column, int pointX, int pointY) {
        int edgeX = 0;
        int edgeY = 0;
        EdgeDirection direction;

        if (row % 2 != 0) {
            direction = EdgeDirection.UP;
        } else {
            if ((column % 2 == 0 && row < 5) || (column % 2 != 0 && row > 5)) {
                direction = EdgeDirection.LEFT;
            } else {
                direction = EdgeDirection.RIGHT;
            }
        }

        edgeX = column * 75;
        if (direction == EdgeDirection.UP) {
            edgeX *= 2;
        }

        if (row == 0 || row == 10) {
            edgeX += 565;
        } else if (row == 1 || row == 9) {
            edgeX += 525;
        } else if (row == 2 || row == 8) {
            edgeX += 485;
        } else if (row == 3 || row == 7) {
            edgeX += 450;
        } else if (row == 4 || row == 6) {
            edgeX += 410;
        } else if (row == 5) {
            edgeX += 375;
        }

        edgeY = -row * 65 + 865;

        return Math.sqrt((edgeX - pointX) * (edgeX - pointX) + (edgeY - pointY) * (edgeY - pointY));
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
