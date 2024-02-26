package com.backend.backend.controller;

import com.backend.backend.model.FriendRequest;
import com.backend.backend.model.Player;
import com.backend.backend.model.User;
import com.backend.backend.model.UserDTO;
import com.backend.backend.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "User API")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendRequestRepository friendRequestRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    InvitationRepository invitationRepository;
    @Autowired
    GameRoomRepository gameRoomRepository;

    /**
     * Endpoint to retrieve user details by username.
     *
     * @param username The username of the user to retrieve.
     * @return ResponseEntity containing UserDTO if found, or a not found response.
     */
    @Operation(summary = "Get Specific User ID", description = "Get information about a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String username) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());

            Set<String> friendUser = user.getFriends().stream()
                    .map(User::getUsername)
                    .collect(Collectors.toSet());
            userDTO.setFriendUsername(friendUser);

            List<String> sentRequests = user.getSentFriendRequests().stream()
                    .map(request -> request.getReceiver().getUsername())
                    .collect(Collectors.toList());
            userDTO.setSentFriendRequest(sentRequests);

            List<String> receivedRequests = user.getReceivedFriendRequests().stream()
                    .map(request -> request.getSender().getUsername())
                    .collect(Collectors.toList());
            userDTO.setReceivedFriendRequest(receivedRequests);

            List<Long> Invitation = user.getReceivedInvitations().stream()
                    .map(request -> request.getRoom().getId())
                    .collect(Collectors.toList());
            userDTO.setReceivedInvitation(Invitation);

            return ResponseEntity.ok().body(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());

            Set<String> friendUser = user.getFriends().stream()
                    .map(User::getUsername)
                    .collect(Collectors.toSet());
            userDTO.setFriendUsername(friendUser);

            List<String> sentRequests = user.getSentFriendRequests().stream()
                    .map(request -> request.getReceiver().getUsername())
                    .collect(Collectors.toList());
            userDTO.setSentFriendRequest(sentRequests);


            List<String> receivedRequests = user.getReceivedFriendRequests().stream()
                    .map(request -> request.getSender().getUsername())
                    .collect(Collectors.toList());
            userDTO.setReceivedFriendRequest(receivedRequests);

            List<Long> Invitation = user.getReceivedInvitations().stream()
                    .map(request -> request.getRoom().getId())
                    .collect(Collectors.toList());
            userDTO.setReceivedInvitation(Invitation);


            userDTOs.add(userDTO);
        }

        return userDTOs;
    }

    @Operation(summary = "Create new user", description = "Creates a user with path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/post")
    User PostUserByPath(@RequestBody User newUser){
        userRepository.save(newUser);
        return newUser;
    }

    @Operation(summary = "Add a friend", description = "Sends a friend request to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/{username}/add/{friendName}")
    public ResponseEntity<String> addFriend(@PathVariable String username, @PathVariable String friendName) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        Optional<User> friendOptional = Optional.ofNullable(userRepository.findByUsername(friendName));

        if (userOptional.isPresent() && friendOptional.isPresent()) {
            User user = userOptional.get();
            User friend = friendOptional.get();

            // Check if the users are not already friends
            if (user.equals(friend)) {
                return ResponseEntity.badRequest().body("You cannot add yourself as a friend.");
            } else if (user.getFriends().contains(friend)) {
                return ResponseEntity.badRequest().body("Users are already friends.");
            } else {
                // Check if there is an existing friend request from user to friend
                boolean isDuplicateRequest = user.getSentFriendRequests().stream()
                        .anyMatch(request -> request.getReceiver().equals(friend) && request.getStatus().equals("Pending"));

                if (isDuplicateRequest) {
                    return ResponseEntity.badRequest().body("You have already sent a friend request to " + friendName + ".");
                }

                FriendRequest friendRequest = new FriendRequest(user, friend);
                friendRequestRepository.save(friendRequest);
                user.setSentFriendRequests(friendRequest);
                friend.setReceivedFriendRequests(friendRequest);

                friendRequest.setStatus("Pending");

                return ResponseEntity.ok("Friend request sent successfully.");
            }
        } else {
            return ResponseEntity.badRequest().body(friendName + " is not found");
        }
    }

    @Operation(summary = "Accept friend request", description = "A user accepts a friend request and adds the sender to their friends list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/{username}/accept/{requestUser}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable String username, @PathVariable String requestUser) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Manually search for the user with the specified username
            User friendRequestUser = userRepository.findByUsername(requestUser);

            if (friendRequestUser != null) {
                // Check if there is a pending friend request from requestUser to userId
                Optional<FriendRequest> friendRequestOptional = user.getReceivedFriendRequests().stream()
                        .filter(request -> request.getSender().equals(friendRequestUser) && request.getStatus().equals("Pending"))
                        .findFirst();

                if (friendRequestOptional.isPresent()) {
                    // Accept the friend request
                    FriendRequest friendRequest = friendRequestOptional.get();
                    friendRequest.setStatus("Accepted");

                    // Add each other as friends
                    user.getFriends().add(friendRequestUser);
                    friendRequestUser.getFriends().add(user);



                    // Save the changes
                    userRepository.save(user);
                    userRepository.save(friendRequestUser);

                    // Delete the friend request from the repository
                    friendRequestRepository.delete(friendRequest);

                    return ResponseEntity.ok("Friend request accepted successfully.");
                } else {
                    return ResponseEntity.badRequest().body("No pending friend request from " + requestUser + ".");
                }
            } else {
                return ResponseEntity.badRequest().body(requestUser + " is not found");
            }
        } else {
            return ResponseEntity.badRequest().body(username + " is not found");
        }
    }



