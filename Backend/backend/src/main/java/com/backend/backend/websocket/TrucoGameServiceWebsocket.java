package com.backend.backend.websocket;

import com.backend.backend.controller.*;
import com.backend.backend.model.*;
import com.backend.backend.repository.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@ServerEndpoint("/trucogame/{gameId}")
@Component
public class TrucoGameServiceWebsocket implements ApplicationContextAware {

    private static final Logger logger = Logger.getLogger(TrucoGameServiceWebsocket.class.getName());
    private static ApplicationContext context;
    private Gson gson = new Gson();

    // Session management
    private static Map<Session, Long> sessionGameMap = new ConcurrentHashMap<>();
    private static Map<Long, Session> gameSessionMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        TrucoGameServiceWebsocket.context = applicationContext;
    }

    private TrucoGameService getTrucoGameService() {
        return context.getBean(TrucoGameService.class);
    }

    private UserRepository getUserRepository() {
        return context.getBean(UserRepository.class);
    }

    private GameRepository getGameRepository() {
        return context.getBean(GameRepository.class);
    }

    private PlayerRepository getPlayerRepository() {
        return context.getBean(PlayerRepository.class);
    }

    private CardRepository getCardRepository() {
        return context.getBean(CardRepository.class);
    }
    @OnOpen
    public void onOpen(Session session, @PathParam("gameId") Long gameId) {
        logger.info("New WebSocket connection: Game ID - " + gameId);

        // Associate the session with the game ID
        sessionGameMap.put(session, gameId);
        gameSessionMap.put(gameId, session);

        // Send a welcome message
        sendMessage(session, "Welcome to Truco Game " + gameId);
    }

    // Broadcast a message to all sessions in the same game
    private void broadcastMessage(String message, Long gameId) {
        sessionGameMap.forEach((session, storedGameId) -> {
            if (storedGameId.equals(gameId)) {
                sendMessage(session, message);
            }
        });
    }


    // Send a message to a specific session
    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.severe("Error sending message: " + e.getMessage());
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        JsonObject jsonMessage = JsonParser.parseString(message).getAsJsonObject();
        String action = jsonMessage.get("action").getAsString();

        switch (action) {
            case "playCard":
                handlePlayCard(session, jsonMessage);
                break;
            // Additional cases for other actions
            default:
                sendMessage(session, "Unknown action: " + action);
        }
    }

//    private void handleCompareCard(Session session, JsonObject jsonMessage) {
//        try {
//            // Extract the game ID from the JSON message
//            Long gameId = jsonMessage.get("gameId").getAsLong();
//
//            // Call the compareCard method in your game service
//            Card winningCard = trucoGameService.compareCard(gameId);
//
//            // You can send a response back to the client if needed
//            sendMessage(session, "Comparison completed. Winning card: " + winningCard.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Handle the exception and send an error message to the client if needed
//            sendMessage(session, "Error during card comparison: " + e.getMessage());
//        }
//    }


    private void handlePlayCard(Session session, JsonObject jsonMessage) {
        try {
            // Extract gameId from the session
            Long gameId = sessionGameMap.get(session);
            if (gameId == null) {
                sendMessage(session, "Game ID not found for this session.");
                return;
            }

            // Parse playerName and card details from the JSON message
            String playerName = jsonMessage.get("playerName").getAsString();
            JsonObject cardJson = jsonMessage.getAsJsonObject("card");
            Card card = gson.fromJson(cardJson, Card.class); // Assuming Card class is compatible with JSON structure

            // Get the TrucoGameService instance and call the playCard method
            TrucoGameService trucoGameService = getTrucoGameService();
            trucoGameService.playCard(gameId, playerName, card);

            // Notify the player that the card was played successfully
            broadcastGameStateUpdate(gameId, card);
            sendMessage(session, "Card played successfully by " + playerName);
        } catch (Exception e) {
            logger.severe("Error in handlePlayCard: " + e.getMessage());
            sendMessage(session, "Error processing play card: " + e.getMessage());
        }
    }





//    private void handleCallTruco(Session session, JsonObject jsonMessage) {
//        try {
//            Boolean desc = jsonMessage.get("desc").getAsBoolean();
//            Long gameId = jsonMessage.get("gameId").getAsLong();
//
//            // Implement the logic for calling Truco
//            // Example: trucoGameService.callTruco(desc, gameId);
//
//            // You can send a response back to the client if needed
//            sendMessage(session, "Truco called successfully");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @OnClose
    public void onClose(Session session) {
        Long gameId = sessionGameMap.get(session);
        sessionGameMap.remove(session);
        gameSessionMap.remove(gameId);

        logger.info("WebSocket connection closed: Game ID - " + gameId);
        // Broadcast a message to other sessions if needed
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.severe("WebSocket error for Game ID - " + sessionGameMap.get(session) + ": " + throwable.getMessage());
        // Optionally, send an error message back to the client
    }

    private void broadcastGameStateUpdate(Long gameId, Card playedCard) {
        // Create a JSON object representing the current game state
        JsonObject gameStateUpdate = new JsonObject();
        gameStateUpdate.addProperty("action", "gameStateUpdate");
        gameStateUpdate.addProperty("playedCard", gson.toJson(playedCard)); // Convert the card to JSON

        // Add any other game state information as needed

        // Broadcast the update to all players in the game
        broadcastMessage(gameStateUpdate.toString(), gameId);
    }


    private void broadcastGameState(Long gameId) {
        // Construct the game state message
        String gameStateMessage = constructGameStateMessage(gameId);

        // Broadcast the message to all sessions in the same game
        sessionGameMap.forEach((session, storedGameId) -> {
            if (storedGameId.equals(gameId)) {
                sendMessage(session, gameStateMessage);
            }
        });
    }

    private String constructGameStateMessage(Long gameId) {
        // Logic to construct a message with the current state of the game
        // This could involve fetching the game state from the database
        // and converting it to a JSON string, for example
        return "{...}"; // Replace with actual game state message
    }

}

