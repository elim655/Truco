package com.backend.backend.test;

import com.backend.backend.controller.*;
import com.backend.backend.model.*;
import com.backend.backend.repository.*;
import com.backend.backend.websocket.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserRoomKeyTest {

    @Test
    void testUserRoomKeyEquality() {
        // Create UserRoomKey instances
        UserRoomKey key1 = new UserRoomKey("user1", 1L);
        UserRoomKey key2 = new UserRoomKey("user1", 1L);
        UserRoomKey key3 = new UserRoomKey("user2", 1L);
        UserRoomKey key4 = new UserRoomKey("user1", 2L);

        // Test equality
        assertEquals(key1, key2);
        assertNotEquals(key1, key3);
        assertNotEquals(key1, key4);
    }

    @Test
    void testUserRoomKeyHashCode() {
        // Create UserRoomKey instances
        UserRoomKey key1 = new UserRoomKey("user1", 1L);
        UserRoomKey key2 = new UserRoomKey("user1", 1L);
        UserRoomKey key3 = new UserRoomKey("user2", 1L);
        UserRoomKey key4 = new UserRoomKey("user1", 2L);

        // Test hash code
        assertEquals(key1.hashCode(), key2.hashCode());
        assertNotEquals(key1.hashCode(), key3.hashCode());
        assertNotEquals(key1.hashCode(), key4.hashCode());
    }
}
