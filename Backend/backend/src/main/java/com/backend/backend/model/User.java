package com.backend.backend.model;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user in the system.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends = new HashSet<>();

    @OneToMany(mappedBy = "sender")
    private Set<FriendRequest> sentFriendRequests = new HashSet<>();

    @OneToMany(mappedBy = "receiver")
    private Set<FriendRequest> receivedFriendRequests = new HashSet<>();

    @OneToMany(mappedBy = "receiver")
    private Set<Invitation> receivedInvitations = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Player player;

    /**
     * Default constructor for the User class.
     */
    public User() {
    }

    @Operation(summary = "Get user id", description = "Returns the id of the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Long getId() {
        return id;
    }

    @Operation(summary = "Set user id", description = "Sets the user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setId(Long id) {
        this.id = id;
    }

    @Operation(summary = "Get username", description = "Returns the username of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public String getUsername() {
        return username;
    }

    @Operation(summary = "Set username", description = "Sets the username of the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setUsername(String username) {
        this.username = username;
    }

    @Operation(summary = "Get users friends", description = "Returns set of users friends")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Set<User> getFriends() {
        return friends;
    }

    /**
     * Get the set of sent friend requests by the user.
     *
     * @return The set of sent friend requests.
     */
    public Set<FriendRequest> getSentFriendRequests() {
        return sentFriendRequests;
    }

    /**
     * Add a sent friend request to the user's set of sent friend requests.
     *
     * @param sentFriendRequests The sent friend request to add.
     */
    public void setSentFriendRequests(FriendRequest sentFriendRequests) {
        this.sentFriendRequests.add(sentFriendRequests);
    }

    /**
     * Get the set of received friend requests by the user.
     *
     * @return The set of received friend requests.
     */
    public Set<FriendRequest> getReceivedFriendRequests() {
        return receivedFriendRequests;
    }

    /**
     * Add a received friend request to the user's set of received friend requests.
     *
     * @param receivedFriendRequests The received friend request to add.
     */
    public void setReceivedFriendRequests(FriendRequest receivedFriendRequests) {
        this.receivedFriendRequests.add(receivedFriendRequests);
    }

    /**
     * Get the set of received invitations by the user.
     *
     * @return The set of received invitations.
     */
    public Set<Invitation> getReceivedInvitations() {
        return receivedInvitations;
    }

    /**
     * Add a received invitation to the user's set of received invitations.
     *
     * @param receivedInvitations The received invitation to add.
     */
    public void setReceivedInvitations(Invitation receivedInvitations) {
        this.receivedInvitations.add(receivedInvitations);
    }

    /**
     * Get the player associated with the user.
     *
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the user's password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the user's password.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
