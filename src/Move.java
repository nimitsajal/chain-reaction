public class Move {
    private int rowPos;
    private int collPos;

    public int getRowPos() {
        return rowPos;
    }

    public int getCollPos() {
        return collPos;
    }

    public void setRowPos(int rowPos) {
        if (rowPos <= 0) {
            throw new IllegalArgumentException("rowPos must be >= 1");
        }
        this.rowPos = rowPos;
    }

    public void setCollPos(int collPos) {
        if (collPos <= 0) {
            throw new IllegalArgumentException("collPos must be >= 1");
        }
        this.collPos = collPos;
    }
}
