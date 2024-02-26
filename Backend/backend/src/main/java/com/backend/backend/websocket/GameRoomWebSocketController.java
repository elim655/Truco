package com.backend.backend.websocket;

import com.backend.backend.model.*;
import com.backend.backend.repository.GameRoomRepository;
import com.backend.backend.repository.InvitationRepository;
import com.backend.backend.repository.PlayerRepository;
import com.backend.backend.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ServerEndpoint("/room/{username}/{roomID}")
@Component
public class GameRoomWebSocketController implements ApplicationContextAware {
    private static ApplicationContext context;
    private static Map<Session, UserRoomKey> sessionUserRoomMap = new Hashtable<Session, UserRoomKey>();
    private static Map<UserRoomKey, Session> userRoomSessionMap = new Hashtable<UserRoomKey, Session>();

    private static final Logger logger = Logger.getLogger(GameRoomWebSocketController.class.getName());


    Gson gson = new Gson();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        GameRoomWebSocketController.context = applicationContext;
    }

    private GameRoomRepository getGameRoomRepository() {
        return context.getBean(GameRoomRepository.class);
    }

    private PlayerRepository getPlayerRepository() {
        return context.getBean(PlayerRepository.class);
    }

    private UserRepository getUserRepository() {
        return context.getBean(UserRepository.class);
    }

    private InvitationRepository getInvitationRepository() {
        return context.getBean(InvitationRepository.class);
    }


    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("roomID") Long roomID) {
        logger.info("New WebSocket connection: Username - " + username + ", Room ID - " + roomID);

        UserRoomKey userRoomKey = new UserRoomKey(username, roomID);

        // Check if the user-room combination is already connected
        if (userRoomSessionMap.containsKey(userRoomKey)) {
            sendErrorAndClose(session, "Error: Combination of Username '" + username + "' and Room ID '" + roomID + "' is already in use.");
        } else {
            sessionUserRoomMap.put(session, userRoomKey);
            userRoomSessionMap.put(userRoomKey, session);

            sendMessageOnOpen(session, "Welcome to Game Room " + roomID + ", " + username);
            broadcastToRoom(roomID, "User '" + username + "' has joined the game room.", session);
        }
    }

    private void broadcastToRoom(Long roomID, String message, Session excludeSession) {
        sessionUserRoomMap.forEach((s, userRoomKey) -> {
            if (userRoomKey.getRoomID().equals(roomID) && !s.equals(excludeSession)) {
                sendMessage(s, message);
            }
        });
    }

    private void sendMessageOnOpen(Session session, String message) {
        try {
            UserRoomKey key = sessionUserRoomMap.get(session);
            if (key != null) {
                session.getBasicRemote().sendText(message);
            } else {
                logger.severe("Session not found in sessionUserRoomMap");
            }
        } catch (IOException e) {
            logger.severe("Error sending message on open: " + e.getMessage());
        }
    }



    @OnMessage
    public void onMessage(Session session, String message) {

        JsonObject jsonMessage = JsonParser.parseString(message).getAsJsonObject();
        String action = jsonMessage.get("action").getAsString();

        switch (action) {
            case "getGameRoomInfo":
                handleGetGameRoomInfo(session, jsonMessage);
                break;
            case "changeReadyStatus":
                handleChangeReadyStatus(session, jsonMessage);
                break;
            case "startGame":
                handleStartGame(session, jsonMessage);
                break;
            case "removePlayer":
                handleRemovePlayer(session, jsonMessage);
                break;
            case "quitGameRoom":
                handleQuitGameRoom(session, jsonMessage);
                break;
            default:
                sendMessage(session, "Unknown action: " + action);
        }
    }

    private void handleGetGameRoomInfo(Session session, JsonObject jsonMessage) {
        Long roomId = jsonMessage.get("roomId").getAsLong();
        Optional<GameRoom> roomOptional = getGameRoomRepository().findById(roomId);

        if (roomOptional.isPresent()) {
            GameRoom room = roomOptional.get();
            List<Player> players = getPlayerRepository().findByGameRoomId(roomId);
            List<String> playerNames = players.stream()
                    .map(Player::getPlayerName)
                    .collect(Collectors.toList());

            int totalPlayers = players.size();

            GameRoomInfoDTO response = new GameRoomInfoDTO(roomId, playerNames, totalPlayers);
            String jsonResponse = gson.toJson(response);

            // Send JSON response back to client
            sendMessage(session, jsonResponse);
        } else {
            // Send an error message if room not found
            sendMessage(session, gson.toJson("Room not found."));
        }
    }


    private void handleChangeReadyStatus(Session session, JsonObject jsonMessage) {
        String playerName = jsonMessage.get("playerName").getAsString();
        Optional<Player> playerOptional = Optional.ofNullable(getPlayerRepository().findByPlayerName(playerName));

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();

            // Toggle the player's ready status
            player.setReady(!player.isReady());

            // Update the Player entity
            getPlayerRepository().save(player);
            String jsonResponse = gson.toJson(new SimpleResponse("Player's ready status : " + player.isReady()));
            sendMessage(session, jsonResponse);
        }else {
            String jsonResponse = gson.toJson(new SimpleResponse("Player not found"));
            sendMessage(session, jsonResponse);
        }
    }

    private void handleStartGame(Session session, JsonObject jsonMessage) {
        String host = jsonMessage.get("host").getAsString();
        Long roomId = jsonMessage.get("roomId").getAsLong();
        boolean gameStarted = false;
        // Retrieve the GameRoom entity from the repository
        Optional<GameRoom> roomOptional = getGameRoomRepository().findById(roomId);
        Optional<Player> playerOptional = Optional.ofNullable(getPlayerRepository().findByPlayerName(host));

        if (roomOptional.isPresent() & playerOptional.isPresent()) {
            GameRoom room = roomOptional.get();
            Player player = playerOptional.get();

            // Get the list of players in the room
            List<Player> players = getPlayerRepository().findByGameRoomId(roomId);

            // Check if the room has enough players to start the game
            int requiredPlayers = room.getMaxPlayers();
            if (players.size() >= requiredPlayers) {
                // Check if all players are ready
                boolean allPlayersReady = players.stream().allMatch(Player::isReady);

                if (allPlayersReady) {
                    if(player.isHost()){
                        gameStarted = true;
                    }else{
                        String jsonResponse = gson.toJson(new SimpleResponse("User not Host"));
                        sendMessage(session, jsonResponse);
                        return;
                    }
                } else {
                    String jsonResponse = gson.toJson(new SimpleResponse("Not all players in the room are ready."));
                    sendMessage(session, jsonResponse);
                    return;
                }
            } else {
                String jsonResponse = gson.toJson(new SimpleResponse("Not enough players to start the game."));
                sendMessage(session, jsonResponse);
                return;
            }
        }
        if (gameStarted) {
            String jsonResponse = gson.toJson(new SimpleResponse("Game started."));
            sendMessage(session, jsonResponse);
        } else {
            String jsonResponse = gson.toJson(new SimpleResponse("Game not started."));
            sendMessage(session, jsonResponse);
        }
    }

    private void handleRemovePlayer(Session session, JsonObject jsonMessage) {
        Long roomId = jsonMessage.get("roomId").getAsLong();
        String host = jsonMessage.get("host").getAsString();
        String playerToKickName = jsonMessage.get("player").getAsString();
        boolean removed = false;

        // Retrieve the Player entity of the host from the repository
        Optional<Player> hostPlayerOptional = Optional.ofNullable(getPlayerRepository().findByPlayerName(host));

        // Retrieve the Player entity of the player to be kicked from the repository
        Optional<Player> playerToKickOptional = Optional.ofNullable(getPlayerRepository().findByPlayerName(playerToKickName));

        // Retrieve the GameRoom entity
        Optional<GameRoom> roomOptional = getGameRoomRepository().findById(roomId);

        if (hostPlayerOptional.isPresent() && playerToKickOptional.isPresent() && roomOptional.isPresent()) {
            Player hostPlayer = hostPlayerOptional.get();
            Player playerToKick = playerToKickOptional.get();
            GameRoom room = roomOptional.get();

            // Check if the requester (host) is the host of the specified room
            if (hostPlayer.isHost() && room.equals(hostPlayer.getGameRoom())) {
                // Check if the player to be kicked is in the specified room
                if (room.equals(playerToKick.getGameRoom()) && !playerToKick.equals(hostPlayer)) {
                    // Remove the player
                    getPlayerRepository().delete(playerToKick);
                    removed = true;

                    // Close the WebSocket session of the player to be kicked
                    UserRoomKey userRoomKey = new UserRoomKey(playerToKickName, roomId);
                    Session playerSession = userRoomSessionMap.get(userRoomKey);
                    if (playerSession != null) {
                        try {
                            playerSession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Removed from game room"));
                        } catch (IOException e) {
                            logger.severe("Error closing WebSocket session for player: " + e.getMessage());
                        }
                    }
                }
            }
        }

        String responseMessage;
        if (removed) {
            responseMessage = "Player '" + playerToKickName + "' removed from the game room.";
        } else {
            responseMessage = "Player removal failed.";
        }

        String jsonResponse = gson.toJson(new SimpleResponse(responseMessage));
        sendMessage(session, jsonResponse);
    }


    private void handleQuitGameRoom(Session session, JsonObject jsonMessage) {
        String playerToQuitName = jsonMessage.get("player").getAsString();
        Long roomId = jsonMessage.get("roomId").getAsLong();
        Optional<GameRoom> roomOptional = getGameRoomRepository().findById(roomId);

        String responseMessage;

        if (roomOptional.isPresent()) {
            GameRoom room = roomOptional.get();

            // Find the player in the room
            Player playerToQuit = getPlayerRepository().findByPlayerNameAndGameRoomId(playerToQuitName, roomId);
            if (playerToQuit != null) {
                boolean isHost = playerToQuit.isHost();
                getPlayerRepository().delete(playerToQuit);

                // Check if the room is empty or the quitting player is the host
                if (isHost || getPlayerRepository().findByGameRoomId(roomId).isEmpty()) {
                    getGameRoomRepository().delete(room);
                    responseMessage = isHost ? "Room deleted due to host quitting." : "Room deleted due to empty room.";
                    broadcastToRoom(roomId, responseMessage, null);
                } else {
                    responseMessage = "Player '" + playerToQuitName + "' has quit the game room.";
                    broadcastToRoom(roomId, responseMessage, session);
                }

                // Close the WebSocket session of the quitting player
                UserRoomKey userRoomKey = new UserRoomKey(playerToQuitName, roomId);
                Session playerSession = userRoomSessionMap.get(userRoomKey);
                if (playerSession != null) {
                    try {
                        playerSession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Quitting game room"));
                    } catch (IOException e) {
                        logger.severe("Error closing WebSocket session for player: " + e.getMessage());
                    }
                }
            } else {
                responseMessage = "Player not found in the game room.";
            }
        } else {
            responseMessage = "Failed to quit the game room. Room not found.";
        }

        String jsonResponse = gson.toJson(new SimpleResponse(responseMessage));
        sendMessage(session, jsonResponse);
    }


    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.severe("Error sending message: " + e.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session) {
        UserRoomKey userRoomKey = sessionUserRoomMap.get(session);
        if (userRoomKey != null) {
            // Remove the user from the session maps
            sessionUserRoomMap.remove(session);
            userRoomSessionMap.remove(userRoomKey);

            // Log the disconnection
            logger.info("WebSocket connection closed: Username - " + userRoomKey.getUsername() + ", Room ID - " + userRoomKey.getRoomID());

            // Broadcast to other users in the same room that this user has left
            broadcastToRoom(userRoomKey.getRoomID(), "User '" + userRoomKey.getUsername() + "' has left the game room.", session);
        }
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        // Log the error with details
        UserRoomKey userRoomKey = sessionUserRoomMap.get(session);
        if (userRoomKey != null) {
            logger.severe("WebSocket error for Username - " + userRoomKey.getUsername() + ", Room ID - " + userRoomKey.getRoomID() + ": " + throwable.getMessage());
        } else {
            logger.severe("WebSocket error: " + throwable.getMessage());
        }

        // Optionally, send an error message back to the client
        try {
            session.getBasicRemote().sendText("An error occurred: " + throwable.getMessage());
        } catch (IOException e) {
            logger.severe("Error sending error message: " + e.getMessage());
        }
    }


    private void sendErrorAndClose(Session session, String errorMessage) {
        try {
            session.getBasicRemote().sendText(errorMessage);
            session.close();
        } catch (IOException e) {
            logger.severe("IOException on closing session: " + e.getMessage());
        }
    }
}
