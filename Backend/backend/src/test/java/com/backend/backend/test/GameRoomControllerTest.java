package com.backend.backend.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.backend.backend.controller.GameRoomController;
import com.backend.backend.model.*;
import com.backend.backend.repository.GameRoomRepository;
import com.backend.backend.repository.InvitationRepository;
import com.backend.backend.repository.PlayerRepository;
import com.backend.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;

@WebMvcTest(GameRoomController.class)
public class GameRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameRoomRepository gameRoomRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private InvitationRepository invitationRepository;

    @Test
    public void getGameRoomInfo() throws Exception {
        Long roomId = 1L;
        when(gameRoomRepository.findById(roomId)).thenReturn(Optional.of(new GameRoom()));

        mockMvc.perform(get("/room/" + roomId))
                .andExpect(status().isOk());
    }


    @Test
    public void declineInvite() throws Exception {
        String receiver = "receiverName";
        Long roomId = 1L;
        when(invitationRepository.findByUserNameAndRoomId(receiver, roomId)).thenReturn(Optional.of(new Invitation()));

        mockMvc.perform(post("/room/invite/{receiver}/decline/{roomId}", receiver, roomId))
                .andExpect(status().isOk());
    }

    @Test
    public void changeReadyStatus() throws Exception {
        String playerName = "TestPlayer";
        when(playerRepository.findByPlayerName(playerName)).thenReturn(new Player());

        mockMvc.perform(post("/room/ready/{playerName}", playerName))
                .andExpect(status().isOk());
    }



}
