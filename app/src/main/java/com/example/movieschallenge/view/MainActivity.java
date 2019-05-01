package com.example.movieschallenge.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.movieschallenge.R;
import com.example.movieschallenge.model.MovieList;
import com.example.movieschallenge.presenter.CustomAdapter;
import com.example.movieschallenge.presenter.Presenter;

public class MainActivity extends AppCompatActivity implements ViewContract, SearchView.OnQueryTextListener{

    RecyclerView recyclerView;
    Presenter presenter;

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

        Thread thread = new Thread(){
            public void run(){
                while(!isInterrupted()){
                    try{
                        Thread.sleep(600_000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                presenter.bindView(MainActivity.this);
                                presenter.initializeRetrofit();
                                presenter.getMovies();

                                Toast.makeText(MainActivity.this, "App restarted!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
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

        return true;
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
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        CustomAdapter customAdapter = (CustomAdapter) recyclerView.getAdapter();
        customAdapter.getFilter().filter(s);
        return false;
    }
}
