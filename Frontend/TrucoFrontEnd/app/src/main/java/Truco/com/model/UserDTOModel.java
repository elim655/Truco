package Truco.com.model;

import java.util.List;
import java.util.Set;

public class UserDTOModel {
    private Long id;
    private String username;
    private Set<String> friendUsername;
    private List<String> sentFriendRequest;
    private List<String> receivedFriendRequest;
    private List<Long> receivedInvitation;
    private List<String> receivedInvitationUsernames;

    public List<String> getReceivedInvitationUsernames() {
        return receivedInvitationUsernames;
    }

    public void setReceivedInvitationUsernames(List<String> receivedInvitationUsernames) {
        this.receivedInvitationUsernames = receivedInvitationUsernames;
    }

    public UserDTOModel() {}

    public UserDTOModel(Long id, String username, Set<String> friendUsername,
                        List<String> sentFriendRequest, List<String> receivedFriendRequest,
                        List<Long> receivedInvitation) {
        this.id = id;
        this.username = username;
        this.friendUsername = friendUsername;
        this.sentFriendRequest = sentFriendRequest;
        this.receivedFriendRequest = receivedFriendRequest;
        this.receivedInvitation = receivedInvitation;
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

    public Set<String> getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(Set<String> friendUsername) {
        this.friendUsername = friendUsername;
    }

    public List<String> getSentFriendRequest() {
        return sentFriendRequest;
    }

    public void setSentFriendRequest(List<String> sentFriendRequest) {
        this.sentFriendRequest = sentFriendRequest;
    }

    public List<String> getReceivedFriendRequest() {
        return receivedFriendRequest;
    }

    public void setReceivedFriendRequest(List<String> receivedFriendRequest) {
        this.receivedFriendRequest = receivedFriendRequest;
    }

    public List<Long> getReceivedInvitation() {
        return receivedInvitation;
    }

    public void setReceivedInvitation(List<Long> receivedInvitation) {
        this.receivedInvitation = receivedInvitation;
    }
}
