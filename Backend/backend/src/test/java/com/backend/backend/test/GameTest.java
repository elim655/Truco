package com.backend.backend.test;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.backend.model.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class GameTest {

    @Test
    void testGameDeck() {
        // Create a Game instance
        Game game = new Game();

        // Create a Deck
        Deck deck = new Deck();
        deck.setId(1L);

        // Set the deck for the game
        game.setDeck(deck);

        // Verify the deck associated with the game
        assertEquals(deck, game.getDeck());
    }

    @Test
    void testGamePlayers() {
        // Create a Game instance
        Game game = new Game();

        // Create players
        Player player1 = new Player();
        Player player2 = new Player();

        // Add players to the game
        game.setPlayers(List.of(player1, player2));

        // Verify the list of players in the game
        assertEquals(2, game.getPlayers().size());
    }

}
