package com.backend.backend.model;

import java.util.List;
import java.util.Objects;

/**
 * Represents a Data Transfer Object (DTO) for providing information about a game room.
 * This DTO is used to transfer game room details to clients.
 */
public class GameRoomInfoDTO {
    private Long roomId; // Unique identifier of the game room
    private List<String> playerNames; // List of player names in the game room
    private int totalPlayers; // Total number of players in the game room

    /**
     * Constructs a GameRoomInfoDTO with the specified parameters.
     *
     * @param roomId        The unique identifier of the game room.
     * @param playerNames   The list of player names in the game room.
     * @param totalPlayers The total number of players in the game room.
     */
    public GameRoomInfoDTO(Long roomId, List<String> playerNames, int totalPlayers) {
        this.roomId = roomId;
        this.playerNames = playerNames;
        this.totalPlayers = totalPlayers;
    }

    /**
     * Gets the unique identifier of the game room.
     *
     * @return The ID of the game room.
     */
    public Long getRoomId() {
        return roomId;
    }

    /**
     * Sets the unique identifier of the game room.
     *
     * @param roomId The ID of the game room.
     */
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    /**
     * Gets the list of player names in the game room.
     *
     * @return The list of player names.
     */
    public List<String> getPlayerNames() {
        return playerNames;
    }

    /**
     * Sets the list of player names in the game room.
     *
     * @param playerNames The list of player names.
     */
    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    /**
     * Gets the total number of players in the game room.
     *
     * @return The total number of players.
     */
    public int getTotalPlayers() {
        return totalPlayers;
    }

    /**
     * Sets the total number of players in the game room.
     *
     * @param totalPlayers The total number of players.
     */
    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRoomInfoDTO that = (GameRoomInfoDTO) o;
        return totalPlayers == that.totalPlayers &&
                Objects.equals(roomId, that.roomId) &&
                Objects.equals(playerNames, that.playerNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, playerNames, totalPlayers);
    }
}



