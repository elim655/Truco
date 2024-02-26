package com.backend.backend.controller;
import com.backend.backend.model.Player;
import com.backend.backend.repository.PlayerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import com.backend.backend.repository.LoginRepository;
import com.backend.backend.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Tag(name = "Login API")
public class LoginController {

    @Autowired
    LoginRepository loginRepository;
    @Autowired
    PlayerRepository playerRepository;

    @Operation(summary = "Sign up", description = "Create a new user with a username and a password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/signup/{user}/{pass}")
    String PostSignupByPath(@PathVariable String user, @PathVariable String pass) {
        // Check if a user with that username exists
        User existingUser = loginRepository.findByUsername(user);
        if (existingUser != null) {
            return "User already exists";
        }

        // Create and save a new User entity
        User newUser = new User();
        newUser.setUsername(user);
        newUser.setPassword(pass);
        loginRepository.save(newUser);


        // Create and save a new Player entity
        Player newPlayer = new Player();
        newPlayer.setUser(newUser);
        newPlayer.setPlayerName(user);
        newPlayer.setPoints(0);
        playerRepository.save(newPlayer);

        return "User added";
    }

    @Operation(summary = "Login", description = "Login with a username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/login")
    String UserLogin(@RequestBody User userLogin){
        String username = userLogin.getUsername();

        if(!userLogin.getPassword().equals(loginRepository.findByUsername(username).getPassword())){
            return "Incorrect password";
        }
        return "Successfully logged in";
    }

    @Operation(summary = "Get all users", description = "Returns a list of all the users in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/users")
    List<User> GetAllUsers(){
        return loginRepository.findAll();
    }

    @Operation(summary = "Delete user", description = "Deletes a user from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @DeleteMapping("/user/delete")
    List<User> DeleteUserByPath(@RequestBody User delUser){
        loginRepository.delete(delUser);
        return GetAllUsers();
    }

    @Operation(summary = "Change password", description = "Change password of a user in the database only if they know the old password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/user/changepassword/{user}/{oldPass}/{newPass}")
    String ChangePassword(@PathVariable String user, @PathVariable String oldPass, @PathVariable String newPass){
        if(loginRepository.findByUsername(user) == null){
            return "User doesnt exist.";
        }
        else if(!loginRepository.findByUsername(user).getPassword().equals(oldPass)){
            return "Incorrect Old Password";
        }


        return "";
    }

}
