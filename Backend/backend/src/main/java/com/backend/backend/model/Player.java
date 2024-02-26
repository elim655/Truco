package com.backend.backend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private GameRoom gameRoom;

    private String playerName;
    private boolean isHost;
    private boolean isReady = false;
    private int points;

    private int team;

    @OneToMany(mappedBy = "player")
    @JsonManagedReference
    private List<Card> cards;

    /**
     * Gets the list of cards held by the player.
     *
     * @return The list of cards held by the player.
     */
    public List<Card> getCards() {
        return cards;
    }

    @Operation(summary = "Set player cards", description = "Sets the cards for the player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    public Player() {

    }

    /**
     * Resets the player's properties, removing associations with the game room and host status.
     * This method is typically used to prepare a player for a new game.
     */
    public void reset() {
        this.gameRoom = null;
        this.isHost = false;
        this.isReady = false;
    }

    @Operation(summary = "Get player id", description = "Returns the id of the player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Long getId() {
        return id;
    }

    @Operation(summary = "Set player id", description = "Returns the id of the player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setId(Long id) {
        this.id = id;
    }

    @Operation(summary = "Get player game room", description = "Returns the game room the player is in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public GameRoom getGameRoom() {
        return gameRoom;
    }

    @Operation(summary = "Set game room", description = "Sets the game room for the player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setGameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    @Operation(summary = "Get player name", description = "Returns the name of the player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public String getPlayerName() {
        return playerName;
    }

    @Operation(summary = "Set player name", description = "Sets the name of the player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Operation(summary = "Game room host", description = "Returns true if the player is the host of the game room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public boolean isHost() {
        return isHost;
    }

    @Operation(summary = "Set game room host", description = "If player becomes host then sets to true")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setHost(boolean host) {
        isHost = host;
    }

    @Operation(summary = "Is player ready", description = "Returns true if player is ready else false")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public boolean isReady() {
        return isReady;
    }

    @Operation(summary = "Set if player is ready", description = "If player clicks ready then set ready to true")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setReady(boolean ready) {
        isReady = ready;
    }

    /**
     * Gets the user associated with the player.
     *
     * @return The user account associated with the player.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the player.
     *
     * @param user The user account to associate with the player.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the number of points scored by the player in the game.
     *
     * @return The player's points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the number of points scored by the player in the game.
     *
     * @param points The player's points.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Gets the game associated with the player.
     *
     * @return The game in which the player is participating.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the game associated with the player.
     *
     * @param game The game in which the player is participating.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }
}
