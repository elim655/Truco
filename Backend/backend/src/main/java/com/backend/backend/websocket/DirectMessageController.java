package com.backend.backend.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{username}/{receiver}")
@Component
public class DirectMessageController {

    private static Map<Session, String> sessionUsernameMap = new ConcurrentHashMap<>();
    private static Map<String, Session> usernameSessionMap = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(DirectMessageController.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        logger.info("Opened connection for user: " + username);
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("username") String sender, @PathParam("receiver") String receiver) {
        if (!usernameSessionMap.containsKey(receiver)) {
            try {
                session.getBasicRemote().sendText("User '" + receiver + "' not found.");
            } catch (IOException e) {
                logger.error("Error sending message: ", e);
            }
            return;
        }

        Session receiverSession = usernameSessionMap.get(receiver);
        try {
            receiverSession.getBasicRemote().sendText("[DM from " + sender + "]: " + message);
        } catch (IOException e) {
            logger.error("Error sending direct message: ", e);
        }
    }

    @OnClose
    public void onClose(Session session) {
        String username = sessionUsernameMap.get(session);
        logger.info("Closed connection for user: " + username);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("Error on connection: ", throwable);
    }
}
