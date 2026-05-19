package model;

public class Game {

    private String status;

    private long createdAt;

    private String hostPlayerId;

    private int gridSize;

    private int maxPlayers;

    private int currentPlayerIndex;

    public Game() {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setHostPlayerId(String hostPlayerId) {
        this.hostPlayerId = hostPlayerId;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
}