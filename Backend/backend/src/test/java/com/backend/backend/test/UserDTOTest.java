package com.backend.backend.test;

import com.backend.backend.model.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
    }

    @Test
    void testGetAndSetId() {
        Long id = 1L;
        userDTO.setId(id);
        assertEquals(id, userDTO.getId());
    }

    @Test
    void testGetAndSetUsername() {
        String username = "testUser";
        userDTO.setUsername(username);
        assertEquals(username, userDTO.getUsername());
    }

    @Test
    void testGetAndSetFriendUsername() {
        Set<String> friendUsernames = new HashSet<>();
        friendUsernames.add("friend1");
        friendUsernames.add("friend2");
        userDTO.setFriendUsername(friendUsernames);
        assertEquals(friendUsernames, userDTO.getFriendUsername());
    }

    @Test
    void testGetAndSetSentFriendRequest() {
        List<String> sentFriendRequests = List.of("request1", "request2");
        userDTO.setSentFriendRequest(sentFriendRequests);
        assertEquals(sentFriendRequests, userDTO.getSentFriendRequest());
    }

    @Test
    void testGetAndSetReceivedFriendRequest() {
        List<String> receivedFriendRequests = List.of("request1", "request2");
        userDTO.setReceivedFriendRequest(receivedFriendRequests);
        assertEquals(receivedFriendRequests, userDTO.getReceivedFriendRequest());
    }

    @Test
    void testGetAndSetReceivedInvitation() {
        List<Long> receivedInvitations = List.of(1L, 2L);
        userDTO.setReceivedInvitation(receivedInvitations);
        assertEquals(receivedInvitations, userDTO.getReceivedInvitation());
    }

    @Test
    void testSetAndGetId() {
        Long id = 123L;
        userDTO.setId(id);
        assertEquals(id, userDTO.getId());
    }

    @Test
    void testSetAndGetUsername() {
        String username = "JohnDoe";
        userDTO.setUsername(username);
        assertEquals(username, userDTO.getUsername());
    }

    @Test
    void testSetAndGetFriendUsername() {
        Set<String> friendUsernames = new HashSet<>();
        friendUsernames.add("Alice");
        friendUsernames.add("Bob");
        userDTO.setFriendUsername(friendUsernames);
        assertEquals(friendUsernames, userDTO.getFriendUsername());
    }

    @Test
    void testSetAndGetSentFriendRequest() {
        List<String> sentFriendRequests = List.of("Request1", "Request2");
        userDTO.setSentFriendRequest(sentFriendRequests);
        assertEquals(sentFriendRequests, userDTO.getSentFriendRequest());
    }

    @Test
    void testSetAndGetReceivedFriendRequest() {
        List<String> receivedFriendRequests = List.of("Request3", "Request4");
        userDTO.setReceivedFriendRequest(receivedFriendRequests);
        assertEquals(receivedFriendRequests, userDTO.getReceivedFriendRequest());
    }

    @Test
    void testSetAndGetReceivedInvitation() {
        List<Long> receivedInvitations = List.of(1L, 2L);
        userDTO.setReceivedInvitation(receivedInvitations);
        assertEquals(receivedInvitations, userDTO.getReceivedInvitation());
    }
}
