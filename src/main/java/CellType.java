enum CellType {
    CORNER(2),
    EDGE(3),
    MIDDLE(4);

    private final int capacity;

    CellType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}