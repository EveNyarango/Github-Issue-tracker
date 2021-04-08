package com.example.github;

import android.widget.TextView;

import com.example.github.ui.RepoIssuesActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.TestCase.assertTrue;


@RunWith(RobolectricTestRunner.class)
public class RepoIssuesActivityTest {
    private RepoIssuesActivity activity;

    @Before
    public void setUp(){
        activity = Robolectric.setupActivity(RepoIssuesActivity.class);
    }

//    @Test
//    public void validateTextViewContent(){
//        TextView noIssuesTextView = activity.findViewById(R.id.noIssuesTextView);
//
//
//        assertTrue("No issues_found for this repository".equals(noIssuesTextView.getText().toString()));
//    }
}
