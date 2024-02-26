package Truco.com.model;

import java.util.List;

public class GameRoomInfoDTOModel {
    private Long roomId;
    private List<String> playerNames;
    private int totalPlayers;

    public GameRoomInfoDTOModel(Long roomId, List<String> playerNames, int totalPlayers) {
        this.roomId = roomId;
        this.playerNames = playerNames;
        this.totalPlayers = totalPlayers;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }
}


