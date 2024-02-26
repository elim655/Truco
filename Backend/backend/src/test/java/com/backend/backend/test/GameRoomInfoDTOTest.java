package com.backend.backend.test;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.backend.model.GameRoomInfoDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class GameRoomInfoDTOTest {

    @Test
    void testGetRoomId() {
        GameRoomInfoDTO gameRoomInfoDTO = new GameRoomInfoDTO(1L, Arrays.asList("JohnDoe", "JaneDoe"), 2);
        Long roomId = 1L;
        assertEquals(roomId, gameRoomInfoDTO.getRoomId());
    }

    @Test
    void testGetPlayerNames() {
        GameRoomInfoDTO gameRoomInfoDTO = new GameRoomInfoDTO(1L, Arrays.asList("JohnDoe", "JaneDoe"), 2);
        List<String> playerNames = Arrays.asList("JohnDoe", "JaneDoe");
        assertEquals(playerNames, gameRoomInfoDTO.getPlayerNames());
    }

    @Test
    void testGetTotalPlayers() {
        GameRoomInfoDTO gameRoomInfoDTO = new GameRoomInfoDTO(1L, Arrays.asList("JohnDoe", "JaneDoe"), 2);
        int totalPlayers = 2;
        assertEquals(totalPlayers, gameRoomInfoDTO.getTotalPlayers());
    }

    // Additional tests for other getter and setter methods...

    @Test
    void testSetRoomId() {
        GameRoomInfoDTO gameRoomInfoDTO = new GameRoomInfoDTO(1L, Arrays.asList("JohnDoe", "JaneDoe"), 2);
        Long roomId = 2L;
        gameRoomInfoDTO.setRoomId(roomId);
        assertEquals(roomId, gameRoomInfoDTO.getRoomId());
    }

    @Test
    void testSetPlayerNames() {
        GameRoomInfoDTO gameRoomInfoDTO = new GameRoomInfoDTO(1L, Arrays.asList("JohnDoe", "JaneDoe"), 2);
        List<String> playerNames = Arrays.asList("JaneDoe", "JimDoe");
        gameRoomInfoDTO.setPlayerNames(playerNames);
        assertEquals(playerNames, gameRoomInfoDTO.getPlayerNames());
    }

    @Test
    void testSetTotalPlayers() {
        GameRoomInfoDTO gameRoomInfoDTO = new GameRoomInfoDTO(1L, Arrays.asList("JohnDoe", "JaneDoe"), 2);
        int totalPlayers = 3;
        gameRoomInfoDTO.setTotalPlayers(totalPlayers);
        assertEquals(totalPlayers, gameRoomInfoDTO.getTotalPlayers());
    }
}
