package Truco.com.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import Truco.com.ChatWebSocketClient;
import Truco.com.R;


import org.junit.Before;
import org.mockito.Mockito;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.core.app.ActivityScenario;

import Truco.com.ChatActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChatActivityTest {

    private ChatWebSocketClient mockClient;

//    @Before
//    public void setup() {
//        // Mock the WebSocket client
//        mockClient = Mockito.mock(ChatWebSocketClient.class);
//
//        // Launch ChatActivity with the mocked WebSocket client
//        ActivityScenario<ChatActivity> scenario = ActivityScenario.launch(ChatActivity.class);
//        scenario.onActivity(activity -> {
//            activity.setWebSocketClient(mockClient);
//        });
//    }
//
//
//    @Test
//    public void testSendMessage() {
//        // Stub the send method of the mock WebSocket client
//        Mockito.doNothing().when(mockClient).send(Mockito.anyString());
//
//        // Launch the activity
//        try (ActivityScenario<ChatActivity> scenario = ActivityScenario.launch(ChatActivity.class)) {
//            // Simulate user input
//            onView(withId(R.id.editTextMessage)).perform(typeText("Hello"), closeSoftKeyboard());
//            onView(withId(R.id.buttonSend)).perform(click());
//
//            // Verify that send was called on the mock WebSocket client
//            Mockito.verify(mockClient).send("Hello");
//        }
//    }
//
//    @Test
//    public void testReceiveMessage() {
//        // Simulate receiving a message through the WebSocket
//        Mockito.doAnswer(invocation -> {
//            mockClient.onMessage("Received message");
//            return null;
//        }).when(mockClient).onOpen(null);
//
//        try (ActivityScenario<ChatActivity> scenario = ActivityScenario.launch(ChatActivity.class)) {
//            // Mockito.doNothing().when(mockClient).onOpen(null);
//        }
//    }

    @Test
    public void testInitialLayout() {
        try (ActivityScenario<ChatActivity> scenario = ActivityScenario.launch(ChatActivity.class)) {
            onView(withId(R.id.editTextMessage)).check(matches(isDisplayed()));
            onView(withId(R.id.buttonSend)).check(matches(isDisplayed()));
            onView(withId(R.id.recyclerViewChat)).check(matches(isDisplayed()));
        }
    }

}
