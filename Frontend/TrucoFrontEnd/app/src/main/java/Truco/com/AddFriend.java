package Truco.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Truco.com.api.FriendRequestApi;
import Truco.com.model.UserDTOModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddFriend extends AppCompatActivity {

    private EditText editTextFriendUsername;
    private FriendRequestApi friendRequestApi;
    private List<String> friendList = new ArrayList<>();
    private FriendsAdapter adapter;
    private RecyclerView friendsRecyclerView;
    private List<String> pendingRequests = new ArrayList<>();
    private String loggedInUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        loggedInUsername = getIntent().getStringExtra("LOGGED_IN_USERNAME");

        editTextFriendUsername = findViewById(R.id.friendUsername);
        Button buttonAddFriend = findViewById(R.id.addFriendBtn);

        friendsRecyclerView = findViewById(R.id.friendsRecyclerView);
        adapter = new FriendsAdapter(friendList, pendingRequests);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendsRecyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://coms-309-059.class.las.iastate.edu:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        friendRequestApi = retrofit.create(FriendRequestApi.class);

        buttonAddFriend.setOnClickListener(v -> {
            String friendUsername = editTextFriendUsername.getText().toString().trim();
            if (validateInput(friendUsername)) {
                sendFriendRequest(friendUsername);
            }
        });

        fetchAndUpdateData();
    }

    private void fetchAndUpdateData() {
        Call<UserDTOModel> call = friendRequestApi.getUserByUsername(loggedInUsername);
        call.enqueue(new Callback<UserDTOModel>() {
            @Override
            public void onResponse(Call<UserDTOModel> call, Response<UserDTOModel> response) {
                if (response.isSuccessful()) {
                    friendList.clear();
                    pendingRequests.clear();
                    friendList.addAll(response.body().getFriendUsername());
                    pendingRequests.addAll(response.body().getReceivedFriendRequest());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AddFriend.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDTOModel> call, Throwable t) {
                Toast.makeText(AddFriend.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInput(String friendUsername) {
        if (friendUsername.isEmpty()) {
            editTextFriendUsername.setError("Friend's username is required");
            return false;
        }
        return true;
    }

    private void sendFriendRequest(String friendUsername) {
        Call<Void> call = friendRequestApi.sendFriendRequest(loggedInUsername, friendUsername);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddFriend.this, "Friend request sent to " + friendUsername, Toast.LENGTH_SHORT).show();
                    fetchAndUpdateData();  // Update data after sending a request
                } else {
                    Toast.makeText(AddFriend.this, "Failed to send friend request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddFriend.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void acceptFriendRequest(String requestUser) {
        Call<ResponseBody> call = friendRequestApi.acceptFriendRequest(loggedInUsername, requestUser);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseStr = response.body().string();
                        Toast.makeText(AddFriend.this, responseStr, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(AddFriend.this, "Failed to read response", Toast.LENGTH_SHORT).show();
                    }
                    fetchAndUpdateData();  // Update data after accepting a request
                } else {
                    Toast.makeText(AddFriend.this, "Failed to accept friend request", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddFriend.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void declineFriendRequest(String requestUser) {
        Call<ResponseBody> call = friendRequestApi.declineFriendRequest(loggedInUsername, requestUser);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseStr = response.body().string();
                        Toast.makeText(AddFriend.this, responseStr, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(AddFriend.this, "Failed to read response", Toast.LENGTH_SHORT).show();
                    }
                    fetchAndUpdateData();  // Update data after declining a request
                } else {
                    Toast.makeText(AddFriend.this, "Failed to decline friend request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddFriend.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void deleteFriend(String friendUsername) {
        Call<Void> call = friendRequestApi.deleteFriend(loggedInUsername, friendUsername);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddFriend.this, "Successfully removed " + friendUsername, Toast.LENGTH_SHORT).show();
                    fetchAndUpdateData();  // Update data after removing a friend
                } else {
                    Toast.makeText(AddFriend.this, "Failed to remove friend", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddFriend.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {

        private final List<String> friendList;
        private final List<String> pendingList;

        public FriendsAdapter(List<String> friendList, List<String> pendingList) {
            this.friendList = friendList;
            this.pendingList = pendingList;
        }

        @Override
        public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_item, parent, false);
            return new FriendViewHolder(itemView);
        }

        @Override
        public int getItemCount() {
            return friendList.size() + pendingList.size();
        }

        @Override
        public void onBindViewHolder(FriendViewHolder holder, int position) {
            if (position < friendList.size()) {
                String friend = friendList.get(position);
                holder.friendNameTextView.setText(friend);

                // For chatting with a friend
                holder.chatButton.setOnClickListener(v -> {
                    openChatWithFriend(friend);
                });

                holder.deleteButton.setOnClickListener(v -> {
                    deleteFriend(friend);
                });

                holder.chatButton.setVisibility(View.VISIBLE);
                holder.acceptButton.setVisibility(View.GONE);
                holder.declineButton.setVisibility(View.GONE);
                holder.deleteButton.setVisibility(View.VISIBLE);

            } else {
                String pendingFriend = pendingList.get(position - friendList.size());
                holder.friendNameTextView.setText(pendingFriend);
                holder.acceptButton.setOnClickListener(v -> {
                    acceptFriendRequest(pendingFriend);
                });
                holder.declineButton.setOnClickListener(v -> {
                    declineFriendRequest(pendingFriend);
                });
                holder.acceptButton.setVisibility(View.VISIBLE);
                holder.declineButton.setVisibility(View.VISIBLE);

                holder.chatButton.setVisibility(View.GONE);
                holder.deleteButton.setVisibility(View.GONE);
            }
        }

        private void openChatWithFriend(String friend) {
            Intent intent = new Intent(AddFriend.this, ChatActivity.class);

            intent.putExtra("LOGGED_IN_USERNAME", loggedInUsername);
            intent.putExtra("CHAT_TYPE", "direct");
            intent.putExtra("RECIPIENT_USERNAME", friend);

            startActivity(intent);
        }



        private class FriendViewHolder extends RecyclerView.ViewHolder {
            final TextView friendNameTextView;
            final Button chatButton;  // Added chat button
            final Button acceptButton;
            final Button declineButton;
            final Button deleteButton;

            FriendViewHolder(View itemView) {
                super(itemView);
                friendNameTextView = itemView.findViewById(R.id.friendNameTextView);
                chatButton = itemView.findViewById(R.id.chatButton);  // Added chat button
                acceptButton = itemView.findViewById(R.id.acceptButton);
                declineButton = itemView.findViewById(R.id.declineButton);
                deleteButton = itemView.findViewById(R.id.deleteButton);
            }
        }
    }
}

