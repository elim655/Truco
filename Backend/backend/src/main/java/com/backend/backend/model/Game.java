package com.backend.backend.model;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.*;

import java.util.List;

/**
 * Represents a game entity in the application.
 */
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Game ID")
    private Long id;

    @Column
    @Schema(name = "Game Mode")
    private Mode mode;

    @Column
    @Schema(name = "Game State")
    private State state;

    @Column(name = "turn")
    @Schema(name = "Players Turn")
    private Long turn;

    @Column(name = "round_number", nullable = false)
    @Schema(name = "Round Number")
    private int round;

    @Column(name = "winner_id")
    @Schema(name = "Winner")
    private Integer winner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deck_id", referencedColumnName = "id")
    @Schema(name = "Deck")
    private Deck deck;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "game", cascade = CascadeType.ALL)
    @Schema(name = "Players")
    private List<Player> players;

    /**
     * Gets the list of players participating in the game.
     *
     * @return The list of players in the game.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Sets the list of players participating in the game.
     *
     * @param players The list of players in the game.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Operation(summary = "Set game winner", description = "Sets the winner of the game if certain ammount of points is reached")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    @Operation(summary = "Get round number", description = "Returns the current round number of the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public int getRound() {
        return round;
    }

    @Operation(summary = "Set round number", description = "Sets the round number of the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setRound(int round) {
        this.round = round;
    }

    @Operation(summary = "Get mode of game", description = "Returns which mode the game is in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Mode getMode() {
        return mode;
    }

    @Operation(summary = "Sets game deck", description = "Sets the deck associated with the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    @Operation(summary = "Get game deck", description = "Returns the deck associated with the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Deck getDeck() {
        return deck;
    }

    @Operation(summary = "Set player turn", description = "Sets the players turn of the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setTurn(Long turn) {
        this.turn = turn;
    }

    @Operation(summary = "Get turn", description = "Returns the id of the player whos turn it is")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Long getTurn() {
        return turn;
    }

    @Operation(summary = "Get game winner", description = "Returns the winner of the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public int getWinner() {
        return winner;
    }

    @Operation(summary = "Set game winner", description = "Sets the player who wins the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setWinner(int winner) {
        this.winner = winner;
    }

    @Operation(summary = "Get game id", description = "Returns the id of the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Long getId() {
        return id;
    }

    @Operation(summary = "Set game id", description = "Sets the id for the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setId(Long id) {
        this.id = id;
    }

    @Operation(summary = "Set game mode", description = "Sets the mode of the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Operation(summary = "Set game state", description = "Sets the state that the game is in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setState(State state) {
        this.state = state;
    }

    @Operation(summary = "Get game state", description = "Returns the state the game is in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public State getState() {
        return state;
    }
}
