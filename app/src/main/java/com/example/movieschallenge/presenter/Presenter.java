package com.example.movieschallenge.presenter;

import android.widget.Toast;

import com.example.movieschallenge.model.MovieList;
import com.example.movieschallenge.model.MoviesApi;
import com.example.movieschallenge.view.ViewContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Presenter implements PresenterContract {

    private static final String BASE_URL = "https://movies-sample.herokuapp.com/";
    MoviesApi api;
    ViewContract view;

    @Override
    public void bindView(ViewContract view) {
        this.view = view;
    }

    @Override
    public void initializeRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
        api = retrofit.create(MoviesApi.class);
    }

    @Override
    public void getMovies() {
        api.getMovies().observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<MovieList>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(MovieList movieList) {
                                view.populateMovie(movieList);
                            }

                            @Override
                            public void onError(Throwable e) {
                                //Toast.makeText(, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
    }

    @Override
    public void onDestroy() {

    }
}
