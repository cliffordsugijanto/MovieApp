package com.test.movieapp.dagger;

import android.content.Context;


import com.test.movieapp.MovieApp;
import com.test.movieapp.utils.PermissionChecker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {
    @Provides
    @Singleton
    Context providesApplicationContext() {
        return MovieApp.instance;
    }

    @Provides
    @Singleton
    PermissionChecker providesPermissionChecker(Context context) {
        return new PermissionChecker(context);
    }
}
