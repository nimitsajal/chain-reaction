package model;

public class Player {

    private int id;

    private String playerId;

    private String playerName;

    private TextColor textColor;

    private int joinOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public TextColor getTextColor() {
        return textColor;
    }

    public void setTextColor(TextColor textColor) {
        this.textColor = textColor;
    }

    public int getJoinOrder() {
        return joinOrder;
    }

    public void setJoinOrder(int joinOrder) {
        this.joinOrder = joinOrder;
    }
}