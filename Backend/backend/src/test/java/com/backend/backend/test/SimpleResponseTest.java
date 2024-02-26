package com.backend.backend.test;


import com.backend.backend.websocket.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleResponseTest {

    @Test
    void testGetMessage() {
        // Create a SimpleResponse instance
        SimpleResponse simpleResponse = new SimpleResponse("Hello, World!");

        // Test the getMessage method
        assertEquals("Hello, World!", simpleResponse.getMessage());
    }

    @Test
    void testSetMessage() {
        // Create a SimpleResponse instance
        SimpleResponse simpleResponse = new SimpleResponse("");

        // Test the setMessage method
        simpleResponse.setMessage("New Message");
        assertEquals("New Message", simpleResponse.getMessage());
    }
}
