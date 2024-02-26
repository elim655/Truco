package com.backend.backend.test;

import com.backend.backend.model.FriendRequest;
import com.backend.backend.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FriendRequestTest {

    @Test
    void testFriendRequestProperties() {
        // Create a FriendRequest instance
        FriendRequest friendRequest = new FriendRequest();

        // Set properties
        friendRequest.setId(1L);

        // Create sender and receiver
        User sender = new User();
        User receiver = new User();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);

        friendRequest.setStatus("Pending");

        // Verify the properties
        assertEquals(1L, friendRequest.getId());
        assertEquals(sender, friendRequest.getSender());
        assertEquals(receiver, friendRequest.getReceiver());
        assertEquals("Pending", friendRequest.getStatus());
    }

    @Test
    void testFriendRequestId() {
        // Create a FriendRequest instance
        FriendRequest friendRequest = new FriendRequest();

        // Set and get the ID
        friendRequest.setId(2L);
        assertEquals(2L, friendRequest.getId());
    }

    @Test
    void testFriendRequestSender() {
        // Create a FriendRequest instance
        FriendRequest friendRequest = new FriendRequest();

        // Create and set the sender
        User sender = new User();
        friendRequest.setSender(sender);

        // Get and verify the sender
        assertEquals(sender, friendRequest.getSender());
    }

    @Test
    void testFriendRequestReceiver() {
        // Create a FriendRequest instance
        FriendRequest friendRequest = new FriendRequest();

        // Create and set the receiver
        User receiver = new User();
        friendRequest.setReceiver(receiver);

        // Get and verify the receiver
        assertEquals(receiver, friendRequest.getReceiver());
    }

    @Test
    void testFriendRequestStatus() {
        // Create a FriendRequest instance
        FriendRequest friendRequest = new FriendRequest();

        // Set and get the status
        friendRequest.setStatus("Accepted");
        assertEquals("Accepted", friendRequest.getStatus());
    }

}
