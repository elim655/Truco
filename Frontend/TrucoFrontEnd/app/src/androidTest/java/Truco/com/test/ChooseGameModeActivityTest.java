package Truco.com.test;

import androidx.test.core.app.ActivityScenario;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import org.junit.Test;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.runner.RunWith;
import Truco.com.R;
import Truco.com.ChooseGameModeActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChooseGameModeActivityTest {

    @Test
    public void testButtonsAreDisplayed() {
        try (ActivityScenario<ChooseGameModeActivity> scenario = ActivityScenario.launch(ChooseGameModeActivity.class)) {
            onView(withId(R.id.button1v1)).check(matches(isDisplayed()));

            onView(withId(R.id.button2v2)).check(matches(isDisplayed()));

            onView(withId(R.id.button3v3)).check(matches(isDisplayed()));
        }
    }
}
