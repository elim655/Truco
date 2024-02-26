package com.backend.backend.test;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.backend.model.GameRoom;
import com.backend.backend.model.Invitation;
import com.backend.backend.model.User;
import org.junit.jupiter.api.Test;


class GameRoomTest {

    @Test
    void testGameRoomProperties() {
        // Create a GameRoom instance
        GameRoom gameRoom = new GameRoom();

        // Set properties
        gameRoom.setId(1L);
        gameRoom.setMaxPlayers(3);
        gameRoom.setHostReady(true);

        // Verify the properties
        assertEquals(1L, gameRoom.getId());
        assertEquals(3, gameRoom.getMaxPlayers());
        assertTrue(gameRoom.isHostReady());
    }

//    @Test
//    void testGameRoomPlayers() {
//        // Create a GameRoom instance
//        GameRoom gameRoom = new GameRoom();
//
//        // Create players
//        Player player1 = new Player();
//        Player player2 = new Player();
//
//        // Add players to the game room
//        gameRoom.getPlayers().add(player1);
//        gameRoom.getPlayers().add(player2);
//
//        // Verify the number of players in the game room
//        assertEquals(2, gameRoom.getPlayers().size());
//    }

    @Test
    void testHostReadyStatus() {
        // Create a GameRoom instance
        GameRoom gameRoom = new GameRoom();

        // Initially, host should not be ready
        assertFalse(gameRoom.isHostReady());

        // Set the host ready status
        gameRoom.setHostReady(true);

        // Verify that the host is ready
        assertTrue(gameRoom.isHostReady());
    }
}
