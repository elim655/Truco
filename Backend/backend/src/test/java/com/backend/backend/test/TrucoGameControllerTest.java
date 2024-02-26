package com.backend.backend.test;

import com.backend.backend.controller.*;
import com.backend.backend.model.*;
import com.backend.backend.repository.GameRepository;
import com.backend.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrucoGameControllerTest {

    @Mock
    private TrucoGameService trucoGameService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private TrucoGameController trucoGameController;

    @Test
    void whenGetPlayerCardsWithValidPlayer_thenReturnsCards() {
        // Arrange
        String player = "Player1";
        Long gameId = 1L;
        Player foundPlayer = new Player();
        List<Card> cards = List.of(new Card());
        when(playerRepository.findByPlayerName(player)).thenReturn(foundPlayer);
        when(cardRepository.findCardsByPlayerIdAndGameId(foundPlayer.getId(), gameId)).thenReturn(cards);

        // Act
        ResponseEntity<List<Card>> response = trucoGameController.getPlayerCards(player, gameId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cards, response.getBody());
    }

    @Test
    void whenPlayCardWithValidData_thenCardIsPlayed() {
        // Arrange
        Long gameId = 1L;
        String playerName = "Player1";
        Card card = new Card();
        Player playerEntity = new Player();
        Game game = new Game();
        game.setId((long) gameId);
        playerEntity.setGame(game);

        when(playerRepository.findByPlayerName(playerName)).thenReturn(playerEntity);

        // Act
        ResponseEntity<?> response = trucoGameController.playCard(gameId, playerName, card);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trucoGameService).playCard(gameId, playerName, card);
    }

    @Test
    void whenCompareCard_thenCorrectCardIsReturned() {
        // Arrange
        Long gameID = 1L;
        Card card = new Card();
        when(trucoGameService.compareCard(gameID)).thenReturn(card);

        // Act
        Card result = trucoGameController.compareCard(gameID);

        // Assert
        assertEquals(card, result);
    }

}
