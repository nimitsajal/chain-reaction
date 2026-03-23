public class Move {
    private int rowPos;
    private int colPos;

    public int getRowPos() {
        return rowPos;
    }

    public int getColPos() {
        return colPos;
    }

    public void setRowPos(int rowPos) {
        if (rowPos < 0) {
            throw new IllegalArgumentException("rowPos must be >= 0");
        }
        this.rowPos = rowPos;
    }

    public void setColPos(int colPos) {
        if (colPos < 0) {
            throw new IllegalArgumentException("colPos must be >= 0");
        }
        this.colPos = colPos;
    }
}
