package Truco.com;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;

public class GameRoomWebSocketClient extends WebSocketClient {

    private GameRoomListener gameRoomListener;

    public GameRoomWebSocketClient(URI serverUri, GameRoomListener gameRoomListener) {
        super(serverUri);
        this.gameRoomListener = gameRoomListener;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        if (gameRoomListener != null) {
            gameRoomListener.onConnect();
        }
    }

    @Override
    public void onMessage(String message) {
        if (gameRoomListener != null) {
            gameRoomListener.onMessageReceived(message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (gameRoomListener != null) {
            gameRoomListener.onDisconnect(reason);
        }
    }

    @Override
    public void onError(Exception ex) {
        if (gameRoomListener != null) {
            gameRoomListener.onError(ex);
        }
    }

    public void sendMessage(String message) {
        this.send(message);
    }

    public interface GameRoomListener {
        void onConnect();
        void onMessageReceived(String message);
        void onDisconnect(String reason);
        void onError(Exception ex);
    }
}
