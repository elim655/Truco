package Truco.com.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;

import Truco.com.AddFriend;
import Truco.com.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddFriendTest {

    @Test
    public void testInitialLayout() {
        ActivityScenario.launch(AddFriend.class);

        onView(withId(R.id.friendUsername)).check(matches(isDisplayed()));

        onView(withId(R.id.addFriendBtn)).check(matches(isDisplayed()));
    }
}
