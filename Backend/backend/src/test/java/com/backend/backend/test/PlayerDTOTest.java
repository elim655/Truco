package com.backend.backend.test;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.backend.model.PlayerDTO;
import org.junit.jupiter.api.Test;

class PlayerDTOTest {

    @Test
    void testGetId() {
        PlayerDTO playerDTO = new PlayerDTO();
        Long playerId = 1L;
        playerDTO.setId(playerId);
        assertEquals(playerId, playerDTO.getId());
    }

    @Test
    void testGetPlayerName() {
        PlayerDTO playerDTO = new PlayerDTO();
        String playerName = "JohnDoe";
        playerDTO.setPlayerName(playerName);
        assertEquals(playerName, playerDTO.getPlayerName());
    }

    @Test
    void testIsHost() {
        PlayerDTO playerDTO = new PlayerDTO();
        boolean isHost = true;
        playerDTO.setHost(isHost);
        assertTrue(playerDTO.isHost());
    }

    @Test
    void testIsReady() {
        PlayerDTO playerDTO = new PlayerDTO();
        boolean isReady = true;
        playerDTO.setReady(isReady);
        assertTrue(playerDTO.isReady());
    }

    @Test
    void testSetPlayerName() {
        PlayerDTO playerDTO = new PlayerDTO();
        String playerName = "JaneDoe";
        playerDTO.setPlayerName(playerName);
        assertEquals(playerName, playerDTO.getPlayerName());
    }

    @Test
    void testSetHost() {
        PlayerDTO playerDTO = new PlayerDTO();
        boolean isHost = false;
        playerDTO.setHost(isHost);
        assertFalse(playerDTO.isHost());
    }

    @Test
    void testSetReady() {
        PlayerDTO playerDTO = new PlayerDTO();
        boolean isReady = false;
        playerDTO.setReady(isReady);
        assertFalse(playerDTO.isReady());
    }
}
