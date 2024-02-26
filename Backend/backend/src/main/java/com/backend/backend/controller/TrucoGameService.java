package com.backend.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.backend.backend.model.*;
import com.backend.backend.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TrucoGameService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Operation(summary = "Initalize a 1v1 game", description = "Creates a game between 2 players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Long initialize1v1Game(String player1, String player2) {
        Game game = new Game();
        game.setMode(Mode.ONEVONE);
        game.setDeck(new Deck(game));
        game.setState(State.NORMAL);
        game.setRound(1);
        game.setWinner(0);

        // Retrieve players from the repository
        Player p1 = playerRepository.findByPlayerName(player1);
        Player p2 = playerRepository.findByPlayerName(player2);

        if (p1 == null || p2 == null) {
            // Handle the case where either player1 or player2 is not found
            // You can return an error or handle it as appropriate for your application
            return null;
        }

        // Initialize the cards list for player 1 and player 2
        p1.setCards(new ArrayList<>());
        p2.setCards(new ArrayList<>());

        // Save players to generate their IDs
        playerRepository.save(p1);
        playerRepository.save(p2);

        Random rand = new Random();
        if (rand.nextInt(2) == 0) {
            game.setTurn(p1.getId());
        } else {
            game.setTurn(p2.getId());
        }

        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        game.setPlayers(players);
        p1.setGame(game);
        p2.setGame(game);
        gameRepository.save(game);

        // Draw and assign cards to player1 and player2
        for (int i = 0; i < 3; i++) {
            Card card1 = drawCard(game.getDeck().getId());
            p1.getCards().add(card1);
            card1.setPlayer(p1);
            cardRepository.save(card1);

            Card card2 = drawCard(game.getDeck().getId());
            p2.getCards().add(card2);
            card2.setPlayer(p2);
            cardRepository.save(card2);
        }
        playerRepository.save(p1);
        playerRepository.save(p2);
        return game.getId();
    }


    // Initialize 2v2 game
    public void initialize2v2Game(String player1, String player2, String player3, String player4) {

    }

    @Transactional
    public Card drawCard(Long deckId) {
        // Assuming deckRepository is your JPA repository for Deck
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new EntityNotFoundException("Deck not found"));

        if (deck.getCards().isEmpty()) {
            throw new IllegalStateException("Cannot draw from an empty deck.");
        }

        Card card = deck.getCards().remove(deck.getCards().size() - 1);
        card.setDeck(null); // If you want to dissociate the card from the deck
        cardRepository.save(card);

        // You would also need to save the state of the deck
        deckRepository.save(deck);

        return card;
    }

    @Operation(summary = "Play a card", description = "A player plays a card and it is removed from their hand, only if it is their turn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void playCard(Long gameId, String playerName, Card card) {
        // Retrieve the game state
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        Player currentPlayer = game.getPlayers().stream()
                .filter(player -> player.getPlayerName().equals(playerName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        if (cardRepository.cardsPlayed(game.getId()).size() >= 2) {
            throw new IllegalStateException("Both players already played a card.");
        }

        if (!Objects.equals(currentPlayer.getId(), game.getTurn())) {
            throw new IllegalStateException("It's not the player's turn");
        } else {
            // Perform actions when it's the player's turn
            card.setCardPlayed(true);
            card.setGame(game);
            card.setPlayer(currentPlayer);
            cardRepository.save(card);

            // Switch the turn to the other player
            Long otherPlayerId = game.getPlayers().stream()
                    .filter(player -> !player.getId().equals(currentPlayer.getId()))
                    .findFirst()
                    .map(Player::getId)
                    .orElseThrow(() -> new IllegalStateException("Other player not found"));

            game.setTurn(otherPlayerId); // Set the turn to the other player's ID

            //if after a player plays a card and their and becomes empty then deal them 3 new cards from the deck
            if(cardRepository.findCardsByPlayerIdAndGameId(currentPlayer.getId(), gameId).isEmpty()){
                for(int i = 0; i < 3; i++){
                    Card newCard = drawCard(game.getDeck().getId());
                    List<Card> cards = currentPlayer.getCards();
                    cards.add(newCard); // Now this should not throw NullPointerException
                    newCard.setPlayer(currentPlayer);
                    cardRepository.save(newCard);
                }
            }

            // Save the changes to the game and player
            gameRepository.save(game);
        }
    }

    public Card compareCard(Long gameID) {
        List<Card> cards = cardRepository.cardsPlayed(gameID);
        Game game = gameRepository.findById((long) gameID)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        if (cards.size() < 2) {
            throw new IllegalStateException("Not enough cards played to compare.");
        }

        int round = game.getRound() + 1;
        game.setRound(round);

        if (cards.get(0).getValue() > cards.get(1).getValue()) {
            int points = cards.get(0).getPlayer().getPoints();
            points += awardPoints( gameID);

            cards.get(0).getPlayer().setPoints(points);

            cardRepository.delete(cards.get(0));
            cardRepository.delete(cards.get(1));
            game.setState(State.NORMAL);
            return cards.get(0);
        } else {
            int points = cards.get(1).getPlayer().getPoints();
            points += awardPoints( gameID);

            cards.get(1).getPlayer().setPoints(points);

            cardRepository.delete(cards.get(0));
            cardRepository.delete(cards.get(1));
            game.setState(State.NORMAL);


            return cards.get(1);
        }
    }

    public void callFlor(Long gameID,String player){
        List<Card> cards = cardRepository.findCardsByPlayerIdAndGameId(playerRepository.findByPlayerName(player).getId(), gameID);

        if (cards.get(0).getSuit().equals(cards.get(1).getSuit()) && cards.get(0).getSuit().equals(cards.get(2).getSuit())){
            int points = cards.get(0).getPlayer().getPoints();
            points +=4;
            cards.get(1).getPlayer().setPoints(points);
        }
        switchTurns(gameID);
    }

    private void switchTurns(Long gameId){
        Game game = gameRepository.findById((long) gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);

        if(Objects.equals(game.getTurn(), player1.getId())){
            game.setTurn(player2.getId());
        }else{
            game.setTurn(player1.getId());
        }
    }

    private void checkForWinner(Long gameID) {
        Game game = gameRepository.findById(gameID)
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }

    //check turn
    public void callTruco(Boolean desc, Long gameID) {
        Game game = gameRepository.findById((gameID))
                .orElseThrow(() -> new RuntimeException("Game not found"));
        if (desc) {
            game.setState(State.TRUCO);
            //winner gets 2 points
        } else {
            awardPoints(gameID);
        }
    }

    @Operation(summary = "Award points", description = "Determines how many points to give based off the current state of the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    private int awardPoints(Long gameID) {
        Game game = gameRepository.findById((gameID))
                .orElseThrow(() -> new RuntimeException("Game not found"));
        if(game.getState() == State.NORMAL){
            return 1;
        }
        if(game.getState() == State.TRUCO){
            return 2;
        }
        if(game.getState() == State.RETRUCO){
            return 3;
        }
        if(game.getState() == State.VALECUATRO){
            return 4;
        }
        if(game.getState() == State.ENVIDO){
            return 2;
        }
        if(game.getState() == State.REAL_ENVIDO){
            return 4;
        }
        return 0;
    }

    public void callReTruco(Boolean desc, Long gameID) {
        Game game = gameRepository.findById((gameID))
                .orElseThrow(() -> new RuntimeException("Game not found"));
        if (desc) {
            game.setState(State.RETRUCO);
            //winner gets 3 points
        } else {
            awardPoints(gameID);
        }
    }

    public void callValeQuatro(Boolean desc, Long gameID) {
        Game game = gameRepository.findById((gameID))
                .orElseThrow(() -> new RuntimeException("Game not found"));
        if (desc) {
            game.setState(State.VALECUATRO);
            //winner gets 4 points
        } else {
            awardPoints(gameID);
        }
    }

    public void callEnvido(Boolean desc, Long gameID) {
        Game game = gameRepository.findById((gameID))
                .orElseThrow(() -> new RuntimeException("Game not found"));
        //example player 1 calls envido meaning that you are saying you have matching suits
        // you can also bluff (pretending you have matching suits)
        //if player 2 says no to my envido then player one gets 1 points

        //if player 2 says yes calculate who has the higher envido
        //if he says yes the winnner of the calculation gets 2 points

        if (desc) {
            game.setState(State.ENVIDO);
        } else {
            awardPoints(gameID);
        }
        // real envido makes it worth double onlt if he says yes
        // so if he says no you still only get one point
        //if he says yes than the winner gets 4 points
    }

    public void callRealEnvido(Boolean desc, Long gameID){
        Game game = gameRepository.findById((gameID))
                .orElseThrow(() -> new RuntimeException("Game not found"));
    }

    /**
     * Deletes a game by its ID.
     *
     * @param gameId The ID of the game to be deleted.
     */
    public void deleteGame(Long gameId) {
        // Retrieve the game by its ID
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));

        // Delete the associated players
        List<Player> players = game.getPlayers();
        playerRepository.deleteAll(players);
        // Delete the game
        gameRepository.delete(game);
    }

    public Card getPlayedCard(Long gameId, Long playerID){
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));

        return cardRepository.cardPlayedby(gameId, playerID);
    }
}