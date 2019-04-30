package com.example.movieschallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList {

    @SerializedName("data")
    @Expose
    public List<Movie> movieList = null;

    public List<Movie> getMovieList(){
        return movieList;
    }
}
