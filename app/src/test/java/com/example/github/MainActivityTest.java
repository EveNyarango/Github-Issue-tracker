package com.example.github;

import android.content.Intent;
import android.widget.TextView;

import com.example.github.ui.MainActivity;
import com.example.github.ui.UserActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setup(){
        activity = Robolectric.setupActivity(MainActivity.class);
    }


//    @Test
//    public void validateTextViewContent(){
//        TextView appName= activity.findViewById(R.id.appName);
//        assertTrue("Github Issue Tracker".equals(appName.getText().toString()));
//    }
//    @Test
//    public void secondActivityStarted(){
//        activity.findViewById(R.id.btn_search).performClick();
//        Intent expectedIntent = new Intent(activity, UserActivity.class);
//        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
//        Intent actualIntent = shadowActivity.getNextStartedActivity();
//        assertTrue(actualIntent.filterEquals(expectedIntent));
//    }
}
