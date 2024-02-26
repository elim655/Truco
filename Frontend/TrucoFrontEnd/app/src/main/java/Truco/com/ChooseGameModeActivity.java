package Truco.com;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import Truco.com.LobbyActivity;
import Truco.com.api.ApiClient;
import Truco.com.api.GameRoomAPI;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChooseGameModeActivity extends AppCompatActivity {

    private GameRoomAPI gameRoomAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game_mode);

        // Initialize Retrofit instance
        gameRoomAPI = ApiClient.getClient().create(GameRoomAPI.class);

        String loggedInUsername = getIntent().getStringExtra("LOGGED_IN_USERNAME");

        Button btn1v1 = findViewById(R.id.button1v1);
        btn1v1.setOnClickListener(v -> createAndNavigateToLobby(2, loggedInUsername, "1v1"));

        Button btn2v2 = findViewById(R.id.button2v2);
        btn2v2.setOnClickListener(v -> createAndNavigateToLobby(4, loggedInUsername, "2v2"));

        Button btn3v3 = findViewById(R.id.button3v3);
        btn3v3.setOnClickListener(v -> createAndNavigateToLobby(6, loggedInUsername, "3v3"));
    }

    private void createAndNavigateToLobby(int maxPlayers, String username, String gameMode) {
        // Call the API to create a new game room
        Call<ResponseBody> call = gameRoomAPI.createGameRoom(username, maxPlayers);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Parse the response body to get the room ID
                        String roomId = response.body().string();
                        navigateToLobby(roomId, username, gameMode);
                    } catch (IOException e) {
                        Log.e("ChooseGameModeActivity", "Error parsing response body", e);
                    }
                } else {
                    // Handle error case
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("ChooseGameModeActivity", "Error Body: " + errorBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle the failure case
                String failureMessage = "Failed to create game room: " + t.getMessage();
                Log.e("ChooseGameModeActivity", failureMessage);
                Toast.makeText(ChooseGameModeActivity.this, failureMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToLobby(String roomIdMessage, String username, String gameMode) {
        Intent intent = new Intent(ChooseGameModeActivity.this, LobbyActivity.class);

        Pattern pattern = Pattern.compile("ID: (\\d+)");
        Matcher matcher = pattern.matcher(roomIdMessage);
        long roomId = -1;
        if (matcher.find()) {
            roomId = Long.parseLong(matcher.group(1));
        }

        if (roomId != -1) {
            intent.putExtra("ROOM_ID", roomId); // Send as a long extra
            intent.putExtra("LOGGED_IN_USERNAME", username);
            intent.putExtra("GAME_MODE", gameMode); // Add the game mode to the intent
            startActivity(intent);
        } else {
            // Handle the case where the room ID couldn't be extracted
            Toast.makeText(this, "Error: Could not extract room ID.", Toast.LENGTH_LONG).show();
        }
    }

}
