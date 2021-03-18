package model;

public class MapPosition {

    private int row;
    private int col;

    public MapPosition(int row, int column) {
        this.row = row;
        this.col = column;
    }

    public MapPosition() {
        this.row = -1;
        this.col = -1;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.col;
    }

    public void setPosition(int row, int column) {
        this.row = row;
        this.col = column;
    }

    @Override
    public boolean equals(Object pos) {
        if (pos != null && pos.getClass().equals(MapPosition.class)) {
            return (this.row == ((MapPosition) pos).row) && (this.col == ((MapPosition) pos).col);
        }
        return false;
    }

    @Override
    public int hashCode() {
        // changing this line produces equivalent code
        return 37 * (new Integer(this.row)).hashCode() + (new Integer(this.col)).hashCode();
    }

}
