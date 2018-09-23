package com.example.prasetyo.moviedb.ui.favorites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.model.Movie;
import com.example.prasetyo.moviedb.movie.GridAdapter;
import com.example.prasetyo.moviedb.movie.MovieAdapter;
import com.example.prasetyo.moviedb.movie.MoviePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;
    private ArrayList<Movie> itemList = new ArrayList<Movie>();

    private LinearLayoutManager layoutManager;

    @BindView(R.id.rootView)
    LinearLayout root;
    @BindView(R.id.listMovie)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        init();

    }

    private void init(){
        ButterKnife.bind(this);
    }

    private void showGridView() {
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // untuk mengisi data dari JSON ke dalam adapter
        GridAdapter gridAdapter = new GridAdapter(this, itemList);
        recyclerView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }

}
