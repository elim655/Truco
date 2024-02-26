package Truco.com;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<ChatMessage> messages = new ArrayList<>();
    private String loggedInUsername;
    private String recipientUsername;
    private String chatType;

    public ChatAdapter(String loggedInUsername, String recipientUsername, String chatType) {
        this.loggedInUsername = loggedInUsername;
        this.recipientUsername = recipientUsername;
        this.chatType = chatType;
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        holder.senderTextView.setText(message.getSender());
        holder.contentTextView.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(ChatMessage message) {
        if ("direct".equals(chatType)) {
            // For DMs, only add if the message is from/to the loggedIn user
            if (message.getSender().equals(loggedInUsername) || message.getSender().equals(recipientUsername)) {
                messages.add(message);
                notifyDataSetChanged();
            }
        } else {
            // For chat room, exclude messages flagged as DM
            if (!message.getContent().startsWith("[DM from ")) {
                messages.add(message);
                notifyDataSetChanged();
            }
        }
    }


    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView senderTextView;
        TextView contentTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.senderTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
        }
    }
}
