package com.backend.backend.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.backend.backend.controller.UserController;
import com.backend.backend.model.*;
import com.backend.backend.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FriendRequestRepository friendRequestRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private InvitationRepository invitationRepository;

    @MockBean
    private GameRoomRepository gameRoomRepository;

    @Test
    public void getUserById() throws Exception {
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(new User());

        mockMvc.perform(get("/users/{username}", username))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUsers() throws Exception {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void postUserByPath() throws Exception {
        User newUser = new User();

        mockMvc.perform(post("/users/post")
                        .contentType("application/json")
                        .content("{\"username\": \"testUser\", \"password\": \"password123\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void addFriend() throws Exception {
        String username = "testUser";
        String friendName = "friendUser";

        when(userRepository.findByUsername(username)).thenReturn(new User());
        when(userRepository.findByUsername(friendName)).thenReturn(new User());

        mockMvc.perform(post("/users/{username}/add/{friendName}", username, friendName))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser() throws Exception {
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(new User());

        mockMvc.perform(delete("/users/delete/{user}", username))
                .andExpect(status().isOk());
    }
}
