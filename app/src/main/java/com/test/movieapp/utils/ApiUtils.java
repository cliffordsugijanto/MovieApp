package com.test.movieapp.utils;

import android.text.TextUtils;

import org.json.JSONObject;

import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ApiUtils {
    private static final int CODE_UNAUTHORIZED = 401;
    public static final int CODE_APP_OUTDATED = 412;

    public static String getErrorMessage(Response response) {
        String errorMessage = getErrorMessage(response.errorBody());
        if (!TextUtils.isEmpty(errorMessage)) {
            return errorMessage;
        }
        return response.raw().message();
    }

    private static String getErrorMessage(ResponseBody errorBody) {
        try {
            String strError = errorBody.string();
            JSONObject jsonObject = new JSONObject(strError);
            Iterator<String> keys = jsonObject.keys();
            String firstKey = keys.next();
            return jsonObject.optJSONArray(firstKey).getString(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static okhttp3.Response interceptResponse(okhttp3.Response response) {
        return response;
    }
}
