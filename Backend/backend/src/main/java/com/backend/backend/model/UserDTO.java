package com.backend.backend.model;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Set;
/**
 * Data Transfer Object (DTO) representing a user for use in data exchange.
 */
public class UserDTO {
    private Long id;
    private String username;
    private Set<String> friendUsername;
    private List<String> sentFriendRequest;
    private List<String> receivedFriendRequest;
    private List<Long> receivedInvitation;

    /**
     * Default constructor for the UserDTO class.
     */
    public UserDTO() {
    }

    @Operation(summary = "Get userDTO id", description = "Returns the id of userDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Long getId() {
        return id;
    }

    @Operation(summary = "Set userDTO id", description = "Sets the id of userDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setId(Long id) {
        this.id = id;
    }

    @Operation(summary = "Get userDTO username", description = "Returns the username of userDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public String getUsername() {
        return username;
    }

    @Operation(summary = "Set userDTO username", description = "Sets the username of userDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setUsername(String username) {
        this.username = username;
    }

    @Operation(summary = "Get friends username", description = "Returns the id of userDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Set<String> getFriendUsername() {
        return friendUsername;
    }

    @Operation(summary = "Set friend usernames", description = "Sets the set of usernames for the users friends")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setFriendUsername(Set<String> friendUsername) {
        this.friendUsername = friendUsername;
    }

    @Operation(summary = "Get sent friend requests", description = "Returns a list of friend request that have been sent by the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public List<String> getSentFriendRequest() {
        return sentFriendRequest;
    }


    @Operation(summary = "Set sent friend request", description = "Set the list of sent friend requests by the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setSentFriendRequest(List<String> sentFriendRequest) {
        this.sentFriendRequest = sentFriendRequest;
    }

    @Operation(summary = "Get friend request", description = "Get the list of received friend requests by the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public List<String> getReceivedFriendRequest() {
        return receivedFriendRequest;
    }

    /**
     * Set the list of received friend requests by the user.
     *
     * @param receivedFriendRequest The list of received friend requests to set.
     */
    @Operation(summary = "Set received friend request", description = "Set the list of received friend requests by the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setReceivedFriendRequest(List<String> receivedFriendRequest) {
        this.receivedFriendRequest = receivedFriendRequest;
    }

    @Operation(summary = "Get receiver invitation", description = "Get the list of receiver invitations by the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public List<Long> getReceivedInvitation() {
        return receivedInvitation;
    }

    @Operation(summary = "Set received invitation", description = "Set the list of received invitations by the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setReceivedInvitation(List<Long> receivedInvitation) {
        this.receivedInvitation = receivedInvitation;
    }
}
