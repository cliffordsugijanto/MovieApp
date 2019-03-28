package com.test.movieapp.fragments;

import com.test.movieapp.MovieAppTest;
import com.test.movieapp.R;
import com.test.movieapp.activities.BaseActivity;
import com.test.movieapp.activities.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static org.junit.Assert.*;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startVisibleFragment;

//@Config(sdk = LOLLIPOP, application = MovieAppTest.class)
@RunWith(RobolectricTestRunner.class)
public class HomeFragmentTest {

    BaseActivity activity;
    HomeFragment fragment;

    private void launchFragment(boolean visibleFragment) {
        fragment = (HomeFragment) HomeFragment.newInstance();
        if (visibleFragment) {
            startVisibleFragment(fragment, MainActivity.class, R.id.fragment_container);
        } else {
            startFragment(fragment, MainActivity.class);
        }
        activity = (BaseActivity) fragment.getBaseActivity();
    }
//
//    @Test
//    public void
}