package Truco.com.model;

public class FriendRequestModel {
    private Long id;
    private UserModel sender;
    private UserModel receiver;
    private String status;

    public FriendRequestModel() {
    }

    public FriendRequestModel(UserModel sender, UserModel receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = "Pending"; // Set the initial status as "Pending"
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getSender() {
        return sender;
    }

    public void setSender(UserModel sender) {
        this.sender = sender;
    }

    public UserModel getReceiver() {
        return receiver;
    }

    public void setReceiver(UserModel receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
