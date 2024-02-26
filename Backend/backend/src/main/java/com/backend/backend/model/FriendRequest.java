package com.backend.backend.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Represents a friend request entity in the application.
 */
@Entity
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Friend Request ID")
    private Long id;

    @ManyToOne
    @Schema(name = "Sending User")
    private User sender;

    @ManyToOne
    @Schema(name = "Receiving User")
    private User receiver;
    @Schema(name = "Status")
    private String status;

    /**
     * Default constructor for creating a friend request.
     */
    public FriendRequest() {
    }

    /**
     * Constructor for creating a friend request with a sender and receiver.
     *
     * @param sender   The user who sent the friend request.
     * @param receiver The user who received the friend request.
     */
    public FriendRequest(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = "Pending"; // Set the initial status as "Pending"
    }

    @Operation(summary = "Gets friend request id", description = "Get the id of a friend request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Long getId() {
        return id;
    }

    @Operation(summary = "Set friend request id", description = "Sets the id of the friend request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setId(Long id) {
        this.id = id;
    }

    @Operation(summary = "Get sender", description = "Gets the sender of the friend request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public User getSender() {
        return sender;
    }

    @Operation(summary = "Set sender", description = "Sets the user who sends the friend request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setSender(User sender) {
        this.sender = sender;
    }

    @Operation(summary = "Get receiver", description = "Gets the user that received friend request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public User getReceiver() {
        return receiver;
    }

    @Operation(summary = "Get a product by id", description = "Returns a product as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Operation(summary = "Gets status", description = "Gets status of friend request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public String getStatus() {
        return status;
    }

    @Operation(summary = "Set status", description = "Sets the status of the friend request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setStatus(String status) {
        this.status = status;
    }
}

