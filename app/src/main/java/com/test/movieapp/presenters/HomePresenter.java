package com.test.movieapp.presenters;

import com.test.movieapp.R;
import com.test.movieapp.api.MainApi;
import com.test.movieapp.fragments.HomeFragment;
import com.test.movieapp.model.MovieListApiResponse;
import com.test.movieapp.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {
    HomeFragment view;
    MainApi mainApi;

    public HomePresenter(HomeFragment view, MainApi mainApi) {
        this.mainApi = mainApi;
        this.view = view;
    }

    //called to fetch data, type 0 for now playing, type 1 for popular
    public void fetchData(int type, int page){
        switch (type){
            case 0:
                fetchNowPlaying(page);
                break;
            case 1:
                fetchPopular(page);
                break;
        }

    }

    //called to fetch now playing
    public void fetchNowPlaying(int page){
        view.showProgressDialog();
        mainApi.getNowPlaying(view.getString(R.string.api_key),page).enqueue(new Callback<MovieListApiResponse>() {
            @Override
            public void onResponse(Call<MovieListApiResponse> call, Response<MovieListApiResponse> response) {
                if(response.isSuccessful()){
                    view.refreshData(response.body().getResults(),page);
                }
                else{
                    view.hideProgressDialog();
                    ToastUtils.showToast(view.getActivity(),response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieListApiResponse> call, Throwable t) {
                view.hideProgressDialog();
                ToastUtils.showToast(view.getActivity(),t.getMessage());
            }
        });
    }

    //called to fetch popular
    public void fetchPopular(int page){
        view.showProgressDialog();
        mainApi.getPopular(view.getString(R.string.api_key),page).enqueue(new Callback<MovieListApiResponse>() {
            @Override
            public void onResponse(Call<MovieListApiResponse> call, Response<MovieListApiResponse> response) {
                if(response.isSuccessful()){
                    view.refreshData(response.body().getResults(),page);
                }
                else{
                    view.hideProgressDialog();
                    ToastUtils.showToast(view.getActivity(),response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieListApiResponse> call, Throwable t) {
                view.hideProgressDialog();
                ToastUtils.showToast(view.getActivity(),t.getMessage());
            }
        });
    }

    //called to get movies based on keyword (title)
    public void searchMovies(String keyword){
        mainApi.searchMovie(view.getString(R.string.api_key),keyword).enqueue(new Callback<MovieListApiResponse>() {
            @Override
            public void onResponse(Call<MovieListApiResponse> call, Response<MovieListApiResponse> response) {
                if(response.isSuccessful()){
                    ToastUtils.showToast(view.getActivity(),"success"+response.body().getResults().size());
                    view.refreshSearchResults(response.body().getResults());
                }
                else{
                    view.hideProgressDialog();
                    ToastUtils.showToast(view.getActivity(),response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieListApiResponse> call, Throwable t) {
                view.hideProgressDialog();
                ToastUtils.showToast(view.getActivity(),t.getMessage());
            }
        });
    }
}
