package Truco.com;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import Truco.com.api.ApiClient;
import Truco.com.api.GameRoomAPI;
import Truco.com.model.GameRoomInfoDTOModel;
import Truco.com.model.InvitationDTOModel;
import Truco.com.model.Login;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LobbyActivity extends AppCompatActivity {

    private GridLayout playerGrid;
    private Button startButton;
    private int maxPlayers = 2; // Default for 1v1
    private String loggedInUsername;
    private View currentPlayerSlot;
    private GameRoomAPI gameRoomAPI;
    private GameRoomWebSocketClient webSocketClient;


    private long roomId;

    private void startWebSocket() {
        try {
            URI serverUri = new URI("ws://coms-309-059.class.las.iastate.edu:8080/room/" + loggedInUsername + "/" + roomId);
            webSocketClient = new GameRoomWebSocketClient(serverUri, new GameRoomWebSocketClient.GameRoomListener() {
                @Override
                public void onConnect() {
                    Log.d("WebSocket", "Connected to the server");
                }

                @Override
                public void onMessageReceived(String message) {
                    Log.d("WebSocket", "Message received: " + message);
                }

                @Override
                public void onDisconnect(String reason) {
                    Log.d("WebSocket", "Disconnected from the server: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    Log.e("WebSocket", "Error: " + ex.getMessage());
                }

            });
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        gameRoomAPI = ApiClient.getClient().create(GameRoomAPI.class);

        roomId = getIntent().getLongExtra("ROOM_ID", -1);
        loggedInUsername = getIntent().getStringExtra("LOGGED_IN_USERNAME");
        String gameMode = getIntent().getStringExtra("GAME_MODE");
        maxPlayers = determineMaxPlayers(gameMode);

        playerGrid = findViewById(R.id.playerGrid);
        startButton = findViewById(R.id.startButton);

        fetchGameRoomInfo();

        startWebSocket();

        setupChatButton();
        setupQuitButton();

    }




    private int determineMaxPlayers(String gameMode) {
        switch (gameMode) {
            case "2v2":
                return 4;
            case "3v3":
                return 6;
            default:
                return 2; // Default to 1v1
        }
    }

    private void updatePlayerSlots(List<String> playerNames) {
        playerGrid.removeAllViews();

        for (int i = 0; i < maxPlayers; i++) {
            View playerSlot = getLayoutInflater().inflate(R.layout.player_slot, playerGrid, false);
            TextView username = playerSlot.findViewById(R.id.username);
            Button readyButton = playerSlot.findViewById(R.id.ready_button);
            Button inviteButton = playerSlot.findViewById(R.id.invite_button);

            if (i < playerNames.size() && playerNames.get(i) != null && !playerNames.get(i).isEmpty()) {
                username.setText(playerNames.get(i));
                readyButton.setVisibility(View.VISIBLE);
                inviteButton.setVisibility(View.GONE);
            } else {
                username.setText("Empty");
                readyButton.setVisibility(View.GONE);
                inviteButton.setVisibility(View.VISIBLE);

                setupInviteButton(inviteButton, roomId);
            }

            setupReadyButton(playerSlot, readyButton);
            playerGrid.addView(playerSlot);
        }
    }

    private void setupInviteButton(Button inviteButton, long roomId) {
        inviteButton.setOnClickListener(v -> {
            showInviteDialog(roomId);
        });
    }

    private void showInviteDialog(long roomId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invite Player");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Invite", null);
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();

        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setTextColor(ContextCompat.getColor(this, R.color.black));
        negativeButton.setTextColor(ContextCompat.getColor(this, R.color.black));

        positiveButton.setOnClickListener(v -> {
            String playerName = input.getText().toString();
            sendInviteToPlayer(roomId, playerName);
        });

        negativeButton.setOnClickListener(v -> dialog.cancel());
    }



    private void sendInviteToPlayer(long roomId, String playerName) {
        InvitationDTOModel invitationDTO = new InvitationDTOModel(roomId, loggedInUsername);

        Call<ResponseBody> call = gameRoomAPI.sendInvite(roomId, invitationDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LobbyActivity.this, "Invitation sent to " + playerName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LobbyActivity.this, "Failed to send invite. " + getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LobbyActivity.this, "Invitation failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getErrorMessage(Response<?> response) {
        try {
            return response.errorBody() != null ? response.errorBody().string() : "Unknown error";
        } catch (IOException e) {
            return "Error parsing error message";
        }
    }



    private void setupReadyButton(final View playerSlot, final Button readyButton) {
        readyButton.setOnClickListener(v -> {
            LayerDrawable layerDrawable = (LayerDrawable) playerSlot.getBackground();
            GradientDrawable background = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.background_color);
            if ("Ready".equals(readyButton.getText().toString())) {
                background.setColor(Color.parseColor("#90EE90")); // Light green color
                readyButton.setText("Cancel");
            } else {
                background.setColor(Color.parseColor("#FFCCCC")); // Light red color
                readyButton.setText("Ready");
            }
        });
    }


    private void setupChatButton() {
        Button chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener(view -> {
            Intent chatIntent = new Intent(LobbyActivity.this, ChatActivity.class);
            chatIntent.putExtra("LOGGED_IN_USERNAME", loggedInUsername);
            startActivity(chatIntent);
        });
    }

    private void setupQuitButton(){
        Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitGameRoom(loggedInUsername, roomId);
            }
        });
    }


    private void quitGameRoom(String player, long roomId) {
        Call<ResponseBody> call = gameRoomAPI.quitGameRoom(player, roomId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Quitting the room was successful
                    runOnUiThread(() -> {
                        // Close the LobbyActivity and return to the previous screen or main menu
                        finish();
                    });
                } else {
                    // Handle unsuccessful response, possibly by showing an error message
                    runOnUiThread(() -> {
                        // Show error to the user
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle the failure case
                runOnUiThread(() -> {
                    // Show error to the user
                });
            }
        });
    }




    private void fetchGameRoomInfo() {

        roomId = getIntent().getLongExtra("ROOM_ID", -1);
        if (roomId != -1) {
            // Use roomId to fetch game room info
            Call<GameRoomInfoDTOModel> call = gameRoomAPI.getGameRoomInfo(roomId);
            call.enqueue(new Callback<GameRoomInfoDTOModel>() {
                @Override
                public void onResponse(Call<GameRoomInfoDTOModel> call, Response<GameRoomInfoDTOModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        updatePlayerSlots(response.body().getPlayerNames());
                    } else {
                        // Handle unsuccessful response
                    }
                }

                @Override
                public void onFailure(Call<GameRoomInfoDTOModel> call, Throwable t) {
                    // Handle the failure case
                }
            });
        } else {
            // Room ID is invalid (-1), handle the error
            Log.e("LobbyActivity", "Invalid room ID");
            finish(); // or inform the user and handle the error appropriately
        }
    }



}
