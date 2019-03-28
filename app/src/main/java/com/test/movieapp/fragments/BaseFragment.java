package com.test.movieapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.test.movieapp.activities.RootActivity;
import com.test.movieapp.utils.ToastUtils;


public class BaseFragment extends Fragment {
    RootActivity activity;
    boolean activityLaunched;

    public boolean canLaunchActivity(){
        if (activityLaunched){
            return false;
        } else {
            activityLaunched = true;
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activityLaunched = false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (RootActivity) context;
    }

    public Activity getBaseActivity(){
        return activity;
    }

    public void showProgressDialog() {
        activity.showProgressDialog();
    }

    public void hideProgressDialog() {
        activity.hideProgressDialog();
    }

    public void showToast(String message) {
        ToastUtils.showToast(activity, message);
    }

    public void showToast(int message) {
        ToastUtils.showToast(activity, message);
    }
}
