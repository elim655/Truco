package Truco.com;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.net.URI;
import java.net.URISyntaxException;

public class ChatActivity extends AppCompatActivity implements ChatWebSocketClient.MessageListener {

    private ChatWebSocketClient client;
    private EditText editTextMessage;
    private Button buttonSend;
    private RecyclerView recyclerViewChat;
    private String loggedInUsername;
    private String chatType;
    private String recipientUsername;
    private ChatAdapter chatAdapter;

    // Method to set a custom WebSocket client (useful for testing)
    public void setWebSocketClient(ChatWebSocketClient client) {
        this.client = client;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        loggedInUsername = getIntent().getStringExtra("LOGGED_IN_USERNAME");
        chatType = getIntent().getStringExtra("CHAT_TYPE");
        recipientUsername = getIntent().getStringExtra("RECIPIENT_USERNAME");


        // Initialize UI components
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        recyclerViewChat = findViewById(R.id.recyclerViewChat);

        chatAdapter = new ChatAdapter(loggedInUsername, recipientUsername, chatType);
        recyclerViewChat.setAdapter(chatAdapter);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));


        // Initialize WebSocket Client
        try {
            URI uri = new URI("ws://coms-309-059.class.las.iastate.edu:8080/chat/" + loggedInUsername);
            client = new ChatWebSocketClient(uri, this);
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageContent = editTextMessage.getText().toString();
                if (!messageContent.isEmpty()) {
                    String formattedMessage = messageContent;

                    if ("direct".equals(chatType)) {
                        // Formatting the message to "@recipientUsername actualMessage" to match server logic
                        formattedMessage = "@" + recipientUsername + " " + messageContent;
                    }

                    client.send(formattedMessage);
                    editTextMessage.setText("");  // Clear the input box
                }
            }
        });

    }

    @Override
    public void onConnect() {
    }

    @Override
    public void onMessageReceived(String message) {
        // Simplified logic for adding the received message to the chat adapter

        final String sender = message.split(": ")[0];
        final String content = message.split(": ", 2)[1];

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatAdapter.addMessage(new ChatMessage(sender, content));
                recyclerViewChat.scrollToPosition(chatAdapter.getItemCount() - 1);
            }
        });
    }






    @Override
    public void onDisconnect(String reason) {
    }

    @Override
    public void onError(Exception ex) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (client != null) {
            client.close();
        }
    }
}
