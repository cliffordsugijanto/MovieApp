package com.test.movieapp;

import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


import com.test.movieapp.dagger.DaggerMovieDatabaseComponent;
import com.test.movieapp.dagger.MovieDatabaseComponent;

import javax.inject.Inject;


public class MovieApp extends MultiDexApplication {
    public static MovieApp instance;
    public static MovieDatabaseComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        MultiDex.install(this);
        getComponent().inject(this);
    }

    public static MovieDatabaseComponent getComponent() {
        synchronized (MovieApp.class) {
            if(component == null) {
                component = DaggerMovieDatabaseComponent.builder().build();
            }
            return component;
        }
    }

    protected MovieDatabaseComponent setUpComponent() {
        return DaggerMovieDatabaseComponent.builder().build();
    }
}
