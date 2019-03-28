package com.test.movieapp;

import android.content.ComponentName;
import android.content.Intent;

import com.test.movieapp.activities.BaseActivity;
import com.test.movieapp.activities.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.robolectric.Robolectric.setupActivity;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class, sdk = LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class MovieAppTest {

    BaseActivity activity;
    MovieApp app;

    @Before
    public void setUp() throws Exception {
        activity = setupActivity(BaseActivity.class);
        app = MovieApp.instance;
    }

    @Test
    public void onCreate() throws Exception {
        assertNotNull(app);
        assertNotNull(MovieApp.getComponent());
    }

    @Test
    public void setUpComponent() throws Exception {
        assertNotNull(app.setUpComponent());
    }

    public static void assertNextStartedActivity(MovieApp app, Class<?> startingClass) {
        Intent intent = shadowOf(app).peekNextStartedActivity();
        assertEquals(new ComponentName(app, startingClass), intent.getComponent());
    }
}