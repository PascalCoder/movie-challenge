package com.example.movieschallenge.view;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.movieschallenge.R;
import com.example.movieschallenge.model.MovieList;
import com.example.movieschallenge.presenter.CustomAdapter;
import com.example.movieschallenge.presenter.Presenter;

public class MainActivity extends AppCompatActivity implements ViewContract, SearchView.OnQueryTextListener{

    RecyclerView recyclerView;
    Presenter presenter;
    static Handler handler;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        presenter = new Presenter();
        presenter.bindView(this);
        presenter.initializeRetrofit();
        presenter.getMovies();


        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 5000);
            }
        };
        handler.post(runnable);

        Runnable runnable1 = () -> {

        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        };

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(onActionExpandListener);

        return true; //super.onCreateOptionsMenu(menu)
    }

    @Override
    public void populateMovie(MovieList movieList) {
        recyclerView.setAdapter(new CustomAdapter(movieList));
    }

    @Override
    public void onError(String errMessage) {
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //handler.removeCallbacks();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        String userInput = s.toLowerCase();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
