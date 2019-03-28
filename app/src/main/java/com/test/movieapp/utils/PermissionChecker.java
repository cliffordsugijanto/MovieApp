package com.test.movieapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PermissionChecker {
    private static final String TAG = PermissionChecker.class.getSimpleName();

    Context context;

    public PermissionChecker(Context context) {
        this.context = context;
    }

    public boolean isGranted(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public List<String> getMissingPermissions(String... permissions) {
        List<String> missingPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (!isGranted(permission)) {
                missingPermissions.add(permission);
            }
        }
        return missingPermissions;
    }

    public void checkAndRequestPermissions(Activity activity, int requestCode, String... permissions) {
        List<String> missingPermissions = getMissingPermissions(permissions);
        if (missingPermissions.size() > 0) {
            ActivityCompat.requestPermissions(activity, missingPermissions.toArray(new String[0]), requestCode);
            Log.v(TAG, "start requesting permissions");
        }
    }



}
