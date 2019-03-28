package com.test.movieapp.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message != null ? message : "", Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int message) {
        showToast(context, context.getString(message));
    }

    public static void showLongToast(Context context, String message) {
        Toast.makeText(context, message != null ? message : "", Toast.LENGTH_LONG).show();
    }
}
