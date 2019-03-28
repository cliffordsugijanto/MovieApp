package com.test.movieapp.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.test.movieapp.R;
import com.test.movieapp.databinding.ActivityMainBinding;
import com.test.movieapp.utils.ToastUtils;

public class BaseActivity extends RootActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    FragmentManager fragmentManager;
    ActivityMainBinding binding;

    private boolean mAllowCommit;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mAllowCommit = false;
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResumeFragments() {
        mAllowCommit = true;
        super.onResumeFragments();
    }

    public boolean allowFragmentCommit() {
        return mAllowCommit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAllowCommit = true;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
    }

    public void setFragment(Fragment fragment, @NonNull String tag, boolean addToBackStack) {
        setFragment(fragment, tag, addToBackStack, null);
    }

    public void setFragment(Fragment fragment, @NonNull String tag, boolean addToBackStack, int[] animRes) {
        if (tag.equals(getCurrentFragmentTag())) {
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (animRes != null) {
            fragmentTransaction.setCustomAnimations(animRes[0], animRes[1], animRes[2], animRes[3]);
        }
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        if (mAllowCommit)
            fragmentTransaction.commit();
        else
            ToastUtils.showToast(this,"Failed opening page. Please try again.");
    }

    public Fragment getCurrentFragment() {
        return fragmentManager.findFragmentById(R.id.fragment_container);
    }

    public void showProgressDialog() {
        binding.progressDialog.setVisibility(View.VISIBLE);
    }

    public void hideProgressDialog() {
        binding.progressDialog.setVisibility(View.GONE);
    }

    private String getCurrentFragmentTag() {
        Fragment f = getCurrentFragment();
        return f != null ? f.getTag() : null;
    }
}

