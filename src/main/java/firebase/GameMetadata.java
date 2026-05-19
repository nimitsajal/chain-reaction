package firebase;

public class GameMetadata {

    private String status;

    private long createdAt;

    private String hostPlayerId;

    private int gridSize;

    private int maxPlayers;

    private int currentPlayerIndex;

    public GameMetadata() {
    }

    public GameMetadata(
            String status,
            long createdAt,
            String hostPlayerId,
            int gridSize,
            int maxPlayers,
            int currentPlayerIndex
    ) {

        this.status = status;
        this.createdAt = createdAt;
        this.hostPlayerId = hostPlayerId;
        this.gridSize = gridSize;
        this.maxPlayers = maxPlayers;
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public String getStatus() {
        return status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getHostPlayerId() {
        return hostPlayerId;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
}