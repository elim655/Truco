package Truco.com;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;

public class ChatWebSocketClient extends WebSocketClient {

   private MessageListener messageListener;

   public ChatWebSocketClient(URI serverUri, MessageListener messageListener) {
      super(serverUri);
      this.messageListener = messageListener;
   }

   @Override
   public void onOpen(ServerHandshake handshakedata) {
      if (messageListener != null) {
         messageListener.onConnect();
      }
   }

   @Override
   public void onMessage(String message) {
      if (messageListener != null) {
         messageListener.onMessageReceived(message);
      }
   }

   @Override
   public void onClose(int code, String reason, boolean remote) {
      if (messageListener != null) {
         messageListener.onDisconnect(reason);
      }
   }

   @Override
   public void onError(Exception ex) {
      if (messageListener != null) {
         messageListener.onError(ex);
      }
   }

   public interface MessageListener {
      void onConnect();
      void onMessageReceived(String message);
      void onDisconnect(String reason);
      void onError(Exception ex);
   }
}
