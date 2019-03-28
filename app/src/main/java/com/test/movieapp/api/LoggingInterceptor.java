package com.test.movieapp.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class LoggingInterceptor implements Interceptor {

    private final String mTag;

    public LoggingInterceptor(String tag) {
        mTag = tag;
    }

    public String getLogTag() {
        return mTag;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();

        Log.i(mTag, String.format(">>> Sending request %s %s", request.method(), request.url()));
        Log.v(mTag, String.format("%s", request.headers()));
        Log.d(mTag, "$ " + curlRequest(request));

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Log.i(mTag,
                String.format("<<< Received response for %s %s in %.1fms%nHTTP %d",
                        response.request().method(),
                        response.request().url(),
                        (t2 - t1) / 1e6d,
                        response.code()));
        Log.v(mTag, String.format("%s", response.headers()));
        return response;
    }

    private String curlRequest(Request request) {
        StringBuilder builder = new StringBuilder();
        builder.append("curl -i -X '").append(request.method()).append("'");

        final RequestBody body = request.body();
        if (body != null) {
            try {
                Buffer buffer = new Buffer();
                body.writeTo(buffer);
                String data = buffer.readUtf8().replaceAll("\"", "\\\"");
                builder.append(" -d '").append(data).append("'");
            } catch (IOException e) {
                if (Log.isLoggable(mTag, Log.VERBOSE)) {
                    Log.v(mTag, "!!! Unable include request body in curl command");
                }
                throw new RuntimeException(e);
            }

            builder.append(" -H 'Content-Type: ").append(body.contentType() != null ? body.contentType().toString() : "").append("'");
        }

        for (String name : request.headers().names()) {
            for (String value : request.headers(name)) {
                builder.append(" -H '").append(name).append(": ").append(value).append("'");
            }
        }

        builder.append(" \"").append(request.url()).append("\"");
        return builder.toString();
    }

}
