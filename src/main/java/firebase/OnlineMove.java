package firebase;

public class OnlineMove {

    private String playerId;
    private int row;
    private int col;
    private long timestamp;

    public OnlineMove() {
    }

    public OnlineMove(
            String playerId,
            int row,
            int col,
            long timestamp
    ) {
        this.playerId = playerId;
        this.row = row;
        this.col = col;
        this.timestamp = timestamp;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public long getTimestamp() {
        return timestamp;
    }
}