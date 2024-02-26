package Truco.com;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Activity representing the dashboard screen of the application.
 * This class is responsible for handling the user interactions on the dashboard,
 * including navigating to different parts of the game such as playing a game or adding friends.
 */
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import java.util.Arrays;
import Truco.com.api.SettingsApi;
import Truco.com.model.SettingsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity {
    /**
     * Called when the activity is starting.
     * This is where most initialization should go: calling setContentView(int) to inflate
     * the activity's UI, using findViewById(int) to programmatically interact with widgets in the UI,
     * registering listeners, etc.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        String loggedInUsername = getIntent().getStringExtra("LOGGED_IN_USERNAME");
        ImageButton btnFriends = findViewById(R.id.btn_friends);
        Button btnPlay = findViewById(R.id.btn_play);

        // Sets up a click listener for the play button
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, ChooseGameModeActivity.class);
                intent.putExtra("LOGGED_IN_USERNAME", loggedInUsername);
                startActivity(intent);
            }
        });

        // Sets up a click listener for the friends button
        ImageButton btnInvites = findViewById(R.id.btn_invites);

        btnInvites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, InvitationsActivity.class);
                intent.putExtra("LOGGED_IN_USERNAME", loggedInUsername);
                startActivity(intent);
            }
        });



        btnFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, AddFriend.class);
                intent.putExtra("LOGGED_IN_USERNAME", loggedInUsername);
                startActivity(intent);
            }
        });

        ImageButton btnSettings = findViewById(R.id.btn_settings);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingsDialog();
            }
        });
    }

    private void showSettingsDialog() {
        final Dialog settingsDialog = new Dialog(this);
        settingsDialog.setContentView(R.layout.settings_dialog);
        settingsDialog.show();

        SeekBar volumeSeekBar = settingsDialog.findViewById(R.id.volumeSeekBar);
        Spinner languageSpinner = settingsDialog.findViewById(R.id.languageSpinner);
        Switch allowSurrenderSwitch = settingsDialog.findViewById(R.id.allowSurrenderSwitch);
        allowSurrenderSwitch.setVisibility(View.GONE);
        Button saveButton = settingsDialog.findViewById(R.id.saveButton);
        Button cancelButton = settingsDialog.findViewById(R.id.cancelButton);

        SettingsApi settingsApi = RetrofitInstance.getRetrofitInstance().create(SettingsApi.class);

        Long userSettingsId = 1L;
        Call<SettingsModel> call = settingsApi.getSettings(userSettingsId);

        call.enqueue(new Callback<SettingsModel>() {
            @Override
            public void onResponse(Call<SettingsModel> call, Response<SettingsModel> response) {
                if (response.isSuccessful()) {
                    SettingsModel settings = response.body();
                    volumeSeekBar.setProgress(settings.getVolume());
                    allowSurrenderSwitch.setChecked(settings.isAllowSurrender());
                    String[] languages = {"English", "Spanish", "Portuguese", "Bosnian", "Malay"};

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(DashBoardActivity.this, android.R.layout.simple_spinner_item, languages);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    languageSpinner.setAdapter(spinnerAdapter);

                    int languagePosition = Arrays.asList(languages).indexOf(settings.getLanguage());
                    languageSpinner.setSelection(languagePosition);

                } else {
                    Log.e("RETROFIT_ERROR", response.message());
                    Toast.makeText(DashBoardActivity.this, "Error fetching settings. Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SettingsModel> call, Throwable t) {
                Toast.makeText(DashBoardActivity.this, "Network error.", Toast.LENGTH_SHORT).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsModel updatedSettings = new SettingsModel();
                updatedSettings.setVolume(volumeSeekBar.getProgress());
                updatedSettings.setLanguage((String) languageSpinner.getSelectedItem());
                updatedSettings.setAllowSurrender(allowSurrenderSwitch.isChecked());

                Call<SettingsModel> updateCall = settingsApi.updateSettings(userSettingsId, updatedSettings);
                updateCall.enqueue(new Callback<SettingsModel>() {
                    @Override
                    public void onResponse(Call<SettingsModel> call, Response<SettingsModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(DashBoardActivity.this, "Settings updated.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DashBoardActivity.this, "Error updating settings.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SettingsModel> call, Throwable t) {
                        Toast.makeText(DashBoardActivity.this, "Network error.", Toast.LENGTH_SHORT).show();
                    }
                });
                settingsDialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
            }
        });
    }
}
