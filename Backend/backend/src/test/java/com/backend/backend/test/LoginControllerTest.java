package com.backend.backend.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.backend.backend.controller.LoginController;
import com.backend.backend.model.User;
import com.backend.backend.repository.LoginRepository;
import com.backend.backend.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginRepository loginRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @Test
    public void testPostSignupByPath() throws Exception {
        String username = "newUser";
        String password = "password";
        when(loginRepository.findByUsername(username)).thenReturn(null);

        mockMvc.perform(post("/signup/{user}/{pass}", username, password))
                .andExpect(status().isOk());
    }

    @Test
    public void testUserLogin() throws Exception {
        User loginUser = new User();
        loginUser.setUsername("existingUser");
        loginUser.setPassword("password");
        when(loginRepository.findByUsername("existingUser")).thenReturn(loginUser);

        mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content("{\"username\":\"existingUser\", \"password\":\"password\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserByPath() throws Exception {
        User delUser = new User();
        delUser.setUsername("userToDelete");

        mockMvc.perform(delete("/user/delete")
                        .contentType("application/json")
                        .content("{\"username\":\"userToDelete\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testChangePassword() throws Exception {
        String username = "existingUser";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        User existingUser = new User();
        existingUser.setPassword(oldPassword);
        when(loginRepository.findByUsername(username)).thenReturn(existingUser);

        mockMvc.perform(post("/user/changepassword/{user}/{oldPass}/{newPass}", username, oldPassword, newPassword))
                .andExpect(status().isOk());
    }
}
