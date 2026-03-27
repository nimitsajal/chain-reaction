public class Player {
    private int id;
    private TextColor textColor;
    private int occupiedCells;
    private boolean hasPlayed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TextColor getTextColor() {
        return textColor;
    }

    public void setTextColor(TextColor textColor) {
        this.textColor = textColor;
    }

    public int getOccupiedCells() {
        return occupiedCells;
    }

    public void setOccupiedCells(int occupiedCells) {
        this.occupiedCells = occupiedCells;
    }

    public boolean isHasPlayed() {
        return hasPlayed;
    }

    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }
}
