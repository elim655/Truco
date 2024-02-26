package Truco.com.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import Truco.com.DashBoardActivity;
import Truco.com.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DashBoardActivityTest {

    @Test
    public void testShowSettingsDialog() {
        try (ActivityScenario<DashBoardActivity> scenario = ActivityScenario.launch(DashBoardActivity.class)) {
            onView(withId(R.id.btn_settings)).perform(click());

            onView(withId(R.id.volumeSeekBar)).check(matches(isDisplayed()));

        }
    }
}
