package Truco.com.test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Truco.com.ChatMessage;

public class ChatMessageTest {

    @Test
    public void testConstructorAndGetters() {
        String sender = "User1";
        String content = "Hello, World!";

        ChatMessage message = new ChatMessage(sender, content);

        assertEquals(sender, message.getSender());
        assertEquals(content, message.getContent());
    }

}
