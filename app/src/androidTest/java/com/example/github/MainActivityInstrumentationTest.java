package com.example.github;


import androidx.recyclerview.widget.RecyclerView;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.github.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void validateEditText() {
        onView(withId(R.id.et_username)).perform(typeText("EveNyarango"))
                .check(matches(withText("EveNyarango")));
    }

//    @Test
//    public void usernameIsSentToUserActivity() {
//        String username = "EveNyarango";
//        onView(withId(R.id.et_username)).perform(typeText(username));
//        onView(withId(R.id.btn_search)).perform(click());
//        onView(withId(R.id.userRecyclerView)).check(matches(withText("userRecyclerView")));
//    }

    @Test
    public void locationIsSentUserActivity(){
        String username = "EveNyarango";
        onView(withId(R.id.et_username)).perform(typeText(username)).perform(closeSoftKeyboard());
        try {                             // the sleep method requires to be checked and handled so we use try block
            Thread.sleep(250);
        } catch (InterruptedException e){
            System.out.println("got interrupted!");
        }
        onView(withId(R.id.btn_search)).perform(click());
        onView(withId(R.id.userRecyclerView)).check(matches
                (withText("userRecyclerView)")));

    }
}
