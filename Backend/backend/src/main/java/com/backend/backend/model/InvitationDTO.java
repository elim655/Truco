package com.backend.backend.model;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;


public class InvitationDTO {
        private Long roomId;

        private String sender;

    public InvitationDTO(Long roomId, String sender) {
        this.roomId = roomId;
        this.sender = sender;
    }

    @Operation(summary = "Get game room id", description = "Returns the id of the game room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public Long getRoomId() {
        return roomId;
    }

    @Operation(summary = "Set game room id", description = "Sets the game room id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Operation(summary = "Get invitation sender", description = "Returns who sent the invitation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public String getSender() {
        return sender;
    }

    @Operation(summary = "Set invitation sender", description = "Sets the sender of the invitation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    public void setSender(String sender) {
        this.sender = sender;
    }
}
