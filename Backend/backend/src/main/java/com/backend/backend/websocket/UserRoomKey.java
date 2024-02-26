package com.backend.backend.websocket;

import java.util.Objects;

public class UserRoomKey {
    private final String username;
    private final Long roomID;

    public UserRoomKey(String username, Long roomID) {
        this.username = username;
        this.roomID = roomID;
    }

    public String getUsername() {
        return username;
    }

    public Long getRoomID() {
        return roomID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoomKey)) return false;
        UserRoomKey that = (UserRoomKey) o;
        return Objects.equals(username, that.username) && Objects.equals(roomID, that.roomID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, roomID);
    }
}
