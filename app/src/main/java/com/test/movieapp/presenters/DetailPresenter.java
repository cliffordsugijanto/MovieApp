package com.test.movieapp.presenters;

import com.test.movieapp.R;
import com.test.movieapp.api.MainApi;
import com.test.movieapp.fragments.DetailFragment;
import com.test.movieapp.fragments.HomeFragment;
import com.test.movieapp.model.MovieDetailApiResponse;
import com.test.movieapp.model.MovieListApiResponse;
import com.test.movieapp.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter {
    DetailFragment view;
    MainApi mainApi;

    public DetailPresenter(DetailFragment view, MainApi mainApi) {
        this.mainApi = mainApi;
        this.view = view;
    }

    //called to fetch movie detail
    public void fetchMovieDetail(int movieId){
        view.showProgressDialog();
        mainApi.getDetail(movieId,view.getString(R.string.api_key)).enqueue(new Callback<MovieDetailApiResponse>() {
            @Override
            public void onResponse(Call<MovieDetailApiResponse> call, Response<MovieDetailApiResponse> response) {
                view.hideProgressDialog();
                if(response.isSuccessful()){
                    view.refreshData(response.body());
                }
                else{
                    ToastUtils.showToast(view.getActivity(),response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieDetailApiResponse> call, Throwable t) {
                view.hideProgressDialog();
                ToastUtils.showToast(view.getActivity(),t.getMessage());
            }
        });
    }
}
