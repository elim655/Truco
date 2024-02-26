package com.backend.backend.test;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.backend.model.InvitationDTO;
import org.junit.jupiter.api.Test;

class InvitationDTOTest {

    @Test
    void testGetRoomId() {
        InvitationDTO invitationDTO = new InvitationDTO(1L, "JohnDoe");
        Long roomId = 1L;
        assertEquals(roomId, invitationDTO.getRoomId());
    }

    @Test
    void testGetSender() {
        InvitationDTO invitationDTO = new InvitationDTO(1L, "JohnDoe");
        String sender = "JohnDoe";
        assertEquals(sender, invitationDTO.getSender());
    }

    @Test
    void testSetRoomId() {
        InvitationDTO invitationDTO = new InvitationDTO(1L, "JohnDoe");
        Long roomId = 2L;
        invitationDTO.setRoomId(roomId);
        assertEquals(roomId, invitationDTO.getRoomId());
    }

    @Test
    void testSetSender() {
        InvitationDTO invitationDTO = new InvitationDTO(1L, "JohnDoe");
        String sender = "JaneDoe";
        invitationDTO.setSender(sender);
        assertEquals(sender, invitationDTO.getSender());
    }
}
