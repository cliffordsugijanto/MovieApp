package com.test.movieapp.api;

import com.test.movieapp.model.MovieCastResponse;
import com.test.movieapp.model.MovieDetailApiResponse;
import com.test.movieapp.model.MovieListApiResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainApi {

    @GET("movie/now_playing")
    Call<MovieListApiResponse> getNowPlaying(@Query("api_key") String apiKey,
                                         @Query("page") int page);

    @GET("movie/popular")
    Call<MovieListApiResponse> getPopular(@Query("api_key") String apiKey,
                                             @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<MovieDetailApiResponse> getDetail(@Path("movie_id") int movieId,
                                           @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<MovieCastResponse> getCasts(@Path("movie_id") int movieId,
                                     @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieListApiResponse> searchMovie(@Query("api_key") String apiKey,
                                          @Query("query") String keyword);
}
