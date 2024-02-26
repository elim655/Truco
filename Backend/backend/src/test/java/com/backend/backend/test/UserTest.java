package com.backend.backend.test;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.backend.model.User;
import com.backend.backend.model.FriendRequest;
import com.backend.backend.model.Invitation;
import com.backend.backend.model.Player;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testGetId() {
        User user = new User();
        Long userId = 1L;
        user.setId(userId);
        assertEquals(userId, user.getId());
    }

    @Test
    void testGetUsername() {
        User user = new User();
        String username = "john_doe";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    void testGetFriends() {
        User user = new User();
        User friend1 = new User();
        User friend2 = new User();

        user.getFriends().add(friend1);
        user.getFriends().add(friend2);

        assertTrue(user.getFriends().contains(friend1));
        assertTrue(user.getFriends().contains(friend2));
    }

    @Test
    void testGetSentFriendRequests() {
        User user = new User();
        FriendRequest friendRequest = new FriendRequest();

        user.getSentFriendRequests().add(friendRequest);

        assertTrue(user.getSentFriendRequests().contains(friendRequest));
    }

    @Test
    void testGetReceivedFriendRequests() {
        User user = new User();
        FriendRequest friendRequest = new FriendRequest();

        user.getReceivedFriendRequests().add(friendRequest);

        assertTrue(user.getReceivedFriendRequests().contains(friendRequest));
    }

    @Test
    void testGetReceivedInvitations() {
        User user = new User();
        Invitation invitation = new Invitation();

        user.getReceivedInvitations().add(invitation);

        assertTrue(user.getReceivedInvitations().contains(invitation));
    }

//    @Test
//    void testGetPlayer() {
//        User user = new User();
//        Player player = new Player();
//
//        user.setPlayer(player);
//
//        assertEquals(player, user.getPlayer());
//    }

    @Test
    void testGetPassword() {
        User user = new User();
        String password = "securePassword";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    // Similar tests for other getter and setter methods...

    @Test
    void testSetPassword() {
        User user = new User();
        String password = "securePassword";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }
}
