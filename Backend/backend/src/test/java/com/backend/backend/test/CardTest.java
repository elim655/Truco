package com.backend.backend.test;

import com.backend.backend.model.*;
import com.backend.backend.controller.*;
import com.backend.backend.repository.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testCardProperties() {
        // Create a Card instance
        Card card = new Card();

        // Set and get the ID
        card.setId(1L);
        assertEquals(1L, card.getId());

        // Set and get the number, suit, and value
        card.setNumber(1);
        card.setSuit("SWORDS");
        card.setValue(10);

        assertEquals(1, card.getNumber());
        assertEquals("SWORDS", card.getSuit());
        assertEquals(10, card.getValue());

        // Test equals method
        Card anotherCard = new Card();
        anotherCard.setNumber(1);
        anotherCard.setSuit("SWORDS");
        anotherCard.setValue(10);

        assertEquals(card, anotherCard);
    }

    @Test
    void testCardGameAssociation() {
        // Create a Card instance
        Card card = new Card();

        // Create a Game instance
        Game game = new Game();

        // Associate the card with the game
        card.setGame(game);

        // Verify the association
        assertEquals(game, card.getGame());
    }

    @Test
    void testCardPlayerAssociation() {
        // Create a Card instance
        Card card = new Card();

        // Create a Player instance
        Player player = new Player();

        // Associate the card with the player
        card.setPlayer(player);

        // Verify the association
        assertEquals(player, card.getPlayer());
    }

    @Test
    void testCardDeckAssociation() {
        // Create a Card instance
        Card card = new Card();

        // Create a Deck instance
        Deck deck = new Deck();

        // Associate the card with the deck
        card.setDeck(deck);

        // Verify the association
        assertEquals(deck, card.getDeck());
    }
}
