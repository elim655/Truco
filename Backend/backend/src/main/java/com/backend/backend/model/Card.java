package com.backend.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.*;
import java.util.Objects;

/**
 * Represents a playing card in a card game.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Card {

    @Schema(name = "Suits", example = "SWORDS", required = true)
    public static final String[] SUITS = {"SWORDS", "CLUBS", "COINS", "CUPS"};

    @Schema(name = "Card ID", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "Card Number", example = "1", required = true)
    @Column(nullable = false)
    private int number;

    @Schema(name = "Suit", example = "SWORDS", required = true)
    @Column(nullable = false)
    private String suit;

    @Schema(name = "Card Value", example = "1", required = true)
    @Column(nullable = false)
    private int value;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @JsonBackReference
    private Player player;

    @Schema(name = "Deck")
    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @Schema(name = "Game")
    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Game game;

    @Schema(name = "Card Played", example = "true", required = true)
    private boolean cardPlayed;

    @Operation(summary = "Get if card is played", description = "Returns if the card has been played")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public boolean isCardPlayed() {
        return cardPlayed;
    }

    @Operation(summary = "Set card if played", description = "Sets card to true if played")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setCardPlayed(boolean cardPlayed) {
        this.cardPlayed = cardPlayed;
    }

    @Operation(summary = "Get game object", description = "Gets the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Game getGame() {
        return game;
    }

    @Operation(summary = "Set game", description = "Sets the game object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setGame(Game game) {
        this.game = game;
    }

    @Operation(summary = "Get player", description = "Retruns the player that has the card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Player getPlayer() {
        return player;
    }

    @Operation(summary = "Set player", description = "Sets player to a card meaning they have that card in their hand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Operation(summary = "Set card value", description = "Sets the value of the card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setSuit(String suit) {
        this.suit = suit;
    }

    @Operation(summary = "Set card number", description = "Sets card number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setNumber(int number) {
        this.number = number;
    }

    @Operation(summary = "Get deck", description = "Returns the deck associated with the card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Deck getDeck() {
        return deck;
    }

    @Operation(summary = "Set deck", description = "Sets deck to the card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    @Operation(summary = "Set card value", description = "Sets the value of the card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Creates a new Card object with the specified suit and number.
     *
     * @param suit   The suit of the card (e.g., "SWORDS", "CLUBS", etc.).
     * @param number The number or rank of the card.
     */
    public Card(String suit, int number){
        this.suit = suit;
        this.number = number;

        if (number == 1 && Objects.equals(suit, "SWORDS")) value =  1;
        if (number == 1 && Objects.equals(suit, "CLUBS")) value = 2;
        if (number == 7 && Objects.equals(suit, "SWORDS")) value = 3;
        if (number == 7 && Objects.equals(suit, "COINS")) value = 4;
        if (number == 3) value = 5;
        if (number == 2) value = 6;
        if ((number == 1 && (Objects.equals(suit, "CUPS") || Objects.equals(suit, "COINS")))) value = 7;
        if (number == 12) value = 8;
        if (number == 11) value = 9;
        if (number == 10) value = 10;
        if (number == 7 && (Objects.equals(suit, "CLUBS") || Objects.equals(suit, "CUPS"))) value = 11;
        if (number == 6) value = 12;
        if (number == 5) value = 13;
        if (number == 4) value = 14;
    }

    public Card() {
    }

    @Operation(summary = "Get suit", description = "Returns the suit of the card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public String getSuit() {
        return suit;
    }

    @Operation(summary = "Get value", description = "Returns the value of the card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public int getValue() {
        return value;
    }

    @Operation(summary = "Get number", description = "Gets the number of the card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public int getNumber() {
        return number;
    }

    @Operation(summary = "Set card id", description = "Sets the id for the card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setId(Long id) {
        this.id = id;
    }

    @Operation(summary = "Get card id", description = "Returns the id of the card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Long getId() {
        return id;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return Objects.equals(value, card.value) && Objects.equals(suit, card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }

}
