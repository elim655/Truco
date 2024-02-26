package Truco.com.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Truco.com.ChatAdapter;
import Truco.com.ChatMessage;

public class ChatAdapterTest {
    private ChatAdapter chatAdapter;
    private final String loggedInUser = "user1";
    private final String recipientUser = "user2";

    @Before
    public void setUp() {
        chatAdapter = new ChatAdapter(loggedInUser, recipientUser, "direct");
    }

    @Test
    public void testItemCount() {
        chatAdapter.addMessage(new ChatMessage(loggedInUser, "Hello"));
        chatAdapter.addMessage(new ChatMessage(recipientUser, "Hi"));
        assertEquals(2, chatAdapter.getItemCount());
    }

}
