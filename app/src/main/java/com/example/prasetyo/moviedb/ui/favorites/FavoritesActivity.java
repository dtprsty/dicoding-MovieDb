package com.example.prasetyo.moviedb.ui.favorites;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.database.DatabaseConstruct;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity {

    private FavoriteAdapter favoriteAdapter;
    private Cursor listMovies;

    private ProgressDialog pDialog;
    private LinearLayoutManager layoutManager;

    @BindView(R.id.rootView)
    LinearLayout root;
    @BindView(R.id.listMovie)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_movie);
        init();
        new LoadData().execute();

    }

    private void init(){
        ButterKnife.bind(this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Favorites Movie");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void showGridView() {
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // untuk mengisi data dari JSON ke dalam adapter
        favoriteAdapter = new FavoriteAdapter(this, listMovies);
        recyclerView.setAdapter(favoriteAdapter);
        favoriteAdapter.notifyDataSetChanged();

        if (listMovies.getCount() == 0){
            Snackbar snackbar = Snackbar
                    .make(root, "Favorite Movie is empty", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class LoadData extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.show();

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(DatabaseConstruct.CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);
            pDialog.dismiss();

            listMovies = movies;
            showGridView();

        }
    }

    @Override protected void onResume() {
        super.onResume();
        new LoadData().execute();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }


}
