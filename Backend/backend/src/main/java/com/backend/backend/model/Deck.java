package com.backend.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck of playing cards in the game.
 */
@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Deck ID")
    private Long id;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(name = "Card List")
    private List<Card> cards = new ArrayList<>();

    public Deck(){}


    public Deck(Game game) {
        // Initialize the deck with cards and associate them with the game and deck itself.
        for (String suit : Card.SUITS) {
            for (int number = 1; number <= 12; number++) {
                Card card = new Card(suit, number);
                cards.add(card);
                card.setGame(game);
                card.setDeck(this);
                card.setCardPlayed(false);
            }
        }
        shuffleDeck(); // Shuffle the deck after initializing it.
    }

    @Operation(summary = "Get cards", description = "Returns the cards in the deck")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public List<Card> getCards() {
        return cards;
    }

    @Operation(summary = "Get deck id", description = "Returns the id of the deck")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Long getId() {
        return id;
    }

    @Operation(summary = "Set deck id", description = "Sets the id of the deck")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setId(Long id) {
        this.id = id;
    }

    @Operation(summary = "Shuffles deck", description = "Randomly shuffles cards in deck so its random")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }
}

