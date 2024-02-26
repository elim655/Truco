package com.backend.backend.controller;

import com.backend.backend.model.*;
import com.backend.backend.repository.GameRoomRepository;
import com.backend.backend.repository.InvitationRepository;
import com.backend.backend.repository.PlayerRepository;
import com.backend.backend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller class for managing game rooms, players, and invitations.
 */
@RestController
@RequestMapping("/room")
@Tag(name = "Game Room API")
public class GameRoomController {
    @Autowired
    GameRoomRepository gameRoomRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    InvitationRepository invitationRepository;


    @Operation(summary = "Get game room info", description = "Get information about a specific game room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/{roomId}")
    public ResponseEntity<GameRoomInfoDTO> getGameRoomInfo(@PathVariable Long roomId) {
        Optional<GameRoom> roomOptional = gameRoomRepository.findById(roomId);

        if (roomOptional.isPresent()) {
            GameRoom room = roomOptional.get();
            List<Player> players = playerRepository.findByGameRoomId(roomId);
            List<String> playerNames = players.stream()
                    .map(Player::getPlayerName)
                    .collect(Collectors.toList());

            int totalPlayers = players.size();

            GameRoomInfoDTO response = new GameRoomInfoDTO(roomId, playerNames, totalPlayers);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get player info", description = "Get information about a specific player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/player/{playerName}")
    public ResponseEntity<PlayerDTO> getPlayerInfo(@PathVariable String playerName) {
        Optional<Player> playerOptional = Optional.ofNullable(playerRepository.findByPlayerName(playerName));

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();

            // Create a PlayerDTO and map the values
            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.setId(player.getId());
            playerDTO.setPlayerName(player.getPlayerName());
            playerDTO.setHost(player.isHost());
            playerDTO.setReady(player.isReady());

            return ResponseEntity.ok(playerDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

        @GetMapping("/invitation/{receiverName}")
        public ResponseEntity<List<InvitationDTO>> getInvitationFromReceiver(@PathVariable String receiverName) {
            List<Invitation> invitations = invitationRepository.findByuserName(receiverName);

            List<InvitationDTO> invitationDTOs = invitations.stream()
                    .map(invitation -> new InvitationDTO(
                            invitation.getRoom().getId(),
                            invitation.getSender().getUsername()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(invitationDTOs);
        }


    @Operation(summary = "Create game room", description = "Create a new game room with a specified player as a host")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/create/{playerName}/{maxPlayers}")
    public ResponseEntity<String> createGameRoom(@PathVariable String playerName, @PathVariable int maxPlayers) {
        Optional<Player> existingPlayer = Optional.ofNullable(playerRepository.findByPlayerName(playerName));

        if (existingPlayer.isPresent()) {
            Player player = existingPlayer.get();

            // Check if the player is not in any game room
            if (player.getGameRoom() == null) {
                GameRoom gameRoom = new GameRoom();
                gameRoom.setMaxPlayers(maxPlayers);

                // Save the room to the repository
                GameRoom savedRoom = gameRoomRepository.save(gameRoom);

                // Assign the player as the host of the room
                player.setHost(true);
                player.setGameRoom(savedRoom);
                player.setReady(true);
                playerRepository.save(player);

                Long roomId = savedRoom.getId();
                return ResponseEntity.ok("Game room created with ID: " + roomId + " and player " + playerName + " as the host.");
            } else {
                return ResponseEntity.badRequest().body("Player with ID " + playerName + " is already in a game room.");
            }
        } else {
            return ResponseEntity.badRequest().body("Player with ID " + playerName + " does not exist.");
        }
    }

    @Operation(summary = "Send invite", description = "Send an invitation to a player to join a specific game room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/{senderName}/{roomId}/invite/{playerName}")
    public ResponseEntity<String> sendInvite(@PathVariable Long roomId, @PathVariable String playerName, @PathVariable String senderName) {
        Optional<GameRoom> roomOptional = gameRoomRepository.findById(roomId);
        Optional<User> senderOptional = Optional.ofNullable(userRepository.findByUsername(senderName));
        Optional<User> receiverOptional = Optional.ofNullable(userRepository.findByUsername(playerName));

        if (roomOptional.isPresent() && senderOptional.isPresent() && receiverOptional.isPresent()) {
            GameRoom room = roomOptional.get();
            User sender = senderOptional.get();
            User receiver = receiverOptional.get();

            // Check if the room has available slots
            List<Player> players = playerRepository.findByGameRoomId(roomId);
            if (players.size() >= room.getMaxPlayers()) {
                return ResponseEntity.badRequest().body("Room is already full");
            }

            // Check if the player is already in another game room
            if(receiver.getPlayer().getGameRoom() != null){
                return ResponseEntity.badRequest().body("Player is already in another game room");
            }

            // Create and save a new Invitation entity with sender, room, and receiver associations
            Invitation invitation = new Invitation(room, receiver);
            receiver.setReceivedInvitations(invitation);
            invitation.setSender(sender);
            invitationRepository.save(invitation);

            return ResponseEntity.ok("Invitation sent to player with name " + playerName);
        } else {
            // Handle the case where the room or users do not exist
            return ResponseEntity.badRequest().body("Room, sender, or receiver not found");
        }
    }

    @Operation(summary = "Accept invite", description = "Accept an invitation to join a specific game room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/{receiver}/accept/{roomId}")
    @Transactional
    public ResponseEntity<String> acceptInvite(@PathVariable String receiver, @PathVariable Long roomId) {
        // Find the invitation based on the sender and receiver
        Optional<Invitation> invitationOptional = invitationRepository.findByUserNameAndRoomId(receiver, roomId);
        Optional<Player> playerOptional = Optional.ofNullable(playerRepository.findByPlayerName(receiver));

        if (invitationOptional.isPresent() && playerOptional.isPresent()) {
            Invitation invitation = invitationOptional.get();
            Player player = playerOptional.get();
            GameRoom room = invitation.getRoom();

            // Check if the room has available slots
            List<Player> players = playerRepository.findByGameRoomId(room.getId());
            if (players.size() >= room.getMaxPlayers()) {
                return ResponseEntity.badRequest().body("Room is already full");
            }

            // Create and save a new Player entity linked to the room
            player.setHost(false);
            player.setGameRoom(room);

            // Delete all invitations for the receiver
            invitationRepository.deleteByReceiver(player.getUser());

            return ResponseEntity.ok("Player with ID " + receiver + " has accepted the invitation and joined the room.");
        } else {
            // Handle the case where the invitation does not exist
            return ResponseEntity.badRequest().body("Invitation not found");
        }
    }

    @Operation(summary = "Decline invite", description = "Decline an invitation to join a specific game room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/invite/{receiver}/decline/{roomId}")
    public ResponseEntity<String> declineInvite(@PathVariable String receiver, @PathVariable Long roomId) {
        // Find the invitation based on the sender and receiver
        Optional<Invitation> invitationOptional = invitationRepository.findByUserNameAndRoomId(receiver, roomId);

        if (invitationOptional.isPresent()) {
            // Delete the invitation after declining
            invitationRepository.delete(invitationOptional.get());
            return ResponseEntity.ok("Player has declined the invitation.");
        } else {
            // Handle the case where the invitation does not exist
            return ResponseEntity.badRequest().body("Invitation not found");
        }
    }

    @Operation(summary = "Change ready status", description = "Change the ready status of a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/ready/{playerName}")
    public ResponseEntity<String> changeReadyStatus(@PathVariable String playerName) {
        // Retrieve the Player entity from the repository
        Optional<Player> playerOptional = Optional.ofNullable(playerRepository.findByPlayerName(playerName));

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();

            // Toggle the player's ready status
            player.setReady(!player.isReady());

            // Update the Player entity
            playerRepository.save(player);
            return ResponseEntity.ok("Player's ready status : " + player.isReady());
        }
        return ResponseEntity.badRequest().body("Player not found");
    }

    @Operation(summary = "Start game", description = "Start a game in a specific game room if conditions are met")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/{host}/start/{roomId}")
    public ResponseEntity<String> startGame(@PathVariable String host, @PathVariable Long roomId) {
        boolean gameStarted = false;
        // Retrieve the GameRoom entity from the repository
        Optional<GameRoom> roomOptional = gameRoomRepository.findById(roomId);
        Optional<Player> playerOptional = Optional.ofNullable(playerRepository.findByPlayerName(host));

        if (roomOptional.isPresent() & playerOptional.isPresent()) {
            GameRoom room = roomOptional.get();
            Player player = playerOptional.get();

            // Get the list of players in the room
            List<Player> players = playerRepository.findByGameRoomId(roomId);

            // Check if the room has enough players to start the game
            int requiredPlayers = room.getMaxPlayers();
            if (players.size() >= requiredPlayers) {
                // Check if all players are ready
                boolean allPlayersReady = players.stream().allMatch(Player::isReady);

                if (allPlayersReady) {
                    if(player.isHost()){
                        gameStarted = true;
                    }else{
                        return ResponseEntity.badRequest().body("User not Host");
                    }
                } else {
                    return ResponseEntity.badRequest().body("Not all players in the room are ready.");
                }
            } else {
                return ResponseEntity.badRequest().body("Not enough players to start the game.");
            }
        }
        if (gameStarted) {
            return ResponseEntity.ok("Game started.");
        } else {
            return ResponseEntity.badRequest().body("Game not started.");
        }
    }

    @Operation(summary = "Remove player", description = "Remove a player from a game room if conditions are met")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @DeleteMapping("/{roomId}/{host}/kick/{player}")
    public ResponseEntity<String> removePlayer(@PathVariable Long roomId, @PathVariable String host, @PathVariable String player) {
        boolean removed = false;

        // Retrieve the Player entity of the host from the repository
        Optional<Player> hostPlayerOptional = Optional.ofNullable(playerRepository.findByPlayerName(host));

        // Retrieve the Player entity of the player to be kicked from the repository
        Optional<Player> playerToKickOptional = Optional.ofNullable(playerRepository.findByPlayerName(player));

        // Retrieve the GameRoom entity
        Optional<GameRoom> roomOptional = gameRoomRepository.findById(roomId);

        if (hostPlayerOptional.isPresent() && playerToKickOptional.isPresent() && roomOptional.isPresent()) {
            Player hostPlayer = hostPlayerOptional.get();
            Player playerToKick = playerToKickOptional.get();
            GameRoom room = roomOptional.get();

            // Check if the requestor (host) is the host of the specified room
            if (hostPlayer.isHost() && room.equals(hostPlayer.getGameRoom())) {
                // Check if the player to be kicked is in the specified room
                if (room.equals(playerToKick.getGameRoom()) & !playerToKick.equals(hostPlayer)) {
                    // Remove the player
                    playerRepository.delete(playerToKick);
                    removed = true;
                }
            }
        }

        if (removed) {
            return ResponseEntity.ok("Player removed from the game room.");
        } else {
            return ResponseEntity.badRequest().body("Player removal failed.");
        }
    }

    @Operation(summary = "Leave game room", description = "Quit a game room by a player, potentially deleting the room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @DeleteMapping("/quit/{player}/{roomId}")
    public ResponseEntity<String> quitGameRoom(@PathVariable String player, @PathVariable Long roomId) {
        Optional<GameRoom> roomOptional = gameRoomRepository.findById(roomId);

        if (roomOptional.isPresent()) {
            GameRoom room = roomOptional.get();

            // Check if the player is in the specified room
            List<Player> playersInRoom = playerRepository.findByGameRoomId(roomId);
            Player playerToQuit = null;
            for (Player p : playersInRoom) {
                if (p.getPlayerName().equals(player)) {
                    playerToQuit = p;
                    break;
                }
            }

            if (playerToQuit != null) {
                if (playerToQuit.isHost()) {
                    // If the player is the host, remove all players from the room and delete it
                    for(Player p : playersInRoom){
                        p.reset();
                    }
                    gameRoomRepository.delete(room);
                    return ResponseEntity.ok("Room deleted due to host quitting.");
                } else {
                    // If the player is not the host, remove the player from the room
                    playerRepository.delete(playerToQuit);
                    playersInRoom.remove(playerToQuit);
                    if (playersInRoom.isEmpty()) {
                        // If there are no players left, delete the room
                        gameRoomRepository.delete(room);
                        return ResponseEntity.ok("Room deleted due to empty room.");
                    }
                    return ResponseEntity.ok("Player removed from the game room.");
                }
            }
        }

        return ResponseEntity.badRequest().body("Failed to quit the game room.");
    }

}

