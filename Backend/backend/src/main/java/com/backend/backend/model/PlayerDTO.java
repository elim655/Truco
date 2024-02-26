package com.backend.backend.model;

public class PlayerDTO {
    private Long id;
    private String playerName;
    private boolean isHost;
    private boolean isReady;

    /**
     * Default constructor for PlayerDTO.
     */
    public PlayerDTO() {
    }

    /**
     * Gets the unique ID of the player.
     *
     * @return The player's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique ID of the player.
     *
     * @param id The player's ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the player.
     *
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the name of the player.
     *
     * @param playerName The player's name.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Checks if the player is the host of the game room.
     *
     * @return True if the player is the host; otherwise, false.
     */
    public boolean isHost() {
        return isHost;
    }

    /**
     * Sets whether the player is the host of the game room.
     *
     * @param host True if the player is the host; otherwise, false.
     */
    public void setHost(boolean host) {
        isHost = host;
    }

    /**
     * Checks if the player is ready to start the game.
     *
     * @return True if the player is ready; otherwise, false.
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * Sets whether the player is ready to start the game.
     *
     * @param ready True if the player is ready; otherwise, false.
     */
    public void setReady(boolean ready) {
        isReady = ready;
    }
}


