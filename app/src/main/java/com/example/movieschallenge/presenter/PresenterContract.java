package com.example.movieschallenge.presenter;

import com.example.movieschallenge.view.ViewContract;

public interface PresenterContract {

    void bindView(ViewContract view);
    void initializeRetrofit();
    void getMovies();
    void onDestroy();
}
