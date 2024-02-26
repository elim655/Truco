package Truco.com;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import Truco.com.api.ApiClient;
import Truco.com.api.GameRoomAPI;
import Truco.com.model.InvitationDTOModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvitationAdapter extends BaseAdapter {

    private Context context;
    private List<InvitationDTOModel> invitations;
    private LayoutInflater inflater;
    private GameRoomAPI apiService;
    private String loggedInUsername;

    public InvitationAdapter(Context context, List<InvitationDTOModel> invitations, String loggedInUsername) {
        this.context = context;
        this.invitations = invitations;
        this.inflater = LayoutInflater.from(context);
        this.apiService = ApiClient.getClient().create(GameRoomAPI.class);
        this.loggedInUsername = loggedInUsername;
    }

    @Override
    public int getCount() {
        return invitations.size();
    }

    @Override
    public InvitationDTOModel getItem(int position) {
        return invitations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.invitations_list_item, parent, false);
            holder.textViewSenderName = convertView.findViewById(R.id.tvInvitationSender);
            holder.buttonAccept = convertView.findViewById(R.id.btnAcceptInvitation);
            holder.buttonDecline = convertView.findViewById(R.id.btnRejectInvitation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final InvitationDTOModel invitation = getItem(position);
        holder.textViewSenderName.setText(invitation.getSender());
        holder.buttonAccept.setOnClickListener(v -> acceptInvitation(loggedInUsername, invitation.getRoomId()));
        holder.buttonDecline.setOnClickListener(v -> declineInvitation(position));

        return convertView;
    }

    private void acceptInvitation(String receiver, Long roomId) {

        Call<ResponseBody> call = apiService.acceptInvite(loggedInUsername, roomId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Intent lobbyIntent = new Intent(context, LobbyActivity.class);
                    lobbyIntent.putExtra("ROOM_ID", roomId);
                    lobbyIntent.putExtra("LOGGED_IN_USERNAME", loggedInUsername);
                    context.startActivity(lobbyIntent);
                    Toast.makeText(context, "Invitation accepted. Joining room...", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the case where the response is not successful
                    Toast.makeText(context, "Unable to join room: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Failed to accept invitation: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void declineInvitation(int position) {
        InvitationDTOModel invitation = getItem(position);
        apiService.declineInvite(loggedInUsername, invitation.getRoomId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    invitations.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Invitation declined", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        Toast.makeText(context, "Failed to decline invitation: " + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(context, "Failed to decline invitation: error while reading error message", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Error declining invitation: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    static class ViewHolder {
        TextView textViewSenderName;
        Button buttonAccept, buttonDecline;
    }
}
