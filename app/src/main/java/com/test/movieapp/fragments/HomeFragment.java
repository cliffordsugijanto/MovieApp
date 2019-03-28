package com.test.movieapp.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.movieapp.MovieApp;
import com.test.movieapp.adapters.MovieListAdapter;
import com.test.movieapp.adapters.MovieSearchAdapter;
import com.test.movieapp.api.MainApi;
import com.test.movieapp.databinding.FragmentHomeBinding;
import com.test.movieapp.model.MovieListItem;
import com.test.movieapp.presenters.HomePresenter;
import com.test.movieapp.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    @Inject
    MainApi mainApi;

    FragmentHomeBinding binding;
    HomePresenter presenter;
    MovieListAdapter adapter;
    MovieSearchAdapter searchAdapter;
    ArrayList<MovieListItem> searchResults = new ArrayList<>(); //list to store the result of search
    Handler handler;
    int currentPage = 1;
    int type = 0; //type of the movie list, 0 for now playing, 1 for popular
    private boolean loading = false; //boolean flag for lazy load the movie list

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MovieApp.getComponent().inject(this);
        presenter = new HomePresenter(this, mainApi);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        //add 2 tab for now playing and popular
        binding.tlCategory.addTab(binding.tlCategory.newTab().setText("Now Playing"));
        binding.tlCategory.addTab(binding.tlCategory.newTab().setText("Popular"));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setup recyclerview for now playing and popular list
        GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        binding.rvMovieList.setLayoutManager(layoutManager);
        binding.rvMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Load more if we have reach the end to the recyclerView

                if (!loading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loading = true;
                    presenter.fetchData(type,currentPage + 1);
                }
            }
        });
        adapter = new MovieListAdapter(activity, new MovieListAdapter.OnMovieItemClickedListener() {
            @Override
            public void onMovieItemClicked(MovieListItem item) {
                activity.setFragment(DetailFragment.newInstance(item),DetailFragment.TAG,true);
            }
        });
        binding.rvMovieList.setAdapter(adapter);

        //setup tab layout
        binding.tlCategory.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type = tab.getPosition();
                currentPage = 1;
                presenter.fetchData(type,currentPage);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                scrollToTop();
            }
        });

        //setup autocomplete for search
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        binding.rvSearchResult.setLayoutManager(linearLayoutManager);
        searchAdapter = new MovieSearchAdapter(activity, searchResults, new MovieSearchAdapter.OnMovieItemClickedListener() {
            @Override
            public void onMovieItemClicked(MovieListItem item) {
                activity.setFragment(DetailFragment.newInstance(item),DetailFragment.TAG,true);
            }
        });
        binding.rvSearchResult.setAdapter(searchAdapter);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //resets the search
        binding.closeSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etSearch.setText("");
                searchResults.clear();
                searchAdapter.notifyDataSetChanged();
            }
        });
        //handler to call search API
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!binding.etSearch.getText().toString().equals("")) {
                        presenter.searchMovies(binding.etSearch.getText().toString());
                    }else{
                        binding.rvSearchResult.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
        presenter.fetchData(type,currentPage);
    }

    //method called when successfully fetch data from search movie API
    public void refreshSearchResults(List<MovieListItem> results){
        searchResults.clear();
        searchResults.addAll(results);
        searchAdapter.notifyDataSetChanged();
        if(results==null || results.size()==0){
            binding.rvSearchResult.setVisibility(View.GONE);
        }else{
            binding.rvSearchResult.setVisibility(View.VISIBLE);
        }
    }

    //method called when successfully fetch data from get movie list API
    public void refreshData(List<MovieListItem> results, int page) {
        loading = false;
        hideProgressDialog();
        if (page == 1) {
            adapter.refreshData(results);
            currentPage = 1;
        } else {
            adapter.addData(results);
            currentPage += 1;
        }
    }

    public void scrollToTop(){
        binding.rvMovieList.smoothScrollToPosition(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        currentPage = 1;
        type = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }
}
