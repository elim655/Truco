package com.backend.backend.model;


import jakarta.persistence.*;

/**
 * Represents an invitation to join a game room.
 * An invitation is sent by a user to invite another user to join a specific game room.
 */
@Entity
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier of the invitation


    @ManyToOne
    @JoinColumn(name = "room_id")
    private GameRoom room; // The game room associated with the invitation

    @ManyToOne
    @JoinColumn(name = "receiver_name")
    private User receiver; // The user receiving the invitation

    @ManyToOne
    @JoinColumn(name = "sender_name")
    private User sender;



    private String userName; // The username of the receiver

    /**
     * Constructs an Invitation object with the specified game room and receiver.
     *
     * @param room     The game room associated with the invitation.
     * @param receiver The user receiving the invitation.
     */
    public Invitation(GameRoom room, User receiver) {
        this.room = room;
        this.receiver = receiver;
        this.userName = receiver.getUsername();
    }

    /**
     * Default constructor for the Invitation class.
     */
    public Invitation() {
    }

    /**
     * Gets the unique identifier of the invitation.
     *
     * @return The ID of the invitation.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the invitation.
     *
     * @param id The ID of the invitation.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the game room associated with the invitation.
     *
     * @return The game room associated with the invitation.
     */
    public GameRoom getRoom() {
        return room;
    }

    /**
     * Sets the game room associated with the invitation.
     *
     * @param room The game room associated with the invitation.
     */
    public void setRoom(GameRoom room) {
        this.room = room;
    }

    /**
     * Gets the user receiving the invitation.
     *
     * @return The user receiving the invitation.
     */
    public User getReceiver() {
        return receiver;
    }

    /**
     * Sets the user receiving the invitation.
     *
     * @param receiver The user receiving the invitation.
     */
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    /**
     * Gets the username of the receiver.
     *
     * @return The username of the receiver.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the receiver.
     *
     * @param userName The username of the receiver.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
