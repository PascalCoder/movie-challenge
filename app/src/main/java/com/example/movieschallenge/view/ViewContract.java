package com.example.movieschallenge.view;

import com.example.movieschallenge.model.MovieList;

public interface ViewContract {

    void populateMovie(MovieList movieList);
    void onError(String errMessage);

}
