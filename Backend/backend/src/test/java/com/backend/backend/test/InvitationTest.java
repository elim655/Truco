package com.backend.backend.test;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.backend.model.GameRoom;
import com.backend.backend.model.Invitation;
import com.backend.backend.model.User;
import org.junit.jupiter.api.Test;

class InvitationTest {

    @Test
    void testGetId() {
        Invitation invitation = new Invitation();
        Long invitationId = 1L;
        invitation.setId(invitationId);
        assertEquals(invitationId, invitation.getId());
    }

    @Test
    void testGetRoom() {
        Invitation invitation = new Invitation();
        GameRoom room = new GameRoom();
        invitation.setRoom(room);
        assertEquals(room, invitation.getRoom());
    }

    @Test
    void testGetReceiver() {
        Invitation invitation = new Invitation();
        User receiver = new User();
        invitation.setReceiver(receiver);
        assertEquals(receiver, invitation.getReceiver());
    }

    @Test
    void testGetUserName() {
        Invitation invitation = new Invitation();
        String userName = "JohnDoe";
        invitation.setUserName(userName);
        assertEquals(userName, invitation.getUserName());
    }

    @Test
    void testGetSender() {
        Invitation invitation = new Invitation();
        User sender = new User();
        invitation.setSender(sender);
        assertEquals(sender, invitation.getSender());
    }

    // Additional tests for other getter and setter methods...

    @Test
    void testSetRoom() {
        Invitation invitation = new Invitation();
        GameRoom room = new GameRoom();
        invitation.setRoom(room);
        assertEquals(room, invitation.getRoom());
    }

    @Test
    void testSetReceiver() {
        Invitation invitation = new Invitation();
        User receiver = new User();
        invitation.setReceiver(receiver);
        assertEquals(receiver, invitation.getReceiver());
    }

    @Test
    void testSetUserName() {
        Invitation invitation = new Invitation();
        String userName = "JaneDoe";
        invitation.setUserName(userName);
        assertEquals(userName, invitation.getUserName());
    }

    @Test
    void testSetSender() {
        Invitation invitation = new Invitation();
        User sender = new User();
        invitation.setSender(sender);
        assertEquals(sender, invitation.getSender());
    }
}
