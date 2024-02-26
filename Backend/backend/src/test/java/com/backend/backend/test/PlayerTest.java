package com.backend.backend.test;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.backend.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PlayerTest {

    @Test
    void testGetId() {
        Player player = new Player();
        Long playerId = 1L;
        player.setId(playerId);
        assertEquals(playerId, player.getId());
    }

    @Test
    void testGetGameRoom() {
        Player player = new Player();
        GameRoom gameRoom = new GameRoom();
        player.setGameRoom(gameRoom);
        assertEquals(gameRoom, player.getGameRoom());
    }

    @Test
    void testGetPlayerName() {
        Player player = new Player();
        String playerName = "JohnDoe";
        player.setPlayerName(playerName);
        assertEquals(playerName, player.getPlayerName());
    }

    @Test
    void testIsHost() {
        Player player = new Player();
        player.setHost(true);
        assertTrue(player.isHost());
    }

    @Test
    void testIsReady() {
        Player player = new Player();
        player.setReady(true);
        assertTrue(player.isReady());
    }

    @Test
    void testGetUser() {
        Player player = new Player();
        User user = new User();
        player.setUser(user);
        assertEquals(user, player.getUser());
    }

    @Test
    void testGetPoints() {
        Player player = new Player();
        int points = 100;
        player.setPoints(points);
        assertEquals(points, player.getPoints());
    }

    @Test
    void testGetGame() {
        Player player = new Player();
        Game game = new Game();
        player.setGame(game);
        assertEquals(game, player.getGame());
    }

    @Test
    void testGetTeam() {
        Player player = new Player();
        int team = 1;
        player.setTeam(team);
        assertEquals(team, player.getTeam());
    }

    @Test
    void testGetCards() {
        Player player = new Player();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card());
        player.setCards(cards);
        assertEquals(cards, player.getCards());
    }

    // Additional tests for other getter and setter methods...

    @Test
    void testSetGameRoom() {
        Player player = new Player();
        GameRoom gameRoom = new GameRoom();
        player.setGameRoom(gameRoom);
        assertEquals(gameRoom, player.getGameRoom());
    }

    @Test
    void testSetPlayerName() {
        Player player = new Player();
        String playerName = "JaneDoe";
        player.setPlayerName(playerName);
        assertEquals(playerName, player.getPlayerName());
    }

    @Test
    void testSetHost() {
        Player player = new Player();
        player.setHost(false);
        assertFalse(player.isHost());
    }

    @Test
    void testSetReady() {
        Player player = new Player();
        player.setReady(false);
        assertFalse(player.isReady());
    }

    @Test
    void testSetUser() {
        Player player = new Player();
        User user = new User();
        player.setUser(user);
        assertEquals(user, player.getUser());
    }

    @Test
    void testSetPoints() {
        Player player = new Player();
        int points = 200;
        player.setPoints(points);
        assertEquals(points, player.getPoints());
    }

    @Test
    void testSetGame() {
        Player player = new Player();
        Game game = new Game();
        player.setGame(game);
        assertEquals(game, player.getGame());
    }

    @Test
    void testSetTeam() {
        Player player = new Player();
        int team = 2;
        player.setTeam(team);
        assertEquals(team, player.getTeam());
    }

    @Test
    void testSetCards() {
        Player player = new Player();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card());
        player.setCards(cards);
        assertEquals(cards, player.getCards());
    }
}
