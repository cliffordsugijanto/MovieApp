package com.test.movieapp.dagger;

import com.test.movieapp.MovieApp;
import com.test.movieapp.activities.MainActivity;
import com.test.movieapp.fragments.DetailFragment;
import com.test.movieapp.fragments.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AndroidModule.class, ApiModule.class})
public interface MovieDatabaseComponent {

    void inject(MovieApp movieApp);

    void inject(HomeFragment homeFragment);

    void inject(DetailFragment detailFragment);
}