//    @PostMapping("/{userId}/accept/{requestId}")
//    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long userId, @PathVariable String requestUser) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        Optional<FriendRequest> requestOptional = friendRequestRepository.findbyId(requestId);
//
//        if (userOptional.isPresent() && requestOptional.isPresent()) {
//            User user = userOptional.get();
//            FriendRequest friendRequest = requestOptional.get();
//
//            // Check if the user is the receiver of the friend request
//            if (user.equals(friendRequest.getReceiver()) && friendRequest.getStatus().equals("Pending")) {
//                // Update the friend request status to "Accepted"
//                friendRequest.setStatus("Accepted");
//                friendRequestRepository.save(friendRequest);
//
//                // Add the sender as a friend of the receiver and vice versa
//                User sender = friendRequest.getSender();
//                user.getFriends().add(sender);
//                sender.getFriends().add(user);
//
//                userRepository.save(user);
//                userRepository.save(sender);
//
//                return ResponseEntity.ok("Friend request accepted successfully.");
//            } else {
//                return ResponseEntity.badRequest().body("Invalid friend request or already accepted.");
//            }
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @Operation(summary = "Decline friend request", description = "Declines a friend request ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping("/{username}/decline/{requestUser}")
    public ResponseEntity<String> declineFriendRequest(@PathVariable String username, @PathVariable String requestUser) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Manually search for the user with the specified username
            User friendRequestUser = userRepository.findAll().stream()
                    .filter(u -> u.getUsername().equals(requestUser))
                    .findFirst()
                    .orElse(null);

            if (friendRequestUser != null) {
                // Check if there is a pending friend request from requestUser to userId
                Optional<FriendRequest> friendRequestOptional = user.getReceivedFriendRequests().stream()
                        .filter(request -> request.getSender().equals(friendRequestUser) && request.getStatus().equals("Pending"))
                        .findFirst();

                if (friendRequestOptional.isPresent()) {
                    // Decline the friend request
                    FriendRequest friendRequest = friendRequestOptional.get();
                    friendRequest.setStatus("Declined");

                    // Remove the declined friend request
                    user.getReceivedFriendRequests().remove(friendRequest);
                    friendRequestUser.getSentFriendRequests().remove(friendRequest);

                    // Save the changes
                    userRepository.save(user);
                    userRepository.save(friendRequestUser);
                    friendRequestRepository.delete(friendRequest);

                    return ResponseEntity.ok("Friend request declined successfully.");
                } else {
                    return ResponseEntity.badRequest().body("No pending friend request from " + requestUser + ".");
                }
            } else {
                return ResponseEntity.badRequest().body(requestUser + " is not found");
            }
        } else {
            return ResponseEntity.badRequest().body(username + " is not found");
        }
    }

    /**
     * Deletes a friend from the user's friend list.
     *
     * @param username    The username of the user removing the friend.
     * @param friendUser  The username of the friend to be removed.
     * @return ResponseEntity with a success message or error message if unsuccessful.
     */

    @Operation(summary = "Delete Friend", description = "Delete user from friendlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @DeleteMapping("/{username}/delete/{friendUser}")
    public ResponseEntity<String> deleteFriend(@PathVariable String username, @PathVariable String friendUser) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        Optional<User> friendOptional = Optional.ofNullable(userRepository.findByUsername(friendUser));

        if (userOptional.isPresent() && friendOptional.isPresent()) {
            User user = userOptional.get();
            User friend = friendOptional.get();

            // Check if the users are friends
            if (user.getFriends().contains(friend) && friend.getFriends().contains(user)) {
                // Remove the friendship relationship
                user.getFriends().remove(friend);
                friend.getFriends().remove(user);

                userRepository.save(user);
                userRepository.save(friend);

                return ResponseEntity.ok("Friendship deleted successfully.");
            } else {
                return ResponseEntity.badRequest().body("Users are not friends.");
            }
        } else {
            return ResponseEntity.badRequest().body(friendUser + " is not in your friend list");
        }
    }

    /**
     * Deletes a user and related records from the database.
     *
     * @param user The username of the user to be deleted.
     * @return ResponseEntity with a success message or error message if unsuccessful.
     */
    @Operation(summary = "Delete User", description = "Delete all related information about this user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @DeleteMapping("/delete/{user}")
    @Transactional
    public ResponseEntity<String> deleteUser(@PathVariable String user) {
        User userToDelete = userRepository.findByUsername(user);

        if (userToDelete != null) {


            // delete invitation
            invitationRepository.deleteByReceiver(userToDelete);
            // Remove the user from friends' lists
            for (User friend : userToDelete.getFriends()) {
                friend.getFriends().remove(userToDelete);
                userRepository.save(friend);
            }

            // Find the related Player record
            Player playerToDelete = playerRepository.findByUser(userToDelete);

            if (playerToDelete != null) {
                if(playerToDelete.getGameRoom() != null){
                    List<Player> playersInRoom = playerRepository.findByGameRoomId(playerToDelete.getGameRoom().getId());
                    if (playerToDelete.isHost()) {
                        // If the player is the host, remove all players from the room and delete it
                        for(Player p : playersInRoom){
                            p.reset();
                        }
                        gameRoomRepository.delete(playerToDelete.getGameRoom());
                    }
                }


                // Delete the related Player record
                playerRepository.delete(playerToDelete);
            }

            // Delete related FriendRequest records
            friendRequestRepository.deleteBySenderOrReceiver(userToDelete, userToDelete);

            // Now delete the user
            userRepository.delete(userToDelete);

            return ResponseEntity.ok("User and related records deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("User not found in the database.");
        }
    }
}

