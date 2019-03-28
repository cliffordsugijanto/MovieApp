package com.test.movieapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.test.movieapp.MovieApp;
import com.test.movieapp.api.MainApi;
import com.test.movieapp.databinding.FragmentMovieInfoBinding;
import com.test.movieapp.model.Cast;
import com.test.movieapp.model.MovieDetailApiResponse;
import com.test.movieapp.model.MovieListItem;
import com.test.movieapp.presenters.DetailPresenter;
import com.test.movieapp.utils.GlideUtil;

import java.util.List;

import javax.inject.Inject;

public class DetailFragment extends BaseFragment {
    public static final String TAG = DetailFragment.class.getSimpleName();
    public static final String ARG_ITEM = "arg_item";

    public static Fragment newInstance(MovieListItem item) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_ITEM, item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Inject
    MainApi mainApi;

    FragmentMovieInfoBinding binding;
    DetailPresenter presenter;
    MovieListItem item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MovieApp.getComponent().inject(this);
        binding = FragmentMovieInfoBinding.inflate(inflater, container, false);
        activity.setSupportActionBar(binding.orderToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new DetailPresenter(this,mainApi);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null){
            item = getArguments().getParcelable(ARG_ITEM);
            if(item!=null){
                presenter.fetchMovieDetail(item.getId());
                presenter.fetchCast(item.getId());
            }
        }
    }

    //called after successfully fetched movie details
    public void refreshData(MovieDetailApiResponse response){
        binding.tvTitle.setText(response.getTitle());
        binding.tvTime.setText(response.getRuntime()+" minutes");
        binding.tvRelease.setText(response.getReleaseDate());
        binding.tvGenre.setText("No information.");
        if(response.getGenres()!=null){
            String genreText = "";
            for(int i=0; i<response.getGenres().size(); i++){
                genreText+=response.getGenres().get(i).getName();
                if(i<response.getGenres().size()-1) genreText+="\n";
            }
            binding.tvGenre.setText(genreText);
        }
        binding.tvRating.setText(response.getVoteAverage()+"/10 ("+response.getVoteCount()+")");
        binding.tvSynopsis.setText(response.getOverview());
        GlideUtil.loadThumbnail(activity, binding.ivPoster, item);
    }

    public void refreshDataCast(List<Cast> casts){
        binding.tvArtists.setText("No information.");
        if(casts!=null){
            String castsText = "";
            for(int i=0; i<5; i++){
                castsText+=casts.get(i).getName()+" as "+casts.get(i).getCharacter();
                if(i<4) castsText+="\n";
            }
            binding.tvArtists.setText(castsText);
        }
    }
}
