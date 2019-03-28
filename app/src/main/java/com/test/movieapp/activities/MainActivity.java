package com.test.movieapp.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.test.movieapp.MovieApp;
import com.test.movieapp.R;
import com.test.movieapp.databinding.ActivityMainBinding;
import com.test.movieapp.fragments.HomeFragment;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFragment(HomeFragment.newInstance(),HomeFragment.TAG,false);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
