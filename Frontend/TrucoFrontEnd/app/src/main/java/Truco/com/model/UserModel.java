package Truco.com.model;

import java.util.Set;

public class UserModel {
    private Long id;
    private String username;
    private Set<UserModel> friends;
    private Set<FriendRequestModel> sentFriendRequests;
    private Set<FriendRequestModel> receivedFriendRequests;

    public UserModel() {}

    public UserModel(String username) {
        this.username = username;
    }

    public UserModel(Long id, String username, Set<UserModel> friends,
                     Set<FriendRequestModel> sentFriendRequests,
                     Set<FriendRequestModel> receivedFriendRequests) {
        this.id = id;
        this.username = username;
        this.friends = friends;
        this.sentFriendRequests = sentFriendRequests;
        this.receivedFriendRequests = receivedFriendRequests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<UserModel> getFriends() {
        return friends;
    }

    public void setFriends(Set<UserModel> friends) {
        this.friends = friends;
    }

    public Set<FriendRequestModel> getSentFriendRequests() {
        return sentFriendRequests;
    }

    public void setSentFriendRequests(Set<FriendRequestModel> sentFriendRequests) {
        this.sentFriendRequests = sentFriendRequests;
    }

    public Set<FriendRequestModel> getReceivedFriendRequests() {
        return receivedFriendRequests;
    }

    public void setReceivedFriendRequests(Set<FriendRequestModel> receivedFriendRequests) {
        this.receivedFriendRequests = receivedFriendRequests;
    }
}
