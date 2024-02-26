package com.backend.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a game room entity in the application where players can join and play games.
 */
@Entity
public class GameRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "gameRoom")
    @JsonIgnore
    private Set<Player> players = new HashSet<>();

    private int maxPlayers; // Maximum number of players allowed in the game room (1, 2, or 3)
    private boolean isHostReady; // Indicates if the host of the game room is ready to start the game

    /**
     * Default constructor for creating a new GameRoom instance.
     */
    public GameRoom() {
    }

    /**
     * Gets the unique identifier of the game room.
     *
     * @return The ID of the game room.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the game room.
     *
     * @param id The ID of the game room.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the set of players currently in the game room.
     *
     * @return The set of players in the game room.
     */
    public Set<Player> getPlayers() {
        return players;
    }

    /**
     * Sets the set of players currently in the game room.
     *
     * @param players The set of players in the game room.
     */
    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    /**
     * Gets the maximum number of players allowed in the game room (1, 2, or 3).
     *
     * @return The maximum number of players allowed in the game room.
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Sets the maximum number of players allowed in the game room (1, 2, or 3).
     *
     * @param maxPlayers The maximum number of players allowed in the game room.
     */
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    /**
     * Checks if the host of the game room is ready to start the game.
     *
     * @return true if the host is ready, false otherwise.
     */
    public boolean isHostReady() {
        return isHostReady;
    }

    /**
     * Sets the readiness status of the host in the game room.
     *
     * @param hostReady true if the host is ready, false otherwise.
     */
    public void setHostReady(boolean hostReady) {
        isHostReady = hostReady;
    }
}

