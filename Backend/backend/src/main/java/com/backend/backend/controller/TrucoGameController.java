package com.backend.backend.controller;

import java.util.List;

import com.backend.backend.model.*;

import com.backend.backend.repository.CardRepository;
import com.backend.backend.repository.GameRepository;
import com.backend.backend.repository.PlayerRepository;
import com.backend.backend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling Truco game-related operations.
 */
@RestController
@RequestMapping("/truco")
@Tag(name = "Truco Game API")
public class TrucoGameController {

    @Autowired
    private TrucoGameService trucoGameService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    CardRepository cardRepository;

    @Operation(summary = "Start a 1v1 game", description = "Initialized a 1v1 truco game between 2 users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/newgame/1v1/{player1}/{player2}")
    public ResponseEntity<?> start1v1(@PathVariable String player1, @PathVariable String player2) {
        if (player1.equals(player2)) {
            return ResponseEntity.badRequest().body("Cannot start a game with the same player for both positions.");
        }

        User user1 = userRepository.findByUsername(player1);
        User user2 = userRepository.findByUsername(player2);

        if (user1 == null) {
            return ResponseEntity.badRequest().body("Player 1 doesn't exist.");
        } else if (user2 == null) {
            return ResponseEntity.badRequest().body("Player 2 doesn't exist.");
        }

        try {
            trucoGameService.initialize1v1Game(player1, player2);
            // Assuming the game object has an ID and other details you want to return
            return ResponseEntity.ok("New game created");
        } catch (Exception e) {
            e.printStackTrace(); // Not recommended for production, use a logger instead
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while starting the game.");
        }
    }

    @Operation(summary = "Get player cards", description = "Returns a list of cards a player currently has in a game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/cards/{gameID}/{player}")
    public ResponseEntity<List<Card>> getPlayerCards(@PathVariable String player, @PathVariable Long gameID) {
        try {
            Player foundPlayer = playerRepository.findByPlayerName(player);
            if (foundPlayer == null) {
                // Player not found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<Card> cards = cardRepository.findCardsByPlayerIdAndGameId(foundPlayer.getId(), gameID);
            if (cards.isEmpty()) {
                // No cards found for player in the specified game
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cards, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();  // Replace with a logger if available
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Play a card", description = "A player plays a card if it is their turn and they have that card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/playcard/{gameid}/{player}")
    public ResponseEntity<?> playCard(@PathVariable Long gameid, @PathVariable String player, @RequestBody Card card) {
        try {
            Player playerEntity = playerRepository.findByPlayerName(player);
            if (playerEntity == null) {
                return ResponseEntity.badRequest().body("Player not found");
            }
            if (playerEntity.getGame().getId() != (gameid)) {
                return ResponseEntity.badRequest().body("Player is not part of the game with the given ID");
            }

            trucoGameService.playCard(gameid, player, card);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Handle different types of exceptions differently as per your business logic.
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Determine round winner", description = "Compares the two cards played by both players and gives points to the winner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/comparecard/{gameid}")
    public Card compareCard(@PathVariable Long gameid) {
        return trucoGameService.compareCard(gameid);
    }

    @Operation(summary = "Get game id of player", description = "Returns the game id that is associated with a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/gameid/{player}")
    public Long getGameId(@PathVariable String player) {
        return playerRepository.findByPlayerName(player).getGame().getId();
    }
    @PostMapping("/callflor/{gameid}/{player}")
    public void callFlor(@PathVariable Long gameid, @PathVariable String player){
        trucoGameService.callFlor(gameid, player);
    }
    @PostMapping("/calltruco/{decision}/{gameID}")
    public void callTruco(@PathVariable Boolean decision, @PathVariable Long gameID){
        trucoGameService.callTruco(decision, gameID);
    }
    @PostMapping("/callretruco/{decision}/{gameID}")
    public void callRetruco(@PathVariable Boolean decision, @PathVariable Long gameID){
        trucoGameService.callReTruco(decision, gameID);
    }
    @PostMapping("callvalequatro/{decision}/{gameID}")
    public void callValeQuatro(@PathVariable Boolean decision, @PathVariable Long gameID){
        trucoGameService.callValeQuatro(decision, gameID);
    }

    @PostMapping("callenvido/{decision}/{gameID}")
    public void callEnvido(@PathVariable Boolean decision, @PathVariable Long gameID){
        trucoGameService.callEnvido(decision, gameID);
    }

    @PostMapping("callrealenvido/{decision}/{gameID}")
    public void callRealEnvido(@PathVariable Boolean decision, @PathVariable Long gameID){
        trucoGameService.callRealEnvido(decision, gameID);
    }

    @DeleteMapping("deletegame/{gameID}")
    public void deleteGame(@PathVariable Long gameID){
        trucoGameService.deleteGame(gameID);
    }

    @GetMapping("points/{player}")
    public int getPoints(@PathVariable String player){ return playerRepository.findByPlayerName(player).getPoints(); }
}
