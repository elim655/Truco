package com.backend.backend.test;

import com.backend.backend.model.*;
import com.backend.backend.controller.*;
import com.backend.backend.repository.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testDeckProperties() {
        // Create a Deck instance
        Deck deck = new Deck();

        // Set and get the ID
        deck.setId(1L);
        assertEquals(1L, deck.getId());

        // Get and verify the cards (assuming you have a method to add cards to the deck)
        assertNotNull(deck.getCards());
    }
}
