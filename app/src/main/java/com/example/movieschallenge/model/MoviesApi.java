package com.example.movieschallenge.model;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MoviesApi {

    @GET("api/movies")
    Observable<MovieList> getMovies();
}
