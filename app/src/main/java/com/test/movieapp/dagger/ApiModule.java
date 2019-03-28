package com.test.movieapp.dagger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.movieapp.BuildConfig;
import com.test.movieapp.api.MainApi;
import com.test.movieapp.utils.ApiUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Singleton
    @Provides
    @Named("main")
    public Retrofit providesMainRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .addHeader("Content-Type","application/x-www-form-urlencoded")
                    .method(original.method(), original.body())
                    .build();

            return ApiUtils.interceptResponse(chain.proceed(request));
        });


        return httpClient.build();
    }

    private Gson createGson(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Singleton
    @Provides
    public MainApi providesMainApi(@Named ("main") Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

}
