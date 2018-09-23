package com.example.prasetyo.moviedb.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.api.ApiEndPoint;
import com.example.prasetyo.moviedb.detail.DetailMovieActivity;
import com.example.prasetyo.moviedb.model.Movie;
import com.example.prasetyo.moviedb.movie.MovieAdapter;
import com.example.prasetyo.moviedb.movie.MoviePresenter;
import com.example.prasetyo.moviedb.movie.MovieView;
import com.example.prasetyo.moviedb.util.RecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements MovieView {
    private MovieAdapter movieAdapter;
    private MoviePresenter presenter;
    private ArrayList<Movie> itemList = new ArrayList<Movie>();

    private LinearLayoutManager layoutManager;
    private ProgressDialog pDialog;

    private static final String STATE_PENCARIAN = "state_pencarian";

    @BindView(R.id.rootView)
    LinearLayout root;
    @BindView(R.id.listMovie)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_movie);

        init();

        setTitle(R.string.search_movie);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Movie m = itemList.get(position);

                        Intent i = new Intent(SearchActivity.this, DetailMovieActivity.class);
                        i.putExtra(DetailMovieActivity.EXTRA_TITLE, m.getTitle());
                        i.putExtra(DetailMovieActivity.EXTRA_DATE, m.getDate());
                        i.putExtra(DetailMovieActivity.EXTRA_OVERVIEW, m.getOverview());
                        i.putExtra(DetailMovieActivity.EXTRA_POSTER, m.getPoster());
                        i.putExtra(DetailMovieActivity.EXTRA_BANNER, m.getBanner());
                        i.putExtra(DetailMovieActivity.EXTRA_RATING, m.getRating());
                        i.putExtra(DetailMovieActivity.EXTRA_VOTER, m.getVoter());
                        startActivity(i);
                    }
                })
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        final SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /*
            Gunakan method ini ketika search selesai atau OK
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            /*
            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.getMovie(newText);
                initRecyclerView();
                return false;
            }
        });
        return true;
    }

    private void init() {
        ApiEndPoint apiEndPoint = new ApiEndPoint();
        presenter = new MoviePresenter(this, apiEndPoint);
        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(SearchActivity.this, DividerItemDecoration.VERTICAL));
        // untuk mengisi data dari JSON ke dalam adapter
        movieAdapter = new MovieAdapter(this, itemList);
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        if (!pDialog.isShowing())
            pDialog.show();

    }

    @Override
    public void hideLoading() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar snackbar = Snackbar
                .make(root, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void getMovie(ArrayList<Movie> data) {

        itemList.clear();
        itemList.addAll(data);
        movieAdapter.notifyDataSetChanged();
    }
}
