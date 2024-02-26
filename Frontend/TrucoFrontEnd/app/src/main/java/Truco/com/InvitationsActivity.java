package Truco.com;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Truco.com.api.GameRoomAPI;
import Truco.com.api.ApiClient;
import Truco.com.model.InvitationDTOModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvitationsActivity extends AppCompatActivity {

    private ListView listViewInvitations;
    private InvitationAdapter adapter;
    private String loggedInUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);

        listViewInvitations = findViewById(R.id.listViewInvitations);
        loggedInUsername = getIntent().getStringExtra("LOGGED_IN_USERNAME");

        fetchInvitations(loggedInUsername);
    }

    private void fetchInvitations(String receiverName) {
        GameRoomAPI apiService = ApiClient.getClient().create(GameRoomAPI.class);
        Call<List<InvitationDTOModel>> call = apiService.getInvitationsForReceiver(receiverName);

        call.enqueue(new Callback<List<InvitationDTOModel>>() {
            @Override
            public void onResponse(Call<List<InvitationDTOModel>> call, Response<List<InvitationDTOModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<InvitationDTOModel> invitations = response.body();
                    adapter = new InvitationAdapter(InvitationsActivity.this, invitations, loggedInUsername);
                    listViewInvitations.setAdapter(adapter);
                } else {
                    Toast.makeText(InvitationsActivity.this, "Failed to get invitations", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<InvitationDTOModel>> call, Throwable t) {
                Toast.makeText(InvitationsActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
